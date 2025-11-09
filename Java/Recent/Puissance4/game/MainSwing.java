package game;

import board.*;
import player.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import IG.Panel;


public class MainSwing {

    public static void main(String[] args) {
        Board board = new Board(10,5);
        Player players[] = {new Player("o","Red.png"),new Player("x","Blue.png")};
        int playerIndex = 1;

        Handler handler = new Handler();

        // Creates a new JFrame , which will close either when we stop it from running or click on the upper right cross
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        // Setting up the Panel , values are arbitrary , used for showing what's useful
        frame.add(new Panel(board,players));
        frame.setSize(Panel.IMAGESIZE*(Board.LENGTH+1)-25,Panel.IMAGESIZE*(Board.HEIGHT+2)+10);
        frame.setVisible(true);

        // Second frame for buttons , placed right below the first
        JFrame frame2 = new JFrame("Click arrow to place pawn in above column");
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setLocationRelativeTo(null);
        // Moving the frame below the first
        Point p = frame2.getLocation();
        frame2.setLocation((int)p.getX(),(int)p.getY()+(Board.HEIGHT+2)*Panel.IMAGESIZE);
        MainSwing.initButtons(frame2,handler);

        while (!board.isFinished()) {
            boolean ok = false;
            while (!ok) {
                try {
                    int column = MainSwing.choose(handler);
                    ok = board.placePawn(players[(playerIndex++)%2].getPawn(), column);
                    if (!ok) {
                        System.out.println("Column is full");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input");
                }
            }
            frame.repaint();
        }
        System.out.println(players[(playerIndex+1)%2].getSwingVisual() + " wins\n");
    }

    /**
     * Returns a column index given when a button is clicked on the frame2
     * @param h the handler
     * @return the given column index
     */
    public static int choose(Handler h) {
        // Doing something useless is required , otherwise it doesn't refresh
        while (h.getValue() < 0) {
            System.out.print("");
        }

        int res = h.getValue();
        h.setValue(-1);

        return res;
    }

    /**
     * Initializes the frame by associating a new button to every column
     * @param f the frame to initialize
     * @param h the handler
     */
    public static void initButtons(JFrame f , Handler h) {
        // Adds a button for every column
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        for (int i = 0 ; i < Board.LENGTH ; i++) {
            // This integer is necessary because if we do h.setValue(i) instead , every button will share the same value (plus , java doesn't allow it)
            int buttonValue = i;
            JButton button = new JButton("↑");
            button.setPreferredSize(new Dimension(40,40));
            // When the button is clicked on , it gives its value to the handler
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        h.setValue(buttonValue);
                    }
            });
            panel.add(button);
        }

        // Displaying the frame containing the buttons .
        f.add(panel);
        f.pack();
        f.setVisible(true);
    }
}
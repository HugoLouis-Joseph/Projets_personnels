import javax.swing.*;

import Board.*;
import IG.*;

import java.awt.event.*;
import java.util.*;

public class SwingMain {
    protected static int IMAGESIZE = 50;
    public static void main(String[] args) {
        Board board = new Board(10,10,15);

        JFrame frame = new JFrame("Boutons d'action");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setSize(SwingMain.IMAGESIZE*(Board.LENGTH+1),SwingMain.IMAGESIZE*(Board.HEIGHT+1));
        
        Tile[][] tiles = board.getTiles();
        ArrayList<ColButton> buttons = new ArrayList<ColButton>();

        Handler handler = new Handler();
        for (int lineI = 0 ; lineI < Board.LENGTH ; lineI++) {
            for (int columnI = 0 ; columnI < Board.HEIGHT ; columnI++) {
                ColButton button = new ColButton(tiles[lineI][columnI]);
                button.setBounds(SwingMain.IMAGESIZE*lineI, SwingMain.IMAGESIZE*columnI, SwingMain.IMAGESIZE, SwingMain.IMAGESIZE);

                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handler.setChosen(button.getTile().getColor());
                    }
                });

                buttons.add(button);
                frame.add(button);
            }
        }

        frame.setVisible(true);

        while (board.winState() == 0) {
            while (handler.getChosen() == null) {
                System.out.print("");
            }
            board.playOneSwingStep(handler.getChosen());
            handler.setChosen(null);
            SwingMain.changeColor(buttons);
        }
        if (board.winState() == -1) {
            System.out.println("You lose\n");
        }
        else {
            System.out.println("You win\n");
        }
    }

    protected static void changeColor(ArrayList<ColButton> buttons) {
        for (ColButton button : buttons) {
            button.poke();
        }
    }
}

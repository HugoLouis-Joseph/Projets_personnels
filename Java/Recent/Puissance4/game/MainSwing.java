package game;

import board.*;
import io.Input;
import player.*;
import javax.swing.*;
import IG.Panel;


public class MainSwing {
    public static void main(String[] args) {
        Board board = new Board(10,5);
        Player players[] = {new Player("o","Red.png"),new Player("x","Blue.png")};
        int playerIndex = 1;

        // Creates a new JFrame , which will close either when we stop it from running or click on the upper right cross
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        // Setting up the Panel , values are arbitrary , used for showing what's useful
        frame.add(new Panel(board,players));
        frame.setSize(Panel.IMAGESIZE*(Board.LENGTH+1)-25,Panel.IMAGESIZE*(Board.HEIGHT+2)+10);
        frame.setVisible(true);

        while (!board.isFinished()) {
            boolean ok = false;
            while (!ok) {
                try {
                    System.out.print("Enter a column index : ");
                    int column = Input.readInt();
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
}
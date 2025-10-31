package game;

import board.*;
import io.Input;
import player.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Player players[] = {new Player("o"),new Player("x")};
        int playerIndex = 0;
        board.displayIntoTerminal();

        for (int i = 0 ; i < 7 ; i++) {
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
            board.displayIntoTerminal();
        }
    }
}

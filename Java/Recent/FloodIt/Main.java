import Board.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.display();
        while (board.winState() == 0) {
            board.playOneStep();
            board.display();
        }
        if (board.winState() == -1) {
            System.out.println("You lose\n");
        }
        else {
            System.out.println("You win\n");
        }
    }
}

// Package //
package actions;
// ------- //

// Imports //
import board.*;
import ship.*;
import javax.swing.*;
// ------- //

/**
 * Public class for PlaceShip
 */
public class PlaceSubmarine extends Action {

    /**
     * Creates a new PlaceShip action
     * @param board board
     */
    public PlaceSubmarine(Board board) {
        super("PlaceSubmarine",board);
    }

    /**
     * Places a new submarine at given coordinates with given orientation
     */
    public void act() {
        System.out.println("\nSize of the submarine :" + 3 + "\n");
        boolean nok = true;
        int cpl[] = {0,0};
        Orientation dir = Orientation.NORTH;

        while (nok) {
            nok = false;
            cpl = super.input();
            dir = Orientation.choose();
            if (this.board.outOfBounds(cpl, dir, 3,1) || this.board.shipCollision(cpl,dir,3,1)) {
                System.out.println("Invalid coordinates or direction , either a collision with another ship was detected , or the submarine would be out of bounds\n");
                nok = true;
            }
        }

        Submarine sub = new Submarine(3, dir , cpl);
        this.board.addSub(sub);
        this.board.placeShip(cpl , sub , true);
        System.out.println("");
        
    }

    public void act(JFrame m , JFrame f) {
    }

    /**
     * Board has to not have a submarine
     * @return boolean
     */
    public boolean canAct() {
        return this.board.getSub() == null;
    }
}

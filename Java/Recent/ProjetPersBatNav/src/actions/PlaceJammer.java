// Package //
package actions;
// ------- //

// Imports //
import board.*;
import ship.*;
import javax.swing.*;
// ------- //

/**
 * Public class for PlaceJammer
 */
public class PlaceJammer extends Action {

    /**
     * Creates a new PlaceShip action
     * @param board board
     */
    public PlaceJammer(Board board) {
        super("PlaceJammer",board);
    }

    /**
     * Places a new jammer at given coordinates with given orientation
     */
    public void act() {
        System.out.println("\nSize of the jammer :" + 2 + "\n");
        boolean nok = true;
        int cpl[] = {0,0};
        Orientation dir = Orientation.NORTH;

        while (nok) {
            nok = false;
            cpl = super.input();
            dir = Orientation.choose();
            if (this.board.outOfBounds(cpl, dir, 2,1) || this.board.shipCollision(cpl,dir,2,1)) {
                System.out.println("Invalid coordinates or direction , either a collision with another ship was detected , or the jammer would be out of bounds\n");
                nok = true;
            }
        }

        Jammer jam = new Jammer(2, dir , cpl);
        this.board.addJammer(jam);
        this.board.placeShip(cpl , jam , true);
        System.out.println("");
        
    }

    public void act(JFrame m , JFrame f) {
    }

    /**
     * Board has to not have a jammer
     * @return boolean
     */
    public boolean canAct() {
        return this.board.getJammer() == null;
    }
}
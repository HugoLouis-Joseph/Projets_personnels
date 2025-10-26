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
public class PlaceCarrier extends Action {

    /**
     * Creates a new PlaceShip action
     * @param board board
     */
    public PlaceCarrier(Board board) {
        super("PlaceCarrier",board);
    }

    /**
     * Places a new jammer at given coordinates with given orientation
     */
    public void act() {
        System.out.println("\nSize of the jammer : 3*2 \n");
        boolean nok = true;
        int cpl[] = {0,0};
        Orientation dir = Orientation.NORTH;

        while (nok) {
            nok = false;
            cpl = super.input();
            dir = Orientation.choose();
            if (this.board.outOfBounds(cpl, dir, 3,2) || this.board.shipCollision(cpl,dir,3,2)) {
                System.out.println("Invalid coordinates or direction , either a collision with another ship was detected , or the jammer would be out of bounds\n");
                nok = true;
            }
        }

        CarrierShip car = new CarrierShip(3,2, dir , cpl);
        this.board.addCarrier(car);
        this.board.placeShip(cpl , car , false);
        System.out.println("");
        
    }

    public void act(JFrame m , JFrame f) {
    }

    /**
     * Board has to not have a jammer
     * @return boolean
     */
    public boolean canAct() {
        return this.board.getCarrier() == null;
    }
}
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
public class PlaceShip extends Action {
    // letter (for printing) of ships
    public static String LETTERS[] = {" A "," B "," C "," D "," E "};
    // index of current size
    protected int ind = 0;

    /**
     * Creates a new PlaceShip action
     * @param board board
     */
    public PlaceShip(Board board) {
        super("PlaceShip",board);
    }

    /**
     * Places a new ship at given coordinates with given orientation
     */
    public void act() {
        System.out.println("\nSize of the ship :" + 3 + "\n");
        boolean nok = true;
        int cpl[] = {0,0};
        Orientation dir = Orientation.NORTH;

        while (nok) {
            nok = false;
            cpl = super.input();
            dir = Orientation.choose();
            if (this.board.outOfBounds(cpl, dir, 3,1) || this.board.shipCollision(cpl,dir,3,1)) {
                System.out.println("Invalid coordinates or direction , either a collision with another ship was detected , or the ship would be out of bounds\n");
                nok = true;
            }
        }

        Ship ship = new Ship(3,1, dir , cpl , PlaceShip.LETTERS[this.ind++],"Bateau");
        this.board.placeShip(cpl , ship , true);
        System.out.println("");
        
    }

    public void act(JFrame m , JFrame f) {
    }

    /**
     * Index has to be lower than the amount of lengths
     * @return boolean
     */
    public boolean canAct() {
        return this.ind < 4;
    }
}

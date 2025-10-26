// Package //
package actions;
// ------- //

// Imports //
import board.*;
import io.Input;
import jeu.Main;
import ship.*;
import java.util.*;
import javax.swing.*;
// ------- //

/**
 * Public class for MoveShip
 */
public class RotateShip extends Action{
    protected HashMap<Ship,ArrayList<Orientation>> shipsThatCanRotate;
    /**
     * Creates a new MoveShip action
     * @param board given board
     */
    public RotateShip(Board board) {
        super("RotateShip",board);
    }

    /**
     * Moves a ship
     */
    public void act() {
        ArrayList<Ship> ships = new ArrayList<Ship>();

        System.out.println("Ships available for rotation : ");

        int i = 0;
        for (Ship ship : this.shipsThatCanRotate.keySet()) {
            ships.add(ship);
            System.out.print("["+(i++)+" :"+ship.getHull()[0][0].getLetter()+"]");
        }

        boolean nok = true;
        int j = 0;
        while (nok) {
            try {
                System.out.print("\nWhich ship do you want to rotate : ");
                j = Input.readInt();
                nok = false;
                if (j < 0 || j >= i) {
                    System.out.print("\nInvalid ship number");
                    nok = true;
                }
            } catch (java.io.IOException e) {
                System.out.print("\nEnter an integer");
            } 
        }

        Ship ship = ships.get(j);
        i=0;
        ArrayList<Orientation> types = new ArrayList<Orientation>();
        for (Orientation tp : this.shipsThatCanRotate.get(ship)) {
            types.add(tp);
            System.out.print("["+(i++)+" : "+tp.toString()+"]");
        }

        nok = true;
        j=0;

        while (nok) {
            try {
                System.out.print("\nIn which direction should he rotate : ");
                j = Input.readInt();
                nok = false;
                if (j < 0 || j >= i) {
                    System.out.print("\nInvalid rotation number");
                    nok = true;
                }
            } catch (java.io.IOException e) {
                System.out.print("\nEnter an integer");
            } 
        }

        Orientation type = types.get(j);

        this.board.rotate(ship,type);
    }

    public void act(JFrame m , JFrame f) {
        ArrayList<Ship> ships = new ArrayList<Ship>();

        for (Ship ship : this.shipsThatCanRotate.keySet()) {
            ships.add(ship);
        }

        Ship ship = ships.get(Main.choose(ships.size(),f));
        Orientation type = this.shipsThatCanRotate.get(ship).get(Main.choose(this.shipsThatCanRotate.get(ship).size(),f));
        this.board.rotate(ship,type);

    }

    /**
     * At least one ship can rotate
     * @return boolean
     */
    public boolean canAct() {
        this.shipsThatCanRotate = this.board.shipsThatCanRotate();
        return !this.shipsThatCanRotate.isEmpty();
    }
}
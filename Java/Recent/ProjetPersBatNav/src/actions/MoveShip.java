// Package //
package actions;
// ------- //

// Imports //
import board.*;
import io.Input;
import ship.*;
import java.util.*;
import jeu.*;
// ------- //

import javax.swing.JFrame;

/**
 * Public class for MoveShip
 */
public class MoveShip extends Action{
    protected HashMap<Ship,ArrayList<String>> shipsThatCanMove;
    /**
     * Creates a new MoveShip action
     * @param board given board
     */
    public MoveShip(Board board) {
        super("MoveShip",board);
    }

    /**
     * Moves a ship
     */
    public void act() {
        ArrayList<Ship> ships = new ArrayList<Ship>();

        System.out.println("Ships available for movement : ");

        int i = 0;
        for (Ship ship : this.shipsThatCanMove.keySet()) {
            ships.add(ship);
            System.out.print("["+(i++)+" :"+ship.getHull()[0][0].getLetter()+"]");
        }

        boolean nok = true;
        int j = 0;
        while (nok) {
            try {
                System.out.print("\nWhich ship do you want to move : ");
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
        System.out.println("\nWhich direction should he move : ");
        ArrayList<String> types = new ArrayList<String>();
        for (String tp : this.shipsThatCanMove.get(ship)) {
            types.add(tp);
            System.out.print("["+(i++)+" : "+tp+"]");
        }

        nok = true;
        j=0;

        while (nok) {
            try {
                j = Input.readInt();
                nok = false;
                if (j < 0 || j >= i) {
                    System.out.print("\nInvalid direction number");
                    nok = true;
                }
            } catch (java.io.IOException e) {
                System.out.print("\nEnter an integer");
            } 
        }

        String type = types.get(j);

        this.board.move(ship,type);
    }

    public void act(JFrame m ,JFrame f) {
        ArrayList<Ship> ships = new ArrayList<Ship>();

        for (Ship ship : this.shipsThatCanMove.keySet()) {
            ships.add(ship);
        }

        Ship ship = ships.get(Main.choose(ships.size(),f));
        String type = this.shipsThatCanMove.get(ship).get(Main.choose(this.shipsThatCanMove.get(ship).size(),f));
        this.board.move(ship,type);
    }

    /**
     * At least one ship can move
     * @return boolean
     */
    public boolean canAct() {
        this.shipsThatCanMove = this.board.shipsThatCanMove();
        return !this.shipsThatCanMove.isEmpty();
    }
}
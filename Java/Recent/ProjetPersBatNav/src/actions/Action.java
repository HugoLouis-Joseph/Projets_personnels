// Package //
package actions;
// ------- //

// Imports //
import board.*;
import io.*;
import javax.swing.*;
// ------- //

/**
 * Public abstract class for Action
 */
public abstract class Action {
    protected String name;
    protected Board board;
    protected boolean canReChoose = false;

    /**
     * Creates a new Action
     * @param name name of the action
     * @param game game
     */
    public Action(String name,Board board) {
        this.name = name;
        this.board = board;
    }

    /**
     * Prints the name of the action
     */
    public void print() {
        System.out.println(this.name);
    }

    /**
     * Gets inputs for coordinates
     * @return a couple of given coordinates
     */
    public int[] input() {
        int x = 0;
        int y = 0;
        int cpl[] = new int[2];

        boolean nokX = true;
        boolean nokY = true;

        System.out.println("Action : " + this.name);
        while (nokX) {
            try {
                System.out.print("Enter a line number : ");
                x = Input.readInt();
                if (x <= 0 || x > this.board.getLength()) {
                    System.out.println("Out of bounds");
                } else {
                    nokX = false;
                    cpl[0] = x-1;
                }
            } catch (java.io.IOException e) {
                System.out.println("Enter an integer");
            }
        } 
        while (nokY) {
            try {
                System.out.print("Enter a column number : ");
                y = Input.readInt();
                if (y <= 0 || y > this.board.getWidth()) {
                    System.out.println("Out of bounds");
                } else {
                    nokY = false;
                    cpl[1] = y-1;
                }
            } catch (java.io.IOException e) {
                System.out.println("Enter an integer");
            }
        }
        System.out.println("");
        return cpl;
    }

    /**
     * Getter for name
     * @return string
     */
    public String getName() {
        return this.name;
    }

    /**
     * Executes the action
     */
    public abstract void act();

    public abstract void act(JFrame m , JFrame f);

    /**
     * Returns true if can act
     * @return boolean
     */

    public abstract boolean canAct();
    /**
     * Returns true if can choose another action
     * @return boolean
     */
    public boolean canReChoose() {
        boolean canReChoose = this.canReChoose;
        this.canReChoose = false;
        return canReChoose;
    }
}

// Package //
package actions;
// ------- //

import javax.swing.JFrame;

// Imports //
import board.*;
// ------- //
import jeu.Main;

/**
 * Public class for Attack
 */
public class AttackAir extends Action {
    /**
     * Creates a new Attack action
     * @param board target board
     */
    public AttackAir(Board board) {
        super("AttackAir",board);
    }

    /**
     * Hits the tile at the given coordinates
     * @param x line
     * @param y column
     */
    public void act() {
        System.out.println("");
        int cpl[] = super.input();
        this.board.getBoard()[cpl[0]][cpl[1]].hitAir(this.board);
        System.out.println("");
    }

    public void act(JFrame m ,JFrame f) {
        System.out.println("");
        int cpl[] = new int[2];
        cpl[0] = Main.choose(board.getLength()-1,f);
        cpl[1] = Main.choose(board.getWidth()-1,f);
        this.board.getBoard()[cpl[0]][cpl[1]].hitAir(this.board);
        System.out.println("");
    }

    /**
     * You can always attack
     * @return true
     */
    public boolean canAct() {
        return this.board.getFlak() != null;
    }
}

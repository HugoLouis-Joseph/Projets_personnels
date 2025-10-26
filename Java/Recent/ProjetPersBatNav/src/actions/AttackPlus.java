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
public class AttackPlus extends Action {
    protected Board boardThatAttacks;
    /**
     * Creates a new Attack action
     * @param board target board
     */
    public AttackPlus(Board boardToAttack , Board boardThatAttacks) {
        super("AttackPlus",boardToAttack);
        this.boardThatAttacks = boardThatAttacks;
    }

    /**
     * Hits the tile at the given coordinates
     * @param x line
     * @param y column
     */
    public void act() {
        System.out.println("");
        int cpl[] = super.input();
        this.board.getBoard()[cpl[0]][cpl[1]].hit();
        if (cpl[0]-1 >= 0) {
            this.board.getBoard()[cpl[0]-1][cpl[1]].hit();
        }
        if (cpl[0]+1 < this.board.getLength()) {
            this.board.getBoard()[cpl[0]+1][cpl[1]].hit();
        }
        if (cpl[1]-1 >= 0) {
            this.board.getBoard()[cpl[0]][cpl[1]-1].hit();
        }
        if (cpl[1]+1 < this.board.getWidth()) {
            this.board.getBoard()[cpl[0]][cpl[1]+1].hit();
        }
        System.out.println("");
    }

    public void act(JFrame m ,JFrame f) {
        System.out.println("");
        int cpl[] = new int[2];
        cpl[0] = Main.choose(board.getLength()-1,f);
        cpl[1] = Main.choose(board.getWidth()-1,f);
        this.board.getBoard()[cpl[0]][cpl[1]].hit();
        if (cpl[0]-1 >= 0) {
            this.board.getBoard()[cpl[0]-1][cpl[1]].hit();
        }
        if (cpl[0]+1 < this.board.getLength()) {
            this.board.getBoard()[cpl[0]+1][cpl[1]].hit();
        }
        if (cpl[1]-1 >= 0) {
            this.board.getBoard()[cpl[0]][cpl[1]-1].hit();
        }
        if (cpl[1]+1 < this.board.getWidth()) {
            this.board.getBoard()[cpl[0]][cpl[1]+1].hit();
        }
        System.out.println("");
    }
    
    /**
     * Attack available when submarine is not submerged and not destroyed
     * @return boolean
     */
    public boolean canAct() {
        return this.boardThatAttacks.getSub() != null && !this.boardThatAttacks.getSub().isSubmerged() && !this.boardThatAttacks.getSub().isDestroyed();
    }
}

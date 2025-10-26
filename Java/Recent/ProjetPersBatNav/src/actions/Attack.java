// Package //
package actions;
// ------- //

// Imports //
import board.*;
import javax.swing.*;
import jeu.*;
// ------- //

/**
 * Public class for Attack
 */
public class Attack extends Action {
    /**
     * Creates a new Attack action
     * @param board target board
     */
    public Attack(Board board) {
        super("Attack",board);
    }

    /**
     * Hits the tile at the given coordinates
     * @param x line
     * @param y column
     */
    public void act() {
        System.out.println("");
        int cpl[] = super.input();
        if (this.board.getBoard()[cpl[0]][cpl[1]].getPart() != null && !this.board.getBoard()[cpl[0]][cpl[1]].getPart().isHit() && !this.board.getBoard()[cpl[0]][cpl[1]].isJammed()) {
            this.canReChoose = true;
        }
        this.board.getBoard()[cpl[0]][cpl[1]].hit();
        System.out.println("");
    }

    public void act(JFrame m ,JFrame f) {
        int cpl[] = new int[2];
        cpl[0] = Main.choose(board.getLength()-1,f);
        cpl[1] = Main.choose(board.getWidth()-1,f);
        if (this.board.getBoard()[cpl[0]][cpl[1]].getPart() != null && !this.board.getBoard()[cpl[0]][cpl[1]].getPart().isHit() && !this.board.getBoard()[cpl[0]][cpl[1]].isJammed()) {
            this.canReChoose = true;
        }
        this.board.getBoard()[cpl[0]][cpl[1]].hit();
        System.out.println("");

        if (this.canReChoose()) {
            m.repaint();
            this.act(m,f);
        }
    }

    /**
     * You can always attack
     * @return true
     */
    public boolean canAct() {
        return true;
    }
}

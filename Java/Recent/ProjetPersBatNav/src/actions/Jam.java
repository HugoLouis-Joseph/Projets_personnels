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
public class Jam extends Action {
    protected int cooldown = 0;
    /**
     * Creates a new Attack action
     * @param board target board
     */
    public Jam(Board board) {
        super("Jam",board);
    }

    /**
     * Hits the tile at the given coordinates
     * @param x line
     * @param y column
     */
    public void act() {
        System.out.println("");
        int cpl[] = super.input();
        for (int offsetLeft = 2 ; offsetLeft >= 0 ; offsetLeft--) {
            for (int offsetUp = 0 ; offsetUp < 3 ; offsetUp++) {
                if (0 <= cpl[0]+(2-offsetLeft)-offsetUp && cpl[0]+(2-offsetLeft)-offsetUp < this.board.getLength() && 0 <= cpl[1]+offsetLeft-offsetUp && cpl[1]+offsetLeft-offsetUp < this.board.getWidth()) {
                    this.board.getBoard()[cpl[0]+(2-offsetLeft)-offsetUp][cpl[1]+offsetLeft-offsetUp].jam();
                }
            }
        }
        for (int offsetLeft = 1 ; offsetLeft >= 0 ; offsetLeft--) {
            for (int offsetUp = 0 ; offsetUp < 2 ; offsetUp++) {
                if (0 <= cpl[0]+(1-offsetLeft)-offsetUp && cpl[0]+(1-offsetLeft)-offsetUp < this.board.getLength() && 0 <= cpl[1]+offsetLeft-offsetUp && cpl[1]+offsetLeft-offsetUp < this.board.getWidth()) {
                    this.board.getBoard()[cpl[0]+(1-offsetLeft)-offsetUp][cpl[1]+offsetLeft-offsetUp].jam();
                }
            }
        }
        System.out.println("");
        this.cooldown = 9;
    }

    public void act(JFrame m ,JFrame f) {
        System.out.println("");
        int cpl[] = new int[2];
        cpl[0] = Main.choose(board.getLength()-1,f);
        cpl[1] = Main.choose(board.getWidth()-1,f);
        for (int offsetLeft = 2 ; offsetLeft >= 0 ; offsetLeft--) {
            for (int offsetUp = 0 ; offsetUp < 3 ; offsetUp++) {
                if (0 <= cpl[0]+(2-offsetLeft)-offsetUp && cpl[0]+(2-offsetLeft)-offsetUp < this.board.getLength() && 0 <= cpl[1]+offsetLeft-offsetUp && cpl[1]+offsetLeft-offsetUp < this.board.getWidth()) {
                    this.board.getBoard()[cpl[0]+(2-offsetLeft)-offsetUp][cpl[1]+offsetLeft-offsetUp].jam();
                }
            }
        }
        for (int offsetLeft = 1 ; offsetLeft >= 0 ; offsetLeft--) {
            for (int offsetUp = 0 ; offsetUp < 2 ; offsetUp++) {
                if (0 <= cpl[0]+(1-offsetLeft)-offsetUp && cpl[0]+(1-offsetLeft)-offsetUp < this.board.getLength() && 0 <= cpl[1]+offsetLeft-offsetUp && cpl[1]+offsetLeft-offsetUp < this.board.getWidth()) {
                    this.board.getBoard()[cpl[0]+(1-offsetLeft)-offsetUp][cpl[1]+offsetLeft-offsetUp].jam();
                }
            }
        }
        System.out.println("");
        this.cooldown = 9;
    }

    /**
     * You can always attack
     * @return true
     */
    public boolean canAct() {
        return this.board.getJammer() != null && this.cooldown-- <= 0;
    }
}

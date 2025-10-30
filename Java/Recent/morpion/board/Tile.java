package board;

import player.*;

public class Tile {

    protected Pawn content;

    /**
     * Creates a new Tile , associated to NULLPLAYER (no player) .
     */
    public Tile() {
        this.content = Pawn.NULLPAWN;
    }

    /**
     * Getter for content
     * @return the Pawn object present on the Tile .
     */
    public Pawn getContent() {
        return this.content;
    }

    /**
     * Setter for content
     * @param content the content replacing current content .
     */
    public void setContent(Pawn content) {
        this.content = content;
    }

    /**
     * Display method , prints the player's visual .
     */
    public void displayIntoTerminal() {
        System.out.print(this.content.getVisual());
    }
}

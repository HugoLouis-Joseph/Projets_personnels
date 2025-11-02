package player;

public class Pawn {

    protected Player player;

    /**
     * Creates a new Pawn object , associated to a specific Player
     * @param player the associated Player .
     */
    public Pawn(Player player) {
        this.player = player;
    }

    /**
     * Getter for visual
     * @return the Player's visual .
     */
    public String getVisual() {
        return this.player.getVisual();
    }

    /**
     * Getter for swingVisual
     * @return the Player's swingVisual .
     */
    public String getSwingVisual() {
        return this.player.getSwingVisual();
    }
}

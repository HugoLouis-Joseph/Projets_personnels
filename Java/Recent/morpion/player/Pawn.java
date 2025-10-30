package player;

public class Pawn {

    protected Player player;

    //Single instance of NULLPAWN required , used when no Pawn is present on a Tile .
    public static Pawn NULLPAWN = new Pawn(Player.NULLPLAYER);

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

}

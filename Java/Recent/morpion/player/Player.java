package player;

public class Player {

    protected String visual;

    //Single instance of NULLPLAYER required , used to mark a lack of Player , representation is a space to show emptiness .
    public static Player NULLPLAYER = new Player(" ");

    /**
     * Creates a new Player object , using a given visual 
     * @param visual the given visual .
     */
    public Player(String visual) {
        //Will later be using an enum for 4 players (at max)
        this.visual = visual;
    }

    /**
     * Getter for visual
     * @return the Player's visual .
     */
    public String getVisual() {
        return this.visual;
    }
}

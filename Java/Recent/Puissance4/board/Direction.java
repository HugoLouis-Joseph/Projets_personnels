package board;

public enum Direction {
    //Only four directions are needed , since we will go back and forth , which will represent the eight cardinal directions .
    N(-1,0),E(0,1),NE(-1,1),NW(-1,-1);

    private final int x;
    private final int y;
    private Direction(int x,int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for y
     * @return y
     */
    public int getY() {
        return this.y;
    }
}

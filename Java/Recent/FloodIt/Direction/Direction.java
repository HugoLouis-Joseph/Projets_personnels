package Direction;

/**
 * Enumeration of directions that are used to look for neighboring tiles , modifying the instances changes how the game behaves .
 */
public enum Direction {
    N(-1,0),E(0,1),S(1,0),W(0,-1);

    private final int lineCoef;
    private final int columnCoef;
    private Direction(int lineCoef,int columnCoef) {
        this.lineCoef = lineCoef;
        this.columnCoef = columnCoef;
    }

    public int getLineCoef() {
        return this.lineCoef;
    }

    public int getColumnCoef() {
        return this.columnCoef;
    }
}
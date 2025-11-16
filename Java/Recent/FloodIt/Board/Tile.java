package Board;

public class Tile {

    protected int lineI;
    protected int columnI;
    protected Color color;
    // State depicting if the tile was added to the board neighbors map attribute , also decreases costs (avoids : 'list contains tile' which goes through the list) in case the tile is a neighbor of multiple tiles present into the arraylist corresponding to the chosen color
    protected Boolean isTaken = false;

    public Tile(int lineI , int columnI , Color color) {
        this.lineI = lineI;
        this.columnI = columnI;
        this.color = color;
    }

    public int getLineI() {
        return this.lineI;
    }

    public int getColumnI() {
        return this.columnI;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void take() {
        this.isTaken = true;
    }

    public Boolean isTaken() {
        return this.isTaken;
    }

    public void display() {
        System.out.print(this.color.getTerminalVisual());
    }
}

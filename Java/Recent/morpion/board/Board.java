package board;

public class Board {

    public static int LENGTH = 10;
    public static int HEIGHT = 6;

    protected Tile tiles[][];

    /**
     * Creates a new Board object , and initializes its contents with new Tiles , which are associated to NULLPLAYER (no player) .
     */
    public Board() {
        this.tiles = new Tile[Board.HEIGHT][Board.LENGTH];

        for (int line = 0 ; line < Board.HEIGHT ; line++) {
            for (int column = 0 ; column < Board.LENGTH ; column++) {
                this.tiles[line][column] = new Tile();
            }
        }

    }

    /**
     * Displays the current board state into the terminal , using the players's visual , or a space if NULLPLAYER (no player) .
     */
    public void displayIntoTerminal() {

        //Draws the content of the board , separated by '|' to differentiate tiles .
        for (Tile[] line : this.tiles) {
            System.out.print("\n| ");
            for (Tile tile : line) {
                tile.displayIntoTerminal();
                System.out.print(" | ");
            }
        }

        //Draws the bottom of the board to have a better visual .
        System.out.println("");
        for (int i = 0 ; i < Board.LENGTH*4+1 ; i++) {
            System.out.print("^");
        }
    }
}

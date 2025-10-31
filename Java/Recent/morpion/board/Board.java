package board;

import player.Pawn;

public class Board {

    public static int LENGTH = 10;
    public static int HEIGHT = 6;

    protected Tile tiles[][];
    //Array of indexes where the first empty Tile of every column is at . Initially at HEIGHT-1 , which is the bottommost position .
    protected int lineIndex[];

    /**
     * Creates a new Board object , and initializes its contents with new Tiles , which are associated to NULLPLAYER (no player) .
     */
    public Board() {
        this.tiles = new Tile[Board.HEIGHT][Board.LENGTH];
        this.lineIndex = new int[Board.LENGTH];

        for (int column = 0 ; column < Board.LENGTH ; column++) {
            for (int line = 0 ; line < Board.HEIGHT ; line++) {
                this.tiles[line][column] = new Tile();
            }
            //Board.HEIGHT-1 is the last line of the Board
            lineIndex[column] = Board.HEIGHT-1;
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
        System.out.println("");
    }

    /**
     * Places a pawn at a given column
     * @param pawn the pawn to place
     * @param column the given column index
     * @return a boolean equal to true if the placement was done , and false otherwise , which means the given column is full .
     */
    public Boolean placePawn(Pawn pawn , int column) {
        int index = this.lineIndex[column]--;
        //If column is full (no more empty Tiles)
        if (index < 0) {
            return false;
        }
        //Putting the pawn at the last empty Tile of the column given , in the Board.
        this.tiles[index][column].setContent(pawn);
        return true;
    }
}

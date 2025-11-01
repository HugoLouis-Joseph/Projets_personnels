package board;

import player.Pawn;

public class Board {

    public static int LENGTH = 0;
    public static int HEIGHT = 0;

    protected Tile tiles[][];
    //Array of indexes where the first empty Tile of every column is at . Initially at HEIGHT-1 , which is the bottommost position .
    protected int lineIndex[];
    protected Boolean finished = false;

    /**
     * Creates a new Board object , and initializes its contents with new Tiles , which are associated to NULLPLAYER (no player) .
     */
    public Board(int length , int height) {
        Board.HEIGHT = height;
        Board.LENGTH = length;

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
        this.checkVictory(index,column);
        return true;
    }

    /**
     * Checks if the placed pawn ends the game or not , if yes , a flag is set up for marking .
     * @param line line index of the placed pawn
     * @param column column index of the placed pawn
     */
    public void checkVictory(int line , int column) {
        //Sum of same pawns encountered (init at 1 because self is counted)
        int sum;
        for (Direction dir : Direction.values()) {
            sum = 1;

            //Checking for pawns in the direction .
            int offsetLine = line + dir.getX();
            int offsetColumn = column + dir.getY();

            while (this.inBoard(offsetLine,offsetColumn) && this.tiles[offsetLine][offsetColumn].getContent() == this.tiles[line][column].getContent()) {
                offsetLine += dir.getX();
                offsetColumn += dir.getY();
                sum++;
            }

            //Now checking for pawns in the opposite direction .
            offsetLine = line - dir.getX();
            offsetColumn = column - dir.getY();

            while (this.inBoard(offsetLine,offsetColumn) && this.tiles[offsetLine][offsetColumn].getContent() == this.tiles[line][column].getContent()) {
                offsetLine -= dir.getX();
                offsetColumn -= dir.getY();
                sum++;
            }

            //If at least four pawns of the same Player are in line , said Player wins , we set up a flag to indicate game end .
            if (sum > 3) {
                this.finished = true;
                //Pointless to keep going .
                break;
            }
        }
    }

    /**
     * Checks if given coordinates are out of bounds or not
     * @param line line index
     * @param column column index
     * @return a Boolean equal to true if the coordinates are in the board
     */
    public Boolean inBoard(int line ,int column) {
        return (line > 0 && line < Board.HEIGHT) && (column > 0 && column < Board.LENGTH);
    }

    /**
     * Getter for the flag finished
     * @return the flag
     */
    public Boolean isFinished() {
        return this.finished;
    }
}

package Board;

import java.util.*;

import Direction.Direction;

public class Board {

    public static int LENGTH;
    public static int HEIGHT;
    protected Tile tiles[][];

    // Game ends if numberOfSteps equals MAXSTEPS and listSize is not equal to LENGTH*HEIGHT
    protected static int MAXSTEPS = 8;
    protected int numberOfSteps = 0;

    // Current colored tiles
    protected ArrayList<Tile> coloredTiles;
    // Size of the above list , to reduce costs regarding termination condition (no need to go through the list to get its size every iteration)
    protected int listSize = 0;
    // Map of lists of neighboring tiles separated by color , when a color is chosen , the corresponding list is emptied into coloredTiles , the size of the list is added to listSize , and new neighboring tiles are then added into the map
    protected HashMap<Color,ArrayList<Tile>> neighbors;

    public Board(int length , int height , int tries) {
        Board.LENGTH = length;
        Board.HEIGHT = height;
        Board.MAXSTEPS = tries;

        this.coloredTiles = new ArrayList<Tile>();
        this.neighbors = new HashMap<Color,ArrayList<Tile>>();
        // Init of the map so as not to have nullPointerException
        for (Color col : Color.values()) {
            this.neighbors.put(col, new ArrayList<Tile>());
        }

        // Init of the board tiles
        this.tiles = new Tile[Board.LENGTH][Board.HEIGHT];
        for (int lineI = 0 ; lineI < Board.HEIGHT ; lineI++) {
            for (int columnI = 0 ; columnI < Board.LENGTH ; columnI++) {
                // Every tile has a random color
                this.tiles[lineI][columnI] = new Tile(lineI,columnI,Color.randomColor());
            }
        }

        this.recursiveAdd(this.tiles[0][0],this.tiles[0][0].getColor());
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Recursively adds tiles as long as their color is equal to origin , if it isn't , the tiles are instead added into the map for later selection
     * @param observed
     * @param origin color of the first observed tile
     */
    public void recursiveAdd(Tile observed, Color origin) {
        // Set the flag to true (if removed , the same tile can be added multiple times , which breaks the winState method)
        observed.take();
        this.addToList(observed);
        // Checking the color of the neighbors (in case their flag is set to false)
        for (Tile neighbor : this.getNeighbors(observed)) {
            if (!neighbor.isTaken()) {
                if (neighbor.getColor().equals(origin)) {
                    this.recursiveAdd(neighbor, origin);
                }
                else {
                    neighbor.take();
                    this.addToMap(neighbor);
                }
            }
        }
    }

    /**
     * Adds a tile to the list of current "owned" tiles and increments listSize
     * @param tile the tile to add
     */
    public void addToList(Tile tile) {
        this.coloredTiles.add(tile);
        this.listSize++;
    }

    /**
     * Adds a tile to the map of choices
     * @param tile tile to add
     */
    public void addToMap(Tile tile) {
        Color color = tile.getColor();
        this.neighbors.get(color).add(tile);
    }

    /**
     * Plays one step , which increments numberOfSteps , takes a color input , and adds the corresponding tiles contained in the list of the map with the given color as key , using recursiveAdd , and finally changing the color of tiles in coloredTiles
     */
    public void playOneStep() {
        Color color = Color.input();
        this.numberOfSteps++;
        // Adding the tiles in the list to coloredTiles , and potentially adding their neighbors too to either coloredTiles or to neighbors
        for (Tile tile : this.neighbors.get(color)) {
            this.recursiveAdd(tile, color);
        }
        this.neighbors.get(color).clear();
        this.changeColor(color);
    }

    /**
     * Plays one step , which increments numberOfSteps , takes a color input , and adds the corresponding tiles contained in the list of the map with the given color as key , using recursiveAdd , and finally changing the color of tiles in coloredTiles
     */
    public void playOneSwingStep(Color color) {
        this.numberOfSteps++;
        // Adding the tiles in the list to coloredTiles , and potentially adding their neighbors too to either coloredTiles or to neighbors
        for (Tile tile : this.neighbors.get(color)) {
            this.recursiveAdd(tile, color);
        }
        this.neighbors.get(color).clear();
        this.changeColor(color);
    }

    /**
     * Changes the color of the tiles in coloredTiles into the one given
     * @param color the color used
     */
    public void changeColor(Color color) {
        for (Tile tile : this.coloredTiles) {
            tile.setColor(color);
        }
    }

    /**
     * Checks for the direct neighbors of a tile and adds them to a list that will be returned
     * @param tile tile to start from
     * @return the list of the tile's neighbors
     */
    public ArrayList<Tile> getNeighbors(Tile tile) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        int tileLineI = tile.getLineI();
        int tileColumnI = tile.getColumnI();

        for (Direction dir : Direction.values()) {
            int dirLineCoef = dir.getLineCoef();
            int dirColumnCoef = dir.getColumnCoef();

            // Checks if coordinates out of bounds
            boolean notOutOfBounds = (tileLineI + dirLineCoef >= 0 && tileLineI + dirLineCoef < Board.LENGTH) && (tileColumnI + dirColumnCoef >= 0 && tileColumnI + dirColumnCoef < Board.HEIGHT);
            if (notOutOfBounds) {
                tiles.add(this.tiles[tileLineI+dirLineCoef][tileColumnI+dirColumnCoef]);
            }
        }
        return tiles;
    }

    /**
     * @return a value corresponding to the game's state . -1 -> lost | 0 -> not over | 1 -> won
     */
    public int winState() {
        // Game is won -> all the tiles of the board are present in listSize
        if (this.listSize == Board.LENGTH*Board.HEIGHT) {
            return 1;
        }
        // Game is lost -> maxsteps reached , and not every tile of the board is present in listSize
        if (this.numberOfSteps == Board.MAXSTEPS) {
            return -1;
        }
        // Not over
        return 0;
    }

    /**
     * Method to display the board into the terminal
     */
    public void display() {
        System.out.println("Step " + this.numberOfSteps + " out of " + Board.MAXSTEPS);
        for (int lineI = 0 ; lineI < Board.HEIGHT ; lineI++) {
            for (int columnI = 0 ; columnI < Board.LENGTH ; columnI++) {
                System.out.print("|");
                this.tiles[lineI][columnI].display();
            }
            System.out.println("|");
        }
    }
}
// Package //
package game;
// ------- //

// Imports //
import board.Board;
import board.Tile;
import io.Input;

import java.util.*;

import actions.*;
import ship.*;
// ------- //

/**
 * Public class for Game
 */
public class Game {
    protected Board[] boards;
    protected int length;
    protected int width;

    /**
     * Creates a new Game
     * @param length lenght of the board
     * @param width width of the boards
     */
    public Game(int length , int width) {
        this.boards = new Board[2];
        this.boards[0] = new Board(length, width);
        this.boards[1] = new Board(length, width);

        this.length = length;
        this.width = width;
    }

    /**
     * Places a ship at random
     * @param board the board where the ship is placed
     */
    public void placeRandom(int board) {
        for (int ind = 0 ; ind < 4 ; ind++) {
            HashMap<Orientation,ArrayList<int[]>> map = this.possibleRandomPlacements(board, ind,1);
            Orientation ori = Orientation.random();
            ArrayList<int[]> list = map.get(ori);
            int cpl[] = list.get(Orientation.ALEA.nextInt(list.size()));
            String letter = PlaceShip.LETTERS[ind];
            Ship ship = new Ship(3,1, ori , cpl , letter , "Bateau");
            this.boards[board].placeShip(cpl, ship , true);
        }

        HashMap<Orientation,ArrayList<int[]>> map = this.possibleRandomPlacements(board, 3,1);
        Orientation ori = Orientation.random();
        ArrayList<int[]> list = map.get(ori);
        int cpl[] = list.get(Orientation.ALEA.nextInt(list.size()));
        Submarine sub = new Submarine(3, ori , cpl);
        this.boards[board].placeShip(cpl, sub , true);
        this.boards[board].addSub(sub);

        HashMap<Orientation,ArrayList<int[]>> map2 = this.possibleRandomPlacements(board, 3,1);
        Orientation ori2 = Orientation.random();
        ArrayList<int[]> list2 = map2.get(ori2);
        int cpl2[] = list2.get(Orientation.ALEA.nextInt(list2.size()));
        Jammer jam = new Jammer(2, ori2 , cpl2);
        this.boards[board].placeShip(cpl2, jam , true);
        this.boards[board].addJammer(jam);

        HashMap<Orientation,ArrayList<int[]>> map3 = this.possibleRandomPlacements(board, 3,2);
        Orientation ori3 = Orientation.random();
        ArrayList<int[]> list3 = map3.get(ori3);
        int cpl3[] = list3.get(Orientation.ALEA.nextInt(list3.size()));
        CarrierShip car = new CarrierShip(3,2, ori3 , cpl3);
        this.boards[board].placeShip(cpl3, car , false);
        this.boards[board].addCarrier(car);

        HashMap<Orientation,ArrayList<int[]>> map4 = this.possibleRandomPlacements(board, 3,1);
        Orientation ori4 = Orientation.random();
        ArrayList<int[]> list4 = map4.get(ori4);
        int cpl4[] = list4.get(Orientation.ALEA.nextInt(list4.size()));
        FlakShip flak = new FlakShip(2, ori4 , cpl4);
        this.boards[board].placeShip(cpl4, flak , true);
        this.boards[board].addFlak(flak);
    }

    /**
     * Returns a HashMap containing possible placements for every direction , in case the placement of ships is randomized 
     * @param board the board to check
     * @param ind size index in PlaceShip
     * @return a HashMap
     */
    public HashMap<Orientation,ArrayList<int[]>> possibleRandomPlacements(int board ,int ind , int sizeY) {
        HashMap<Orientation,ArrayList<int[]>> map = new HashMap<Orientation,ArrayList<int[]>>();
        for (Orientation ori : Orientation.values()) {
            map.put(ori, new ArrayList<int[]>());
            for (int i = 0 ; i < this.length ; i++) {
                for (int j = 0 ; j < this.width ; j++) {
                    int cpl[] = new int[2];
                    cpl[0] = i;
                    cpl[1] = j;
                    if (!this.boards[board].outOfBounds(cpl, ori, 3,sizeY) && !this.boards[board].shipCollision(cpl, ori, 3,sizeY)) {
                        map.get(ori).add(cpl);
                    }
                }
            }
        }
        return map;
    }

    /**
     * Getter for first board
     * @return first board
     */
    public Board getBoard1() {
        return this.boards[0];
    }

    /**
     * Getter for second board
     * @return second board
     */
    public Board getBoard2() {
        return this.boards[1];
    }

    /**
     * Displays the boards
     * @param player player that plays , the display used is different
     */
    public void display(int player) {

        String letters[] = {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

        System.out.print(" ");

        for (int column = 0 ; column < this.width ; column++) {
            System.out.print(" " + letters[column] + " ");
        }
        System.out.print("|  ");
        for (int column = 0 ; column < this.width ; column++) {
            System.out.print(" " + letters[column] + " ");
        }
        System.out.println("");

        int lineNumber = -1;
        for (int line = 0 ; line < this.length ; line++) {
            System.out.print(letters[++lineNumber]);

            //Prints the corresponding line of the first board
            for (int column = 0 ; column < this.width ; column++) {
                Tile tile = this.boards[0].getBoard()[line][column];
                if (player == 1) {
                    tile.displayHidden();
                }
                else {
                    tile.display();
                }
            }
            System.out.print("| " + letters[lineNumber]);

            //Prints the corresponding line of the second board
            for (int column = 0 ; column < this.width ; column++) {
                Tile tile = this.boards[1].getBoard()[line][column];
                if (player == 0) {
                    tile.displayHidden();
                }
                else {
                    tile.display();
                }
            }
            System.out.println("");
        }
    }

    /**
     * Checks if the game is Over or not , which is when one side has a game over
     * @return boolean
     */
    public boolean gameOver() {
        boolean over = false;
        for (Board board : this.boards) {
            over = over || board.gameOver();
        }
        return over;
    }

    /**
     * 
     * @param possibleActions
     * @return
     */
    public Action choose(ArrayList<Action> possibleActions) {
        int number = 1;
        System.out.print("\nActions available : ");
        for (Action act : possibleActions) {
            System.out.print("[ " + (number++) + " : " + act.getName() + " ]");
        }
        System.out.println("");

        int index = 0;
        while (index <= 0 || index > possibleActions.size()) {
            try {
                index = Input.readInt();
            }
            catch (java.io.IOException e) {
                System.out.println("Enter a number");
            }
        }
        return possibleActions.get(index-1);
    }
}

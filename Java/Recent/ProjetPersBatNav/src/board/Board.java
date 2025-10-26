// Package //
package board;
// ------- //

// Imports //
import java.util.*;
import ship.*;
// ------- //

/**
 * Public class for Board
 */
public class Board {
    protected Tile[][] board;
    protected ArrayList<Ship> ships;
    protected ArrayList<Plane> planes;
    protected Submarine sub = null;
    protected Jammer jam = null;
    protected CarrierShip car = null;
    protected FlakShip flak = null;
    protected int length;
    protected int width;

    /**
     * Creates a new Board object
     * @param length length of board
     * @param width width of board
     */
    public Board(int length , int width) {
        this.ships = new ArrayList<Ship>();
        this.planes = new ArrayList<Plane>();
        this.length = length;
        this.width = width;
        this.board = new Tile[length][width];
        
        for (int i = 0 ; i < this.length ; i++) {
            for (int j = 0 ; j < this.width ; j++) {
                this.board[i][j] = new Tile(j*40,i*40);
            }
        }
    }

    /**
     * Getter for board
     * @return board
     */
    public Tile[][] getBoard() {
        return this.board;
    }

    public void movePlanes(Board enemy) {
        ArrayList<Plane> planesToRemove = new ArrayList<Plane>();
        for (Plane plane : this.planes) {
            int x = plane.getCpl()[0]+plane.getOrientation().getX();
            int y = plane.getCpl()[1]+plane.getOrientation().getY();
            if (x < 0 || x >= this.length || y < 0 || y >= this.width) {
                planesToRemove.add(plane);
                this.board[plane.getCpl()[0]][plane.getCpl()[1]].resetAirContent();
                enemy.getCarrier().addPlane();
            }
            else {
                this.board[plane.getCpl()[0]][plane.getCpl()[1]].resetAirContent();
                this.board[x][y].setAirContent(plane);
                plane.setCpl(x,y);
            }

            if (this.board[plane.getCpl()[0]][plane.getCpl()[1]].getPart() != null) {
                plane.setLetter("\u001B[31m p \u001B[0m");
            } else {
                plane.setLetter(" p ");
            }
        }

        

        for (Plane plane : planesToRemove) {
            this.planes.remove(plane);
        }
    }

    public void addPlane(Plane plane) {
        this.planes.add(plane);
        this.board[plane.getCpl()[0]][plane.getCpl()[1]].setAirContent(plane);
        if (this.board[plane.getCpl()[0]][plane.getCpl()[1]].getPart() != null) {
            plane.setLetter("\u001B[31m p \u001B[0m");
        }
    }

    public void removePlane(Plane plane) {
        this.planes.remove(plane);
    }

    /**
     * Getter for length
     * @return length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Getter for width
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Adds a submarine
     * @param sub a submarine
     */
    public void addSub(Submarine sub) {
        this.sub = sub;
    }

    /**
     * Getter for sub
     * @return sub
     */
    public Submarine getSub() {
        return this.sub;
    }

    /**
     * Places a ship at the given coordinates
     * @param cpl a couple of coordinates
     * @param ship the ship to place
     */
    public void placeShip(int cpl[],Ship ship,boolean add) {
        if (add) {
            this.ships.add(ship);
        }
        
        // Places every part of the ship on the board
        for (int y = 0 ; y < ship.getSizeY() ; y++) {
            for (int i = 0 ; i < ship.getSizeX() ; i++) {
                int coefX = i*ship.getOrientation().getX();
                int coefY = i*ship.getOrientation().getY();
                this.board[cpl[0]+coefX+y*ship.getOrientation().getY()][cpl[1]+coefY+y*ship.getOrientation().getX()].setContent(ship.getHull()[i][y]);
            }
        }
    }

    /**
     * Returns true if the ship would be out of bounds
     * @param cpl a couple of coordinates
     * @param ori an orientation
     * @param size the size of the ship
     * @return a boolean equal to true if the ship would be out of bounds
     */
    public boolean outOfBounds(int cpl[] , Orientation ori , int size,int sizeY) { 
        return ((cpl[0] < 0 || cpl[0] >= this.length) || (cpl[0]+(size-1)*ori.getX() < 0 || cpl[0]+(size-1)*ori.getX() >= this.length))
                ||
               ((cpl[1] < 0 || cpl[1] >= this.width) || (cpl[1]+(size-1)*ori.getY() < 0 || cpl[1]+(size-1)*ori.getY() >= this.width))
                ||
               ((cpl[0]+(sizeY-1)*ori.getY() < 0 || cpl[0]+(sizeY-1)*ori.getY() >= this.length) || (cpl[0]+(size-1)*ori.getX()+(sizeY-1)*ori.getX() < 0 || cpl[0]+(size-1)*ori.getX()+(sizeY-1)*ori.getX() >= this.length))
                ||
               ((cpl[1]+(sizeY-1)*ori.getY() < 0 || cpl[1]+(sizeY-1)*ori.getY() >= this.width) || (cpl[1]+(size-1)*ori.getY()+(sizeY-1)*ori.getX() < 0 || cpl[1]+(size-1)*ori.getY()+(sizeY-1)*ori.getX() >= this.width));
    }

    /**
     * Returns true if a collision with another ship is detected
     * @param cpl a couple of coordinates
     * @param ori an orientation
     * @param size size of the ship
     * @return a boolean equal to true if a collision with another ship is detected
     */
    public boolean shipCollision(int cpl[] , Orientation ori , int size , int sizeY) {
        boolean ok = false;
        for (int y = 0 ; y < sizeY ; y++) {
            for (int i = 0 ; i < size && !ok ; i++) {
                ok = ok || this.board[cpl[0]+i*ori.getX()+y*ori.getY()][cpl[1]+i*ori.getY()+y*ori.getX()].getPart() != null;
            }
        }
        return ok;
    }

    /**
     * Checks if the game is over , that is , if every ship is destroyed
     * @return a boolean equal to true if the game is over
     */
    public boolean gameOver() {
        boolean over = true;
        for (Ship ship : this.ships) {
            over = over && (ship.isDestroyed() || (ship instanceof Submarine));
        }
        return over && (this.sub.isDestroyed() || (this.sub.isSubmerged() && !this.subCanUnSubmerge()));
    }

    /**
     * Identifies the ships that can move (backward and/or forward)
     * @return an HashMap that contains every ship that can move as keys and their possible movements as values
     */
    public HashMap<Ship,ArrayList<String>> shipsThatCanMove() {
        HashMap<Ship,ArrayList<String>> map = new HashMap<Ship,ArrayList<String>>();

        for (Ship ship : this.ships) {

            if (!ship.isHit()) {

                int x1 = ship.getCpl()[0] - ship.getOrientation().getX();
                int y1 = ship.getCpl()[1] - ship.getOrientation().getY();

                if (x1 >= 0 && x1 < this.length && y1 >= 0 && y1 < this.width) {
                    if ((this.board[x1][ship.getCpl()[1]].content == null && !this.board[x1][ship.getCpl()[1]].isHit()) || (this.board[ship.getCpl()[0]][y1].content == null && !this.board[ship.getCpl()[0]][y1].isHit())) {
                        if (!map.containsKey(ship)) {
                            map.put(ship,new ArrayList<String>());
                        }
                        map.get(ship).add("backward");
                    }
                }

                int x2 = ship.getCpl()[0] + ship.getOrientation().getX() * ship.getSizeX();
                int y2 = ship.getCpl()[1] + ship.getOrientation().getY() * ship.getSizeX();

                if (x2 >= 0 && x2 < this.length && y2 >= 0 && y2 < this.width) {
                    if ((this.board[x2][ship.getCpl()[1]].content == null && !this.board[x2][ship.getCpl()[1]].isHit()) || (this.board[ship.getCpl()[0]][y2].content == null && !this.board[ship.getCpl()[0]][y2].isHit())) {
                        if (!map.containsKey(ship)) {
                            map.put(ship,new ArrayList<String>());
                        }
                        map.get(ship).add("forward");
                    }
                }
            }
        }

        return map;
    }

    /**
     * Moves the given ship with the type of movement
     * @param ship given ship
     * @param type type of movement
     */
    public void move(Ship ship , String type) {
        if (type == "forward") {
            int cpl[] = ship.getCpl();
            cpl[0] += ship.getOrientation().getX();
            cpl[1] += ship.getOrientation().getY();
            for (int i = ship.getSizeX()-1 ; i > -1 ; i--) {
                ship.getHull()[i][0].changeCpl(cpl);
                this.board[cpl[0]+i*ship.getOrientation().getX()][cpl[1]+i*ship.getOrientation().getY()].setContent(ship.getHull()[i][0]);
            }
            this.board[cpl[0]-ship.getOrientation().getX()][cpl[1]-ship.getOrientation().getY()].resetContent();
        }
        else {
            int cpl[] = ship.getCpl();
            cpl[0] -= ship.getOrientation().getX();
            cpl[1] -= ship.getOrientation().getY();
            for (int i = 0 ; i < ship.getSizeX() ; i++) {
                ship.getHull()[i][0].changeCpl(cpl);
                this.board[cpl[0]+i*ship.getOrientation().getX()][cpl[1]+i*ship.getOrientation().getY()].setContent(ship.getHull()[i][0]);
            }
            this.board[cpl[0]+ship.getSizeX()*ship.getOrientation().getX()][cpl[1]+ship.getSizeX()*ship.getOrientation().getY()].resetContent();
        }
    }

    /**
     * Checks which ships can rotate and if they can , the type of rotation
     * @return a map containing ships that can rotate associated with the directions in which they can rotate
     */
    public HashMap<Ship,ArrayList<Orientation>> shipsThatCanRotate() {
        HashMap<Ship,ArrayList<Orientation>> map = new HashMap<Ship,ArrayList<Orientation>>();
        for (Ship ship : this.ships) {
            if (!ship.isHit()) {
                 for (Orientation ori : Orientation.rotation(ship.getOrientation())) {
                    boolean ok = !outOfBounds(ship.getCpl(), ori, ship.getSizeX(),ship.getSizeY());
                    for (int i = 1 ; i < ship.getSizeX() && ok ; i++) {
                        ok = ok && this.board[ship.getCpl()[0] + ori.getX()*i][ship.getCpl()[1] + ori.getY()*i].content == null && !this.board[ship.getCpl()[0] + ori.getX()*i][ship.getCpl()[1] + ori.getY()*i].isHit();
                    }
                    if (ok) {
                        if (!map.containsKey(ship)) {
                                map.put(ship, new ArrayList<Orientation>());
                        }
                        map.get(ship).add(ori);
                    }
                }
            }
        }

        return map;
    }

    /**
     * Rotates the given ship with the type direction
     * @param ship a ship
     * @param type a direction
     */
    public void rotate(Ship ship , Orientation type) {
        for (int i = 1 ; i < ship.getSizeX() ; i++) {
            this.board[ship.getCpl()[0]+ship.getOrientation().getX()*i][ship.getCpl()[1]+ship.getOrientation().getY()*i].resetContent();
        }
        ship.setOrientation(type);
        for (int i = 1 ; i < ship.getSizeX() ; i++) {
            this.board[ship.getCpl()[0]+ship.getOrientation().getX()*i][ship.getCpl()[1]+ship.getOrientation().getY()*i].setContent(ship.getHull()[i][0]);
        }
        ship.getHull()[ship.getSizeX()-1][0].changeLetter(Orientation.ARROWS[ship.getOrientation().ordinal()]);
    }

    /**
     * Changes the status of the submarine if it and no tile above it has been hit
     */
    public void changeSubStatus() {
        boolean ok = !this.sub.isHit();
        for (int i = 0 ; i < 3 ; i++) {
            ok = ok && !this.board[this.sub.getCpl()[0]+this.sub.getOrientation().getX()*i][this.sub.getCpl()[1]+this.sub.getOrientation().getY()*i].isHit();
        }
        if (ok) {
            this.sub.changeStatus();
        }
    }

    /**
     * Checks in a diagonal grid if a spot of size 3 is available
     * @return boolean
     */
    public boolean subCanUnSubmerge() {
        int line = 0;
        boolean available = false;
        for (int column = 2 ; column < this.length + this.width && !available ; column = column + 3 ) {
            for (int step = 0 ; step < column+1 ; step++) {
                if (column-step < this.width && line+step < this.length) {
                    available = available || !this.board[line+step][column-step].isHit();
                }
            }
        }
        return available;
    }

    /**
     * adds a jammer
     * @param jam jammer
     */
    public void addJammer(Jammer jam) {
        this.jam = jam;
    }

    /**
     * getter for jammer
     * @return jammer
     */
    public Jammer getJammer() {
        return this.jam;
    }

    /**
     * adds a jammer
     * @param jam jammer
     */
    public void addCarrier(CarrierShip car) {
        this.car = car;
    }

    /**
     * getter for jammer
     * @return jammer
     */
    public CarrierShip getCarrier() {
        return this.car;
    }

    /**
     * adds a FlakShip
     * @param flak FlakShip
     */
    public void addFlak(FlakShip flak) {
        this.flak = flak;
    }

    /**
     * getter for FlakShip
     * @return FlakShip
     */
    public FlakShip getFlak() {
        return this.flak;
    }
}

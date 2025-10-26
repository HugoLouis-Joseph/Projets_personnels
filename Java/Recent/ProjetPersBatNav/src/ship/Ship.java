// Package //
package ship;
// ------- //

/**
 * Public class for Ship
 */
public class Ship {
    protected boolean submerged = false;
    protected int sizeX;
    protected int sizeY;
    protected Part hull[][];
    protected Orientation orientation;
    protected String letter;
    protected boolean isHit;

    /**
     * Creates a new Ship object
     * @param size number of parts
     * @param orientation orientation of ship
     */
    public Ship(int size,int sizeY, Orientation orientation , int[] cpl , String letter , String image) {
        this.isHit = false;
        this.sizeX = size;
        this.sizeY = sizeY;
        this.orientation = orientation;
        this.hull = new Part[size][sizeY];
        this.letter = letter;
        for (int j = 0 ; j < sizeY ; j++) {
            for (int i = 0 ; i < size ; i++) {
                this.hull[i][j] = new Part(this,cpl,letter,(image + "/" + (i+1) + (j+1)));
            }
        }
        this.hull[size-1][0].changeLetter(Orientation.ARROWS[orientation.ordinal()]);
        this.hull[size-1][sizeY-1].changeLetter(Orientation.ARROWS[orientation.ordinal()]);
    }

    /**
     * Returns true if ship is destroyed
     * @return true if destroyed , which mean every part of the ship was hit
     */
    public boolean isDestroyed() {
        boolean isDestroyed = true;
        for (Part parts[] : this.hull) {
            for (Part part : parts) {
                isDestroyed = isDestroyed && part.isHit();
            }
        }
        return isDestroyed;
    }
    
    /**
     * Getter for isHit
     * @return true if at least one part is hit
     */
    public boolean isHit() {
        return this.isHit;
    }

    /**
     * Sets isHit to true
     */
    public void hit() {
        this.isHit = true;
    }

    /**
     * Getter for size
     * @return size
     */
    public int getSizeX() {
        return this.sizeX;
    }

    /**
     * Getter for size
     * @return size
     */
    public int getSizeY() {
        return this.sizeY;
    }

    public int[] getCpl() {
        return this.hull[0][0].cpl;
    }

    /**
     * Getter for orientation
     * @return orientation
     */
    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setOrientation(Orientation ori) {
        this.orientation = ori;
    }

    /**
     * Getter for hull
     * @return hull
     */
    public Part[][] getHull() {
        return this.hull;
    }

    public boolean isSubmerged() {
        return this.submerged;
    }
}

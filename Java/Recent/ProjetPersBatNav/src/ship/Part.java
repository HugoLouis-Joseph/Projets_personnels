// Package //
package ship;
// ------- //

/**
 * Public class for Part
 */
public class Part {
    protected boolean isHit = false;
    protected Ship ship;
    protected int cpl[];
    protected String letter;
    protected String image;

    /**
     * Creates a new Part object
     */
    public Part(Ship ship , int cpl[] , String letter , String image) {
        this.ship = ship;
        this.cpl = cpl;
        this.letter = letter;
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    /**
     * Getter for isHit
     * @return isHit
     */
    public boolean isHit() {
        return this.isHit;
    }

    /**
     * Hits the part
     */
    public void hit() {
        this.isHit = true;
        this.ship.hit();
    }

    /**
     * Getter for ship
     * @return parent ship
     */
    public Ship getShip() {
        return this.ship;
    }

    public String getLetter() {
        return this.letter;
    } 
    
    public void changeLetter(String letter) {
        this.letter = letter;
    }

    public void changeCpl(int cpl[]) {
        this.cpl = cpl;
    }
}

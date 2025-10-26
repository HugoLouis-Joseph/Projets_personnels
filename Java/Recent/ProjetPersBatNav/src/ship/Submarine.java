// Package //
package ship;
// ------- //

/**
 * Public class for Submarine
 */
public class Submarine extends Ship {
    protected String letters[] = {" s "," S "};
    protected int letterIndex = 0;

    /**
     * Creates a new Submarine
     * @param size size
     * @param orientation orientation
     * @param cpl coords
     */
    public Submarine(int size, Orientation orientation , int[] cpl) {
        super(size,1, orientation, cpl, " S ","SousMarin");
    }

    /**
     * Changes status of the submarine , which makes it submerged if not , and vice versa
     */
    public void changeStatus() {
        this.submerged = !this.submerged;
        for (int i = 0 ; i < 2 ; i++) {
            this.hull[i][0].changeLetter(this.letters[letterIndex%2]);
        }
        letterIndex++;
    }

    public String getStatus() {
        return this.letters[this.letterIndex];
    }
}

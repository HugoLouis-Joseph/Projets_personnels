// Package //
package ship;
// ------- //

/**
 * Public class for Jammer
 */
public class Jammer extends Ship {

    /**
     * Creates a new Jammership
     * @param size size
     * @param orientation orientation
     * @param cpl coords
     */
    public Jammer(int size, Orientation orientation , int[] cpl) {
        super(size,1, orientation, cpl, " J ","Brouilleur");
    }
}

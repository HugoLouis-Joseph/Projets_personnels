// Package //
package ship;
// ------- //

/**
 * Public class for Jammer
 */
public class FlakShip extends Ship {

    /**
     * Creates a new Jammership
     * @param size size
     * @param orientation orientation
     * @param cpl coords
     */
    public FlakShip(int size, Orientation orientation , int[] cpl) {
        super(size,1, orientation, cpl, " F ","AntiAerien");
    }
}

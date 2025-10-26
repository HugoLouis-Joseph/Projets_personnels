// Package //
package ship;
// ------- //

/**
 * Public class for Jammer
 */
public class CarrierShip extends Ship {
    protected int planeCount = 3;
    /**
     * Creates a new CarrierShip
     * @param sizeX length
     * @param sizeY width
     * @param orientation orientation
     * @param cpl coords
     */
    public CarrierShip(int sizeX , int sizeY, Orientation orientation , int[] cpl) {
        super(sizeX , sizeY, orientation, cpl, " Z ","PorteAvions");
    }

    public int getPlaneCount() {
        return this.planeCount;
    }

    public void addPlane() {
        this.planeCount++;
    }

    public void removePlane() {
        this.planeCount--;
    }
}

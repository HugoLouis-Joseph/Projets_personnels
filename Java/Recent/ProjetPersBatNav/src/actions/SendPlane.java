// Package //
package actions;
// ------- //

// Imports //
import board.*;
import ship.*;
import java.util.Random;
import javax.swing.*;
// ------- //

public class SendPlane extends Action {
    public static final Random ALEA = new Random();
    protected Board target;

    public SendPlane(Board board , Board target) {
        super("SendPlane",board);
        this.target = target;
    }

    public void act() {
        Orientation ori = Orientation.random();
        int[] cpl = Orientation.cpl(ori , this.board);
        Plane plane = new Plane(ori,cpl);
        this.board.getCarrier().removePlane();
        this.target.addPlane(plane);
    }

    public void act(JFrame m , JFrame f) {
        Orientation ori = Orientation.random();
        int[] cpl = Orientation.cpl(ori , this.board);
        Plane plane = new Plane(ori,cpl);
        this.board.getCarrier().removePlane();
        this.target.addPlane(plane);
    }

    public boolean canAct() {
        return board.getCarrier().getPlaneCount() > 0;
    }
    
}

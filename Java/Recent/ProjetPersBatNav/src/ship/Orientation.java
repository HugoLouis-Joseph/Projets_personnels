// Package //
package ship;
// ------- //

// Imports //
import io.*;
import java.util.*;
import board.Board;
// ------- //

/**
 * Public enum for Orientation
 */
public enum Orientation {
    NORTH(-1,0),EAST(0,1),SOUTH(1,0),WEST(0,-1);
    public static final Random ALEA = new Random();
    public static String ARROWS[] = {" ^ "," > "," v "," < "};
    public static String LETTERS[] = {"N","E","S","W"};

    private final int x;
    private final int y;
    private Orientation(int x,int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for y
     * @return y
     */
    public int getY() {
        return this.y;
    }

    public String getLetter() {
        return Orientation.LETTERS[this.ordinal()];
    }

    /**
     * Chooses an orientation
     * @return the chosen orientation
     */
    public static Orientation choose() {
        Orientation or = null;
        int ind = 0;
        boolean nok = true;

        for (Orientation ori : Orientation.values()) {
            System.out.print(" [ " + (ind++) + " : " + ori + " ] ");
        }

        System.out.println("");
        while (nok) {
            System.out.print("Choose an orientation : ");
            try {
                int x = Input.readInt();
                if (x < 0 || x >= ind) {
                    System.out.println("Invalid orientation");
                } else {
                    nok = false;
                    or = Orientation.values()[x];
                }
            } catch (java.io.IOException e) {
                System.out.println("Enter an integer");
            }
        }

        System.out.println("");
        return or;
    }

    /**
     * Returns a random orientation from the enumeration
     * @return a random orientation from the enumeration
     */
    public static Orientation random() {
        return Orientation.values()[ALEA.nextInt(Orientation.values().length)];
    }

    public static ArrayList<Orientation> rotation(Orientation ori) {
        ArrayList<Orientation> oris = new ArrayList<Orientation>();
        if (ori == NORTH || ori == SOUTH) {
            oris.add(EAST);
            oris.add(WEST);
        }
        else {
            oris.add(NORTH);
            oris.add(SOUTH);
        }
        return oris;
    }

    public static int[] cpl(Orientation ori , Board board) {
        int[] cpl = new int[2];

        if (ori == SOUTH) {
            cpl[0] = 0;

            cpl[1] = ALEA.nextInt(board.getWidth());
        }
        if (ori == NORTH) {
            cpl[0] = board.getLength()-1;

            cpl[1] = ALEA.nextInt(board.getWidth());
        }
        if (ori == EAST) {
            cpl[1] = 0;

            cpl[0] = ALEA.nextInt(board.getLength());
        }
        if (ori == WEST) {
            cpl[1] = board.getWidth()-1;

            cpl[0] = ALEA.nextInt(board.getLength());
        }

        return cpl;
    }
}

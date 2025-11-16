package Board;

import java.util.Random;
import io.*;

public enum Color {
    BLUE(" b " , java.awt.Color.BLUE),RED(" r ",java.awt.Color.RED),GREEN(" g ",java.awt.Color.GREEN),YELLOW(" y ",java.awt.Color.YELLOW),CYAN(" c ",java.awt.Color.CYAN),MAGENTA(" m ",java.awt.Color.MAGENTA);

    // Visual representation for the terminal
    protected String terminalVisual;
    protected java.awt.Color color;
    // Access to random
    protected static Random ALEA = new Random();

    private Color(String visual , java.awt.Color color) {
        this.terminalVisual = visual;
        this.color = color;
    }

    public String getTerminalVisual() {
        return this.terminalVisual;
    }

    public java.awt.Color getSwingColor() {
        return this.color;
    }

    /**
     * @return a random color contained in the enum
     */
    public static Color randomColor() {
        return Color.values()[Color.ALEA.nextInt(Color.values().length)];
    }

    /**
     * @return the color at the given index
     */
    public static Color input() {
        boolean nOk = true;
        int ind = 0;

        // Displaying the color choices , associated with their index
        for (Color col : Color.values()) {
            System.out.print("[" + col + " : " + (ind++) + "] ");
        }

        System.out.println("");

        // While the input is incorrect , retry
        while (nOk) {
            System.out.print("Enter a value between 0 and " + (Color.values().length-1) + " : ");
            try {
                ind = Input.readInt();
                if (ind < 0 || ind >= Color.values().length) {
                    System.out.println("Invalid value");
                }
                else {
                    nOk = false;
                }
            } catch (Exception e) {
                System.out.println("Invalid entry");
            }
        }
        return Color.values()[ind];
    }
}

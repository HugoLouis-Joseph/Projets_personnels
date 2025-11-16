package Board;

import java.util.Random;
import io.*;

public enum Color {
    BLUE(" b "),RED(" r "),GREEN(" g "),YELLOW("y"),CYAN("c"),MAGENTA("m");

    // Visual representation for the terminal
    protected String terminalVisual;
    // Access to random
    protected static Random ALEA = new Random();

    private Color(String visual) {
        this.terminalVisual = visual;
    }

    public String getTerminalVisual() {
        return this.terminalVisual;
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

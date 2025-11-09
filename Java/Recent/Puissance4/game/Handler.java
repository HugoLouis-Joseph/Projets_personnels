package game;

public class Handler {
    protected int value = -1;
    /**
     * Creates a new Handler object which is used to listen to button use
     */
    public Handler() {
    }
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    } 
}
package jeu;
public class Handler {
    protected int x = -1;
    public Handler() {
    }

    public void setChosen(int x) {
        this.x = x;
    }

    public int getChosen() {
        return this.x;
    }
}

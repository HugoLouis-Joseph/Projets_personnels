package IG;

import Board.Color;

public class Handler {
    protected Color c = null;
    public Handler() {
    }

    public void setChosen(Color c) {
        this.c = c;
    }

    public Color getChosen() {
        return this.c;
    }
}

package IG;

import Board.Tile;
import java.awt.*;

public class ColButton extends Button{
    protected Tile tile;
    public ColButton(Tile tile) {
        this.tile = tile;
        this.poke();
    }
    public Tile getTile() {
        return this.tile;
    }
    public void poke() {
        this.setBackground(tile.getSwingColor());
    }
}
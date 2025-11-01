package jeu;
import javax.swing.*;
import java.awt.*;
import board.*;

public class Panel extends JPanel {
    protected Board[] boards;
    protected int player;
    public Panel(Board[] boards , int player) {
        this.boards = boards;
        this.player = player;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 500;

        for (Tile[] line : this.boards[this.player].getBoard()) {
            for (Tile tile : line) {
                g.drawImage((new ImageIcon("src/IG/Ebauches/Eau.png")).getImage(), tile.getX() + (x*this.player) , tile.getY() , this);
                if (tile.getPart() != null) {
                    g.drawImage((new ImageIcon(tile.getImage())).getImage(), tile.getX() + (x*this.player) , tile.getY() , this);
                    if (tile.isHit()) {
                        g.drawImage((new ImageIcon("src/IG/Ebauches/Explose.png")).getImage(), tile.getX() + (x*this.player) , tile.getY() , this);
                    }
                }
                else if (tile.isHit()) {
                    g.drawImage((new ImageIcon("src/IG/Ebauches/Touche.png")).getImage(), tile.getX() + (x*this.player) , tile.getY() , this);
                }
                if (tile.hasAir()) {
                    g.drawImage((new ImageIcon(tile.getImageAir())).getImage(), tile.getX() + (x*this.player) , tile.getY() , this);
                }
                if (tile.isJammed()){
                    g.drawImage((new ImageIcon("src/IG/Ebauches/Brouille.png")).getImage(), tile.getX() + (x*this.player) , tile.getY() , this);
                }
            }
        }
        
        for (Tile[] line : this.boards[(this.player+1)%2].getBoard()) {
            for (Tile tile : line) {
                g.drawImage((new ImageIcon("src/IG/Ebauches/Eau.png")).getImage(), tile.getX() + (x*((this.player+1)%2)) , tile.getY() , this);
                if (tile.isJammed()){
                    g.drawImage((new ImageIcon("src/IG/Ebauches/Brouille.png")).getImage(), tile.getX() + (x*((this.player+1)%2)) , tile.getY() , this);
                }
                else if (tile.isHit()) {
                    if (tile.getPart() != null) {
                        if (tile.getPart().getShip().isDestroyed()) {
                            g.drawImage((new ImageIcon(tile.getImage())).getImage(), tile.getX() + (x*((this.player+1)%2)) , tile.getY() , this);
                        }
                        g.drawImage((new ImageIcon("src/IG/Ebauches/Explose.png")).getImage(), tile.getX() + (x*((this.player+1)%2)) , tile.getY() , this);
                    }
                    else {
                        g.drawImage((new ImageIcon("src/IG/Ebauches/Touche.png")).getImage(), tile.getX() + (x*((this.player+1)%2)) , tile.getY() , this);
                    }
                }
            }
        }

    }
}

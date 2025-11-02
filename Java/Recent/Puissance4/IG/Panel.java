package IG;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import board.*;
import java.util.*;
import player.*;

public class Panel extends JPanel {

    public static int IMAGESIZE = 40;
    protected Board board;
    // This map is used to have less File objects created , since we can reuse the same visual every time the same Pawn is encountered .
    protected HashMap<String,File> imagesForPawns;

    /**
     * Creates a new Panel which extends JPanel , for different display .
     * @param board the Board
     * @param players the array of Player objects
     */
    public Panel(Board board,Player players[]) {
        this.board = board;
        this.imagesForPawns = new HashMap<String,File>();
        // Filling the map .
        for (Player player : players) {
            // Uses Java String concatenation to fabricate the links , allows more flexible use , as we only need to add the visual and give it to a Player to have it show up .
            this.imagesForPawns.put(player.getSwingVisual(), new File("IG/Visuals/" + player.getSwingVisual()));
        }
    }

    /**
     * Overriding the paintComponent method in order to change its behaviour , wasn't able to draw multiple images on the same panel , and at given coordinates , without doing it like this .
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Tile[][] board = this.board.getBoard();

        // The following is for the display of the board , without the pawns .
        try {
            for (int line = 0 ; line < Board.HEIGHT ; line++) {
                g.drawImage(ImageIO.read(new File("IG/Visuals/LeftMiddleBoard.png")), 0 , Panel.IMAGESIZE*(line+1) , this);
                g.drawImage(ImageIO.read(new File("IG/Visuals/RightMiddleBoard.png")), Panel.IMAGESIZE*(Board.LENGTH-1) , Panel.IMAGESIZE*(line+1) , this);
            }
            for (int column = 0 ; column < Board.LENGTH-2 ; column++) {
                g.drawImage(ImageIO.read(new File("IG/Visuals/MiddleUpperBoard.png")), Panel.IMAGESIZE*(column+1) , 0 , this);
                g.drawImage(ImageIO.read(new File("IG/Visuals/MiddleBottomBoard.png")), Panel.IMAGESIZE*(column+1) , Panel.IMAGESIZE*(Board.HEIGHT+1) , this);
            }
            for (int line = 0 ; line < Board.HEIGHT ; line++) {
                for (int column = 0 ; column < Board.LENGTH-2 ; column++) {
                    g.drawImage(ImageIO.read(new File("IG/Visuals/MiddleBoard.png")), Panel.IMAGESIZE*(column+1) , Panel.IMAGESIZE*(line+1) , this);
                }
            }
            g.drawImage(ImageIO.read(new File("IG/Visuals/LeftUpperBoard.png")), 0,0 , this);
            g.drawImage(ImageIO.read(new File("IG/Visuals/RightUpperBoard.png")), Panel.IMAGESIZE*(Board.LENGTH-1),0 , this);
            g.drawImage(ImageIO.read(new File("IG/Visuals/LeftBottomBoard.png")), 0, Panel.IMAGESIZE*(Board.HEIGHT+1) , this);
            g.drawImage(ImageIO.read(new File("IG/Visuals/RightBottomBoard.png")), Panel.IMAGESIZE*(Board.LENGTH-1), Panel.IMAGESIZE*(Board.HEIGHT+1) , this);
        } catch (Exception e) {}  

        // Displaying the pawns on every Tile from the Board .
        for (int line = 0 ; line < Board.HEIGHT ; line++) {
            for (int column = 0 ; column < Board.LENGTH ; column++) {
                try {
                    g.drawImage(ImageIO.read(this.imagesForPawns.get(board[line][column].getContent().getSwingVisual())), Panel.IMAGESIZE*column , Panel.IMAGESIZE*(line+1) , this);
                } catch (Exception e) {}
            }
        }
    }
}

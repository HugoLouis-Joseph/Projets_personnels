// Package //
package board;
// ------- //

// Imports //
import ship.*;
// ------- //

/**
 * Public class for Tile
 */
public class Tile {
    protected Ship content = null;
    protected Plane airContent = null;
    protected boolean isHit = false;
    protected Part part = null;
    protected int isJammed = 0;
    protected int x;
    protected int y;

    /**
     * Creates a new Tile object
     */
    public Tile(int x , int y) {
        this.x = x;
        this.y = y;
    }

    // Faire des fichiers PNG pour chaque partie de bateau , dans toutes les orientations (Nord , Sud...) , attribuer automatiquement un lien relatif vers l'image correspondante
    // aux bateaux en réalisant un truc de la sorte : on donne d'abord le type du bateau en lien coupé -> ex : "./IG/Ebauches/AntiAerien/AntiAerien"
    // en fonction de la partie du bateau , on lui ajoute un chiffre -> ex : "./IG/Ebauches/AntiAerien/AntiAerien" + 1
    // en fonction de l'inclinaison , on lui ajoute un deuxième chiffre (ou lettre) -> ex : "./IG/Ebauches/AntiAerien/AntiAerien" + 1 + 1 / -> ex : "./IG/Ebauches/AntiAerien/AntiAerien" + 1 + "N"
    // faire une méthode annexe qui réalise la même chose mais pour les avions (ou le faire dans Draw directement)
    public String getImage() {
        String txt = this.part.getImage() + this.part.getShip().getOrientation().getLetter();
        if (this.content instanceof Submarine && ((Submarine)this.content).isSubmerged()) {
            txt += "s";
        }
        return "./IG/Ebauches/" + txt + ".png";
    }

    public String getImageAir() {
        return "./IG/Ebauches/Avion/" + this.airContent.getOrientation().getLetter() + ".png";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Getter for part
     * @return part
     */
    public Part getPart() {
        return this.part;
    }

    /**
     * Jams the tile
     */
    public void jam() {
        this.isJammed = 3;
    }

    /**
     * Returns true if isJammed is equal or higher than 1
     * @return boolean
     */
    public boolean isJammed() {
        return this.isJammed >= 1;
    }

    /**
     * Changes attribute isHit to true , and does the same for the contained part if it has one
     */
    public void hit() {
        this.isHit = true;
        if (this.part != null && !this.part.getShip().isSubmerged()) {
            this.part.hit();
        }
    }

    public void hitAir(Board board) {
        if (this.airContent != null) {
            System.out.println("A plane has been hit !");
            board.removePlane(this.airContent);
            this.airContent = null;
        }
    }

    /**
     * Getter for isHit
     * @return isHit
     */
    public boolean isHit() {
        return this.isHit;
    }

    /**
     * Sets the part to contain , and the mother ship
     * @param part part to contain
     */
    public void setContent(Part part) {
        this.part = part;
        this.content = part.getShip();
    }

    public void setAirContent(Plane plane) {
        this.airContent = plane;
    }

    public void resetContent() {
        this.content = null;
        this.part = null;
    }

    public void resetAirContent() {
        this.airContent = null;
    }

    public boolean hasAir() {
        return this.airContent != null;
    }

    /**
     * Display method
     */
    public void display() {

        if (!this.isHit) {
            if (this.content != null) {
                if (this.hasAir()) {
                    System.out.print(this.airContent.getLetter());
                }
                else {
                    System.out.print(this.part.getLetter());
                }
            }
            else {
                System.out.print(" ~ ");
            }
        }
        else {
            if (this.content != null && this.content.isHit()) {
                if (this.content.isDestroyed()) {
                    System.out.print(" ¤ ");
                }
                else {
                    System.out.print(" o ");
                }
            }
            else {
                System.out.print(" x ");
            }
        }
    }

    /**
     * Same as display() but is used when it isn't the player's turn , in order to hide the non-hit ships
     */
    public void displayHidden() {

        if (this.hasAir()) {
            System.out.print(this.airContent.getLetter());
        } else {

        if ((this.isJammed--) > 0 || !this.isHit) {
            System.out.print(" ~ ");
        }
        else {
            if (this.content != null) {
                if (this.content.isDestroyed()) {
                    System.out.print(" ¤ ");
                }
                else {
                    System.out.print(" o ");
                }
            }
            else {
                System.out.print(" x ");
            }
        }
    }
    }
}
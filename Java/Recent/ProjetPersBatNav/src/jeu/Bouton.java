package jeu;
import java.awt.*;

public class Bouton extends Button{
    protected int valeur;
    public Bouton(String nom , int valeur) {
        super(nom);
        this.valeur = valeur;
    }

    public int getValue() {
        return this.valeur;
    }

    public void setValue(int x) {
        this.valeur = x;
    }
}

package entite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends Entite {
    // Type de la plateforme
    protected String type;

    public Plateforme(){}

    public Plateforme(double x, double y, double largeur, double hauteur, Color couleur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.couleur = couleur;
    }

    public String getType() {
        return type;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Dessine une plateforme
     *
     * @param context context
     * @param fenetreY position en y de la fenêtre
     */
    public void draw(GraphicsContext context, double fenetreY) {
        context.setFill(couleur);

        // Position en y par rapport au canvas
        double yAffiche = -fenetreY + 480 - y;

        context.fillRect(x, yAffiche, largeur, hauteur);
    }
}
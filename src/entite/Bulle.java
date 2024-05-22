package entite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulle extends Entite {
    // Rayon de la bulle
    private double rayon;
    private Color couleur;

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    public double getRayon() {
        return this.rayon;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    /**
     * Met à jour l'affichage d'une bulle
     *
     * @param deltaTime variation de temps
     */
    public void update(double deltaTime) {
        y = y - vy * deltaTime;
    }

    /**
     * Dessine une bulle
     *
     * @param context context
     */
    public void draw(GraphicsContext context) {
        context.setFill(couleur);
        context.fillOval(x, y, 2 * rayon, 2 * rayon);
    }
}
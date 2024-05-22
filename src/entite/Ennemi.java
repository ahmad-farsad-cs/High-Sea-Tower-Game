package entite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ennemi extends Entite {

    // Image utilisée pour dessiner l'ennemi
    private Image image = new Image("/ennemi.png");
    // Indique si l'ennemi doit disparaître
    private boolean disparaitre = false;
    // Plateforme sur laquelle l'ennemi se trouve
    private Plateforme plancher;
    // Utilisé pour prévoir le dépassement horizontal de la plateforme plancher
    private double newX;

    public Ennemi(double x, double y) {
        this.hauteur = 50;
        this.largeur = 50;
        this.x = x;
        this.y = y;
        this.vx = 40;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
    }

    public boolean getDisparaite() {
        return disparaitre;
    }

    public void setPlancher(Plateforme plancher) {
        this.plancher = plancher;
    }

    /**
     * Met à jour l'affichage d'un ennemi
     *
     * @param deltaTime variation de temps
     */
    public void update(double deltaTime) {
        newX = x + deltaTime * vx;

        // Si l'ennemi est sur le point de dépasser horizontalement de sa plateforme plancher
        if (newX + largeur > plancher.getX() + plancher.getLargeur()) {
            disparaitre = true;
        } else {
            vx += deltaTime * ax;
            x += deltaTime * vx;
        }
    }

    /**
     * Dessine un ennemi
     *
     * @param context context
     * @param fenetreY position en y de la fenêtre
     */
    public void draw(GraphicsContext context, double fenetreY) {
        // Position en y par rapport au canvas
        double yAffiche = -fenetreY + 480 - y;

        context.drawImage(image, x, yAffiche, largeur, hauteur);
    }

}
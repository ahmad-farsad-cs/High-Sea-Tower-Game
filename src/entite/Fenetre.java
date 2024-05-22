package entite;

public class Fenetre extends Entite {

    // Indique si la vitesse de la montée de la fenêtre doit être triplée
    private boolean accelerer = false;
    // Indique si la vitesse de la montée de la fenêtre devrait redevenir celle qu'elle était juste avant de tripler
    private boolean decelerer = false;
    // Vitesse de la montée de la fenêtre juste avant qu'elle ne soit triplée
    private double vyAnterieure;

    public Fenetre(double y, double vy, double ay) {
        this.hauteur = 480;
        this.largeur = 350;
        this.x = 0;
        this.y = y;
        this.vx = 0;
        this.vy = vy;
        this.ax = 0;
        this.ay = ay;
    }

    public void setDecelerer(boolean decelerer) {
        this.decelerer = decelerer;
    }

    public void setAccelerer(boolean accelerer) {
        this.accelerer = accelerer;
    }

    /**
     * Ajuste la fenêtre pour que la méduse ne dépasse pas 75% de la hauteur de l'écran à partir du bas
     *
     * @param difference différence entre 75% de la hauteur de l'écran à partir du bas et la position en y de la méduse
     * par rapport à l'écran en partant du bas
     */
    public void ajuster(double difference) {
        y -= difference;
    }

    /**
     * Met à jour l'affichage de la fenêtre
     *
     * @param deltaTime variation de temps
     * @param debug indication que la partie est en mode debug
     */
    public void update(double deltaTime, boolean debug) {
        // La fenêtre ne monte pas en mode debug.
        if (!debug) {
            if (accelerer) {
                vyAnterieure = vy;
                vy *= 3;
                accelerer = false;
            }
            if (decelerer) {
                vy = vyAnterieure;
                decelerer = false;
            }
            vy += deltaTime * ay;
            y -= vy * deltaTime;
        }
    }

}
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Controleur {

    private Modele modele = new Modele();

    public boolean getAvantPartie() {
        return modele.getAvantPartie();
    }

    public void setAvantPartie(boolean avantPartie) {
        modele.setAvantPartie(avantPartie);
    }

    public boolean getDebug() {
        return modele.getDebug();
    }

    public void setDebug(boolean debug) {
        modele.setDebug(debug);
    }

    public boolean getPause() {
        return modele.getPause();
    }

    public void setPause(boolean pause) {
        modele.setPause(pause);
    }

    public void setSaute(boolean saute) {
        modele.setSaute(saute);
    }

    public boolean getParterre() {
        return modele.getParterre();
    }

    public void setAllerDroite(boolean allerDroite) {
        modele.setAllerDroite(allerDroite);
    }

    public void setAllerGauche(boolean allerGauche) {
        modele.setAllerGauche(allerGauche);
    }

    public void setRegardeDroite(boolean regardeDroite) {
        modele.setRegardeDroite(regardeDroite);
    }

    public boolean getFinPartie() {
        return modele.getFinPartie();
    }

    public void setFinPartie(boolean finPartie) {
        modele.setFinPartie(finPartie);
    }

    public String getScore() {
        return modele.getScore();
    }

    /**
     * Retourne les informations pour le mode debug créées dans la méthode infosDegug() de la classe Modele
     *
     * @return ArrayList<String> Retourne les informations pour le mode debug
     */
    public ArrayList<String> infosDebug() {
        return modele.infosDebug();
    }

    /**
     * Réinitialise les entités
     */
    public void reset() {
        modele.reset();
    }

    /**
     * Met à jour l'affichage
     *
     * @param dt variation de temps
     */
    public void update(double dt) {
        modele.update(dt);
    }

    /**
     * Dessine l'affichage
     *
     * @param context context
     */
    public void draw(GraphicsContext context) {
        modele.draw(context);
    }

}
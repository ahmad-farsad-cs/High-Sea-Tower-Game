import entite.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Modele {

    // Utilisés pour savoir que trois secondes ce sont écoulées
    private double totalTime = 0;
    private boolean flag = true;
    // Indique que la partie n'est pas encore commencée
    private boolean avantPartie = true;
    // Indique que la partie est en mode debug
    private boolean debug = false;
    // Indique que la partie est en pause
    private boolean pause = false;
    // Indique que la partie est terminée
    private boolean finPartie = false;
    // Nombre de "mètres" montés
    private String score;
    // Entités du jeu
    private Fenetre fenetre = new Fenetre(0, 50, 2);
    private Meduse meduse = new Meduse(150, 50);
    private ArrayList<Plateforme> plateformes = constructeurPlateformes();
    private ArrayList<Bulle> bulles = new ArrayList<>();
    private ArrayList<Ennemi> ennemis = constructeurEnnemi();

    public boolean getAvantPartie() {
        return avantPartie;
    }

    public void setAvantPartie(boolean avantPartie) {
        this.avantPartie = avantPartie;
    }

    public boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean getPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean getFinPartie() {
        return finPartie;
    }

    public void setFinPartie(boolean finPartie) {
        this.finPartie = finPartie;
    }

    public String getScore() {
        return score;
    }

    public void setAllerDroite(boolean allerDroite) {
        meduse.setAllerDroite(allerDroite);
    }

    public void setAllerGauche(boolean allerGauche) {
        meduse.setAllerGauche(allerGauche);
    }

    public void setRegardeDroite(boolean regardeDroite) {
        meduse.setRegardeDroite(regardeDroite);
    }

    public void setSaute(boolean saute) {
        meduse.setSaute(saute);
    }

    public boolean getParterre() {
        return meduse.getParterre();
    }

    /**
     * Construit la liste des Plateforme à afficher avant la partie et au début de celle-ci
     *
     * @return ArrayList<Plateforme> Retourne la liste des Plateforme affichées avant et au début de la partie
     */
    public ArrayList<Plateforme> constructeurPlateformes() {
        ArrayList<Plateforme> temp = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            temp.add(ajouterPlateforme(temp, (i + 1) * (100 + 10)));
        }

        return temp;
    }

    /**
     * Crée une Plateforme à ajouter à la liste des Plateforme actuelles
     *
     * @param liste Plateforme actuelles
     * @param y     Position de la plateforme à ajouter
     * @return Plateforme Retourne une Plateforme
     */
    public Plateforme ajouterPlateforme(ArrayList<Plateforme> liste, double y) {
        double nbAlea = Math.random();

        Plateforme plateforme = new Plateforme();
        plateforme.setLargeur(Math.random() * 95 + 80);
        plateforme.setX(Math.random() * (350 - plateforme.getLargeur()));
        plateforme.setHauteur(10);
        plateforme.setY(y);

        if (liste.size() > 0) {
            if (!liste.get(liste.size() - 1).getType().equals("solide")) {
                if (nbAlea >= 0 && nbAlea < 0.05) {
                    plateforme.setCouleur(Color.rgb(184, 15, 36));
                    plateforme.setType("solide");
                } else if (nbAlea >= 0.05 && nbAlea < 0.15) {
                    plateforme.setCouleur(Color.rgb(230, 221, 58));
                    plateforme.setType("accelerante");
                } else if (nbAlea >= 0.15 && nbAlea < 0.35) {
                    plateforme.setCouleur(Color.LIGHTGREEN);
                    plateforme.setType("rebondissante");
                } else {
                    plateforme.setCouleur(Color.rgb(230, 134, 58));
                    plateforme.setType("simple");
                }
            } else {
                if (nbAlea >= 0 && nbAlea < 0.1 / 0.95) {
                    plateforme.setCouleur(Color.rgb(230, 221, 58));
                    plateforme.setType("accelerante");
                } else if (nbAlea >= 0.1 / 0.95 && nbAlea < 0.3 / 0.95) {
                    plateforme.setCouleur(Color.LIGHTGREEN);
                    plateforme.setType("rebondissante");
                } else {
                    plateforme.setCouleur(Color.rgb(230, 134, 58));
                    plateforme.setType("simple");
                }
            }
        } else {
            if (nbAlea >= 0 && nbAlea < 0.05) {
                plateforme.setCouleur(Color.rgb(184, 15, 36));
                plateforme.setType("solide");
            } else if (nbAlea >= 0.05 && nbAlea < 0.15) {
                plateforme.setCouleur(Color.rgb(230, 221, 58));
                plateforme.setType("accelerante");
            } else if (nbAlea >= 0.15 && nbAlea < 0.35) {
                plateforme.setCouleur(Color.LIGHTGREEN);
                plateforme.setType("rebondissante");
            } else {
                plateforme.setCouleur(Color.rgb(230, 134, 58));
                plateforme.setType("simple");
            }
        }

        return plateforme;
    }

    public ArrayList<Ennemi> constructeurEnnemi() {
        ArrayList<Ennemi> temp = new ArrayList<>();

        // La boucle commence à 1 car on ne veut pas (possiblement) mettre le premier fantôme sur la prmière plateforme.
        for (int i = 1; i < 4; i++) {
            double nbAlea = Math.random();

            Plateforme plateforme = plateformes.get(i);

            if (0 <= nbAlea && nbAlea < 0.5) {
                if (temp.size() > 0) {
                    if (!(temp.get(temp.size() - 1).getY() > plateforme.getY() - 110)) {
                        Ennemi ennemi = new Ennemi(plateforme.getX(), plateforme.getY() + 50);
                        ennemi.setPlancher(plateforme);
                        temp.add(ennemi);
                    }
                } else {
                    Ennemi ennemi = new Ennemi(plateforme.getX(), plateforme.getY() + 50);
                    ennemi.setPlancher(plateforme);
                    temp.add(ennemi);
                }
            }
        }

        return temp;
    }

    /**
     * Construit la liste des Bulle à afficher
     *
     * @return ArrayList<Bulle> Retourne la liste des Bulle à afficher
     */
    public ArrayList<Bulle> constructeurBulles() {
        ArrayList<Bulle> tempListe = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            double pos = (Math.random() * 350) + (Math.random() * 40 - 20);
            for (int j = 0; j < 5; j++) {
                Bulle b = new Bulle();

                b.setRayon(Math.random() * (20 - 5) + 5);
                b.setX(pos);
                b.setY(520);
                b.setCouleur(Color.rgb(0, 0, 255, 0.4));
                b.setVy(Math.random() * (350 - 450) + 350);

                tempListe.add(b);
            }
        }

        return tempListe;
    }

    /**
     * Réinitialise les entités
     */
    public void reset() {
        // Réinisialisation de la fenêtre
        this.fenetre = new Fenetre(0, 50, 2);

        // Position en x initiale
        double x = 150;
        // Position en y initiale
        double y = 50;
        // Réinitialisation de la position de la méduse
        this.meduse = new Meduse(x, y);

        // Suppression des bulles
        this.bulles.clear();

        // Suppresion des plateformes
        this.plateformes.clear();
        // Création de nouvelles plateformes initiales
        this.plateformes = constructeurPlateformes();

        // Suppresion des ennemis
        this.ennemis.clear();
        // Création de nouveuax ennemis initiaux
        this.ennemis = constructeurEnnemi();
    }

    /**
     * Crée et retourne les informations pour le mode debug
     *
     * @return ArrayList<String> Retourne les informations pour le mode debug
     */
    public ArrayList<String> infosDebug() {
        ArrayList<String> infosDebug = new ArrayList<>();

        String position = "position=(" + (int) meduse.getX() + "," + (int) meduse.getY() + ")";
        infosDebug.add(position);

        String acceleration = "v=(" + ((int) meduse.getVx()) + "," + ((int) meduse.getVy()) + ")";
        infosDebug.add(acceleration);

        String vitesse = "a=(" + ((int) meduse.getAx()) + "," + ((int) meduse.getAy()) + ")";
        infosDebug.add(vitesse);

        String sol = "Touche le sol: ";
        if (meduse.getParterre()) {
            sol += "oui";
        } else {
            sol += "non";
        }
        infosDebug.add(sol);

        return infosDebug;
    }

    /**
     * Met à jour l'affichage
     *
     * @param deltaTime variation de temps
     */
    public void update(double deltaTime) {

        // Met à jour l'origine de la fenêtre
        fenetre.update(deltaTime, debug);

        // Ajoute des plateformes et des ennemis
        if (-fenetre.getY() + 480 > plateformes.get(plateformes.size() - 1).getY() + 100) {
            Plateforme plateforme = ajouterPlateforme(plateformes,
                (plateformes.get(plateformes.size() - 1)).getY() + 110);

            plateformes.add(plateforme);

            double nbAlea = Math.random();

            if (0 <= nbAlea && nbAlea < 0.5) {
                if (ennemis.size() > 0) {
                    if (!(ennemis.get(ennemis.size() - 1).getY() > plateforme.getY() - 110)) {
                        Ennemi ennemi = new Ennemi(plateforme.getX(), plateforme.getY() + 50);
                        ennemi.setPlancher(plateforme);
                        ennemis.add(ennemi);
                    }
                } else {
                    Ennemi ennemi = new Ennemi(plateforme.getX(), plateforme.getY() + 50);
                    ennemi.setPlancher(plateforme);
                    ennemis.add(ennemi);
                }
            }
        }

        // Fait disparaître les plateformes qui ont dépassé le bas de l'écran
        if (-fenetre.getY() > plateformes.get(0).getY()) {
            plateformes.remove(0);
        }

        // Fait disparaître les ennemis qui ont dépassé horizontalement leur plateforme plancher
        if (ennemis.size() > 0) {
            // Ennemis à faire disparaître
            ArrayList<Ennemi> temp = new ArrayList<>();
            for (Ennemi i : ennemis) {
                if (i.getDisparaite()) {
                    temp.add(i);
                }
            }
            ennemis.removeAll(temp);
        }

        // Crée des bulles toutes les trois secondes
        if (totalTime <= 3 && flag) {
            flag = false;
            ArrayList<Bulle> tempBulles = constructeurBulles();
            for (Bulle tempBulle : tempBulles)
                bulles.add(tempBulle);
        }

        // Supprime les bulles qui sont plus haut que le haut de l'écran
        for (int j = 0; j < bulles.size(); j++) {
            (bulles.get(j)).update(deltaTime);
            if ((bulles.get(j)).getY() + ((bulles.get(j)).getRayon()) <= 0) {
                bulles.remove(j);
            }
        }

        totalTime += deltaTime;
        if (totalTime > 3) {
            flag = true;
            totalTime = 0;
        }

        // Met à jour les ennemis
        for (Ennemi i : ennemis) {
            i.update(deltaTime);
        }

        // Met à jour la méduse
        meduse.update(deltaTime, fenetre, plateformes, debug);

//        System.out.println(meduse.getY());

        // Met à jour le score
        score = "" + (int) -fenetre.getY() + "m";

        // Si la méduse dépasse 75% de l'écran à partir du bas en sautant, l’écran monte de façon à ce que la coordonnée
        // la plus haute de la méduse ne soit jamais affichée plus haut que ça.
        double yAffichage = -fenetre.getY() + 480 - meduse.getY();
        if (meduse.getVy() > 0 && yAffichage < HighSeaTower.HEIGHT * (1 - 0.75)) {
            double difference = HighSeaTower.HEIGHT * (1 - 0.75) - yAffichage;
            fenetre.ajuster(difference);
        }

        // Si la méduse est parterre et que la partie est en mode debug, le plancher est jaune, sinon il ne l'est pas.
        if (debug && meduse.getParterre()) {
            meduse.getPlancher().setCouleur(Color.YELLOW);
        } else {
            if (meduse.getPlancher().getType().equals("solide")) {
                meduse.getPlancher().setCouleur(Color.rgb(184, 15, 36));
            } else if (meduse.getPlancher().getType().equals("accelerante")) {
                meduse.getPlancher().setCouleur(Color.rgb(230, 221, 58));
            } else if (meduse.getPlancher().getType().equals("rebondissante")) {
                meduse.getPlancher().setCouleur(Color.LIGHTGREEN);
            } else {
                meduse.getPlancher().setCouleur(Color.rgb(230, 134, 58));
            }
        }

        // Vérifie si la partie est terminée
        if (yAffichage > HighSeaTower.HEIGHT || meduse.collisionsEnnemis(ennemis)) {
            finPartie = true;
        }
    }

    /**
     * Dessine l'affichage
     *
     * @param context context
     */
    public void draw(GraphicsContext context) {
        // Dessine la couleur de l'arrière-plan
        context.setFill(Color.rgb(0, 0, 139));
        context.fillRect(0, 0, HighSeaTower.WIDTH, HighSeaTower.HEIGHT);

        // Dessine les bulles
        for (int j = 0; j < bulles.size(); j++) {
            (bulles.get(j)).draw(context);
        }

        // Dessine les plateformes
        for (Plateforme p : plateformes) {
            p.draw(context, fenetre.getY());
        }

        // Dessine les ennemis
        for (Ennemi i : ennemis) {
            i.draw(context, fenetre.getY());
        }

        // Dessine la méduse
        meduse.draw(context, fenetre.getY());

        // Dessine le carré rouge de la méduse lorsque la partie est en mode debug
        if (debug) {
            context.setFill(Color.rgb(255, 0, 0, 0.5));

            // Position en y par rapport au canvas
            double yAffichage = -fenetre.getY() + 480 - meduse.getY();

            context.fillRect(meduse.getX(), yAffichage, 50, 50);
        }
    }

}
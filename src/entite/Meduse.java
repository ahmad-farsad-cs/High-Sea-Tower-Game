package entite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Meduse extends Entite {

	// Image utilisée pour dessiner la méduse avant que la partie commence et en premier lorsque la partie commence
	private Image image;
	// Images utilisées pour l'animation de la méduse quand elle regarde vers la droite et vers la gauche,
	// respectivement
	private Image[] framesDroite, framesGauche;
	// 8 images par seconde pour l'animation de la méduse
	private double frameRate = 8;
	// Utilisé pour savoir que trois secondes ce sont écoulées
	private double tempsTotal = 0;
	// Plateforme sur laquelle la méduse se trouve ou se trouvait avant de se trouver sur une autre
	private Plateforme plancher = new Plateforme(0, 480, 350, 10, Color.rgb(230, 134, 58));
	// Utilisés pour prévoir les collisions de la méduse avec les plateformes et les côtés de l'écran
	private double newX, newY;
	// Inidiquent si la méduse doit se diriger vers la droite ou vers la gauche, respectivement
	private boolean allerDroite = false, allerGauche = false;
	// Indique si la méduse doit regarder à droite (sinon, elle regarde à gauche)
	private boolean regardeDroite = true;
	// Inidique s'il est le moment que la méduse entâme son saut (La méduse est au tout premier instant de son saut.)
	private boolean saute = false;
	// Inidique si la méduse se trouve sur une plateforme
	private boolean parterre = true;

	public Meduse(double x, double y) {
		this.hauteur = 50;
		this.largeur = 50;
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.ax = 0;
		this.ay = -1200;
		framesDroite = new Image[]{
				new Image("/jellyfish1.png"),
				new Image("/jellyfish2.png"),
				new Image("/jellyfish3.png"),
				new Image("/jellyfish4.png"),
				new Image("/jellyfish5.png"),
				new Image("/jellyfish6.png"),
		};
		framesGauche = new Image[]{
				new Image("/jellyfish1g.png"),
				new Image("/jellyfish2g.png"),
				new Image("/jellyfish3g.png"),
				new Image("/jellyfish4g.png"),
				new Image("/jellyfish5g.png"),
				new Image("/jellyfish6g.png"),
		};
		image = framesDroite[0];
		this.plancher.type = "simple";
	}

	public Plateforme getPlancher() {
		return plancher;
	}

	public void setAllerDroite(boolean allerDroite) {
		this.allerDroite = allerDroite;
	}

	public void setAllerGauche(boolean allerGauche) {
		this.allerGauche = allerGauche;
	}

	public void setRegardeDroite(boolean regardeDroite) {
		this.regardeDroite = regardeDroite;
	}

	public void setSaute(boolean saute) {
		this.saute = saute;
	}

	public boolean getParterre() {
		return parterre;
	}

	/**
	 * Indique si la méduse intersecte une plateforme donnée vers le bas
	 *
	 * @param plateforme plateforme donnée
	 * @return boolean Retourne true si la méduse intersecte une plateforme donnée vers le bas et false sinon
	 */
	public boolean intersectsVersLeBas(Plateforme plateforme) {
		return vy < 0 &&
			newY - hauteur < plateforme.getY() && newY - hauteur > plateforme.getY() - plateforme.getHauteur()
			&& newX >= plateforme.getX() - largeur && newX <= plateforme.getX() + plateforme.getLargeur();
	}

	/**
	 * Indique si la méduse intersecte une plateforme donnée vers le haut
	 *
	 * @param plateforme plateforme donnée
	 * @return boolean Retourne true si la méduse intersecte une plateforme donnée vers le haut et false sinon
	 */
	public boolean intersectsVersLeHaut(Plateforme plateforme) {
		return vy > 0 && newY < plateforme.getY() + plateforme.getHauteur() && newY > plateforme.getY()
				&& newX + largeur >= plateforme.getX() && newX <= plateforme.getX() + plateforme.getLargeur();
	}

	/**
	 * Indique si la méduse intersecte une plateforme donnée par les côtés
	 *
	 * @param plateforme plateforme donnée
	 * @return boolean Retourne true si la méduse intersecte une plateforme donnée par les côtés et false sinon
	 */
	public boolean intersectsParLesCotes(Plateforme plateforme) {
		// Indique si la méduse était à côté de la plateforme
		boolean etaitSurLeCote = x + largeur < plateforme.getX() || x > plateforme.getX() + plateforme.getLargeur();
		// Indique si la méduse est à une postion en y à laquelle il est possible d'entrer en collision par les côtés
		// avec la plateforme
		boolean horizontal =
			(newX + largeur > plateforme.getX() && newX + largeur < plateforme.getX() + plateforme.getLargeur())
				|| (newX < plateforme.getX() + plateforme.getLargeur() && newX > plateforme.getX());
		// Indique si la méduse est à une postion en y à laquelle il est possible d'entrer en collision par les côtés
		// avec la plateforme
        boolean vertical =
			(newY - hauteur < plateforme.getY() && newY - hauteur > plateforme.getY() - plateforme.getHauteur())
				|| (newY < plateforme.getY() && newY > plateforme.getY() - plateforme.getHauteur())
				|| (newY > plateforme.getY() && newY - hauteur < plateforme.getY() - plateforme.getHauteur());
		return etaitSurLeCote && horizontal && vertical;
	}

	/**
	 * Gère les collisions potentielles entre la méduse et une plateforme donnée de type solide
	 *
	 * @param plateforme plateforme donnée
	 * @param debug indicateur que la partie est en mode débug
	 */
	public void solide(Plateforme plateforme, boolean debug) {
		if (intersectsVersLeBas(plateforme)) {
			parterre = true;
			plancher = plateforme;
			vx = 0;
			y = plateforme.getY() + hauteur;
		}
		if (intersectsVersLeHaut(plateforme)) {
			// De la friction a été ajoutée.
			vy *= -0.9;
			if (debug) {
				plateforme.couleur = Color.YELLOW;
			}
		}
		if (intersectsParLesCotes(plateforme)) {
			// De la friction a été ajoutée.
			vx *= -0.9;
		}
	}

	/**
	 * Gère les collisions potentielles entre la méduse et une plateforme donnée de type accélérante
	 *
	 * @param plateforme plateforme donnée
	 * @param fenetre fenetre du jeu
	 */
	public void accelerante(Plateforme plateforme, Fenetre fenetre) {
		if (intersectsVersLeBas(plateforme)) {
			fenetre.setAccelerer(true);
			parterre = true;
			plancher = plateforme;
			y = plateforme.getY() + hauteur;
		}
	}

	/**
	 * Gère les collisions potentielles entre la méduse et une plateforme donnée de type rebondissante
	 *
	 * @param plateforme plateforme donnée
	 * @param debug indicateur que la partie est en mode débug
	 */
	public void rebondissante(Plateforme plateforme, boolean debug) {
		if (intersectsVersLeBas(plateforme)) {
			vy *= -1.5;
			ax = 0;
			vx = 0;
			if (debug) {
				plateforme.couleur = Color.YELLOW;
			}
		}
	}

	/**
	 * Gère les collisions potentielles entre la méduse et une plateforme donnée de type simple
	 *
	 * @param plateforme plateforme donnée
	 */
	public void simple(Plateforme plateforme) {
		if (intersectsVersLeBas(plateforme)) {
			parterre = true;
			plancher = plateforme;
			vx = 0;
			y = plateforme.getY() + hauteur;
		}
	}

	/**
	 * Gère les collisions potentielles entre la méduse et les plateformes actuelles
	 *
	 * @param fenetre fenetre du jeu
	 * @param plateformes plateformes actuelles
	 * @param debug indicateur que la partie est en mode débug
	 */
	public void testCollisionsPlateformes(Fenetre fenetre, ArrayList<Plateforme> plateformes, boolean debug) {
		for (Plateforme platforme : plateformes) {
		if (platforme.type.equals("solide")) {
			if (platforme.couleur == Color.YELLOW && plancher != platforme) {
				platforme.couleur = Color.rgb(184, 15, 36);
			}
			solide(platforme, debug);
		} else if (platforme.type.equals("accelerante")) {
			accelerante(platforme, fenetre);
		} else if (platforme.type.equals("rebondissante")) {
			// Si la plateforme était jaune, elle reprend sa couleur originale (car elle était en collision avec la
			// plateforme uniquement à un instant).
			if (platforme.couleur == Color.YELLOW) {
				platforme.couleur = Color.LIGHTGREEN;
			}
			rebondissante(platforme, debug);
		} else
			simple(platforme);
		}
	}

	/**
	 * Inidique s'il y au moins une collision entre la méduse et les ennemis actuels
	 *
	 * @param ennemis ennemis actuels
	 * @return boolean Retourne true s'il y au moins une collision entre la méduse et les ennemis actuels et false sinon
	 */
	public boolean collisionsEnnemis(ArrayList<Ennemi> ennemis) {
		for (Ennemi i : ennemis) {
			if (!(x + largeur < i.x || x > i.x + i.largeur || y - largeur > i.y || y < i.y - largeur)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Met à jour l'affichage de la méduse
	 *
	 * @param deltaTime variation de temps
	 * @param fenetre fenêtre
	 * @param plateformes plateformes
	 * @param debug indication que la partie est en mode debug
	 */
	public void update(double deltaTime, Fenetre fenetre, ArrayList<Plateforme> plateformes, boolean debug) {
		newX = x + deltaTime * vx;
		newY = y + deltaTime * vy;
		// Gère les collisions potentielles entre la méduse et les plateformes actuelles
		testCollisionsPlateformes(fenetre, plateformes, debug);
		// Si la méduse entre en collision avec l'un des côtés de l'écran
		if (newX + largeur > 350 || newX < 0) {
			// De la friction a été ajoutée.
			vx *= -0.9;
			ax = 0;
		}

		// Mise à jour de la position en y par l'application de l'accélération gravitationnelle
		if(!parterre) {
			vy += deltaTime * ay;
			y += deltaTime * vy;
		} else {
			vy = 0;
		}

		// S'il est le moment que la méduse entâme un saut et qu'elle se trouve sur une plateforme (La méduse est au
		// tout premier instant de son saut.)
		if (saute && parterre) {
			ax = 0;
			vx = 0;
			// Assigne une vitesse instantannée vers le haut de 600 pixels par seconde
			vy = 600;
			// La méduse n'est plus en train d'entâmer son saut (La méduse est au tout premier instant de son saut.)
			saute = false;
			if (plancher.type.equals("accelerante")) {
				fenetre.setDecelerer(true);
			}
			// La méduse n'est plus par terre.
			parterre = false;
		}

		// Déplacements horizontauxS
		if (allerDroite) {
			ax = 1200;
			vx += deltaTime * ax;
			x += deltaTime * vx;
		} else if (allerGauche) {
			ax = -1200;
			vx += deltaTime * ax;
			x += deltaTime * vx;
		} else {
			if (!parterre) {
				vx *= 0.9;
				vx += deltaTime * ax;
				x += deltaTime * vx;
			} else {
				vx = 0;
			}
		}

		// Si la méduse ne se trouve plus sur sa plateforme plancher
		if (parterre && (x + largeur < plancher.x || x > plancher.x + plancher.largeur)) {
			parterre = false;
		}

		// Mise à jour de l'image pour l'animation de la méduse
		tempsTotal += deltaTime;
		int frame = (int) (tempsTotal * frameRate);
		if (regardeDroite) {
			image = framesDroite[frame % framesDroite.length];
		} else {
			image = framesGauche[frame % framesGauche.length];
		}
	}

	/**
	 * Dessine la méduse
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
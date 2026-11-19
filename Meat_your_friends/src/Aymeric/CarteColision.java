package Aymeric;

import java.awt.Color;
import java.awt.Graphics2D;

public class CarteColision {

    private final Map carte;

    public CarteColision(Map carte) {
        this.carte = carte;
    }

    /** Case (ligne, colonne) bloquante ? Hors carte = bloquant. */
    public boolean estSolide(int ligne, int col) {
        if (ligne < 0 || col < 0 || ligne >= carte.getHauteur() || col >= carte.getLargeur()) {
            return true;
        }
        return carte.getNiveau().get(Niveau.CALQUE_COLLISION, ligne, col) != 0;
    }

    /** Même test à partir d'une position en pixels. */
    public boolean estSolidePx(double x, double y) {
        int col = (int) Math.floor(x / Map.TAILLE_TUILE);
        int ligne = (int) Math.floor(y / Map.TAILLE_TUILE);
        return estSolide(ligne, col);
    }

    /** Utilisé quand DEBUG_COLLISIONS est vrai : cases bloquantes en rouge transparent. */
    public void rendu(Graphics2D g) {
        g.setColor(new Color(255, 0, 0, 100));
        for (int l = 0; l < carte.getHauteur(); l++) {
            for (int c = 0; c < carte.getLargeur(); c++) {
                if (estSolide(l, c)) {
                    g.fillRect(c * Map.TAILLE_TUILE, l * Map.TAILLE_TUILE,
                               Map.TAILLE_TUILE, Map.TAILLE_TUILE);
                }
            }
        }
    }
}
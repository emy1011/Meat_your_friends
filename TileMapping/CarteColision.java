package TileMapping;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class CarteColision {

    public static final int LARGEUR = 20;      // en cases
    public static final int HAUTEUR = 20;      // en cases
    public static final int TAILLE_TUILE = 32; // en pixels

    // 0 = libre, 1 = bloquant
    private final int[][] blocs = new int[HAUTEUR][LARGEUR];

    public CarteColision() {

        // ===== Bordure de la carte (arbres d'automne) =====
        remplir(0, 0, LARGEUR, 1);             // haut
        remplir(0, HAUTEUR - 1, LARGEUR, 1);   // bas
        remplir(0, 0, 1, HAUTEUR);             // gauche
        remplir(LARGEUR - 1, 0, 1, HAUTEUR);   // droite

        // ===== Lacs =====
        remplir(8, 1, 4, 4);     // lac du haut
        remplir(2, 8, 4, 4);     // lac de gauche
        remplir(15, 8, 4, 4);    // lac de droite
        remplir(8, 15, 4, 4);    // lac du bas

        // ===== Bâtiments (seulement les bâtiments, pas les champs) =====
        remplir(2, 1, 2, 3);     // maison rouge (haut gauche)
        remplir(15, 1, 3, 2);    // grange (haut droite)
        remplir(18, 1, 1, 2);    // silo
        remplir(2, 15, 2, 2);    // maison bleue (bas gauche)
        remplir(16, 15, 2, 2);   // maison verte (bas droite)

        // ===== Optionnel : forêt centrale =====
        // remplir(7, 5, 7, 9);

        // Pour dégager une case précise après un remplir :
        // poser(colonne, ligne, false);
    }

    // Pose ou retire un obstacle sur une case
    public final void poser(int colonne, int ligne, boolean bloquant) {
        if (dansLaCarte(colonne, ligne)) {
            blocs[ligne][colonne] = bloquant ? 1 : 0;
        }
    }

    // Remplit un rectangle de cases avec des obstacles
    // (colonne et ligne de départ, puis largeur et hauteur en cases)
    public final void remplir(int colonne, int ligne, int largeur, int hauteur) {
        for (int y = ligne; y < ligne + hauteur; y++) {
            for (int x = colonne; x < colonne + largeur; x++) {
                poser(x, y, true);
            }
        }
    }

    private boolean dansLaCarte(int colonne, int ligne) {
        return colonne >= 0 && ligne >= 0 && colonne < LARGEUR && ligne < HAUTEUR;
    }

    // Vrai si la case (colonne, ligne) est bloquante
    public boolean estBloquant(int colonne, int ligne) {
        if (!dansLaCarte(colonne, ligne)) {
            return true; // hors de la carte = bloquant
        }
        return blocs[ligne][colonne] == 1;
    }

    // Vrai si un rectangle (en pixels) touche une case bloquante
    public boolean collision(Rectangle zone) {
        if (zone.x < 0 || zone.y < 0) {
            return true;
        }

        int xMin = zone.x / TAILLE_TUILE;
        int xMax = (zone.x + zone.width - 1) / TAILLE_TUILE;
        int yMin = zone.y / TAILLE_TUILE;
        int yMax = (zone.y + zone.height - 1) / TAILLE_TUILE;

        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (estBloquant(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Debug : cases bloquantes en rouge semi-transparent
    public void rendu(Graphics2D g) {
        g.setColor(new Color(255, 0, 0, 100));
        for (int y = 0; y < HAUTEUR; y++) {
            for (int x = 0; x < LARGEUR; x++) {
                if (blocs[y][x] == 1) {
                    g.fillRect(x * TAILLE_TUILE, y * TAILLE_TUILE, TAILLE_TUILE, TAILLE_TUILE);
                }
            }
        }
    }

    // Debug : quadrillage avec les coordonnées "colonne,ligne" de chaque case
    public void renduGrille(Graphics2D g) {
        g.setColor(new Color(255, 255, 255, 60));
        for (int i = 0; i <= LARGEUR; i++) {
            g.drawLine(i * TAILLE_TUILE, 0, i * TAILLE_TUILE, HAUTEUR * TAILLE_TUILE);
        }
        for (int j = 0; j <= HAUTEUR; j++) {
            g.drawLine(0, j * TAILLE_TUILE, LARGEUR * TAILLE_TUILE, j * TAILLE_TUILE);
        }
        g.setColor(Color.WHITE);
        for (int j = 0; j < HAUTEUR; j++) {
            for (int i = 0; i < LARGEUR; i++) {
                g.drawString(i + "," + j, i * TAILLE_TUILE + 2, j * TAILLE_TUILE + 12);
            }
        }
    }
}
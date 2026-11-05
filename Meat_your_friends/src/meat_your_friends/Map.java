
package TileMapping;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Map {

    private int largeur = 20;
    private int hauteur = 20;
    private int tailleTuile = 32;

    private BufferedImage[] tuiles;

    // Matrice représentant les 20 lignes et les 20 colonnes de la carte
    private int[][] decor = new int[hauteur][largeur];

    public Map() {
        try {
            // Chargement de l'image complète
            BufferedImage tileset = ImageIO.read(
                getClass().getResource("images/map.png")
            );

            // Calcul du nombre de tuiles dans l'image
            int colonnes = tileset.getWidth() / tailleTuile;
            int lignes = tileset.getHeight() / tailleTuile;

            // Création du tableau contenant les 400 tuiles
            tuiles = new BufferedImage[colonnes * lignes];

            // Découpage de l'image en tuiles de 32 x 32 pixels
            for (int i = 0; i < tuiles.length; i++) {

                int x = (i % colonnes) * tailleTuile;
                int y = (i / colonnes) * tailleTuile;

                tuiles[i] = tileset.getSubimage(
                    x, y, tailleTuile, tailleTuile
                );
            }

            // Remplissage de la matrice avec les indices des tuiles
            for (int y = 0; y < hauteur; y++) {
                for (int x = 0; x < largeur; x++) {
                    decor[y][x] = y * largeur + x;
                }
            }

        } catch (IOException | IllegalArgumentException ex) {
            Logger.getLogger(Map.class.getName())
                  .log(Level.SEVERE, "Erreur de chargement de la carte", ex);
        }
    }

    public void miseAJour() {

    }

    public void rendu(Graphics2D contexte) {

        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {

                int numeroTuile = decor[y][x];

                contexte.drawImage(
                    tuiles[numeroTuile],
                    x * tailleTuile,
                    y * tailleTuile,
                    null
                );
            }
        }
    }
}

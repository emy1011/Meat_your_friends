package Aymeric;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;


public class Map {
    
    

    public static final int TAILLE_TUILE = 32;   // doit correspondre au fichier (32 32)
    private static final String FICHIER_NIVEAU = "/resources/texte_map.txt";


    private final Niveau niveau = new Niveau(FICHIER_NIVEAU);
    private BufferedImage tileset;
    private int colonnesTileset;

    public Map() {
        if (niveau.getTailleTuileW() != TAILLE_TUILE || niveau.getTailleTuileH() != TAILLE_TUILE) {
            throw new IllegalStateException("TAILLE_TUILE (" + TAILLE_TUILE
                    + ") ne correspond pas au fichier");
        }
        chargerTileset();
        colonnesTileset = tileset.getWidth() / TAILLE_TUILE;
    }

    /** Cherche d'abord /resources/tileset.png, sinon le chemin écrit dans le fichier. */
    private void chargerTileset() {
        try (InputStream in = Map.class.getResourceAsStream("/resources/tileset.png")) {
            if (in != null) {
                tileset = ImageIO.read(in);
                return;
            }
            tileset = ImageIO.read(new File(niveau.getCheminTileset()));
        } catch (Exception e) {
            throw new RuntimeException("Tileset introuvable (ni /resources/tileset.png, ni "
                    + niveau.getCheminTileset() + ")", e);
        }
    }

    public Niveau getNiveau() { return niveau; }
    public int getLargeur()   { return niveau.getLargeur(); }
    public int getHauteur()   { return niveau.getHauteur(); }

    /** Dessine le sol puis le décor. Le calque de collision n'est pas dessiné. */
    public void rendu(Graphics2D g) {
        dessinerCalque(g, Niveau.CALQUE_SOL);
        dessinerCalque(g, Niveau.CALQUE_DECOR);
    }

    private void dessinerCalque(Graphics2D g, int calque) {
        for (int l = 0; l < getHauteur(); l++) {
            for (int c = 0; c < getLargeur(); c++) {
                int id = niveau.get(calque, l, c);
                if (id < 0) continue;   // case vide
                int sx = (id % colonnesTileset) * TAILLE_TUILE;
                int sy = (id / colonnesTileset) * TAILLE_TUILE;
                g.drawImage(tileset,
                        c * TAILLE_TUILE, l * TAILLE_TUILE,
                        (c + 1) * TAILLE_TUILE, (l + 1) * TAILLE_TUILE,
                        sx, sy, sx + TAILLE_TUILE, sy + TAILLE_TUILE, null);
            }
        }
    }
}
package TileMapping;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FenetreDeJeuProjet extends JFrame {

    // Passe à false pour masquer les collisions (version finale)
    private static final boolean DEBUG_COLLISIONS = false;
//    private static final boolean DEBUG_COLLISIONS = false;

    // Facteur d'agrandissement de l'affichage (1.5 = 50 % plus grand)
    private static final double ECHELLE = 1.5;

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;

    private Map carte;
    private CarteColision colisions;

    public FenetreDeJeuProjet() {

        // Initialisation de la fenêtre
        this.setTitle("Mon jeu - TileMapping");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Création des deux couches
        carte = new Map();                 // couche visuelle
        colisions = new CarteColision();   // couche de collision

        // Taille du framebuffer calculée à partir des constantes
        int largeurPx = CarteColision.LARGEUR * CarteColision.TAILLE_TUILE;
        int hauteurPx = CarteColision.HAUTEUR * CarteColision.TAILLE_TUILE;

        framebuffer = new BufferedImage(
            largeurPx, hauteurPx, BufferedImage.TYPE_INT_ARGB
        );

        contexte = framebuffer.createGraphics();

        // 1) La map visuelle
        carte.rendu(contexte);

        // 2) Les collisions en rouge (debug)
        if (DEBUG_COLLISIONS) {
            colisions.rendu(contexte);
        }

        contexte.dispose();

        // Taille affichée à l'écran (agrandissement)
        int largeurEcran = (int) (largeurPx * ECHELLE);
        int hauteurEcran = (int) (hauteurPx * ECHELLE);

        // Image agrandie, sans lissage pour garder un rendu net
        BufferedImage agrandie = new BufferedImage(
            largeurEcran, hauteurEcran, BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = agrandie.createGraphics();
        g2.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );
        g2.drawImage(framebuffer, 0, 0, largeurEcran, hauteurEcran, null);
        g2.dispose();

        jLabel1 = new JLabel();
        jLabel1.setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setVerticalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(agrandie));

        this.setContentPane(jLabel1);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
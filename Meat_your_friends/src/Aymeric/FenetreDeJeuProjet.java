package Aymeric;
//explication texte_map : 
//lignes 1 à 3 : largeur, hauteur, taille d'une tuile
// ligne 4 à 33 : sol (herbe, terre...)
//ligne 34 à 63 : décor (arbres, maisons, barrières...)
//ligne 64 à 93 : collision (1=bloqué, 0=libre)
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
//explication texte_map : 
//
public class FenetreDeJeuProjet extends JFrame {

    // Passe a true pour voir les collisions en rouge
    private static final boolean DEBUG_COLLISIONS = false;

    // Zoom maximal autorise (1.0 = taille native, 1.5 = comme avant)
    private static final double ECHELLE_MAX = 1.5;

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    public int LATITUDE_MAX = 1100;
public int LONGITUDE_MAX = 1300 ;
   

    private Jeu jeu;
    private CarteColision colisions;

    public FenetreDeJeuProjet() {

        this.setTitle("Meat your friends - TileMapping");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // La carte d'abord : les dimensions viennent du fichier de niveau
        jeu = new Jeu();
        colisions = new CarteColision(jeu.getCarte());

        LONGITUDE_MAX = jeu.getCarte().getLargeur() * Map.TAILLE_TUILE;
        LATITUDE_MAX = jeu.getCarte().getHauteur() * Map.TAILLE_TUILE;

        framebuffer = new BufferedImage(
            LONGITUDE_MAX , LATITUDE_MAX, BufferedImage.TYPE_INT_ARGB
        );

        contexte = framebuffer.createGraphics();

        jeu.rendu(contexte);

        if (DEBUG_COLLISIONS) {
            colisions.rendu(contexte);
        }

        contexte.dispose();

        // Zone utilisable de l'ecran (hors barre des taches)
        Rectangle ecran = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();

        // Marge pour la barre de titre et les bordures de la fenetre
        int margeW = 20;
        int margeH = 60;

        double echelleW = (ecran.width - margeW) / (double) LONGITUDE_MAX;
        double echelleH = (ecran.height - margeH) / (double) LATITUDE_MAX;
        double echelle = Math.min(ECHELLE_MAX, Math.min(echelleW, echelleH));

        int largeurEcran = (int) (LONGITUDE_MAX * echelle);
        int hauteurEcran = (int) (LATITUDE_MAX * echelle);

        BufferedImage agrandie = new BufferedImage(
            largeurEcran, hauteurEcran, BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = agrandie.createGraphics();
        g2.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR
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

    public int getLATITUDE_MAX() {
        return LATITUDE_MAX;
    }

    public int getLONGITUDE_MAX() {
        return LONGITUDE_MAX;
    }
    
    
}
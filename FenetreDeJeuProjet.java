

package TileMapping;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FenetreDeJeuProjet extends JFrame {

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;

    private Map carte;

    public FenetreDeJeuProjet() {

        // Initialisation de la fenêtre
        this.setTitle("Mon jeu - TileMapping");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Création de la carte
        carte = new Map();

        // Création du framebuffer de 640 x 640 pixels
        framebuffer = new BufferedImage(
            640, 640, BufferedImage.TYPE_INT_ARGB
        );

        // Récupération du contexte graphique
        contexte = framebuffer.createGraphics();

        // Dessin de la carte
        carte.rendu(contexte);

        // Libération du contexte graphique
        contexte.dispose();

        // Création du JLabel
        jLabel1 = new JLabel();
        jLabel1.setPreferredSize(new Dimension(640, 640));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setVerticalAlignment(SwingConstants.CENTER);

        // Affichage de la carte
        jLabel1.setIcon(new ImageIcon(framebuffer));

        this.setContentPane(jLabel1);

        // Ajustement de la fenêtre
        this.pack();

        // Centrage
        this.setLocationRelativeTo(null);

        // Affichage
        this.setVisible(true);
    }
}

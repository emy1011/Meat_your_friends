/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AvatarEtItem;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Ours extends Animal {

    private int delaiAttaque = 0;
    private int degats = 100;

    public Ours() {
        super();
        this.vie = 300;
        this.vitesseDeplacement = 1; // Correspond au + 1 de ton switch
        this.dist = 75;              // Distance d'attaque de l'ours
        this.nom_item = "brin";        // Item lâché à la mort
        this.xmin=300;
        this.xmax=600;
        this.ymin=300;
        this.ymax=600;
        this.barre_de_vie = this.vie;

        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/ours.png"));
        } catch (IOException ex) {
            Logger.getLogger(Ours.class.getName()).log(Level.SEVERE, null, ex);
        }
        placer();
    }

    @Override
    public void placer() {
        this.x = 300 + Math.random() * 300;
        this.y = 300 + Math.random() * 300;
    }

    @Override
    public void miseAJour(Avatar av) {
        // Gestion du respawn gérée via la classe Animal si ramasse est vrai
        if (ramasse) { // Note : rend "ramasse" protected dans Animal si besoin
            delaiRespawn--;
            if (delaiRespawn <= 0) {
                placer();
                ramasse = false;
                barre_de_vie = 300;
            }
            return;
        }

        if (av.estMort()) {
            deplacementAnimal(av);
            return;
        }

        if (delaiAttaque > 0) {
            delaiAttaque--;
        }

        double dx = (av.getX() + av.getLargeur() / 2) - (x + getLargeur() / 2);
        double dy = (av.getY() + av.getLargeur() / 2) - (y + getHauteur() / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);

        // L'Avatar est à portée de détection
        if (distance <= 200) {
            // L'ours est assez proche pour attaquer
            if (distance <= 50) {
                if (delaiAttaque == 0) {
                    av.recevoirDegats(degats);
                    delaiAttaque = 60;
                }
            } else {
                // L'ours poursuit l'Avatar
                x += (dx / distance) * 2;
                y += (dy / distance) * 2;
            }
        } else {
            // Déplacement aléatoire (utilise la méthode de la classe mère avec des limites personnalisées si besoin)
            deplacementAnimal(av);
        }
    }
}
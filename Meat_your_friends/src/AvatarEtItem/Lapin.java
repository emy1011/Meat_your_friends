/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AvatarEtItem;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Lapin extends Animal {

    public Lapin() {
        super();
        this.vitesseDeplacement = 8; // Vitesse propre au lapin
        this.vie = 30;               // Points de vie
        this.dist = 50;              // Distance d'attaque
        this.nom_item = "Viande de lapin";     // Ou viande de lapin selon ton jeu
        this.compteurDeplacement=30;
        this.xmin=0;
        this.xmax=300;
        this.ymin=0;
        this.ymax=300;
        this.barre_de_vie = this.vie;

        try {
            this.sprite = ImageIO.read(getClass().getResource("/resources/lapin.png")); // Adapte le chemin de ton image
        } catch (IOException e) {
            e.printStackTrace();
        }
        placer();
    }
}

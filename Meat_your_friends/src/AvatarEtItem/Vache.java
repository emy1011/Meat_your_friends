/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AvatarEtItem; //vache

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Vache extends Animal {

    public Vache() {
        super();
        this.vitesseDeplacement = 1; // Vitesse propre au lapin
        this.vie = 90;               // Points de vie
        this.dist = 100;              // Distance d'attaque
        this.nom_item = "Steak";     // Ou viande de lapin selon ton jeu
        this.compteurDeplacement=20;
        this.xmin=0;
        this.xmax=300;
        this.ymin=0;
        this.ymax=300;
        this.barre_de_vie = this.vie;

        try {
            this.sprite = ImageIO.read(getClass().getResource("/resources/vache.png")); // Adapte le chemin de ton image
        } catch (IOException e) {
            e.printStackTrace();
        }
        placer();
    }
}

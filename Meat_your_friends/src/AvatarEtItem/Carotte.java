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


public class Carotte extends Legume {

    public Carotte() {
        super();
        this.nom_item = "Carotte";     // Ou viande de lapin selon ton jeu
        this.xmin=0;
        this.xmax=300;
        this.ymin=0;
        this.ymax=300;

        try {
            this.sprite = ImageIO.read(getClass().getResource("/resources/carotte.png")); // Adapte le chemin de ton image
        } catch (IOException e) {
            e.printStackTrace();
        }
        placer();
    }
}
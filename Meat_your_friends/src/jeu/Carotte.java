/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Carotte {

    protected BufferedImage sprite;
    protected double x, y;
    private boolean ramasse;
    private int delaiRespawn = 0;

    public Carotte() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/carotte.png"));
        } catch (IOException ex) {
            Logger.getLogger(Carotte.class.getName()).log(Level.SEVERE, null, ex);
        }
        placer();
    }

    public void miseAJour(Avatar av) {
        if (ramasse) {
            delaiRespawn--;

            if (delaiRespawn <= 0) {
                placer();
            }

            return;
        }

        verifierRamassage(av);
    }

    public void rendu(Graphics2D contexte) {
        if (!ramasse && sprite != null) {
            contexte.drawImage(sprite, (int) x, (int) y, null);
        }
    }

    public void placer() {
        this.x = Math.random() * 960;
        this.y = Math.random() * 960;
        this.ramasse = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLargeur() {
        return sprite.getHeight();
    }

    public double getHauteur() {
        return sprite.getWidth();
    }

    public boolean estRamasse() {
        return ramasse;
    }

    public void verifierRamassage(Avatar av) {
        if (ramasse || sprite == null) {
            return;
        }

        boolean collision
                = av.getX() < x + getLargeur()
                && av.getX() + av.getLargeur() > x
                && av.getY() < y + getHauteur()
                && av.getY() + av.getHauteur() > y;

        if (collision) {
            av.getInventaire().ajouterItem(new Item("Carotte"));

            // La carotte disparaît
            ramasse = true;
            delaiRespawn = 120;
        }
    }
}

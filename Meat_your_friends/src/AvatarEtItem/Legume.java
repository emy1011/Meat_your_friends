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

/**
 *
 * @author mlemestr
 */
public class Legume {
    

    protected BufferedImage sprite;
    protected double x, y;
    private boolean ramasse;
    private int delaiRespawn = 0;
    protected String nom_item;
    protected int xmin;
    protected int xmax;
    protected int ymin;
    protected int ymax;
    

    public Legume() {
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
        this.x = xmin + Math.random() * (xmax - xmin);
        this.y = ymin + Math.random() * (ymax - ymin);
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
            av.getInventaire().ajouterItem(new Item(nom_item));

            // La carotte disparaît
            ramasse = true;
            delaiRespawn = 120;
        }
    }
}

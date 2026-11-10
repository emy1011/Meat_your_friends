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

public class Vache {

    protected BufferedImage sprite;
    protected double x, y;
    private boolean ramasse;
    private int delaiRespawn = 0;
    private int direction = 0;
    private int compteurDeplacement = 0;
    private int vie = 90;

    public Vache() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/vache.png"));
        } catch (IOException ex) {
            Logger.getLogger(Vache.class.getName()).log(Level.SEVERE, null, ex);
        }
        placer();
    }

    public void miseAJour(Avatar av) {
        if (ramasse) {
            delaiRespawn--;

            if (delaiRespawn <= 0) {
                placer();
                ramasse = false;
                vie = 90;
            }

            return;
        }

        deplacementVache();
    }

    public void rendu(Graphics2D contexte) {
        if (vie > 0 && sprite != null) {
            contexte.drawImage(sprite, (int) x, (int) y, null);
        }
    }

    public void placer() {
        this.x = Math.random() * 300;
        this.y = Math.random() * 300;
    }

    public void deplacementVache() {
        // Choisir une nouvelle direction toutes les 20 mises à jour
        if (compteurDeplacement == 0) {
            direction = (int) (Math.random() * 4);
            compteurDeplacement = 20;
        }

        double ancienX = x;
        double ancienY = y;

        switch (direction) {
            case 0:
                x += 1;
                break;
            case 1:
                x -= 1;
                break;
            case 2:
                y += 1;
                break;
            case 3:
                y -= 1;
                break;
        }

        // Si la vache sort de la zone, annuler le mouvement
        if (x < 0 || x > 300 || y < 0 || y > 300) {
            x = ancienX;
            y = ancienY;

            // Forcer le choix d'une autre direction
            compteurDeplacement = 0;
        } else {
            compteurDeplacement--;
        }
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

    public void attaquer(Avatar av, int sourisX, int sourisY) {

        if (vie <= 0 || ramasse) {
            return;
        }

        // Vérifier que le clic est sur la vache
        boolean clicSurVache
                = sourisX >= x
                && sourisX <= x + getLargeur()
                && sourisY >= y
                && sourisY <= y + getHauteur();

        // Distance entre le cuisinier et la vache
        double dx = (av.getX() + av.getLargeur() / 2)
                - (x + getLargeur() / 2);

        double dy = (av.getY() + av.getHauteur() / 2)
                - (y + getHauteur() / 2);

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (clicSurVache && distance <= 50) {
            vie -= 30;

            if (vie < 0) {
                vie = 0;
            }

            if (vie == 0) {
                av.getInventaire().ajouterItem(new Item("Steak"));
                ramasse = true;
                delaiRespawn = 120;
            }
        }
    }

}

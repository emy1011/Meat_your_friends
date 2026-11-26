/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AvatarEtItem;

/**
 *
 * @author mlemestr
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Animal {

    protected BufferedImage sprite;
    protected double x, y;
    protected boolean ramasse;
    protected int delaiRespawn = 0;
    private int direction = 0;
    protected int compteurDeplacement;
    protected int vie;
    protected int vitesseDeplacement;
    protected int barre_de_vie;
    private int deplacement=0;
    protected int dist;
    protected String nom_item;
    protected int xmin;
    protected int xmax;
    protected int ymin;
    protected int ymax;
    

    public Animal() {
         barre_de_vie = vie;

    }

    public void miseAJour(Avatar av) {
        if (ramasse) {
            delaiRespawn--;

            if (delaiRespawn <= 0) {
                placer();
                ramasse = false;
                barre_de_vie = vie;
            }

            return;
        }

        deplacementAnimal(av);
    }

    public void rendu(Graphics2D contexte) {
        if (barre_de_vie > 0 && sprite != null) {
            contexte.drawImage(sprite, (int) x, (int) y, null);
        }
    }

    public void placer() {
        this.x = Math.random() * 300;
        this.y = Math.random() * 300;
    }

    public void deplacementAnimal(Avatar av) {
        // Choisir une nouvelle direction toutes les 20 mises à jour
        if ( deplacement == 0) {
            direction = (int) (Math.random() * 4);
            deplacement = compteurDeplacement;
        }

        double ancienX = x;
        double ancienY = y;

        switch (direction) {
            case 0:
                x += vitesseDeplacement;
                break;
            case 1:
                x -= vitesseDeplacement;
                break;
            case 2:
                y += vitesseDeplacement;
                break;
            case 3:
                y -= vitesseDeplacement;
                break;
        }

        // Si la vache sort de la zone, annuler le mouvement
        if (x < xmin || x > xmax || y < ymin || y > ymax || this.collision(av)) {
            x = ancienX;
            y = ancienY;

            // Forcer le choix d'une autre direction
            deplacement = 0;
        } else {
            deplacement--;
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
        public boolean collision(Avatar av){
    return av.getX() < x + getLargeur()
                && av.getX() + av.getLargeur() > x
                && av.getY() < y + getHauteur()
                && av.getY() + av.getHauteur() > y;
 }



    public void attaquer(Avatar av, int sourisX, int sourisY) {

        if (barre_de_vie <= 0 || ramasse) {
            return;
        }

        // Vérifier que le clic est sur la vache
        boolean clicSurAnimal
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

        if (clicSurAnimal && distance <= dist) {
            barre_de_vie -= 30;

            if (barre_de_vie < 0) {
                barre_de_vie = 0;
            }

            if (barre_de_vie == 0) {
                av.getInventaire().ajouterItem(new Item(nom_item));
                ramasse = true;
                delaiRespawn = 120;
            }
        }
    }

}


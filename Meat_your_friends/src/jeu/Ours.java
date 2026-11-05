/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeu;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Ours {

    protected BufferedImage sprite;
    protected double x, y;
    private boolean ramasse;
    private int delaiRespawn = 0;
    private int direction = 0;
    private int compteurDeplacement = 0;
    private int vie = 300;
    private int delaiAttaque = 0;
    private int degats = 100;

    public Ours() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/ours.png"));
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
            vie = 300;
        }
        return;
    }

    if (av.estMort()) {
        deplacementOurs();
        return;
    }

    if (delaiAttaque > 0) {
        delaiAttaque--;
    }

    double dx = (av.getX() + av.getLargeur() / 2)
            - (x + getLargeur() / 2);

    double dy = (av.getY() + av.getHauteur() / 2)
            - (y + getHauteur() / 2);

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

        // Déplacement aléatoire
        deplacementOurs();
    }
}
    

    public void rendu(Graphics2D contexte) {
        if (vie > 0 && sprite != null) {
            contexte.drawImage(sprite, (int) x, (int) y, null);
        }
    }

    public void placer() {
        this.x = 300+Math.random() * 300;
        this.y = 300+Math.random() * 300;
    }

    public void deplacementOurs() {
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
        if (x < 300 || x > 600 || y < 300 || y > 600) {
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
        boolean clicSurOurs
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

        if (clicSurOurs && distance <= 75) {
            vie -= 30;

            if (vie < 0) {
                vie = 0;
            }

            if (vie == 0) {
                av.getInventaire().ajouterItem(new Item("brin"));
                ramasse = true;
                delaiRespawn = 120;
            }
        }
    }


}


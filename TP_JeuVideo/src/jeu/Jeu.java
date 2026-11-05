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

/**
 *
 * @author ejolivet
 */
public class Jeu {

    private BufferedImage decor;
    private int score;
    private Banane uneBanane;
    private Avatar avatar;

    public Jeu() {
        try {
            this.decor = ImageIO.read(getClass().getResource("../resources/jungle.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.score = 0;
        this.uneBanane = new Banane();
        this.avatar = new Avatar();
    }

    public void rendu(Graphics2D contexte) {

        contexte.drawImage(this.decor, 0, 0, null);// 1. Rendu du décor
        contexte.drawString("Score : " + score, 10, 20);// 3. Rendu des textes
        this.uneBanane.rendu(contexte);        // 2. Rendu des sprites
        this.avatar.rendu(contexte);

        

        
    }

    public void miseAJour() {
        this.uneBanane.miseAJour();// 2. Mise à jour des autres éléments (objets, monstres, etc.)
        this.avatar.miseAJour();// 1. Mise à jour de l’avatar en fonction des commandes des joueurs
        
        if (collisionEntreAvatarEtBanane()){// 3. Gérer les interactions (collisions et autres règles)
            score++;
            uneBanane.lancer(); 
        }
        
        
        
    }

    public boolean estTermine() {
        return this.uneBanane.getY() > 400;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public boolean collisionEntreAvatarEtBanane() {
        if ((uneBanane.getX() >= avatar.getX() + avatar.getLargeur()) // trop à droite
                || (uneBanane.getX() + uneBanane.getLargeur() <= avatar.getX()) // trop à gauche
                || (uneBanane.getY() >= avatar.getY() + avatar.getHauteur()) // trop en bas
                || (uneBanane.getY() + uneBanane.getHauteur() <= avatar.getY())) { // trop en haut
            return false;
        } else {
            return true;
        }
    }

}

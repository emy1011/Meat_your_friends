/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package meat_your_friends;

import jeu.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;

/**
 *
 * @author mlemestr
 */
public class Jeu {

    private BufferedImage decor;
    private int score;
    private Carotte uneCarotte;
    private Avatar av;
    private Vache uneVache;
    private Ours unOurs;
    private Champi unChampi;
    private Tomate uneTomate; 
    private Lapin unLapin; 

    public Jeu() {
        try {
            this.decor = ImageIO.read(getClass().getResource("../resources/ferme.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.score = 0;
        this.uneCarotte = new Carotte();
        this.av = new Avatar();
        this.uneVache = new Vache();
        this.unOurs=new Ours();
        this.unChampi= new Champi();
        this.uneTomate = new Tomate (); 
        this.unLapin = new Lapin();
    }

    public void clicSouris(MouseEvent e) {
        uneVache.attaquer(av, e.getX(), e.getY());
        unOurs.attaquer(av, e.getX(), e.getY());
        unLapin.attaquer(av,e.getX(),e.getY());
    }
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.decor, 0, 0, null);
        contexte.drawString("Score : " + score, 10, 20);
        this.uneCarotte.rendu(contexte);
        this.uneVache.rendu(contexte);
        this.unOurs.rendu(contexte);
        this.unChampi.rendu(contexte);
        this.av.rendu(contexte);
        this.uneTomate.rendu(contexte);
        this.unLapin.rendu(contexte);
        this.av.getInventaire().rendu(
                contexte,
                960,
                960
        );
    }
 
    public void miseAJour() {
        this.uneCarotte.miseAJour(this.av);
        this.uneVache.miseAJour(this.av);
        this.unOurs.miseAJour(this.av);
        this.unChampi.miseAJour(this.av);
        this.uneTomate.miseAJour(this.av);
        this.unLapin.miseAJour(this.av);
        this.av.miseAJour();

// 1. Mise à jour de l’avatar en fonction des commandes des joueurs
// 2. Mise à jour des autres éléments (objets, monstres, etc.)
// 3. Gérer les interactions (collisions et autres règles)
    }

    public boolean estTermine() {
        //return this.uneBanane.getY() > 400;
        return this.score > 1000;
    }

    public Avatar getAvatar() {
        return av;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AvatarEtItem;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import outils.SingletonJDBC;

/**
 * Exemple de classe lutin
 *
 * @author manon.lemestre
 */
public class Avatar {

    protected BufferedImage sprite;
    protected double x, y;
    private boolean toucheGauche, toucheDroite, toucheHaut, toucheBas;
    private Inventaire inventaire;
    private int vie = 300;
    private boolean mort = false;
    private int delaiRespawn = 0;
    //private Map map;

    public Avatar() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/cuisto.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 170;
        this.y = 320;
        this.toucheGauche = false;
        this.toucheDroite = false;
        this.toucheBas = false;
        this.toucheHaut = false;
        this.inventaire = new Inventaire();
    }

    public void miseAJour() {

        if (mort) {
            miseAJourRespawn();
            return;
        }

        if (toucheGauche) {
            x -= 5;
        }

        if (toucheDroite) {
            x += 5;
        }

        if (x > 960 - sprite.getWidth()) {
            x = 960 - sprite.getWidth();
        }

        if (x < 0) {
            x = 0;
        }

        if (toucheBas) {
            y += 5;
        }

        if (toucheHaut) {
            y -= 5;
        }

        if (y > 960 - sprite.getHeight()) {
            y = 960 - sprite.getHeight();
        }

        if (y < 0) {
            y = 0;
        }
    }

    public void miseAJourRespawn() {

        if (mort) {
            delaiRespawn--;

            if (delaiRespawn <= 0) {
                x = 170;
                y = 320;
                vie = 300;
                mort = false;
            }
        }
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void rendu(Graphics2D contexte) {
        if (!mort && vie > 0 && sprite != null) {
            contexte.drawImage(sprite, (int) x, (int) y, null);
        }
    }

    public void setToucheGauche(boolean etat) {
        this.toucheGauche = etat;
    }

    public void setToucheDroite(boolean etat) {
        this.toucheDroite = etat;
    }

    public void setToucheHaut(boolean etat) {
        this.toucheHaut = etat;
    }

    public void setToucheBas(boolean etat) {
        this.toucheBas = etat;
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

    public void recevoirDegats(int degats) {

        if (mort) {
            return;
        }

        vie -= degats;

        if (vie <= 0) {
            vie = 0;
            mort = true;
            delaiRespawn = 120;
            perdreInventaire();
        }
    }

    public void perdreInventaire() {
        inventaire.vider();
    }

    public boolean estMort() {
        return mort;
    }
    

    //private void deplacer(String pseudo,double dLat, double dLon) {
    //    try {
    //        Connection connexion = SingletonJDBC.getInstance().getConnection();
    //        PreparedStatement requete = connexion.prepareStatement(
    //                "UPDATE dresseurs SET "
    //                + "latitude = LEAST(?, GREATEST(?, latitude + ?)), "
    //                + "longitude = LEAST(?, GREATEST(?, longitude + ?)) "
    //                + "WHERE pseudo = ?");
    //        requete.setDouble(1, map.LATITUDE_MAX);
    //        requete.setDouble(2, map.LATITUDE_MIN);
    //        requete.setDouble(3, dLat);
    //        requete.setDouble(4, map.LONGITUDE_MAX);
    //        requete.setDouble(5, map.LONGITUDE_MIN);
    //        requete.setDouble(6, dLon);
    //        requete.setString(7, pseudo);
    //        requete.executeUpdate();
    //        requete.close();
    //    } catch (SQLException ex) {
    //        ex.printStackTrace();
    //    }
    //}
    
    private void CreationJoueur(String pseudo) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO Joueurs VALUES (?, ?, ?, NOW(),0)");
            
            requete.setString(1, pseudo);
            requete.setDouble(2,10.0);
            requete.setDouble(3, 10.0);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

}

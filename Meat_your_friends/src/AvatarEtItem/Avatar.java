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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import outils.SingletonJDBC;
import Aymeric.FenetreDeJeuProjet;

/**
 *
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
    private FenetreDeJeuProjet map;
    private String nom;
    private boolean positionModifiee = false;
    private long derniereSync = 0;
    private static final long DELAI_SYNC_MS = 200;

    private Connection connexion; //état de connexion

    public Avatar() {

        this.connexion = SingletonJDBC.getInstance().getConnection();
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/cuisto.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 170;
        this.y = 200;
        this.toucheGauche = false;
        this.toucheDroite = false;
        this.toucheBas = false;
        this.toucheHaut = false;
        this.inventaire = new Inventaire();

        this.nom = nom;
        this.map = map;

    }
// cherche les cases avec des hitbox 
    private boolean collisionCarte(Jeu jeu, double nx, double ny) {

        int taille = Aymeric.Map.TAILLE_TUILE;

        int colMin = (int) (nx / taille);
        int colMax = (int) ((nx + getLargeur() - 1) / taille);

        int ligneMin = (int) (ny / taille);
        int ligneMax = (int) ((ny + getHauteur() - 1) / taille);

        for (int ligne = ligneMin; ligne <= ligneMax; ligne++) {
            for (int col = colMin; col <= colMax; col++) {

                if (jeu.getColisions().estSolide(ligne, col)) {
                    return true;
                }
            }
        }

        return false;

       

    }

    private void essayerDeplacer(Jeu jeu, double dx, double dy) {

    double nx = x + dx;
    double ny = y + dy;

    if (collisionCarte(jeu, nx, ny)) {
        return;
    }

    x = nx;
    y = ny;
}
    public void miseAJour(Jeu jeu) {

        if (mort) {
            miseAJourRespawn();
            return;
        }

        if (toucheGauche) {
            deplacerGauche(jeu);
        }

        if (toucheDroite) {
            deplacerDroite(jeu);
        }

//        if (x > 960 - sprite.getWidth()) {
//            x = 960 - sprite.getWidth();
//        }
//
//        if (x < 0) {
//            x = 0;
//        }
        if (toucheBas) {
            deplacerBas(jeu);
        }

        if (toucheHaut) {
            deplacerHaut(jeu);
        }

//        if (y > 960 - sprite.getHeight()) {
//            y = 960 - sprite.getHeight();
//        }
//
//        if (y < 0) {
//            y = 0;
//        }
        long maintenant = System.currentTimeMillis();
        // actualisation des Sync
        if (positionModifiee && maintenant - derniereSync >= DELAI_SYNC_MS) {
            positionModifiee = false;
            derniereSync = maintenant;
        }
    }
    
    
    
    
    

//    public void deplacerDroite(Jeu jeu) {
//        x += 5;
//        if (jeu.collisionAnimal()) {
//            x -= 5;
//        }
//    }
//
//    public void deplacerGauche(Jeu jeu) {
//        x -= 5;
//        if (jeu.collisionAnimal()) {
//            x += 5;
//        }
//    }
//
//    public void deplacerHaut(Jeu jeu) {
//        y -= 5;
//        if (jeu.collisionAnimal()) {
//            y += 5;
//        }
//    }
//
//    public void deplacerBas(Jeu jeu) {
//        y += 5;
//        if (jeu.collisionAnimal()) {
//            y -= 5;
//        }
//    }
//    
  public void deplacerDroite(Jeu jeu) {
    essayerDeplacer(jeu, 5, 0);
}

public void deplacerGauche(Jeu jeu) {
    essayerDeplacer(jeu, -5, 0);
}

public void deplacerHaut(Jeu jeu) {
    essayerDeplacer(jeu, 0, -5);
}

public void deplacerBas(Jeu jeu) {
    essayerDeplacer(jeu, 0, 5);
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
        return sprite.getWidth();

    }

    public double getHauteur() {
        return sprite.getHeight();

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

    public boolean collision(Animal an) {
        return an.getX() < x + getLargeur()
                && an.getX() + an.getLargeur() > x
                && an.getY() < y + getHauteur()
                && an.getY() + an.getHauteur() > y;
    }

    public void perdreInventaire() {
        inventaire.vider();
    }

    public boolean estMort() {
        return mort;
    }

    private void deplacer(String pseudo, double dLat, double dLon) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();
            PreparedStatement requete = connexion.prepareStatement(
                    "UPDATE Joueurs SET "
                    + "latitude = LEAST(?, GREATEST(?, latitude + ?)), "
                    + "longitude = LEAST(?, GREATEST(?, longitude + ?)) "
                    + "WHERE pseudo = ?");
            requete.setDouble(1, map.LATITUDE_MAX);
            requete.setDouble(2, 0);
            requete.setDouble(3, dLat);
            requete.setDouble(4, map.LONGITUDE_MAX);
            requete.setDouble(5, 0);
            requete.setDouble(6, dLon);
            requete.setString(7, pseudo);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void CreationJoueur(String pseudo) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO Joueurs VALUES (?, ?, ?, NOW(),0)");

            requete.setString(1, pseudo);
            requete.setDouble(2, 10.0);
            requete.setDouble(3, 10.0);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void viderJoueurs() {
        Connection connexion = SingletonJDBC.getInstance().getConnection();

        try (PreparedStatement requete = connexion.prepareStatement("DELETE FROM Joueurs")) {
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void supprimerJoueur(String pseudo) {
        if (pseudo == null || pseudo.trim().isEmpty()) {
            return;
        }
        String sql = "DELETE FROM Joueurs WHERE pseudo = ?";
        Connection connexion = SingletonJDBC.getInstance().getConnection();

        try (PreparedStatement requete = connexion.prepareStatement(sql)) {
            requete.setString(1, pseudo.trim());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void closeTable() {
        try {
            this.connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}

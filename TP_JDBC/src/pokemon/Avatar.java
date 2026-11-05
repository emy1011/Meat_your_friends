/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import outils.OutilsJDBC;
import outils.SingletonJDBC;

/**
 * Exemple de classe avatar
 *
 * @author guillaume.laurent
 */
public class Avatar {

    private boolean toucheHaut, toucheBas, toucheDroite, toucheGauche;
    private String pseudo;
    protected Carte laCarte;
    private static final double PAS = 0.00010; // correspond à 0.00001°

    public Avatar(Carte laCarte) {
        this.laCarte = laCarte;
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheDroite = false;
        this.toucheGauche = false;
        this.pseudo = "ondine";
        

    }

    public void miseAJour() {

        if (this.toucheHaut) {
            System.out.println("Déplacement de " + this.pseudo + " vers le haut");
            deplacer(+PAS, 0);


        }
        if (this.toucheBas) {
            System.out.println("Déplacement de " + this.pseudo + " vers le bas");

            deplacer(-PAS, 0);
        }
        if (this.toucheDroite) {
            System.out.println("Déplacement de " + this.pseudo + " vers la droite");

            deplacer(0, +PAS);
        }
        if (this.toucheGauche) {
            System.out.println("Déplacement de " + this.pseudo + " vers la gauche");

            deplacer(0, -PAS);
        }
        
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheDroite = false;
        this.toucheGauche = false;
    }
    
    private void deplacer(double dLat, double dLon) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();
            PreparedStatement requete = connexion.prepareStatement(
                    "UPDATE dresseurs SET "
                    + "latitude = LEAST(?, GREATEST(?, latitude + ?)), "
                    + "longitude = LEAST(?, GREATEST(?, longitude + ?)) "
                    + "WHERE pseudo = ?");
            requete.setDouble(1, Carte.LATITUDE_MAX);
            requete.setDouble(2, Carte.LATITUDE_MIN);
            requete.setDouble(3, dLat);
            requete.setDouble(4, Carte.LONGITUDE_MAX);
            requete.setDouble(5, Carte.LONGITUDE_MIN);
            requete.setDouble(6, dLon);
            requete.setString(7, pseudo);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rendu(Graphics2D contexte) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();

            PreparedStatement requete = connexion.prepareStatement("SELECT latitude, longitude FROM dresseurs WHERE pseudo = ?");
            requete.setString(1, pseudo);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()) {
                
                double latitude = resultat.getDouble("latitude");
                double longitude = resultat.getDouble("longitude");
                //System.out.println(pseudo + " = (" + latitude + "; " + longitude + ")");

                int x = laCarte.longitudeEnPixel(longitude);
                int y = laCarte.latitudeEnPixel(latitude);
                contexte.setColor(Color.red);
                contexte.drawOval(x - 7, y - 7, 14, 14);
                //contexte.drawString(pseudo, x + 8, y - 8);
            }
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setToucheHaut(boolean etat) {
        this.toucheHaut = etat;
    }

    public void setToucheBas(boolean etat) {
        this.toucheBas = etat;
    }

    public void setToucheGauche(boolean etat) {
        this.toucheGauche = etat;
    }

    public void setToucheDroite(boolean etat) {
        this.toucheDroite = etat;
    }

    public String getPseudo(){
        return this.pseudo;
    }

}

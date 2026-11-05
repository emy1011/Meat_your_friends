/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            
             try {

            Connection connexion = DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/tp_jdbc", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("UPDATE dresseurs SET latitude = ? WHERE pseudo = ?");
            requete.setDouble(1, 47.251);
            requete.setString(3, pseudo);
            System.out.println(requete);
            int nombreDeModifications = requete.executeUpdate();
            System.out.println(nombreDeModifications + " enregistrement(s) modifie(s)");

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        }
        if (this.toucheBas) {
            System.out.println("Déplacement de " + this.pseudo + " vers le bas");

            // ajouter une requete ici...
        }
        if (this.toucheDroite) {
            System.out.println("Déplacement de " + this.pseudo + " vers la droite");

            // ajouter une requete ici...
        }
        if (this.toucheGauche) {
            System.out.println("Déplacement de " + this.pseudo + " vers la gauche");

            // ajouter une requete ici...
        }
        
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheDroite = false;
        this.toucheGauche = false;
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

}

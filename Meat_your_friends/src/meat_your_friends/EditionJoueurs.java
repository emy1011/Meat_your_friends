/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package meat_your_friends;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ejolivet
 */
public class EditionJoueurs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            Connection connexion = DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/2026-2027_s1_vs1_tp2_meat_your_friends ", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("UPDATE joueurs SET latitude = ?, longitude = ? WHERE pseudo = ?");
            //requete.setDouble(1, 47.251);
            //requete.setDouble(2, 5.994);
            System.out.println(requete);
            int nombreDeModifications = requete.executeUpdate();
            System.out.println(nombreDeModifications + " enregistrement(s) modifie(s)");

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}

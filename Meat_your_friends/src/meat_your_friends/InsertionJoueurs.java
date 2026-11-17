/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package meat_your_friends;

import outils.OutilsJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ejolivet
 */
public class InsertionJoueurs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         try {

            Connection connexion = DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/tp_jdbc", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("INSERT INTO dresseurs VALUES (?, ?, ?, ?, ?, NOW() )");
            requete.setString(1, "paul");
            requete.setString(2, "paul@ens2m.org");
            requete.setString(3, OutilsJDBC.MD5("1"));
            requete.setDouble(4, 47.6767);
            requete.setDouble(5, 5.6767);
            System.out.println(requete);
            int nombreDAjouts = requete.executeUpdate();
            System.out.println(nombreDAjouts + " enregistrement(s) ajoute(s)");

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}

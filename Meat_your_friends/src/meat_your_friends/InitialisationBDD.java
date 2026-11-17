/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meat_your_friends;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import outils.OutilsJDBC;



/**
 *
 * @author guillaume.laurent
 */
public class InitialisationBDD {

    public static void main(String[] args) {

        try {

            Connection connexion =  DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/2026-2027_s1_vs1_tp2_meat_your_friends", "etudiant", "YTDTvj9TR3CDYCmP");

            Statement statement = connexion.createStatement() ;
   
            
            ResultSet resultat3 = statement.executeQuery("SELECT * FROM Joueurs");
            ResultSet resultat1 = statement.executeQuery("SELECT * FROM Animaux");
            ResultSet resultat2 = statement.executeQuery("SELECT * FROM objets");
            OutilsJDBC.afficherResultSet(resultat1);
            OutilsJDBC.afficherResultSet(resultat2);
            OutilsJDBC.afficherResultSet(resultat3);
            
          
            statement.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import outils.OutilsJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author guillaume.laurent
 */
public class TestSelectAll_1 {

    public static void main(String[] args) { // donne tous les pokemons donc la latitude et la longitude sont à moins de 0.001° de (47.250°;5.992°)

        try {

            Connection connexion = DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/tp_jdbc", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("SELECT * FROM pokemons WHERE latitude <= 47.251 AND latitude >= 47.249 AND longitude <= 5.993 AND longitude <= 5.991 ");
            System.out.println(requete);
            ResultSet resultat = requete.executeQuery();
            OutilsJDBC.afficherResultSet(resultat);

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}

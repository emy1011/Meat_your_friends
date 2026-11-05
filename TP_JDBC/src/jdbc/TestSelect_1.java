/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author guillaume.laurent
 */
public class TestSelect_1 {

    public static void main(String[] args) { // donne tous les pokemons appartenant a sacha

        try {

            Connection connexion = DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/tp_jdbc", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("SELECT id, espece, latitude, longitude FROM pokemons WHERE proprietaire = 'sacha'");
            System.out.println(requete);
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) {
                String espece = resultat.getString("espece");
                double id = resultat.getDouble("id");
                double latitude = resultat.getDouble("latitude");
                double longitude = resultat.getDouble("longitude");
                System.out.println(espece + ",         id="+id+ ",         coordonees = (" + latitude + "; " + longitude + ")");
            }

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}

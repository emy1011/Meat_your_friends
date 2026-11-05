
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author mlemestr
 */
package jeu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedHashMap;
import java.util.Map;

public class Inventaire {

    private Map<String, Integer> objets = new LinkedHashMap<>();

    public void ajouterItem(Item item) {
        String nom = item.getNom();
        objets.put(nom, objets.getOrDefault(nom, 0) + 1);
    }
    
    public void vider() {
    objets.clear();
}

    public void rendu(Graphics2D g, int largeurEcran, int hauteurEcran) {

        int hauteurBarre = 80;
        int yBarre = hauteurEcran - hauteurBarre;

        // Fond de l'inventaire
        g.setColor(new Color(40, 40, 40));
        g.fillRect(0, yBarre, largeurEcran, hauteurBarre);

        // Bordure supérieure
        g.setColor(Color.WHITE);
        g.drawLine(0, yBarre, largeurEcran, yBarre);

        // Titre
        g.drawString("INVENTAIRE :", 15, yBarre + 20);

        int x = 15;
        int y = yBarre + 32;

        // Affichage des objets
        for (Map.Entry<String, Integer> objet : objets.entrySet()) {

            g.setColor(new Color(80, 80, 80));
            g.fillRoundRect(x, y, 120, 35, 10, 10);

            g.setColor(Color.WHITE);
            g.drawString(
                    objet.getKey() + " x" + objet.getValue(),
                    x + 10,
                    y + 22
            );

            x += 130;
        }
    }
}

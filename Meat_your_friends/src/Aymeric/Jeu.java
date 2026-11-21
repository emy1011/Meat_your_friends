package Aymeric;

import java.awt.Graphics2D;

public class Jeu {

    private Map carte;

    public Jeu() {
        this.carte = new Map();
    }

    public Map getCarte() {
        return carte;
    }

    public void miseAJour() {
        // rien à mettre à jour pour l'instant (carte statique)
        // c'est ici que tu appelleras les joueurs, ennemis, etc.
    }

    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte);
    }
}

package Aymeric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Niveau {

    public static final int CALQUE_SOL = 0;
    public static final int CALQUE_DECOR = 1;
    public static final int CALQUE_COLLISION = 2;
    public static final int NB_CALQUES = 3;

    private String nom;
    private String cheminTileset;
    private int largeur, hauteur;          // en tuiles
    private int tailleTuileW, tailleTuileH;
    private int[][][] calques;             // calques[calque][ligne][colonne]

    public Niveau(String cheminRessource) {
        try (InputStream in = Niveau.class.getResourceAsStream(cheminRessource)) {
            if (in == null) {
                throw new IllegalStateException("Fichier introuvable : " + cheminRessource);
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8));

            nom = br.readLine().trim();
            cheminTileset = br.readLine().trim();

            String[] dim = br.readLine().trim().split("\\s+");
            largeur = Integer.parseInt(dim[0]);
            hauteur = Integer.parseInt(dim[1]);
            tailleTuileW = Integer.parseInt(dim[2]);
            tailleTuileH = Integer.parseInt(dim[3]);

            calques = new int[NB_CALQUES][hauteur][largeur];
            for (int c = 0; c < NB_CALQUES; c++) {
                for (int l = 0; l < hauteur; l++) {
                    String ligne = br.readLine();
                    while (ligne != null && ligne.trim().isEmpty()) {
                        ligne = br.readLine();   // ignore les lignes vides
                    }
                    if (ligne == null) {
                        throw new IllegalStateException(
                                "Fichier trop court (calque " + c + ", ligne " + l + ")");
                    }
                    String[] v = ligne.trim().split("\\s+");
                    if (v.length != largeur) {
                        throw new IllegalStateException("Calque " + c + ", ligne " + l
                                + " : " + v.length + " valeurs au lieu de " + largeur);
                    }
                    for (int x = 0; x < largeur; x++) {
                        calques[c][l][x] = Integer.parseInt(v[x]);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du niveau", e);
        }
    }

    public String getNom()           { return nom; }
    public String getCheminTileset() { return cheminTileset; }
    public int getLargeur()          { return largeur; }
    public int getHauteur()          { return hauteur; }
    public int getTailleTuileW()     { return tailleTuileW; }
    public int getTailleTuileH()     { return tailleTuileH; }

    /** Retourne -1 si hors carte. */
    public int get(int calque, int ligne, int col) {
        if (ligne < 0 || col < 0 || ligne >= hauteur || col >= largeur) {
            return -1;
        }
        return calques[calque][ligne][col];
    }
    
}
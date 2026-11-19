package Aymeric;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenForet {

    // Fichier : 3 lignes d'en-tete, puis 3 calques de HAUTEUR lignes :
    // sol, decor, collision
    private static final int LIGNES_ENTETE = 3;
    private static final int LARGEUR = 40;
    private static final int HAUTEUR = 30;

    // Zone a vider dans le decor (chemins compris)
    private static final int V_L0 = 9, V_L1 = 21;
    private static final int V_C0 = 9, V_C1 = 30;

    // Zone d'herbe de la foret : 12 lignes x 18 colonnes de tuiles
    private static final int P_L0 = 9;
    private static final int P_C0 = 11;

    // La foret en blocs de 2x2 tuiles : 6 lignes x 9 colonnes de blocs
    private static final int NB_L = 6;
    private static final int NB_C = 9;

    // Colonne de gauche de chaque arbre dans le tileset (16 colonnes)
    private static final int[] ARBRES = {48, 50, 52, 54};
    private static final int COLONNES_TILESET = 16;

    // Pourcentage d'arbres a l'interieur de l'arene
    private static final int POURCENT_ARBRES_ARENE = 30;

    // Graine : meme valeur = meme foret. Change-la pour obtenir une autre foret.
    private static final long GRAINE = 42;

    public static void main(String[] args) throws IOException {
        Path fichier = Paths.get(args.length > 0 ? args[0] : "src/resources/texte_map.txt");
        List<String> lignes = new ArrayList<>(Files.readAllLines(fichier, StandardCharsets.UTF_8));

        // Sauvegarde : creee une seule fois, jamais ecrasee
        Path bak = Paths.get(fichier + ".bak");
        if (!Files.exists(bak)) {
            Files.copy(fichier, bak);
        }

        int debutDecor = LIGNES_ENTETE + HAUTEUR;
        int debutCollision = LIGNES_ENTETE + 2 * HAUTEUR;

        int[][] decor = lireCalque(lignes, debutDecor);
        int[][] collision = lireCalque(lignes, debutCollision);

        // Vider le decor de la zone
        for (int l = V_L0; l <= V_L1; l++) {
            for (int c = V_C0; c <= V_C1; c++) {
                decor[l][c] = -1;
            }
        }
        // Vider les collisions de la foret
        for (int l = P_L0; l < P_L0 + NB_L * 2; l++) {
            for (int c = P_C0; c < P_C0 + NB_C * 2; c++) {
                collision[l][c] = 0;
            }
        }

        // Construire un quart de foret puis le recopier par symetrie
        Random rnd = new Random(GRAINE);
        int[][] blocs = new int[NB_L][NB_C];
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 4; c++) {
                int v = choisirBloc(r, c, rnd);
                blocs[r][c] = v;
                blocs[NB_L - 1 - r][c] = v;
                blocs[r][NB_C - 1 - c] = v;
                blocs[NB_L - 1 - r][NB_C - 1 - c] = v;
            }
        }

        // Ecrire les blocs dans le decor et les collisions
        for (int r = 0; r < NB_L; r++) {
            for (int c = 0; c < NB_C; c++) {
                int t = blocs[r][c];
                if (t < 0) continue;
                int l = P_L0 + 2 * r;
                int col = P_C0 + 2 * c;
                decor[l][col]         = t;
                decor[l][col + 1]     = t + 1;
                decor[l + 1][col]     = t + COLONNES_TILESET;
                decor[l + 1][col + 1] = t + COLONNES_TILESET + 1;
                collision[l][col]         = 1;
                collision[l][col + 1]     = 1;
                collision[l + 1][col]     = 1;
                collision[l + 1][col + 1] = 1;
            }
        }

        ecrireCalque(lignes, debutDecor, decor);
        ecrireCalque(lignes, debutCollision, collision);

        Files.write(fichier, lignes, StandardCharsets.UTF_8);
        System.out.println("Foret regeneree dans " + fichier + " (sauvegarde : .bak)");
    }

    // Renvoie l'id de l'arbre du bloc (r, c) dans le quart haut-gauche, ou -1 si vide.
    // r va de 0 a 2, c va de 0 a 4 (c = 4 est la colonne centrale).
    private static int choisirBloc(int r, int c, Random rnd) {
        // Entree haute (au centre) : le bloc (0, 4) et son symetrique en bas
        if (r == 0 && c == 4) return -1;
        // Entrees gauche et droite : blocs (2, 0) et (2, 1), plus leurs symetriques
        if (r == 2 && c <= 1) return -1;

        boolean arene = (r >= 1 && c >= 2);
        if (arene) {
            // Couloirs d'acces et centre toujours libres
            if (r == 1 && c == 4) return -1;
            if (r == 2 && c == 2) return -1;
            if (r == 2 && c == 4) return -1;
            if (rnd.nextInt(100) < POURCENT_ARBRES_ARENE) {
                return ARBRES[rnd.nextInt(ARBRES.length)];
            }
            return -1;
        }

        // Mur de la foret : toujours un arbre
        return ARBRES[rnd.nextInt(ARBRES.length)];
    }

    private static int[][] lireCalque(List<String> lignes, int debut) {
        int[][] calque = new int[HAUTEUR][LARGEUR];
        for (int l = 0; l < HAUTEUR; l++) {
            String[] t = lignes.get(debut + l).trim().split("\\s+");
            for (int c = 0; c < LARGEUR; c++) {
                calque[l][c] = Integer.parseInt(t[c]);
            }
        }
        return calque;
    }

    private static void ecrireCalque(List<String> lignes, int debut, int[][] calque) {
        for (int l = 0; l < HAUTEUR; l++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < LARGEUR; c++) {
                if (c > 0) sb.append(' ');
                sb.append(calque[l][c]);
            }
            lignes.set(debut + l, sb.toString());
        }
    }
}
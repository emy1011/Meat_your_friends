
package TileMapping;

import java.awt.EventQueue;

public class MainProjet {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FenetreDeJeuProjet();
            }
        });
    }
}

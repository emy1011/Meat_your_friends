package Aymeric;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class InfoTileset {
    public static void main(String[] args) throws Exception {
        BufferedImage t = ImageIO.read(InfoTileset.class.getResourceAsStream("/resources/tileset.png"));
        int cols = t.getWidth() / 32, lignes = t.getHeight() / 32;
        System.out.println("Colonnes : " + cols + ", lignes : " + lignes);
    }
}
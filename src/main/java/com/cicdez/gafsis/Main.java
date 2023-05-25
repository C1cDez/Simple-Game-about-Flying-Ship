package com.cicdez.gafsis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static MainWindow WINDOW;

    public static void main(String[] args) {
        WINDOW = new MainWindow();
    }

    public static InputStream getResource(String pathname) {
        return Main.class.getResourceAsStream(pathname);
    }
    public static BufferedImage getImage(String pathname) {
        try {
            return ImageIO.read(getResource(pathname));
        } catch (IOException e) {
            return null;
        }
    }
}

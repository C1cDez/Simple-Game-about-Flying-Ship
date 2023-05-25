package com.cicdez.gafsis;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyShip {
    public double x;
    public final double y;
    public final double tickRate;
    public final BufferedImage texture;
    public final double vx = -0.7;

    public EnemyShip(double x, double y, double tickRate) {
        this.x = x;
        this.y = y;
        this.tickRate = tickRate;
        this.texture = Main.getImage("/textures/enemy_ship.png");
    }

    public void updateAndRender(Graphics2D graphics) {
        this.x += (vx * tickRate);
        graphics.drawImage(texture, (int) x - 32, (int) y - 32, null);
    }
}

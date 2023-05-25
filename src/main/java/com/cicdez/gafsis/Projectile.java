package com.cicdez.gafsis;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Projectile {
    public final double y;
    public double x;
    public final Ship owner;
    public final BufferedImage texture;
    public final double vx;

    public Projectile(Ship owner, double x, double vx) {
        this.owner = owner;
        this.texture = TEXTURE;
        this.y = owner.y;
        this.x = x;
        this.vx = vx;
    }

    public static BufferedImage TEXTURE = Main.getImage("/textures/projectile.png");

    public BufferedImage getTexture() {
        return texture;
    }

    public void updateAndRender(Graphics2D graphics) {
        this.x += (vx * owner.tickRate);
        graphics.drawImage(texture, (int) (x - 24), (int) (y - 24), null);
    }
}

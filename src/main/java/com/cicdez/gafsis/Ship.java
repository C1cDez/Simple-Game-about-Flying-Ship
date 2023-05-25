package com.cicdez.gafsis;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ship {
    public double y;
    public int score = 0;
    public final double tickRate;
    public final BufferedImage texture;
    public final ShipFlyingController flyingController;

    public int projectilesLeft = 10;

    public Ship(double tickRate) {
        this.texture = Main.getImage("/textures/ship.png");
        this.tickRate = tickRate;
        this.flyingController = new ShipFlyingController(this);
    }

    public ShipFlyingController getFlyingController() {
        return flyingController;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public Projectile createProjectile(double x) {
        projectilesLeft--;
        if (projectilesLeft <= 0) return null;
        else return new Projectile(this, x, 4);
    }

    public void updatePos(double vy) {
        this.y += (vy * tickRate);
    }

    public void kill(int cy) {
        score = 0;
        y = cy;
        projectilesLeft = 10;
    }
}

package com.cicdez.gafsis;

import java.awt.*;
import java.util.Random;

public class StarsRenderManager {
    public final Random random;
    public final int starsCount;
    public final Star[] stars;
    private final int width, height;

    public StarsRenderManager(int starsCount, Random random, int width, int height) {
        this.random = random;
        this.starsCount = starsCount;
        this.stars = new Star[starsCount];
        for (int i = 0; i < starsCount; i++) {
            stars[i] = new Star(random.nextInt(width), random.nextInt(height),
                    random.nextDouble(), random.nextInt(4) + 1);
        }
        this.width = width;
        this.height = height;
    }

    public void updateAndRender(Graphics2D graphics) {
        for (Star star : stars) {
            star.moveX(-1);
            render(star, graphics);
            if (star.x < -10) {
                star.moveX(width + 30);
                star.y = random.nextInt(height);
            }
        }
    }

    public void render(Star star, Graphics2D graphics) {
        Color color = new Color((int) (star.brightness * 127 + 127),
                (int) (star.brightness * 127 + 127), (int) (star.brightness * 127 + 127));
        graphics.setColor(color);
        graphics.fillOval(star.x, star.y, star.size, star.size);
    }

    public static class Star {
        public int x, y;
        public final double brightness;
        public final int size;
        public Star(int x, int y, double brightness, int size) {
            this.x = x;
            this.y = y;
            this.brightness = SomeMath.clamp(brightness, 0, 1);
            this.size = size;
        }
        public void moveX(int dx) {
            this.x += dx;
        }
        public void moveY(int dy) {
            this.y += dy;
        }
    }
}

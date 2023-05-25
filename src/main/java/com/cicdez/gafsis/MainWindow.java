package com.cicdez.gafsis;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainWindow extends JFrame {
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final BufferedImage DEATH_SCREEN = Main.getImage("/textures/death_screen.png");

    public final int msWait = 1;
    public final double tickRate = 1;
    public final int deathTime = 1500;
    public int deathLeftTimer = 0;

    public final int width = SCREEN_SIZE.width * 2 / 3, height = SCREEN_SIZE.height * 2 / 3;
    public final int centerX = width / 2, centerY = height / 2;

    public final Thread renderThread = new Thread(this::startRendering, "RenderThread");

    public final Color background = new Color(24, 10, 70);
    public final StarsRenderManager starsRenderManager = new StarsRenderManager(100,
            new Random(), width, height);
    public final BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    public final int shipRenderOffset = width / 6;
    public final Ship ship = new Ship(tickRate);
    public boolean dead = false;

    public final List<Projectile> flyingProjectiles = new ArrayList<>();
    public final int maxProjectileCoolDown = 20;
    public int projectileCoolDown = maxProjectileCoolDown;

    public final List<EnemyShip> enemyShips = new ArrayList<>();
    public final Random enemyShipsRandom = new Random();
    public final int enemyShipsSpawnChance = 250;

    public MainWindow() {
        super("Game about Flying Ship in Space");
        this.setSize(width, height);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        ship.y = centerY;
        this.addKeyListener(ship.getFlyingController());

        this.setVisible(true);
        renderThread.start();
    }

    @Override
    public void paint(Graphics g) {
        if (!dead) {
            render(screen.createGraphics());
            g.drawImage(screen, 0, 0, width, height, null);
        } else {
            setDeathScreen(screen.createGraphics());
            g.drawImage(screen, 0, 0, width, height, null);
            try {
                Thread.sleep(deathTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dead = false;
        }
    }

    public void render(Graphics2D graphics) {
        renderBackground(graphics);
        renderShip(graphics);
        renderFlyingProjectiles(graphics);
        trySummonEnemyShip();
        renderEnemyShips(graphics);
        renderProjectiles(graphics);
    }

    public void renderBackground(Graphics2D graphics) {
        graphics.setColor(background);
        graphics.fillRect(0, 0, width, height);
        starsRenderManager.updateAndRender(graphics);
    }
    public void renderShip(Graphics2D graphics) {
        graphics.drawImage(ship.texture, shipRenderOffset, (int) ship.y - 32, null);
        checkShipValidPosition();
    }
    public void renderFlyingProjectiles(Graphics2D graphics) {
        projectileCoolDown++;
        for (int i = 0; i < flyingProjectiles.size(); i++) {
            Projectile projectile = flyingProjectiles.get(i);
            projectile.updateAndRender(graphics);
            if (projectile.x >= width + 10) {
                flyingProjectiles.remove(projectile);
                i--;
            }
        }
    }
    public void renderProjectiles(Graphics2D graphics) {
        for (int i = 0; i < ship.projectilesLeft - 1; i++) {
            int x = 15, y = 35 + 48 * i - 20 * i;
            graphics.drawImage(Projectile.TEXTURE, x, y, null);
        }
    }

    public void summonShipFlyingProjectile() {
        if (projectileCoolDown > maxProjectileCoolDown) {
            projectileCoolDown = 0;
            Projectile projectile = ship.createProjectile(shipRenderOffset);
            if (projectile != null) flyingProjectiles.add(projectile);
        }
    }

    public void renderEnemyShips(Graphics2D graphics) {
        for (int i = 0; i < enemyShips.size(); i++) {
            EnemyShip enemyShip = enemyShips.get(i);
            enemyShip.updateAndRender(graphics);
            for (int j = 0; j < flyingProjectiles.size(); j++) {
                Projectile projectile = flyingProjectiles.get(j);
                double distance = SomeMath.getDistance(enemyShip.x, enemyShip.y, projectile.x, projectile.y);
                if (distance <= 25) {
                    flyingProjectiles.remove(projectile);
                    enemyShips.remove(enemyShip);
                    j--;
                    i--;
                    this.ship.projectilesLeft += (enemyShipsRandom.nextInt(2) == 1 ? 0 :
                            enemyShipsRandom.nextInt(3));
                }
            }
            if (enemyShip.x <= shipRenderOffset) {
                kill();
            }
        }
    }

    public void trySummonEnemyShip() {
        if (enemyShipsRandom.nextInt(enemyShipsSpawnChance) == 1) {
            enemyShips.add(new EnemyShip((int) (enemyShipsRandom.nextInt(width / 5) + width * 4 / 3),
                    enemyShipsRandom.nextInt(height - 200) + 100, tickRate));
        }
    }

    public void startRendering() {
        while (true) {
            try {
                Thread.sleep(msWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ship.score++;
            this.repaint();
        }
    }

    public void checkShipValidPosition() {
        if (ship.y <= 0 || ship.y >= height) kill();
    }

    public void kill() {
        dead = true;
        ship.kill(centerY);
        flyingProjectiles.clear();
        enemyShips.clear();
    }

    public void setDeathScreen(Graphics2D graphics) {
        graphics.drawImage(DEATH_SCREEN, centerX - 384, centerY - 48, null);
        graphics.drawString("Score: " + ship.score, centerX - 50, centerY + 150);
    }
}

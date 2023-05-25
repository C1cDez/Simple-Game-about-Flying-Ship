package com.cicdez.gafsis;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShipFlyingController implements KeyListener {
    public final Ship ship;
    public ShipFlyingController(Ship ship) {
        this.ship = ship;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP: {
                ship.updatePos(-3);
                break;
            }
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN: {
                ship.updatePos(3);
                break;
            }
            case KeyEvent.VK_E: {
                Main.WINDOW.summonShipFlyingProjectile();
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

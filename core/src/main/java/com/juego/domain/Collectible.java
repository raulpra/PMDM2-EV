package com.juego.domain;

import com.badlogic.gdx.math.Rectangle;

public class Collectible {
    private final Rectangle bounds;
    private boolean collected;

    public Collectible(float startX, float startY) {
        // Objeto de 16x16 píxeles
        bounds = new Rectangle(startX, startY, 16, 16);
        collected = false;
    }

    public Rectangle getBounds() { return bounds; }
    public boolean isCollected() { return collected; }
    public void setCollected(boolean collected) { this.collected = collected; }
}

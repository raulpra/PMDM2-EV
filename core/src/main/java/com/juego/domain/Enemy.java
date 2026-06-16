package com.juego.domain;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private final Vector2 position;
    private final Vector2 velocity;
    private final Rectangle bounds;

    // Variables para la patrulla
    private final float startX;
    private final float patrolDistance = 40f; // Cuánto camina antes de darse la vuelta
    private boolean movingRight = true;
    private boolean facingRight = true;

    private float stateTimer;

    public Enemy(float startX, float startY) {
        this.position = new Vector2(startX, startY);
        this.startX = startX;
        this.velocity = new Vector2(30f, 0); // Camina más despacio que el jugador
        this.bounds = new Rectangle(startX, startY, 16, 16);
        this.stateTimer = 0;
    }

    public void update(float delta) {
        stateTimer += delta;

        // Lógica de patrulla básica (Ping-Pong)
        if (movingRight) {
            position.x += velocity.x * delta;
            facingRight = true;
            if (position.x > startX + patrolDistance) {
                movingRight = false; // Da media vuelta
            }
        } else {
            position.x -= velocity.x * delta;
            facingRight = false;
            if (position.x < startX - patrolDistance) {
                movingRight = true; // Da media vuelta
            }
        }

        bounds.setPosition(position.x, position.y);
    }

    public Vector2 getPosition() { return position; }
    public Rectangle getBounds() { return bounds; }
    public float getStateTimer() { return stateTimer; }
    public boolean isFacingRight() { return facingRight; }
}

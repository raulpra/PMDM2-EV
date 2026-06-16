package com.juego.domain;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private final Vector2 position;
    private final Vector2 velocity;
    private final Rectangle bounds;

    private final EnemyType type;


    // Variables para la patrulla
    private final float startX;
    private float patrolDistance; // Cuánto camina antes de darse la vuelta
    private boolean movingRight = true;
    private boolean facingRight = true;

    private float stateTimer;

    public Enemy(float startX, float startY, EnemyType type) {
        this.position = new Vector2(startX, startY);
        this.startX = startX;
        this.type = type;
        this.bounds = new Rectangle(startX, startY, 16, 16);
        this.stateTimer = 0;

        // --- AQUÍ ESTÁ LA MAGIA DE LOS 3 TIPOS ---
        switch(type) {
            case FAST:
                this.velocity = new Vector2(60f, 0); // Corre el doble
                this.patrolDistance = 60f;
                break;
            case TANK:
                this.velocity = new Vector2(15f, 0); // Va súper lento
                this.patrolDistance = 100f; // Pero llega muy lejos
                break;
            case NORMAL:
            default:
                this.velocity = new Vector2(30f, 0); // Velocidad estándar
                this.patrolDistance = 40f;
                break;
        }
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
    public EnemyType getType() { return type; }
}

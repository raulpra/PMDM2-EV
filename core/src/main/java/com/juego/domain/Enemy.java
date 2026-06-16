package com.juego.domain;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private final Vector2 position;
    private final Vector2 velocity;
    private final Rectangle bounds;

    private final EnemyType type;


    // Variables para la patrulla
    private final float minX;
    private final float maxX;
    private boolean movingRight = true;
    private boolean facingRight = true;

    private float stateTimer;

    public Enemy(float startX, float startY, EnemyType type, float patrolWidth) {
        this.position = new Vector2(startX, startY);
        this.minX = startX;
        // Si no se dibuja anchura en Tiled, le damos 40 píxeles por defecto
        this.maxX = startX + (patrolWidth > 0 ? patrolWidth : 40f);
        this.type = type;
        this.bounds = new Rectangle(startX, startY, 16, 16);
        this.stateTimer = 0;

        // --- AQUÍ ESTÁ LA MAGIA DE LOS 3 TIPOS ---
        switch(type) {
            case FAST:
                this.velocity = new Vector2(60f, 0); // Corre el doble
                break;
            case TANK:
                this.velocity = new Vector2(15f, 0); // Va súper lento
                break;
            case NORMAL:
            default:
                this.velocity = new Vector2(30f, 0); // Velocidad estándar
                break;
        }
    }

    public void update(float delta) {
        stateTimer += delta;

        // Lógica de patrulla básica (Ping-Pong) usando los límites del rectángulo
        if (movingRight) {
            position.x += velocity.x * delta;
            facingRight = true;
            if (position.x > maxX) {
                movingRight = false; // Da media vuelta
            }
        } else {
            position.x -= velocity.x * delta;
            facingRight = false;
            if (position.x < minX) {
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

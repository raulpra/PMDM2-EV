package com.juego.domain;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Representa al jugador principal.
 */
public class Player {
    private final Vector2 position;
    private final Vector2 velocity;
    private final Rectangle bounds;
    private float stateTimer; // Cronómetro para saber qué fotograma pintar
    private boolean facingRight; // ¿Mira a la derecha?

    // Modifica el constructor:
    public Player(float startX, float startY) {
        position = new Vector2(startX, startY);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(startX, startY, 16, 16); // El OnionLad mide 16x16
        stateTimer = 0;
        facingRight = true;
    }

    public void update(float delta) {
        // Le sumamos a la posición la velocidad multiplicada por el tiempo (delta)
        position.add(velocity.x * delta, velocity.y * delta);

        // Actualizamos la caja invisible para que siga al dibujo del jugador
        bounds.setPosition(position.x, position.y);
        // Sumamos el tiempo
        stateTimer += delta;
        // Actualizamos hacia dónde mira según su velocidad
        if (velocity.x > 0 && !facingRight) {
            facingRight = true;
        } else if (velocity.x < 0 && facingRight) {
            facingRight = false;
        }
    }

    public Vector2 getPosition() { return position; }
    public Vector2 getVelocity() { return velocity; }
    public Rectangle getBounds() { return bounds; }
    public float getStateTimer() { return stateTimer; }
    public boolean isFacingRight() { return facingRight; }
}

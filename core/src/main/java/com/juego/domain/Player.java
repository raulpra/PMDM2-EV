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

    public Player(float startX, float startY) {
        position = new Vector2(startX, startY);
        velocity = new Vector2(0, 0);

        // Asumimos que nuestro personaje mide 16 de ancho y 24 de alto
        bounds = new Rectangle(startX, startY, 16, 24);
    }

    public void update(float delta) {
        // Le sumamos a la posición la velocidad multiplicada por el tiempo (delta)
        position.add(velocity.x * delta, velocity.y * delta);

        // Actualizamos la caja invisible para que siga al dibujo del jugador
        bounds.setPosition(position.x, position.y);
    }

    public Vector2 getPosition() { return position; }
    public Vector2 getVelocity() { return velocity; }
    public Rectangle getBounds() { return bounds; }
}

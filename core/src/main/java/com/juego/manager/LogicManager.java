package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.juego.domain.Player;
/**
 * Se encarga de procesar las reglas del juego (movimiento, inputs, colisiones).
 * No dibuja nada, solo calcula.
 */
public class LogicManager {

    private final Player player;
    private int vidas = 3;
    private int puntuacion = 0;


    // Constantes de físicas
    private static final float GRAVITY = -400f;       // Fuerza de la gravedad (hacia abajo)
    private static final float MOVEMENT_SPEED = 100f; // Velocidad al caminar
    private static final float JUMP_SPEED = 200f;     // Fuerza del salto


    public LogicManager() {
        // Hacemos que nazca en la coordenada X=50, Y=150 (en el aire)
        player = new Player(50, 150);
    }

    /**
     * Actualiza el estado del juego.
     * @param delta Tiempo transcurrido desde el último frame
     */
    public void update(float delta) {

        // 1. CONTROLES (Inputs)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.getVelocity().x = MOVEMENT_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.getVelocity().x = -MOVEMENT_SPEED;
        } else {
            // Si no tocamos nada, se frena instantáneamente
            player.getVelocity().x = 0;
        }
        // SALTO: Solo puede saltar si está tocando el suelo (de momento asumimos que el suelo es y<=32)
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            && player.getPosition().y <= 32) {
            player.getVelocity().y = JUMP_SPEED;
        }
        // 2. GRAVEDAD
        // La gravedad tira de él hacia abajo constantemente
        player.getVelocity().y += GRAVITY * delta;
        // 3. MOVER AL JUGADOR ---
        player.update(delta);
        // 4. COLISIONES (Suelo base temporal)
        if (player.getPosition().y < 32) {
            player.getPosition().y = 32;
            player.getVelocity().y = 0; // Frenamos la caída
        }
    }
    public Player getPlayer() {
        return player;
    }

    public int getVidas() { return vidas; }
    public int getPuntuacion() { return puntuacion; }
}

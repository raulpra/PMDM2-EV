package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.juego.domain.Player;
import com.badlogic.gdx.utils.Array;
import com.juego.domain.Collectible;
/**
 * Se encarga de procesar las reglas del juego (movimiento, inputs, colisiones).
 * No dibuja nada, solo calcula.
 */
public class LogicManager {

    private final Player player;
    private int vidas = 3;
    private int puntuacion = 0;
    private final Array<Collectible> collectibles;



    // Constantes de físicas
    private static final float GRAVITY = -400f;       // Fuerza de la gravedad (hacia abajo)
    private static final float MOVEMENT_SPEED = 100f; // Velocidad al caminar
    private static final float JUMP_SPEED = 200f;     // Fuerza del salto


    public LogicManager() {
        // Hacemos que nazca en la coordenada X=50, Y=150 (en el aire)
        player = new Player(50, 150);
        collectibles = new Array<>();
        // 3 frutas de prueba
        collectibles.add(new Collectible(100, 40));
        collectibles.add(new Collectible(150, 60));
        collectibles.add(new Collectible(200, 40));
    }

    /**
     * Actualiza el estado del juego.
     * @param delta Tiempo transcurrido desde el último frame
     */
    public void update(float delta, float mapWidth) {

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
        // Muro izquierdo
        if (player.getPosition().x < 0) {
            player.getPosition().x = 0;
            player.getVelocity().x = 0;
        }
        // Muro derecho
        // Restamos 16 que es el ancho del Onion Lad
        if (player.getPosition().x > mapWidth - 16) {
            player.getPosition().x = mapWidth - 16;
            player.getVelocity().x = 0;
        }
        // COMPROBAR COLECCIONABLES
        for (Collectible c : collectibles) {
            // Si no ha sido recogida Y el jugador la toca
            if (!c.isCollected() && player.getBounds().overlaps(c.getBounds())) {
                c.setCollected(true);
                puntuacion += 10;
            }
        }
    }
    public Player getPlayer() {
        return player;
    }

    public int getVidas() { return vidas; }
    public int getPuntuacion() { return puntuacion; }
    public Array<Collectible> getCollectibles() { return collectibles; }
}

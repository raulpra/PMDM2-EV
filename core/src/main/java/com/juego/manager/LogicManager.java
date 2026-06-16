package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.juego.domain.Enemy;
import com.juego.domain.EnemyType;
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
    private final Array<Enemy> enemies;



    // Constantes de físicas
    private static final float GRAVITY = -400f;       // Fuerza de la gravedad (hacia abajo)
    private static final float MOVEMENT_SPEED = 100f; // Velocidad al caminar
    private static final float JUMP_SPEED = 240f;     // Aumentado para saltar un bloque extra


    public LogicManager() {
        // Hacemos que nazca en la coordenada X=50, Y=150 (en el aire)
        player = new Player(50, 150);
        collectibles = new Array<>();
        enemies = new Array<>();
    }

    /**
     * Actualiza el estado del juego.
     * @param delta Tiempo transcurrido desde el último frame
     */
    public void update(float delta, LevelManager levelManager, SoundManager soundManager) {

        // 1. CONTROLES (Inputs)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.getVelocity().x = MOVEMENT_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.getVelocity().x = -MOVEMENT_SPEED;
        } else {
            player.getVelocity().x = 0;
        }

        // 2. GRAVEDAD
        player.getVelocity().y += GRAVITY * delta;

        // 3. MOVER X Y COMPROBAR COLISION
        player.getPosition().x += player.getVelocity().x * delta;
        if (levelManager.isSolid(player.getPosition().x, player.getPosition().y + 1) ||
            levelManager.isSolid(player.getPosition().x + 15, player.getPosition().y + 1) ||
            levelManager.isSolid(player.getPosition().x, player.getPosition().y + 14) ||
            levelManager.isSolid(player.getPosition().x + 15, player.getPosition().y + 14)) {
            // Deshacemos el movimiento en X
            player.getPosition().x -= player.getVelocity().x * delta;
            player.getVelocity().x = 0;
        }

        // 4. MOVER Y Y COMPROBAR COLISION
        player.getPosition().y += player.getVelocity().y * delta;
        if (player.getVelocity().y < 0) { // cayendo
            // Comprobamos un poco por dentro (x+1 y x+14) para no chocar con paredes laterales
            if (levelManager.isSolid(player.getPosition().x + 1, player.getPosition().y) ||
                levelManager.isSolid(player.getPosition().x + 14, player.getPosition().y)) {
                // Toca el suelo
                player.getPosition().y = (float) (Math.ceil(player.getPosition().y / 16) * 16);
                player.getVelocity().y = 0;
            }
        } else if (player.getVelocity().y > 0) { // saltando
            if (levelManager.isSolid(player.getPosition().x + 1, player.getPosition().y + 15) ||
                levelManager.isSolid(player.getPosition().x + 14, player.getPosition().y + 15)) {
                // Toca el techo
                player.getPosition().y = (float) (Math.floor(player.getPosition().y / 16) * 16);
                player.getVelocity().y = 0;
            }
        }

        // SALTO
        // Miramos si justo debajo de los pies hay suelo
        boolean grounded = levelManager.isSolid(player.getPosition().x, player.getPosition().y - 1) || 
                           levelManager.isSolid(player.getPosition().x + 15, player.getPosition().y - 1);
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && grounded) {
            player.getVelocity().y = JUMP_SPEED;
            soundManager.playJump();
        }

        player.update(delta); // Actualiza estado/animacion
        
        // LIMITES DEL MAPA (Muros transparentes y agujeros)
        if (player.getPosition().x < 0) player.getPosition().x = 0;
        if (player.getPosition().x > levelManager.getMapPixelWidth() - 16) player.getPosition().x = levelManager.getMapPixelWidth() - 16;
        
        // Si cae por un agujero (fuera del mapa)
        if (player.getPosition().y < -32) {
            vidas--;
            soundManager.playHit();
            player.getPosition().set(50, 150); // Reaparecer
            player.getVelocity().set(0,0);
        }
        // COMPROBAR COLECCIONABLES
        for (Collectible c : collectibles) {
            // Si no ha sido recogida Y el jugador la toca
            if (!c.isCollected() && player.getBounds().overlaps(c.getBounds())) {
                c.setCollected(true);
                puntuacion += 10;
                soundManager.playCoin();
            }
        }

        for (Enemy e : enemies) {
            e.update(delta);
        }
        // COLISIONES CON ENEMIGOS
        for (Enemy e : enemies) {
            if (player.getBounds().overlaps(e.getBounds())) {
                // 1. Restamos una vida
                vidas--;
                soundManager.playHit();
                // 2. Mandamos al jugador instantáneamente de vuelta a la salida
                player.getPosition().set(50, 150);

                // 3. Le frenamos la velocidad por si estaba saltando o cayendo
                player.getVelocity().set(0, 0);

                // Salimos del bucle para no chocar con más cosas a la vez
                break;
            }
        }
    }
    public Player getPlayer() {
        return player;
    }

    public int getVidas() { return vidas; }
    public int getPuntuacion() { return puntuacion; }
    public Array<Collectible> getCollectibles() { return collectibles; }
    public Array<Enemy> getEnemies() { return enemies; }
}

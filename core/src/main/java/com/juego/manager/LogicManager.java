package com.juego.manager;
import com.juego.domain.Player;
/**
 * Se encarga de procesar las reglas del juego (movimiento, inputs, colisiones).
 * No dibuja nada, solo calcula.
 */
public class LogicManager {

    private final Player player;
    // Fuerza de la gravedad (hacia abajo)
    private static final float GRAVITY = -400f;

    public LogicManager() {
        // Hacemos que nazca en la coordenada X=50, Y=150 (en el aire)
        player = new Player(50, 150);
    }

    /**
     * Actualiza el estado del juego.
     * @param delta Tiempo transcurrido desde el último frame
     */
    public void update(float delta) {
        // 1. La gravedad afecta a la velocidad vertical (Y)
        player.getVelocity().y += GRAVITY * delta;
        // 2. Movemos al jugador según su velocidad
        player.update(delta);
        // 3. Un suelo invisible en Y = 32 para que no caiga al infinito

        if (player.getPosition().y < 32) {
            player.getPosition().y = 32;
            player.getVelocity().y = 0; // Se frena al tocar el suelo
        }
    }
    public Player getPlayer() {
        return player;
    }
}

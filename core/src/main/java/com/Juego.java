package com;

import com.badlogic.gdx.Game;
import com.juego.screen.FirstScreen;
/**
 * Clase principal del juego. Hereda de Game (libGDX) para poder gestionar
 * múltiples pantallas (Screens) de forma sencilla.
 */
public class Juego extends Game {
    @Override
    public void create() {
        // Inicializamos el juego cargando la primera pantalla
        setScreen(new FirstScreen());
    }
}

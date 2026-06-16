package com.juego;

import com.badlogic.gdx.Game;
import com.juego.manager.ResourceManager;
import com.juego.manager.SoundManager;
import com.juego.screen.MainMenuScreen;

/**
 * Clase principal del juego. Hereda de Game (libGDX) para poder gestionar
 * múltiples pantallas (Screens) de forma sencilla.
 */
public class Juego extends Game {

    private ResourceManager resourceManager;
    private SoundManager soundManager;

    @Override
    public void create() {
        resourceManager = new ResourceManager();
        resourceManager.loadAllResources();
        resourceManager.finishLoading(); // Cargamos todo antes de empezar
        resourceManager.createAnimations();

        // Iniciamos en el menú principal
        setScreen(new MainMenuScreen(this));

        soundManager = new com.juego.manager.SoundManager();
        soundManager.playMusic();
    }
    public ResourceManager getResourceManager() {
        return resourceManager;
    }
    @Override
    public void dispose() {
        super.dispose();
        if (resourceManager != null) {
            resourceManager.dispose();
            soundManager.dispose();
        }
    }

    public SoundManager getSoundManager() { return soundManager; }
}

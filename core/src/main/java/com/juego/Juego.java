package com.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    private Skin skin;

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
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
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
            skin.dispose();
        }
    }

    public SoundManager getSoundManager() { return soundManager; }
    public Skin getSkin() { return skin; }
}

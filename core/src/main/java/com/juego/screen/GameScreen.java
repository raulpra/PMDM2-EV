package com.juego.screen;

import com.badlogic.gdx.Screen;
import com.juego.Juego;
import com.juego.manager.LevelManager;
import com.juego.manager.LogicManager;
import com.juego.manager.RenderManager;

public class GameScreen implements Screen {
    private final Juego juego;
    private final RenderManager renderManager;
    private final LogicManager logicManager;
    private final LevelManager levelManager;

    public GameScreen(Juego juego) {
        this.juego = juego;
        // Inicializamos nuestros gestores core
        this.renderManager = new RenderManager();
        this.logicManager = new LogicManager();

        // Instanciamos el gestor de nivel y cargamos el nivel 1
        this.levelManager = new LevelManager(juego);
        this.levelManager.loadLevel("maps/nivel1.tmx");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // 1. Calculamos la lógica matemática (físicas, movimiento)
        logicManager.update(delta);

        // 2. Dibujamos el resultado en pantalla
        renderManager.render(levelManager);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        renderManager.dispose();
        levelManager.dispose();
    }
}

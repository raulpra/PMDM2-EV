package com.juego.screen;

import com.badlogic.gdx.Screen;
import com.juego.Juego;
import com.juego.manager.LevelManager;
import com.juego.manager.LogicManager;
import com.juego.manager.RenderManager;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
    private final Juego juego;
    private final RenderManager renderManager;
    private final LogicManager logicManager;
    private final LevelManager levelManager;
    private final HudRenderer hud;


    public GameScreen(Juego juego) {
        this.juego = juego;
        // Inicializamos nuestros gestores core
        this.renderManager = new RenderManager();
        this.logicManager = new LogicManager();

        // Instanciamos el gestor de nivel y cargamos el nivel 1
        this.levelManager = new LevelManager(juego);
        this.levelManager.loadLevel("maps/nivel1.tmx", this.logicManager);

        hud = new HudRenderer();

    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // 1. Calculamos la lógica matemática (físicas, movimiento)
        logicManager.update(delta, levelManager, juego.getSoundManager());

        // 2. Dibujamos el resultado en pantalla
        renderManager.render(levelManager, levelManager.getBackground(), logicManager, juego.getResourceManager());

        hud.render(logicManager);

        //  COMPROBAR FIN DE PARTIDA

        // 1. GAME OVER
        if (logicManager.getVidas() <= 0) {
            juego.setScreen(new GameOverScreen(juego));
            dispose(); // Destruimos este nivel para liberar memoria
            return;    // Salimos del método para que no intente dibujar nada más
        }

        // 2. VICTORIA O SIGUIENTE NIVEL
        if (logicManager.getPlayer().getPosition().x >= levelManager.getMapPixelWidth() - 32) {
            if (levelManager.getCurrentLevel() == 1) {
                // Pasamos al nivel 2
                levelManager.setCurrentLevel(2);
                levelManager.loadLevel("maps/nivel2.tmx", logicManager);
                // Ponemos al jugador al principio
                logicManager.getPlayer().getPosition().set(50, 150);
                logicManager.getPlayer().getVelocity().set(0, 0);
            } else {
                // Ya ha completado el nivel 2, ha ganado el juego
                juego.setScreen(new VictoryScreen(juego, logicManager.getPuntuacion()));
                dispose();
            }
            return;
        }
        // 2. Dibujamos el resultado en pantalla
        renderManager.render(levelManager, levelManager.getBackground(), logicManager, juego.getResourceManager());

        hud.render(logicManager);
    }

    @Override
    public void resize(int width, int height) { renderManager.resize(width, height);}

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
        hud.dispose();
    }
}

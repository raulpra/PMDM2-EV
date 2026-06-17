package com.juego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.juego.Juego;
import com.juego.manager.LevelManager;
import com.juego.manager.LogicManager;
import com.juego.manager.RenderManager;

public class GameScreen implements Screen {
    private final Juego juego;
    private final RenderManager renderManager;
    private final LogicManager logicManager;
    private final LevelManager levelManager;
    private final HudRenderer hud;

    // --- VARIABLES PARA LA PAUSA ---
    private enum State { RUNNING, PAUSED }
    private State state = State.RUNNING;
    private Stage pauseStage;

    public GameScreen(final Juego juego) {
        this.juego = juego;
        // Inicializamos nuestros gestores core
        this.renderManager = new RenderManager();
        this.logicManager = new LogicManager();

        // Instanciamos el gestor de nivel y cargamos el nivel 1
        this.levelManager = new LevelManager(juego);
        this.levelManager.loadLevel("maps/nivel1.tmx", this.logicManager);

        hud = new HudRenderer();

        // --- CREAR MENÚ DE PAUSA ---
        createPauseMenu();
    }

    private void createPauseMenu() {
        pauseStage = new Stage();

        Table tabla = new Table(juego.getSkin());
        tabla.setFillParent(true);
        tabla.center();

        Label titulo = new Label("JUEGO PAUSADO", juego.getSkin());
        titulo.setFontScale(2f);
        titulo.setColor(Color.ORANGE);
        tabla.add(titulo).center().padBottom(40f).row();

        // Botón Continuar
        TextButton btnContinuar = new TextButton("CONTINUAR", juego.getSkin());
        btnContinuar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                state = State.RUNNING; // Quitamos la pausa
            }
        });
        tabla.add(btnContinuar).center().width(250).padBottom(15f).row();

        // Botón Activar/Desactivar Sonido
        final TextButton btnSonido = new TextButton(juego.getSoundManager().isSoundEnabled() ? "SILENCIAR SONIDO" : "ACTIVAR SONIDO", juego.getSkin());
        btnSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                boolean isEnabled = juego.getSoundManager().isSoundEnabled();
                juego.getSoundManager().setSoundEnabled(!isEnabled);
                juego.getSoundManager().setMusicEnabled(!isEnabled); // Silenciamos ambos por simplicidad
                btnSonido.setText(!isEnabled ? "SILENCIAR SONIDO" : "ACTIVAR SONIDO");
            }
        });
        tabla.add(btnSonido).center().width(250).padBottom(15f).row();

        // Botón Menú Principal
        TextButton btnMenu = new TextButton("VOLVER AL MENU", juego.getSkin());
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                juego.setScreen(new MainMenuScreen(juego));
            }
        });
        tabla.add(btnMenu).center().width(250).padBottom(15f).row();

        // Botón Salir
        TextButton btnSalir = new TextButton("SALIR DEL JUEGO", juego.getSkin());
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });
        tabla.add(btnSalir).center().width(250).row();

        pauseStage.addActor(tabla);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // --- CONTROL DE PAUSA ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            state = (state == State.RUNNING) ? State.PAUSED : State.RUNNING;
        }

        if (state == State.RUNNING) {
            // 1. Calculamos la lógica solo si NO estamos en pausa
            logicManager.update(delta, levelManager, juego.getSoundManager());

            // 2. Comprobar Fin de Partida
            if (logicManager.getVidas() <= 0) {
                juego.setScreen(new GameOverScreen(juego, logicManager.getPuntuacion()));
                dispose();
                return;
            }

            // 3. Comprobar Victoria o Siguiente Nivel
            if (logicManager.getPlayer().getPosition().x >= levelManager.getMapPixelWidth() - 32) {
                int nextLevel = levelManager.getCurrentLevel() + 1;
                String nextMapPath = "maps/nivel" + nextLevel + ".tmx";
                
                if (juego.getResourceManager().isLoaded(nextMapPath)) {
                    // Hay un siguiente nivel disponible
                    levelManager.setCurrentLevel(nextLevel);
                    levelManager.loadLevel(nextMapPath, logicManager);
                    logicManager.getPlayer().getPosition().set(50, 150);
                    logicManager.getPlayer().getVelocity().set(0, 0);
                } else {
                    // No hay más niveles, has completado el juego
                    juego.setScreen(new VictoryScreen(juego, logicManager.getPuntuacion()));
                    dispose();
                }
                return;
            }
        }

        // Siempre dibujamos el juego de fondo (incluso pausado)
        renderManager.render(levelManager, levelManager.getBackground(), logicManager, juego.getResourceManager());
        hud.render(logicManager, levelManager);

        if (state == State.PAUSED) {
            // Le damos el control del ratón al menú de pausa
            Gdx.input.setInputProcessor(pauseStage);

            // Dibujamos un filtro oscurecedor simple bajando el alpha
            // Aquí dibujamos la interfaz por encima
            pauseStage.act(delta);
            pauseStage.draw();
        } else {
            // Si volvemos a jugar, quitamos el control del ratón del menú
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void resize(int width, int height) {
        renderManager.resize(width, height);
        pauseStage.getViewport().update(width, height, true);
    }

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
        if (pauseStage != null) pauseStage.dispose();
    }
}

package com.juego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.juego.Juego;

public class MainMenuScreen implements Screen {
    private final Juego juego;
    private final Stage stage;

    public MainMenuScreen(final Juego juego) {
        this.juego = juego;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table tabla = new Table(juego.getSkin());
        tabla.setFillParent(true);
        tabla.center();

        Label titulo = new Label("ONION LAND ADVENTURES", juego.getSkin());
        titulo.setFontScale(2.5f);
        titulo.setColor(Color.GREEN);
        tabla.add(titulo).center().padBottom(50f).row();

        // Botón Jugar
        TextButton btnJugar = new TextButton("NUEVA PARTIDA", juego.getSkin());
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                juego.setScreen(new GameScreen(juego));
            }
        });
        tabla.add(btnJugar).center().width(250).padBottom(15f).row();

        // Botón Ranking
        TextButton btnRanking = new TextButton("TOP 10 PUNTUACIONES", juego.getSkin());
        btnRanking.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                juego.setScreen(new HighScoresScreen(juego));
            }
        });
        tabla.add(btnRanking).center().width(250).padBottom(15f).row();

        // Botón Instrucciones
        TextButton btnInstrucciones = new TextButton("INSTRUCCIONES", juego.getSkin());
        btnInstrucciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                juego.setScreen(new InstructionsScreen(juego));
            }
        });
        tabla.add(btnInstrucciones).center().width(250).padBottom(15f).row();

        // Botón Opciones
        TextButton btnOpciones = new TextButton("OPCIONES", juego.getSkin());
        btnOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                juego.setScreen(new ConfigurationScreen(juego));
            }
        });
        tabla.add(btnOpciones).center().width(250).padBottom(15f).row();

        // Botón Salir
        TextButton btnSalir = new TextButton("SALIR DEL JUEGO", juego.getSkin());
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });
        tabla.add(btnSalir).center().width(250).padBottom(15f).row();

        stage.addActor(tabla);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}

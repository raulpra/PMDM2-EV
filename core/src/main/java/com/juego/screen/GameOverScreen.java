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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.juego.Juego;
import com.juego.manager.ScoreManager;

public class GameOverScreen implements Screen {
    private final Juego juego;
    private final int puntuacion;
    private final Stage stage;
    private final ScoreManager scoreManager;

    public GameOverScreen(Juego juego, int puntuacion) {
        this.juego = juego;
        this.puntuacion = puntuacion;
        this.scoreManager = new ScoreManager();
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        Table tabla = new Table(juego.getSkin());
        tabla.setFillParent(true);
        tabla.center();

        Label titulo = new Label("¡GAME OVER!", juego.getSkin());
        titulo.setFontScale(2.5f);
        titulo.setColor(Color.RED);

        Label lblPuntos = new Label("Puntuacion final: " + puntuacion, juego.getSkin());
        lblPuntos.setColor(Color.WHITE);

        tabla.add(titulo).center().padBottom(20f).row();
        tabla.add(lblPuntos).center().padBottom(30f).row();

        // --- CAJA DE TEXTO PARA EL NOMBRE ---
        Label lblNombre = new Label("Introduce tu nombre para el TOP 10:", juego.getSkin());
        tabla.add(lblNombre).center().padBottom(6f).row();

        final TextField campoNombre = new TextField("", juego.getSkin());
        campoNombre.setMaxLength(12);
        campoNombre.setMessageText("Tu nombre..."); 
        tabla.add(campoNombre).center().width(200).padBottom(20f).row();

        // --- BOTÓN DE GUARDAR ---
        TextButton btnGuardar = new TextButton("GUARDAR Y VER RANKING", juego.getSkin());
        btnGuardar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                String nombre = campoNombre.getText();
                if (nombre == null || nombre.trim().isEmpty()) nombre = "Anónimo";

                scoreManager.addScore(nombre, puntuacion);

                // Automáticamente nos vamos al ranking
                dispose();
                juego.setScreen(new HighScoresScreen(juego));
            }
        });

        tabla.add(btnGuardar).center().width(250).padBottom(20f).row();

        stage.addActor(tabla);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Fondo rojo oscuro
        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        stage.dispose();
    }
}

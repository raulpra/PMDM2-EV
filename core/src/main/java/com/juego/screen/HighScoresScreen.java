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
import com.juego.manager.ScoreManager;

import java.util.List;

public class HighScoresScreen implements Screen {
    private final Juego juego;
    private final Stage stage;
    private final ScoreManager scoreManager;

    public HighScoresScreen(final Juego juego) {
        this.juego = juego;
        this.scoreManager = new ScoreManager();
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        Table tabla = new Table(juego.getSkin());
        tabla.setFillParent(true);
        tabla.center();

        Label titulo = new Label("TOP 10 JUGADORES", juego.getSkin());
        titulo.setFontScale(2f);
        titulo.setColor(Color.GREEN);
        tabla.add(titulo).center().padBottom(30f).row();

        List<ScoreManager.ScoreRecord> topScores = scoreManager.getTopScores();

        if (topScores.isEmpty()) {
            tabla.add(new Label("Aún no hay puntuaciones", juego.getSkin())).center().padBottom(20f).row();
        } else {
            for (int i = 0; i < topScores.size(); i++) {
                ScoreManager.ScoreRecord record = topScores.get(i);
                Label lblScore = new Label((i + 1) + ". " + record.name + " : " + record.score, juego.getSkin());
                tabla.add(lblScore).center().padBottom(10f).row();
            }
        }

        TextButton btnVolver = new TextButton("VOLVER AL MENU", juego.getSkin());
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                juego.setScreen(new MainMenuScreen(juego));
            }
        });

        tabla.add(btnVolver).center().width(250).padTop(30f).row();
        stage.addActor(tabla);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
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

package com.juego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juego.Juego;
import com.juego.manager.ScoreManager;
import com.juego.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class VictoryScreen implements Screen {
    private final Juego juego;
    private final int puntuacion;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final ScoreManager scoreManager;
    private final List<Integer> topScores;

    // Pasamos la puntuación para mostrarla
    public VictoryScreen(Juego juego, int puntuacion) {
        this.juego = juego;
        this.puntuacion = puntuacion;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        this.scoreManager = new ScoreManager();
        this.scoreManager.addScore(puntuacion);
        this.topScores = scoreManager.getTopScores();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Fondo verde oscuro
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "¡NIVEL COMPLETADO!", Constants.APP_WIDTH / 2f - 60, Constants.APP_HEIGHT / 2f + 80);
        font.draw(batch, "Puntuación Final: " + puntuacion, Constants.APP_WIDTH / 2f - 60, Constants.APP_HEIGHT / 2f + 50);

        // Dibujamos el Top 5
        font.draw(batch, "--- TOP MEJORES ---", Constants.APP_WIDTH / 2f - 60, Constants.APP_HEIGHT / 2f + 20);
        int yOffset = 0;
        for (int i = 0; i < topScores.size(); i++) {
            font.draw(batch, (i + 1) + ". " + topScores.get(i) + " pts", Constants.APP_WIDTH / 2f - 40, Constants.APP_HEIGHT / 2f + yOffset);
            yOffset -= 20; // Bajamos 20 píxeles para el siguiente de la lista
        }
        font.draw(batch, "Pulsa ENTER para menú", Constants.APP_WIDTH / 2f - 70, Constants.APP_HEIGHT / 2f - 110);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            juego.setScreen(new MainMenuScreen(juego));
            dispose();
        }
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
        batch.dispose();
        font.dispose();
    }
}

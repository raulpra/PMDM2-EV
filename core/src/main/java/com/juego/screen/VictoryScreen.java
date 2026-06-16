package com.juego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juego.Juego;
import com.juego.util.Constants;

public class VictoryScreen implements Screen {
    private final Juego juego;
    private final int puntuacion;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;

    // Fíjate que le pasamos la puntuación para mostrarla
    public VictoryScreen(Juego juego, int puntuacion) {
        this.juego = juego;
        this.puntuacion = puntuacion;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
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
        font.draw(batch, "¡NIVEL COMPLETADO!", Constants.APP_WIDTH / 2f - 60, Constants.APP_HEIGHT / 2f + 40);
        font.draw(batch, "Puntuación Final: " + puntuacion, Constants.APP_WIDTH / 2f - 60, Constants.APP_HEIGHT / 2f);
        font.draw(batch, "Pulsa ENTER para volver al menú", Constants.APP_WIDTH / 2f - 100, Constants.APP_HEIGHT / 2f - 40);
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

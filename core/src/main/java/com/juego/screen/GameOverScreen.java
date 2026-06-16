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

public class GameOverScreen implements Screen {
    private final Juego juego;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;

    public GameOverScreen(Juego juego) {
        this.juego = juego;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Fondo rojo oscuro
        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "¡GAME OVER!", Constants.APP_WIDTH / 2f - 40, Constants.APP_HEIGHT / 2f + 20);
        font.draw(batch, "Pulsa ENTER para volver al menú", Constants.APP_WIDTH / 2f - 100, Constants.APP_HEIGHT / 2f - 20);
        batch.end();

        // Si pulsa ENTER, volvemos a empezar
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

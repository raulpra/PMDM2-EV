package com.juego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juego.Juego;

public class ConfigurationScreen implements Screen {
    private final Juego juego;
    private SpriteBatch batch;
    private BitmapFont font;

    public ConfigurationScreen(Juego juego) {
        this.juego = juego;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2f);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Fondo azulado para diferenciarlo del menú principal
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "PANTALLA DE CONFIGURACION", 180, 300);
        font.draw(batch, "- Sonido: Activado (Proximamente)", 180, 220);
        font.draw(batch, "Pulsa ESCAPE para volver al Menu", 180, 150);
        batch.end();

        // Volver atrás
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            juego.setScreen(new MainMenuScreen(juego));
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

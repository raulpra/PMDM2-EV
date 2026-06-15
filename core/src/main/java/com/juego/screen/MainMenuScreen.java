package com.juego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juego.Juego;

public class MainMenuScreen implements Screen {
    private final Juego juego;
    private SpriteBatch batch;
    private BitmapFont font;

    public MainMenuScreen(Juego juego) {
        this.juego = juego;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont(); // Fuente por defecto de libGDX
        this.font.getData().setScale(2f); // Aumentamos el tamaño de la letra
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Limpiamos la pantalla con un color gris oscuro
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujamos el texto
        batch.begin();
        font.draw(batch, "MI JUEGO DE PLATAFORMAS", 200, 300);
        font.draw(batch, "Pulsa ENTER para Jugar", 230, 200);
        font.draw(batch, "Pulsa C para Configuracion", 230, 150);
        batch.end();

        // Lógica de transición de pantallas
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            juego.setScreen(new GameScreen(juego));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            juego.setScreen(new ConfigurationScreen(juego));
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

package com.juego.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.juego.manager.LogicManager;
import com.juego.util.Constants;

/**
 * Se encarga de dibujar información por encima del juego (vidas, puntos, tiempo).
 */
public class HudRenderer implements Disposable {
    private final SpriteBatch hudBatch;
    private final BitmapFont font;
    private final OrthographicCamera hudCamera;

    public HudRenderer() {
        hudBatch = new SpriteBatch();
        font = new BitmapFont();

        // La cámara del HUD no tiene zoom. Queremos que el texto se lea nítido
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

    public void render(LogicManager logic) {
        hudCamera.update();
        hudBatch.setProjectionMatrix(hudCamera.combined);

        // Dibujamos la info estática en pantalla
        hudBatch.begin();
        font.draw(hudBatch, "Vidas: " + logic.getVidas(), 20, Constants.APP_HEIGHT - 20);
        font.draw(hudBatch, "Puntos: " + logic.getPuntuacion(), 20, Constants.APP_HEIGHT - 40);
        hudBatch.end();
    }

    @Override
    public void dispose() {
        hudBatch.dispose();
        font.dispose();
    }
}

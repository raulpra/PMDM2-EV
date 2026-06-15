package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juego.util.Constants;

/**
 * Se encarga en exclusiva de dibujar en la pantalla (cámara, sprites, etc.).
 */
public class RenderManager {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;

    public RenderManager() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        // Configuramos la cámara para que vea nuestra resolución virtual (800x480)
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

    public void render() {
        // 1. Actualizamos la cámara
        camera.update();
        // 2. Le decimos al batch que dibuje desde el punto de vista de la cámara
        batch.setProjectionMatrix(camera.combined);

        // 3. Limpiamos la pantalla (color negro)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //  Dibujamos el mapa primero (para que quede de fondo)
        if (levelManager != null) {
            levelManager.render(camera);
        }

        // 4. Empezamos a dibujar
        batch.begin();

        batch.end();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void dispose() {
        batch.dispose();
    }
}

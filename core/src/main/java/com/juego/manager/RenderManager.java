package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juego.util.Constants;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.juego.domain.Player;
import com.juego.manager.ResourceManager;


/**
 * Se encarga en exclusiva de dibujar en la pantalla (cámara, sprites, etc.).
 */
public class RenderManager {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;

    public RenderManager() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        // Configuramos la cámara para que vea nuestra resolución virtual (800x480)
        // Dividimos entre 3 para hacer un "zoom x3".
        camera.setToOrtho(false, Constants.APP_WIDTH/ 2.5f, Constants.APP_HEIGHT);
        shapeRenderer = new ShapeRenderer();
    }

    public void render(LevelManager levelManager, Texture background, LogicManager logicManager, ResourceManager resourceManager) {
        // 1. Actualizamos la cámara
        camera.update();
        // 2. Le decimos al batch que dibuje desde el punto de vista de la cámara
        batch.setProjectionMatrix(camera.combined);

        // 3. Limpiamos la pantalla (color negro)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // 1. Pintamos el fondo. Usamos el tamaño de la cámara para que ocupe todo
        if (background != null) {
            batch.begin();
            batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
            batch.end();
        }

        // 2. Pintamos el mapa encima del fondo
        if (levelManager != null) {
            levelManager.render(camera);
        }
        // --- PINTAR AL JUGADOR ---
        Player p = logicManager.getPlayer();
        TextureRegion currentFrame;

        // Decidimos si usamos la animación de correr o de estar quieto
        if (p.getVelocity().x != 0) {
            currentFrame = resourceManager.getRunAnim().getKeyFrame(p.getStateTimer());
        } else {
            currentFrame = resourceManager.getIdleAnim().getKeyFrame(p.getStateTimer());
        }

        // Volteamos la imagen si mira a la izquierda (flip)
        if (!p.isFacingRight() && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        } else if (p.isFacingRight() && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
        // Lo dibujamos
        batch.begin();
        batch.draw(currentFrame, p.getPosition().x, p.getPosition().y, 16, 16);
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
        shapeRenderer.dispose();
    }
}

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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Se encarga en exclusiva de dibujar en la pantalla (cámara, sprites, etc.).
 */
public class RenderManager {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private final Viewport viewport;

    public RenderManager() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        // Configuramos la cámara para que vea nuestra resolución virtual (800x480)
        // Dividimos entre 3 para hacer un "zoom x3".
        viewport = new FitViewport(Constants.APP_WIDTH / 3f, Constants.APP_HEIGHT / 3f, camera);
        viewport.apply();
        shapeRenderer = new ShapeRenderer();
    }

    public void render(LevelManager levelManager, Texture background, LogicManager logicManager, ResourceManager resourceManager) {
        // LA CÁMARA SIGUE AL JUGADOR
        Player p = logicManager.getPlayer();
        // Centramos la cámara en el jugador (en el eje X e Y)
        float halfScreenWidth = camera.viewportWidth / 2f;
        float halfScreenHeight = camera.viewportHeight / 2f;
        // 1. La cámara persigue al jugador en horizontal (X e Y)
        camera.position.x = p.getPosition().x;
        camera.position.y = p.getPosition().y;
        // 2. La cámara se queda FIJA en vertical (Y) para que el suelo no flote
        //camera.position.y = halfScreenHeight;
        // Tope de la cámara izquierdo
        if (camera.position.x < halfScreenWidth) {
            camera.position.x = halfScreenWidth;
        }
        // Tope de la cámara a la derecha en función de los pixel del mapa
        float maxCameraX = levelManager.getMapPixelWidth() - halfScreenWidth;
        if (camera.position.x > maxCameraX) {
            camera.position.x = maxCameraX;
        }

        // Muro de cámara por abajo (para que no baje del suelo base y no flote el mapa)
        if (camera.position.y < halfScreenHeight) {
            camera.position.y = halfScreenHeight;
        }

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
            batch.draw(background, camera.position.x - camera.viewportWidth / 2f,
                camera.position.y - camera.viewportHeight / 2f,
                camera.viewportWidth, camera.viewportHeight);
            batch.end();
        }

        // 2. Pintamos el mapa encima del fondo
        if (levelManager != null) {
            levelManager.render(camera);
        }

        // --- PINTAMOS LOS COLECCIONABLES ---
        batch.begin();
        for (com.juego.domain.Collectible c : logicManager.getCollectibles()) {
            if (!c.isCollected()) {
                batch.draw(resourceManager.getItemFrame(), c.getBounds().x, c.getBounds().y, 16, 16);
            }
        }


        // --- PINTAR AL JUGADOR ---
        //Player p = logicManager.getPlayer();
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
        batch.draw(currentFrame, p.getPosition().x, p.getPosition().y, 16, 16);

        batch.end();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}

package com.juego.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Se encarga de cargar y gestionar todos los recursos (imágenes, sonidos, mapas)
 * usando el AssetManager de libGDX.
 */
public class ResourceManager {
    private final AssetManager assetManager;
    private Animation<TextureRegion> idleAnim;
    private Animation<TextureRegion> runAnim;

    public ResourceManager() {
        assetManager = new AssetManager();
        // Le indicamos a libGDX cómo debe leer los archivos .tmx de Tiled
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
    }

    /**
     * Encola todos los recursos que necesitamos para jugar.
     */
    public void loadAllResources() {

        // assetManager.load("images/tileset.png", Texture.class);
        assetManager.load("maps/nivel1.tmx", TiledMap.class);
        assetManager.load("images/onion_idle.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/onion_run.png", com.badlogic.gdx.graphics.Texture.class);

    }

    /**
     * Actualiza la carga de recursos. Devuelve true cuando ha terminado de cargar todo.
     */
    public boolean update() {
        return assetManager.update();
    }

    /**
     * Fuerza a que el juego se congele hasta que termine de cargar .
     */
    public void finishLoading() {
        assetManager.finishLoading();
    }

    public void createAnimations() {

        com.badlogic.gdx.graphics.Texture idleSheet = get("images/onion_idle.png");
        com.badlogic.gdx.graphics.Texture runSheet = get("images/onion_run.png");
        // Las cortamos en cuadraditos de 16x16
        TextureRegion[][] idleFrames = TextureRegion.split(idleSheet, 16, 16);
        TextureRegion[][] runFrames = TextureRegion.split(runSheet, 16, 16);
        // Creamos las animaciones con la fila 0
        // El 0.1f es la velocidad de la animación
        idleAnim = new Animation<>(0.2f, idleFrames[0]);
        runAnim = new Animation<>(0.1f, runFrames[0]);

        // Hacemos que se repitan en bucle infinito
        idleAnim.setPlayMode(Animation.PlayMode.LOOP);
        runAnim.setPlayMode(Animation.PlayMode.LOOP);
    }

    /**
     * Devuelve un recurso ya cargado dado su nombre/ruta.
     */
    public <T> T get(String fileName) {
        return assetManager.get(fileName);
    }

    /**
     * Libera la memoria cuando cerramos el juego.
     */
    public void dispose() {
        assetManager.dispose();
    }
    public Animation<TextureRegion> getIdleAnim() { return idleAnim; }
    public Animation<TextureRegion> getRunAnim() { return runAnim; }
}

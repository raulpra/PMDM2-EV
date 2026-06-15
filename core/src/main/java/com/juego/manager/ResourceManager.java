package com.juego.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Se encarga de cargar y gestionar todos los recursos (imágenes, sonidos, mapas)
 * usando el AssetManager de libGDX.
 */
public class ResourceManager {
    private final AssetManager assetManager;

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
        // assetManager.load("maps/nivel1.tmx", TiledMap.class);
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
}

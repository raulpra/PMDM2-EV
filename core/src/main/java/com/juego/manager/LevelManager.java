package com.juego.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.juego.Juego;

/**
 * Se encarga de cargar, guardar y dibujar el mapa creado en Tiled.
 */
public class LevelManager {
    private final Juego juego;
    private TiledMap currentMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    public LevelManager(Juego juego) {
        this.juego = juego;
    }

    public void loadLevel(String mapPath) {
        // Pedimos el mapa al ResourceManager
        currentMap = juego.getResourceManager().get(mapPath);

        // Creamos el objeto de libGDX que se encarga de pintar TiledMaps
        mapRenderer = new OrthogonalTiledMapRenderer(currentMap);
    }

    public void render(OrthographicCamera camera) {
        if (mapRenderer != null) {
            // Le decimos al mapa desde dónde lo estamos mirando (cámara)
            mapRenderer.setView(camera);
            mapRenderer.render();
        }
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    public float getMapPixelWidth() {
        if (currentMap == null) return 0;
        // Leemos cuántas casillas de ancho tiene el mapa
        int widthInTiles = currentMap.getProperties().get("width", Integer.class);
        // Leemos cuántos píxeles mide cada casilla (16)
        int tileWidth = currentMap.getProperties().get("tilewidth", Integer.class);

        return widthInTiles * tileWidth;
    }

    public void dispose() {
        if (mapRenderer != null) {
            mapRenderer.dispose();
        }
    }
}

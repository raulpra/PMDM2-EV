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

    public void loadLevel(String mapPath, LogicManager logicManager) {
        // Pedimos el mapa al ResourceManager
        currentMap = juego.getResourceManager().get(mapPath);

        // Creamos el objeto de libGDX que se encarga de pintar TiledMaps
        mapRenderer = new OrthogonalTiledMapRenderer(currentMap);

        // --- GENERADOR DE NIVELES (Cargar Objetos) ---
        // Limpiamos las listas por si venimos de otro nivel
        logicManager.getEnemies().clear();
        logicManager.getCollectibles().clear();

        // Leemos Enemigos
        com.badlogic.gdx.maps.MapLayer enemigosLayer = currentMap.getLayers().get("enemigos");
        if (enemigosLayer != null) {
            for (com.badlogic.gdx.maps.MapObject obj : enemigosLayer.getObjects()) {
                if (obj instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                    com.badlogic.gdx.math.Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) obj).getRectangle();
                    // Leemos la propiedad "tipo". Si no la has puesto, ponemos NORMAL por defecto
                    String tipoStr = obj.getProperties().get("tipo", "NORMAL", String.class);
                    com.juego.domain.EnemyType tipo;
                    try {
                        tipo = com.juego.domain.EnemyType.valueOf(tipoStr);
                    } catch (Exception e) {
                        tipo = com.juego.domain.EnemyType.NORMAL; // A prueba de fallos tipográficos
                    }
                    logicManager.getEnemies().add(new com.juego.domain.Enemy(rect.x, rect.y, tipo));
                }
            }
        }

        // Leemos Frutas
        com.badlogic.gdx.maps.MapLayer frutasLayer = currentMap.getLayers().get("frutas");
        if (frutasLayer != null) {
            for (com.badlogic.gdx.maps.MapObject obj : frutasLayer.getObjects()) {
                if (obj instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                    com.badlogic.gdx.math.Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) obj).getRectangle();
                    logicManager.getCollectibles().add(new com.juego.domain.Collectible(rect.x, rect.y));
                }
            }
        }
    }

    /**
     * Comprueba si una coordenada exacta X, Y cae encima de un bloque de la Capa de patrones 1
     */
    public boolean isSolid(float x, float y) {
        if (currentMap == null) return false;
        
        com.badlogic.gdx.maps.tiled.TiledMapTileLayer layer = 
            (com.badlogic.gdx.maps.tiled.TiledMapTileLayer) currentMap.getLayers().get("Capa de patrones 1");
            
        if (layer == null) return false;

        // Tiled funciona en una cuadrícula de 16x16
        int cellX = (int) (x / 16);
        int cellY = (int) (y / 16);

        // Si te sales de los bordes del mapa
        if (cellX < 0 || cellX >= layer.getWidth() || cellY < 0 || cellY >= layer.getHeight()) {
            return false;
        }

        // Buscamos si en esa casilla hay un bloque dibujado
        com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell cell = layer.getCell(cellX, cellY);
        return cell != null && cell.getTile() != null;
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

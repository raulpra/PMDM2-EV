package com.juego.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.juego.Juego;
import com.juego.domain.Collectible;
import com.juego.domain.Enemy;
import com.juego.domain.EnemyType;

/**
 * Se encarga de cargar, guardar y dibujar el mapa creado en Tiled.
 */
public class LevelManager {
    private final Juego juego;
    private TiledMap currentMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private int currentLevel = 1;
    private com.badlogic.gdx.graphics.Texture background;

    public LevelManager(Juego juego) {
        this.juego = juego;
    }

    public void loadLevel(String mapPath, LogicManager logicManager) {
        // Pedimos el mapa al ResourceManager
        currentMap = juego.getResourceManager().get(mapPath);

        // Creamos el objeto de libGDX que se encarga de pintar TiledMaps
        mapRenderer = new OrthogonalTiledMapRenderer(currentMap);

        // Cargamos el fondo personalizado desde Tiled
        if (background != null) {
            background.dispose(); // Limpiamos el fondo del nivel anterior
        }
        String bgPath = currentMap.getProperties().get("background", String.class);
        if (bgPath != null && !bgPath.isEmpty()) {
            background = new com.badlogic.gdx.graphics.Texture(bgPath);
        } else {
            background = new com.badlogic.gdx.graphics.Texture("images/fondo1.png"); // Fondo por defecto
        }

        // --- GENERADOR DE NIVELES (Cargar Objetos) ---
        // Limpiamos las listas por si venimos de otro nivel
        logicManager.getEnemies().clear();
        logicManager.getCollectibles().clear();

        // Leemos Enemigos
        MapLayer enemigosLayer = currentMap.getLayers().get("enemigos");
        if (enemigosLayer != null) {
            for (MapObject obj : enemigosLayer.getObjects()) {
                if (obj instanceof RectangleMapObject) {
                    com.badlogic.gdx.math.Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) obj).getRectangle();

                    // Comprobamos si tiene un tipo especial (FAST, TANK)
                    com.juego.domain.EnemyType tipo = com.juego.domain.EnemyType.NORMAL;
                    if (obj.getProperties().containsKey("tipo")) {
                        String tipoString = obj.getProperties().get("tipo", String.class);
                        if (tipoString.equals("FAST")) tipo = com.juego.domain.EnemyType.FAST;
                        else if (tipoString.equals("TANK")) tipo = com.juego.domain.EnemyType.TANK;
                    }

                    // Pasamos también la anchura (rect.width) para que sepa hasta dónde patrullar
                    logicManager.getEnemies().add(new com.juego.domain.Enemy(rect.x, rect.y, tipo, rect.width));
                }
            }
        }

        // Leemos Frutas
        MapLayer frutasLayer = currentMap.getLayers().get("frutas");
        if (frutasLayer != null) {
            for (MapObject obj : frutasLayer.getObjects()) {
                if (obj instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                    logicManager.getCollectibles().add(new Collectible(rect.x, rect.y));
                }
            }
        }
    }

    /**
     * Comprueba si una coordenada exacta X, Y cae encima de un bloque de la Capa de patrones 1
     */
    public boolean isSolid(float x, float y) {
        if (currentMap == null) return false;

        TiledMapTileLayer layer =
            (TiledMapTileLayer) currentMap.getLayers().get("Capa de patrones 1");

        if (layer == null) return false;

        // Tiled funciona en una cuadrícula de 16x16
        int cellX = (int) (x / 16);
        int cellY = (int) (y / 16);

        // Si te sales de los bordes del mapa
        if (cellX < 0 || cellX >= layer.getWidth() || cellY < 0 || cellY >= layer.getHeight()) {
            return false;
        }

        // Buscamos si en esa casilla hay un bloque dibujado
        TiledMapTileLayer.Cell cell = layer.getCell(cellX, cellY);
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
        int widthInTiles = currentMap.getProperties().get("width", Integer.class);
        int tileWidth = currentMap.getProperties().get("tilewidth", Integer.class);
        return widthInTiles * tileWidth;
    }

    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int level) { this.currentLevel = level; }
    public com.badlogic.gdx.graphics.Texture getBackground() { return background; }

    public void dispose() {
        if (mapRenderer != null) mapRenderer.dispose();
        if (background != null) background.dispose();
    }
}

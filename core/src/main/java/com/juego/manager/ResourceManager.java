package com.juego.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.juego.domain.EnemyType;

/**
 * Se encarga de cargar y gestionar todos los recursos (imágenes, sonidos, mapas)
 * usando el AssetManager de libGDX.
 */
public class ResourceManager {
    private final AssetManager assetManager;
    private Animation<TextureRegion> idleAnim;
    private Animation<TextureRegion> runAnim;

    private Animation<TextureRegion> animEnemyA;
    private Animation<TextureRegion> animEnemyB;
    private Animation<TextureRegion> animEnemyC;

    private TextureRegion itemFrame;

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
        assetManager.load("maps/nivel2.tmx", TiledMap.class);
        assetManager.load("images/onion_idle.png", Texture.class);
        assetManager.load("images/onion_run.png", Texture.class);
        assetManager.load("images/item.png", Texture.class);
        assetManager.load("images/enemy_a.png", Texture.class);
        assetManager.load("images/enemy_b.png", Texture.class);
        assetManager.load("images/enemy_c.png", Texture.class);
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

        Texture idleSheet = get("images/onion_idle.png");
        Texture runSheet = get("images/onion_run.png");
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

        Texture itemSheet = get("images/item.png");
        TextureRegion[][] itemFrames = TextureRegion.split(itemSheet, 16, 16);
        itemFrame = itemFrames[0][0]; // Cogemos la primera fruta


        animEnemyA = new Animation<>(0.1f, TextureRegion.split(get("images/enemy_a.png"), 16, 16)[0]);
        animEnemyB = new Animation<>(0.1f, TextureRegion.split(get("images/enemy_b.png"), 16, 32)[0]);
        animEnemyC = new Animation<>(0.1f, TextureRegion.split(get("images/enemy_c.png"), 16, 32)[0]);
        animEnemyA.setPlayMode(Animation.PlayMode.LOOP);
        animEnemyB.setPlayMode(Animation.PlayMode.LOOP);
        animEnemyC.setPlayMode(Animation.PlayMode.LOOP);


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
    public TextureRegion getItemFrame() { return itemFrame; }
    public Animation<TextureRegion> getEnemyAnim(EnemyType type) {
        if (type == EnemyType.FAST) return animEnemyB;
        if (type == EnemyType.TANK) return animEnemyC;
        return animEnemyA; // NORMAL por defecto
    }
}

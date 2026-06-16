package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private Music backgroundMusic;
    private Sound jumpSound;
    private Sound coinSound;
    private Sound hitSound;

    public SoundManager() {

        // Cargar música
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        backgroundMusic.setLooping(true); // Que se repita infinitamente
        backgroundMusic.setVolume(0.6f);  // Volumen

        // Cargar efectos
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.mp3"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));
}

    public void playMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    public void playJump() { if (jumpSound != null) jumpSound.play(0.6f); }
    public void playCoin() { if (coinSound != null) coinSound.play(0.76f); }
    public void playHit()  { if (hitSound != null)  hitSound.play(0.87f); }

    public void dispose() {
        if (backgroundMusic != null) backgroundMusic.dispose();
        if (jumpSound != null) jumpSound.dispose();
        if (coinSound != null) coinSound.dispose();
        if (hitSound != null) hitSound.dispose();
    }
}

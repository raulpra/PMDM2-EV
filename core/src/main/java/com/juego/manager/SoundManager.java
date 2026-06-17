package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private Music backgroundMusic;
    private Sound jumpSound;
    private Sound coinSound;
    private Sound hitSound;

    private Preferences prefs;
    private boolean musicEnabled;
    private boolean soundEnabled;

    public SoundManager() {
        prefs = Gdx.app.getPreferences("onion_lad_prefs");
        musicEnabled = prefs.getBoolean("music", true);
        soundEnabled = prefs.getBoolean("sound", true);

        // Cargar música
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        backgroundMusic.setLooping(true); // Que se repita infinitamente
        backgroundMusic.setVolume(0.6f);  // Volumen

        // Cargar efectos
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.mp3"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));
}

    public boolean isMusicEnabled() { return musicEnabled; }
    public boolean isSoundEnabled() { return soundEnabled; }

    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        prefs.putBoolean("music", enabled);
        prefs.flush();
        if (enabled) playMusic();
        else stopMusic();
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        prefs.putBoolean("sound", enabled);
        prefs.flush();
    }

    public void playMusic() {
        if (musicEnabled && backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    public void playJump() { if (soundEnabled && jumpSound != null) jumpSound.play(0.6f); }
    public void playCoin() { if (soundEnabled && coinSound != null) coinSound.play(0.76f); }
    public void playHit()  { if (soundEnabled && hitSound != null)  hitSound.play(0.87f); }

    public void dispose() {
        if (backgroundMusic != null) backgroundMusic.dispose();
        if (jumpSound != null) jumpSound.dispose();
        if (coinSound != null) coinSound.dispose();
        if (hitSound != null) hitSound.dispose();
    }
}

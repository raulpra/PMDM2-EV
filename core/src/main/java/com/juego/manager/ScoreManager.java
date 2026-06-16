package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {
    private static final String PREFS_NAME = "MiJuegoScores";
    private static final int MAX_SCORES = 5; // Guardaremos el Top 5

    public void addScore(int newScore) {
        // Obtenemos el archivo de guardado
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);

        // Cargamos las puntuaciones actuales
        List<Integer> scores = getTopScores();

        // Añadimos la nuestra
        scores.add(newScore);

        // Las ordenamos de MAYOR a MENOR
        scores.sort(Collections.reverseOrder());

        // Guardamos solo las 5 mejores
        for (int i = 0; i < Math.min(scores.size(), MAX_SCORES); i++) {
            prefs.putInteger("score_" + i, scores.get(i));
        }

        prefs.flush(); // Guarda físicamente en el disco
    }

    public List<Integer> getTopScores() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        List<Integer> scores = new ArrayList<>();

        for (int i = 0; i < MAX_SCORES; i++) {
            int score = prefs.getInteger("score_" + i, 0); // Devuelve 0 si no existe
            if (score > 0) {
                scores.add(score);
            }
        }
        return scores;
    }
}

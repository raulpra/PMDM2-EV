package com.juego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {
    private static final String PREFS_NAME = "MiJuegoScores";
    private static final int MAX_SCORES = 10;

    // Pequeña clase para guardar Nombre + Puntuación juntos
    public static class ScoreRecord implements Comparable<ScoreRecord> {
        public String name;
        public int score;

        public ScoreRecord(String name, int score) {
            this.name = name;
            this.score = score;
        }

        // Para ordenar de mayor a menor automáticamente
        @Override
        public int compareTo(ScoreRecord other) {
            return Integer.compare(other.score, this.score);
        }
    }

    public void addScore(String name, int newScore) {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        List<ScoreRecord> scores = getTopScores();

        scores.add(new ScoreRecord(name, newScore));
        Collections.sort(scores); // Ordena usando el compareTo

        // Guardamos el top 10
        for (int i = 0; i < Math.min(scores.size(), MAX_SCORES); i++) {
            prefs.putString("name_" + i, scores.get(i).name);
            prefs.putInteger("score_" + i, scores.get(i).score);
        }
        prefs.flush();
    }

    public List<ScoreRecord> getTopScores() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        List<ScoreRecord> scores = new ArrayList<>();

        for (int i = 0; i < MAX_SCORES; i++) {
            int score = prefs.getInteger("score_" + i, 0);
            if (score > 0) {
                // Si no hay nombre guardado, ponemos Anónimo por defecto
                String name = prefs.getString("name_" + i, "Anónimo");
                scores.add(new ScoreRecord(name, score));
            }
        }
        return scores;
    }
}

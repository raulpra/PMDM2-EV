package com.juego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.juego.Juego;

public class ConfigurationScreen implements Screen {
    private final Juego juego;
    private final Stage stage;

    public ConfigurationScreen(final Juego juego) {
        this.juego = juego;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table tabla = new Table(juego.getSkin());
        tabla.setFillParent(true);
        tabla.center();

        Label titulo = new Label("CONFIGURACION", juego.getSkin());
        titulo.setFontScale(2f);
        titulo.setColor(Color.GREEN);
        tabla.add(titulo).center().padBottom(50f).row();

        // Opción 1: Música
        final CheckBox cbMusic = new CheckBox(" Activar Musica", juego.getSkin());
        cbMusic.setChecked(juego.getSoundManager().isMusicEnabled());
        cbMusic.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.getSoundManager().setMusicEnabled(cbMusic.isChecked());
            }
        });
        tabla.add(cbMusic).left().padBottom(20f).row();

        // Opción 2: Efectos de Sonido
        final CheckBox cbSound = new CheckBox(" Activar Efectos de Sonido", juego.getSkin());
        cbSound.setChecked(juego.getSoundManager().isSoundEnabled());
        cbSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.getSoundManager().setSoundEnabled(cbSound.isChecked());
            }
        });
        tabla.add(cbSound).left().padBottom(50f).row();

        // Botón Volver
        TextButton btnVolver = new TextButton("VOLVER AL MENU", juego.getSkin());
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                juego.setScreen(new MainMenuScreen(juego));
            }
        });
        tabla.add(btnVolver).center().width(250).row();

        stage.addActor(tabla);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}

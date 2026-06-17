package com.juego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.juego.Juego;

public class InstructionsScreen implements Screen {
    private final Juego juego;
    private final Stage stage;

    public InstructionsScreen(final Juego juego) {
        this.juego = juego;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table tabla = new Table(juego.getSkin());
        tabla.setFillParent(true);
        tabla.center();

        Label titulo = new Label("INSTRUCCIONES", juego.getSkin());
        titulo.setFontScale(2f);
        titulo.setColor(Color.GREEN);
        tabla.add(titulo).center().padBottom(30f).row();

        Label l1 = new Label("- Usa A y D para moverte a izquierda y derecha.", juego.getSkin());
        Label l2 = new Label("- Pulsa W para saltar.", juego.getSkin());
        Label l3 = new Label("- Coge frutas para conseguir puntos extras.", juego.getSkin());
        Label l4 = new Label("- Evita a los enemigos o perderas vidas.", juego.getSkin());
        Label l5 = new Label("- Llega hasta el final del mapa para avanzar de nivel.", juego.getSkin());
        Label l6 = new Label("- Pulsa ESCAPE durante el juego para pausar.", juego.getSkin());

        tabla.add(l1).left().padBottom(10f).row();
        tabla.add(l2).left().padBottom(10f).row();
        tabla.add(l3).left().padBottom(10f).row();
        tabla.add(l4).left().padBottom(10f).row();
        tabla.add(l5).left().padBottom(10f).row();
        tabla.add(l6).left().padBottom(40f).row();

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

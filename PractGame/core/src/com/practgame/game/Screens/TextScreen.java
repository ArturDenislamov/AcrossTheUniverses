package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

//TODO class created for text CutScenes, it's under construction
public class TextScreen implements Screen {
    private Label titleLabel;
    private Stage stage;
    private Table table;

    private float WORLD_W = 1280;
    private float WORLD_H = 720;

    public TextScreen(){
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new FitViewport(WORLD_W, WORLD_H));
        table = new Table();
        titleLabel = new Label("", skin, "title");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}

package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.practgame.game.PractGame;


public class PauseScreen implements Screen {
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720; // using 16/9 ( 1280X720, 1920X1080, ... )

    private Stage stage;
    private PractGame maingame;

    private TextButton toGameButton;
    private TextButton toMenuButton;
    private TextButton toSettingsButton;

    Skin skin;
    private final float scale = 2.5f;

    public PauseScreen(PractGame game){
        maingame = game;
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        toGameButton = new TextButton("Play", skin);
        toGameButton.setTransform(true);
        toGameButton.scaleBy(scale);

        toMenuButton = new TextButton("Menu", skin);
        toMenuButton.setTransform(true);
        toMenuButton.scaleBy(scale);

        toSettingsButton = new TextButton("Settings", skin);
        toSettingsButton.setTransform(true);
        toSettingsButton.scaleBy(scale);

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        toGameButton.setPosition(WORLD_WIDTH/2 - toGameButton.getWidth(), WORLD_HEIGHT/2, Align.center);
        toMenuButton.setPosition(WORLD_WIDTH/2  - toMenuButton.getWidth(), WORLD_HEIGHT/2 - WORLD_HEIGHT/3, Align.center);
        toSettingsButton.setPosition(WORLD_WIDTH/2 - toSettingsButton.getWidth(), WORLD_HEIGHT/2 - WORLD_HEIGHT/6, Align.center);

        ClickListener playListener  = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maingame.musicManager.play();
                maingame.setScreen(maingame.playScreen);
            }
        };
        toGameButton.addListener(playListener);
        stage.addActor(toGameButton);

        ClickListener menuListener  = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maingame.musicManager.setSound("title.ogg");
                maingame.setScreen(maingame.menuLevel);
            }
        };
        toMenuButton.addListener(menuListener);
        stage.addActor(toMenuButton);

        ClickListener settingsListener  = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maingame.musicManager.pause();
                maingame.settingsScreen.fromPause = true;
                maingame.setScreen(maingame.settingsScreen);
            }
        };
        toSettingsButton.addListener(settingsListener);
        stage.addActor(toSettingsButton);
    }

    @Override
    public void render(float delta) {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
            stage.dispose();
            skin.dispose();
    }
}

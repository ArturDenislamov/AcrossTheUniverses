package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.practgame.game.PractGame;


public class PauseScreen implements Screen {

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720; // using 16/9 ( 1280X20, 1920X1080 )

    Texture backgroundtexture;
    private Stage stage;
    private PractGame maingame;

    private TextButton toGameButton;
    private TextButton toMenuButton;

    Texture toGameTexture;
    Texture toMenuTexture;

    Skin skin;
    float bsize = 5;


    public PauseScreen(PractGame game){
        maingame = game;
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        /*
        backgroundtexture = maingame.manager.get("pause/pause.png");
        toGameTexture = maingame.manager.get("pause/pause_play.png");
        toMenuTexture = maingame.manager.get("pause/pause_menu.png");
        toGameButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(toGameTexture)));
        toMenuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(toMenuTexture)));
        */
        toGameButton = new TextButton("Play", skin);
        toGameButton.setTransform(true);
        toGameButton.scaleBy(2.5f);
        toMenuButton = new TextButton("Menu", skin);
        toMenuButton.setTransform(true);
        toMenuButton.scaleBy(2.5f);
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
    }
    @Override
    public void show() {
            Gdx.input.setInputProcessor(stage);
            toGameButton.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2, Align.center);
            toMenuButton.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2 - WORLD_HEIGHT/6, Align.center);

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
    }

    @Override
    public void render(float delta) {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
            toMenuTexture.dispose();
            toGameTexture.dispose();
            backgroundtexture.dispose();
            stage.dispose();
    }
}

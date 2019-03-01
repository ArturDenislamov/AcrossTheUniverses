package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import  com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.practgame.game.PractGame;
import com.practgame.game.utils.Multilanguage;



public class StartScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720; // TODO maybe better 16/9 ( 1280X20, 1920X1080 )

    Texture backgroundtexture;
    private Stage stage;
    private Texture playTexture;
    private Texture playPressedTexture;
    private ImageButton playButton;
    private PractGame maingame;
    Skin skin;

   public  StartScreen(PractGame maingame){
        this.maingame = maingame;
       // this.skin = maingame.skinM;
    }


    @Override
    public void show() {
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        backgroundtexture = new Texture("mainMenuWall_hdpi_2.png");
        Image background = new Image(backgroundtexture);
        stage.addActor(background);
        playTexture = new Texture("ui/play.png");
        playPressedTexture = new Texture("ui/playDown.png");
        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playPressedTexture)), new TextureRegionDrawable(new TextureRegion(playTexture)));

        playButton.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2, Align.center);

        ClickListener playListener  = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maingame.setScreen(maingame.menuLevel);
            }
        };
        playButton.addListener(playListener);
        stage.addActor(playButton);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }
}

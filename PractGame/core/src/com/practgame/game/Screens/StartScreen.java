package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import  com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.practgame.game.PractGame;
import com.practgame.game.Tween.ImageAccessor;
import com.practgame.game.Tween.ImageButtonAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;


public class StartScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720; // using 16/9 ( 1280X20, 1920X1080...)

    private Texture backgroundtexture;
    private Stage stage;
    private Texture playTexture, playPressedTexture;
    private Texture settingsTexture, settingsPressedTexture;
    private Texture newGameTexture, newGamePressedTexture;
    private ImageButton playButton, settingsButton, newGameButton;
    private PractGame maingame;

    private TweenManager tweenManager; // UniversalTweenManager library, for tweening ( interpolation )

    private Sound clickSound;

   public  StartScreen(PractGame practGame){
        maingame = practGame;
        tweenManager = new TweenManager();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(false);
        backgroundtexture = maingame.manager.get("mainMenuWall_hdpi_2.png");
        Image background = new Image(backgroundtexture);
        background.getColor().a = 0;
        stage.addActor(background);

        playTexture = maingame.manager.get("ui/play.png");
        playPressedTexture = maingame.manager.get("ui/playDown.png");
        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playPressedTexture)),
                new TextureRegionDrawable(new TextureRegion(playTexture)));
        playButton.getColor().a = 0; // needed for fading in

        playButton.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2, Align.center);

        ClickListener playListener  = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maingame.setScreen(maingame.menuLevel);
                String rand ="sound/switch" + ((int)(Math.random()*2 + 1)) + ".wav"; // random click sound
                clickSound = maingame.manager.get(rand);
                clickSound.play(maingame.playScreen.soundVolume);
            }
        };
        playButton.addListener(playListener);
        stage.addActor(playButton);

        settingsTexture = maingame.manager.get("ui/settings.png");
        settingsPressedTexture = maingame.manager.get("ui/settingsDown.png");
        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsTexture)),
                new TextureRegionDrawable(new TextureRegion(settingsPressedTexture)));
        settingsButton.getColor().a = 0;

        settingsButton.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2 - WORLD_HEIGHT/3, Align.center);
        ClickListener settingsListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maingame.setScreen(maingame.settingsScreen);
                String rand ="sound/switch" + ((int)(Math.random()*2 + 1)) + ".wav";
                clickSound = maingame.manager.get(rand);
                clickSound.play(maingame.playScreen.soundVolume);
            }
        };
        settingsButton.addListener(settingsListener);

        stage.addActor(settingsButton);

        newGameTexture = maingame.manager.get("ui/newGame.png");
        newGamePressedTexture = maingame.manager.get("ui/newGameDown.png");
        newGameButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(newGameTexture)),
                new TextureRegionDrawable(new TextureRegion(newGamePressedTexture)));
        newGameButton.getColor().a = 0;

        newGameButton.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2 - WORLD_HEIGHT/6, Align.center);

        ClickListener newGameListener  = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String rand ="sound/switch" + ((int)(Math.random()*2 + 1)) + ".wav";
                clickSound = maingame.manager.get(rand);
                clickSound.play(maingame.playScreen.soundVolume);

                Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
                Dialog dialog = new Dialog("", skin, "dialog"){ // libgdx dialog, like in android
                  public void result(Object agree){
                        if((Boolean) agree){
                            maingame.cleanSafeData(); // NewGame confirmed
                        } else {
                            // do nothing
                        }
                  }
                };
                dialog.setScale(2);
                dialog.text("Are you sure?");
                dialog.button("Definitely", true);
                dialog.button("No", false);
                dialog.show(stage);
                dialog.setPosition(WORLD_WIDTH/2 - dialog.getWidth(), WORLD_HEIGHT/2);
            }
        };

        newGameButton.addListener(newGameListener);
        stage.addActor(newGameButton);

        Tween.registerAccessor(Image.class, new ImageAccessor());
        Tween.registerAccessor(ImageButton.class, new ImageButtonAccessor());

        Tween.to(background, ImageAccessor.ALPHA, 1).target(1).start(tweenManager);
        Tween.to(playButton, ImageAccessor.ALPHA, 1).target(1).start(tweenManager);
        Tween.to(settingsButton, ImageAccessor.ALPHA, 1).target(1).start(tweenManager);
        Tween.to(newGameButton, ImageAccessor.ALPHA, 1).target(1).start(tweenManager);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        tweenManager.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        backgroundtexture.dispose();
    }
}

package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.practgame.game.PractGame;
import com.practgame.game.Sprites.Gun;
import com.practgame.game.Utils.AppPreferences;

import java.util.HashMap;


public class GunScreen implements Screen {
    private final float WORLD_WIDTH = 1280;
    private final float WORLD_HEIGHT = 720;

    Stage stage;
    Texture backgroundTexture;
    float bscale = 2.8f;
    TextButton acr130Button;
    TextButton redLineButton;
    TextButton infinityButton;
    TextButton acceleratorButton;
    TextButton tpsl2Button;

    Skin skin;

    HashMap <String, Gun> guns;

    Sound errSound;
    Sound pickSound;

    AssetManager manager;
    PractGame maingame;
    float soundVolume;

    private final Preferences prefs = Gdx.app.getPreferences(AppPreferences.PREFS_NAME);

    public GunScreen(HashMap <String, Gun> gunMap, PractGame game){
        maingame = game;
        manager = maingame.manager;
        pickSound = manager.get("sound/switch1.wav");
        errSound = manager.get("sound/err.wav");

        backgroundTexture = new Texture("gunScreen/background.png");
        Image background = new Image(backgroundTexture);
        guns = gunMap;
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        acr130Button = new TextButton("ACR130", skin, "default");
        acr130Button.setTransform(true);
        acr130Button.scaleBy(bscale);

        redLineButton = new TextButton("RedLine", skin, "default");
        redLineButton.setTransform(true);
        redLineButton.scaleBy(bscale);

        infinityButton = new TextButton("Infinity", skin, "default");
        infinityButton.setTransform(true);
        infinityButton.scaleBy(bscale);

        acceleratorButton = new TextButton("Accelerator", skin, "default");
        acceleratorButton.setTransform(true);
        acceleratorButton.scaleBy(bscale);

        tpsl2Button = new TextButton("TPS-L2", skin, "default");
        tpsl2Button.setTransform(true);
        tpsl2Button.scaleBy(bscale);

        acr130Button.setPosition(WORLD_WIDTH/2 - acr130Button.getWidth(), WORLD_HEIGHT/2 + WORLD_HEIGHT/6, Align.center);
        redLineButton.setPosition(WORLD_WIDTH/2 - redLineButton.getWidth(),
                WORLD_HEIGHT/2, Align.center);
        infinityButton.setPosition(WORLD_WIDTH/2 - infinityButton.getWidth(),
                WORLD_HEIGHT/2 - WORLD_HEIGHT/6, Align.center);
        acceleratorButton.setPosition(WORLD_WIDTH/2 - acceleratorButton.getWidth(),
                WORLD_HEIGHT/2 - WORLD_HEIGHT/3, Align.center);
        tpsl2Button.setPosition(WORLD_WIDTH/2 - tpsl2Button.getWidth(),
                WORLD_HEIGHT/2 - (WORLD_HEIGHT *2 / 3), Align.center);

        Label titleLabel = new Label("Guns", skin, "title"); // TODO out of use at the moment
        titleLabel.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT - titleLabel.getHeight());

        acr130Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!guns.get("acr130").isLocked()) {
                    Gdx.app.log("GunScreen", "ACR130 activated");
                    prefs.putString(AppPreferences.PREFS_GUN, "acr130");
                    prefs.flush();
                    pickSound.play(soundVolume);
                    maingame.playScreen.player.updateGun();
                    maingame.setScreen(maingame.menuLevel);
                }
            }
        });

        redLineButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!guns.get("redLine").isLocked()) {
                    Gdx.app.log("GunScreen", "RedLine activated");
                    prefs.putString(AppPreferences.PREFS_GUN, "redLine");
                    prefs.flush();
                    pickSound.play(soundVolume);
                    maingame.playScreen.player.updateGun();
                    maingame.setScreen(maingame.menuLevel);
                } else {
                    errSound.play(soundVolume);
                }
            }
        });

        infinityButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!guns.get("infinity").isLocked()) {
                    Gdx.app.log("GunScreen", "Infinity activated");
                    prefs.putString(AppPreferences.PREFS_GUN, "infinity");
                    prefs.flush();
                    pickSound.play(soundVolume);
                    maingame.playScreen.player.updateGun();
                    maingame.setScreen(maingame.menuLevel);
                } else {
                    errSound.play(soundVolume);
                }
            }
        });

        acceleratorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!guns.get("accelerator").isLocked()) {
                    Gdx.app.log("GunScreen", "Accelerator activated");
                    prefs.putString(AppPreferences.PREFS_GUN, "accelerator");
                    prefs.flush();
                    pickSound.play(soundVolume);
                    maingame.playScreen.player.updateGun();
                    maingame.setScreen(maingame.menuLevel);
                } else {
                    errSound.play(soundVolume);
                }
            }
        });

        tpsl2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!guns.get("tpsl2").isLocked()) {
                    Gdx.app.log("GunScreen", "TPS-L2 activated");
                    prefs.putString(AppPreferences.PREFS_GUN, "tpsl2");
                    prefs.flush();
                    pickSound.play(soundVolume);
                    maingame.playScreen.player.updateGun();
                    maingame.setScreen(maingame.menuLevel);
                } else {
                    errSound.play(soundVolume);
                }
            }
        });

        background.setFillParent(true);
        stage.addActor(background);

        stage.addActor(acr130Button);
        stage.addActor(redLineButton);
        stage.addActor(infinityButton);
        stage.addActor(acceleratorButton);
        stage.addActor(tpsl2Button);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        soundVolume = maingame.playScreen.soundVolume;

        if(guns.get("infinity").isLocked())
           infinityButton.setVisible(false);
        else
            infinityButton.setVisible(true);

        if(guns.get("redLine").isLocked())
            redLineButton.setVisible(false);
        else
            redLineButton.setVisible(true);

        if(guns.get("accelerator").isLocked())
            acceleratorButton.setVisible(false);
        else
            acceleratorButton.setVisible(true);

        if(guns.get("tpsl2").isLocked())
            tpsl2Button.setVisible(false);
        else
            tpsl2Button.setVisible(true);
    }

    @Override
    public void render(float delta) {
        stage.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            maingame.setScreen(maingame.menuLevel);
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {}
}

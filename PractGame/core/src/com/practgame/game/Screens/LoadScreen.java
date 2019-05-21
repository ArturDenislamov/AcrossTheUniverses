package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.practgame.game.PractGame;
import com.practgame.game.Sprites.Gun;
import com.practgame.game.Tween.ImageAccessor;
import com.practgame.game.Utils.AppPreferences;
import com.practgame.game.Utils.LevelInfo;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class LoadScreen implements Screen {
    public AssetManager manager;
    private long startTime;
    private PractGame maingame;
    private Image background;

    private Stage stage;
    private TweenManager tweenManager;

    public LoadScreen(PractGame practGame){
        maingame = practGame;
        maingame.setScreen(this);
        startTime = TimeUtils.millis();
        manager = new AssetManager();
        stage = new Stage();

        loadAssets();

        background = new Image(new Texture("loading.gif"));
        background.setColor(background.getColor().r, background.getColor().g, background.getColor().b, 0);
        stage.addActor(background);
    }

    public void loadAssets(){

        manager.load("mainMenuWall_hdpi_2.png" , Texture.class);
        //ui
        manager.load("ui/back.png", Texture.class);
        manager.load("ui/first.png", Texture.class);
        manager.load("ui/play.png", Texture.class);
        manager.load("ui/playDown.png", Texture.class);
        manager.load("ui/second.png", Texture.class);
        manager.load("ui/settings.png", Texture.class);
        manager.load("ui/settingsDown.png", Texture.class);
        manager.load("ui/third.png", Texture.class);
        manager.load("ui/newGame.png", Texture.class);
        manager.load("ui/newGameDown.png", Texture.class);
        // maps
        //TODO add here maps
        maingame.levelList1.add(new LevelInfo("maps/lv1_1.tmx"));
        maingame.levelList1.add(new LevelInfo("maps/lv1_2.tmx"));
        maingame.levelList1.add(new LevelInfo("maps/lv1_3.tmx"));
        maingame.levelList1.add(new LevelInfo("maps/lv1_4.tmx"));
        maingame.levelList2.add(new LevelInfo("maps/lv2_1.tmx"));
        maingame.levelList3.add(new LevelInfo("maps/lv1_1.tmx"));
        //Character
        manager.load("Character/bullet_texture.png", Texture.class);
        //game controls
        manager.load("gc/aDark.png", Texture.class);
        manager.load("gc/aLight.png", Texture.class);
        manager.load("gc/rightDark.png", Texture.class);
        manager.load("gc/rightLight.png", Texture.class);
        manager.load("gc/leftDark.png", Texture.class);
        manager.load("gc/leftLight.png", Texture.class);
        manager.load("gc/bDark.png", Texture.class);
        manager.load("gc/bLight.png", Texture.class);

        //music and sound
        manager.load("sound/acr130.ogg", Sound.class);
        manager.load("music/title.ogg", Music.class);
        manager.load("sound/switch1.wav", Sound.class);
        manager.load("sound/switch2.wav", Sound.class);
        manager.load("music/world1.ogg", Music.class);
        manager.load("sound/slide.wav", Sound.class);
        manager.load("sound/noAmmo.wav", Sound.class);
        manager.load("sound/reload.wav", Sound.class);
        manager.load("sound/redLine.ogg", Sound.class);
        manager.load("sound/err.wav", Sound.class);
        manager.load("music/world2.ogg", Music.class);

        //guns
        maingame.gunMap.put("acr130", new Gun("acr130"));
        maingame.gunMap.get("acr130").unlock();
        maingame.gunMap.put("redLine", new Gun("redLine"));
        //if gun is already unlocked - unlocking the gun
        if(Gdx.app.getPreferences(AppPreferences.PREFS_NAME).getBoolean(AppPreferences.PREFS_IS_REDLINE_UNLOCKED, false)){
            maingame.gunMap.get("redLine").unlock();
        }

        manager.finishLoading(); // forcing asynchronous load
    }

    @Override
    public void show(){
        //using UniversalTweenEngine for fading in
        tweenManager = new TweenManager();
        Tween.registerAccessor(Image.class, new ImageAccessor());
        Tween.to(background, ImageAccessor.ALPHA, 2).target(1).start(tweenManager);
    }

    @Override
    public void render(float delta){
        //code to render splash here
        stage.draw();
        tweenManager.update(delta);

        //check if assets are loaded and time greater than 2 seconds
        if(manager.update() && TimeUtils.timeSinceMillis(startTime) > 2000){
            maingame.setScreen(maingame.startScreen);
        }
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
        manager.dispose();
    }
}

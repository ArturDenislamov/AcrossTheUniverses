package com.practgame.game.Screens;

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
import com.practgame.game.Utils.LevelInfo;


public class LoadScreen implements Screen {
    public AssetManager manager;
    private long startTime;
    private PractGame maingame;

    private Stage stage;

    public LoadScreen(PractGame practGame){
        maingame = practGame;
        maingame.setScreen(this);
        startTime = TimeUtils.millis();
        manager = new AssetManager();

        stage = new Stage();

        loadAssets();

        stage.addActor(new Image(new Texture("loading.gif")));
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
        // maps
        //TODO add here maps
        maingame.levelList1.add(new LevelInfo("maps/lv1_1.tmx"));
        maingame.levelList1.add(new LevelInfo("maps/lv1_2.tmx"));
        maingame.levelList1.add(new LevelInfo("maps/lv1_3.tmx"));
        maingame.levelList1.add(new LevelInfo("maps/lv1_4.tmx"));
        maingame.levelList2.add(new LevelInfo("maps/lv2_1.tmx"));
        //maingame.levelList3.add(new LevelInfo("maps/lv3_1.tmx"));

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
        manager.load("sound/acr130.wav", Sound.class);
        manager.load("music/title.ogg", Music.class);
        manager.load("sound/switch1.wav", Sound.class);
        manager.load("sound/switch2.wav", Sound.class);
        manager.load("music/world1.ogg", Music.class);
        manager.load("sound/slide.wav", Sound.class);
        manager.load("sound/noAmmo.wav", Sound.class);
        manager.load("sound/reload.wav", Sound.class);
        manager.load("sound/redLine.wav", Sound.class);
        manager.load("sound/err.wav", Sound.class);

        //pause screen
        manager.load("pause/pause_menu.png", Texture.class);
        manager.load("pause/pause_play.png", Texture.class);
        manager.load("pause/pause.png", Texture.class);

        //guns
        maingame.gunMap.put("acr130", new Gun("acr130"));
        maingame.gunMap.get("acr130").unlock();
        maingame.gunMap.put("redLine", new Gun("redLine"));

        manager.finishLoading();
    }

    @Override
    public void show(){}

    @Override
    public void render(float delta){
        //code to render splash here
        stage.draw();

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

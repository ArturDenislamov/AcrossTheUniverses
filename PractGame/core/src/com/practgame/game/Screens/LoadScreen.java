package com.practgame.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.practgame.game.PractGame;
import com.practgame.game.Utils.LevelInfo;

import java.util.logging.Logger;


public class LoadScreen implements Screen {

    public AssetManager manager;
    private long startTime;
    private PractGame maingame;

    private Animation <Texture> loadingAnimation;
    private Stage stage;

    private final Logger LOGGER = Logger.getLogger(LoadScreen.class.getName());

    public LoadScreen(PractGame practGame){
        maingame = practGame;
        maingame.setScreen(this);
        startTime = TimeUtils.millis();
        manager = new AssetManager();

        stage = new Stage();

        loadAssets();

            // Loading animation or picture
       // Array<Texture> frames = new Array <Texture>();
     //   frames.add(new Texture("loading.gif"));
      //  loadingAnimation = new Animation<Texture>(1.0f, frames);
     //   frames.clear();

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
        //maingame.levelList3.add(new LevelInfo("maps/lv1_1.tmx"));

        //Character
        manager.load("Character/bullet_texture.png", Texture.class);
       // manager.load("Character/Character.pack", TextureAtlas.class);
       // manager.load("Character/Character.png", Texture.class); //TODO commented because of the game realization
       // manager.load("Character/gun.png", Texture.class);
     //   manager.load("Character/gun_redline.png", Texture.class);

        //game controls
        manager.load("gc/aDark.png", Texture.class);
        manager.load("gc/aLight.png", Texture.class);
        manager.load("gc/rightDark.png", Texture.class);
        manager.load("gc/rightLight.png", Texture.class);
        manager.load("gc/leftDark.png", Texture.class);
        manager.load("gc/leftLight.png", Texture.class);
        manager.load("gc/bDark.png", Texture.class);
        manager.load("gc/bLight.png", Texture.class);

        manager.finishLoading();
        LOGGER.info("Loading finished");

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta){
        //code to render splash here
        stage.draw();


        //check if assets are loaded and time greater than 10 seconds
        if(manager.update() && TimeUtils.timeSinceMillis(startTime) > 4000){ // 10 seconds is too long
            LOGGER.info("Loading screen changed");
            maingame.setScreen(maingame.startScreen);
        }
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

    }
}

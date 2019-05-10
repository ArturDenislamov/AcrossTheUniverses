package com.practgame.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;
import com.practgame.game.Scenes.Hud;
import com.practgame.game.Scenes.WindowManager;
import com.practgame.game.Sprites.Bullet;
import com.practgame.game.Sprites.Invader;
import com.practgame.game.Sprites.MovingBlock;
import com.practgame.game.Sprites.Player;
import com.practgame.game.Utils.AppPreferences;
import com.practgame.game.Utils.Controller;
import com.practgame.game.Utils.LevelWorldCreator1;
import com.practgame.game.Utils.WorldContactListener;

import java.util.ArrayList;


public class PlayScreen implements Screen {
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    public PractGame maingame;
    private Hud hud;
    private final float SCREEN_W = 160;
    private final float SCREEN_H = 90;
    private TextureAtlas atlas;
    private TextureAtlas gunAtlas;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    public Player player;
    private Controller controller;
    private LevelWorldCreator1 creator;

    private WindowManager windowManager;

    private float mapPixelWidth;
    private float mapPixelHeight;

    public int shotsMade;

    private ArrayList <Bullet> bulletsArray;
    private ArrayList <Bullet> destroyBullets;

    Sound gunShot;
    Sound slideSound;
    Sound magSoung;
    Sound noAmmo;

    public boolean killed;
    public float soundVolume;

    private final Preferences prefs = Gdx.app.getPreferences(AppPreferences.PREFS_NAME);

    public PlayScreen(PractGame game){
        this.maingame = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(SCREEN_W / PractGame.PPM, SCREEN_H / PractGame.PPM, gamecam);
        world = new World(new Vector2(0, -10), true); // gravity vector
        mapLoader = new TmxMapLoader();
        gamecam.position.set(SCREEN_W / 2 / PractGame.PPM,SCREEN_H / 2 / PractGame.PPM,0);
        atlas  = new TextureAtlas("Character/Character.pack");
        gunAtlas = new TextureAtlas("Character/guns.pack");
        map = new TiledMap();
        windowManager = new WindowManager(maingame);
        player = new Player(world, this);
        b2dr = new Box2DDebugRenderer();

        hud = new Hud(game.batch);
        hud.updateBullets(player.bulletsAmount);

        world.setContactListener(new WorldContactListener(windowManager, world, maingame, this));

        shotsMade = prefs.getInteger(AppPreferences.PREF_SHOTS, 0);
        bulletsArray = new ArrayList<Bullet>(); // for active bullets
        destroyBullets = new ArrayList<Bullet>(); // for destroying bullets after hit

        slideSound = maingame.manager.get("sound/slide.wav");
        noAmmo = maingame.manager.get("sound/noAmmo.wav");
        magSoung = maingame.manager.get("sound/reload.wav");
        updateSoundVolume();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller.stage); // without this controller doesn't work
        Gdx.input.setCatchBackKey(true); // input with back key
    }



    public void setLevel(String mapWay){
        // deleting map objects
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(int i = 0; i < bodies.size; i++){
            world.destroyBody(bodies.get(i));
        }

        bulletsArray.clear();

        gamecam.position.set(SCREEN_W / 2 / PractGame.PPM,SCREEN_H / 2 / PractGame.PPM,0);

        map = mapLoader.load(mapWay);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PractGame.PPM);
        creator = new LevelWorldCreator1(this);
        player.definePlayer();
        controller = new Controller(maingame.manager); // this line influences gun shot ( after going to the next level)

        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        if(maingame.worldType == 1)
            shotsMade = 0;
      //  shotsMade = prefs.getInteger(AppPreferences.PREF_SHOTS);

        hud.updateBullets(player.gun.bulletsAmount - shotsMade);

        gunShot = maingame.manager.get("sound/"+player.gun.name+".wav");
       slideSound.play(soundVolume);
    }

    public void handleInput() {
        if(controller.isRightPressed())
            player.b2body.setLinearVelocity(new Vector2(0.75f, player.b2body.getLinearVelocity().y));
        else if(controller.isLeftPressed())
            player.b2body.setLinearVelocity(new Vector2(-0.75f, player.b2body.getLinearVelocity().y));
        else
            player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));

        if(controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0 && windowManager.onGround) {
            player.b2body.applyLinearImpulse(new Vector2(0, 2.5f), player.b2body.getWorldCenter(), true);
            windowManager.onGround = false;
        }

        if(controller.isBPressed() && windowManager.waitingForAnwser != "none"){
            windowManager.showWindow(windowManager.waitingForAnwser);
            windowManager.hideMessage();
        }

            // in this case player shooting
        if(controller.isBPressed() && windowManager.waitingForAnwser == "none"){
            if(shotsMade < player.bulletsAmount) {
                bulletsArray.add(new Bullet(world, player, maingame.manager));
                controller.bPressed = false; // for one click - one shot
                shotsMade++;
                hud.updateBullets(player.bulletsAmount - shotsMade);
                gunShot.play(soundVolume);
                Gdx.input.vibrate(150);
            }
        }

        if(controller.isBPressed() && windowManager.waitingForAnwser == "none" && shotsMade == player.bulletsAmount){
            noAmmo.stop();
            noAmmo.play(soundVolume);
        }

        for(int i = 0; i < bulletsArray.size(); i++){
            if(bulletsArray.get(i).b2bullet != null) {
                if (bulletsArray.get(i).b2bullet.getPosition().x <= 0 || bulletsArray.get(i).b2bullet.getPosition().x >= (mapPixelWidth / PractGame.PPM)) {
                    bulletsArray.get(i).dispose();
                    bulletsArray.remove(i);
                }
            } else {
                bulletsArray.get(i).dispose();
                bulletsArray.remove(i);
            }


        }

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            maingame.musicManager.pause();
            maingame.setScreen(maingame.pauseScreen);
        }

    }

    public void update(float dt) {
        handleInput();
        world.step(1 / 60f, 6, 2);


        //destroying bodies, cleaning destroy array (doing this out of step method)
       for(int i = 0; i < destroyBullets.size(); i++){
           world.destroyBody(destroyBullets.get(i).b2bullet);
           destroyBullets.get(i).b2bullet = null;
       }
       destroyBullets.clear();

        // smooth camera
        float lerp = 0.1f;
        Vector3 position = gamecam.position;
        if(player.b2body.getPosition().x >= SCREEN_W/(2*PractGame.PPM) && player.b2body.getPosition().x <= (mapPixelWidth/PractGame.PPM - SCREEN_W/(2*PractGame.PPM))) // 0.40 == 40 pixels
            position.x += (player.b2body.getPosition().x - position.x) * lerp;

        if(player.b2body.getPosition().y >= SCREEN_H/(2*PractGame.PPM) && player.b2body.getPosition().y <= (mapPixelHeight/PractGame.PPM - SCREEN_H/(2*PractGame.PPM) ) )
        position.y += (player.b2body.getPosition().y - position.y) * lerp;

        if(player.b2body.getPosition().y < SCREEN_H/(2*PractGame.PPM))
            position.y += (SCREEN_H/(2*PractGame.PPM) - position.y)*lerp;


        gamecam.update();
        // tell our renderer to draw only what our camera see in game world
        renderer.setView(gamecam);
        player.update(dt);

        for(int i = 0; i < bulletsArray.size(); i++){
            if(bulletsArray.get(i).b2bullet.getUserData() != "null") {
                bulletsArray.get(i).update();
                } else {
                bulletsArray.get(i).dispose();
                bulletsArray.remove(i);
            }
        }

        if( player.b2body.getPosition().y < -10 || killed){
            killed = false;
            maingame.changeScreen(maingame.worldType);
        }

        for(Invader invader : creator.getInvaders()) {
            invader.update(dt);
            //activating enemies
            if(invader.b2body != null)
                if (Math.abs(invader.b2body.getPosition().x - player.b2body.getPosition().x - 0.05f) <= SCREEN_W / (2 * PractGame.PPM)
                        && Math.abs(player.b2body.getPosition().y - (invader.b2body.getPosition().y + 0.0212)) <= 0.01 && invader.b2body != null) {
                    if(player.b2body.getPosition().x < invader.b2body.getPosition().x)
                         invader.velocity.set(-1, 0);
                    else
                        invader.velocity.set(1, 0);
                }
        }
    }



    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        maingame.batch.setProjectionMatrix(gamecam.combined);

        maingame.batch.begin();

        player.draw(maingame.batch);
        for(Invader invader : creator.getInvaders()) {
            invader.draw(maingame.batch);
        }

        for(MovingBlock block : creator.getMovingBlocks()){
            block.draw(maingame.batch);
        }
        maingame.batch.end();


        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

        windowManager.stage.draw();

            hud.stage.draw();

       //   b2dr.render(world, gamecam.combined); // if it is used, debug render lines appear

        maingame.batch.begin();
        maingame.batch.setProjectionMatrix(gamecam.combined);
        for(int i = 0; i < bulletsArray.size(); i++) {
            if(bulletsArray.get(i).b2bullet.getUserData() != "null") {
                bulletsArray.get(i).drawBullet(maingame.batch);
            } else {
                bulletsArray.get(i).dispose();
                bulletsArray.remove(i);
            }
        }
        maingame.batch.end();
    }


    public TextureAtlas getAtlas(){return atlas;}
    public TextureAtlas getGunAtlas(){return gunAtlas;}

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    public void destroy(Bullet bullet){
        if (!destroyBullets.contains(bullet)){
            destroyBullets.add(bullet);
            bulletsArray.remove(bullet);
        }
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    public void reload(){
        shotsMade = 0;
        magSoung.stop();
        magSoung.play(soundVolume);
        hud.updateBullets(player.bulletsAmount - shotsMade);
    }

    public void updateSoundVolume(){
        if(prefs.getBoolean(AppPreferences.PREF_SOUND_ENABLED, true))
            soundVolume = prefs.getFloat(AppPreferences.PREF_SOUND_VOL, 0.7f);
        else
            soundVolume = 0;
    }



    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
       // b2dr.dispose();
      //  gunShot.dispose(); TODO just commented
        noAmmo.dispose();
        slideSound.dispose();
        magSoung.dispose();
    }
}

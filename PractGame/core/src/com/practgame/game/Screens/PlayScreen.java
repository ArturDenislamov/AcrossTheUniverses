package com.practgame.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.practgame.game.Sprites.Player;
import com.practgame.game.Utils.Controller;
import com.practgame.game.Utils.LevelWorldCreator;
import com.practgame.game.Utils.WorldContactListener;

import java.util.logging.Logger;

public class PlayScreen implements Screen {
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private PractGame maingame;
    private Hud hud;
    private float SCREEN_W = 160;
    private float SCREEN_H = 90;
    private TextureAtlas atlas;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    Controller controller;

    public WindowManager windowManager;

    private float mapPixelWidth;
    private float mapPixelHeight;

    private final static Logger LOGGER = Logger.getLogger(PlayScreen.class.getName());

    public PlayScreen(PractGame game){ // TODO get width, height programmatically
        this.maingame = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(SCREEN_W / PractGame.PPM, SCREEN_H / PractGame.PPM, gamecam); // TODO 03/24 remake this!
        hud = new Hud(game.batch);
        world = new World(new Vector2(0, -10), true); // gravity vector
        mapLoader = new TmxMapLoader();
        gamecam.position.set(SCREEN_W / 2 / PractGame.PPM,SCREEN_H / 2 / PractGame.PPM,0);
        atlas  = new TextureAtlas("Character.pack");
        map = new TiledMap();
        windowManager = new WindowManager(maingame);
        player = new Player(world, this);

        world.setContactListener(new WorldContactListener(windowManager));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller.stage); // without this controller doesn't work
    }

    public void setLevel(String mapWay){
         // TODO maybe you should remake this
        // deleting map objects

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(int i = 0; i < bodies.size; i++){
            world.destroyBody(bodies.get(i));
        }

        gamecam.position.set(SCREEN_W / 2 / PractGame.PPM,SCREEN_H / 2 / PractGame.PPM,0);

        map = mapLoader.load(mapWay);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PractGame.PPM);
        new LevelWorldCreator(world, map);
        controller = new Controller();
        player.definePlayer();

        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        b2dr = new Box2DDebugRenderer();
    }


    public void handleInput() {
        if(controller.isRightPressed())
            player.b2body.setLinearVelocity(new Vector2(0.75f, player.b2body.getLinearVelocity().y));
        else if(controller.isLeftPressed())
            player.b2body.setLinearVelocity(new Vector2(-0.75f, player.b2body.getLinearVelocity().y));
        else
            player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));

        if(controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0)
            player.b2body.applyLinearImpulse(new Vector2(0, 2.5f), player.b2body.getWorldCenter(), true);

        if(controller.isBPressed() && windowManager.waitingForAnwser != "none"){
            windowManager.showWindow(windowManager.waitingForAnwser);
            windowManager.hideMessage();
        }

       LOGGER.info("player's position : " + player.b2body.getPosition().x + " " +player.b2body.getPosition().y);
        // TODO maybe you should remove this Log
    }

    public void update(float dt) {
        handleInput();
        world.step(1 / 60f, 6, 2); // TODO get width, height by code
        /*
        if(player.b2body.getPosition().x >= SCREEN_W/(2*PractGame.PPM) && player.b2body.getPosition().x <= (mapPixelWidth/PractGame.PPM - SCREEN_W/(2*PractGame.PPM))) // 0.40 == 40 pixels
            gamecam.position.x = player.b2body.getPosition().x;

        float smooth = SCREEN_H/PractGame.PPM;
        if(player.b2body.getPosition().y*smooth - 0.11f >= SCREEN_H/(2*PractGame.PPM) && player.b2body.getPosition().y <= (mapPixelHeight/PractGame.PPM - SCREEN_W/(2*PractGame.PPM) ) )// TODO so, this works, but it can be better 04/05
            gamecam.position.y =  player.b2body.getPosition().y*smooth - 0.11f;

*/
        float lerp = 0.1f; // 0.1f
        Vector3 position = gamecam.position;
        if(player.b2body.getPosition().x >= SCREEN_W/(2*PractGame.PPM) && player.b2body.getPosition().x <= (mapPixelWidth/PractGame.PPM - SCREEN_W/(2*PractGame.PPM))) // 0.40 == 40 pixels
            position.x += (player.b2body.getPosition().x - position.x) * lerp;

        if(player.b2body.getPosition().y >= SCREEN_H/(2*PractGame.PPM) && player.b2body.getPosition().y <= (mapPixelHeight/PractGame.PPM - SCREEN_W/(2*PractGame.PPM) ) )// TODO so, this works, but it can be better 04/05
        position.y += (player.b2body.getPosition().y - position.y) * lerp;

        if(player.b2body.getPosition().y < SCREEN_H/(2*PractGame.PPM))
            position.y += (SCREEN_H/(2*PractGame.PPM) - position.y)*lerp;

       // if(player.b2body.getPosition().y)



        gamecam.update();
        // tell our renderer to draw only what our camera see in game world
        renderer.setView(gamecam);
        player.update(dt);
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
        maingame.batch.end();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

        windowManager.stage.draw();

       //   b2dr.render(world, gamecam.combined); // if it is used, green lines appear
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}

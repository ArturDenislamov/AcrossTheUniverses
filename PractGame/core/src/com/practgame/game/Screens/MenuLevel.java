package com.practgame.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;
import com.practgame.game.Scenes.WindowManager;
import com.practgame.game.Sprites.Player;
import com.practgame.game.Utils.MenuWorldCreator;
import com.practgame.game.Utils.Controller;
import com.practgame.game.Utils.WorldContactListener;


public class MenuLevel implements Screen {
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private float SCREEN_W = 80;
    private float SCREEN_H = 45;
    private TextureAtlas atlas;

    private int mapPixelWidth;
    private int mapPixelHeight;

    public int currentFloor;


    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Player player;
    public Controller controller;

    public WindowManager windowManager;
    private WorldContactListener contactListener;

    private PractGame mainGame;

    public MenuLevel(PractGame practGame) {
        mainGame = practGame;
        atlas  = new TextureAtlas("Character/Character.pack");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(SCREEN_W / PractGame.PPM, SCREEN_H / PractGame.PPM, gamecam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/menu1.tmx"); // menu map, 260X60 pixels
        currentFloor = 1;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PractGame.PPM);
        gamecam.position.set(SCREEN_W / 2 / PractGame.PPM, SCREEN_H / 2 / PractGame.PPM, 0);

        world = new World(new Vector2(0, -10), true); // (in constructor - gravity vector)
        player = new Player(world, this);
        controller = new Controller(mainGame.manager);

        new MenuWorldCreator(this);

        windowManager = new WindowManager(mainGame);

        contactListener = new WorldContactListener(windowManager, world);
        world.setContactListener(contactListener);

        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        gamePort.setWorldSize(mapPixelHeight/PractGame.PPM*16/9, mapPixelHeight/PractGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(controller.stage); // without this controller doesn't respond

        if(windowManager.liftShown){
            Gdx.input.setInputProcessor(windowManager.stage);
        }
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

        if(controller.isBPressed() && windowManager.waitingForAnwser != "none"){ // in case than message is shown
            controller.touchUpAll();
            windowManager.showWindow(windowManager.waitingForAnwser);
            Gdx.app.log("MenuLevel", "TouchUpAll activated");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            mainGame.setScreen(mainGame.startScreen);
        }
    }

    public void update(float dt) {
        handleInput();
        world.step(1 / 60f, 6, 2);
        if(player.b2body.getPosition().x >= gamePort.getWorldWidth()/2 && player.b2body.getPosition().x <= mapPixelWidth/PractGame.PPM - gamePort.getWorldWidth()/2 ) // 0.40 == 40 pixels, that's why we use PPM
         gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();
        // tell our renderer to draw only what our camera see in game world
        renderer.setView(gamecam);
        player.update(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);
     //   controller.draw(); // this line was used in gameplay recording (it hides controllers under map)

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        mainGame.batch.setProjectionMatrix(gamecam.combined);
        mainGame.batch.begin();
        player.draw(mainGame.batch);
        mainGame.batch.end();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
             controller.draw();

        windowManager.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    public void changeMap(int floorNum){
        // deleting map objects
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(int i = 0; i < bodies.size; i++){
            world.destroyBody(bodies.get(i));
        }

        map = mapLoader.load("maps/menu"+floorNum+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PractGame.PPM); // PPM - pixels per meter
        new MenuWorldCreator(this);

        // moving player to the lift
        // method player.b2body.setTransform(...) can cause errors
        switch (floorNum){
            case 1:
                player.definePlayer(210,32);
                break;

            case 2:
                player.definePlayer(216, 32);
                break;

            case 3:
                player.definePlayer(210,32);
                break;
        }

        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class); // width in tiles (my tiles is 8 X 8 pixels)
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        gamePort.setWorldSize(mapPixelHeight/PractGame.PPM*16/9, mapPixelHeight/PractGame.PPM);
        gamePort.apply(); // applies the viewPort to the camera, (it is needed)
        float playerX = player.b2body.getPosition().x;
        gamecam.position.set(playerX > gamePort.getWorldWidth()/2 ? playerX : gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()/2, 0);
        gamecam.position.set(playerX < mapPixelWidth/PractGame.PPM - gamePort.getWorldWidth()/2 ? playerX : mapPixelWidth/PractGame.PPM - gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        currentFloor = floorNum;
    }


    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
        renderer.dispose();
        world.dispose();
        atlas.dispose();
        windowManager.dispose();
    }
}

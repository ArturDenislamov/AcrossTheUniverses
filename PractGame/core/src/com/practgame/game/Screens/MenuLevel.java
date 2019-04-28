package com.practgame.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;
import com.practgame.game.Scenes.WindowManager;
import com.practgame.game.Sprites.Player;
import com.practgame.game.Utils.MenuWorldCreator;
import com.practgame.game.Utils.Controller;
import com.practgame.game.Utils.WorldContactListener;


public class MenuLevel implements Screen {
    public OrthographicCamera gamecam;
    private Viewport gamePort;
    private float SCREEN_W = 80;
    private float SCREEN_H = 45;
    private TextureAtlas atlas;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    public Player player;
    public Controller controller;

   public WindowManager windowManager;

    private PractGame mainGame;


    public MenuLevel(PractGame practGame) {
        mainGame = practGame;
        atlas  = new TextureAtlas("Character/Character.pack");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(SCREEN_W / PractGame.PPM, SCREEN_H / PractGame.PPM, gamecam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/lv0.tmx"); // menu map, 260X60 pixels
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PractGame.PPM);
        gamecam.position.set(SCREEN_W / 2 / PractGame.PPM, SCREEN_H / 2 / PractGame.PPM, 0);

        world = new World(new Vector2(0, -10), true); // gravity vector
        player = new Player(world, this);
       // gamecam.position.y = (24*8 - );
      //  player.b2body.getPosition().set(player.b2body.getPosition().x, player.b2body.getPosition().y * 2);
        controller = new Controller(mainGame.manager);

        new MenuWorldCreator(this);

        windowManager = new WindowManager(mainGame);

        world.setContactListener(new WorldContactListener(windowManager, world));
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(false);
        Gdx.input.setInputProcessor(controller.stage); // without this controller doesn't work
        if(windowManager.waitingForAnwser != "none")
            Gdx.input.setInputProcessor(windowManager.stage);
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
        }
    }


    public void update(float dt) {
        handleInput();
        world.step(1 / 60f, 6, 2);
        if(player.b2body.getPosition().x >= 0.40 && player.b2body.getPosition().x <= 2.20) // 0.40 == 40 pixels
         gamecam.position.x = player.b2body.getPosition().x;


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

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
    }
}

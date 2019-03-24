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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;
import com.practgame.game.Scenes.WindowManager;
import com.practgame.game.Sprites.Player;
import com.practgame.game.Utils.B2WorldCreator;
import com.practgame.game.Utils.Controller;
import com.practgame.game.Utils.WorldContactListener;


public class MenuLevel implements Screen {
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private float SCREEN_W = 80; // TODO screen view, situation  improved 03/20
    private float SCREEN_H = 45;
    private TextureAtlas atlas;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    Controller controller;

   public WindowManager windowManager;

    private PractGame mainGame;


    public MenuLevel(PractGame practGame) {
        atlas  = new TextureAtlas("Character.pack");
        mainGame = practGame;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(SCREEN_W / PractGame.PPM, SCREEN_H / PractGame.PPM, gamecam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/lv0.tmx"); // menu map, 260X60 pixels
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PractGame.PPM);
        gamecam.position.set(SCREEN_W / 2 / PractGame.PPM, SCREEN_H / 2 / PractGame.PPM, 0); // TODO check situation with PPM

        world = new World(new Vector2(0, -10), true); // gravity vector
        b2dr = new Box2DDebugRenderer();
        player = new Player(world, this);
        controller = new Controller();

        new B2WorldCreator(world, map);

        windowManager = new WindowManager(mainGame.batch, mainGame);

        world.setContactListener(new WorldContactListener(windowManager));
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {
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

      //  b2dr.render(world, gamecam.combined); // if it is used, green lines appear

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

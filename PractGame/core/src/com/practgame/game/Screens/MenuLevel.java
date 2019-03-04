package com.practgame.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;
import com.practgame.game.Sprites.Player;
import com.practgame.game.Utils.B2WorldCreator;
import com.practgame.game.Utils.Controller;

public class MenuLevel implements Screen {
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private PractGame game;
    private float SCREEN_W = 80; // TODO you need to improve the situation about the screen view
    private float SCREEN_H = 45;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    Controller controller;

    private PractGame mainGame;

    public MenuLevel(PractGame practGame) {
        mainGame = practGame;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(SCREEN_W / PractGame.PPM, SCREEN_H / PractGame.PPM, gamecam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lv0.tmx"); // TODO change to menu.tmx 02/15
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PractGame.PPM);
        gamecam.position.set(SCREEN_W / 2 / PractGame.PPM, SCREEN_H / 2 / PractGame.PPM, 0);

        world = new World(new Vector2(0, -10), true); // gravity vector
        b2dr = new Box2DDebugRenderer();
        player = new Player(world);
        controller = new Controller();

        new B2WorldCreator(world, map);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller.stage); // 03/01 without this controller doesn't work

    }

    public void handleInput() {
        if(controller.isRightPressed())
            player.b2body.setLinearVelocity(new Vector2(1, player.b2body.getLinearVelocity().y));
        else if(controller.isLeftPressed())
            player.b2body.setLinearVelocity(new Vector2(-1, player.b2body.getLinearVelocity().y));
        else
            player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));

        if(controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0)
            player.b2body.applyLinearImpulse(new Vector2(0, 2.5f), player.b2body.getWorldCenter(), true);
    }


    public void update(float dt) {
        handleInput();
        world.step(1 / 60f, 6, 2);

         gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();
        // tell our renderer to draw only what our camera see in game world
        renderer.setView(gamecam);

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, gamecam.combined);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
        controller.draw();
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

    }
}

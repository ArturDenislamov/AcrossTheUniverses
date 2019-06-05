package com.practgame.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.practgame.game.PractGame;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Sprites.ActionBrick;
import com.practgame.game.Sprites.BlockTileObject;
import com.practgame.game.Sprites.Invader;
import com.practgame.game.Sprites.JumpBlock;
import com.practgame.game.Sprites.MovingBlock;


public class LevelWorldCreator {
    public Array<Invader> getInvaders() {
        return invaders;
    }
    public Array<MovingBlock> getMovingBlocks(){return movingBlocks;}

    private Array <Invader> invaders;
    private Array<MovingBlock> movingBlocks;

    TiledMap map;
    World world;
    PlayScreen playScreen;

    public LevelWorldCreator(PlayScreen playScreen) {
        this.playScreen = playScreen;
        invaders = new Array<Invader>();
        movingBlocks = new Array<MovingBlock>();
    }

    public void createWorld1(){
        map = playScreen.getMap();
        world = playScreen.getWorld();

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) { // ground
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new BlockTileObject(world, map, rect); // creating object using class
        }

        //creating all invaders
        invaders.clear();
        Gdx.app.log("LevelCreator", "Invaders array created");
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) { // invaders spawns
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            invaders.add(new Invader(playScreen, rect.getX()/ PractGame.PPM, rect.getY()/ PractGame.PPM));
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) { // reload blocks
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "ammo").setCategoryFilter(PractGame.RECHARGE_BIT);
        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) { // finish
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "next_level");
        }

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) { // extra, guns
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, map.getLayers().get(6).getName()).setCategoryFilter(PractGame.GUN_BIT);
        }

        movingBlocks.clear();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) { // moving blocks
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            movingBlocks.add(new MovingBlock(world, map, rect));
        }
    }

    public void createWorld2(){
        map = playScreen.getMap();
        world = playScreen.getWorld();
        invaders.clear();
        movingBlocks.clear();

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) { // ground
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new BlockTileObject(world, map, rect); // creating object using class
        }

        // under construction, no enemies in world 2 at this moment
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) { // enemy spawns
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
        //    enemies.add(new Soldier(playScreen, rect.getX()/ PractGame.PPM, rect.getY()/ PractGame.PPM));
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) { // reload blocks
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "ammo").setCategoryFilter(PractGame.RECHARGE_BIT);
        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) { // finish
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "next_level");
        }

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) { // extra, guns
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, map.getLayers().get(6).getName()).setCategoryFilter(PractGame.GUN_BIT);
        }

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) { // jumping block
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new JumpBlock(world, map, rect).setCategoryFilter(PractGame.JUMPBLOCK_BIT);
        }
    }

    public void createWorld3(){
        // under construction
        // at this time world 3 uses createWorld1() method
    }
}
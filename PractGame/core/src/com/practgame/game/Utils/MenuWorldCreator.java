package com.practgame.game.Utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.PractGame;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Sprites.ActionBrick;
import com.practgame.game.Sprites.BlockTileObject;

import jdk.nashorn.internal.ir.Block;

public class MenuWorldCreator {
    // for creating Menu Level

    public MenuWorldCreator(MenuLevel menuLevel) {
        World world = menuLevel.getWorld();
        TiledMap map = menuLevel.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) { //floor
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new BlockTileObject(world, map, rect); // creating object using class
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) { // in menu - lift
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "lift");
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) { // in menu - will be soon
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

             new BlockTileObject(world, map, rect);
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) { // in menu - guy
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

           new ActionBrick(world, map, rect, "lobby");

    }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) { // gun
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "gun");

        }

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) { // in menu - guy
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "portal");

        }

    }
}

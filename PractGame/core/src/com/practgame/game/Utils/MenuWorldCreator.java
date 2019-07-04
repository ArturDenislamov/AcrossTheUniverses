package com.practgame.game.Utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Sprites.ActionBrick;
import com.practgame.game.Sprites.BlockTileObject;


public class MenuWorldCreator {
    public MenuWorldCreator(MenuLevel menuLevel) {
        World world = menuLevel.getWorld();
        TiledMap map = menuLevel.getMap();

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new BlockTileObject(world, map, rect); // creating object using class
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) { // lift
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "lift");
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) { // level select screen
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ActionBrick(world, map, rect, "level_select");
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) { // lobby
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

           new ActionBrick(world, map, rect, "lobby");
        }
    }
}

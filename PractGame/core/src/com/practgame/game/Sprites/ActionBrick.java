package com.practgame.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;


public class ActionBrick extends InteractiveTileObject {
    public ActionBrick(World world, TiledMap map, Rectangle bounds, String name){
        super(world, map, bounds, true, "default");
        fixture.setUserData(name); // needed for access
    }
}

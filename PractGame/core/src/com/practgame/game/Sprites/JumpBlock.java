package com.practgame.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;


public class JumpBlock extends InteractiveTileObject { //they appear in world 2
    public JumpBlock(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds, false, "jumpingBlock");
    }
}

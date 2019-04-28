package com.practgame.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.PractGame;

public class BlockTileObject extends InteractiveTileObject {
    public BlockTileObject(World world,TiledMap map, Rectangle bounds) {
        super(world, map, bounds, false);
        setCategoryFilter(PractGame.DEFAULT_BIT);
    }
}

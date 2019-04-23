package com.practgame.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.PractGame;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Utils.Controller;

import java.util.logging.Logger;


public class ActionBrick extends InteractiveTileObject {
    private final static Logger LOGGER = Logger.getLogger(ActionBrick.class.getName());

    public ActionBrick(World world, TiledMap map, Rectangle bounds, String name){
        super(world, map, bounds, true);
        fixture.setUserData(name); // needed for access
    }


    /*
    public void onContact() {
        LOGGER.info("Collision");
    }
    */
}

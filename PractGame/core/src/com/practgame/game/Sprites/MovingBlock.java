package com.practgame.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.PractGame;


public class MovingBlock extends InteractiveTileObject { // it appears in world 1 level 3
    Sprite sprite;
    private Body body;

    public MovingBlock(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds, false, "movingBlock");
        setCategoryFilter(PractGame.DEFAULT_BIT);
        sprite = new Sprite(new Texture("maps/movingBlock.png"));
        sprite.setBounds(0,0, 8 / PractGame.PPM,8 / PractGame.PPM);

        this.body = super.getBody();
    }

    public void draw(SpriteBatch batch){
        sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2 - 0.01f);
        sprite.draw(batch);
    }
}

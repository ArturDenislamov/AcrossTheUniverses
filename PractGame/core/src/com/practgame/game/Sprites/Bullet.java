package com.practgame.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.PractGame;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Utils.WorldContactListener;

import java.util.logging.Logger;

public class Bullet extends Sprite{

    private Player player;
    private World world;

    public Body b2bullet;

   // public Sprite bullet_sprite;

    private  final static Logger LOGGER = Logger.getLogger(Bullet.class.getName());

    public Bullet(World world, Player player){
        this.player = player;
        this.world = world;

        BodyDef bdefB = new BodyDef();
        bdefB.type = BodyDef.BodyType.DynamicBody; // Kinematic bodies does not collide with static ones

        if(player.runningRight == true) {
            bdefB.position.set(player.b2body.getPosition().x + 0.09f, player.b2body.getPosition().y + 0.01f );
            bdefB.linearVelocity.set(200/ PractGame.PPM, 0); // 200 for default gun
        }
        if(player.runningRight == false) {
            bdefB.position.set(player.b2body.getPosition().x - 0.11f, player.b2body.getPosition().y + 0.01f);
            bdefB.linearVelocity.set(-200/ PractGame.PPM, 0);
        }


        FixtureDef fdefB = new FixtureDef();
        fdefB.friction = 0;
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(1/PractGame.PPM, 1/PractGame.PPM);
        fdefB.shape = shape;
        bdefB.bullet = true;
        fdefB.isSensor = true;

        b2bullet = world.createBody(bdefB);
        b2bullet.setBullet(true);
        b2bullet.setGravityScale(0f); // bullets are not affected by gravity
        b2bullet.createFixture(fdefB).setUserData(this); // needed for destroying object

       // bullet_sprite = new Sprite(new Texture("bullet_texture.png"));
        setTexture(new Texture("bullet_texture.png"));
        setBounds(0,0, 2/PractGame.PPM, 2/PractGame.PPM);
    }


    public void dispose(){
        world.destroyBody(b2bullet);
        b2bullet.setUserData("null");
        b2bullet = null;
    }

    public void update(){
        setPosition(b2bullet.getPosition().x - 0.01f, b2bullet.getPosition().y - 0.01f); // TODO changed for testing 04/13
     //   LOGGER.info("Bullet position set is : " + b2bullet.getPosition().x + " " + b2bullet.getPosition().y );
    }

    public void drawBullet(SpriteBatch batch){
        super.draw(batch);
    }

}

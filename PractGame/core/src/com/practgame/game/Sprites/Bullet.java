package com.practgame.game.Sprites;

import com.badlogic.gdx.assets.AssetManager;
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


public class Bullet extends Sprite{
    private World world;

    public Body b2bullet;
    private float bulletVelocity;

    public Bullet(World world, Player player, AssetManager manager, float bulletVelocity){
        Player player1 = player;
        this.world = world;
        this.bulletVelocity = player.gun.bulletVelocity;

        BodyDef bdefB = new BodyDef();
        bdefB.type = BodyDef.BodyType.DynamicBody; // Kinematic bodies does not collide with static ones

        if(player.runningRight == true) {
            bdefB.position.set(player1.b2body.getPosition().x + 0.09f, player1.b2body.getPosition().y + 0.01f );
            bdefB.linearVelocity.set(bulletVelocity/ PractGame.PPM, 0); // 200 for default gun
        }
        if(player.runningRight == false) {
            bdefB.position.set(player1.b2body.getPosition().x - 0.11f, player1.b2body.getPosition().y + 0.01f);
            bdefB.linearVelocity.set(-bulletVelocity/ PractGame.PPM, 0);
        }

        FixtureDef fdefB = new FixtureDef();
        fdefB.friction = 0;
        fdefB.density = 10000000;
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(1/PractGame.PPM, 1/PractGame.PPM);
        fdefB.shape = shape;
        // bdefB.bullet = true; // prevents body from tunneling
        fdefB.filter.categoryBits = PractGame.BULLET_BIT;
        fdefB.filter.maskBits = PractGame.DEFAULT_BIT | PractGame.ENEMY_BIT | PractGame.RECHARGE_BIT | PractGame.PLAYER_BIT;

        b2bullet = world.createBody(bdefB);
        b2bullet.setGravityScale(0); // bullets are not affected by gravity
        b2bullet.createFixture(fdefB).setUserData(this); // needed for destroying object

        setTexture((Texture) manager.get("Character/bullet_texture.png"));
        setBounds(0,0, 2/PractGame.PPM, 2/PractGame.PPM);
    }

    public Bullet(World world, Player player, AssetManager manager, float bulletVelocity, String typeOfGun){
        Player player1 = player;
        this.world = world;

        BodyDef bdefB = new BodyDef();
        bdefB.type = BodyDef.BodyType.DynamicBody; // Kinematic bodies does not collide with static ones
        bdefB.fixedRotation = true; // bullets don't rotate

        if(player.runningRight == true) {
            bdefB.position.set(player1.b2body.getPosition().x + 0.09f, player1.b2body.getPosition().y + 0.01f );
            bdefB.linearVelocity.set(bulletVelocity/ PractGame.PPM, 0); // 200 for default gun
        }
        if(player.runningRight == false) {
            bdefB.position.set(player1.b2body.getPosition().x - 0.11f, player1.b2body.getPosition().y + 0.01f);
            bdefB.linearVelocity.set(-bulletVelocity/ PractGame.PPM, 0);
        }

        FixtureDef fdefB = new FixtureDef();
        fdefB.friction = 0;
        fdefB.density = 1000000000; // bullets are small but heavy, player can stand on them
        Shape shape = new PolygonShape();
        if(typeOfGun.equals("platformGun")){
            ((PolygonShape) shape).setAsBox(10/ PractGame.PPM, 1 / PractGame.PPM);
        }else {
            ((PolygonShape) shape).setAsBox(1 / PractGame.PPM, 1 / PractGame.PPM);
        }
        fdefB.shape = shape;
        // bdefB.bullet = true; // prevents body from tunneling
        fdefB.filter.categoryBits = PractGame.BULLET_BIT;
        fdefB.filter.maskBits = PractGame.DEFAULT_BIT | PractGame.ENEMY_BIT | PractGame.RECHARGE_BIT | PractGame.PLAYER_BIT | PractGame.JUMPBLOCK_BIT;

        b2bullet = world.createBody(bdefB);
        b2bullet.setGravityScale(0); // bullets are not affected by gravity
        b2bullet.createFixture(fdefB).setUserData(this); // needed for destroying object

        if(typeOfGun.equals("platformGun")) {
            setTexture((Texture) manager.get("Character/bullet_platformGun.png"));
            setBounds(0,0, 20/PractGame.PPM, 2/PractGame.PPM);
        }else {
            setTexture((Texture) manager.get("Character/bullet_texture.png"));
            setBounds(0,0, 2/PractGame.PPM, 2/PractGame.PPM);
        }
    }


    public void dispose(){
        world.destroyBody(b2bullet);
        b2bullet.setUserData("null");
        b2bullet = null;
    }

    public void update(){
        setPosition(b2bullet.getPosition().x - 0.01f, b2bullet.getPosition().y - 0.01f);
    }

    public void drawBullet(SpriteBatch batch){
        super.draw(batch);
    }
}

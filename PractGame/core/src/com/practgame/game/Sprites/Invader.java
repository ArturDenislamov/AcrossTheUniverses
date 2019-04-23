package com.practgame.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.practgame.game.PractGame;
import com.practgame.game.Screens.PlayScreen;

public class Invader extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private TextureAtlas atlas;

    float x;
    float y;

    private boolean setToDestroy;
    private boolean destroyed;

    //TODO join different atlases
    public Invader(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.x = x;
        this.y = y;
        atlas = new TextureAtlas("Enemy/invader.pack");
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++){
            frames.add(new TextureRegion(atlas.findRegion("invader_right1"), i*12,0, 12, 21));
        }
        walkAnimation = new Animation(0.1f, frames);
        stateTime = 0;

        setToDestroy = false;
        destroyed = false;

<<<<<<< HEAD
        velocity = new Vector2(0, 0);
=======
        velocity = new Vector2(-1, 0);
        b2body.setActive(false);
>>>>>>> 24a5a2400f5d1b6f65b743acc502174597eed379
    }

    public void update(float dt){
        stateTime +=dt;

        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            b2body.setUserData(null); // just to ensure ourselves
            b2body = null;
            destroyed = true;
            setRegion(new TextureRegion(atlas.findRegion("invader_stand"), 0, 0, 12, 21));
            stateTime = 0;
        } else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()/2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
            setBounds(getX(), getY(), 12 / PractGame.PPM, 21 / PractGame.PPM);
        }

    }

    @Override
    public void draw(Batch batch) {
        if(!destroyed || stateTime < 1){ // killed enemy disappears after a second
            super.draw(batch);
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set( getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 0; // player doesn't stick to the walls (can be changed)
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(6/PractGame.PPM, 9.88f/PractGame.PPM);
        // shape.setRadius(6 / PractGame.PPM); // CircleShape changed to BoxShape
        fdef.shape = shape;
        fdef.filter.categoryBits = PractGame.ENEMY_BIT;
        fdef.filter.maskBits = PractGame.DEFAULT_BIT | PractGame.PLAYER_BIT | PractGame.BULLET_BIT;
        b2body.createFixture(fdef).setUserData(this); // important to set userData
        b2body.setGravityScale(12f); // falling faster
    }

    @Override
    public void damage() {
         setToDestroy = true;
    }
}

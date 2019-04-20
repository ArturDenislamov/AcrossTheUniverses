package com.practgame.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    }

    public void update(float dt){
        stateTime +=dt;
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()*0.56f);
        setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        setBounds(getX(),getY(),12/PractGame.PPM,21/PractGame.PPM);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set( 32/ PractGame.PPM, 32/ PractGame.PPM); // were 32 and 32 (it suited normally)
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 0; // player doesn't stick to the walls (can be changed)
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(6/PractGame.PPM, 10.5f/PractGame.PPM);
        // shape.setRadius(6 / PractGame.PPM); // CircleShape changed to BoxShape
        fdef.shape = shape;
        fdef.filter.categoryBits = PractGame.ENEMY_BIT;
        fdef.filter.maskBits = PractGame.DEFAULT_BIT | PractGame.PLAYER_BIT;
        b2body.createFixture(fdef).setUserData("enemy");

    }
}

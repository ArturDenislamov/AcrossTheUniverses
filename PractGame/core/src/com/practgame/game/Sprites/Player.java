package com.practgame.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.practgame.game.PractGame;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Screens.PlayScreen;


public class Player extends Sprite {
    public enum State {FALLING , JUMPING , STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Animation <TextureRegion> playerRun;
    private float stateTimer;
    private boolean runningRight;

    public Player (World world, MenuLevel level){ // TODO I think you need to make universal class for level 03/08
        super(level.getAtlas().findRegion("stand"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        // for(int i = 2; i <=3; i++) // TODO remake this 03/09
        //   frames.add(new TextureRegion(getTexture(), i*13, 0, 13,26));
        frames.add(new TextureRegion(getTexture(), 27, 0, 13, 26)); // Remake this! 03/08
        frames.add(new TextureRegion(getTexture(), 41, 0, 13, 26));
        playerRun = new Animation <TextureRegion> (0.14f, frames); // 0.14f is suitable
        frames.clear();

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 55,0,13,26);
        setBounds(0,0, 13 / PractGame.PPM,26 / PractGame.PPM);
        setRegion(playerStand);

    }

    public Player (World world, PlayScreen level){ // TODO I think you need to make universal class for level 03/08
        super(level.getAtlas().findRegion("stand"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        // for(int i = 2; i <=3; i++) // TODO remake this 03/09
        //   frames.add(new TextureRegion(getTexture(), i*13, 0, 13,26));
        frames.add(new TextureRegion(getTexture(), 27, 0, 13, 26)); // Remake this! 03/08
        frames.add(new TextureRegion(getTexture(), 41, 0, 13, 26));
        playerRun = new Animation <TextureRegion> (0.14f, frames); // 0.14f is suitable
        frames.clear();

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 55,0,13,26);
        setBounds(0,0, 13 / PractGame.PPM,26 / PractGame.PPM);
        setRegion(playerStand);

    }





    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()*0.52f  ); // numbers are needed for correct image position 03/08
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region =  playerRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
                default:
                    region = playerStand;
                break;
        }
            //flipping player horizontally if he is running left or right
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX() ) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void definePlayer(){
                BodyDef bdef = new BodyDef();
                bdef.position.set( 32/ PractGame.PPM, 32/ PractGame.PPM); // were 32 and 32 (it suited normally)
                bdef.type = BodyDef.BodyType.DynamicBody;
                b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();
          //  fdef.friction = 0; // player doesn't stick to the walls (can be changed)
            Shape shape = new PolygonShape();
            ((PolygonShape) shape).setAsBox(4/PractGame.PPM, 12/PractGame.PPM);
           // shape.setRadius(6 / PractGame.PPM); // CircleShape changed to BoxShape
            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData("player");
    }
}

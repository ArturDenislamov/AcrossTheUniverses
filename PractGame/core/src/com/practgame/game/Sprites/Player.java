package com.practgame.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.practgame.game.PractGame;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Utils.AppPreferences;


public class Player extends Sprite {
    public enum State {STANDING, RUNNING};
    public State currentState;
    public State previousState;
    private PlayScreen playScreen;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Animation <TextureRegion> playerRun;
    private float stateTimer;
    public boolean runningRight;

    private Sprite gunSprite;

    public Gun gun;
    private String gunName;

    public int bulletsAmount;

    public Player (World world, MenuLevel level){
        super(level.getAtlas().findRegion("stand"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 27, 0, 13, 26));
        frames.add(new TextureRegion(getTexture(), 41, 0, 13, 26));
        playerRun = new Animation <TextureRegion> (0.14f, frames); // 0.14f is suitable
        frames.clear();

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 55,0,13,26);
        setBounds(0,0, 13 / PractGame.PPM,26 / PractGame.PPM);
        setRegion(playerStand);
    }

    public Player (World world, PlayScreen level){
        super(level.getAtlas().findRegion("stand"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        playScreen = level;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 27, 0, 13, 26));
        frames.add(new TextureRegion(getTexture(), 41, 0, 13, 26));
        playerRun = new Animation <TextureRegion> (0.14f, frames); // 0.14f is suitable
        frames.clear();

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 55,0,13,26);
        setBounds(0,0, 13 / PractGame.PPM,26 / PractGame.PPM);
        setRegion(playerStand);

        gunName = Gdx.app.getPreferences(AppPreferences.PREFS_NAME).getString(AppPreferences.PREFS_GUN, "acr130");
        gun = playScreen.maingame.gunMap.get(gunName);
        gunSprite = new Sprite();
        gunSprite.setRegion(playScreen.getGunAtlas().findRegion(gunName));
        gunSprite.setBounds(0, 0, gunSprite.getRegionWidth() / PractGame.PPM, gunSprite.getRegionHeight() / PractGame.PPM );

        bulletsAmount = gun.bulletsAmount;
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()*0.52f  ); // numbers are needed for correct sprite position
        setRegion(getFrame(dt));

        if(gunSprite != null) {
            if(runningRight) {
                gunSprite.setPosition(this.getX() + this.getRegionWidth()/PractGame.PPM - 0.01f, b2body.getPosition().y - 0.02f);
            }
            if(!runningRight) {
                gunSprite.setPosition(this.getX() - gunSprite.getRegionWidth()/PractGame.PPM + 0.01f, b2body.getPosition().y - 0.02f);
            }
        }
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

        // flipping player horizontally if he is running left or right
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);

            if(gunSprite != null)
            if(!gunSprite.isFlipX())
            gunSprite.flip(true, false);

            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX() ) {
            region.flip(true, false);

            if(gunSprite != null)
            if(gunSprite.isFlipX())
            gunSprite.flip(true, false);

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
        runningRight = true; // player is spawned looking to the right
        if(gunSprite != null)
            if(gunSprite.isFlipX()) // gun is also spawned looking to the right
                gunSprite.flip(true, false);

        BodyDef bdef = new BodyDef();
        bdef.position.set( 32 / PractGame.PPM, 32 / PractGame.PPM); // default position
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 0; // player doesn't stick to the walls (can be changed)
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(4/PractGame.PPM, 12/PractGame.PPM);
        fdef.filter.categoryBits = PractGame.PLAYER_BIT; // it is defined as a player
        fdef.filter.maskBits = PractGame.DEFAULT_BIT | PractGame.RECHARGE_BIT | PractGame.GUN_BIT |
                PractGame.ENEMY_BIT | PractGame.JUMPBLOCK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("player");

        // for not slipping with head
        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-3 / PractGame.PPM, -12 / PractGame.PPM) , new Vector2( 3/ PractGame.PPM, -12 / PractGame.PPM)  );
        fdef.shape = feet;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("feet");
    }

    //for certain position
    public void definePlayer(float x, float y){
        runningRight = true; // player is spawned looking to the right
        if(gunSprite != null)
            if(gunSprite.isFlipX()) // gun is also spawned looking to the right, needed to fix glitch
                gunSprite.flip(true, false);

        BodyDef bdef = new BodyDef();
        bdef.position.set( x / PractGame.PPM, y / PractGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 0; // player doesn't stick to the walls (can be changed)
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(4/PractGame.PPM, 12/PractGame.PPM);
        fdef.filter.categoryBits = PractGame.PLAYER_BIT; // it is defined as a player
        fdef.filter.maskBits = PractGame.DEFAULT_BIT | PractGame.RECHARGE_BIT | PractGame.GUN_BIT |
                PractGame.ENEMY_BIT | PractGame.JUMPBLOCK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("player");

        // for not slipping with head. player can jump, when feet is on ground
        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-3 / PractGame.PPM, -12 / PractGame.PPM) , new Vector2( 3/ PractGame.PPM, -12 / PractGame.PPM)  );
        fdef.shape = feet;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("feet");
    }

    public void updateGun(){
        gunName = Gdx.app.getPreferences(AppPreferences.PREFS_NAME).getString(AppPreferences.PREFS_GUN);
        gun = playScreen.maingame.gunMap.get(gunName);
        bulletsAmount = gun.bulletsAmount;
        gunSprite.setRegion(playScreen.getGunAtlas().findRegion(gunName));
        gunSprite.setBounds(0, 0, gunSprite.getRegionWidth() / PractGame.PPM, gunSprite.getRegionHeight() / PractGame.PPM );
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);

        if(gunSprite != null)
            gunSprite.draw(batch);
    }
}

package com.practgame.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.PractGame;
import com.practgame.game.Scenes.WindowManager;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Sprites.Bullet;
import com.practgame.game.Sprites.Invader;


public class WorldContactListener implements ContactListener {
    private WindowManager windowManager;
    public boolean messageShown;
    private World world;
    private PlayScreen playScreen;
    private PractGame maingame;

    private static final Preferences prefs = Gdx.app.getPreferences(AppPreferences.PREFS_NAME);

    public WorldContactListener(WindowManager wm, World world){
        windowManager = wm;
        this.world = world;
    }

    public WorldContactListener(WindowManager wm, World world, PractGame game, PlayScreen playScreen){
        windowManager = wm;
        this.world = world;
        maingame = game;
        this.playScreen = playScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        // defining collision using bit masks
        switch (cDef){
            case PractGame.ENEMY_BIT | PractGame.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == PractGame.ENEMY_BIT) {
                    ((Invader) fixA.getUserData()).damage();
                    playScreen.destroy((Bullet) fixB.getUserData());
                }
                else {
                    ((Invader) fixB.getUserData()).damage();
                    playScreen.destroy((Bullet) fixA.getUserData());
                }
                break;

            case PractGame.ENEMY_BIT | PractGame.DEFAULT_BIT:
                if(fixA.getFilterData().categoryBits == PractGame.ENEMY_BIT)
                    ((Invader)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Invader)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case PractGame.PLAYER_BIT | PractGame.ENEMY_BIT:
                playScreen.killed = true;
                break;

            case PractGame.ENEMY_BIT | PractGame.ENEMY_BIT:
                ((Invader)fixA.getUserData()).reverseVelocity(true, false);
                ((Invader)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case PractGame.PLAYER_BIT | PractGame.RECHARGE_BIT:
                playScreen.reload();
                break;

            case PractGame.PLAYER_BIT | PractGame.GUN_BIT:
                String name = playScreen.getMap().getLayers().get(6).getName();
                if(!maingame.gunMap.get(name).isLocked()) {
                    Gdx.app.log("WorldContactListener", "Gun already unlocked");
                    windowManager.showMessage("gun");
                    messageShown = true;
                    break;
                }

                maingame.gunMap.get(name).unlock();
                // getting corresponding preferences key to gun
                String tmp = "PREFS_IS_" + name.toUpperCase() + "_UNLOCKED";
                prefs.putBoolean(tmp, true);
                prefs.flush();

                windowManager.showMessage("gun");
                messageShown = true;
                break;
        }

        if (contact.getFixtureB().getUserData() instanceof Bullet && contact.getFixtureA().getUserData() != "next_level") {
            playScreen.destroy((Bullet) contact.getFixtureB().getUserData());
        }

        // needed for correct bullet collision control
        if (contact.getFixtureA().getUserData() instanceof Bullet && contact.getFixtureB().getUserData() != "next_level") {
            playScreen.destroy((Bullet) contact.getFixtureA().getUserData());
        }

        if(("feet").equals(fixA.getUserData()) || ("feet").equals(fixB.getUserData())){
            windowManager.onGround = true; // player can jump once, when he is onGround
        }

        //jump block collision with feet-object, (this blocks appear in world 2)
        if(("feet").equals(fixA.getUserData()) && fixB.getFilterData().categoryBits == PractGame.JUMPBLOCK_BIT) {
            fixA.getBody().setLinearVelocity(fixA.getBody().getLinearVelocity().x,0);
            fixA.getBody().applyLinearImpulse(new Vector2(0, 3.5f), fixA.getBody().getWorldCenter(), true);
            Gdx.app.log("WorldContactList", "JUMP Block collision");
        }else if(("feet").equals(fixB.getUserData()) && fixA.getFilterData().categoryBits == PractGame.JUMPBLOCK_BIT){
            fixB.getBody().setLinearVelocity(fixB.getBody().getLinearVelocity().x,0);
            fixB.getBody().applyLinearImpulse(new Vector2(0, 3.5f), fixB.getBody().getWorldCenter(), true);
            Gdx.app.log("WorldContactList", "JUMP Block collision");
        }

        if(("player").equals(fixA.getUserData()) || ("player").equals(fixB.getUserData())){
            Fixture player = ("player").equals(fixA.getUserData()) ? fixA : fixB;
            Fixture object = fixA == player ? fixB : fixA;

            if(("lobby").equals(object.getUserData())){
                windowManager.showMessage("lobby");
                messageShown = true;
            }

            if(("lift").equals(object.getUserData())){
                windowManager.showMessage("lift");
                windowManager.waitingForAnwser = "lift";
                messageShown = true;
            }

            if(("level_select").equals(object.getUserData())){
                windowManager.showMessage("level_select");
                windowManager.waitingForAnwser = "level_select";
                messageShown = true;
            }

            if(("next_level".equals(object.getUserData()))){
                windowManager.showMessage("next_level");
                windowManager.waitingForAnwser = "next_level";
                messageShown = true;
            }
        }
    }

    @Override
    public void endContact(Contact contact){
            if(messageShown && (("player").equals(contact.getFixtureA().getUserData()) || ("player").equals(contact.getFixtureB().getUserData()))) {
                messageShown = false;
                windowManager.hideMessage();
            }
            if((("player").equals(contact.getFixtureA().getUserData()) || ("player").equals(contact.getFixtureB().getUserData())))
            windowManager.waitingForAnwser = "none";
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}

package com.practgame.game.Utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.practgame.game.Scenes.WindowManager;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Sprites.ActionBrick;
import com.practgame.game.Sprites.BlockTileObject;

import java.util.logging.Logger;


public class WorldContactListener implements ContactListener {
    private final static Logger LOGGER = Logger.getLogger(WorldContactListener.class.getName());
    private WindowManager windowManager;
    private boolean messageShown;

        public WorldContactListener(WindowManager wm){
            windowManager = wm;
        }
    @Override
    public void beginContact(Contact contact) {
        LOGGER.info("Contact began");
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB(); // TODO  why null error ? 03/17

        if(("player").equals(fixA.getUserData()) || ("player").equals(fixB.getUserData())){
            Fixture player = ("player").equals(fixA.getUserData()) ? fixA : fixB;
            Fixture object = fixA == player ? fixB : fixA;

            LOGGER.info("player : "  + player.getUserData());
            LOGGER.info("object : "  + object.getUserData());


            if(("lobby").equals(object.getUserData())){
                windowManager.showMessage("lobby");
                messageShown = true;
            }

            if(("lift").equals(object.getUserData())){
                windowManager.showMessage("lift");
                windowManager.waitingForAnwser = "lift";
                messageShown = true;
            }
        }
    }


    @Override
    public void endContact(Contact contact){
            if(messageShown) {
                messageShown = false;
                windowManager.hideMessage();
            }
            windowManager.waitingForAnwser = "none";
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

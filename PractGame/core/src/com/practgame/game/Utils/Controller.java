package com.practgame.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;

import java.util.logging.Logger;


public class Controller {
    private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private float bsize = 15;
    Viewport viewport;
    public  Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera cam;
    ImageButton upbutton;

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public Controller() {
        LOGGER.info("controller created");
        cam = new OrthographicCamera();
        viewport = new FitViewport(160, 90, cam);
        stage = new Stage(viewport, PractGame.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();
        Texture leftTexture = new Texture("gc/shadedDark26.png"); // TODO maybe you should make ImageButtons for all of the buttons 03/04
        Texture upPressedTexture = new Texture("gc/shadedLight26.png");
        upbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(leftTexture)), new TextureRegionDrawable(new TextureRegion(upPressedTexture)));
        upbutton.addListener(new InputListener(){
                                   @Override
                                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                       upPressed = true;
                                       LOGGER.info("up pressed");
                                       return true;
                                   }

                                   @Override
                                   public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                       upPressed = false;
                                   }
                               });

        /*
        Image upImg = new Image(new Texture("gc/shadedDark26.png"));
        upImg.setSize(bsize,bsize);
        upImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                LOGGER.info("up pressed");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });
        */

        Image downImg = new Image(new Texture("gc/shadedDark27.png"));
        downImg.setSize(bsize,bsize);
        downImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                LOGGER.info("down pressed");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("gc/shadedDark25.png"));
        rightImg.setSize(bsize,bsize);
        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                LOGGER.info("right pressed");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("gc/shadedDark24.png"));
        leftImg.setSize(bsize,bsize);
        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                LOGGER.info("left pressed"); // TODO REMOVE maybe
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        table.add();
        table.add(upbutton).size(bsize, bsize);
        table.add();
        table.row().pad(3, 3, 3, 3);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.row().padBottom(5);
        table.add();
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        table.add();
        table.pack();

        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width , height);
    }

}

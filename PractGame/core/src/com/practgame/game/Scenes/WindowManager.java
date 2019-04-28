package com.practgame.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;
import com.practgame.game.Sprites.Player;

import java.util.logging.Logger;

public class WindowManager implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Label messageLabel;
    private Table table, levelTable;
    public String waitingForAnwser = "none";
    private ImageButton firstW, secondW, thirdW, backW;
    float bsize = 50;

    public boolean onGround;

    private String floor;

    private PractGame maingame;

    private final static Logger LOGGER = Logger.getLogger(WindowManager.class.getName());

    public WindowManager(PractGame maingame){
        this.maingame = maingame;

        viewport = new FitViewport(320, 180, new OrthographicCamera()); // remember 16/9! 02/11
        stage = new Stage(viewport, maingame.batch);

        onGround = false;
        floor = "first";
    }



    public void showMessage(String tag){
        table = new Table();
        table.top();
        table.setFillParent(true);

        messageLabel = new Label( "", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add().expandX().padTop(10);
        table.add(messageLabel).expandX().padTop(10);
        table.add().expandX().padTop(10);
        stage.addActor(table);


        if(tag.equals("lobby")){
            LOGGER.info("in showMessage method -- Hello");
            messageLabel.setText("Hello, James!");
        }

        if(tag.equals("portal")){
            LOGGER.info("portal message appears");
            messageLabel.setText("B - go to mission");
        }

        if(tag.equals("next_level")){
            LOGGER.info("next level message");
            messageLabel.setText("B - next level");
        }
        if(tag.equals("reload")){
            messageLabel.setText("Reload");
        }

        if(tag.equals("gun")){
            messageLabel.setText("B - change gun");
        }

        if(tag.equals("lift")){
            messageLabel.setText("B - go to lift");

        }

    }

    public void hideMessage(){
        messageLabel.setText("");
    }

    public void hideWindow(){
        LOGGER.info("hideWindow activated");
        stage.clear(); // ok, (stage.clear();)it partially works 03/23
        waitingForAnwser = "none";
        maingame.menuLevel.show();
    }

    // this method also does actions, not only shows windows
    public void showWindow(String tag){
        if(tag.equals("portal")){
            levelTable = new Table();
            messageLabel.setText("");
            levelTable.setFillParent(true);
            levelTable.center();
            Gdx.input.setInputProcessor(stage);
            Texture first = new Texture("ui/first.png");
            Texture second = new Texture("ui/second.png");
            Texture third = new Texture("ui/third.png");
            Texture back = new Texture("ui/back.png");
            firstW = new ImageButton(new TextureRegionDrawable(new TextureRegion(first)));
            firstW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    LOGGER.info("Button First World pressed");
                    hideWindow(); // if it is located after line 111, controller doesn't work
                    maingame.changeScreen(1); // interesting, but normal
                        maingame.musicManager.setSound("world1.ogg");
                        waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                         //   waitingForAnwser = "none";
                }
            });

            secondW = new ImageButton(new TextureRegionDrawable(new TextureRegion(second)));

            /*
            secondW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    LOGGER.info("Exit level selection pressed");
                    hideWindow();
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    //   waitingForAnwser = "none";
                }
            });
            */


            thirdW = new ImageButton(new TextureRegionDrawable(new TextureRegion(third)));

            /*
            thirdW.addListener(new InputListener(){

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    LOGGER.info("Button Third World pressed");
                    maingame.changeScreen(3);
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                }
            });
            */

            backW = new ImageButton(new TextureRegionDrawable(new TextureRegion(back)));


            backW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    LOGGER.info("Exit level selection pressed");
                    hideWindow();
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    //   waitingForAnwser = "none";
                }
            });


            levelTable.row().padLeft(5).padRight(5);
            levelTable.add(firstW).size(bsize, bsize).padTop(10);
            levelTable.add(secondW).size(bsize, bsize).padTop(10);
            levelTable.add(thirdW).size(bsize, bsize).padTop(10);
            levelTable.add(backW).size(bsize, bsize).padTop(10);
            levelTable.pack();
            stage.addActor(levelTable);
            LOGGER.info("Level table added");
        }

        if(tag.equals("next_level")){
            if(maingame.worldType == 1)
                maingame.levelLine1++;
            else if(maingame.worldType == 2)
                maingame.levelLine2++;
            else
                maingame.levelLine3++;

            maingame.changeScreen(maingame.worldType);
        }

        if(tag.equals("lift")){
            maingame.menuLevel.controller.bPressed = false;
            Player player = maingame.menuLevel.player;
            LOGGER.info("Lift activated");
            if(floor.equals("first")){
                player.b2body.setTransform(player.b2body.getPosition().x, player.b2body.getPosition().y / 2, 0);
                maingame.menuLevel.gamecam.position.y = 45 / 3 / PractGame.PPM + 0.06f;
                floor = "second";
            } else if(floor.equals("second")){
                player.b2body.setTransform(player.b2body.getPosition().x, player.b2body.getPosition().y * 2 + 0.3f, 0);
                maingame.menuLevel.gamecam.position.y = player.b2body.getPosition().y + 0.1f;
                floor = "first";
            }
           // player.b2body.getPosition().set(player.b2body.getPosition().x, player.b2body.getPosition().y/2);
        }

        if(tag.equals("gun")){
         //   maingame.setScreen(GunScreen);
        }

    }



    @Override
    public void dispose() {
        stage.dispose();

    }
}

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

import java.util.logging.Logger;

public class WindowManager implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Label messageLabel;
    private Table table, levelTable;
    public String waitingForAnwser = "none";
    private ImageButton firstW, secondW, thirdW, backW;
    float bsize = 50;

    private PractGame maingame;

    private final static Logger LOGGER = Logger.getLogger(WindowManager.class.getName());

    public WindowManager(SpriteBatch sb, PractGame maingame){
        this.maingame = maingame;

        viewport = new FitViewport(320, 180, new OrthographicCamera()); // remember 16/9! 02/11
        stage = new Stage(viewport, sb);


        /*
        table = new Table();
        table.top();
        table.setFillParent(true);


        messageLabel = new Label( "", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add().expandX().padTop(10);
        table.add(messageLabel).expandX().padTop(10);
        table.add().expandX().padTop(10);
        stage.addActor(table);
        */
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

        if(tag.equals("lift")){
            LOGGER.info("lift message appears");
            messageLabel.setText("B -- go to lift");
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

    public void showWindow(String tag){
        if(tag.equals("lift")){
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
                        maingame.changeScreen(1);
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
    }



    @Override
    public void dispose() {
        stage.dispose();

    }
}

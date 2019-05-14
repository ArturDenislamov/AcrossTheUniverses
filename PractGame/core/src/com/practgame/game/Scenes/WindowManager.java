package com.practgame.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.PropertiesUtils;
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

    public boolean onGround;

    private PractGame maingame;

    public WindowManager(PractGame maingame){
        this.maingame = maingame;
        viewport = new FitViewport(320, 180, new OrthographicCamera());
        stage = new Stage(viewport, maingame.batch);
        onGround = false;
        levelTable = new Table();

        table = new Table();
        table.top();
        table.setFillParent(true);

        messageLabel = new Label( "", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add().expandX().padTop(10);
        table.add(messageLabel).expandX().padTop(10);
        table.add().expandX().padTop(10);
        stage.addActor(table);
    }

    public void showMessage(String tag){
        if(tag.equals("lobby")){
            String str1 = "lobby" + (int)(Math.random()* 11);
            String str2 = maingame.getBundle().get(str1);
            messageLabel.setText(str2);
        }else if(tag.equals("lift")){
            messageLabel.setText("B - go to lift");
        }else if(tag.equals("next_level")){
            messageLabel.setText("B - next level");
        }else if(tag.equals("reload")){
            messageLabel.setText("Reload");
        }else if(tag.equals("gun")){
            messageLabel.setColor(Color.BLUE);
            messageLabel.setText("Gun Unlocked");
        }

    }

    public void hideMessage(){
        messageLabel.setText("");
        messageLabel.setColor(Color.WHITE);
    }

    public void hideWindow(){
       // stage.clear();
        levelTable.clearChildren();
        waitingForAnwser = "none";
        maingame.menuLevel.show();
    }

    // this method also does actions, not only shows windows
    public void showWindow(String tag){
        if(tag.equals("lift")){
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
                    hideWindow(); // if it is located after line 101, controller doesn't work, it's understandable
                    maingame.changeScreen(1);
                    maingame.musicManager.setSound("world1.ogg");
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            });

            secondW = new ImageButton(new TextureRegionDrawable(new TextureRegion(second)));


            secondW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    hideWindow(); // if it is located after line 101, controller doesn't work
                    maingame.changeScreen(2);
                    maingame.musicManager.setSound("world2.ogg");
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            });



            thirdW = new ImageButton(new TextureRegionDrawable(new TextureRegion(third)));


            thirdW.addListener(new InputListener(){

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    hideWindow();
                    maingame.setScreen(maingame.gunScreen);
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                }
            });


            backW = new ImageButton(new TextureRegionDrawable(new TextureRegion(back)));


            backW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    hideWindow();
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            });


            levelTable.row().padLeft(5).padRight(5);
            levelTable.add(firstW).size(bsize, bsize).padTop(10);
            levelTable.add(secondW).size(bsize, bsize).padTop(10);
            levelTable.add(thirdW).size(bsize, bsize).padTop(10);
            levelTable.add(backW).size(bsize, bsize).padTop(10);
            levelTable.pack();
            stage.addActor(levelTable);
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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

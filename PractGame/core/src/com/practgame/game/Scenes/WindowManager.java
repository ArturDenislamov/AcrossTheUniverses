package com.practgame.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;
import com.practgame.game.Utils.AppPreferences;


public class WindowManager implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Label messageLabel;
    private Table table, levelTable;
    public String waitingForAnwser = "none";  // safes a name of the window, which is displayed and can be accepted by pressing B button
    private ImageButton firstW, secondW, thirdW, backW, gunW;
    float bsize = 50;
    Drawable backgroundLift;

    public boolean onGround; // checks, if player is on ground (for jumping)

    private PractGame maingame;

    public boolean liftShown;

    private Preferences prefs = Gdx.app.getPreferences(AppPreferences.PREFS_NAME);

    public WindowManager(PractGame maingame){
        this.maingame = maingame;
        viewport = new FitViewport(320, 180, new OrthographicCamera());
        stage = new Stage(viewport, maingame.batch);
        onGround = false;
        levelTable = new Table();
        backgroundLift =  new TextureRegionDrawable(new Texture("ui/backgroundLift.png"));
        liftShown = false;

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
        }else if(tag.equals("level_select")){
            messageLabel.setText("B - select mission");
        }
    }

    public void hideMessage(){
        messageLabel.setText("");
        messageLabel.setColor(Color.WHITE);
    }

    public void hideWindow(){
        levelTable.clearChildren();
        levelTable.setBackground((Drawable) null); // setting background to null to clear it
        waitingForAnwser = "none";
        maingame.menuLevel.show();
    }

    // this method also does actions, not only shows windows
    public void showWindow(String tag){
        if(tag.equals("level_select")){
            messageLabel.setText("");
            levelTable.setFillParent(true);
            levelTable.center();

            Gdx.input.setInputProcessor(stage);
            liftShown = true;

            Texture first = new Texture("ui/first.png");
            Texture second = new Texture("ui/second.png");
            Texture third = new Texture("ui/third.png");
            Texture back = new Texture("ui/back.png");
            Texture gun = new Texture("ui/gun.png");

            firstW = new ImageButton(new TextureRegionDrawable(new TextureRegion(first)));
            firstW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    liftShown = false;
                    hideWindow(); // if it is located after line "change screen", controller doesn't work, it's understandable
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
                    liftShown = false;
                    hideWindow();
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
                    liftShown = false;
                    hideWindow();
                    maingame.changeScreen(3);
                    maingame.musicManager.setSound("world3.ogg");
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            });

            backW = new ImageButton(new TextureRegionDrawable(new TextureRegion(back)));

            backW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    liftShown = false;
                    hideWindow();
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            });

            gunW = new ImageButton(new TextureRegionDrawable(new TextureRegion(gun)));

            gunW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    liftShown = false;
                    hideWindow();
                    maingame.setScreen(maingame.gunScreen);
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            });

            levelTable.setBackground(backgroundLift);
            levelTable.row().padLeft(5).padRight(5);
            levelTable.add(firstW).size(bsize, bsize).padTop(10);
            levelTable.add(secondW).size(bsize, bsize).padTop(10);
            levelTable.add(thirdW).size(bsize, bsize).padTop(10);
            levelTable.add(gunW).size(bsize, bsize).padTop(10);
            levelTable.add(backW).size(bsize).padTop(10);
            levelTable.pack();
            stage.addActor(levelTable);

            liftShown = true;
        }

        if(tag.equals("lift")){ // lift, moving in menu
            messageLabel.setText("");
            levelTable.setFillParent(true);
            levelTable.center();

            Gdx.input.setInputProcessor(stage);
            liftShown = true;

            Texture first = new Texture("ui/first.png");
            Texture second = new Texture("ui/second.png");
            Texture third = new Texture("ui/third.png");
            Texture back = new Texture("ui/back.png");

            firstW = new ImageButton(new TextureRegionDrawable(new TextureRegion(first)));
            firstW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    liftShown = false;
                    hideWindow(); // if it is located after line "change screen", controller doesn't work, it's understandable
                   maingame.menuLevel.changeMap(1);
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
                    liftShown = false;
                    hideWindow();
                    maingame.menuLevel.changeMap(2);
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
                    liftShown = false;
                    hideWindow();
                    maingame.menuLevel.changeMap(3);
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            });

            backW = new ImageButton(new TextureRegionDrawable(new TextureRegion(back)));

            backW.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    liftShown = false;
                    hideWindow();
                    waitingForAnwser = "none";
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            });

            levelTable.setBackground(backgroundLift);
            levelTable.row().padLeft(5).padRight(5);

            if(maingame.menuLevel.currentFloor != 1)
                levelTable.add(firstW).size(bsize, bsize).padTop(10);

            if(maingame.menuLevel.currentFloor != 2)
                levelTable.add(secondW).size(bsize, bsize).padTop(10);

            if(maingame.menuLevel.currentFloor != 3)
                levelTable.add(thirdW).size(bsize, bsize).padTop(10);

            levelTable.add(backW).size(bsize).padTop(10);
            levelTable.pack();
            stage.addActor(levelTable);

            liftShown = true;
        }

        if(tag.equals("next_level")){
            if(maingame.worldType == 1) {
                maingame.levelLine1++;
                prefs.putInteger(AppPreferences.PREF_SHOTS_1, maingame.playScreen.shotsMade);
                prefs.flush();
            } else if(maingame.worldType == 2) {
                maingame.levelLine2++;
                prefs.putInteger(AppPreferences.PREF_SHOTS_2, maingame.playScreen.shotsMade);
                prefs.flush();
            } else {
                maingame.levelLine3++;
                prefs.putInteger(AppPreferences.PREF_SHOTS_3, maingame.playScreen.shotsMade);
                prefs.flush();
            }
            maingame.changeScreen(maingame.worldType);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
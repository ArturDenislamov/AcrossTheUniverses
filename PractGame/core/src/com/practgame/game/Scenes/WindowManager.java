package com.practgame.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.logging.Logger;

public class WindowManager implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Label messageLabel;
    private Table table;

    private final static Logger LOGGER = Logger.getLogger(WindowManager.class.getName());

    public WindowManager(SpriteBatch sb){

        viewport = new FitViewport(320, 180, new OrthographicCamera()); // remember 16/9! 02/11
        stage = new Stage(viewport, sb);

        Table table = new Table();
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
            LOGGER.info("in showMessage method -- Hello");
            messageLabel.setText("Hello, K04!");
        }
    }

    public void hideMessage(){
        messageLabel.setText("");
    }


    @Override
    public void dispose() {
        stage.dispose();

    }
}

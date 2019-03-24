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
import com.practgame.game.PractGame;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer score;


    Label scoreLabel;
    Label levelLabel;

    public Hud(SpriteBatch sb){
        score = 0;

        viewport = new FitViewport(320, 180, new OrthographicCamera()); // remember 16/9! 02/11
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%04d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
      //  levelLabel = new Label("planet D280", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(scoreLabel).expandX().padTop(10);
        table.add().expandX().padTop(10);
        table.add().expandX().padTop(10);


        stage.addActor(table);



    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}

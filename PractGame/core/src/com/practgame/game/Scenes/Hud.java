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


public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    Label bulletsLabel;

    public Hud(SpriteBatch sb){
        viewport = new FitViewport(320, 180, new OrthographicCamera()); //16/9
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top().right();
        table.setFillParent(true);

        bulletsLabel = new Label(String.format("%02d",0), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(bulletsLabel).padTop(10).padRight(15);

        stage.addActor(table);
    }

    public void updateBullets(int bulletsAmount){
        if(bulletsAmount == 0){
            bulletsLabel.setColor(Color.valueOf("#0F52BA"));
            bulletsLabel.setText(Integer.toString(bulletsAmount));
        }
        if(bulletsAmount > 0)
            bulletsLabel.setColor(Color.WHITE);
        bulletsLabel.setText(Integer.toString(bulletsAmount));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

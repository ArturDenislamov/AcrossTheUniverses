package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.practgame.game.Sprites.Gun;
import com.practgame.game.Utils.AppPreferences;

import java.util.HashMap;
import java.util.Map;


public class GunScreen implements Screen {
    Stage stage;
    Table table;
    Texture backgroundTexture;
    int bscale = 7;
    TextButton acr130Button;
    TextButton redLineButton;
    Skin skin;

    HashMap <String, Gun> guns;

    private final Preferences prefs = Gdx.app.getPreferences(AppPreferences.PREFS_NAME);

    public GunScreen(HashMap <String, Gun> gunMap){
        backgroundTexture = new Texture("gunScreen/background.png");
        Image background = new Image(backgroundTexture);
        guns = gunMap;
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        acr130Button = new TextButton("ACR130", skin, "default");
        redLineButton = new TextButton("RedLine", skin, "default");
        Label titleLabel = new Label("Guns", skin, "title");

        acr130Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!guns.get("acr130").isLocked()) {
                    Gdx.app.log("GunScreen", "ACR130 activated");
                    prefs.putString(AppPreferences.PREFS_GUN, "acr130");
                    prefs.flush();
                    hide();
                }
            }
        });

        redLineButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!guns.get("redLine").isLocked()) {
                    Gdx.app.log("GunScreen", "RedLine activated");
                    prefs.putString(AppPreferences.PREFS_GUN, "redLine");
                    prefs.flush();
                    hide();
                }
            }
        });

        background.setFillParent(true);
        stage.addActor(background);

        table.add(titleLabel).expandX();
        table.row();
        table.add(acr130Button).pad(10,5,10, 5);
        table.add(redLineButton).pad(10,5,10, 5);
        stage.addActor(table);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
       // stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {}
}

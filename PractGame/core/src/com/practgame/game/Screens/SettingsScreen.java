package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.practgame.game.PractGame;
import com.practgame.game.Utils.AppPreferences;

import java.util.logging.Logger;

public class SettingsScreen implements Screen {
    private Stage stage;
    Table table;
    PractGame maingame;
    Skin skin;

    private static final float WORLD_WIDTH = 640;
    private static final float WORLD_HEIGHT = 360;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    private final Preferences preferences = Gdx.app.getPreferences(AppPreferences.PREFS_NAME);

    private static final Logger LOGGER = Logger.getLogger(SettingsScreen.class.getName());

    public SettingsScreen(PractGame game){
        maingame = game;
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        table = new Table();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));

        //volume
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin);
        volumeMusicSlider.setValue( preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME) );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                preferences.putFloat(AppPreferences.PREF_MUSIC_VOLUME, volumeMusicSlider.getValue());
                preferences.flush();
                maingame.musicManager.setVolume(preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME));
                return false;
            }
        });


        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setTransform(true);
        musicCheckbox.scaleBy(2f);
        musicCheckbox.setChecked(preferences.getBoolean(AppPreferences.PREF_MUSIC_ENABLED) );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
               preferences.putBoolean(AppPreferences.PREF_MUSIC_ENABLED, enabled);
               preferences.flush();
                maingame.musicManager.setVolume(preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME));
                return false;
            }
        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin, "default");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                maingame.musicManager.setVolume(preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME));
                maingame.setScreen(maingame.startScreen);
            }
        });

        final CheckBox soundCheckbox = new CheckBox(null, skin);
        musicCheckbox.setTransform(true);
        musicCheckbox.scaleBy(2f);
        musicCheckbox.setChecked(preferences.getBoolean(AppPreferences.PREF_SOUND_ENABLED) );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                preferences.putBoolean(AppPreferences.PREF_SOUND_ENABLED, enabled);
                preferences.flush();
                return false;
            }
        });


        titleLabel = new Label( "Preferences", skin, "title");
        volumeMusicLabel = new Label( "Music", skin );
        volumeSoundLabel = new Label( "Sounds", skin );
        musicOnOffLabel = new Label( "Music on/off", skin );
        soundOnOffLabel = new Label( "Sounds on/off", skin );

        table.setFillParent(true);
        table.add(titleLabel);
        table.row().pad(15, 0, 0, 10);
        table.add(volumeMusicLabel);
        table.add(volumeMusicSlider);
        table.row().pad(30, 0, 0, 10);
        table.add(musicOnOffLabel);
        table.add(musicCheckbox);
        table.row().pad(15, 0, 0, 10);
        table.add(volumeSoundLabel);
        //  table.add(soundMusicSlider);
        table.row().pad(30, 0, 0, 10);
        table.add(soundOnOffLabel);
        // table.add(soundEffectsCheckbox);
        table.row().pad(10, 0, 0, 10);
        table.add(backButton);

        stage.addActor(table);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

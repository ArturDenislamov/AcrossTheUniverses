package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    public boolean fromPause;

    public SettingsScreen(PractGame game){
        maingame = game;
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        table = new Table();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        fromPause = false;

        //volume
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin);
        volumeMusicSlider.setValue( preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME, 0.8f) );
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
        musicCheckbox.setChecked(preferences.getBoolean(AppPreferences.PREF_MUSIC_ENABLED, true) );
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
                maingame.musicManager.setVolume(preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME, 0.8f));
                if(fromPause) {
                    fromPause = false;
                    maingame.setScreen(maingame.pauseScreen);
                }
                else
                maingame.setScreen(maingame.startScreen);
            }
        });

        //sound
        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setTransform(true);
        soundCheckbox.scaleBy(2f);
        soundCheckbox.setChecked(preferences.getBoolean(AppPreferences.PREF_SOUND_ENABLED, true) );
        soundCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                preferences.putBoolean(AppPreferences.PREF_SOUND_ENABLED, enabled);
                preferences.flush();
                maingame.playScreen.updateSoundVolume();
                return false;
            }
        });

        //sound slider
        final Slider soundSlider = new Slider( 0f, 1f, 0.1f,false, skin);
        soundSlider.setValue( preferences.getFloat(AppPreferences.PREF_SOUND_VOL, 0.7f) );
        soundSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                preferences.putFloat(AppPreferences.PREF_SOUND_VOL, soundSlider.getValue());
                preferences.flush();
                maingame.playScreen.updateSoundVolume();
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
        table.add(soundSlider);
        table.row().pad(30, 0, 0, 10);
        table.add(soundOnOffLabel);
        table.add(soundCheckbox);
        table.row().pad(10, 0, 0, 10);
        table.add(backButton);

        stage.addActor(table);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            maingame.musicManager.setVolume(preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME, 0.8f));
            if(fromPause) {
                fromPause = false;
                maingame.setScreen(maingame.pauseScreen);
            }
            else
                maingame.setScreen(maingame.startScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

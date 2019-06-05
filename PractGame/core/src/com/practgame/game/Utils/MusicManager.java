package com.practgame.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;


public class MusicManager {
// changes tracks, it doesn't load them
// tracks are loaded by assetManager

    AssetManager manager;
    final Preferences preferences = Gdx.app.getPreferences(AppPreferences.PREFS_NAME);
    Music music;

    public MusicManager(AssetManager manager){
        this.manager = manager;
    }

    public void setSound(String name){
        if(music != null)
        if(music.isPlaying())
            music.stop();

        String path = "music/" + name;
        music = manager.get(path);

        if(preferences.getBoolean(AppPreferences.PREF_MUSIC_ENABLED, true))
            music.setVolume(preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME, 0.8f));
        else
            music.setVolume(0);

        music.setLooping(true);
        music.play();
    }

    public void pause(){
            music.pause();
    }

    public void play(){
        music.play();
    }

    public void setVolume(float volume){
        if(preferences.getBoolean(AppPreferences.PREF_MUSIC_ENABLED, true))
            music.setVolume(preferences.getFloat(AppPreferences.PREF_MUSIC_VOLUME, 0.8f));
        else
            music.setVolume(0);
    }

    public void dispose(){
        music.dispose();
        manager.dispose();
    }
}

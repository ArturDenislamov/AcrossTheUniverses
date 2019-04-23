package com.practgame.game.Utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class MusicManager {
// changes tracks, it doesn't load them

    AssetManager manager;

    Music music;

    public MusicManager(AssetManager manager){
        this.manager = manager;
    }

    public void setSound(String name){
        if(music != null)
        if(music.isPlaying())
            music.stop();

        String path = "sound/" + name;
        music = manager.get(path);
     //   music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setVolume(0.85f);
        music.setLooping(true);
        music.play();
    }

    public void pause(){
        music.pause();
    }

    public void dispose(){
        music.dispose();

    }


}

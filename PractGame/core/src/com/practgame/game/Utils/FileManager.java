package com.practgame.game.Utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class FileManager {

    public AssetManager manager;

    public FileManager(){
        manager = new AssetManager();
        manager.load("mainMenuWall_hdpi_2.png", Texture.class); // one for test
    }

    public void isLoad(){
        if(manager.update()){
                    return;
        }
    }
}

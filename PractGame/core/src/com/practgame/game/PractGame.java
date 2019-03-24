package com.practgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Screens.StartScreen;
import com.practgame.game.Utils.Controller;
import com.practgame.game.Utils.LevelInfo;
import com.practgame.game.Utils.Multilanguage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PractGame extends Game {
	public static SpriteBatch batch;

	public PlayScreen playScreen;
	public StartScreen startScreen;
	public MenuLevel menuLevel;
	public static Skin skinM;
	private TextureAtlas atlas;
	public  int levelLine1;
	public int levelLine2;
	public int levelLine3;
	public static float PPM = 100;
	Controller controller;
	ArrayList <LevelInfo> levelList1 = new ArrayList<LevelInfo>();
   // ArrayList <LevelInfo> levelList2 = new ArrayList<LevelInfo>();
  //  ArrayList <LevelInfo> levelList3 = new ArrayList<LevelInfo>();
    //LevelInfo demoLevel; // TODO make this 03/23




    @Override
	public void create() {
		// TODO add if(save exists) 02/15
        levelLine1 = 0; // created for managing levels
        levelLine2 = 0;
        levelLine3 = 0;
		batch = new SpriteBatch();
		startScreen = new StartScreen(this);
		playScreen = new PlayScreen(this);
		menuLevel = new MenuLevel(this);
		Multilanguage.setLanguage("eng"); // TODO 02/04 it should be replaced
        // TODO Multilanguage error null object reference 02/08
        setScreen(startScreen);

        levelList1.add(new LevelInfo("maps/lv1_1.tmx"));
        // levelList1.add(new LevelInfo("lv1_2"));  // TODO you need map files (and tilesets) 03/23
        // levelList1.add(new LevelInfo("lv1_3.tmx"));
       // levelList2.add(new LevelInfo("lv2_1"));
        // levelList3.add(new LevelInfo("lv3_!"));
    }

    public void changeScreen(int worldType){
            switch (worldType){
                case 1:
                    playScreen.setLevel(levelList1.get(levelLine1).mapInfo);
                    break;

                case 2:
                    //playScreen.setLevel(levelLine2);
                    break;
                case 3:
                    //playScreen.setLevel(levelLine3);
                    break;
            }
            setScreen(playScreen);
    }


	@Override
	public void render() {
		super.render();
	}

}
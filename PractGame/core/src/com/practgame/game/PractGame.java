package com.practgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.practgame.game.Screens.LoadScreen;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Screens.PauseScreen;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Screens.StartScreen;
import com.practgame.game.Utils.Controller;
import com.practgame.game.Utils.LevelInfo;
import com.practgame.game.Utils.Multilanguage;
import com.practgame.game.Utils.MusicManager;

import java.util.ArrayList;

public class PractGame extends Game {
    public static final short DEFAULT_BIT = 1; // ground
    public static final short PLAYER_BIT = 2; // player
    public static final short RECHARGE_BIT = 4; // recharge - item
    public static final short GUN_BIT = 8; // gun - item
    public static final short DESTROYED_BIT = 16; // ?
    public static final short ENEMY_BIT = 32;
    public static final short BULLET_BIT = 64;
    // maybe you should add bullet bit

    public static SpriteBatch batch;

	public PlayScreen playScreen;
	public StartScreen startScreen;
	public MenuLevel menuLevel;
	private LoadScreen loadScreen;
	public PauseScreen pauseScreen;

	public  int levelLine1;
	public int levelLine2;
	public int levelLine3;
	public int worldType;

	public static float PPM = 100;
	public ArrayList <LevelInfo> levelList1;
    ArrayList <LevelInfo> levelList2;
    ArrayList <LevelInfo> levelList3;
    //LevelInfo demoLevel; // TODO make this 03/23 ; really ? 04/18, a week before preshow


	public AssetManager manager;
	public MusicManager musicManager;

    @Override
	public void create() {
		levelList1 = new ArrayList<LevelInfo>();
		levelList2 = new ArrayList<LevelInfo>();
		levelList3 = new ArrayList<LevelInfo>();


		// TODO add if(save exists) 02/15

		loadScreen = new LoadScreen(this);
		setScreen(loadScreen);

		manager = loadScreen.manager;
        musicManager = new MusicManager(manager);
        musicManager.setSound("title.ogg");

        levelLine1 = 0; // created for managing levels
        levelLine2 = 0; // 2 - for test
        levelLine3 = 0;
		batch = new SpriteBatch();
		startScreen = new StartScreen(this);
		playScreen = new PlayScreen(this);
		menuLevel = new MenuLevel(this);
		pauseScreen = new PauseScreen(this);

		//TODO under construction
		//Multilanguage.setLanguage("eng"); // TODO 02/04 it should be replaced
        // TODO Multilanguage error null object reference 02/08

		//adding maps in ArrayLists in LoadScreen class
    }

    public void changeScreen(int worldType){
            switch (worldType){
                case 1:
                    playScreen.setLevel(levelList1.get(levelLine1).mapInfo);
                    this.worldType = 1;
                    break;

                case 2:
                    playScreen.setLevel(levelList2.get(levelLine2).mapInfo);
					this.worldType = 2;
                    break;
                case 3:
                    playScreen.setLevel(levelList3.get(levelLine3).mapInfo);
					this.worldType = 3;
                    break;
            }
            setScreen(playScreen);
    }


	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
		musicManager.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

}
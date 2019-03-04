package com.practgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Screens.StartScreen;
import com.practgame.game.Utils.Controller;
import com.practgame.game.Utils.Multilanguage;

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


	@Override
	public void create() {
		// TODO add if(save exists) 02/15
        levelLine1 = 0;
        levelLine2 = 0;
        levelLine3 = 0;
		batch = new SpriteBatch();
		startScreen = new StartScreen(this);
		playScreen = new PlayScreen(this);
		menuLevel = new MenuLevel(this);
		Multilanguage.setLanguage("eng"); // TODO 02/04 it should be replaced
        // TODO Multilanguage error null object reference 02/08
        setScreen(startScreen);
	}

	@Override
	public void render() {
		super.render();
	}

}
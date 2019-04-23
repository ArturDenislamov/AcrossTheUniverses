package com.practgame.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.practgame.game.PractGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","Public");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PractGame(), config);
	}
}

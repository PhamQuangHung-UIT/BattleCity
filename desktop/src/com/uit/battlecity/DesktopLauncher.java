package com.uit.battlecity;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.uit.battlecity.utils.GameConstants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
		config.setTitle("Battle City");
		config.setWindowIcon(Files.FileType.Internal, "icon.png");
		config.setWindowedMode(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
		new Lwjgl3Application(new BattleCity(), config);
	}
}

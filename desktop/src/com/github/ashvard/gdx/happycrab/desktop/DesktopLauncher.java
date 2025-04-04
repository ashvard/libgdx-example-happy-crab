package com.github.ashvard.gdx.happycrab.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.github.ashvard.gdx.happycrab.HappyCrabSimpleGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("happy crab");

		config.setWindowedMode(1766, 1024);
		//config.setForegroundFPS(60);
		//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		new Lwjgl3Application(new HappyCrabSimpleGame(), config);
	}
}
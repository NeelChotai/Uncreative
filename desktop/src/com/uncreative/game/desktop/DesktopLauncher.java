package com.uncreative.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uncreative.game.main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = false;
		config.title = "Placeholder";
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new main(), config);
	}
}

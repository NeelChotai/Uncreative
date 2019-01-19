package com.uncreative.game.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uncreative.game.Pirates;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		DisplayMode mode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		config.setFromDisplayMode(mode);
		config.title = "York Pirates!";
		new LwjglApplication(new Pirates(), config);
	}
}

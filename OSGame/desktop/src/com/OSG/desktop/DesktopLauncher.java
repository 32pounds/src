package com.OSG.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.OSG.OSGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "32pounds is awesome";
        config.width = 960;
        config.height = 576;
        new LwjglApplication(new OSGame(), config);
	}
}

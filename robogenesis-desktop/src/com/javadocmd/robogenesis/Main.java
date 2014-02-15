package com.javadocmd.robogenesis;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "robogenesis";
		cfg.useGL20 = false;
		cfg.width = 1200;
		cfg.height = 800;
		cfg.resizable = false;
		cfg.vSyncEnabled = true;
		
		new LwjglApplication(new RgGame(), cfg);
	}
}

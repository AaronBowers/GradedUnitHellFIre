package com.Aaron.Hellfire;

import com.Aaron.HellFire.HellFire;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "HellFire " + HellFire.VERSION;
		cfg.useGL20 = true;
		cfg.width = 1080;
		cfg.height = 720;
		
		new LwjglApplication(new HellFire(), cfg);
	}
}

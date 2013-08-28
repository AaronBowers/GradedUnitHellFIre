package com.Aaron.HellFire;

import com.Aaron.HellFire.Screens.GameScreen;
import com.Aaron.HellFire.Screens.MainMenu;
import com.Aaron.HellFire.Screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;

public class HellFire extends Game
{

	public static final String VERSION = "0.0.1.0 Pre-Alpha";
	public static final String LOG = "HellFire";
	FPSLogger log;
	
	
	@Override
	public void create()
	{	
		log = new FPSLogger();
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void render()
	{		
		super.render();
		log.log();
	}
	
	public FPSLogger getFps()
	{
		return log;
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void resume()
	{
		super.resume();
	}
}

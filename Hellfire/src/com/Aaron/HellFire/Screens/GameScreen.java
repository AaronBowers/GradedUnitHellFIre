package com.Aaron.HellFire.Screens;

import com.Aaron.HellFire.HellFire;
import com.Aaron.HellFire.View.World;
import com.Aaron.HellFire.View.WorldRenderer;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen
{
	
	HellFire game;
	World world;//this world is import from our built game world class, not the box 2D Imp that comes with lib gdx.
	WorldRenderer render;
	
	public GameScreen(HellFire game)
	{
		this.game = game;
		world = new World(game);
		render = new WorldRenderer(world);
	}
	
	@Override
	public void render(float delta)
	{
		world.update();
		render.render();
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void show()
	{
		
	}

	@Override
	public void hide() 
	{
		dispose();
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void dispose()
	{
		world.dispose();
	}

}

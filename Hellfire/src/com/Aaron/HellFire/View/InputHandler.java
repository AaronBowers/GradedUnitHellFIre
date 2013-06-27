package com.Aaron.HellFire.View;

import com.Aaron.HellFire.Models.Bullet;
import com.Aaron.HellFire.Models.Ship;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements InputProcessor
{
	World world;
	Ship ship;
	Vector3 touch = new Vector3();
	Vector2 vec2Touch = new Vector2();
	
	public InputHandler(World world)
	{
		this.world = world;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		ship = world.getShip();
		switch(keycode)
		{
		case Keys.W:
			ship.getVelocity().y = 1;
			break;
		case Keys.S:
			ship.getVelocity().y = -1;
			break;
		case Keys.A:
			ship.getVelocity().x = -1;
			break;
		case Keys.D:
			ship.getVelocity().x = 1;
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		ship = world.getShip();
		switch(keycode)
		{
		case Keys.W:
			//if(ship.getVelocity().y == 1)
			ship.getVelocity().y = 0;
			break;
		case Keys.S:
			//if(ship.getVelocity().y == -1)
			ship.getVelocity().y = 0;
			break;
		case Keys.D:
			//if(ship.getVelocity().x == -1)
			ship.getVelocity().x = 0;
			break;
		case Keys.A:
			//if(ship.getVelocity().x == 1)
			ship.getVelocity().x = 0;
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		touch.set(screenX, screenY, 0);
		world.getRenderer().getCamera().unproject(touch);
		vec2Touch.set(touch.x, touch.y);
		world.addBullet(new Bullet(8, 1, 1,1, new Vector2(ship.getPosition().x, ship.getPosition().y), new Vector2(vec2Touch.sub(ship.getPosition()).nor())));
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{

		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{

		return false;
	}
	

}

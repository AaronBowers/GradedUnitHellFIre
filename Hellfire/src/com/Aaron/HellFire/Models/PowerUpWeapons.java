package com.Aaron.HellFire.Models;

import com.badlogic.gdx.math.Vector2;

public class PowerUpWeapons extends PowerUp
{
	

	public PowerUpWeapons(float SPEED, float rotation, float width, float height, Vector2 position, int type)
	{
		super(SPEED + 200, rotation, width/2, height/2, position, type);
		
	}

	@Override
	public void advance(float delta, Ship ship)
	{

		//move power up left across the screen at a constant speed
		position.x -= delta * SPEED;
		
		super.update(ship);
		
	}

}

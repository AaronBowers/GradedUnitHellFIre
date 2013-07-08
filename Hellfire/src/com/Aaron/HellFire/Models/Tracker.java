package com.Aaron.HellFire.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Tracker extends Enemy
{
	
	float ROTATION_SPEED = 300;
	
	public Tracker(float SPEED, float rotation, float width, float height,
			Vector2 position)
	{
		super(SPEED, rotation, width, height, position);
		// TODO Auto-generated constructor stub
	}





	@Override
	public void advance(float delta, Ship ship)
	{
		position.lerp(ship.getPosition(), delta);
		
		rotation += delta * ROTATION_SPEED;
		
		if (rotation > 360)
			rotation -= 360;
		
		
		super.update(ship);

		
	}

}

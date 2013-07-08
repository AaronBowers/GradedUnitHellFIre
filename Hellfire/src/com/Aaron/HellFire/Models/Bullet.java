package com.Aaron.HellFire.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends MoveableEntity
{
	public static float SPEED = 15;

	public Bullet(float SPEED, float rotation, float width, float height,Vector2 position, Vector2 velocity)
	{
		super(SPEED, rotation, width, height, position);
		this.velocity = velocity;
		
	}
	
	@Override
	public void update(Ship ship)
	{
		position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime() * SPEED));
		//rotation = velocity.angle();//direction for bullet
		super.update(ship);
	}

}

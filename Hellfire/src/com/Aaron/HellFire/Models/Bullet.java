package com.Aaron.HellFire.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;


//bullets that move forward
public class Bullet extends MoveableEntity
{
	public static float SPEED = 500;

	public Bullet(float SPEED, float BulletRotation, float width, float height,Vector2 position, Vector2 velocity)
	{
		super(SPEED, BulletRotation, width*100, height*100, position);
		this.velocity = velocity;
		
	}
	
	@Override
	public void update(Ship ship)
	{
		position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime() * SPEED));

		super.update(ship);
	}
	
	//set rotation
	
	

}

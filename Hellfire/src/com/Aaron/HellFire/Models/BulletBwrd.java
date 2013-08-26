package com.Aaron.HellFire.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

//bullets that mover backwards 
public class BulletBwrd extends MoveableEntity
{
	public static float SPEED = 500;

	public BulletBwrd(float SPEED, float rotation, float width, float height,Vector2 position, Vector2 velocity)
	{
		super(SPEED, rotation, width*100, height*100, position);
		this.velocity = velocity;
		
	}
	
	@Override
	public void update(Ship ship)
	{
		position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime() * -SPEED));
		//rotation =  90;//velocity.angle();//direction for bullet
		super.update(ship);
	}

}

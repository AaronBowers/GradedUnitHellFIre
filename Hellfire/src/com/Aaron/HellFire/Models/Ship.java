package com.Aaron.HellFire.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Ship extends MoveableEntity
{

	public Ship(Vector2 position, float width, float height, float rotation, float SPEED)
	{
		super(SPEED, rotation, width, height, position);
	}

	//@Override
	public void update()
	{
		position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime() * SPEED));
		
		//if (velocity.x != 0 || velocity.y !=0)// rotates the ship... might be added to do flips I dont know yet....
		//rotation = velocity.angle() - 90;
		
		bounds.x = position.x;
		bounds.y = position.y;
	}
	
	//name of ship
	//texture
	//size
	//

}

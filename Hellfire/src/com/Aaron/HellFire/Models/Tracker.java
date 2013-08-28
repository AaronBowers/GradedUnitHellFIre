package com.Aaron.HellFire.Models;

import com.badlogic.gdx.math.Vector2;

public class Tracker extends Enemy
{
	//This enemy type is a seeker
	//it attempts to smash into the players ship
	//traits: moves fast, low health, no weapons.

	float ROTATION_SPEED = 300;
	private int rank;
	private int type;
	
	public Tracker(float SPEED, float rotation, float width, float height, Vector2 position, int type, int rank)
	{
		super(SPEED, rotation, width/2, height/2, position, rank, type);
		
		this.rank = rank;
		this.type = type;
	}


	@Override
	public void advance(float delta, Ship ship)
	{
		//position.lerp(ship.getPosition(), delta);//follows the player ship if on
		//places enemy on static position
		
		
		
		//position.y  = 720 / 2;
		//position.x -r;
		
		rotation += delta * ROTATION_SPEED;
		
		if (rotation > 360)
			rotation -= 360;
		
		
		super.update(ship);
		
	}
	
	

}

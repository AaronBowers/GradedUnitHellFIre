package com.Aaron.HellFire.Models;

import com.badlogic.gdx.math.Vector2;

public abstract class PowerUp extends MoveableEntity
{

	//FIELDS
	private int type;
	
	//1 -- increase  weapon level
	//2 -- increase  weapon Energy 
	//3 -- increase ship speed 
	//4 -- increase ship lives
	//5 -- increase ship special weapons power
	//6 -- bonus points
	
	
	public PowerUp(float SPEED, float rotation, float width, float height, Vector2 position)
	{
		super(SPEED, rotation, width*100, height*100, position);
		
	}

	
	//Functions
	
	public int getType() { return this.type; }
	public void setType(int type) {this.type = type;}
	

	//rotation not set, left as 0
	public float getRotation() {return 0;}


	
	public abstract void advance(float delta, Ship ship);

}

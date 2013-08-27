package com.Aaron.HellFire.Models;

import com.badlogic.gdx.math.Vector2;
import java.awt.*;

public abstract class Enemy extends MoveableEntity
{

	private int health;
	private int type;
	private int rank;
	
	//private Color color1;
	
	private boolean ready;
	private boolean dead;

	
	//constructors
	public Enemy(float SPEED, float rotation, float width, float height,Vector2 position, int type, int rank)
	{
		super(SPEED, rotation, width*100, height*100, position);
		
		this.type = type;
		this.rank = rank;
		
		//default enemy
		if (type == 1)
		{
			//color1 = Color.BLUE;
			if (rank == 1)
			{
				health = 4;
			}
		}
		
		ready = false;
		dead = false;
		
	}
	
	public int getType() { return type;}
	public int getRank() { return rank;}
	
	public boolean isDead() { return dead; }
	
	public void hit()
	{
		health--;
		if(health <= 0)
		{
			dead = true;
		}
	}
	
	public abstract void advance(float delta, Ship ship);


}

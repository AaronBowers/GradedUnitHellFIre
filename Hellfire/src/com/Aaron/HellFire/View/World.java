package com.Aaron.HellFire.View;

import java.util.Iterator;

import com.Aaron.HellFire.HellFire;
import com.Aaron.HellFire.Models.Bullet;
import com.Aaron.HellFire.Models.Ship;
import com.Aaron.HellFire.Models.Tracker;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World
{
	
	HellFire game;
	Ship ship;// takes our built ship models
	Tracker tracker;
	Array<Bullet> bullets = new Array<Bullet>();
	WorldRenderer wr;
	Iterator<Bullet> bIter;
	Bullet b;
	
	public World (HellFire game)
	{
		this.game = game;
		ship = new Ship(new Vector2(5, 5),1 ,1, 0, 5f);
		tracker = new Tracker(5f, 0, 1, 1, new Vector2(10, 10));
		Gdx.input.setInputProcessor(new InputHandler(this));
	}
	
	public Ship getShip()
	{
		return ship;
		
	}
	
	public Tracker getTracker()
	{
		return tracker;
	}

	public void update()
	{
		ship.update();
		tracker.update(ship);
		
		if(ship.getBounds().overlaps(tracker.getBounds()))
			Gdx.app.log(HellFire.LOG, "Hull Collision");
		
		bIter = bullets.iterator();
		while(bIter.hasNext())
		{
			b= bIter.next();
			b.update(ship);
			
		}
	
		
	}	
		
	
	public void addBullet(Bullet b)
	{
		bullets.add(b);
	}
	
	public Array<Bullet> getBullets()
	{
		return bullets;
	}
	public void setRenderer(WorldRenderer wr)
	{
		this.wr = wr;
	}
	
	public WorldRenderer getRenderer()
	{
		return wr;
	}
	
	public void dispose()
	{
		
		
	}
	

	
}

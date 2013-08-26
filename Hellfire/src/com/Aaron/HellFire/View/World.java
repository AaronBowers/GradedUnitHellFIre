package com.Aaron.HellFire.View;

import java.util.ArrayList;
import java.util.Iterator;

import com.Aaron.HellFire.HellFire;
import com.Aaron.HellFire.Models.BulletFwrd;
import com.Aaron.HellFire.Models.BulletBwrd;
import com.Aaron.HellFire.Models.Enemy;
import com.Aaron.HellFire.Models.Ship;
import com.Aaron.HellFire.Models.Tracker;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World
{
	
	HellFire game;
	
	Ship ship;// takes our built ship models
	
	public ArrayList<BulletFwrd> Fbullets = new ArrayList<BulletFwrd>();
	public ArrayList<BulletBwrd> Bbullets = new ArrayList<BulletBwrd>();
	//public ArrayList<BulletFwrd> Fbullets = new ArrayList<BulletFwrd>();
	//public ArrayList<BulletFwrd> Fbullets = new ArrayList<BulletFwrd>();
	
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	WorldRenderer wr;
	
	//4 iterators for four bullet types
	Iterator<BulletFwrd> FbIter;
	Iterator<BulletBwrd> BbIter;
	//Iterator<BulletFwrd> FbIter;
	//Iterator<BulletFwrd> FbIter;
	
	BulletFwrd Fb;
	BulletBwrd Bb;
	
	
	Iterator<Enemy> eIter;
	Enemy e;
	
	private long waveStartTimer;
	private long waveStartTimerDiff;
	private int waveNumber;
	private boolean waveStart;
	private int waveDelay = 2000;
	
	public World (HellFire game)
	{
		this.game = game;
		
		//player is created here
		ship = new Ship(new Vector2(50, 720/2),2 ,1, 0, 10f);
		
		waveStartTimer = 0;
		waveStartTimerDiff = 0;
		waveNumber = 0;

		createNewEnemies();
		
		Gdx.input.setInputProcessor(new InputHandler(this));
	}
	
	//generates new enemies,
	//this is currently a test bed, the idea is to generate enemies based on level timers and dectection queues 
	//e.g. when the player passes a certain part of the map a set of enemies appear.
	
	// This current section of code produces an enemy on the y axis on the far right of the screen
	// the enemy must be destroyed and the timer then progresses to spawn another enemy, I did not use the delta time 
	// to mesure this, instead i used the sysyems nano timer and dived that by 1million to get the time in seconds, please refer to the update method for timer details 
	
	private void createNewEnemies()
	{
		enemies.clear();
		
		//the grade of enemy as well as type of enemy is determined by numbers
		
		if (waveNumber == 1)
		{
			for(int i = 0; i < 1; i++)
			{
				float x; 
				int y;
				
				x = (float) (Math.random() * 720 / 2);// a ranadom;y generated vertical position is generated. then enemy spawns
				
				enemies.add(new Tracker(1f, 0, 1, 1, new Vector2(i+1200,  x), 1, 1));
			}
		}
		if (waveNumber == 2)
		{
			for(int i = 0; i < 1; i++)
			{
				float x; 
				int y;
				
				x = (float) (Math.random() * 720 / 2);
				
				enemies.add(new Tracker(1f, 0, 1, 1, new Vector2(i+1200,  x), 1, 1));
			}
		}
		if (waveNumber == 3)
		{
			for(int i = 0; i < 1; i++)
			{
				float x; 
				int y;
				
				x = (float) (Math.random() * 720 / 2);
				
				enemies.add(new Tracker(1f, 0, 1, 1, new Vector2(i+1200,  x), 1, 1));
			}
		}
		if (waveNumber == 4)
		{
			for(int i = 0; i < 1; i++)
			{
				float x; 
				int y;
				
				x = (float) (Math.random() * 720 / 2);
				
				enemies.add(new Tracker(1f, 0, 1, 1, new Vector2(i+1200,  x), 1, 1));
			}
		}
		if (waveNumber == 5)
		{
			for(int i = 0; i < 1; i++)
			{
				float x; 
				int y;
				
				x = (float) (Math.random() * 720 / 2 + 1280 / 4);
				
				enemies.add(new Tracker(1f, 0, 1, 1, new Vector2(i+1000,  x), 1, 1));
			}
		}
		if (waveNumber == 6)
		{
			for(int i = 0; i < 1; i++)
			{
				float x; 
				int y;
				
				x = (float) (Math.random() * 720 / 2);
				
				enemies.add(new Tracker(1f, 0, 1, 1, new Vector2(i+1000,  x), 1, 1));
			}
		}		
	}
	
	public Ship getShip()
	{
		return ship;
		
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}

	public void update()
	{
		ship.update();
		
		//
		if(waveStartTimer == 0 && enemies.size() == 0)
		{
			waveNumber ++;
			waveStart = false;
			waveStartTimer = System.nanoTime();
		}
		else
		{
			waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
			if(waveStartTimerDiff > waveDelay)
			{
				
				waveStart = true;
				waveStartTimer = 0;
				waveStartTimerDiff = 0;
			}
		}
		
		//create Enemies
		if(waveStart && enemies.isEmpty())
		{
			createNewEnemies();
		}

		//updates bullets
		
		//forward bullet update
		FbIter = Fbullets.iterator();
		while(FbIter.hasNext())
		{
			Fb = FbIter.next();
			Fb.update(ship);
		}
		
		//Backward bullet update
		BbIter = Bbullets.iterator();
		while(BbIter.hasNext())
		{
			Bb = BbIter.next();
			Bb.update(ship);
		}
		
		
		
		//player-cameraBoundry collision
		
		

		//player-enemy collision
		eIter = enemies.iterator();
		while(eIter.hasNext())
		{
			e = eIter.next();
			e.advance(Gdx.graphics.getDeltaTime(), ship);
			
			if(ship.getBounds().overlaps(e.getBounds()))
			{
				if (ship.isRecovering())
				{
					Gdx.app.log(HellFire.LOG, "Ship Recovering!");
				}
				else
				{
					Gdx.app.log(HellFire.LOG, "Hull Collision!");
					//update player lives
					ship.loseLife();
				}
			}
		}
		
		
		// ======= Bullets Collisions =======================================================================================//
		//enemy-bullet collision for forward bullets
		FbIter = Fbullets.iterator();
		while(FbIter.hasNext())
		{
			Fb = FbIter.next();
			eIter = enemies.iterator();
			while(eIter.hasNext())
			{
				e = eIter.next();
				
				if(e.getBounds().overlaps(Fb.getBounds()))
				{
					Gdx.app.log(HellFire.LOG, "Enemy Disintegrated with Fb!");
					//bIter.remove();//revomes item from array
					//update player score
					ship.addScore(e.getType() + e.getRank());
					eIter.remove();
					FbIter.remove();
					
					
					/*if(e.isDead())
					{
						ship.addScore(e.getType() + e.getRank());
						eIter.remove();
					}*/
					//HellfireAudio.explode();//sound clip for enemy death
				}
			}
		}
		
		//enemy-bullet collision for backward bullets
		BbIter = Bbullets.iterator();
		while(BbIter.hasNext())
		{
			Bb = BbIter.next();
			eIter = enemies.iterator();
			while(eIter.hasNext())
			{
				e = eIter.next();
				
				if(e.getBounds().overlaps(Bb.getBounds()))
				{
					Gdx.app.log(HellFire.LOG, "Enemy Disintegrated with Bb!");
					//bIter.remove();//revomes item from array
					//update player score
					ship.addScore(e.getType() + e.getRank());
					eIter.remove();
					BbIter.remove();
					
					
					/*if(e.isDead())
					{
						ship.addScore(e.getType() + e.getRank());
						eIter.remove();
					}*/
					//HellfireAudio.explode();//sound clip for enemy death
				}
			}
		}
		
		
		
		
		
		//check dead enemies
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).isDead())
			{
				Enemy e = enemies.get(i);
				ship.addScore(e.getType() + e.getRank());
				enemies.remove(i);
				i--;
			}
		}
			
	}	
		

	//forward bullets 
	public void addFwrdBullet(BulletFwrd Fb)
	{
		Fbullets.add(Fb);
	}
	
	public ArrayList<BulletFwrd> getFBullets()
	{
		return Fbullets;
	}
	
	//Backward bullets
	public void addBwrdBullet(BulletBwrd Bb)
	{
		Bbullets.add(Bb);
	}
	
	public ArrayList<BulletBwrd> getBBullets()
	{
		return Bbullets;
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

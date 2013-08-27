package com.Aaron.HellFire.View;

import java.util.ArrayList;
import java.util.Iterator;

import com.Aaron.HellFire.HellFire;
import com.Aaron.HellFire.Models.Bullet;
import com.Aaron.HellFire.Models.Enemy;
import com.Aaron.HellFire.Models.PowerUp;
import com.Aaron.HellFire.Models.PowerUpWeapons;
import com.Aaron.HellFire.Models.Ship;
import com.Aaron.HellFire.Models.Tracker;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World
{
	
	HellFire game;
	
	Ship ship;
	
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
	
	WorldRenderer wr;
	
	Iterator<Bullet> bIter;
	Bullet b;
	
	Iterator<Enemy> eIter;
	Enemy e;
	
	Iterator<PowerUp> PIter;
	PowerUp p;
	
	private long waveStartTimer;
	private long waveStartTimerDiff;
	private int waveNumber;
	private boolean waveStart;
	private int waveDelay = 2000;
	
	public World (HellFire game)
	{
		this.game = game;
		
		//player is created here
		ship = new Ship(new Vector2(50, 720/2),2 ,1, 0, 500f);
		
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
	
	//builds a single power up item.
	
	//constructs new test enemies on level
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
		
		//CREATES NEW POWER UP ONSCREEN
		//createNewPowerUp();

		//updates bullets
		bIter = bullets.iterator();
		while(bIter.hasNext())
		{
			b = bIter.next();
			b.update(ship);
		}
		
		//update posttion of powerups
		PIter = powerups.iterator();
		while(PIter.hasNext())
		{
			p = PIter.next();
			p.update(ship);
		}
		
		//ship-level container Collision
		if(ship.getPosition().x < 0){ship.getPosition().x = 0;} //left boundry
		
		if(ship.getPosition().y < 0){ship.getPosition().y = 0;} //bottom boundry
		
		if(ship.getPosition().x > 1280 - ship.getWidth() ){ship.getPosition().x = 1280 - ship.getWidth();} //left boundry
		
		if(ship.getPosition().y > 650 - ship.getHeight()){ship.getPosition().y = 650 - ship.getHeight();} //bottom boundry
		
		//player static object collision
		//what to expect- level objects
		
		//enemy player collision checks
		eIter = enemies.iterator();
		while(eIter.hasNext())
		{
			e = eIter.next();
			e.advance(Gdx.graphics.getDeltaTime(), ship);
			//if(ship.getPosition().x > ship.getWidth()){ship.getPosition().x = ship.getWidth();} //left boundry

			
			if(ship.getBounds().overlaps(e.getBounds()))
			{
				if (ship.isRecovering())
				{
					//Gdx.app.log(HellFire.LOG, "Ship Recovering!");
				}
				else
				{
					//Gdx.app.log(HellFire.LOG, "Hull Collision!");
					//update player lives
					ship.loseLife();
				}
			}
		}
		
		
		
		
		// ======= Bullets Collisions =======================================================================================//
		//enemy-bullet collision
		bIter = bullets.iterator();
		while(bIter.hasNext())
		{
			b = bIter.next();
			
			if(b.getPosition().x > 1280 || b.getPosition().y > 650 || b.getPosition().x < 0 || b.getPosition().y < 0)
			{
				bIter.remove();
			}
	
			eIter = enemies.iterator();
			while(eIter.hasNext())
			{
				e = eIter.next();
				
				if(e.getBounds().overlaps(b.getBounds()))
				{
					ship.addScore(e.getType() + e.getRank());
					
					//makes new power up item on enemy death
					powerups.add(new PowerUpWeapons(1f, 0, 1, 1, new Vector2(e.getPosition().x,e.getPosition().y)));
					
					eIter.remove();
					bIter.remove();
					
					
					/*if(e.isDead())
					{
						ship.addScore(e.getType() + e.getRank());
						eIter.remove();
					}*/
					//HellfireAudio.explode();//sound clip for enemy death
				}
				
				//checks if bullets pass level boundary, bullets must be made first to ensure safe boundary check
				//for this reason it has been placed within the bullet iterator. 
				// so it will only check bullet level collision if there is a bullet available to check
			}
		}
		
		//enemy-enemy collision
		//make this work so an enemy can occupy the same space as another enemy
		
		//this is set the reverse way from how it should be
		
		//change to if ship collides into enemy
		
		
		
		
		// ==== disabled atm =========================
		//Player power-up collision
		PIter = powerups.iterator();
		while(PIter.hasNext())
		{
			p = PIter.next();
			p.advance(Gdx.graphics.getDeltaTime(), ship);
			//this power up changes the state of the ships weapons
			//it duplicates the amount of bullets that will be 
			//fired from the ship
			if(ship.getBounds().overlaps(p.getBounds()))
			{
				//add detection for power up type
				//increase power level
				ship.setWeaponLv(+1);
				PIter.remove();
			}
		}
		//===============================================
		
		
		
		//bullet level collision

		
		
		/*
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
		}*/
			
	}	
		

	//forward bullets 
	public void addBullet(Bullet Fb)
	{
		bullets.add(Fb);
	}
	
	public ArrayList<Bullet> getBullets()
	{
		return bullets;
	}

	
	public void addPowerUp(PowerUp Pu)
	{
		powerups.add(Pu);
	}
	
	public ArrayList<PowerUp> getPowerUps()
	{
		return powerups;
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

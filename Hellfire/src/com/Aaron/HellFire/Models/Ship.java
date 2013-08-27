package com.Aaron.HellFire.Models;

import com.Aaron.HellFire.HellFire;
import com.Aaron.HellFire.View.InputHandler;
import com.Aaron.HellFire.View.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class Ship extends MoveableEntity
{
	
	World world;
	Ship ship;
	Vector3 touch = new Vector3();
	Vector2 vec2Touch = new Vector2();
	InputHandler inputhandler;
	
	private int lives;
	private int health;
	private int energy;//used to power weapons
	private int weaponLv;//power up counter for ship weapons
	private int weaponMode;//indicator for weapon mode
	private int speedLv;//power up counter for ship speed
	
	//private int bonus;
	
	private int score;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;

	
	private boolean recovering; //player recovery state
	private long recoveryTimer; //cooldown after hit, timer counts down until player can be hit again.
	
	
	//FUCNTIONS
	public int getLives() {return lives;}
	public int getScore() {return score;}
	
	public int getEnergyLevel() {return energy;}
	public void setEndergyLevel(int b) {energy -= b;}
	
	public int getHealthLevel() {return health;}
	
	//set and get weapon level
	public int getWeaponLv() {return weaponLv;}
	public void setWeaponLv(int Wlv) {weaponLv ++;}
	
	public int getWeaponMode() {return weaponMode;}
	public void setWeaponMode() {changeMode();}//increase the count on weapon state
	
	public int getSpeedState() {return speedLv;}
	
	//CONTROLS ====================================================
	public boolean setFiring(boolean b) {return firing = b;}
	
	//COOLDOWNS ===================================================
	public boolean isRecovering() {return recovering;}
	//END OF COOLDOWNS ============================================
	public void addScore (int i) {score += i;}
	
	public void loseLife()
	{
		lives--;
		recovering = true;
		recoveryTimer = System.nanoTime();
	}
	
	public void changeMode()
	{
		if (weaponMode >= 3)
		{
			weaponMode = 0;//reverts weapon state to mode1
		}
		else
		{
			weaponMode ++;//cycles to next weaponstate
		}
	}
	
	//END OF FUNCTIONS
	
	//CONSTRUCTOR
	public Ship(Vector2 position, float width, float height, float rotation, float SPEED)
	{
		super(SPEED, rotation, width*100, height*100, position);//the multiples is percent, it refers to a scale 

		recovering = false;
		recoveryTimer = 0;
		lives = 3;
		health = 1;
		energy = 100;//used to power weapons
		weaponMode = 0;
		weaponLv = 0;//power up counter for ship weapons
		speedLv = 0;//power up counter for ship speed
		score = 0;
	}
	
	//@Override
	public void update()
	{
		position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime() * SPEED));
		
		bounds.x = position.x;
		bounds.y = position.y;
		
		//drains the ships resrves of engery, the ship can recharge its power but it on has a limited amount
		//long periods of rapid fire will drain the ships power, resulting in a slow firing rate
		if(firing)
		{
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;
			if (elapsed > firingDelay)
			{	
				if(energy <= 99)
				{
					energy ++;//
					Gdx.app.log(HellFire.LOG, "energy recharging!");
				}
				Gdx.app.log(HellFire.LOG, "Firing Weapons");
				firingTimer = System.nanoTime();
			}
		}
		
		//calculates the player hit cool-down
		//this will effectively make the player indestructable for 2 seconds
		long elapsed = (System.nanoTime() - recoveryTimer) / 1000000;
		if(elapsed > 2000)
		{
			recovering = false;
			recoveryTimer = 0;
		}
		
		
		
	}

}

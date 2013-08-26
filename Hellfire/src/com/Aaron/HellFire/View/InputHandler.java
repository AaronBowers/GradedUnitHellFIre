package com.Aaron.HellFire.View;

import com.Aaron.HellFire.HellFire;
import com.Aaron.HellFire.Models.BulletBwrd;
import com.Aaron.HellFire.Models.BulletFwrd;
import com.Aaron.HellFire.Models.Ship;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements InputProcessor
{
	World world;
	Ship ship;
	Vector3 touch = new Vector3();
	Vector2 vec2Touch = new Vector2();
	
	public boolean Fstate;
	public boolean Bstate;
	public boolean UdState;
	public boolean DiagState;
	
	//must reference public static power up
	//power level
	//level 1 - bullets are normal do 1 damage
	//level 2 - bullets do 2 damage 
	//level 3 - bullets do 3 damage/ turns to laser weapon, shoots two thin beams
	//level 4 - bullets do 4 damage beams thicken  
	//level 5 - bullets do 5 damage beams thicken again, each battery fires 3 beams per direction, except for diagonal shots
	
	public InputHandler(World world)
	{
		this.world = world;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		ship = world.getShip();
		switch(keycode)
		{
		case Keys.W:
			ship.getVelocity().y = 1;
			break;
		case Keys.S:
			ship.getVelocity().y = -1;
			break;
		case Keys.A:
			ship.getVelocity().x = -1;
			break;
		case Keys.D:
			ship.getVelocity().x = 1;
			break;
		//controls for weapons
		case Keys.J:
			ship.setFiring(false);
			fireWeapon();
			break;
		case Keys.K:
			ship.changeMode();
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		ship = world.getShip();
		switch(keycode)
		{
		case Keys.W:
			//if(ship.getVelocity().y == 1)
			ship.getVelocity().y = 0;
			break;
		case Keys.S:
			//if(ship.getVelocity().y == -1)
			ship.getVelocity().y = 0;
			break;
		case Keys.D:
			//if(ship.getVelocity().x == -1)
			ship.getVelocity().x = 0;
			break;
		case Keys.A:
			//if(ship.getVelocity().x == 1)
			ship.getVelocity().x = 0;
			break;
		case Keys.J:
			ship.setFiring(true);

			break;			
		}
		
		//insert weapon keybind here
		
		//if tap change weapon to next preset
		//forward 1 bullet
		//back 1 bullet
		//horizontal 4 bullets
		//diag 4 bullets
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		
		return false;
	}
	
	public void fireWeapon()
	{
		int weaponMode = ship.getWeaponMode();
		if(ship.getEnergyLevel() > 0)
		{
			if (weaponMode == 0)//fire forwards
			{
				//Gdx.app.log(HellFire.LOG, "Firing Weapons");
				int weaponDrain = 10;
				ship.setEndergyLevel(weaponDrain);
				world.addFwrdBullet(new  BulletFwrd(BulletFwrd.SPEED, 0, .1f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+45), new Vector2(1,0)));
			}
			else if(weaponMode == 1)//fire backwards
			{
				world.addBwrdBullet(new  BulletBwrd(BulletBwrd.SPEED, 0, .1f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+45), new Vector2(1,0)));
			}
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		//touch.set(screenX, screenY, 0);
		//world.getRenderer().getCamera().unproject(touch);
		//vec2Touch.set(touch.x, touch.y);
		//first values are origin, second set are destination path. bullet will travel indefinatly
		//world.addBullet(new  Bullet(Bullet.SPEED, 1, .1f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+45), new Vector2(50,0)));
		
		//change firing mode here
		
		//add if statement, for collection of power ups
		//if rank 1 
		//if rank 2
		//if rank 3
		
		//add if for firing modes
		//firing mode fwrd
		//firing mode back
		//firing mode up down
		//firing mode diagonal

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{

		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{

		return false;
	}
}

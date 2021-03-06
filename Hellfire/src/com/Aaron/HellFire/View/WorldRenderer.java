package com.Aaron.HellFire.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.Aaron.HellFire.Models.Bullet;
import com.Aaron.HellFire.Models.Enemy;
import com.Aaron.HellFire.Models.MoveableEntity;
import com.Aaron.HellFire.Models.PowerUp;
import com.Aaron.HellFire.Models.PowerUpWeapons;
import com.Aaron.HellFire.Models.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class WorldRenderer
{
	World world;
	SpriteBatch batch;
	InputHandler inputhandler;
	MoveableEntity moveableentity;
	
	//must make this explictly white
	BitmapFont scoreFont, black, white, font;

	Ship ship;
	OrthographicCamera cam;//the view of the world!! or just the view of the current bounds showing the view of the stage... in this case space.
	Texture shipTexture, trackerTexture, BulletTexture, BwrdBulletTexture, EnergyBarTexture, HudBgTexture, PowerUpWeaponsTexture, PowerUpEnergyTexture,
	PowerUpSpeedTexture, PowerUpLivesTexture, PowerUpSpecialTexture, PowerUpBounsTexture;
	float width, height;
	ShapeRenderer sr;
	
	ArrayList<Bullet> bullets;
	Iterator<Bullet> bIter;
	Bullet bullet;
	
	ArrayList<Enemy> enemies;
	Iterator<Enemy> eIter;
	Enemy enemy;
	
	ArrayList<PowerUp> powerups;
	Iterator<PowerUp> PIter;
	PowerUp powerup;
	

	ParticleEmitter PlasmaEmission;

	private Stage stage;
    private Touchpad touchpad;
    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    
    //Variables for button, and button text
	TextureAtlas atlas;
	Skin skin;
	TextButton buttonA, ButtonB;
	Label label;//not used atm 
	
	

	//CONSTRUCTOR
	public WorldRenderer(final World world)
	{
		this.world = world;
		
		world.setRenderer(this);
		
		//set object scales here do not divide the values otherwise stuff will get crazy to locate
		//always leave as default size, unless you really want to zoom the camera.
		//camera can also be zoomed to the pixel scale with a division of 100.
		
		width = (Gdx.graphics.getWidth());//w-1280
		height = (Gdx.graphics.getHeight());//h-720		
		
		//create camera
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width,height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		//create a touch pad skin
		touchpadSkin = new Skin();
		
		//Font file
		scoreFont = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"),
		Gdx.files.internal("data/whitefont_0.tga"), false);
		
		
		
		//Game Textures 
		
		HudBgTexture = new Texture("Hud/HudBg.png");
		HudBgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		shipTexture = new Texture("data/GameSprites/Ship.png");//ship graphic
		shipTexture.getTextureData();
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		

		trackerTexture = new Texture("data/GameSprites/Tracker.png");//tracker graphic
		trackerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		BulletTexture = new Texture("data/GameSprites/bullet.png");
		BulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		BwrdBulletTexture = new Texture("data/GameSprites/bullet.png");
		BwrdBulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		EnergyBarTexture = new Texture("Hud/energyBar.png");
		EnergyBarTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		//set this within a if statement to change the sprite along with the
		//power ups effects
		//note power up effects change depending on what PowerUp type is
		//rolled
		
		//power up 1
		PowerUpWeaponsTexture = new Texture("data/GameSprites/WeaponPwr.png");
		PowerUpWeaponsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		//2
		PowerUpEnergyTexture = new Texture("data/GameSprites/ShipPwr.png");
		PowerUpEnergyTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		//3
		PowerUpSpeedTexture = new Texture("data/GameSprites/ShipSpeed.png");
		PowerUpSpeedTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		//4
		PowerUpLivesTexture = new Texture("data/GameSprites/ShipLives.png");
		PowerUpLivesTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		//5
		PowerUpSpecialTexture = new Texture("data/GameSprites/ShipSpecial.png");
		PowerUpSpecialTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		//6
		PowerUpBounsTexture = new Texture("data/GameSprites/ShipBonus.png");
		PowerUpBounsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		sr = new ShapeRenderer();
		
		
		
		//touch screen controls
	    //Create a touchpad skin    
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);
		
      //Create a Stage and add TouchPad
      	stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
      	stage.addActor(touchpad);			
      	Gdx.input.setInputProcessor(stage);
      	
		PlasmaEmission = new ParticleEmitter();
		
		try
		{
			PlasmaEmission.load(Gdx.files.internal("Effects/fwrdPlasma.p").reader(2024));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Texture PlasmaTexture = new Texture(Gdx.files.internal("Effects/bullet.png"));
		Sprite Plasma = new Sprite(PlasmaTexture);
		PlasmaEmission.setSprite(Plasma);
		PlasmaEmission.getScale().setHigh(0.2f);
		PlasmaEmission.start();
		
		
    	//Handles button controls 
		
      	//draw A Button
		
      	//data files for button and text
    	batch = new SpriteBatch();
    	skin = new Skin();
    	atlas = new TextureAtlas("data/button.pack");
    	skin.addRegions(atlas);
    	white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
    	black  = new BitmapFont(Gdx.files.internal("data/font.fnt"),false);
		
    	Gdx.input.setInputProcessor(stage);
    		
    	TextButtonStyle style = new TextButtonStyle();
    	style.up = skin.getDrawable("buttonnormal");
    	style.down = skin.getDrawable("buttonpressed");
    	style.font = black;
    		
    	buttonA = new TextButton(" A ", style);
    	buttonA.setWidth(110);
    	buttonA.setHeight(110);
    	buttonA.setX(850);
    	buttonA.setY(15);
    	
      	//draw B Button
    	Gdx.input.setInputProcessor(stage);
    	
    	//set sizes for on screen buttons
    	ButtonB = new TextButton(" B ", style);
    	ButtonB.setWidth(110);
    	ButtonB.setHeight(110);
    	ButtonB.setX(1000);
    	ButtonB.setY(15);
		
		
    	skin = new Skin();
		atlas = new TextureAtlas("data/button.pack");
		
		skin.addRegions(atlas);
		
		white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		black  = new BitmapFont(Gdx.files.internal("data/font.fnt"),false);
		


		
		//Sets each level of weapon power
		//when weapon level increases so does the power demand
		//
		//controller for buttons
    	buttonA.addListener(new InputListener()
    	{
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
    		{
    			System.out.println("A down");
    			ship.setFiring(false);
    			int weaponDrain = 0;
    			int weaponMode = ship.getWeaponMode();
    			int weaponLv = ship.getWeaponLv();
    			
    			if (weaponLv == 0)
    			{
		        			if(ship.getEnergyLevel() > 0)
		        			{
		        				if (weaponMode == 0)//fire forwards
		        				{
		        					//Gdx.app.log(HellFire.LOG, "Firing Weapons");
		        					weaponDrain = 4;
		        					if(ship.getEnergyLevel() >= weaponDrain)
		        					{
		            					ship.setEndergyLevel(weaponDrain);
		            					world.addBullet(new  Bullet(Bullet.SPEED, 0, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+45), new Vector2(1,0)));
		        					}
		
		        				}
		        				
		        				if(weaponMode == 1)//fire backwards
		        				{
		        					weaponDrain = 4;
		        					if(ship.getEnergyLevel() >= weaponDrain)
		        					{
		            					ship.setEndergyLevel(weaponDrain);
		            					world.addBullet(new  Bullet(Bullet.SPEED, 0, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y+45), new Vector2(-1,0)));
		        					}
		
		        				}
		        				
		        				if(weaponMode == 2)//fire up down
		        				{
		        					weaponDrain = 8;
		        					if(ship.getEnergyLevel() >= weaponDrain)
		        					{
		            					ship.setEndergyLevel(weaponDrain);
		            					world.addBullet(new  Bullet(Bullet.SPEED, 90, .3f,.1f, new Vector2(ship.getPosition().x+180/2, ship.getPosition().y+90), new Vector2(0,1)));//up bullet
		            					world.addBullet(new  Bullet(Bullet.SPEED, -90, .3f,.1f, new Vector2(ship.getPosition().x+180/2, ship.getPosition().y), new Vector2(0,-1)));//down bullet
		        					}
		        				}
		        				
		        				if(weaponMode == 3)//fire diagonal
		        				{
		        					weaponDrain = 16;
		        					if(ship.getEnergyLevel() >= weaponDrain)
		        					{
		    	    					ship.setEndergyLevel(weaponDrain);
		    	    					
		    	    					//front top right and bottom right
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, 45, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+90), new Vector2(1,1)));//top right
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, -45, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y), new Vector2(1,-1)));//bottom right
		    	    					
		    	    					//back top left and bottom left
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, -135, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y), new Vector2(-1,-1)));//bottom left
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, 135, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y+90), new Vector2(-1,1)));//top left
		        					}
		        				}
		        			} 
    			}
    			else if (weaponLv == 1)
    			{
    				int leveldrain = 1;
		        			if(ship.getEnergyLevel() > 0)
		        			{
		        				if (weaponMode == 0)//fire forwards
		        				{
		        					//Gdx.app.log(HellFire.LOG, "Firing Weapons");
		        					weaponDrain = 4 + leveldrain;
		        					if(ship.getEnergyLevel() >= weaponDrain)
		        					{
		            					ship.setEndergyLevel(weaponDrain);
		            					world.addBullet(new  Bullet(Bullet.SPEED, 0, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+60), new Vector2(1,0)));
		            					world.addBullet(new  Bullet(Bullet.SPEED, 0, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+30), new Vector2(1,0)));
		        					}
		
		        				}
		        				
		        				if(weaponMode == 1)//fire backwards
		        				{
		        					weaponDrain = 4 + leveldrain;
		        					if(ship.getEnergyLevel() >= weaponDrain)
		        					{
		            					ship.setEndergyLevel(weaponDrain);
		            					world.addBullet(new  Bullet(Bullet.SPEED, 0, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y+60), new Vector2(-1,0)));
		            					world.addBullet(new  Bullet(Bullet.SPEED, 0, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y+30), new Vector2(-1,0)));
		        					}
		
		        				}
		        				
		        				if(weaponMode == 2)//fire up down
		        				{
		        					weaponDrain = 8 + leveldrain;
		        					if(ship.getEnergyLevel() >= weaponDrain)
		        					{
		            					ship.setEndergyLevel(weaponDrain);
		            					world.addBullet(new  Bullet(Bullet.SPEED, 90, .3f,.1f, new Vector2(ship.getPosition().x+180/4, ship.getPosition().y+90), new Vector2(0,1)));//up bullet
		            					world.addBullet(new  Bullet(Bullet.SPEED, 90, .3f,.1f, new Vector2(ship.getPosition().x+180/4 + 180/2, ship.getPosition().y+90), new Vector2(0,1)));//up bullet
		            					
		            					world.addBullet(new  Bullet(Bullet.SPEED, -90, .3f,.1f, new Vector2(ship.getPosition().x+180/4, ship.getPosition().y), new Vector2(0,-1)));//down bullet
		            					world.addBullet(new  Bullet(Bullet.SPEED, -90, .3f,.1f, new Vector2(ship.getPosition().x+180/4 + 180/2, ship.getPosition().y), new Vector2(0,-1)));//down bullet
		        					}
		        				}
		        				
		        				if(weaponMode == 3)//fire diagonal
		        				{
		        					weaponDrain = 16 + leveldrain;
		        					if(ship.getEnergyLevel() >= weaponDrain)
		        					{
		    	    					ship.setEndergyLevel(weaponDrain);
		    	    					
		    	    					//front top right and bottom right
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, 45, .3f,.1f, new Vector2(ship.getPosition().x+170, ship.getPosition().y+90), new Vector2(1,1)));//top right
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, 45, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+80), new Vector2(1,1)));//top right
		    	    					
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, -45, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+10), new Vector2(1,-1)));//bottom right
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, -45, .3f,.1f, new Vector2(ship.getPosition().x+170, ship.getPosition().y), new Vector2(1,-1)));//bottom right
		    	    					
		    	    					//back top left and bottom left
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, -135, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y+10), new Vector2(-1,-1)));//bottom left
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, -135, .3f,.1f, new Vector2(ship.getPosition().x+10, ship.getPosition().y), new Vector2(-1,-1)));//bottom left
		    	    					
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, 135, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y+80), new Vector2(-1,1)));//top left
		    	    					world.addBullet(new  Bullet(Bullet.SPEED, 135, .3f,.1f, new Vector2(ship.getPosition().x+10, ship.getPosition().y+90), new Vector2(-1,1)));//top left
		        					}
		        				}
		        			}				
    			}
    			
    			return true;
    		}
    		
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button)
    		{
    			System.out.println("A Up");
    			ship.setFiring(true);
    		}
    		
    	});	
    	
    	//Listener for change weapon cycle
    	ButtonB.addListener(new InputListener()
    	{
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
    		{
    			System.out.println("B down");
    			ship.changeMode();
    			return true;
    		}
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button)
    		{
    			System.out.println("B Up");
    		}
    	});
    	
    	stage.addActor(buttonA);
    	stage.addActor(ButtonB);
    	
	}
	
	
	public void render()
	{
		Gdx.gl.glClearColor(1,1,1,1);//gl clear screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		ship = world.getShip();
		enemies = world.getEnemies();
		bullets = world.getBullets();
		powerups = world.getPowerUps();
		
		//refresh camera position allow to focus player ship
		//cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
		cam.update();
		
		//set batch matrix to camera matrix
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		
		//==== PLayer user interface ===
		
		//DRAWS HUD BACKGROUND
		//This is a test image, used to gauge hud background placement
		batch.draw(HudBgTexture, 0, 720-70, 1280, 70);
		
		//DRAW PLAYER STATS
		scoreFont.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		//scoreFont.scale(0);
		CharSequence scoreStr = "score : " + ship.getScore();
		scoreFont.draw(batch, scoreStr, 1000, 700);
		
		
		//DRAW PLAYER LIVES
		CharSequence livesStr = " : " + ship.getLives();
		scoreFont.draw(batch, livesStr, 560, 700);
		//DRAWS LIVES SPRITE
		batch.draw(shipTexture, 500, 680, 180/3, 80/3);
		

		//draws energy bar sprite
		batch.draw(EnergyBarTexture, 10, 690, 300 / 100 * ship.getEnergyLevel(), 80/3);
		//DRAW PLAYER ENERGY
		CharSequence energyStr = "Energy : " + ship.getEnergyLevel();
		scoreFont.draw(batch, energyStr, 10, 710);
		
		//DRAW PLAYER WEAPON LV
		CharSequence weaponLvStr = "WpnLv : " + ship.getWeaponLv();
		scoreFont.draw(batch, weaponLvStr, 10, 680);
		
		//DRAW PLAYER WEAPON MODE ID
		CharSequence weaponModeStr = "Mode : " + ship.getWeaponMode();
		scoreFont.draw(batch, weaponModeStr, 320, 700);
		//END OF DRAW PLAYER STATS
		

		
		
		
		//plasma trail position

		//setPlasmaRotation();
		
		//draw plasma Emission trail

		//========================== ship movment updates ===============================================
		
		//visual dsiplay for dpad, image moves based on
		//where the touch pad is touched
		ship.getVelocity().x =+ touchpad.getKnobPercentX();
		ship.getVelocity().y =+ touchpad.getKnobPercentY();

		// =============================================================== end ============================
		
		
		//draws player ship
		batch.draw(	
					shipTexture,//define texture region
					ship.getPosition().x, ship.getPosition().y, 0, 0,
					ship.getWidth(), ship.getHeight() //
					, 1f, 1f, ship.getRotation(), 0, 0,
					shipTexture.getWidth(), shipTexture.getHeight(), false, false
					);
		
		
		//draws enemy ship
		eIter = enemies.iterator();
		while(eIter.hasNext())
		{
			enemy = eIter.next();
			batch.draw(
				//Draw tracker
				trackerTexture,//texture name will have to change to accommodate more than one enemy
				enemy.getPosition().x, enemy.getPosition().y,
				enemy.getWidth()/2, enemy.getHeight()/2,//central axis of object simple division
				enemy.getWidth(), enemy.getHeight(), 1, 1,//texture width and height, with scale ratios
				enemy.getRotation(), 0, 0,//rotation
				//Texture size, streach's texture
				trackerTexture.getWidth(), trackerTexture.getHeight(),//draw how much of the texture area? right now its set to 100% of texture size
				false, false//flips hor, vert etc
				);
		}
		
		//draws player bullets ==============================================
			bIter = bullets.iterator();//set rotation here for weapons new position, should fix bounding box
			
			while(bIter.hasNext())
			{
				bullet = bIter.next();
				batch.draw(
					BulletTexture,
					bullet.getPosition().x,bullet.getPosition().y, 0,0,
					bullet.getWidth(), bullet.getHeight(), 1,1,
					bullet.getRotation(), 0,0,
					BulletTexture.getWidth(),BulletTexture.getHeight(),
					false,false
				);
				//PlasmaEmission.setPosition(0 ,0);
				//PlasmaEmission.draw(batch, Gdx.graphics.getDeltaTime());
			}
			
			
		//draw player power ups ==================================================
			
			PIter = powerups.iterator();
			while(PIter.hasNext())
			{
				powerup = PIter.next();
				if (powerup.getType() == 1)
				{
					batch.draw(
							PowerUpWeaponsTexture,
							powerup.getPosition().x, powerup.getPosition().y, 0,0,
							powerup.getHeight(), powerup.getWidth(), 1,1,
							0, 0,0,
							PowerUpWeaponsTexture.getWidth(),PowerUpWeaponsTexture.getHeight(),
							false,false
						);
				}
				else if (powerup.getType() == 2)
				{
					batch.draw(
							PowerUpEnergyTexture,
							powerup.getPosition().x, powerup.getPosition().y, 0,0,
							powerup.getHeight(), powerup.getWidth(), 1,1,
							0, 0,0,
							PowerUpEnergyTexture.getWidth(),PowerUpEnergyTexture.getHeight(),
							false,false
						);
				}
				else if (powerup.getType() == 3)
				{
					batch.draw(
							PowerUpSpeedTexture,
							powerup.getPosition().x, powerup.getPosition().y, 0,0,
							powerup.getHeight(), powerup.getWidth(), 1,1,
							0, 0,0,
							PowerUpSpeedTexture.getWidth(),PowerUpSpeedTexture.getHeight(),
							false,false
						);
				}
				else if (powerup.getType() == 4)
				{
					batch.draw(
							PowerUpLivesTexture,
							powerup.getPosition().x, powerup.getPosition().y, 0,0,
							powerup.getHeight(), powerup.getWidth(), 1,1,
							0, 0,0,
							PowerUpLivesTexture.getWidth(),PowerUpLivesTexture.getHeight(),
							false,false
						);
				}
				else if (powerup.getType() == 5)
				{
					batch.draw(
							PowerUpSpecialTexture,
							powerup.getPosition().x, powerup.getPosition().y, 0,0,
							powerup.getHeight(), powerup.getWidth(), 1,1,
							0, 0,0,
							PowerUpSpecialTexture.getWidth(),PowerUpSpecialTexture.getHeight(),
							false,false
						);
				}
				else if (powerup.getType() == 6)
				{
					batch.draw(
							PowerUpBounsTexture,
							powerup.getPosition().x, powerup.getPosition().y, 0,0,
							powerup.getHeight(), powerup.getWidth(), 1,1,
							0, 0,0,
							PowerUpBounsTexture.getWidth(),PowerUpBounsTexture.getHeight(),
							false,false
						);
				}
			}
			
		batch.end();
		
		//adds controller to stage
		stage.act(Gdx.graphics.getDeltaTime());	    
		stage.draw();
		

		// ==================== testing methods combined with volume and position of objects in game
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Rectangle);
		sr.setColor(Color.BLUE);
		//encapsulate these in sr(); this makes the shape renderer that shows the bounds
		sr.rect(ship.getBounds().x, ship.getBounds().y, ship.getBounds().width, ship.getBounds().height);//must re-size hit area
		
		sr.setColor(Color.RED); 
		
		//enemies bounding boxes
		eIter = enemies.iterator();
		while(eIter.hasNext())
		{
			enemy = eIter.next();
			sr.rect(enemy.getBounds().x,enemy.getBounds().y, enemy.getBounds().width, enemy.getBounds().height);
		}
		
		//====== bullet bounding boxes ==============================
		//forward bullet
		bIter = bullets.iterator();
		while(bIter.hasNext())
		{
			bullet = bIter.next();
			sr.rect(bullet.getBounds().x,bullet.getBounds().y, bullet.getBounds().width, bullet.getBounds().height);
		}
		
		sr.setColor(Color.PINK);
		//poweUp bounding boxies
		PIter = powerups.iterator();
		while(PIter.hasNext())
		{
			powerup = PIter.next();
			sr.rect(powerup.getBounds().x, powerup.getBounds().y, powerup.getBounds().width, powerup.getBounds().height);
		}
		
		sr.end();
	}
	
	//not called yet, but will be called in the future to set plasma beam angles
	//for the different firing modes
	private void setPlasmaRotation()
	{
		float angle = ship.getRotation();
		PlasmaEmission.getAngle().setLow(angle + 270);
		PlasmaEmission.getAngle().setHighMin(angle + 270 - 45);
		PlasmaEmission.getAngle().setHighMax(angle + 270 + 45);
	}
	
	
	public OrthographicCamera getCamera()
	{
		return cam;
	}
	
	public void dispose()
	{
		batch.dispose();
		shipTexture.dispose();
		sr.dispose();
		BulletTexture.dispose();
		trackerTexture.dispose();
		skin.dispose();
		stage.dispose();

	}
	
}

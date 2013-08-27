package com.Aaron.HellFire.View;

import java.awt.Button;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.Aaron.HellFire.HellFire;
import com.Aaron.HellFire.Models.Bullet;
import com.Aaron.HellFire.Models.Enemy;
import com.Aaron.HellFire.Models.MoveableEntity;
import com.Aaron.HellFire.Models.Ship;
import com.Aaron.HellFire.Models.Tracker;
import com.Aaron.HellFire.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
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
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

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
	Texture shipTexture, trackerTexture, BulletTexture, BwrdBulletTexture;
	float width, height, shipwidth, shipheight;
	ShapeRenderer sr;
	
	ArrayList<Bullet> bullets;
	Iterator<Bullet> bIter;
	Bullet b;
	
	ArrayList<Enemy> enemies;
	Iterator<Enemy> eIter;
	
	public boolean movey;
	public boolean movex;

	
	
	Enemy e;
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
		
		shipTexture = new Texture("data/GameSprites/Ship.png");//ship graphic
		shipTexture.getTextureData();
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		

		trackerTexture = new Texture("data/GameSprites/Tracker.png");//tracker graphic
		trackerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		BulletTexture = new Texture("data/GameSprites/bullet.png");
		BulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		BwrdBulletTexture = new Texture("data/GameSprites/bullet.png");
		BwrdBulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
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
		
		//controller for buttons
    	buttonA.addListener(new InputListener()
    	{
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
    		{
    			System.out.println("A down");
    			ship.setFiring(false);
    			
    			int weaponMode = ship.getWeaponMode();
    			if(ship.getEnergyLevel() > 0)
    			{
    				if (weaponMode == 0)//fire forwards
    				{
    					//Gdx.app.log(HellFire.LOG, "Firing Weapons");
    					int weaponDrain = 4;
    					ship.setEndergyLevel(weaponDrain);
    					world.addBullet(new  Bullet(Bullet.SPEED, 0, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+45), new Vector2(1,0)));
    				}
    				else if(weaponMode == 1)//fire backwards
    				{
    					int weaponDrain = 4;
    					ship.setEndergyLevel(weaponDrain);
    					world.addBullet(new  Bullet(Bullet.SPEED, 0, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y+45), new Vector2(-1,0)));
    				}
    				else if(weaponMode == 2)//fire backwards
    				{
    					int weaponDrain = 8;
    					ship.setEndergyLevel(weaponDrain);
    					world.addBullet(new  Bullet(Bullet.SPEED, 90, .3f,.1f, new Vector2(ship.getPosition().x+180/2, ship.getPosition().y+90), new Vector2(0,1)));//up bullet
    					world.addBullet(new  Bullet(Bullet.SPEED, -90, .3f,.1f, new Vector2(ship.getPosition().x+180/2, ship.getPosition().y), new Vector2(0,-1)));//down bullet
    				}
    				else if(weaponMode == 3)//fire backwards
    				{
    					int weaponDrain = 16;
    					ship.setEndergyLevel(weaponDrain);
    					
    					//front top right and bottom right
    					world.addBullet(new  Bullet(Bullet.SPEED, 45, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y+90), new Vector2(1,1)));//top right
    					world.addBullet(new  Bullet(Bullet.SPEED, -45, .3f,.1f, new Vector2(ship.getPosition().x+180, ship.getPosition().y), new Vector2(1,-1)));//bottom right
    					
    					//back top left and bottom left
    					world.addBullet(new  Bullet(Bullet.SPEED, -135, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y), new Vector2(-1,-1)));//bottom left
    					world.addBullet(new  Bullet(Bullet.SPEED, 135, .3f,.1f, new Vector2(ship.getPosition().x, ship.getPosition().y+90), new Vector2(-1,1)));//top left

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
		
		//refresh camera position allow to focus player ship
		//cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
		cam.update();
		
		//set batch matrix to camera matrix
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
												//==== PLayer user interface ===
		
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
		
		//DRAW PLAYER ENERGY
		CharSequence energyStr = "Energy : " + ship.getEnergyLevel();
		scoreFont.draw(batch, energyStr, 10, 700);
		
		//DRAW PLAYER WEAPON LV
		CharSequence weaponLvStr = "WpnLv : " + ship.getWeaponLv();
		scoreFont.draw(batch, weaponLvStr, 100, 650);
		
		//DRAW PLAYER WEAPON MODE ID
		CharSequence weaponModeStr = "Mode : " + ship.getWeaponMode();
		scoreFont.draw(batch, weaponModeStr, 300, 700);
		//END OF DRAW PLAYER STATS
		

		
		
		
		//plasma trail position

		//setPlasmaRotation();
		
		//draw plasma Emission trail

		//========================== ship movment updates ===============================================
		
		//move player ship with d pad
		if(movex == true)
		{
			ship.getVelocity().x = 0;
			Gdx.app.log(HellFire.LOG, "TRUE!");
		}
		else if(movex == false)
		{
			ship.getVelocity().x =+ touchpad.getKnobPercentX();
		}
		//if (movex == true)
		//{
		ship.getVelocity().y =+ touchpad.getKnobPercentY();
		//}
		
		// =============================================================== end ============================
		
		
		//draws player ship
		batch.draw(	
					shipTexture,//define texture region
					ship.getPosition().x, ship.getPosition().y, 0, 0,
					ship.getWidth(), ship.getHeight() //
					, 1f, 1f, ship.getRotation(), 0, 0,
					shipTexture.getWidth(), shipTexture.getHeight(), false, false
					);
		
		//dpad
		
		//set true false catchs here to stop momentum
		
		

		
		//draws enemy ship
		eIter = enemies.iterator();
		while(eIter.hasNext())
		{
			e = eIter.next();
			batch.draw(
				//Draw tracker
				trackerTexture,//texture name will have to change to accommodate more than one enemy
				e.getPosition().x, e.getPosition().y,
				e.getWidth()/2, e.getHeight()/2,//central axis of object simple division
				e.getWidth(), e.getHeight(), 1, 1,//texture width and height, with scale ratios
				e.getRotation(), 0, 0,//rotation
				//Texture size, streach's texture
				trackerTexture.getWidth(), trackerTexture.getHeight(),//draw how much of the texture area? right now its set to 100% of texture size
				false, false//flips hor, vert etc
				);
		}
		
		//draws player bullets ==============================================
			bIter = bullets.iterator();
			
			while(bIter.hasNext())
			{
				b = bIter.next();
				batch.draw(
					BulletTexture,
					b.getPosition().x,b.getPosition().y, 0,0,
					b.getWidth(), b.getHeight(), 1,1,
					b.getRotation(), 0,0,
					BulletTexture.getWidth(),BulletTexture.getHeight(),
					false,false
				);
				//PlasmaEmission.setPosition(0 ,0);
				//PlasmaEmission.draw(batch, Gdx.graphics.getDeltaTime());
			}
			
			
		//draw touch pad
			
		batch.end();
		
		
		//adds controller to stage
		stage.act(Gdx.graphics.getDeltaTime());	    
		stage.draw();
		

		// ==================== testing methods combined with volume and position of obects in game
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
			e = eIter.next();
			sr.rect(e.getBounds().x,e.getBounds().y, e.getBounds().width, e.getBounds().height);
		}
		
		//====== bullet bounding boxes ==============================
		//forward bullet
		bIter = bullets.iterator();
		while(bIter.hasNext())
		{
			b = bIter.next();
			sr.rect(b.getBounds().x,b.getBounds().y, b.getBounds().width, b.getBounds().height);
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
	
	public void setMovex(boolean movex)
	{
		this.movex = movex;
	}
	
	public boolean getMovex(boolean movex)
	{
		return this.movex;
	}
	
	public void setMovey(boolean movey)
	{
		this.movey = movey;
	}
	
	public boolean getMovey(boolean movey)
	{
		return this.movey;
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

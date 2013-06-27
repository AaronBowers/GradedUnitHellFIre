package com.Aaron.HellFire.View;

import java.util.Iterator;

import com.Aaron.HellFire.Models.Bullet;
import com.Aaron.HellFire.Models.Ship;
import com.Aaron.HellFire.Models.Tracker;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class WorldRenderer
{

	World world;
	SpriteBatch batch;
	Ship ship;
	OrthographicCamera cam;//our view of the world!! or just our view of the current bounds showing our view of the stage... in this case space.
	Texture shipTexture, trackerTexture, bulletTexture;
	float width, height, shipwidth, shipheight;
	ShapeRenderer sr;
	Tracker tracker;
	Array<Bullet> bullets;
	Iterator<Bullet> bIter;
	Bullet b;
	
	public WorldRenderer(World world)
	{
		this.world = world;
		
		world.setRenderer(this);
		
		
		width = (Gdx.graphics.getWidth()/80) ;
		height = (Gdx.graphics.getHeight() /80);
		
		
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width,height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		
		shipTexture = new Texture("data/GameSprites/ship.png");//ship graphic
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		

		trackerTexture = new Texture("data/GameSprites/Tracker.png");
		trackerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		bulletTexture = new Texture("data/GameSprites/bullet.png");
		bulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		sr = new ShapeRenderer();
	}
	
	public void render()
	{
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		ship = world.getShip();
		tracker = world.getTracker();
		bullets = world.getBullets();
		
		cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		//draws player ship
		batch.draw(shipTexture,ship.getPosition().x,		ship.getPosition().y, 0,0, 	ship.getWidth() ,	ship.getHeight(), 1,1,ship.getRotation(), 0,0,shipTexture.getWidth(),shipTexture.getHeight(),false,false);
		//draws enemy ship
		batch.draw(trackerTexture, tracker.getPosition().x, tracker.getPosition().y, 	tracker.getWidth()/2, tracker.getHeight()/2,tracker.getWidth(), tracker.getHeight(),1,1, tracker.getRotation(),0,0, trackerTexture.getWidth(), trackerTexture.getHeight(), false, false);
		//draws bullet
		bIter = bullets.iterator();
			
			while(bIter.hasNext())
			{
				b = bIter.next();
				batch.draw(bulletTexture, b.getPosition().x,		b.getPosition().y, 0,0, 	b.getWidth() ,	b.getHeight(), 1,1,b.getRotation(), 0,0,bulletTexture.getWidth(),bulletTexture.getHeight(),false,false);
			}
			
		batch.end();
		
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Rectangle);
		sr.setColor(Color.CYAN);
		sr.rect(ship.getBounds().x, ship.getBounds().y, ship.getBounds().width, ship.getBounds().height);
		
		sr.setColor(Color.RED);
		sr.rect(tracker.getBounds().x,tracker.getBounds().y, tracker.getBounds().width, tracker.getBounds().height);
		sr.end();
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
	}
	
}

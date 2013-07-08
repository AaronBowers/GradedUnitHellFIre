package com.Aaron.HellFire.View;

import java.util.Iterator;

import com.Aaron.HellFire.Models.Bullet;
import com.Aaron.HellFire.Models.Enemy;
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
	Array<Bullet> bullets;
	Array<Enemy> enemies;
	Iterator<Bullet> bIter;
	Iterator<Enemy> eIter;
	Bullet b;
	Enemy e;
	
	public WorldRenderer(World world)
	{
		this.world = world;
		
		world.setRenderer(this);
		
		width = (Gdx.graphics.getWidth()/100);
		height = (Gdx.graphics.getHeight()/100);
		
		//width = (Gdx.graphics.getWidth()/280);
		//height = (Gdx.graphics.getHeight()/130);
		
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width,height);
		cam.update();
		
		
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		
		shipTexture = new Texture("data/GameSprites/Ship.png");//ship graphic
		shipTexture.getTextureData();
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		

		trackerTexture = new Texture("data/GameSprites/Tracker.png");//tracker graphic
		trackerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		bulletTexture = new Texture("data/GameSprites/bullet.png");
		bulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear );
		
		sr = new ShapeRenderer();
	}
	
	public void render()
	{
		Gdx.gl.glClearColor(0,0,0,1);//gl clear screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		ship = world.getShip();
		enemies = world.getEnemies();
		bullets = world.getBullets();
		
		cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		//draws player ship
		
		batch.draw(	
					shipTexture,//define texture region
					ship.getPosition().x, ship.getPosition().y, 0, 0,
					ship.getWidth(), ship.getHeight()
					, 1f, 1f, ship.getRotation(), 0, 0,
					shipTexture.getWidth(), shipTexture.getHeight(), false, false
					);
		
		//draws enemy ship
		eIter = enemies.iterator();
		while(eIter.hasNext())
		{
			e = eIter.next();
			batch.draw(
				//Draw tracker
				trackerTexture,//texture name will have to change to accomdate more than one enemy
				e.getPosition().x, e.getPosition().y,
				e.getWidth()/2, e.getHeight()/2,
				e.getWidth(), e.getHeight(), 1, 1,
				e.getRotation(), 0, 0,
				//Texture size, streach's texture
				trackerTexture.getWidth(), trackerTexture.getHeight(),
				false, false
				);
		}
		
		//draws bullet
			bIter = bullets.iterator();
			
			while(bIter.hasNext())
			{
				b = bIter.next();
				batch.draw(
					bulletTexture,
					b.getPosition().x,b.getPosition().y, 0,0,
					b.getWidth(), b.getHeight(), 1,1,
					b.getRotation(), 0,0,
					bulletTexture.getWidth(),bulletTexture.getHeight(),
					false,false
				);
			}
		batch.end();
		
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Rectangle);
		sr.setColor(Color.CYAN);
		//encapsulate these in sr(); this makes the hit detection bounds
		sr.rect(ship.getBounds().x, ship.getBounds().y, ship.getBounds().width, ship.getBounds().height);
		
		
		sr.setColor(Color.RED); 
		//enemies bounding boxes
		eIter = enemies.iterator();
		while(eIter.hasNext())
		{
			e = eIter.next();
			sr.rect(e.getBounds().x,e.getBounds().y, e.getBounds().width, e.getBounds().height);
		}
		//bullets bounding boxes
		bIter = bullets.iterator();
		while(bIter.hasNext())
		{
			b = bIter.next();
			sr.rect(b.getBounds().x,b.getBounds().y, b.getBounds().width, b.getBounds().height);
		}
		
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

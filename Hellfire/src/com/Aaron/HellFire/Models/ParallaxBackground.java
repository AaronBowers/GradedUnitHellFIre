package com.Aaron.HellFire.Models;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//currently not working
//cannot figure out how to load map into game :(
// may just load directly into game world and add a loader to change the backgrounds.


public class ParallaxBackground
{

	private ParallaxLayer[] layers;
	
	private Camera camera;
	
	private SpriteBatch batch;
	
	
	public ParallaxBackground(ParallaxLayer[] pLayers, Camera pCamera,
			SpriteBatch pBatch)
	{
		layers = pLayers;
		camera = pCamera;
		batch = pBatch;
	}
	
	public void render()
	{
		batch.setProjectionMatrix(camera.projection);
		batch.begin();
		for(ParallaxLayer layer : layers)
		{
			batch.draw(layer.region, -camera.viewportWidth/2
					- layer.positionX, -camera.viewportHeight/2
					-layer.positionY);
		}
		batch.end();
	}
	
	public void moveX(float pDelta)
	{
		for (ParallaxLayer layer : layers)
		{
			layer.moveX(pDelta);
		}
	}
	
	public void MoveY(float pDelta)
	{
		for (ParallaxLayer layer : layers)
		{
			layer.moveY(pDelta);
		}
	}
	
	
}

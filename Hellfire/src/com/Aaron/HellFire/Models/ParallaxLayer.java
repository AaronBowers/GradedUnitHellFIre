package com.Aaron.HellFire.Models;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

//the movement commands for the layer
public class ParallaxLayer
{

	public TextureRegion region;
	
	float ratioX, ratioY;
	
	float positionX, positionY;
	
	public ParallaxLayer(TextureRegion pRegion, float pRatioX, float pRatioY)
	{
		region = pRegion;
		ratioX = pRatioX;
		ratioY = pRatioY;
	}
	
	protected void moveX(float pDelta)
	{
		positionX += pDelta * ratioX;
	}
	
	protected void moveY(float pDelta)
	{
		positionY += pDelta * ratioY;
	}
}

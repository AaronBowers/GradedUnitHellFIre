package com.Aaron.HellFire.Screens;

import com.Aaron.HellFire.HellFire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class MainMenu implements Screen
{
	HellFire game;
	public static int count = 0;
	Stage stage;
	BitmapFont black, white, font;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton button;
	Label label;
	
	


	public MainMenu(HellFire game)
	{
		this.game = game;
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0,0,1,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		font = new BitmapFont();
		

		
		stage.act(delta);
		
		batch.begin();
		stage.draw();
		font.draw(batch, "qweqweqw", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		if(stage == null)
		{
			stage = new Stage(width, height, true);
		}
		stage.clear();
		
		Gdx.input.setInputProcessor(stage);
		
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
		
		button = new TextButton("Start!", style);
		button.setWidth(400);
		button.setHeight(100);
		button.setX(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2);
		button.setY(Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
	
		//handles main menu selection menu button action.
		button.addListener(new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				System.out.println("down");
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button)
			{
				System.out.println("Up");
				game.setScreen(new GameScreen(game));
			}
		});
		
		//label style and on-screen positioning. 
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		label = new Label("HELLFIRE", ls);
		label.setX(0);
		label.setY(Gdx.graphics.getHeight() / 2  + 100);
		label.setWidth(width);
		label.setAlignment(Align.center);		
		label.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		
	
		stage.addActor(button);
		stage.addActor(label);
	}

	@Override
	public void show()
	{
		batch = new SpriteBatch();
		skin = new Skin();
		atlas = new TextureAtlas("data/button.pack");
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		black  = new BitmapFont(Gdx.files.internal("data/font.fnt"),false);
	
		
	}

	@Override
	public void hide()
	{
		dispose();
	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void dispose() 
	{
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		stage.dispose();
		
	}

}

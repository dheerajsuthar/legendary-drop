package com.dheerajsuthar.libgdxprojects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game {

	public SpriteBatch spriteBatch;
	public BitmapFont font;
	private Screen gameScreen;
	public int finalScore;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		spriteBatch.dispose();
		font.dispose();
		if (this.gameScreen != null) {
			this.gameScreen.dispose();
		}
	}

	public Screen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(Screen gameScreen) {
		this.gameScreen = gameScreen;
	}
}

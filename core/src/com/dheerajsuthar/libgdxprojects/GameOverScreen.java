package com.dheerajsuthar.libgdxprojects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameOverScreen implements Screen {
	final MyGame game;
	OrthographicCamera camera;

	public GameOverScreen(final MyGame game) {
		// TODO Auto-generated constructor stub
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.spriteBatch.setProjectionMatrix(camera.combined);

		game.spriteBatch.begin();
		game.font.draw(game.spriteBatch, "Game Over!", 350, 240);
		game.font.draw(game.spriteBatch, "Your score: " + game.finalScore, 350, 140);
		game.font.draw(game.spriteBatch, "Press 'M' to go back to Main Menu", 350, 40);
		game.spriteBatch.end();
		if (Gdx.input.isKeyPressed(Input.Keys.M)) {
			game.setScreen(new MainMenuScreen(game));
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}

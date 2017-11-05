package com.dheerajsuthar.libgdxprojects;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	private static final int MAX_DROPPED_COUNT = 5;
	final MyGame game;
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private Rectangle bucket;
	private Vector3 touchPos;
	private Array<Rectangle> rainDrops;
	private long lastDropTime;
	private Rectangle rainDrop;
	private Iterator<Rectangle> iterator;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private int dropsCollected;
	private int dropsDropped;

	private void spawnDrop() {
		Rectangle rainDrop = new Rectangle();
		rainDrop.x = MathUtils.random(0, 800 - 64);
		rainDrop.y = 480;
		rainDrop.height = 64;
		rainDrop.width = 64;
		rainDrops.add(rainDrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	public GameScreen(final MyGame game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.game.setGameScreen(this);
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		spriteBatch = this.game.spriteBatch;
		rainMusic.setLooping(true);


		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		rainDrops = new Array<Rectangle>();
		spawnDrop();
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		rainMusic.play();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		this.game.font.draw(spriteBatch, "Drops Collected: " + dropsCollected, 600, 480);
		this.game.font.setColor(Color.RED);
		this.game.font.draw(spriteBatch, "Drops Missed: " + dropsDropped, 600, 460);
		this.game.font.setColor(Color.WHITE);
		spriteBatch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle rainDrop : rainDrops) {
			spriteBatch.draw(dropImage, rainDrop.x, rainDrop.y);
		}
		spriteBatch.end();

		if (Gdx.input.isTouched()) {
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			bucket.x += 200 * Gdx.graphics.getDeltaTime();
		}

		if (bucket.x < 0) {
			bucket.x = 0;
		}
		if (bucket.x > 800 - 64) {
			bucket.x = 800 - 64;
		}

		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			spawnDrop();
		}

		iterator = rainDrops.iterator();
		while (iterator.hasNext()) {
			rainDrop = iterator.next();
			rainDrop.y -= MathUtils.random(100, 300) * Gdx.graphics.getDeltaTime();
			if (rainDrop.y + 64 < 0) {
				iterator.remove();
				dropsDropped++;
				if (dropsDropped == MAX_DROPPED_COUNT) {
					game.finalScore = dropsCollected;
					game.setScreen(new GameOverScreen(game));
				}
			}
			if (rainDrop.overlaps(bucket)) {
				dropSound.play();
				iterator.remove();
				dropsCollected++;
			}
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
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}

}

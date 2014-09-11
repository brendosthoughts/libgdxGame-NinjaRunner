package wiser.development.starAssault.screens;

import java.util.ArrayList;

import wiser.development.starAssault.utils.Assets;
import wiser.development.starAssault.utils.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;


public class GameOverScreen implements Screen{
	public static final float CAMERA_WIDTH = 15f;
	public static final float CAMERA_HEIGHT = 10.5f;


	Game game;

	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle soundBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Rectangle mainMenuBounds;
	Vector3 touchPoint;
	Rectangle retryBounds;

	int levelNum;

	int levelSelect, i, j;

	public GameOverScreen (Game game, int level) {
		this.game = game;
		this.levelNum=level;
		guiCam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		guiCam.position.set(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 0);
		batcher = new SpriteBatch();
		soundBounds = new Rectangle(0, 0, 1.5f, 1.5f);
		retryBounds= new Rectangle(0, CAMERA_HEIGHT/5, CAMERA_WIDTH/3, 2*CAMERA_HEIGHT/5);
		mainMenuBounds = new Rectangle(CAMERA_WIDTH/2, CAMERA_HEIGHT/5, CAMERA_WIDTH/3, 2*CAMERA_HEIGHT/5);
		touchPoint = new Vector3();
		levelSelect=0;
	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (retryBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new GameScreen(game, levelNum));
			}
			if (mainMenuBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
			}

			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
			}
		}
	}

	long last = TimeUtils.nanoTime();

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);

		batcher.disableBlending();



		batcher.begin();
		batcher.enableBlending();
		batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 1.5f, 1.5f);

		batcher.draw(Assets.gameOver,CAMERA_WIDTH/8, 3*CAMERA_HEIGHT/5, CAMERA_WIDTH*3 /4 , 2*CAMERA_HEIGHT/5 );

		batcher.draw(Assets.retry, CAMERA_WIDTH /6 , CAMERA_HEIGHT/5, CAMERA_WIDTH/8, CAMERA_HEIGHT/8 );
		batcher.draw(Assets.menu, 4*CAMERA_WIDTH /6 , CAMERA_HEIGHT/5, CAMERA_WIDTH/8, CAMERA_HEIGHT/8 );		




		batcher.end();


	}

	@Override
	public void render (float delta) {
		update();
		draw();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show () {
	}

	@Override
	public void hide () {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}

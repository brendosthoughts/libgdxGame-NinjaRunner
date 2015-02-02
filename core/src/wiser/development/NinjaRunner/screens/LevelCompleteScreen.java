package wiser.development.NinjaRunner.screens;

import java.util.ArrayList;

import wiser.development.NinjaRunner.utils.Assets;
import wiser.development.NinjaRunner.utils.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class LevelCompleteScreen implements Screen{
	public static final float CAMERA_WIDTH = 15f;
	public static final float CAMERA_HEIGHT = 10.5f;


	Game game;

	OrthographicCamera guiCam;
	SpriteBatch batcher;

	Rectangle nextLevelBounds;
	Vector3 touchPoint;
	Rectangle retryBounds;

	int levelNum;

	int levelSelect, i, j;

	public LevelCompleteScreen (Game game, int level) {
		this.game = game;
		this.levelNum=level;
		guiCam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		guiCam.position.set(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 0);
		batcher = new SpriteBatch();
		nextLevelBounds= new Rectangle( 5*CAMERA_WIDTH/8  , CAMERA_HEIGHT/8, CAMERA_WIDTH/4 ,CAMERA_HEIGHT/5);
		retryBounds= new Rectangle( CAMERA_WIDTH/8  , CAMERA_HEIGHT/8, CAMERA_WIDTH/4 ,CAMERA_HEIGHT/5);
		
		touchPoint = new Vector3();
		if(levelNum == Settings.levelReached()){
			
			Settings.newLevelComplete();
		}
	}

	public void update () {
	
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			if (nextLevelBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);

				game.setScreen(new GameScreen(game, this.levelNum+1));
			}
			if (retryBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);

				game.setScreen(new GameScreen(game, this.levelNum));

			}
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.disableBlending();



		batcher.begin();
		batcher.enableBlending();
		if(Settings.levelReached() < Settings.totalLevels()){
		batcher.draw(Assets.levelComplete,  CAMERA_WIDTH/8  , CAMERA_HEIGHT/2, 3*CAMERA_WIDTH/4 ,2*CAMERA_HEIGHT/5);
		batcher.draw(Assets.retry,  CAMERA_WIDTH/8  ,CAMERA_HEIGHT/8, CAMERA_WIDTH/4 ,CAMERA_HEIGHT/5);
		batcher.draw(Assets.nextLevel, + 9*CAMERA_WIDTH/16  ,  CAMERA_HEIGHT/8, CAMERA_WIDTH/4 , CAMERA_HEIGHT/6);
		batcher.draw(Assets.play, 5*CAMERA_WIDTH/8  ,  CAMERA_HEIGHT/4, 3*CAMERA_WIDTH/16 , 3*CAMERA_HEIGHT/16);
		}else{
			batcher.draw(Assets.logo,  CAMERA_WIDTH/8  , CAMERA_HEIGHT/2, 3*CAMERA_WIDTH/4 ,2*CAMERA_HEIGHT/5);
				
			
		}
		
		batcher.disableBlending();


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
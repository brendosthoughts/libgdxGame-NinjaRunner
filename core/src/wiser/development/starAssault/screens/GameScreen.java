package wiser.development.starAssault.screens;
import wiser.development.starAssault.controller.BobController;
import wiser.development.starAssault.controller.SkeletonController;
import wiser.development.starAssault.model.World;
import wiser.development.starAssault.utils.Assets;
import wiser.development.starAssault.view.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;



public class GameScreen implements Screen, InputProcessor {

	public enum GameState{
		READY, RUNNING, PAUSED, LEVEL_END, GAME_OVER ;
	}
	public static final float CAMERA_WIDTH = 10f;
	public static final float CAMERA_HEIGHT = 7f;
	
	Game game;
	private OrthographicCamera cam;
	private World world;
	private WorldRenderer renderer;
	private BobController	controller;
	private SkeletonController skeletonController;
	private int width, height;
	SpriteBatch batcher;
	public GameState gameState;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Vector3 touchPoint;
	public GameScreen(Game game, int levelNum){
		touchPoint = new Vector3();
		this.game =game;
		gameState=GameState.RUNNING;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(0,0, 0);
		this.cam.update();
		
		batcher = new SpriteBatch();
		pauseBounds = new Rectangle(  cam.position.x + CAMERA_WIDTH/2 -1f, cam.position.y +CAMERA_HEIGHT/2 -1f, 1f,1f);
		resumeBounds = new Rectangle(CAMERA_WIDTH, CAMERA_HEIGHT, CAMERA_WIDTH, CAMERA_HEIGHT );
		quitBounds = new Rectangle(6f, 4f,4f, 1f);
		
		world = new World(levelNum);
		renderer = new WorldRenderer(world, false, this.cam, batcher);
        controller = new BobController(world, this);
        skeletonController = new SkeletonController(world, this);
        Gdx.input.setInputProcessor(this);
	}
 
	public void setGameState(GameState state){
		gameState=state;
		
	}
	public void update (float deltaTime) {

		switch (gameState) {
		case READY:
			//updateReady();
			break;
		case RUNNING:
			updateRunning(deltaTime);
			break;
		case PAUSED:
			updatePaused();
			break;
		case LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}
	
	private void updatePaused() {
		if (Gdx.input.justTouched()) {
			cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				gameState = GameState.RUNNING;
				return;
			}

			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
		
	}

	private void updateRunning(float delta){
		 Gdx.gl.glClearColor(0.7f, 0.1f, 0.1f, 1);
		 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.justTouched()) {
			cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				gameState = GameState.PAUSED;
				return;
			}
		}
		 controller.update(delta);
		 skeletonController.update(delta);
		 renderer.render();

	}
	private void updateLevelEnd(){
		if (Gdx.input.justTouched()) {
			game.setScreen(new MainMenuScreen(game));
		}		
	}
	private void updateGameOver(){
		if (Gdx.input.justTouched()) {
			game.setScreen(new MainMenuScreen(game));
		}		
	}
	
	public void draw(){
		switch (gameState) {
		case READY:
			presentReady();
			break;
		case RUNNING:
			presentRunning();
			break;
		case PAUSED:
			presentPaused();
			break;
		case LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		
	}
	private void presentLevelEnd() {
		batcher.draw(Assets.logo, cam.position.x -CAMERA_WIDTH/2, cam.position.y -CAMERA_HEIGHT/4,CAMERA_WIDTH/2, 3*CAMERA_HEIGHT/4);
		
	}

	private void presentReady() {
		// TODO Auto-generated method stub
		
	}

	private void presentRunning() {
		
		batcher.draw(Assets.pause,  cam.position.x + CAMERA_WIDTH/2 -1f, cam.position.y +CAMERA_HEIGHT/2 -1f, 1f,1f);
		
		
	}

	private void presentPaused() {
		batcher.draw(Assets.pauseMenu, cam.position.x -CAMERA_WIDTH/2, cam.position.y -CAMERA_HEIGHT/2,CAMERA_WIDTH, CAMERA_HEIGHT);

		
	}

	private void presentGameOver() {
		batcher.draw(Assets.gameOver, cam.position.x -CAMERA_WIDTH/3, cam.position.y -CAMERA_HEIGHT/3, 2*CAMERA_WIDTH/3, 2*CAMERA_HEIGHT/3);
		batcher.draw(Assets.arrow, 320 - 320/2, 240 -96, 160, 96) ;

		
	}

	@Override
	public void render(float delta) {		
		update(delta);
		batcher.begin();
		draw();
		batcher.end();
	}

 @Override
	public void show() {

	}
@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width=width;
		this.height=height;
	}
 
@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

@Override
	public void pause() {
	//	if (gameState == gameState.RUNNING) gameState = gameState.PAUSED;
	}

@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
//* InputProcessor methods ***************************//


@Override
public boolean keyDown(int keycode) {
	if (keycode == Keys.LEFT)
		controller.leftPressed();
	if (keycode == Keys.RIGHT)
		controller.rightPressed();
	if (keycode == Keys.Z)
		controller.jumpPressed();
	if (keycode == Keys.X)
		controller.punchPressed();
	return true;
}

@Override
public boolean keyUp(int keycode) {
	if (keycode == Keys.LEFT)
		controller.leftReleased();
	if (keycode == Keys.RIGHT)
		controller.rightReleased();
	if (keycode == Keys.Z)
		controller.jumpReleased();
	if (keycode == Keys.X)
		controller.punchReleased();
	if (keycode == Keys.D)
		renderer.setDebug(!renderer.isDebug());
	return true;
}

@Override
public boolean keyTyped(char character) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean touchDown(int x, int y, int pointer, int button) {
	/*if (!Gdx.app.getType().equals(ApplicationType.Android))
		return false;*/
	if (x < width / 3 && y > height / 2) {
		if(x< width/6){		
			controller.leftPressed();
		}else{
			controller.rightPressed();	
		}		
		return true;
	}
	if (x > width / 2 && y > height / 2) {
		controller.jumpPressed();
		return true;
	}
	if (pauseBounds.contains(x, y)) {
		Assets.playSound(Assets.clickSound);
		gameState = GameState.PAUSED;
	}
	return false;
}

@Override
public boolean touchUp(int x, int y, int pointer, int button) {
/*	if (!Gdx.app.getType().equals(ApplicationType.Android))
		return false;
*/	if (x < width / 3 && y > height / 2) {
/*		if(x< width/6){		
			controller.leftReleased();
		}else{
			controller.rightReleased();	
		}		
		jsut release both to make sure that on drag from left to right 
		or vice versa bob doesn't just eep running in one direction ... this should be better dealt 
		with in some sort of state machine, in the future. 
*/
		controller.leftReleased();
		controller.rightReleased();
		
	}
	if (x > width / 2 && y > height / 2) {
		controller.jumpReleased();
	}


	return true;
}

@Override
public boolean touchDragged(int x, int y, int pointer) {
	// TODO Auto-generated method stub
	return false;
}

// @Override
public boolean touchMoved(int x, int y) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean scrolled(int amount) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean mouseMoved(int screenX, int screenY) {
	// TODO Auto-generated method stub
	return false;
}


}

package wiser.development.starAssault.screens;
import wiser.development.starAssault.controller.BobController;
import wiser.development.starAssault.controller.MyInputProcessor;
import wiser.development.starAssault.controller.MyInputProcessor.DirectionListener;
import wiser.development.starAssault.controller.ObjectController;
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
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;



public class GameScreen implements Screen, InputProcessor, GestureListener{

	public enum GameState{
		READY, RUNNING, PAUSED, LEVEL_END, GAME_OVER ;
	}
	public static final float CAMERA_WIDTH = 10f;
	public static final float CAMERA_HEIGHT = 7f;
	
	Game game;
	public OrthographicCamera cam;
	private World world;
	private WorldRenderer renderer;
	private BobController	controller;
	private SkeletonController skeletonController;
	private ObjectController objectController;
	private int width, height;
	SpriteBatch batcher;
	public GameState gameState;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle ninjaInfo;
	Vector3 touchPoint;
	String numThrowingStars;
	GestureDetector gestureDetector ;
	public GameScreen(Game game, int levelNum){
		touchPoint = new Vector3();
		this.game =game;
		gameState=GameState.RUNNING;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(0,0, 0);
		this.cam.update();
		
		batcher = new SpriteBatch();
		pauseBounds = new Rectangle( this.cam.position.x +  CAMERA_WIDTH/2 -1f,this.cam.position.y+CAMERA_HEIGHT/2  -1f, 2f, 2f);
		resumeBounds = new Rectangle( this.cam.position.x - CAMERA_WIDTH/2, this.cam.position.y , CAMERA_WIDTH, CAMERA_HEIGHT/2);
		quitBounds = new Rectangle( this.cam.position.x -  CAMERA_WIDTH/2 ,this.cam.position.y -CAMERA_HEIGHT/2 ,CAMERA_WIDTH, CAMERA_HEIGHT/2);
		ninjaInfo = new Rectangle(  cam.position.x - CAMERA_WIDTH/2 +1f, cam.position.y +CAMERA_HEIGHT/2 -1f, 1f,1f);
		
		world = new World(levelNum);
		renderer = new WorldRenderer(world, false, this, batcher);
        controller = new BobController(world, this);
        skeletonController = new SkeletonController(world, this);
        objectController = new ObjectController(world, this);
        
       gestureDetector = new GestureDetector(this);
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
		this.cam.update();
	}
	
	private void updatePaused() {
		Gdx.gl.glClearColor(0.7f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		resumeBounds.set( this.cam.position.x - CAMERA_WIDTH/2, this.cam.position.y  , CAMERA_WIDTH, CAMERA_HEIGHT/2);
		quitBounds.set(  this.cam.position.x - CAMERA_WIDTH/2, this.cam.position.y -CAMERA_HEIGHT/2 , CAMERA_WIDTH, CAMERA_HEIGHT/2);

		if (Gdx.input.justTouched()) {
			cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				gameState = GameState.RUNNING;
				return;
			}

			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				gameState = GameState.GAME_OVER;
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
		// update rectangle bounds for pause button and pause menu 
		pauseBounds.set(  this.cam.position.x + CAMERA_WIDTH/2 -1f , this.cam.position.y +CAMERA_HEIGHT/2 -1f  , 2f, 2f);
		objectController.update(delta);
		skeletonController.update(delta);
		controller.update(delta); 
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
		batcher.draw(Assets.ninjaStars,  this.cam.position.x - CAMERA_WIDTH/2 +0.5f, this.cam.position.y +CAMERA_HEIGHT/2 -0.5f, 0.5f,0.5f);
		numThrowingStars = ""+controller.getBob().getThrowingStars();
		Assets.font.setColor(0f, 0f, 0f, 1f);
		Assets.font.setScale(0.04f);
		Assets.font.draw(batcher,numThrowingStars , cam.position.x - CAMERA_WIDTH/2 + 0.5f, cam.position.y +CAMERA_HEIGHT/2 -0.5f);
	}
	private void presentPaused() {
		//batcher.draw(Assets.pauseMenu, cam.position.x -CAMERA_WIDTH/2, cam.position.y -CAMERA_HEIGHT/2,CAMERA_WIDTH, CAMERA_HEIGHT);

		
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
	if (keycode == Keys.C)
		controller.throwPressed();
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
	if (keycode == Keys.C)
		controller.throwReleased();
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
	/*if (x > width / 2 && y > height / 2) {
		controller.jumpPressed();
		return true;
	}

	if( x> width/2 && y < height/2){
		controller.punchPressed();
	}
	*/
	if (pauseBounds.contains(x, y)) {
		Assets.playSound(Assets.clickSound);
		gameState = GameState.PAUSED;
		return true;
	}
	
	
	
	return false;
}

@Override
public boolean touchUp(int x, int y, int pointer, int button) {
/*	if (!Gdx.app.getType().equals(ApplicationType.Android))
		return false;
*/
	if (x < width / 3 && y > height / 2) {
		if(x< width/6){		
			controller.leftReleased();
			return true;
		}else{
			controller.rightReleased();	
			return true;
		
		}		
	}
	return false;
/*
		jsut release both to make sure that on drag from left to right 
		or vice versa bob doesn't just eep running in one direction ... this should be better dealt 
		with in some sort of state machine, in the future. 
*/
		
//		controller.leftReleased();
//		controller.rightReleased();
//		
//	}
//	if (x > width / 2 && y > height / 2) {
//		controller.jumpReleased();
//	}



	
}

@Override
public boolean touchDragged(int x, int y, int pointer) {
		
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
/// neew to figure out how to integrate swipes into game properly


@Override
public boolean touchDown(float x, float y, int pointer, int button) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean tap(float x, float y, int count, int button) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean longPress(float x, float y) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean fling(float velocityX, float velocityY, int button) {
	
	if(Math.abs(velocityX)>Math.abs(velocityY)){
		if(velocityX>0){
			controller.throwPressed();
			controller.throwReleased();
			return true;
		}else{
			return false;
		}
	}else{
		if(velocityY>0){
			controller.jumpPressed();
			controller.jumpReleased();
			return true;
		}else{                                  
			return false;
		}
	}

}

@Override
public boolean pan(float x, float y, float deltaX, float deltaY) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean panStop(float x, float y, int pointer, int button) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean zoom(float initialDistance, float distance) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
		Vector2 pointer1, Vector2 pointer2) {
	// TODO Auto-generated method stub
	return false;
}


}

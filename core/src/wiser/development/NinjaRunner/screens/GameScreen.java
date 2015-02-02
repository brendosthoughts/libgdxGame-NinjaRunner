package wiser.development.NinjaRunner.screens;
import wiser.development.NinjaRunner.controller.BobController;
import wiser.development.NinjaRunner.controller.ObjectController;
import wiser.development.NinjaRunner.controller.SkeletonController;
import wiser.development.NinjaRunner.model.World;
import wiser.development.NinjaRunner.model.Bob.BobState;
import wiser.development.NinjaRunner.utils.Assets;
import wiser.development.NinjaRunner.view.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, GestureListener, InputProcessor{

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
	Rectangle nextLevelBounds;
	Rectangle retryBounds;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle ninjaInfo;
	Vector3 touchPoint;
	String numThrowingStars;
	GestureDetector gestureDetector ;
	String message;
	int directionPointer= -1;
	int actionPointer=-1;
	int levelNumber;
	float dif_x, dif_y;
	long time_on_screen;
	boolean beenRevived;
	float down_x, down_y , initpos_y, initpos_x, initpan_x, initpan_y, delta_x, delta_y;
	float[] rgb =new float[4];

	public GameScreen(Game game, int levelNum){
		touchPoint = new Vector3();
		this.game =game;
		gameState=GameState.READY;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(0,0, 0);
		this.cam.update();
		this.levelNumber=levelNum;
		batcher = new SpriteBatch();
		 nextLevelBounds= new Rectangle(this.cam.position.x + CAMERA_WIDTH/8  , this.cam.position.y- 3*CAMERA_HEIGHT/8, CAMERA_WIDTH/4 ,CAMERA_HEIGHT/5);
		retryBounds= new Rectangle(this.cam.position.x - 3*CAMERA_WIDTH/8  , this.cam.position.y- 3*CAMERA_HEIGHT/8, CAMERA_WIDTH/4 ,CAMERA_HEIGHT/5);

		pauseBounds = new Rectangle( this.cam.position.x +  CAMERA_WIDTH/2 -3f,this.cam.position.y+CAMERA_HEIGHT/2  -3f, 1f, 1f);
		resumeBounds = new Rectangle( this.cam.position.x - CAMERA_WIDTH/2, this.cam.position.y , CAMERA_WIDTH, CAMERA_HEIGHT/2);
		quitBounds = new Rectangle( this.cam.position.x -  CAMERA_WIDTH/2 ,this.cam.position.y -CAMERA_HEIGHT/2 ,CAMERA_WIDTH, CAMERA_HEIGHT/2);
		ninjaInfo = new Rectangle(  cam.position.x - CAMERA_WIDTH/2 +1f, cam.position.y +CAMERA_HEIGHT/2 -1f, 1f,1f);
		
		world = new World(levelNum);
		renderer = new WorldRenderer(world, false, this, batcher);
        controller = new BobController(world, this);
        skeletonController = new SkeletonController(world, this);
        objectController = new ObjectController(world, this);
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(20, 0.1f, 1.1f, 0.1f, this);
       beenRevived=false;
        im.addProcessor(gd);
        im.addProcessor(this);
        
        Gdx.input.setInputProcessor(im);
     }
	// this method is called when add watched and bob needs to be place somewhere specifically in the level
	public GameScreen(Game game, int levelNum, Vector2 bobPosition){
		touchPoint = new Vector3();
		this.game =game;
		gameState=GameState.READY;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(0,0, 0);
		this.cam.update();
		this.levelNumber=levelNum;
		batcher = new SpriteBatch();
		 nextLevelBounds= new Rectangle(this.cam.position.x + CAMERA_WIDTH/8  , this.cam.position.y- 3*CAMERA_HEIGHT/8, CAMERA_WIDTH/4 ,CAMERA_HEIGHT/5);
		retryBounds= new Rectangle(this.cam.position.x - 3*CAMERA_WIDTH/8  , this.cam.position.y- 3*CAMERA_HEIGHT/8, CAMERA_WIDTH/4 ,CAMERA_HEIGHT/5);

		pauseBounds = new Rectangle( this.cam.position.x +  CAMERA_WIDTH/2 -1f,this.cam.position.y+CAMERA_HEIGHT/2  -1f, 2f, 2f);
		resumeBounds = new Rectangle( this.cam.position.x - CAMERA_WIDTH/2, this.cam.position.y , CAMERA_WIDTH, CAMERA_HEIGHT/2);
		quitBounds = new Rectangle( this.cam.position.x -  CAMERA_WIDTH/2 ,this.cam.position.y -CAMERA_HEIGHT/2 ,CAMERA_WIDTH, CAMERA_HEIGHT/2);
		ninjaInfo = new Rectangle(  cam.position.x - CAMERA_WIDTH/2 +1f, cam.position.y +CAMERA_HEIGHT/2 -1f, 1f,1f);
		
		world = new World(levelNum);
		renderer = new WorldRenderer(world, false, this, batcher);
        controller = new BobController(world, this);
        skeletonController = new SkeletonController(world, this);
        objectController = new ObjectController(world, this);
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(20, 0.1f, 1.1f, 0.1f, this);
       
        
        im.addProcessor(gd);
        im.addProcessor(this);
        
        Gdx.input.setInputProcessor(im);
        controller.placeBob(bobPosition);
        beenRevived=true;
        
     }
	

	

 
	public void setGameState(GameState state){
		gameState=state;
		
	}
	public void update (float deltaTime) {
		
		switch (gameState) {
		case READY:
			updateReady(deltaTime);
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
		default: updateReady(deltaTime);
		}
		this.cam.update();
	}
	private void updateReady(float delta){
		//reset ie release all controls in order to not continue anythingleft over from bob's previos life
		controller.climbDownReleased();
		controller.jumpReleased();
		controller.climbReleased();
		controller.punchReleased();
		controller.throwReleased();
		controller.leftReleased();
		controller.rightReleased();
		controller.getBob().setVelocity(new Vector2(0,0));
		rgb=renderer.getLevelColor();
		gameState= GameState.RUNNING;
		
	}
	private void updatePaused() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		resumeBounds.set( cam.position.x +CAMERA_WIDTH/8, cam.position.y -CAMERA_HEIGHT/4,CAMERA_WIDTH/4, CAMERA_HEIGHT/4);
		quitBounds.set( cam.position.x - 3*CAMERA_WIDTH/8, cam.position.y -CAMERA_HEIGHT/4,CAMERA_WIDTH/4, CAMERA_HEIGHT/4);

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
	//	Gdx.gl.glClearColor(red, green, blue, alpha);
		// this wil be replaced with rgb
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
		pauseBounds.set(cam.position.x + CAMERA_WIDTH/2 -1f  ,cam.position.y +CAMERA_HEIGHT/2 -1f , 2f, 2f);
		
		objectController.update(delta);
		skeletonController.update(delta);
		controller.update(delta); 
		renderer.render();

	}
	private void updateLevelEnd(){
		if (Gdx.input.justTouched()) {
			game.setScreen(new LevelCompleteScreen(game, levelNumber));
		}	
	}
	private void updateGameOver(){
	//	if ((time_on_screen + 40  <=  System.currentTimeMillis()) ||Gdx.input.justTouched()) {
			// if bob has not been revived user may revive hime else user can replay level or go to ain menu 
			//at the game over screen 
			if(beenRevived==false){
			game.setScreen(new GameOverScreen(game, levelNumber,world.findSafePlace(levelNumber)));
			}else{
				game.setScreen(new GameOverScreen(game, levelNumber,null));
			}
		//}		
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
//		batcher.enableBlending();
		
		batcher.draw(Assets.levelComplete,  this.cam.position.x - 3*CAMERA_WIDTH/8  , this.cam.position.y, 3*CAMERA_WIDTH/4 ,2*CAMERA_HEIGHT/5);
		
//		batcher.disableBlending();
	}

	private void presentReady() {
		// TODO Auto-generated method stub
		
	}

	private void presentRunning() {
		batcher.draw(Assets.pause,  cam.position.x + CAMERA_WIDTH/2 -1f, cam.position.y +CAMERA_HEIGHT/2 -1f, 1f,1f);
		for(int i=0; i<=controller.getBob().getThrowingStars()-1; ++i){
			batcher.draw(Assets.ninjaStars,  this.cam.position.x - CAMERA_WIDTH/2 +(0.5f*i) , this.cam.position.y +CAMERA_HEIGHT/2 -0.5f, 0.5f,0.5f);
		}

	}
	private void presentPaused() {
		/// i want to put in some sort of pause menu text perhaps an add 
		
		batcher.draw(Assets.resume, cam.position.x +CAMERA_WIDTH/8, cam.position.y -CAMERA_HEIGHT/4,CAMERA_WIDTH/4, CAMERA_HEIGHT/4);
		batcher.draw(Assets.menu, cam.position.x - 3*CAMERA_WIDTH/8, cam.position.y -CAMERA_HEIGHT/4,CAMERA_WIDTH/4, CAMERA_HEIGHT/4);

		
	}

	private void presentGameOver() {
	//	time_on_screen= System.currentTimeMillis();
		controller.getBob().setState(BobState.DEAD);

	/*	batcher.enableBlending();
		///replace this with Ninja has Reached his desmisetem.
		batcher.draw(Assets.gameOver,this.cam.position.x - 3*CAMERA_WIDTH/8, cam.position.y , CAMERA_WIDTH*3 /4 , 2*CAMERA_HEIGHT/5 );
		batcher.disableBlending();	*/	
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
		//renderer.setSize(width, height);
		this.width=width;
		this.height=height;
	}
 
@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

@Override
	public void pause() {
		if (gameState == gameState.RUNNING) gameState = gameState.PAUSED;

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
	if (keycode==Keys.UP)
		controller.climbPressed();
	else if (keycode == Keys.DOWN)
		controller.downPressed();
	else if (keycode == Keys.LEFT)
		controller.leftPressed();
	else if (keycode == Keys.RIGHT)
		controller.rightPressed();
	else if (keycode == Keys.Z){
		controller.bobJump();
	message="Jump was pressed";
	Gdx.app.log("INFO", message);
	}
	else if (keycode == Keys.C)
		controller.throwPressed();
	else if (keycode == Keys.X)
		controller.punchPressed();
	return true;
}

@Override
public boolean keyUp(int keycode) {
	if(keycode==Keys.UP){
		controller.climbReleased();
	}else if (keycode == Keys.DOWN)
		controller.climbDownReleased();
	else if (keycode == Keys.LEFT)
		controller.leftReleased();
	else if (keycode == Keys.RIGHT)
		controller.rightReleased();
	else if (keycode == Keys.Z)
		controller.jumpReleased();
	else if (keycode == Keys.X)
		controller.punchReleased();
	else if (keycode == Keys.C)
		controller.throwReleased();
	else if (keycode == Keys.D)
		renderer.setDebug(!renderer.isDebug());
	return true;
}

@Override
public boolean keyTyped(char character) {
	// TODO Auto-generated method stub
	message= "keyTyped was detected";
	Gdx.app.log("INFO", message);
	return false;
	
}


/// all below methods are for touch based game play
@Override
public boolean fling(float velocityX, float velocityY, int button) {

	return false;
}

@Override
public boolean touchDown(int x, int y, int pointer, int button) {
	if (pauseBounds.contains(x, y)) {
		Assets.playSound(Assets.clickSound);
		gameState = GameState.PAUSED;
		message= "pause was clicked  the game should be in PAUSE state";
		Gdx.app.log("INFO", message);
		return true;
	}

	if (x < width / 4 && y > height / 2) {
		directionPointer= pointer;
		if(x< 2*width/16f && (y > height / 4) ||(y < height / 3)){		
			controller.leftPressed();
			message=" driection TOUCH DOWN POINTER IS: " + pointer  + "the action pointer -> " +actionPointer +"directionPointer->"+ directionPointer;
			Gdx.app.log("INFO", message);	

		}else if (x> (3*width/16f ) && (y > height /4 ) ||(y < height / 3)){
			controller.rightPressed();	
			message=" direction TOUCH DOWN POINTER IS: " + pointer  + "the action pointer -> " +actionPointer +"directionPointer->"+ directionPointer;
			Gdx.app.log("INFO", message);	
			
		}else if(y < 3*height/4 ){
			controller.climbPressed();	
			message=" direction TOUCH DOWN POINTER IS: " + pointer  + "the action pointer -> " +actionPointer +"directionPointer->"+ directionPointer;
			Gdx.app.log("INFO", message);
		}else if(y > 3* height/4){
		
			controller.climbDownPressed();	
			message=" direction TOUCH DOWN POINTER IS: " + pointer  + "the action pointer -> " +actionPointer +"directionPointer->"+ directionPointer;
			Gdx.app.log("INFO", message);
		//	return true;
			
		}	
		message="x value for direction pointer is -> "+x+ " y value is -> " +y;
		Gdx.app.log("INFO", message);
		message="climb down value y is less than" + height/4 
				+ "   the right space inputs are x > " +  (5*width/32f ) + "y > " + 3*height /12  +
				"the left space inputs are x < " +  (5*width/32f ) +"and y > " +  3*height /12  ;
				;
		Gdx.app.log("INFO", message);
	}else{
		actionPointer= pointer;
		down_x=x;
		down_y=y;
		message= "Pointer value is" + actionPointer;
	}

	message="TOUCH DOWN POINTER IS: " + pointer  + "the action pointer -> " +actionPointer +"directionPointer->"+ directionPointer;
	Gdx.app.log("INFO", message);	
	return false;
	
}

@Override
public boolean touchUp(int x, int y, int pointer, int button) {
	if (pauseBounds.contains(x, y)) {
		Assets.playSound(Assets.clickSound);
		gameState = GameState.PAUSED;
		message= "pause was clicked  the game should be in PAUSE state";
		Gdx.app.log("INFO", message);
		return true;
	}

	if ( pointer == directionPointer) {			
		controller.leftReleased();
		controller.rightReleased();	
		controller.climbDownReleased();
		controller.climbReleased();
		directionPointer=-1;
		//return false;				
	}else if (pointer == actionPointer){
	//	swipe direction
	// or if not over max , a punch 
	
	
		dif_x =Math.abs(down_x -x);
		dif_y= Math.abs(down_y -y);

		if(dif_y> dif_x && down_y -y >0){ // this is swipe up 
			controller.bobJump();
			return false;		
		}else if (dif_y> dif_x && down_y - y<0){ // swipe down 
			controller.bobPunch();
			return false;
			/// maybe make swipe down some sort of special eg. sword , shield , star power
		}else if (dif_y < dif_x && down_x-x < 0){ //  swipe right 
			controller.throwPressed();
			return false;
		}else if (dif_y < dif_x && down_x-x > 0){ // swipe left
			controller.throwPressed();
			return false;
		}
		
		
	}
	else if (x < width / 4 && y > height / 2) {
	
		controller.leftReleased();
		controller.rightReleased();	
		directionPointer=-1;
		message= "TOUCHUP pointer is ->"+ pointer + "actionPointer->"+ actionPointer + "directionPointer-> "+ directionPointer;
		Gdx.app.log("INFO", message);
			//return true;				
	}
	
	message= "touchUP was detected";
	Gdx.app.log("INFO", message);
	return false;
	
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


@Override
public boolean touchDown(float x, float y, int pointer, int button) {
return false;
}

@Override
public boolean tap(float x, float y, int count, int button) {
	if (pauseBounds.contains(x, y)) {
		Assets.playSound(Assets.clickSound);
		gameState = GameState.PAUSED;
		return true;
	}
	
	return false;
}

@Override
public boolean longPress(float x, float y) {

	return false;
}


@Override
public boolean pan(float x, float y, float deltaX, float deltaY) {

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

public void makeVibrate(){
	Gdx.input.vibrate(100);
}
@Override
public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
		Vector2 pointer1, Vector2 pointer2) {
	// TODO Auto-generated method stub
	return false;
}


}

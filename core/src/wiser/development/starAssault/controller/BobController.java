package wiser.development.starAssault.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import wiser.development.starAssault.model.Block;
import wiser.development.starAssault.model.Bob;
import wiser.development.starAssault.model.Bob.BobState;
import wiser.development.starAssault.model.Fire;
import wiser.development.starAssault.model.FireBall;
import wiser.development.starAssault.model.NinjaStars;
import wiser.development.starAssault.model.Skeleton;
import wiser.development.starAssault.model.Skeleton.SkeletonState;
import wiser.development.starAssault.model.Spring;
import wiser.development.starAssault.model.World;
import wiser.development.starAssault.screens.GameScreen;
import wiser.development.starAssault.screens.GameScreen.GameState;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class BobController {

	enum Keys {
		LEFT, RIGHT, JUMP, PUNCH, THROWSTAR
	}
	private static final long LONG_JUMP_PRESS 	= 150l;
	private static final long MIN_THROW_TIME    = 500l;
	private static final float ACCELERATION 	= 20f;
	private static final float GRAVITY 			= -20f;
	private static final float MAX_JUMP_SPEED	= 7f;
	private static final float DAMP 			= 0.90f;
	private static final float MAX_VEL 			= 4f;
	private World 	world;
	private Bob 	bob;
	private GameScreen gameScreen;
	private long 	throwTime;
	private long	jumpPressedTime;
	private boolean jumpingPressed;
	private boolean punchingPressed;
	private Array<Block> collidableBlocks = new Array<Block>();
	private Array<Fire> collidableFires = new Array<Fire>(); 
	private Array<FireBall> collidableFireBalls = new Array<FireBall>(); 
	private Array<Spring> collidableSprings = new Array<Spring>(); 
	private ArrayList<Skeleton> collidableSkeletons = new ArrayList<Skeleton>();
	private Array<NinjaStars> collidableStars = new Array<NinjaStars>();
	
	public Boolean grounded =false;

	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	static Map<Keys, Boolean> keys = new HashMap<BobController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.PUNCH, false);
		keys.put(Keys.THROWSTAR, false);
	};

	public BobController(World world, GameScreen playScreen) {
		this.world = world;
		this.bob = world.getBob();
		this.gameScreen = playScreen;
		//initialize throw time 
		throwTime = System.currentTimeMillis();
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void punchPressed() {
		keys.get(keys.put(Keys.PUNCH, true));
		punchingPressed=true;
	}
	public void throwPressed(){
		keys.get(keys.put(Keys.THROWSTAR, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		jumpingPressed = false;
	}

	public void punchReleased() {
		keys.get(keys.put(Keys.PUNCH, false));
	//	bob.setState(State.IDLE);
		punchingPressed=false;
	}
	public void throwReleased(){
		keys.get(keys.put(Keys.THROWSTAR, false));
		
	}
	/** The main update method **/
	public void update(float delta) {
		// Processing the input - setting the states of Bob
		processInput();
		
		// If Bob is grounded then reset the state to IDLE 
		if (grounded && bob.getState().equals(BobState.JUMPING)) {
			bob.setState(BobState.IDLE);
		}

		// Setting initial vertical acceleration 
		bob.getAcceleration().y = GRAVITY;
		
		// Convert acceleration to frame time
		bob.getAcceleration().scl(delta); //this should be mul
		
		
		checkCollisionWithObjects(delta);
		
		// apply acceleration to change velocity
		bob.getVelocity().add(bob.getAcceleration().x, bob.getAcceleration().y);

		// checking collisions with the surrounding blocks depending on Bob's velocity



		// apply damping to halt Bob nicely 
		bob.getVelocity().x *= DAMP;
		
		// ensure terminal velocity is not exceeded
		if (bob.getVelocity().x > MAX_VEL) {
			bob.getVelocity().x = MAX_VEL;
		}
		if (bob.getVelocity().x < -MAX_VEL) {
			bob.getVelocity().x = -MAX_VEL;
		}
		// check to see if bob has passed the finish line of the level
		if((bob.getPosition().x >=world.getLevel().getFinish().x) &&(bob.getPosition().y >= world.getLevel().getFinish().y)){
			gameScreen.setGameState(GameState.LEVEL_END);
		}
		// simply updates the state time
		bob.update(delta);
		

	}

	/** Collision checking **/
	private void checkCollisionWithObjects(float delta) {
		// scale velocity to frame units 
		bob.getVelocity().scl(delta);// this should be mul (TODO fix)

		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle bobRect = rectPool.obtain();
		// set the rectangle to bob's bounding box
		bobRect.set(bob.getBounds().x, bob.getBounds().y, bob.getBounds().width, bob.getBounds().height);

		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) bob.getBounds().y;
		int endY = (int) (bob.getBounds().y + bob.getBounds().height);
		// if Bob is heading left then we check if he collides with the block on his left
		// we check the block on his right otherwise
		if (bob.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(bob.getBounds().x + bob.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(bob.getBounds().x + bob.getBounds().width + bob.getVelocity().x);
		}

		// get the block(s) bob can collide with
		populateColidableObjects(startX, startY, endX, endY);
	

		// simulate bob's movement on the X
		bobRect.x += bob.getVelocity().x;
		


		// clear collision boxes in world
		world.getCollisionRects().clear();

		// if bob collides, make his horizontal velocity 0
		for (Block block : collidableBlocks) {
			if (block == null) continue;
			if (bobRect.overlaps(block.getBounds())) {
				bob.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		
		for(NinjaStars ninja_star: collidableStars ){
			if(ninja_star != null){
				if(bobRect.overlaps(ninja_star.getBounds())){
					ninja_star.setPosition(new Vector2(1,1));
					bob.setThrowingStars(bob.getThrowingStars()+5);			
				}
			}
		}

		for (Fire fire: collidableFires){
			if (fire == null) continue;
			if (bob.getBounds().overlaps(fire.getBounds())) {
				gameScreen.setGameState(GameState.GAME_OVER);
				bob.setState(BobState.DEAD);
				bob.getVelocity().y = 0;
			}
		}
		for (FireBall fireBall: collidableFireBalls){
			if (fireBall == null) continue;
			if (bob.getBounds().overlaps(fireBall.getBounds())) {
				gameScreen.setGameState(GameState.GAME_OVER);
				bob.setState(BobState.DEAD);
				bob.getVelocity().y = 0;
			}
		}
		// while loop searches for collisions with skeletons (skeleton controller kills bob non vice versa)
		Iterator<Skeleton> xIt =  collidableSkeletons.iterator();
		while(xIt.hasNext())
		{
		    Skeleton skeleton = xIt.next();

		    if (bobRect.overlaps(skeleton.getBounds()) ) {

		    	if(bob.getState().equals(BobState.PUNCHING)){
		    		skeleton.setState(SkeletonState.DEAD);
		    	}else{
		    		gameScreen.setGameState(GameState.GAME_OVER);
		    		bob.setState(BobState.DEAD);
		    		bob.getVelocity().x = 0;	
		    	}			
		    }			
		
			
		}
		
		// check collision of ninjaStar's (thrownstars) bob has thrown
	
		// reset the x position of the collision box
		bobRect.x = bob.getPosition().x;

		// the same thing but on the vertical Y axis
		startX = (int) bob.getBounds().x;
		endX = (int) (bob.getBounds().x + bob.getBounds().width);
		if (bob.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(bob.getBounds().y + bob.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(bob.getBounds().y + bob.getBounds().height + bob.getVelocity().y);
		}
		
		populateColidableObjects(startX, startY, endX, endY);
		bobRect.y += bob.getVelocity().y;

		for (Block block : collidableBlocks) {
			if (block == null) continue;
			if (bobRect.overlaps(block.getBounds())) {
				if (bob.getVelocity().y < 0) {
					grounded = true;
				}
				bob.getVelocity().y = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		for (FireBall fireBall: collidableFireBalls){
			if (fireBall == null) continue;
			if (bob.getBounds().overlaps(fireBall.getBounds())) {
				gameScreen.setGameState(GameState.GAME_OVER);
				bob.setState(BobState.DEAD);
				bob.getVelocity().y = 0;
			}
		}
		for(NinjaStars ninja_star: collidableStars ){
			if(ninja_star != null){
				if(bobRect.overlaps(ninja_star.getBounds())){
					ninja_star.setPosition(new Vector2(1,1));
					bob.setThrowingStars(bob.getThrowingStars()+5);
				}
			}
		}
		for (Fire fire: collidableFires){
			if (fire == null) continue;
			if (bob.getBounds().overlaps(fire.getBounds())) {
				gameScreen.setGameState(GameState.GAME_OVER);
				bob.setState(BobState.DEAD);
				bob.getVelocity().y = 0;
			}
		}
		if(bob.getState().equals(BobState.JUMPING)){
			for (Spring spring: collidableSprings){
				if (spring == null) continue;
				if (bob.getBounds().overlaps(spring.getBounds())) {
					
					bob.getVelocity().y = 0.3f;
				//	grounded= false;
				}
			}
		}
		Iterator<Skeleton> yIt =  collidableSkeletons.iterator();
		while(yIt.hasNext())
		{
		    Skeleton skeleton = yIt.next();
		    if (bob.getBounds().overlaps(skeleton.getBounds()) ) {
		    		gameScreen.setGameState(GameState.GAME_OVER);
		    		bob.setState(BobState.DEAD);
		    		bob.getVelocity().x = 0;			    				
		    }					
		}
	//	if(bob.getState().equals(BobState.JUMPING)){

			//TODO test against springs if bob in air and over spring comppress spring shoot bob , uncompress spring
		//}
		// reset the collision box's position on Y
		bobRect.y = bob.getPosition().y;

		// update Bob's position
		bob.getPosition().add(bob.getVelocity());
		bob.getBounds().x = bob.getPosition().x;
		bob.getBounds().y = bob.getPosition().y;
		

		// un-scale velocity (not in frame time)
		bob.getVelocity().scl(1/delta);// this should be mul (TODO fix)

	}

 /// finds anything bob can collide with other then enemies as they are stored differently;
	public void populateColidableObjects(int startX, int startY, int endX, int endY) {
		collidableBlocks.clear();
		collidableFires.clear();
		collidableStars.clear();
		collidableSprings.clear();
		collidableFireBalls.clear();
		//collidableSkeletons.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidableBlocks.add(world.getLevel().getCollidableBlocks(x, y));
					collidableFires.add(world.getLevel().getCollidableFires(x, y));
					collidableStars.add(world.getLevel().getCollidableStars(x, y));
					collidableSprings.add(world.getLevel().getCollidableSprings(x, y));
					collidableFireBalls.add(world.getLevel().getCollidableFireBalls(x, y));
					
				}
			}
		}
		//collidableSkeletons=world.getLevel().getSkeletons();
		
	}
	
	


	
	

	/** Change Bob's state and parameters based on input controls **/
	private void processInput() {
		if (keys.get(Keys.JUMP)) {
			if (!bob.getState().equals(BobState.JUMPING)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				bob.setState(BobState.JUMPING);
				bob.getVelocity().y = MAX_JUMP_SPEED; 
				grounded = false;

			} else {
				if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
					jumpingPressed = false;
				} else {
					if (jumpingPressed) {
						bob.getVelocity().y = MAX_JUMP_SPEED;
					}
				}
			}
		}
		if (keys.get(Keys.PUNCH)) {
			/// punch is pressed
			if(!bob.getState().equals(BobState.JUMPING)){
			punchingPressed = true;
			bob.setState(BobState.PUNCHING);  
			}
		}
		if (keys.get(Keys.THROWSTAR ) && (bob.getThrowingStars() > 0)){
		// make the throwing star , and give it a velocity and add it to a arraylist of throwing stars, 
		// in collision checking stars will be checked to see if hit something , if so they disapear

				if(bob.canThrow()){
					throwTime= System.currentTimeMillis();
					bob.setState(BobState.THROW);
					bob.setThrowingStars(bob.getThrowingStars() -1);
					
					if(bob.isFacingLeft()){
					world.getLevel().addThrowingStar(bob.getPosition(), new Vector2(-6,0));
					}else{
					world.getLevel().addThrowingStar(bob.getPosition(), new Vector2(6,0));	
					}	
					bob.setCanThrow(false);
				}
				else if ((System.currentTimeMillis() - throwTime >= MIN_THROW_TIME)){
					bob.setCanThrow(true);
				} 
				
		}
			
		
		if (keys.get(Keys.LEFT)) {
			// left is pressed
			bob.setFacingLeft(true);
			if (!bob.getState().equals(BobState.JUMPING )&& !bob.getState().equals(BobState.PUNCHING)) {
				bob.setState(BobState.WALKING);
			}
			bob.getAcceleration().x = -ACCELERATION;
		} else if (keys.get(Keys.RIGHT)) {
			// left is pressed
			bob.setFacingLeft(false);
			if (!bob.getState().equals(BobState.JUMPING)&& !bob.getState().equals(BobState.PUNCHING)) {
				bob.setState(BobState.WALKING);
			}
			bob.getAcceleration().x = ACCELERATION;
			
			
		}else {
			if (!bob.getState().equals(BobState.JUMPING) && !bob.getState().equals(BobState.PUNCHING)) {
				bob.setState(BobState.IDLE);
			}
			bob.getAcceleration().x = 0;
			
		}
	
	}
	public Bob getBob(){
		return bob;
	}
}
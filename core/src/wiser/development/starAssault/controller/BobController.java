package wiser.development.starAssault.controller;

import java.util.HashMap;
import java.util.Map;

import wiser.development.starAssault.model.Block;
import wiser.development.starAssault.model.Bob;
import wiser.development.starAssault.model.Bob.State;
import wiser.development.starAssault.model.Fire;
import wiser.development.starAssault.model.Skeleton;
import wiser.development.starAssault.model.World;
import wiser.development.starAssault.screens.GameScreen;
import wiser.development.starAssault.screens.GameScreen.GameState;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class BobController {

	enum Keys {
		LEFT, RIGHT, JUMP, PUNCH
	}
	private static final long LONG_JUMP_PRESS 	= 150l;
	private static final float ACCELERATION 	= 20f;
	private static final float GRAVITY 			= -20f;
	private static final float MAX_JUMP_SPEED	= 7f;
	private static final float DAMP 			= 0.90f;
	private static final float MAX_VEL 			= 4f;
	private World 	world;
	private Bob 	bob;
	private GameScreen gameScreen;

	private long	jumpPressedTime;
	private boolean jumpingPressed;
	private boolean punchingPressed;
	private Array<Block> collidableBlocks = new Array<Block>();
	private Array<Fire> collidableFires = new Array<Fire>(); 
	private Array<Skeleton> collidableSkeletons = new Array<Skeleton>();
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
	};

	public BobController(World world, GameScreen playScreen) {
		this.world = world;
		this.bob = world.getBob();
		this.gameScreen = playScreen;
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

	/** The main update method **/
	public void update(float delta) {
		// Processing the input - setting the states of Bob
		processInput();
		
		// If Bob is grounded then reset the state to IDLE 
		if (grounded && bob.getState().equals(State.JUMPING)) {
			bob.setState(State.IDLE);
		}
		
		// Setting initial vertical acceleration 
		bob.getAcceleration().y = GRAVITY;
		
		// Convert acceleration to frame time
		bob.getAcceleration().scl(delta); //this should be mul
		
		// apply acceleration to change velocity
		bob.getVelocity().add(bob.getAcceleration().x, bob.getAcceleration().y);

		// checking collisions with the surrounding blocks depending on Bob's velocity
		checkCollisionWithObjects(delta);
		

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
		populateCollidableBlocks(startX, startY, endX, endY);
		populateCollidableEnemies(startX, startY, endX, endY);
		populateCollidableFires(startX, startY, endX, endY);

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

		for (Skeleton skeleton: collidableSkeletons){
			if (skeleton == null) continue;
			if (bobRect.overlaps(skeleton.getBounds()) ) {
				
				if(bob.getState().equals(State.PUNCHING)){
					
				}else{
					gameScreen.setGameState(GameState.GAME_OVER);
					bob.setState(State.DEAD);
					bob.getVelocity().x = 0;	
				}			
			}					
			
		}
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
		populateCollidableBlocks(startX, startY, endX, endY);
		populateCollidableEnemies(startX, startY, endX, endY);
		populateCollidableFires(startX, startY, endX, endY);

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
		for (Fire fire: collidableFires){
			if (fire == null) continue;
			if (bobRect.overlaps(fire.getBounds())) {
				gameScreen.setGameState(GameState.GAME_OVER);
				bob.setState(State.DEAD);
				bob.getVelocity().y = 0;
			}
		}
		
		for (Skeleton skeleton: collidableSkeletons){
			if (skeleton == null) continue;
			if (bobRect.overlaps(skeleton.getBounds()) ) {
				
				if(bob.getState().equals(State.PUNCHING)){
					
				}else{
					gameScreen.setGameState(GameState.GAME_OVER);
					bob.setState(State.DEAD);
					bob.getVelocity().x = 0;	
				}			
			}					
			
		}
		// reset the collision box's position on Y
		bobRect.y = bob.getPosition().y;

		// update Bob's position
		bob.getPosition().add(bob.getVelocity());
		bob.getBounds().x = bob.getPosition().x;
		bob.getBounds().y = bob.getPosition().y;

		// un-scale velocity (not in frame time)
		bob.getVelocity().scl(1/delta);// this should be mul (TODO fix)

	}


	
	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		collidableBlocks.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidableBlocks.add(world.getLevel().getCollidableBlocks(x, y));
				}
			}
		}
	}
	private void populateCollidableFires(int startX, int startY, int endX, int endY) {
		collidableFires.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidableFires.add(world.getLevel().getCollidableFires(x, y));
				}
			}
		}
	}
	
	private void populateCollidableEnemies(int startX, int startY, int endX, int endY) {
		collidableSkeletons.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidableSkeletons.add(world.getLevel().getCollidableSkeletons(x, y));
				}
			}
		}
	}
	
	

	/** Change Bob's state and parameters based on input controls **/
	private boolean processInput() {
		if (keys.get(Keys.JUMP)) {
			if (!bob.getState().equals(State.JUMPING)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				bob.setState(State.JUMPING);
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
			punchingPressed = true;
			bob.setState(State.PUNCHING);
		}
			
		
		if (keys.get(Keys.LEFT)) {
			// left is pressed
			bob.setFacingLeft(true);
			if (!bob.getState().equals(State.JUMPING )&& !bob.getState().equals(State.PUNCHING)) {
				bob.setState(State.WALKING);
			}
			bob.getAcceleration().x = -ACCELERATION;
		} else if (keys.get(Keys.RIGHT)) {
			// left is pressed
			bob.setFacingLeft(false);
			if (!bob.getState().equals(State.JUMPING)&& !bob.getState().equals(State.PUNCHING)) {
				bob.setState(State.WALKING);
			}
			bob.getAcceleration().x = ACCELERATION;
		} else {
			if (!bob.getState().equals(State.JUMPING) && !bob.getState().equals(State.PUNCHING)) {
				bob.setState(State.IDLE);
			}
			bob.getAcceleration().x = 0;
			
		}
		return false;
	}
}
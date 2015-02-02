package wiser.development.NinjaRunner.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import wiser.development.NinjaRunner.model.Block;
import wiser.development.NinjaRunner.model.Block.BlockType;
import wiser.development.NinjaRunner.model.Bob;
import wiser.development.NinjaRunner.model.Bob.BobState;
import wiser.development.NinjaRunner.model.Climable;
import wiser.development.NinjaRunner.model.Fire;
import wiser.development.NinjaRunner.model.NinjaStars;
import wiser.development.NinjaRunner.model.Platform;
import wiser.development.NinjaRunner.model.Platform.PlatformState;
import wiser.development.NinjaRunner.model.SpeedPad;
import wiser.development.NinjaRunner.model.SpeedPad.SpeedPadType;
import wiser.development.NinjaRunner.model.Spring;
import wiser.development.NinjaRunner.model.World;
import wiser.development.NinjaRunner.screens.GameScreen;
import wiser.development.NinjaRunner.screens.GameScreen.GameState;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class BobController {

	enum Keys {
		LEFT, RIGHT, JUMP, PUNCH, THROWSTAR, UP,DOWN
	}
	private static final long MIN_THROW_TIME    = 500l;
	private static final float ACCELERATION 	= 20f;
	private static final float GRAVITY 			= -20f;
	private static final float MAX_JUMP_SPEED	= 9f;
	private static final float DAMP 			= 0.9f;//0.90f;
	private static final float MAX_VEL 			=  12f;//4.9f;
	private static final float CLIMBING_SPEED =0.08f;
	private World 	world;
	private Bob 	bob;
	private GameScreen gameScreen;
	private long 	throwTime;
	private long	jumpPressedTime;
	private boolean jumpingPressed;
	private boolean punchingPressed; 
	private boolean doubleJump=false;
	private boolean onLadder_x=false;
	private boolean onLadder_y=false;
	private Array<Block> collidableBlocks = new Array<Block>();
	private Array<Fire> collidableFires = new Array<Fire>(); 
	private Array<Spring> collidableSprings = new Array<Spring>(); 
	private Array<SpeedPad> collidableSpeedPads = new Array<SpeedPad>();
	private Array<Climable> collidableClimbs = new Array<Climable>();
	private Array<NinjaStars> collidableStars = new Array<NinjaStars>();
	private ArrayList <Platform> platforms=new ArrayList<Platform>();
	private Platform bobOnThisPlatform ;

	int count_cycle=0;
	private boolean climbUp=false;
	public Boolean grounded =false;
	private boolean on_platform = false;

	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	static Map<Keys, Boolean> keys = new HashMap<BobController.Keys, Boolean>();
	private float relPos;
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.PUNCH, false);
		keys.put(Keys.THROWSTAR, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	};

	public BobController(World world, GameScreen playScreen) {
		this.world = world;
		this.bob = world.getBob();
		this.gameScreen = playScreen;
		//initialize throw time 
		throwTime = System.currentTimeMillis();
		on_platform=false;
		
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void jumpPressed() {
		/*keys.get(keys.put(Keys.JUMP, true));*/
		bobJump();
	}

	public void punchPressed() {
		//	keys.get(keys.put(Keys.PUNCH, true));
		bobPunch();
		punchingPressed=true;
	}
	public void throwPressed(){
		bobThrowStar();
		//keys.get(keys.put(Keys.THROWSTAR, true));
	}
	public void climbPressed(){
		keys.get(keys.put(Keys.UP, true));
	}
	public void climbDownPressed(){
		keys.get(keys.put(Keys.DOWN, true));
	}
	public void climbDownReleased(){
		keys.get(keys.put(Keys.DOWN, false));
		bobStopClimb();
	}
	public void climbReleased(){
		keys.get(keys.put(Keys.UP, false));
		bobStopClimb();
	}
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		//jumpingPressed = false;
	}

	public void punchReleased() {
		keys.get(keys.put(Keys.PUNCH, false));
		//	bob.setState(State.IDLE);
		punchingPressed=false;
	}
	public void throwReleased(){
		keys.get(keys.put(Keys.THROWSTAR, false));

	}
	//	public void setIdle(){
	//		bob.setState(BobState.IDLE);
	//	}
	/** The main update method **/
	public void update(float delta) {
		// Processing the input - setting the states of Bob
	/*	if(bob.getVelocity().y!=0){
			grounded=false;
			
		}*/
		//this statement is to ensure that bob does not go below bounds of level and therefore causing a run time error 
		//belief for bug is bob updating between delta's and missing the spikes thereofr falling forever
		if(bob.getPosition().y < 1f){
			bob.setState(BobState.DEAD);
			gameScreen.setGameState(GameState.GAME_OVER);
		}

		processInput();
		// If Bob is grounded then reset the stat= to IDLE 
		if (grounded && (bob.getState().equals(BobState.JUMPING) || bob.getState().equals(BobState.DOUBLEJUMP))){		
			bob.setState(BobState.IDLE);
		}
		if(!onLadder_x && !onLadder_y && ( bob.getState().equals(BobState.IDLE_CLIMBING)||bob.getState().equals(BobState.CLIMBING))){
			bob.setState(BobState.IDLE);
		}
		if(!on_platform){
			bob.getAcceleration().y = GRAVITY;
			
		}
		bob.getAcceleration().scl(delta);
		// apply acceleration to change velocity
		bob.getVelocity().add(bob.getAcceleration().x, bob.getAcceleration().y);

		// checking collisions with the surrounding blocks depending on Bob's velocity


		bob.getAcceleration().scl(1/delta);
		//  apply damping to halt Bob nicely
		checkCollisionWithObjects(delta);
		bob.getVelocity().x *= DAMP;
		if(bobOnThisPlatform!=null){
			bob.getPosition();
		}
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
		bob.getVelocity().scl(delta);

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

		// get the things bob can collide with
		populateColidableObjects(startX, startY, endX, endY);


		// simulate bob's movement on the X
		bobRect.x += bob.getVelocity().x;


		platforms = world.getLevel().getPlatforms();
		Iterator<Platform> platformIt = platforms.iterator();
		while(platformIt.hasNext()){
			Platform platform = platformIt.next();
			 if (on_platform && grounded &&  platform==bobOnThisPlatform){
				 count_cycle++;
				
				bobOnThisPlatform=platform;
				grounded=true;
				on_platform=true;
				if( bob.getState().equals(BobState.WALKING) && grounded  && on_platform ){

					//BobState state = bob.getState();
					on_platform=true;
					//bob.getVelocity().x=platform.getVelocity().x;
					bob.getAcceleration().y=0;
					bob.getVelocity().y=0;
					bob.getPosition().y=platform.getPosition().y+ platform.getBounds().height;
					
					doubleJump= false;
					
					if(bob.isFacingLeft()){	
						bob.getAcceleration().x=0;
						bob.setVelocity(new Vector2(platform.getVelocity().x*delta - (2.3f*delta), platform.getVelocity().y));
					}else{
						bobOnThisPlatform=platform;
						bob.setVelocity(new Vector2(platform.getVelocity().x*delta + (2.3f *delta) ,platform.getVelocity().y));
					}
				}
				else if(on_platform && bob.getState().equals(BobState.JUMPING)){
					jumpingPressed=true;
					grounded=false;
					on_platform =false;
					bob.getAcceleration().y=ACCELERATION/delta;
					bobJump();
					bobOnThisPlatform=null;
				}//	if (bob.getState().equals(BobState.IDLE) || bob.getState().equals(BobState.PUNCHING)){
				else{	 
					relPos = platform.getVelocity().x*delta;					
					//bob.getPosition().x=(platform.getVelocity().x*delta) ;
					bob.getVelocity().x=platform.getVelocity().x*delta ;
					bob.getPosition();
				}			
			}
		
		
			 else if(platform.getBounds().overlaps(bobRect)){
				if(bob.getVelocity().y>=0 &&(bob.getState().equals(BobState.JUMPING) || bob.getState().equals(BobState.DOUBLEJUMP))
						&& bob.getPosition().y <= platform.getBounds().y+ 7*platform.getBounds().height/8){
					bob.setPosition(new Vector2(bob.getPosition().x, bob.getPosition().y-0.1f));				// bob hiut the bottom of the platform
					bob.getVelocity().y=0;

				}else if((bob.getPosition().x <= platform.getBounds().x
						&&(bob.getPosition().y <=platform.getBounds().y+ platform.getBounds().height))){
					// bob coliided right
					bob.getVelocity().x=0;
					bob.getVelocity().y=0;
					if(grounded){
						bob.getVelocity().x=platform.getVelocity().x-0.05f;
					//	bob.setPosition(new Vector2(bob.getPosition().x -0.1f, bob.getPosition().y));
					}else{
						bob.setPosition(new Vector2(bob.getPosition().x -0.1f, bob.getPosition().y-0.3f));	
					}
					
				}else if( bob.getPosition().x >= platform.getBounds().x + platform.getBounds().width
						&&(bob.getPosition().y <=platform.getBounds().y + platform.getBounds().height)){
					// bob collided left

					bob.getVelocity().x=0;
					bob.getVelocity().y=0;					
					if(grounded){
						bob.setPosition(new Vector2(bob.getPosition().x +0.05f, bob.getPosition().y));
	
					}else{
						bob.setPosition(new Vector2(bob.getPosition().x +0.1f, bob.getPosition().y-0.1f));
						
					}
					
				}else if( bob.getVelocity().y<=0){
					grounded=true;	
					on_platform=true;
					bob.getAcceleration().y=0;
					bob.getVelocity().y=0;
					bobOnThisPlatform=platform;

					bob.getVelocity().scl(1/delta);
					//bob.getVelocity().x= platform.getVelocity().x;
					bob.getVelocity().scl(delta);	
					bob.getPosition().y=platform.getPosition().y+ platform.getBounds().height;
					bob.setState(BobState.IDLE);
					jumpingPressed=false;
					doubleJump= false;
				}
			}
			if(on_platform && bobOnThisPlatform==platform && bobOnThisPlatform!=null
					&&  (bob.getPosition().y <= platform.getBounds().y+ 7*platform.getBounds().height/8
					|| bob.getPosition().x <= platform.getBounds().x || bob.getPosition().x >= platform.getBounds().x + platform.getBounds().width)
					){
				on_platform=false;	
				grounded=false;
				bobOnThisPlatform =null;
			}
		}
		if(count_cycle>10){
			bob.getPosition();
			
		}
		

		// if bob collides, make his horizontal velocity 0

		for (Block block : collidableBlocks) {
			if (block == null) continue;
			if (bobRect.overlaps(block.getBounds())) {		
				bob.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				if(block.getType().equals(BlockType.SPIKE) || block.getType().equals(BlockType.SPIKE_TOP) ){
					bob.setState(BobState.DEAD);
					gameScreen.setGameState(GameState.GAME_OVER);
				}
			}
		}
		for (Climable climable: collidableClimbs){
			if(climable!=null){
				if(bobRect.overlaps(climable.getBounds())){
					onLadder_x=true;
					if(bob.getState().equals(BobState.CLIMBING) && climbUp){
						bob.getVelocity().y=CLIMBING_SPEED;
					}
					else if(bob.getState().equals(BobState.CLIMBING) && !climbUp){
						bob.getVelocity().y = -CLIMBING_SPEED;
					}
					else if(bob.getLastState().equals(BobState.CLIMBING) || bob.getState().equals(BobState.CLIMBING) || bob.getState().equals(BobState.IDLE_CLIMBING) ){
						bob.getVelocity().y=0;

					}
					break;
				}
			}else{
				onLadder_x=false;
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

		for (SpeedPad speedPad: collidableSpeedPads){

			if (speedPad == null) continue;
			if (bob.getBounds().overlaps(speedPad.getBounds())) {
				if(speedPad.getType().equals(SpeedPadType.RIGHT)){
					bob.getVelocity().x += 3f;
				}else if (speedPad.getType().equals(SpeedPadType.LEFT)){
					bob.getVelocity().x -= 3f;					
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
		world.getCollisionRects().clear();
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

				if((block.getType().equals(BlockType.SPIKE)) || (block.getType().equals(BlockType.SPIKE_TOP)) ){
					bob.setState(BobState.DEAD);
					gameScreen.setGameState(GameState.GAME_OVER);
					bob.getVelocity().y = 0;
				}if(block.getType().equals(BlockType.METAL)){
					bob.getVelocity().y = 0;
					if(bob.getPosition().y > block.getPosition().y){		
						grounded = true;
						doubleJump=false;		
					}
				}
			}
		}
		for (Climable climable: collidableClimbs){
			if(climable!=null){
				if(bobRect.overlaps(climable.getBounds())){
					onLadder_y=true;
					if(bob.getState().equals(BobState.CLIMBING) && climbUp){
						bob.getVelocity().y=CLIMBING_SPEED;
					}
					else if(bob.getState().equals(BobState.CLIMBING) && !climbUp){
						bob.getVelocity().y = -CLIMBING_SPEED;
					}else if(bob.getLastState().equals(BobState.CLIMBING) || bob.getState().equals(BobState.CLIMBING) || bob.getState().equals(BobState.IDLE_CLIMBING) ){
						bob.getVelocity().y=0;

					}
					break;
				}

			}else{
				onLadder_y=false;
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
		for (SpeedPad speedPad: collidableSpeedPads){

			if (speedPad == null) continue;
			if (bob.getBounds().overlaps(speedPad.getBounds())) {
				if(speedPad.getType().equals(SpeedPadType.RIGHT)){
					bob.getVelocity().x += 3f;
				}else if (speedPad.getType().equals(SpeedPadType.LEFT)){
					bob.getVelocity().x -= 3f;					
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
		if(bob.getState().equals(BobState.JUMPING) || bob.getState().equals(BobState.DOUBLEJUMP) ){
			for (Spring spring: collidableSprings){
				if (spring == null) continue;
				if (bob.getBounds().overlaps(spring.getBounds())) {
					
					bob.getVelocity().y = 0.3f;
					//grounded=true;
					doubleJump=false;
					bob.setState(BobState.JUMPING);
					//bobJump();
					
					
					
				}
			}
		}

		// reset the collision box's position on Y
		bobRect.y = bob.getPosition().y;
			
	
		// update Bob's position
		bob.getPosition().add(bob.getVelocity());
		
		bob.getBounds().x = bob.getPosition().x;
		bob.getBounds().y = bob.getPosition().y;
		if(bobOnThisPlatform!=null){
			bob.getPosition();
			
		}
		


		// un-scale velocity (not in frame time)
		bob.getVelocity().scl(1/delta);

	}

	/// finds anything bob can collide with other then enemies as they are stored differently;
	public void populateColidableObjects(int startX, int startY, int endX, int endY) {
		collidableBlocks.clear();
		collidableFires.clear();
		collidableStars.clear();
		collidableSprings.clear();
		collidableSpeedPads.clear();
		collidableClimbs.clear();

		for (int x = startX; x <= endX; x++) { // NEED to make some type of dynamic grabbing of fireball like skeletons (basicaly all moving objects)
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidableBlocks.add(world.getLevel().getCollidableBlocks(x, y));
					collidableFires.add(world.getLevel().getCollidableFires(x, y));
					collidableStars.add(world.getLevel().getCollidableStars(x, y));
					collidableSprings.add(world.getLevel().getCollidableSprings(x, y));
					collidableSpeedPads.add(world.getLevel().getCollidableSpeedPads(x, y));
					collidableClimbs.add(world.getLevel().getColliableClimbs(x,y));

				}
			}
		}
	}







	/** Change Bob's state and parameters based on input controls **/
	private void processInput() {		

		if (keys.get(Keys.LEFT)) {
			// left is pressed
			bob.setFacingLeft(true);
			//TODO lookup xor in java piece of logic I have been really missing
			if (!bob.getState().equals(BobState.JUMPING ) && !bob.getState().equals(BobState.PUNCHING)
					&& !bob.getState().equals(BobState.IDLE_CLIMBING)&& !bob.getState().equals(BobState.CLIMBING) && !bob.getState().equals(BobState.DOUBLEJUMP) && grounded ){

				bob.setState(BobState.WALKING);
			}
			bob.getAcceleration().x = -ACCELERATION;
		} else if (keys.get(Keys.RIGHT)) {   
			// left is pressed
			bob.setFacingLeft(false);
			if (!bob.getState().equals(BobState.JUMPING)&& !bob.getState().equals(BobState.PUNCHING) 
					&& !bob.getState().equals(BobState.IDLE_CLIMBING)&& !bob.getState().equals(BobState.CLIMBING) && !bob.getState().equals(BobState.DOUBLEJUMP ) && grounded                                                  ) {

				bob.setState(BobState.WALKING);
			}
			bob.getAcceleration().x = ACCELERATION;

		}else if (keys.get(Keys.UP)){
			bobClimb();
		}else if (keys.get(Keys.DOWN)){
			bobClimbDown();
		}
		else {
			if (bob.getState().equals(BobState.CLIMBING)){
				bob.setState(BobState.IDLE_CLIMBING);
				bob.setVelocity(new Vector2(0,0));
			}
			else if (!bob.getState().equals(BobState.JUMPING) && !bob.getState().equals(BobState.PUNCHING) &&
					!bob.getState().equals(BobState.DOUBLEJUMP) && !bob.getState().equals(BobState.CLIMBING)
					&& !bob.getState().equals(BobState.IDLE_CLIMBING)) {
				bob.setState(BobState.IDLE);
			}
			bob.getAcceleration().x = 0;
		}
	}
	public void bobJump(){

	//	platform=null;
		on_platform=false;
		if((grounded ==false) && bob.getState().equals(BobState.JUMPING) && jumpingPressed==true && doubleJump==false && bob.getVelocity().y!=0){
			bob.setState(BobState.DOUBLEJUMP);
			doubleJump=true;
			if(bob.getVelocity().y<0){
				bob.getVelocity().y = MAX_JUMP_SPEED;	
			}else{
				bob.getVelocity().y += MAX_JUMP_SPEED;	
			}


		}else if ((!bob.getState().equals(BobState.JUMPING) && !bob.getState().equals(BobState.DOUBLEJUMP)  && grounded==true)|| bob.getState().equals(BobState.CLIMBING)|| bob.getState().equals(BobState.IDLE_CLIMBING)){
			doubleJump=false;
			on_platform =false;
			jumpingPressed = true;
			bob.setState(BobState.JUMPING);
			bob.getVelocity().y = MAX_JUMP_SPEED; 
			grounded = false;
		} 	
	}
	public void bobClimb(){
		if(onLadder_x || onLadder_y){
			bob.setState(BobState.CLIMBING);
			climbUp=true;
		}

	}
	public void bobStopClimb(){
		if(onLadder_x || onLadder_y){
			bob.setState(BobState.IDLE_CLIMBING);
		}
	}
	public void bobClimbDown(){
		if(onLadder_x || onLadder_y){
			bob.setState(BobState.CLIMBING);
			climbUp=false;
		}

	}	

	public void bobThrowStar(){
		if(bob.canThrow() && bob.getThrowingStars() > 0){
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
	public void bobPunch(){
		punchingPressed = true;
		bob.setState(BobState.PUNCHING);  		
	}
	public Bob getBob(){
		return bob;
	}


	public void placeBob(Vector2 bobPosition) {
		bob.setPosition(bobPosition);
		
	}
	
}
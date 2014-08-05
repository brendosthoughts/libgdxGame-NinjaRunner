package wiser.development.starAssault.controller;

import java.util.ArrayList;
import java.util.Iterator;

import wiser.development.starAssault.model.Block;
import wiser.development.starAssault.model.Bob;
import wiser.development.starAssault.model.Bob.BobState;
import wiser.development.starAssault.model.Fire;
import wiser.development.starAssault.model.NinjaStars;
import wiser.development.starAssault.model.Skeleton;
import wiser.development.starAssault.model.Skeleton.SkeletonState;
import wiser.development.starAssault.model.Skeleton.SkeletonType;
import wiser.development.starAssault.model.World;
import wiser.development.starAssault.screens.GameScreen;
import wiser.development.starAssault.screens.GameScreen.GameState;
import wiser.development.starAssault.utils.Assets;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class SkeletonController {
	
	private static final float ACCELERATION 	= 3f;
	private static final float MAX_VEL 	= 1f;

	private World 	world;
	private ArrayList<Skeleton> skeletons =new ArrayList<Skeleton>();
	private GameScreen gameScreen;
	private Bob bob;
	private Array<Block> collidableBlocks = new Array<Block>();
	
		
	public SkeletonController(World world, GameScreen playScreen) {
		this.world = world;
		this.skeletons = world.getLevel().getSkeletons();
		this.bob= world.getBob();
		this.gameScreen = playScreen;
	}
	
	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	
	public void update(float delta) {
		skeletons = world.getLevel().getSkeletons();
		Iterator<Skeleton> it =  skeletons.iterator();
		while(it.hasNext())
		{
		    Skeleton skeleton = it.next();
			if(skeleton.getState().equals(SkeletonState.DEAD)){
				skeleton.getVelocity().x=0;
				skeleton.getAcceleration().x = 0;
			}else{
		    
				skeleton.getVelocity().scl(delta);
				//skeleton.getAcceleration().y = Assets.GRAVITY;
				moveSkeleton(skeleton, delta);
				// apply acceleration to change velocity
				skeleton.getVelocity().add(skeleton.getAcceleration().x, skeleton.getAcceleration().y);
				skeleton.getVelocity().x *= Assets.DAMP;
				// ensure terminal velocity is not exceeded
				if (skeleton.getVelocity().x > MAX_VEL) {
					skeleton.getVelocity().x = MAX_VEL;
				}
				if (skeleton.getVelocity().x < -MAX_VEL) {
					skeleton.getVelocity().x = -MAX_VEL;
				}				
				// checking collisions with the surrounding blocks depending on skeleton's velocity
				checkCollisionWithObjects(delta, skeleton);
			
				// simply updates the state time
				skeleton.update(delta);
			}

			skeleton.getVelocity().scl(1/delta);
		}
			
		
	}
	private void moveSkeleton(Skeleton skeleton, float delta){
		SkeletonType type= skeleton.getSkeletonType();

		switch (type){
		case BACKFORTH:
		/***the skeleton move back and forth from current position  2block in each direction***/
			if(( skeleton.getPosition().x <= skeleton.getInitialPosition().x +2f)
				&& (skeleton.getPosition().x > skeleton.getInitialPosition().x -2f )
				&& (skeleton.isFacingLeft()))
			{
				skeleton.setFacingLeft(true);
				skeleton.getVelocity().x=0;
				skeleton.getAcceleration().x = -ACCELERATION;
			}else if( skeleton.getPosition().x > skeleton.getInitialPosition().x +2f)
			{
				skeleton.setFacingLeft(true);
				skeleton.getVelocity().x=0;
				skeleton.getAcceleration().x = -ACCELERATION;

			}else if(( skeleton.getPosition().x <= skeleton.getInitialPosition().x +2f)
				&& ( skeleton.getPosition().x > skeleton.getInitialPosition().x -2f )
				&& !(skeleton.isFacingLeft()))
			{			
				skeleton.setFacingLeft(false);
				skeleton.getVelocity().x=0;
				skeleton.getAcceleration().x = ACCELERATION;
			}else if (skeleton.getPosition().x < skeleton.getInitialPosition().x -2f){
				skeleton.setFacingLeft(false);
				skeleton.getVelocity().x=0;
				skeleton.getAcceleration().x = ACCELERATION;					
			}
		
			break;
		case LEFT: 
			skeleton.setFacingLeft(true);
			skeleton.getVelocity().x=0;
			skeleton.getAcceleration().x = -ACCELERATION;
			break;	
		case RIGHT: 
			skeleton.setFacingLeft(false);
			skeleton.getVelocity().x=0;
			skeleton.getAcceleration().x = ACCELERATION;
			break;
		default: 
		}	
		

	}	
	/** Collision checking **/
	private void checkCollisionWithObjects(float delta, Skeleton skeleton) {
		// scale velocity to frame units 
		skeleton.getVelocity().scl(delta);

		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle skeletonRect = rectPool.obtain();
		// set the rectangle to skeleton's bounding box
		skeletonRect.set(skeleton.getBounds().x, skeleton.getBounds().y, skeleton.getBounds().width, skeleton.getBounds().height);

		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) skeleton.getBounds().y;
		int endY = (int) (skeleton.getBounds().y + skeleton.getBounds().height);
		// if skeletonis heading left then we check if he collides with the block on his left
		// we check the block on his right otherwise
		if (skeleton.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(skeleton.getBounds().x + skeleton.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(skeleton.getBounds().x + skeleton.getBounds().width + skeleton.getVelocity().x);
		}

		// get the block(s) skeleton can collide with
		if(skeleton.getBounds().overlaps(bob.getBounds()) && !(skeleton.getState().equals(SkeletonState.DEAD)) ){
			if(bob.getState().equals(BobState.PUNCHING)){
				skeleton.setHealth(skeleton.getHealth() - 1);
				if(skeleton.getHealth()==0){
				skeleton.setState(SkeletonState.DEAD);
				}
			}else{
				gameScreen.setGameState(GameState.GAME_OVER);
				bob.setState(BobState.DEAD);
				bob.getVelocity().x = 0;		
	    	}
		}
		
//		thrownStars=world.getLevel().getThrownStars();
//		Iterator<NinjaStars> starIt =  thrownStars.iterator();
//		int index=0;
//		while(starIt.hasNext()){
//			NinjaStars star= starIt.next();
//			
//			star.getVelocity().scl(delta);
//		//	star.setPosition(star.getPosition().add(star.getVelocity()));
//			
//		
//			    if (star.getBounds().overlaps(skeleton.getBounds()) ) {
//			    		skeleton.setState(SkeletonState.DEAD);	
//			    		skeleton.setVelocity(new Vector2(0,0));
//			    		star.setPosition(new Vector2( 2,2));
//						world.getLevel().destroyThrowingStar(index);
//			    		
//			    }	
//			star.getVelocity().scl(1/delta);
//		}
//			for (Block block : collidableBlocks) {
//				if (block == null) continue;
//				if (star.getBounds().overlaps(block.getBounds())) {
//					world.getLevel().destroyThrowingStar(index);
//				}
//			}

		populateCollidableBlocks(startX, startY, endX, endY);

		// simulate skeleton's movement on the X
		skeletonRect.x += skeleton.getVelocity().x;

		// clear collision boxes in world
		world.getCollisionRects().clear();

		// if skeleton collides, make his horizontal velocity 0
		for (Block block : collidableBlocks) {
			if (block != null){
			if (skeletonRect.overlaps(block.getBounds())) {
				skeleton.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
			}
		}

		// reset the x position of the collision box
		skeletonRect.x = skeleton.getPosition().x;

		// the same thing but on the vertical Y axis
		startX = (int) skeleton.getBounds().x;
		endX = (int) (skeleton.getBounds().x + skeleton.getBounds().width);
		if (skeleton.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(skeleton.getBounds().y + skeleton.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(skeleton.getBounds().y + skeleton.getBounds().height + skeleton.getVelocity().y);
		}

		populateCollidableBlocks(startX, startY, endX, endY);
		skeletonRect.y += skeleton.getVelocity().y;

		for (Block block : collidableBlocks) {
			if (block == null) continue;
			if (skeletonRect.overlaps(block.getBounds())) {
				skeleton.getVelocity().y = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		// reset the collision box's position on Y
		skeletonRect.y = skeleton.getPosition().y;
		
		// update skeleton's position
		skeleton.getPosition().add(skeleton.getVelocity());
		skeleton.getBounds().x = skeleton.getPosition().x;
		skeleton.getBounds().y = skeleton.getPosition().y;
		
		// un-scale velocity (not in frame time)
		skeleton.getVelocity().scl(1/delta);// this should be mul (TODO fix)

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


}

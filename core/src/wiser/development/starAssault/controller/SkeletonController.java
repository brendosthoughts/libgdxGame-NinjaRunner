package wiser.development.starAssault.controller;

import wiser.development.starAssault.model.Block;
import wiser.development.starAssault.model.Bob;
import wiser.development.starAssault.model.Fire;
import wiser.development.starAssault.model.Skeleton;
import wiser.development.starAssault.model.Skeleton.State;
import wiser.development.starAssault.model.World;
import wiser.development.starAssault.screens.GameScreen;
import wiser.development.starAssault.screens.GameScreen.GameState;
import wiser.development.starAssault.utils.Assets;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class SkeletonController {
	
	
	private World 	world;
	private Skeleton[][] skeletons;
	private GameScreen gameScreen;
	private Bob bob;
	
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

		for (Skeleton[] skeleton_a : skeletons){
			for (Skeleton skeleton : skeleton_a){
				
			

				// checking collisions with the surrounding blocks depending on skeleton's velocity
				//checkCollisionWithObjects(delta, skeleton);


				// apply damping to halt skeleton nicely 
				//skeleton.getVelocity().x *= Assets.DAMP;

				// ensure terminal velocity is not exceeded
				/*if (skeleton.getVelocity().x > Assets.MAX_VEL) {
					skeleton.getVelocity().x = Assets.MAX_VEL;
				}
				if (skeleton.getVelocity().x < -Assets.MAX_VEL) {
					skeleton.getVelocity().x = -Assets.MAX_VEL;
				}
*/
				// simply updates the state time
				skeleton.update(delta);
			}
			
		}

	}
		
	/** Collision checking **/
	private void checkCollisionWithObjects(float delta, Skeleton skeleton) {
		// scale velocity to frame units 
		skeleton.getVelocity().scl(delta);// this should be mul (TSDO fix)

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

		// get the block(s) skeletoncan collide with
	//	populateCollidableBlocks(startX, startY, endX, endY);

		// simulate skeleton's movement on the X
		skeletonRect.x += skeleton.getVelocity().x;

		// clear collision boxes in world
		world.getCollisionRects().clear();

		// if skeletoncollides, make his horizontal velocity 0
	/*	for (Block block : collidableBlocks) {
			if (block == null) continue;
			if (skeletonRect.overlaps(block.getBounds())) {
				skeleton.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}*/

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

	//	populateCollidableBlocks(startX, startY, endX, endY);
	//	populateCollidableFires(startX, startY, endX, endY);
		skeletonRect.y += skeleton.getVelocity().y;
/*
		for (Block block : collidableBlocks) {
			if (block == null) continue;
			if (skeletonRect.overlaps(block.getBounds())) {
				if (skeleton.getVelocity().y < 0) {
					grounded = true;
				}
				skeleton.getVelocity().y = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		for (Fire fire: collidableFires){
			if (fire == null) continue;
			if (skeletonRect.overlaps(fire.getBounds())) {
				gameScreen.setGameState(GameState.GAME_OVER);
				skeleton.setState(State.DEAD);
				skeleton.getVelocity().y = 0;
			}
		}*/
		// reset the collision box's position on Y
		skeletonRect.y = skeleton.getPosition().y;

		// update skeleton's position
		skeleton.getPosition().add(skeleton.getVelocity());
		skeleton.getBounds().x = skeleton.getPosition().x;
		skeleton.getBounds().y = skeleton.getPosition().y;

		// un-scale velocity (not in frame time)
		skeleton.getVelocity().scl(1/delta);// this should be mul (TODO fix)

	}


/*	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
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
	}*/



}

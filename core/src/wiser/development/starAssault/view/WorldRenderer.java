package wiser.development.starAssault.view;


import wiser.development.starAssault.model.Block;
import wiser.development.starAssault.model.Bob;
import wiser.development.starAssault.model.Bob.BobState;
import wiser.development.starAssault.model.Fire;
import wiser.development.starAssault.model.NinjaStars;
import wiser.development.starAssault.model.Skeleton;
import wiser.development.starAssault.model.Skeleton.SkeletonState;
import wiser.development.starAssault.model.Spring;
import wiser.development.starAssault.model.Spring.SpringState;
import wiser.development.starAssault.model.World;
import wiser.development.starAssault.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;


public class WorldRenderer {

	private static final float RUNNING_FRAME_DURATION = 0.08f;
	
	private World world;
	
	/**camera controls**/
	OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();


	private TextureRegion bobFrame;

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis

	private TextureRegion skeletonFrame;
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / this.cam.viewportWidth;
		ppuY = (float)height / this.cam.viewportHeight;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public WorldRenderer(World world, boolean debug, OrthographicCamera cam, SpriteBatch batcher) {
		this.spriteBatch=batcher;
		this.world = world;
		this.cam = cam;
		this.cam.update();
	}
	

	
	public void render() {
	
  	
		spriteBatch.begin();
		drawBlocks();
		drawBob();
		drawFire();
		drawSkeletons();
		drawNinjaStars();
		drawSprings();
		spriteBatch.end();

    	this.cam.position.x = (world.getBob().getPosition().x);
    	this.cam.position.y=(world.getBob().getPosition().y);
    	this.cam.update();
    	spriteBatch.setProjectionMatrix(cam.combined);
    	
    
  
	}
	private void drawSprings() {
		for (Spring spring : world.getDrawableSprings((int)this.cam.viewportWidth , (int)this.cam.viewportHeight)) {
			if(spring.getState().equals(SpringState.FULL)){ 
				spriteBatch.draw(Assets.spring, spring.getPosition().x , spring.getPosition().y, Spring.SIZE , Spring.SIZE);
			}else{
				spriteBatch.draw(Assets.springDown, spring.getPosition().x , spring.getPosition().y, Spring.SIZE , Spring.SIZE/2);
			}
		
		}
	}
	private void drawFire() {
		for (Fire fire : world.getDrawableFire((int)this.cam.viewportWidth , (int)this.cam.viewportHeight)) {
			spriteBatch.draw(Assets.fireTexture, fire.getPosition().x , fire.getPosition().y, Fire.SIZE , Fire.SIZE );
		}
	}

	private void drawBlocks() {
		for (Block block : world.getDrawableBlocks((int)this.cam.viewportWidth , (int)this.cam.viewportHeight)) {
			spriteBatch.draw(Assets.blockTexture, block.getPosition().x , block.getPosition().y, Block.SIZE , Block.SIZE );
		}
	}
	private void drawNinjaStars(){
		for (NinjaStars ninjaStar : world.getDrawableNinjaStars((int)this.cam.viewportWidth , (int)this.cam.viewportHeight)) {
			
			spriteBatch.draw(Assets.ninjaStars, ninjaStar.getPosition().x , ninjaStar.getPosition().y, Block.SIZE , Block.SIZE );
		}
		
	}
	
private void drawSkeletons(){
	for (Skeleton skeleton : world.getDrawableSkeletons((int)this.cam.viewportWidth , (int)this.cam.viewportHeight)) {
		if(skeleton.getState().equals(SkeletonState.WALKING)){
			skeletonFrame= skeleton.isFacingLeft() ? Assets.skeletonLeftAnimation.getKeyFrame(skeleton.getStateTime(), true) : Assets.skeletonRightAnimation.getKeyFrame(skeleton.getStateTime(), true);
			spriteBatch.draw(skeletonFrame, skeleton.getPosition().x, skeleton.getPosition().y , skeleton.SIZE , skeleton.SIZE);
		}else if(skeleton.getState().equals(SkeletonState.DEAD)){
			skeletonFrame= skeleton.isFacingLeft() ? Assets.skeletonDeadLeftAnimation.getKeyFrame(skeleton.getStateTime(), false) : Assets.skeletonDeadRightAnimation.getKeyFrame(skeleton.getStateTime(), true);
			spriteBatch.draw(skeletonFrame, skeleton.getPosition().x, skeleton.getPosition().y , skeleton.SIZE , skeleton.SIZE);
		}


	}
	
}

	private void drawBob() {
		Bob bob = world.getBob();
		bobFrame = bob.isFacingLeft() ? Assets.bobIdleLeft : Assets.bobIdleRight;
		
		if(bob.getState().equals(BobState.PUNCHING)){
			bobFrame = bob.isFacingLeft() ? Assets.punchLeftAnimation.getKeyFrame(bob.getStateTime(), true) : Assets.punchRightAnimation.getKeyFrame(bob.getStateTime(), true);
		}
		else if(bob.getState().equals(BobState.WALKING)) {
			bobFrame = bob.isFacingLeft() ? Assets.walkLeftAnimation.getKeyFrame(bob.getStateTime(), true) : Assets.walkRightAnimation.getKeyFrame(bob.getStateTime(), true);
		} 
		else if (bob.getState().equals(BobState.JUMPING)) {
			if (bob.getVelocity().y > 0) {
				bobFrame = bob.isFacingLeft() ? Assets.bobJumpLeft : Assets.bobJumpRight;
			} else {
				bobFrame = bob.isFacingLeft() ? Assets.bobFallLeft : Assets.bobFallRight;
			}
				
		}else if(bob.getState().equals(BobState.DEAD)){
			bobFrame=Assets.bobDead;	

		}
		spriteBatch.draw(bobFrame, bob.getPosition().x, bob.getPosition().y , Bob.SIZE , Bob.SIZE);
		
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		for (Block block : world.getDrawableBlocks((int)this.cam.viewportWidth , (int)this.cam.viewportHeight)) {
			Rectangle rect = block.getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		// render Bob
		Bob bob = world.getBob();
		Rectangle rect = bob.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		debugRenderer.end();
	}
	
	private void drawCollisionBlocks() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 1, 1, 1));
		for (Rectangle rect : world.getCollisionRects()) {
			debugRenderer.setColor(0, 0, 0, 0);
		}
		debugRenderer.end();
		
	}
}

package wiser.development.NinjaRunner.view;

import wiser.development.NinjaRunner.model.Block;
import wiser.development.NinjaRunner.model.Bob;
import wiser.development.NinjaRunner.model.Climable;
import wiser.development.NinjaRunner.model.Fire;
import wiser.development.NinjaRunner.model.FireBall;
import wiser.development.NinjaRunner.model.NinjaStars;
import wiser.development.NinjaRunner.model.Platform;
import wiser.development.NinjaRunner.model.Skeleton;
import wiser.development.NinjaRunner.model.SpeedPad;
import wiser.development.NinjaRunner.model.Spring;
import wiser.development.NinjaRunner.model.World;
import wiser.development.NinjaRunner.model.Block.BlockType;
import wiser.development.NinjaRunner.model.Bob.BobState;
import wiser.development.NinjaRunner.model.FireBall.FireBallState;
import wiser.development.NinjaRunner.model.Platform.PlatformState;
import wiser.development.NinjaRunner.model.Skeleton.SkeletonState;
import wiser.development.NinjaRunner.model.Skeleton.SkeletonType;
import wiser.development.NinjaRunner.model.SpeedPad.SpeedPadType;
import wiser.development.NinjaRunner.model.Spring.SpringState;
import wiser.development.NinjaRunner.screens.GameScreen;
import wiser.development.NinjaRunner.screens.GameScreen.GameState;
import wiser.development.NinjaRunner.utils.Assets;

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

	private static final float FRAME_DURATION = 0.25f;

	private World world;

	/** camera controls **/
	OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	private TextureRegion bobFrame;

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private GameScreen game;
	private TextureRegion skeletonFrame;
	private TextureRegion ninjaStarFrame;
	private float doubleJumpDraws=0;
	private TextureRegion speedPadFrame;
	private TextureRegion fireballFrame;
	float s_width,s_height;
	float stateTime;
	float dif;
	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public WorldRenderer(World world, boolean debug, GameScreen playScreen,
			SpriteBatch batcher) {
		this.spriteBatch = batcher;
		this.world = world;
		game = playScreen;
		this.cam = playScreen.cam;
		this.cam.update();
	}

	public void render() {

		spriteBatch.begin();
		drawBlocks();
		
		
		drawClimable();
		drawSkeletons();
		drawNinjaStars();
		drawSprings();
		drawFireBall();
		drawSpeedPads();
		drawPlatforms();
		drawRandomAssets();
		drawFire();
	
		// things like pause button, level progress and
							// similar
		drawBob();
		spriteBatch.end();
		this.cam.position.x = (world.getBob().getPosition().x);
		this.cam.position.y = (world.getBob().getPosition().y);
		this.cam.update();
		spriteBatch.setProjectionMatrix(cam.combined);

	}

	private void drawRandomAssets() {
		if (game.gameState.equals(GameState.RUNNING)) {
			 
			 spriteBatch.draw(Assets.directionRight, this.cam.position.x -
					 this.cam.viewportWidth/4 - 0.5f , this.cam.position.y -
					 this.cam.viewportHeight/4 - 0.7f , 0.7f , 0.7f);
			 spriteBatch.draw(Assets.directionUp, this.cam.position.x -
					 this.cam.viewportWidth/4 -1.25f  , this.cam.position.y -
					 this.cam.viewportHeight/4 , 0.7f , 0.7f);
			 spriteBatch.draw(Assets.directionLeft, this.cam.position.x -
					 this.cam.viewportWidth/2 +0.5f , this.cam.position.y -
					 this.cam.viewportHeight/4 - 0.7f , 0.7f , 0.7f);
			 spriteBatch.draw(Assets.directionDown, this.cam.position.x -
					 this.cam.viewportWidth/4 -1.25f , this.cam.position.y -
					 this.cam.viewportHeight/2 + 0.3f , 0.7f , 0.7f);
			// /nput proccessing is done by the input processor nolonger in
			// gamescreen
		} else if (game.gameState.equals(GameState.PAUSED)) {

		}

	}
	private void drawPlatforms(){
		for (Platform platform : world.getDrawablePlatforms(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)) {
					spriteBatch.draw(Assets.metalPlatform, platform.getPosition().x,
							platform.getPosition().y , platform.SIZE_WIDTH,
							platform.SIZE_HEIGHT );				
		
		}
	}
	private void drawClimable(){
		for(Climable climable : world.getDrawableClimable(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)){
			spriteBatch
			.draw(Assets.ladderTexture, climable.getPosition().x,
					climable.getPosition().y, Climable.SIZE,
					Climable.SIZE);
		}
	}
	private void drawSprings() {
		for (Spring spring : world.getDrawableSprings(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)) {
			if (spring.getState().equals(SpringState.FULL)) {
				spriteBatch.draw(Assets.spring, spring.getPosition().x,
						spring.getPosition().y, Spring.SIZE, Spring.SIZE);
			} else {
				spriteBatch.draw(Assets.springDown, spring.getPosition().x,
						spring.getPosition().y, Spring.SIZE, Spring.SIZE / 2);
			}

		}
	}

	private void drawFire() {
		for (Fire fire : world.getDrawableFire((int) this.cam.viewportWidth,
				(int) this.cam.viewportHeight)) {
			spriteBatch.draw(Assets.fireTexture, fire.getPosition().x,
					fire.getPosition().y, Fire.SIZE, Fire.SIZE);
		}
	}

	private void drawFireBall() {
		for (FireBall fireBall : world.getDrawableFireBall(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)) {
							
			
			
			if (fireBall.getState().equals(FireBallState.LEFT)) {
				fireballFrame =Assets.fireballLeftAnimation.getKeyFrame(
				fireBall.getStateTime(), true);					
			} else if (fireBall.getState().equals(FireBallState.RIGHT)){
					fireballFrame =Assets.fireballRightAnimation.getKeyFrame(
							fireBall.getStateTime(), true);
				
				
			}else{
				if (fireBall.getState().equals(FireBallState.UP)) {
					fireballFrame =Assets.fireballUpAnimation.getKeyFrame(
							fireBall.getStateTime(), true);
					
				} else if (fireBall.getState().equals(FireBallState.DOWN)) {
					fireballFrame =Assets.fireballDownAnimation.getKeyFrame(
							fireBall.getStateTime(), true);
				}
			}
			spriteBatch.draw(fireballFrame, fireBall.getPosition().x,
				fireBall.getPosition().y, fireBall.SIZE,
						fireBall.SIZE);
			
		}
	}

	private void drawBlocks() {
		for (Block block : world.getDrawableBlocks(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)) {
			if (block.getType().equals(BlockType.METAL)) {
				spriteBatch.draw(Assets.blockTexture, block.getPosition().x,
						block.getPosition().y, Block.SIZE, Block.SIZE);
			} else if (block.getType().equals(BlockType.SPIKE)) {
				spriteBatch
						.draw(Assets.spikeTexture, block.getPosition().x,
								block.getPosition().y, Block.SIZE,
								Block.SIZE);
			} else if (block.getType().equals(BlockType.SPIKE_TOP)) {
				spriteBatch
						.draw(Assets.spikeTopTexture, block.getPosition().x,
								block.getPosition().y, Block.SIZE,
								Block.SIZE);
			}
		}
	}
	private void drawSpeedPads(){
		for (SpeedPad speedPad : world.getDrawableSpeedPads(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)) {
				if(speedPad.getType().equals(SpeedPadType.RIGHT)){
					speedPadFrame = Assets.speedPadRight.getKeyFrame(
						speedPad.getStateTime(), true);
				}else if(speedPad.getType().equals(SpeedPadType.LEFT)){
					speedPadFrame = Assets.speedPadLeft.getKeyFrame(
							speedPad.getStateTime(), true);
				}
				spriteBatch.draw(speedPadFrame, speedPad.getPosition().x,
						speedPad.getPosition().y , speedPad.SIZE_WIDTH,
						speedPad.SIZE_HEIGHT );
		
		}
		
	}
	private void drawNinjaStars() {
		for (NinjaStars ninjaStar : world.getDrawableNinjaStars(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)) {
			if (ninjaStar.getVelocity().x == 0) {
				spriteBatch.draw(Assets.ninjaStars, ninjaStar.getPosition().x,
						ninjaStar.getPosition().y, Block.SIZE, Block.SIZE);
			} else {
				ninjaStarFrame = Assets.throwingStarAnimation.getKeyFrame(
						ninjaStar.getStateTime(), true);
				spriteBatch.draw(ninjaStarFrame, ninjaStar.getPosition().x,
						ninjaStar.getPosition().y + 0.3f, ninjaStar.SIZE / 2,
						ninjaStar.SIZE / 2);
			}
		}

	}

	private void drawSkeletons() {
		for (Skeleton skeleton : world.getDrawableSkeletons(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)) {
			if(skeleton.getSkeletonType().equals(SkeletonType.SKELETON)){	
				if(skeleton.getHealth()==1){
					skeletonFrame = skeleton.isFacingLeft() ? Assets.skeletonLeftAnimation
							 .getKeyFrame(skeleton.getStateTime(), true)
							 : Assets.skeletonRightAnimation.getKeyFrame(
									 skeleton.getStateTime(), true);
				}else if(skeleton.getHealth()==0){
					skeletonFrame = skeleton.isFacingLeft() ? Assets.skeletonDeadLeftAnimation
							.getKeyFrame(skeleton.getStateTime(), false)
							: Assets.skeletonDeadRightAnimation.getKeyFrame(
									skeleton.getStateTime(), true);
				}else if(skeleton.getHealth()==2){
					skeletonFrame = skeleton.isFacingLeft() ? Assets.skeletonToughLeftAnimation
							 .getKeyFrame(skeleton.getStateTime(), true)
							 : Assets.skeletonToughRightAnimation.getKeyFrame(
									 skeleton.getStateTime(), true);
				}
			}else if(skeleton.getSkeletonType().equals(SkeletonType.REAPER) ){
				if (skeleton.getState().equals(SkeletonState.DEAD)) {
					skeletonFrame = skeleton.isFacingLeft() ? Assets.skeletonDeadLeftAnimation
							.getKeyFrame(skeleton.getStateTime(), false)
							: Assets.skeletonDeadRightAnimation.getKeyFrame(
									skeleton.getStateTime(), true);
				} else{
					skeletonFrame = skeleton.isFacingLeft() ? Assets.ghostLeftAnimation
							.getKeyFrame(skeleton.getStateTime(), true)
							: Assets.ghostRightAnimation.getKeyFrame(
									skeleton.getStateTime(), true);		
				}
				
			}else if(skeleton.getSkeletonType().equals(SkeletonType.REAPER_HOVER)){
				if (skeleton.getState().equals(SkeletonState.DEAD)) {
					skeletonFrame = skeleton.isFacingLeft() ? Assets.skeletonDeadLeftAnimation
							.getKeyFrame(skeleton.getStateTime(), false)
							: Assets.skeletonDeadRightAnimation.getKeyFrame(
									skeleton.getStateTime(), true);
				} else{
			
					
					dif = skeleton.getStateTime()%3;
					 if (dif< 0.5f){
						skeletonFrame= Assets.ghostHover1;
						 skeleton.setReaperInvincible(false);
					 }else if(dif<1.0f){
						 skeletonFrame= Assets.ghostHover2;
					 }else if(dif<1.5f) {
						 skeletonFrame= Assets.ghostHover3;
					 }else{
						 skeletonFrame= Assets.ghostHover4;	
						 skeleton.setReaperInvincible(true);
					 }
					
				}
			}
			spriteBatch.draw(skeletonFrame, skeleton.getPosition().x,
				 skeleton.getPosition().y, skeleton.SIZE, skeleton.SIZE);
			if(skeleton.isInvincible()){
				spriteBatch.draw(Assets.ghostKillerField , skeleton.getPosition().x-1 ,
						 skeleton.getPosition().y-1, 4*skeleton.SIZE, 4*skeleton.SIZE);
			}
			

		}

	}

	private void drawBob() {
		Bob bob = world.getBob();
		bobFrame = bob.isFacingLeft() ? Assets.bobIdleLeft
				: Assets.bobIdleRight;

		if (bob.getState().equals(BobState.PUNCHING)) {
			bobFrame = bob.isFacingLeft() ? Assets.swordLeftAnimation
					.getKeyFrame(bob.getStateTime(), true)
					: Assets.swordRightAnimation.getKeyFrame(
							bob.getStateTime(), true);
					
					s_width=Bob.SIZE_WIDTH*3;
					s_height=Bob.SIZE_HEIGHT*1.75f;
	
		} else if (bob.getState().equals(BobState.WALKING)) {
			bobFrame = bob.isFacingLeft() ? Assets.walkLeftAnimation
					.getKeyFrame(bob.getStateTime(), true)
					: Assets.walkRightAnimation.getKeyFrame(bob.getStateTime(),
							true);
					s_width=Bob.SIZE_WIDTH;
					s_height=Bob.SIZE_HEIGHT;
	
		} else if (bob.getState().equals(BobState.JUMPING)) {
			if (bob.getVelocity().y > 0) {
				bobFrame = bob.isFacingLeft() ? Assets.bobJumpLeft
						: Assets.bobJumpRight;
			} else {
				bobFrame = bob.isFacingLeft() ? Assets.bobFallLeft
						: Assets.bobFallRight;
			}
			s_width=Bob.SIZE_WIDTH;
			s_height=Bob.SIZE_HEIGHT;

		} else if (bob.getState().equals(BobState.DOUBLEJUMP)) {
			if (bob.getVelocity().y > 0) {
				if(bob.isFacingLeft() && (bob.getStateTime()-doubleJumpDraws < 0.1f || doubleJumpDraws==0 ) ){
					bobFrame=Assets.bobDoubleJumpLeftAnimation
							.getKeyFrame(bob.getStateTime(), true);
					doubleJumpDraws+=bob.getStateTime();
				}
				else if(!bob.isFacingLeft()&& (bob.getStateTime()-doubleJumpDraws < 0.1f || doubleJumpDraws==0)){
					bobFrame=Assets.bobDoubleJumpRightAnimation
							.getKeyFrame(bob.getStateTime(), true);
					doubleJumpDraws+=bob.getStateTime();
				}
				else{
					bobFrame = bob.isFacingLeft() ? Assets.bobJumpLeft
						: Assets.bobJumpRight;				
				}
			} else {
				bobFrame = bob.isFacingLeft() ? Assets.bobFallLeft 
						: Assets.bobFallRight;
				doubleJumpDraws=0;
			}
			s_width=Bob.SIZE_WIDTH;
			s_height=Bob.SIZE_HEIGHT;
		} else if (bob.getState().equals(BobState.CLIMBING)) {
			bobFrame=Assets.climbUpAnimation
			.getKeyFrame(bob.getStateTime(), true);
			s_width=Bob.SIZE_WIDTH;
			s_height=Bob.SIZE_HEIGHT;
		} else if (bob.getState().equals(BobState.DEAD)) {
			bobFrame = Assets.bobDead;
			s_width=Bob.SIZE_WIDTH;
			s_height=Bob.SIZE_HEIGHT;
		}else if (bob.getState().equals(BobState.IDLE_CLIMBING)){
			bobFrame = Assets.climbIdle;
			s_width=Bob.SIZE_WIDTH;
			s_height=Bob.SIZE_HEIGHT;
		}
		if(s_width>Bob.SIZE_WIDTH && s_height> Bob.SIZE_HEIGHT){
			spriteBatch.draw(bobFrame, bob.getPosition().x-Bob.SIZE_WIDTH, bob.getPosition().y,
				s_width,s_height);
			
		}else{
			spriteBatch.draw(bobFrame, bob.getPosition().x, bob.getPosition().y,
					s_width,s_height);
		}
		s_width=Bob.SIZE_WIDTH;
		s_height=Bob.SIZE_HEIGHT;
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		for (Block block : world.getDrawableBlocks(
				(int) this.cam.viewportWidth, (int) this.cam.viewportHeight)) {
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

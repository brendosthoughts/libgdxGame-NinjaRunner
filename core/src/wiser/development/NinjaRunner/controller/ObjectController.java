package wiser.development.NinjaRunner.controller;

import java.util.ArrayList;
import java.util.Iterator;

import wiser.development.NinjaRunner.model.Block;
import wiser.development.NinjaRunner.model.Bob;
import wiser.development.NinjaRunner.model.Fire;
import wiser.development.NinjaRunner.model.FireBall;
import wiser.development.NinjaRunner.model.NinjaStars;
import wiser.development.NinjaRunner.model.Platform;
import wiser.development.NinjaRunner.model.Skeleton;
import wiser.development.NinjaRunner.model.SpeedPad;
import wiser.development.NinjaRunner.model.World;
import wiser.development.NinjaRunner.model.Bob.BobState;
import wiser.development.NinjaRunner.model.FireBall.FireBallState;
import wiser.development.NinjaRunner.model.Platform.PlatformState;
import wiser.development.NinjaRunner.model.Skeleton.SkeletonState;
import wiser.development.NinjaRunner.screens.GameScreen;
import wiser.development.NinjaRunner.screens.GameScreen.GameState;
import wiser.development.NinjaRunner.utils.Assets;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ObjectController {
	
	private static final float ACCELERATION 	= 5f;
	private static final float MAX_VEL 	= 4f;


	
	private World 	world;
	private ArrayList<Skeleton> skeletons =new ArrayList<Skeleton>();
	private GameScreen gameScreen;
	private Bob bob;
	private Array<Block> collidableBlocks = new Array<Block>();
	private ArrayList<NinjaStars> thrownStars= new ArrayList<NinjaStars>();
	private Skeleton skeleton;
	private ArrayList<FireBall>  fireBalls = new ArrayList<FireBall>();
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private Rectangle platformDumbyBounds= new Rectangle();
	private Vector2 platformLeft;
	private Vector2 platformRight;
	private Rectangle dumbyBounds;
	public ObjectController(World world, GameScreen playScreen) {
		this.world = world;
		this.skeletons = world.getLevel().getSkeletons();
		this.bob= world.getBob();
		this.gameScreen = playScreen;
	}
	
		
	public void update(float delta) {
		controlFireballs(delta);
	 	controlPlatforms(delta);
		controlStars(delta);
		//update speedpad for animations :) ... not a great place for this but not sure where is better
		for (SpeedPad speedPad : world.getDrawableSpeedPads(
				world.getLevel().getWidth(), world.getLevel().getHeight())) {
			if(speedPad==null) continue;
			speedPad.update(delta);
		
		}
	}
	private void controlPlatforms(float delta){
		platforms= world.getLevel().getPlatforms();
		Iterator<Platform> platformIt = platforms.iterator();
		while(platformIt.hasNext()){
			Platform platform = platformIt.next();
				if(platform.getState().equals(PlatformState.LEFT)){
					platform.getVelocity().scl(delta);
					platform.setPosition(platform.getPosition().add(platform.getVelocity()));
					platform.getVelocity().scl(1/delta);
					if(platform.getPosition().x < platform.getInititalPosition().x -platform.getVariance() ){
						platform.setState(PlatformState.RIGHT);
						platform.getVelocity().x=platform.getSpeed();
					}
				}
				if(platform.getState().equals(PlatformState.RIGHT)){
					platform.getVelocity().scl(delta);
					platform.setPosition(platform.getPosition().add(platform.getVelocity()));
					platform.getVelocity().scl(1/delta);
					if(platform.getPosition().x > platform.getInititalPosition().x + platform.getVariance() ){
						platform.setState(PlatformState.LEFT);
						platform.getVelocity().x=-platform.getSpeed();
						
					}
					
				}
				platform.update(delta);
		}
	}
	private void controlFireballs(float delta){
		fireBalls = world.getLevel().getFireBalls();
		Iterator<FireBall> fireballIt =  fireBalls.iterator();
		while(fireballIt.hasNext())
		{
				FireBall fireball = fireballIt.next(); 
				
				fireball.getVelocity().scl(delta);
				 if(fireball.getState().equals(FireBallState.LEFT) ||fireball.getState().equals(FireBallState.RIGHT) ){
					// fireball is moving right inside variance
					if(fireball.getPosition().x < fireball.getInititalPosition().x +fireball.getVariance() 
							&& ( fireball.getPosition().x > fireball.getInititalPosition().x -fireball.getVariance() )
							&&fireball.getState().equals(FireBallState.RIGHT))
					{
						fireball.setPosition(fireball.getPosition().add(fireball.getVelocity()));
					}
					//fireball is moving left outside variance and therefore switch direction
					else if((fireball.getPosition().x >= fireball.getInititalPosition().x +fireball.getVariance() )) 
					{
						fireball.setState(FireBallState.LEFT);
						fireball.setPosition(fireball.getPosition().sub(fireball.getVelocity()));		
					}
					//fireball is moving right and inside variance
					else if(( fireball.getPosition().x < fireball.getInititalPosition().x +fireball.getVariance())
							&& ( fireball.getPosition().x > fireball.getInititalPosition().x -fireball.getVariance())
							&& fireball.getState().equals(FireBallState.LEFT))
					{			
						fireball.setPosition(fireball.getPosition().sub(fireball.getVelocity()));						
					}
					//fireball is moving right and outside variance therefore switch direction
					else if ((fireball.getPosition().x <= fireball.getInititalPosition().x -fireball.getVariance())) 
					{
						fireball.setState(FireBallState.RIGHT);
						fireball.setPosition(fireball.getPosition().add(fireball.getVelocity()));						
					}

				}else{
					if(( fireball.getPosition().y <= fireball.getInititalPosition().y + fireball.getVariance())
							&& (fireball.getPosition().y > fireball.getInititalPosition().y - fireball.getVariance())
							&& fireball.getState().equals(FireBallState.UP))
					{
						fireball.setPosition(fireball.getPosition().add(fireball.getVelocity()));
					}else if( fireball.getPosition().y > fireball.getInititalPosition().y +fireball.getVariance())
					{
						// change state 
						fireball.setState(FireBallState.DOWN);
						fireball.setPosition(fireball.getPosition().sub(fireball.getVelocity()));	
						
					}else if(( fireball.getPosition().y <= fireball.getInititalPosition().y +fireball.getVariance())
							&& ( fireball.getPosition().y > fireball.getInititalPosition().y -fireball.getVariance() )
							&& fireball.getState().equals(FireBallState.DOWN))
					{			
						fireball.setPosition(fireball.getPosition().sub(fireball.getVelocity()));						
					}else if (fireball.getPosition().y < fireball.getInititalPosition().y -fireball.getVariance()){
						fireball.setState(FireBallState.UP);
						fireball.setPosition(fireball.getPosition().add(fireball.getVelocity()));						
					}
				}

				fireball.getVelocity().scl(1/delta);
				fireball.update(delta);
				if(fireball.getBounds().overlaps(bob.getBounds())){
					bob.setState(BobState.DEAD);
					gameScreen.setGameState(GameState.GAME_OVER);
					bob.getVelocity().x = 0;		
				}
		}
	}
	
	private void controlStars(float delta ){
		thrownStars=world.getLevel().getThrownStars();
		Iterator<NinjaStars> starIt =  thrownStars.iterator();

		while(starIt.hasNext()){
			NinjaStars star= starIt.next();
			
			star.getVelocity().scl(delta);
			star.setPosition(star.getPosition().add(star.getVelocity()));
			
			Iterator<Skeleton> skel =  skeletons.iterator();
			while(skel.hasNext())
			{
				
			    skeleton = skel.next();
			    if (star.getBounds().overlaps(skeleton.getBounds()) && !(skeleton.getState().equals(SkeletonState.DEAD))&& !skeleton.isInvincible()) {
			    		
			    		skeleton.setHealth(skeleton.getHealth()-1);	
			    		starIt.remove();
			    		
			    }							
			}
			populateCollidableBlocks(0, 0, world.getLevel().getWidth(), world.getLevel().getWidth() );
			for (Block block : collidableBlocks) {
				if (block != null) {
					
				   if (star.getBounds().overlaps(block.getBounds())) {
					  starIt.remove();
				   }
				}
			}
			star.getVelocity().scl(1/delta);
			star.update(delta);
		}	
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

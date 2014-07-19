package wiser.development.starAssault.controller;

import java.util.ArrayList;
import java.util.Iterator;

import wiser.development.starAssault.model.Block;
import wiser.development.starAssault.model.Bob;
import wiser.development.starAssault.model.Bob.BobState;
import wiser.development.starAssault.model.Fire;
import wiser.development.starAssault.model.FireBall;
import wiser.development.starAssault.model.FireBall.FireBallState;
import wiser.development.starAssault.model.NinjaStars;
import wiser.development.starAssault.model.Skeleton;
import wiser.development.starAssault.model.Skeleton.SkeletonState;
import wiser.development.starAssault.model.World;
import wiser.development.starAssault.screens.GameScreen;
import wiser.development.starAssault.screens.GameScreen.GameState;
import wiser.development.starAssault.utils.Assets;

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
	private FireBall[][] fireBalls;
	private ArrayList<Skeleton> collidableSkeletons = new ArrayList<Skeleton>();
	private ArrayList<NinjaStars> thrownStars= new ArrayList<NinjaStars>();
	private Skeleton skeleton;
	public ObjectController(World world, GameScreen playScreen) {
		this.world = world;
		this.skeletons = world.getLevel().getSkeletons();
		this.bob= world.getBob();
		this.gameScreen = playScreen;
	}
	
		
	public void update(float delta) {
		fireBalls= world.getLevel().getFireBalls();
		for (int col =0; col <= world.getLevel().getWidth()-1 ; col++ ){
			for (int row=0; row<= world.getLevel().getHeight()-1; row++){
				if(fireBalls[col][row] !=null){
				FireBall fireball = fireBalls[col][row]; 
				fireball.getVelocity().scl(delta);
					if(( fireball.getPosition().y <= fireball.getInititalPosition().y +2f)
							&& (fireball.getPosition().y > fireball.getInititalPosition().y -2f )
							&& fireball.getState().equals(FireBallState.UP))
					{
						fireball.setPosition(fireball.getPosition().add(fireball.getVelocity()));
					}else if( fireball.getPosition().y > fireball.getInititalPosition().y +2f)
					{
						// change state 
						fireball.setState(FireBallState.DOWN);
						fireball.setPosition(fireball.getPosition().sub(fireball.getVelocity()));	
						
					}else if(( fireball.getPosition().y <= fireball.getInititalPosition().y +2f)
							&& ( fireball.getPosition().y > fireball.getInititalPosition().y -2f )
							&& fireball.getState().equals(FireBallState.DOWN))
					{			
						fireball.setPosition(fireball.getPosition().sub(fireball.getVelocity()));						
					}else if (fireball.getPosition().y < fireball.getInititalPosition().y -2f){
						fireball.setState(FireBallState.UP);
						fireball.setPosition(fireball.getPosition().add(fireball.getVelocity()));						
					}
				fireball.getVelocity().scl(1/delta);
				}
			}
		}
		
		int index=0;
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
			    if (star.getBounds().overlaps(skeleton.getBounds()) && !(skeleton.getState().equals(SkeletonState.DEAD))) {
			    		skeleton.setState(SkeletonState.DEAD);	
			    		skeleton.setVelocity(new Vector2(0,0));
			    		starIt.remove();
					//	world.getLevel().destroyThrowingStar(index);
			    		
			    }							
			}
			for (Block block : collidableBlocks) {
				if (block == null) continue;
				if (star.getBounds().overlaps(block.getBounds())) {
					starIt.remove();
				}
			}
			star.getVelocity().scl(1/delta);
			index++;
			star.update(delta);
		}	
		
		
	}


}

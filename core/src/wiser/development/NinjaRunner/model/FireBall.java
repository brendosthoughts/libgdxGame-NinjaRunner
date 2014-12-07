package wiser.development.NinjaRunner.model;

import wiser.development.NinjaRunner.model.Platform.PlatformState;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class FireBall {

	 public static final float SIZE = 0.7f;
	 public static final float SIZE_WIDTH = 0.7f;
	 public static final float SIZE_HEIGHT = 0.7f;

	  Vector2  position = new Vector2();
	  Vector2  velocity = new Vector2();
	  float init_pos_x;
	  float init_pos_y;
	  float stateTime=0;
	  float variance=1;
	  float speed;
	  Rectangle  bounds = new Rectangle();
	  FireBallState state;
	  boolean isBackforth;

		public static enum FireBallState{
			UP, DOWN, LEFT, RIGHT
		}
	  
	  

	 public FireBall(Vector2 initpos, Vector2 vel) {
		this.init_pos_x= initpos.x;
		this.init_pos_y= initpos.y;
		this.position = initpos;
		this.bounds.setX(position.x );
		this.bounds.setY(position.y );
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.velocity= vel;
		this.variance=2f;
		if(vel.x >0){
			this.speed=vel.x;
			this.state=FireBallState.RIGHT; 
		}else if(vel.x<0){
			this.speed= -vel.x;
			this.state=FireBallState.LEFT;
		}else {
			this.speed=vel.y;
			this.state=FireBallState.UP;
		}
	
	}
	 public FireBall(Vector2 initpos, float speed , float variance, FireBallState state) {
			this.init_pos_x= initpos.x;
			this.init_pos_y= initpos.y;
			this.position = initpos;
			this.variance =variance;
			this.bounds.setX(position.x );
			this.bounds.setY(position.y );
			this.bounds.width = SIZE_WIDTH;
			this.bounds.height = SIZE_HEIGHT;
			this.speed=speed;
			if(state == FireBallState.RIGHT || state == FireBallState.LEFT){
		 		this.velocity= new Vector2(speed, 0);
		 	}else{
		 		this.velocity= new Vector2(0,speed);
		 	}
			this.state=state;
			
		
		}
	 public Rectangle getBounds(){
		 return bounds;
	 }
	 public Vector2 getPosition(){
		 return position;
		 
	 }
	 public void setPosition(Vector2 pos){
		 this.position=pos;
		 this.bounds.setX(pos.x);
		 this.bounds.setY(pos.y);
		 
	 }
	public Vector2 getInititalPosition(){
		return new Vector2(init_pos_x, init_pos_y);
	}
	public Vector2 getVelocity() {
		return this.velocity;
	}
	public void update(float delta){
		this.bounds.setX(this.position.x);
		this.bounds.setY( this.position.y);
		stateTime+=delta;
		
	}
	public float getStateTime(){
		return stateTime;
	}
	public void setState(FireBallState newState){
		this.state=newState;
	}
	public FireBallState getState(){
		return this.state;
	}
	public float getVariance() {
		return this.variance;
	}
	public void setVelocity(Vector2 newVel) {
		this.velocity=newVel;
		
	}
	public float getSpeed(){
		return this.speed;
	}
	
}

package wiser.development.starAssault.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
// 286129. transfer num
public class Platform {

	 public static final float SIZE_WIDTH = 3F;
	 public static final float SIZE_HEIGHT = 0.8F;
		
	  Vector2  position = new Vector2();
	  Vector2  velocity = new Vector2();
	  float init_pos_x;
	  float init_pos_y;
	  float variance;
	  float speed;
	  // amount platform moves off initial position (only single as platforms only move left/right or up/down)
	  float stateTime=0;
	  Rectangle  bounds = new Rectangle();
	 PlatformState state;

		public static enum PlatformState{
			LEFT, RIGHT, UP, DOWN
		}
	  
	  

	 public Platform(Vector2 initpos, float speed , float variance, PlatformState state) {
		this.init_pos_x= initpos.x;
		this.init_pos_y= initpos.y;
		this.position = initpos;
		this.variance =variance;
		this.bounds.setX(position.x );
		this.bounds.setY(position.y );
		this.bounds.width = SIZE_WIDTH;
		this.bounds.height = SIZE_HEIGHT;
		this.speed=speed;
		if(state == PlatformState.RIGHT ){
			this.velocity= new Vector2(speed, 0);
	 	}else if(state == PlatformState.LEFT  ){ 
	 		this.velocity= new Vector2(-speed, 0);
	 	}else{
	 		this.velocity= new Vector2( 0, speed);
	 	}
		this.state=state;
		
	
	}
	 public float getSpeed(){
		 return speed;
	 }
	 public Rectangle getBounds(){
		 return bounds;
	 }
	 public Vector2 getPosition(){
		 return position;
		 
	 }
	 public float getVariance(){
		 return variance;
	 }
	 public void setPosition(Vector2 pos){
		 this.position=pos;
		 this.bounds.setX(pos.x);
		 this.bounds.setY(pos.y);
		 
	 }
	public Vector2 getInititalPosition(){
		return new Vector2(init_pos_x, init_pos_y);
	}
	public void setVelocity(Vector2 vel){
		this.velocity=vel;
	}
	public Vector2 getVelocity() {
		return this.velocity;
	}
	public void update(float delta){
		stateTime+=delta;
		
	}
	public float getStateTime(){
		return stateTime;
	}
	public void setState(PlatformState newState){
		this.state=newState;
	}
	public PlatformState getState(){
		return this.state;
	}
	
}

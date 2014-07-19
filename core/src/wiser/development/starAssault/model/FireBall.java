package wiser.development.starAssault.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
// 286129. transfer num
public class FireBall {

	 public static final float SIZE = 0.7f;

	  Vector2  position = new Vector2();
	  Vector2  velocity = new Vector2();
	  float init_pos_x;
	  float init_pos_y;
	  Rectangle  bounds = new Rectangle();
	  FireBallState state;

		public enum FireBallState{
			UP, DOWN, LEFT, RIGHT
		}
	  
	  

	 public FireBall(Vector2 initpos, Vector2 vel) {
		this.init_pos_x= initpos.x;
		this.init_pos_y= initpos.y;
		this.position = initpos;
		this.bounds.setX(position.x + SIZE);
		this.bounds.setY(position.y + SIZE);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.velocity= vel;
		this.state=FireBallState.UP; 
	 }
	 public Rectangle getBounds(){
		 return bounds;
	 }
	 public Vector2 getPosition(){
		 return position;
		 
	 }
	 public void setPosition(Vector2 pos){
		 this.position=pos;
		 this.bounds.setX(position.x);
		 this.bounds.setY(position.y);
		 
	 }
	public Vector2 getInititalPosition(){
		return new Vector2(init_pos_x, init_pos_y);
	}
	public Vector2 getVelocity() {
		return this.velocity;
	}
	public void update(){
		bounds.x=position.x;
		bounds.y = position.y;
		
	}
	public void setState(FireBallState newState){
		this.state=newState;
	}
	public FireBallState getState(){
		return this.state;
	}
	
}

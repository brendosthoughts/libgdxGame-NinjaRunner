package wiser.development.NinjaRunner.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Spring {
	
	public enum SpringState {
		FULL, COMPRESSED
	}

	 public static final float SIZE = 0.5f;

	  Vector2  position = new Vector2();
	  Rectangle  bounds = new Rectangle();
	  SpringState state;
	 public Spring(Vector2 pos) {
	  this.position = pos;
	  this.state = SpringState.FULL;
	  this.bounds.setX(pos.x);
	  this.bounds.setY(pos.y);
	  this.bounds.width = SIZE;
	  this.bounds.height = SIZE;
	 }
	 public Rectangle getBounds(){
		 return bounds;
	 }
	 public Vector2 getPosition(){
		 return position; 
	 }

	 public void setState(SpringState newState){
		 this.state=newState;
	 }
	 public SpringState getState(){
		 return this.state;
	 }
}
	

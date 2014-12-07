package wiser.development.NinjaRunner.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SpeedPad {

	public enum SpeedPadType{
		RIGHT, LEFT, UP, DOWN;
	}
	public static final float SIZE_WIDTH= 1f;
	public static final float SIZE_HEIGHT= 1f;
	 
	float stateTime=0;
	Vector2  position = new Vector2();
	Rectangle  bounds = new Rectangle();
	SpeedPadType type;

 public SpeedPad(Vector2 pos, SpeedPadType type) {
  this.position = pos;
  this.bounds.setX(pos.x);
  this.bounds.setY(pos.y);	
  this.bounds.width = SIZE_WIDTH;
  this.bounds.height = SIZE_HEIGHT;
  this.type=type;
  }
 public SpeedPadType getType(){
	 return this.type;
 }
 public Rectangle getBounds(){
	 return bounds;
 }
 public Vector2 getPosition(){ 
	 return position;
	 
 }
public float getStateTime() {
	// TODO Auto-generated method stub
	return stateTime;
}
public void update(float delta){
	stateTime += delta;
}
}
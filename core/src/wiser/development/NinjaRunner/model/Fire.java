package wiser.development.NinjaRunner.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Fire {

 public static final float SIZE = 1f;

  Vector2  position = new Vector2();
  Rectangle  bounds = new Rectangle();
  float stateTime=0;

 public Fire(Vector2 pos) {
  this.position = pos;
	this.bounds.setX(pos.x*SIZE);
	this.bounds.setY(pos.y*SIZE);
  this.bounds.width = SIZE;
  this.bounds.height = SIZE;
 }
 public Rectangle getBounds(){
	 return bounds;
 }
 public Vector2 getPosition(){
	 return position;
	 
 }
  public void update(float delta){
	stateTime+=delta;
  }
  
  public float getStateTime(){
	  return stateTime;
  }
}
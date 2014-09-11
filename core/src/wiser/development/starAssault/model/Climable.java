package wiser.development.starAssault.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Climable {

	public enum ClimbType{
		LADDER, VINE;
	}
 public static final float SIZE = 1f;
 public static final float SPIKESIZE=1f;
 static float stateTime=0;
  Vector2  position = new Vector2();
  Rectangle  bounds = new Rectangle();
  ClimbType type;
  public Climable(Vector2 pos) {
	  	this.position = pos;
	  	this.bounds.setX(pos.x*SIZE);
		this.bounds.setY(pos.y*SIZE);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.type=ClimbType.LADDER;
	  }
 public Climable(Vector2 pos, ClimbType type) {
  this.position = pos;
		this.bounds.setX(pos.x*SPIKESIZE);
		this.bounds.setY(pos.y*SPIKESIZE);
		this.bounds.width = SPIKESIZE;
		this.bounds.height = SPIKESIZE;
  
  this.type=type;
  }
 public ClimbType getType(){
	 return this.type;
 }
 public Rectangle getBounds(){
	 return bounds;
 }
 public Vector2 getPosition(){
	 return position;
	 
 }

}
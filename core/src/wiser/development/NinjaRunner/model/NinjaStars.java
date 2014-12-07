package wiser.development.NinjaRunner.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*this will be kind of a strange class as a still ninja star represents stars ninja can pick up (giving hime 5)
and stars with a velocity set are stars the ninja has already thrown default velocity(2, 0) ie only moves across x axis*/
public class NinjaStars {

	 public static final float SIZE = 0.7f;

	  Vector2  position = new Vector2();
	  Vector2  velocity = new Vector2();
	  Rectangle  bounds = new Rectangle();
	  float stateTime=0;
	  

	 public NinjaStars(Vector2 pos, Vector2 vel) {
		this.position = pos;
		this.bounds.setX(position.x );
		this.bounds.setY(position.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.velocity= vel;
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
	public Vector2 getVelocity() {
		return this.velocity;
	}
	public float getStateTime() {
		return stateTime;
	}
	public void update(float delta){
		stateTime += delta;
		bounds.x=position.x;
		bounds.y = position.y;
		
	}
	
}

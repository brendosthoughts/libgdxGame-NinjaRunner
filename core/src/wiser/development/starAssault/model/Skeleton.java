package wiser.development.starAssault.model;


import wiser.development.starAssault.model.Platform.PlatformState;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Skeleton {
	
	public enum SkeletonState {
		IDLE,WALKING, DEAD
	}
	public enum SkeletonType{
		 SKELETON, TOUGH, REAPER, FIRE
	}
	public static final float SIZE = 0.7f; // part of a unit
	public static final float SIZE_WIDTH = 0.5f;
	public static final float SIZE_HEIGHT = 0.5f;
	
	public static final float REAPER_SIZE = 1f; // part of a unit
	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2(-5, 0);
	Rectangle 	bounds = new Rectangle();
	SkeletonState		state = SkeletonState.IDLE;
	SkeletonType type;
	boolean custom; 
	private int health;
	boolean		facingLeft = true;
	boolean  backforth;
	float		stateTime = 0;
	float initpos_x, initpos_y;
	float variance;
	float speed;
	public Skeleton (Vector2 position, SkeletonType skeleton_type ) {
		this.initpos_x=position.x;
		this.initpos_y=position.y;
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.state= SkeletonState.WALKING;
		this.type=skeleton_type;
		this.variance=2f;
		this.velocity=new Vector2(2,0);
		this.backforth=true;
		if(skeleton_type==SkeletonType.REAPER){
			this.setHealth(2);
			this.bounds.height=REAPER_SIZE;
			this.bounds.width=REAPER_SIZE;
		}else{
			this.setHealth(1);
			this.bounds.height = SIZE;
			this.bounds.width = SIZE;
		}
		this.custom=false;
	}
	public Skeleton(Vector2 initpos, float speed , float variance, SkeletonType type, boolean backforth) {
		this.initpos_x= initpos.x;
		this.initpos_y= initpos.y;
		this.position = initpos;
		this.variance =variance;
		this.bounds.x=position.x ;
		this.bounds.y=position.y ;
		this.bounds.width = SIZE_WIDTH;
		this.bounds.height = SIZE_HEIGHT;
		this.speed=speed;
		this.setHealth(1);
		this.velocity= new Vector2(speed, 0);
		this.type=type;
		this.custom=true;
		this.backforth=backforth;
	
	}
	public boolean isCustom(){
		return custom;
		
	}
	public Vector2 getInitialPosition(){
		return new Vector2(initpos_x, initpos_y);
	}
	public boolean isFacingLeft() {
		return facingLeft;
	}
	public SkeletonType getSkeletonType(){
		return this.type;
	}
	public void setFacingLeft(boolean newDir) {
		this.facingLeft = newDir;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public SkeletonState getState() {
		return state;
	}
	
	public void setState(SkeletonState newState) {
		this.state = newState;
	}

	public float getStateTime() {
		return stateTime;
	}



	public void setPosition(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
	}


	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}


	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}


	public void update(float delta) {

		bounds.x = position.x;
		bounds.y = position.y;
		stateTime += delta;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public boolean isBackforth() {
		// TODO Auto-generated method stub
		return backforth;
	}
	public float getVariance() {
		// TODO Auto-generated method stub
		return this.variance;
	}


}

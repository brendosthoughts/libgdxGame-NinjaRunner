package wiser.development.starAssault.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Skeleton {
	
	public enum SkeletonState {
		IDLE, WALKING, DEAD
	}
	public enum SkeletonType{
		LEFT, RIGHT, BACKFORTH, TOUGH_LEFT, TOUGH_RIGHT, TOUGH_BACKFORTH
	}
	public static final float SIZE = 0.7f; // part of a unit

	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2(-5, 0);
	Rectangle 	bounds = new Rectangle();
	SkeletonState		state = SkeletonState.IDLE;
	SkeletonType type;
	private int health;
	boolean		facingLeft = true;
	float		stateTime = 0;
	float initpos_x, initpos_y;
	
	public Skeleton (Vector2 position, SkeletonType skeleton_type ) {
		this.initpos_x=position.x;
		this.initpos_y=position.y;
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.state= SkeletonState.WALKING;
		this.type=skeleton_type;
		this.setHealth(1);
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
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


}

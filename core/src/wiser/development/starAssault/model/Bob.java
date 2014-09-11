package wiser.development.starAssault.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import wiser.development.starAssault.model.Level;

public class Bob {

	public enum BobState {
		IDLE, WALKING, JUMPING, DOUBLEJUMP, PUNCHING, DEAD, THROW, CLIMBING, IDLE_CLIMBING
	}
	public static final float SIZE_WIDTH = 0.6f; // part of a unit
	public static final float SIZE_HEIGHT =0.9f;

	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	BobState	state = BobState.IDLE;
	BobState 	lastState = BobState.IDLE;
	boolean		facingLeft = false;
	float		stateTime = 0;
	boolean		longJump = false , canThrow= true;
	float punchTime;
	int throwingStars=0;
	float timeAfterThrow=0;
	
	public Bob(Vector2 position) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.state = BobState.IDLE;
		this.bounds.height = SIZE_HEIGHT;
		this.bounds.width = SIZE_WIDTH;
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
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

	public BobState getState() {
		return state;
	}
	
	public void setState(BobState newState) {
		this.lastState=this.state;
		this.state = newState;
	}

	public float getStateTime() {
		return stateTime;
	}

	public boolean isLongJump() {
		return longJump;
	}
	public BobState getLastState(){
		return this.lastState;
	}

	public void setLongJump(boolean longJump) {
		this.longJump = longJump;
	}


	public void setPosition(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x + SIZE_WIDTH-0.1F);
		this.bounds.setY(position.y + SIZE_HEIGHT-0.1F);
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
	
	public int getThrowingStars(){
		return throwingStars;
	}
	
	public void setThrowingStars(int numStars){
		throwingStars = numStars;	
	}

	public void update(float delta) {
//		position.add(velocity.tmp().mul(delta));
		bounds.x = position.x;
		bounds.y = position.y;
		if(this.state.equals(BobState.PUNCHING)){
			punchTime+=delta;
				if(punchTime< delta*40){
					punchTime+=delta;
				}else{
					this.state=BobState.IDLE;
					punchTime=0;
				}
		}

		stateTime += delta;

	}

	public boolean canThrow() {
		return this.canThrow;
	}
	public void setCanThrow(boolean status){
		this.canThrow=status;
	}
}
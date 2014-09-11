package wiser.development.starAssault.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {

	public enum BlockType{
		METAL, WOOD, GRASS, SPIKE, SPIKE_TOP, LADDER, SPIKE_LEFT, SPIKE_RIGHT;
	}
 public static final float SIZE = 1f;
 public static final float SPIKESIZE_WIDTH=1f;
 public static final float SPIKESIZE_HEIGHT=0.5f;
 static float stateTime=0;
  Vector2  position = new Vector2();
  Rectangle  bounds = new Rectangle();
  BlockType type;
  public Block(Vector2 pos) {
	  	this.position = pos;
	  	this.bounds.setX(pos.x*SIZE);
		this.bounds.setY(pos.y*SIZE);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.type=BlockType.METAL;
	  }
 public Block(Vector2 pos, BlockType type) {
  this.position = pos;
  if(type==BlockType.SPIKE || type==BlockType.SPIKE_TOP ){
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SPIKESIZE_WIDTH;
		this.bounds.height = SPIKESIZE_HEIGHT;
  }else if(type==BlockType.SPIKE_LEFT || type==BlockType.SPIKE_RIGHT){
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.height = SPIKESIZE_WIDTH;
		this.bounds.width = SPIKESIZE_HEIGHT;
  }else{
  
	  this.bounds.setX(pos.x*SIZE);
	  this.bounds.setY(pos.y*SIZE);
	  this.bounds.width = SIZE;
	  this.bounds.height = SIZE;
  }
  this.type=type;
  }
 public BlockType getType(){
	 return this.type;
 }
 public Rectangle getBounds(){
	 return bounds;
 }
 public Vector2 getPosition(){
	 return position;
	 
 }

}
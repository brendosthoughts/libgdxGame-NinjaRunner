package wiser.development.starAssault.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {

	public enum BlockType{
		METAL, WOOD, GRASS, SPIKE;
	}
 public static final float SIZE = 1f;
 public static final float SPIKESIZE=0.5f;

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
	this.bounds.setX(pos.x*SIZE);
	this.bounds.setY(pos.y*SIZE);
  this.bounds.width = SIZE;
  this.bounds.height = SIZE;
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
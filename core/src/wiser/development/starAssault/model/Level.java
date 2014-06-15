package wiser.development.starAssault.model;


import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class Level {

	private int width;
	private int height;
	private Vector2 finish;
	private Block[][] blocks;
	private Fire[][] fires;
	private Skeleton[][] skeletons;
	ArrayList<Skeleton> realSkeletons = new ArrayList<Skeleton>();

	public Vector2 getFinish(){
		return finish;
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Block[][] getBlocks() {
		return blocks;
	}
	public Fire[][] getFires(){
		return fires;
	}
	public ArrayList<Skeleton> getSkeletons() {
		int i=0;
		for (Skeleton[] skeletonA : skeletons){
			for (Skeleton skeleton : skeletonA){
				if(skeleton!=null){
					realSkeletons.add(skeleton);
					
				}
			}
		}
		return realSkeletons;

	}
	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}
	public Block getCollidableBlocks(int x, int y) {
		return blocks[x][y];
	}
	public Fire getCollidableFires(int x, int y){
		return fires[x][y];
	}
	public Skeleton getCollidableSkeletons(int x, int y) {
		return skeletons[x][y];
	}

	
	public Level(int level_num) {
		switch(level_num){
			case 1:
				level_one();
				break;
			case 2:
				level_two();
				break;
			case 3:
				level_three();
				break;
			case 4:
				level_four();
				break;
			case 5:
				level_five();
				break;
			case 6:
				level_six();
				break;
			default:
				level_one();
				break;
		
		}
	}




	private void level_one(){
		width = 50;
		height = 20;
		blocks = new Block[width][height];
		fires = new Fire[width][height];
		skeletons =new Skeleton[width][height];
		//draw all as empty block to start 
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
				fires[col][row]= null;
				skeletons[col][row]=null;
			}
		}

		for (int col = 0; col < width; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0));
			/*if ((col > 4)&& ((col!=9) || (col!=15) || (col!=16 )|| (col!=19 )|| (col!=23 )|| (col!=26 )|| (col!=27 )|| (col!=29 )|| (col!= 30) )){
				blocks[col][2] = new Block(new Vector2(col, 2));
			}*/
		}
//make step set starting at block 8 
		 blocks[8][1] = new Block(new Vector2(8 ,1 ));
		 blocks[9][1] = new Block(new Vector2(9 , 1));
		 blocks[9][2] = new Block(new Vector2(9 , 2));
		 blocks[10][1] = new Block(new Vector2(10 ,1 ));
		 blocks[10][2] = new Block(new Vector2( 10 ,2 ));
		blocks[10][3] = new Block(new Vector2( 10, 3));
		 // fire one
		
		fires[11][1]= new Fire(new Vector2(11,1));
		//jump one
		 blocks[12][1] = new Block(new Vector2( 12, 1));
		 blocks[12][2] = new Block(new Vector2(12 ,2 ));
		blocks[12][3] = new Block(new Vector2( 12, 3));
		
		//fire 2
		fires[13][1]= new Fire(new Vector2(13,1));
		
		//jump2
		
		 blocks[14][1] = new Block(new Vector2( 14, 1));
		 blocks[14][2] = new Block(new Vector2( 14, 2));
		 blocks[14][3] = new Block(new Vector2( 14, 3));
		 blocks[14][4] = new Block(new Vector2( 14, 4));
		 
		 //fire 3
		 fires[15][1] = new Fire(new Vector2(15,1));
		 fires[16][1] = new Fire(new Vector2(16,1));
		 
		 //jump 3 
		 blocks[17][1] = new Block(new Vector2( 17, 1));
		 blocks[17][2] = new Block(new Vector2( 17, 2));
		 blocks[17][3] = new Block(new Vector2( 17, 3));
		 blocks[17][4] = new Block(new Vector2( 17, 4));
		 blocks[18][1] = new Block(new Vector2( 18, 1));
		 blocks[18][2] = new Block(new Vector2( 18, 2));
		 blocks[18][3] = new Block(new Vector2( 18, 3));
		 blocks[18][4] = new Block(new Vector2( 18, 4));
		 
		 fires[19][1] = new Fire(new Vector2(19,1));
		 fires[20][1] = new Fire(new Vector2(20,1));
		 fires[21][1] = new Fire(new Vector2(21,1));
		 
		 blocks[22][1] = new Block(new Vector2( 22, 1));
		 blocks[22][2] = new Block(new Vector2( 22, 2));
		 blocks[22][3] = new Block(new Vector2( 22, 3));
		 blocks[23][1] = new Block(new Vector2( 23, 1));
		 blocks[23][2] = new Block(new Vector2( 23, 2));
		 blocks[23][3] = new Block(new Vector2( 23,3 ));
		 
		 fires[24][1] = new Fire(new Vector2( 24, 1));
		 fires[25][1] = new Fire(new Vector2( 25, 1));
		 fires[26][1] = new Fire(new Vector2( 26,1 ));
		 
		 finish= new Vector2(27, 1);
		 // fresh blank block creation for easier creation with cut copy in future
//      blocks[][] = new Block(new Vector2( , ));
// 		fires[][] = new Fire(new Vector2( , ));
		

	}
	
	private void level_two() {
		width = 85;
		height = 20;
		blocks = new Block[width][height];
		fires = new Fire[width][height];
		skeletons= new Skeleton[width][height];
		
		//draw all as empty block to start 
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
				fires[col][row]= null;
				skeletons[col][row]=null;
			}
		}

		for (int col = 0; col < width; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0));
			/*if ((col > 4)&& ((col!=9) || (col!=15) || (col!=16 )|| (col!=19 )|| (col!=23 )|| (col!=26 )|| (col!=27 )|| (col!=29 )|| (col!= 30) )){
				blocks[col][2] = new Block(new Vector2(col, 2));
			}*/
		}
//make step set starting at block 8 
/*		 blocks[8][1] = new Block(new Vector2(8 ,1 ));
		 blocks[9][1] = new Block(new Vector2(9 , 1));
		 blocks[9][2] = new Block(new Vector2(9 , 2));
		 blocks[10][1] = new Block(new Vector2(10 ,1 ));
		 blocks[10][2] = new Block(new Vector2( 10 ,2 ));
		blocks[10][3] = new Block(new Vector2( 10, 3));*/
		 // fire one
		skeletons[9][1]= new Skeleton(new Vector2(9, 1));
		
		fires[11][1]= new Fire(new Vector2(11,1));
		//jump one
		 blocks[12][1] = new Block(new Vector2( 12, 1));
		 blocks[12][2] = new Block(new Vector2(12 ,2 ));

		
		//fire 2
		fires[13][1]= new Fire(new Vector2(13,1));
		
		//jump2
		
		 blocks[14][1] = new Block(new Vector2( 14, 1));
		 blocks[14][2] = new Block(new Vector2( 14, 2));
		 blocks[14][3] = new Block(new Vector2( 14, 3));
		 blocks[14][4] = new Block(new Vector2( 14, 4));
		 
		 //fire 3
		 fires[15][1] = new Fire(new Vector2(15,1));
		 fires[16][1] = new Fire(new Vector2(16,1));
		 
		 //jump 3 
		 blocks[17][1] = new Block(new Vector2( 17, 1));
		 blocks[17][2] = new Block(new Vector2( 17, 2));
		 blocks[17][3] = new Block(new Vector2( 17, 3));
		 blocks[17][4] = new Block(new Vector2( 17, 4));
		 blocks[18][1] = new Block(new Vector2( 18, 1));
		 blocks[18][2] = new Block(new Vector2( 18, 2));
		 blocks[18][3] = new Block(new Vector2( 18, 3));
		 blocks[18][4] = new Block(new Vector2( 18, 4));
		 
		 fires[19][1] = new Fire(new Vector2(19,1));
		 fires[20][1] = new Fire(new Vector2(20,1));
		 fires[21][1] = new Fire(new Vector2(21,1));
		 
		 blocks[22][1] = new Block(new Vector2( 22, 1));
		 blocks[22][2] = new Block(new Vector2( 22, 2));
		 blocks[22][3] = new Block(new Vector2( 22, 3));
		 blocks[23][1] = new Block(new Vector2( 23, 1));
		 blocks[23][2] = new Block(new Vector2( 23, 2));
		 blocks[23][3] = new Block(new Vector2( 23,3 ));
		 
		 fires[24][1] = new Fire(new Vector2( 24, 1));
		 fires[25][1] = new Fire(new Vector2( 25, 1));
		 fires[26][1] = new Fire(new Vector2( 26,1 ));
		 
		 
		 
		 
		 
		 
		 finish= new Vector2(67, 1);
		
	}
	
	private void level_three() {
		// TODO Auto-generated method stub
		
	}
	private void level_four() {
		// TODO Auto-generated method stub
		
	}
	private void level_five() {
		// TODO Auto-generated method stub
		
	}

	private void level_six() {
		// TODO Auto-generated method stub
		
	}



}
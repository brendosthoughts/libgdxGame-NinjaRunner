package wiser.development.starAssault.model;


import java.util.ArrayList;

import wiser.development.starAssault.model.Block.BlockType;
import wiser.development.starAssault.model.Skeleton.SkeletonType;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class Level {

	private int width;
	private int height;
	private Vector2 finish;
	private Block[][] blocks;
	private Fire[][] fires;
	private Spring[][] springs;
	private Skeleton[][] skeletons;
	private NinjaStars[][] ninja_stars;
	private FireBall[][] fire_balls;
	ArrayList<Skeleton> realSkeletons = new ArrayList<Skeleton>();
	ArrayList<NinjaStars> thrownStars = new ArrayList<NinjaStars>();

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
	public FireBall[][] getFireBalls(){
		return fire_balls;
	}
	public Spring[][] getSprings() {
		// TODO Auto-generated method stub
		return springs;
	}
	public NinjaStars[][] getNinjaStar() {
		// TODO Auto-generated method stub
		return ninja_stars;
	}
	public ArrayList<Skeleton> getSkeletons() {
		return realSkeletons;
	}
	public ArrayList<NinjaStars> getThrownStars(){
		return thrownStars;		
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
	public FireBall getCollidableFireBalls(int x, int y){
		return fire_balls[x][y];
	}
	public NinjaStars getCollidableStars(int x, int y) {
		return ninja_stars[x][y];
	}
	public ArrayList<Skeleton> getCollidableSkeletons(int x, int y) {
		return realSkeletons;
	}
	public Spring getCollidableSprings(int x, int y) {
		return springs[x][y];
	}
	
	public void addThrowingStar(Vector2 initialPosition, Vector2 velocity){
		thrownStars.add(new NinjaStars(new Vector2(initialPosition.x, initialPosition.y), velocity) );		
	}
	public void destroyThrowingStar(int index){
		thrownStars.remove(index);
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
		level_setup(width, height);

		for (int col = 0; col < width; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0));

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
		// all firballs in this level only move vertically
		width = 85;
		height = 30;
		level_setup(width, height);

		for (int col = 0; col < width; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0));
		}

		 // fire one
		skeletons[8][1]= new Skeleton(new Vector2(8, 1), SkeletonType.BACKFORTH);
		realSkeletons.add(skeletons[8][1]);
		
		
		skeletons[10][1]= new Skeleton(new Vector2(10, 1), SkeletonType.BACKFORTH);
		realSkeletons.add(skeletons[10][1]);
		ninja_stars[7][2]=new NinjaStars(new Vector2(7, 2), new Vector2(0,0));
		springs[1][1] = new Spring(new Vector2(1,1));		
		blocks[9][5]= new Block(new Vector2(9,5), BlockType.SPIKE);
		springs[10][1] = new Spring(new Vector2(10,1));
		fires[11][1]= new Fire(new Vector2(11,1));
		//jump one
		 blocks[12][1] = new Block(new Vector2( 12, 1));
		 blocks[12][2] = new Block(new Vector2(12 ,2 ));
		 
		
		//fire 2
		fires[13][1]= new Fire(new Vector2(13,1));
		 fire_balls[1][1] = new FireBall(new Vector2(1,1), new Vector2(0, 1));
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
		 
		 blocks[28][2] = new Block(new Vector2(28 ,2 ));
//		 blocks[28][3] = new Block(new Vector2(28 ,3 ));
		 blocks[29][4] = new Block(new Vector2(29 , 4));
		 blocks[30][5] = new Block(new Vector2( 30, 5));
		 blocks[32][6] = new Block(new Vector2(32 , 6));
		 blocks[33][7] = new Block(new Vector2(33 ,7 ));
		 blocks[34][9] = new Block(new Vector2(34 , 9));
		 blocks[36][11] = new Block(new Vector2( 36,11 ));
		 blocks[37][11] = new Block(new Vector2( 37, 11));
		 
		 blocks[39][12] = new Block(new Vector2( 39, 12));
		 blocks[40][12] = new Block(new Vector2( 40, 12));
		 blocks[41][12] = new Block(new Vector2( 41, 12));
		 blocks[42][12] = new Block(new Vector2( 42,12 ));
		 blocks[43][13] = new Block(new Vector2( 43,13 ));
		 blocks[43][14] = new Block(new Vector2( 43, 14));
		 blocks[44][15] = new Block(new Vector2( 44, 15));
		 blocks[45][15] = new Block(new Vector2( 45, 15));
		 blocks[46][15] = new Block(new Vector2( 46, 15));
			springs[45][1] = new Spring(new Vector2(45,1));
		 blocks[47][15] = new Block(new Vector2(47 , 15));
		 blocks[48][15] = new Block(new Vector2( 48, 15));
		 blocks[49][15] = new Block(new Vector2( 49, 15));
		 blocks[50][15] = new Block(new Vector2(50 ,15 ));
		 blocks[51][15] = new Block(new Vector2(51 ,15 ));
		 blocks[52][15] = new Block(new Vector2(52,15 ));
		 blocks[53][15] = new Block(new Vector2( 53,15 ));
		 blocks[54][15] = new Block(new Vector2( 54, 15));
		 
		 skeletons[52][17]= new Skeleton(new Vector2(52, 17), SkeletonType.LEFT);
		 realSkeletons.add(skeletons[52][17]);

		 finish= new Vector2(53, 15);
		
	}
	
	private void level_three() {
		// all firballs in this level only move vertically
		width = 100;
		height = 100;
		level_setup(width, height);
		blocks[1][1] = new Block(new Vector2(1,1 ));
		blocks[2][1] = new Block(new Vector2(2,1 ));
		blocks[3][1] = new Block(new Vector2(3 ,1));
		blocks[4][1] = new Block(new Vector2(4,1 ));
		blocks[5][2] = new Block(new Vector2(5,2 ));
		blocks[6][2] = new Block(new Vector2( 6,2));
		//sprin and fire pit
		// and ninja stars over area
		blocks[9][3] = new Block(new Vector2(9,3 ));
		blocks[10][3] = new Block(new Vector2(10,3 ));
		blocks[11][3] = new Block(new Vector2(11,3 ));
		/// put in some sort of spikes here
		blocks[12][3] = new Block(new Vector2(12,3 ));
//		upside down spikes here
		blocks[14][3] = new Block(new Vector2(14,3 ));
		blocks[15][3] = new Block(new Vector2(15,3 ));
		blocks[16][3] = new Block(new Vector2(16, 3 ));
		blocks[17][3] = new Block(new Vector2(17,3 ));
		blocks[18][3] = new Block(new Vector2(18,3 ));
		blocks[19][4] = new Block(new Vector2(19,4 ));
		blocks[20][5] = new Block(new Vector2(20,5 ));
		blocks[20][6] = new Block(new Vector2(20, 6 ));
		blocks[21][7] = new Block(new Vector2(21, 7 ));
		blocks[22][7] = new Block(new Vector2(22, 7));
		// were going to switch directions here
//		spring should go here so player an jump gap
		blocks[23][7] = new Block(new Vector2(23, 7 ));
		blocks[23][8] = new Block(new Vector2(23, 8 ));
		blocks[23][9] = new Block(new Vector2(23, 9 ));
		blocks[23][10]= new Block(new Vector2(23, 10 ));
		blocks[23][11]= new Block(new Vector2(23, 11 ));
		blocks[23][12]= new Block(new Vector2(23, 12 ));
		blocks[23][13]= new Block(new Vector2(23, 13 ));
		blocks[23][14]= new Block(new Vector2(23, 14 ));
		blocks[23][15]= new Block(new Vector2(23, 15 ), BlockType.SPIKE);
		blocks[24][15]= new Block(new Vector2(24, 15 ), BlockType.SPIKE);
		blocks[25][15]= new Block(new Vector2(25, 15 ), BlockType.SPIKE);
		
		
		blocks[20][11] = new Block(new Vector2(20,11 ));
		blocks[19][11] = new Block(new Vector2(19,11 ));
		blocks[18][11] = new Block(new Vector2(18,11 ));
		//put skeleton backforth in here
		blocks[17][11] = new Block(new Vector2(17,11 ));
		blocks[16][11] = new Block(new Vector2(16,11 ));
		
		blocks[15][11] = new Block(new Vector2(15,11 ));
		blocks[14][10] = new Block(new Vector2(14, 10 ));
		blocks[13][9] = new Block(new Vector2(13,9 ));
		//insert block of fire along with fireball up down 
		blocks[12][10] = new Block(new Vector2(12, 10 ));
		blocks[11][11] = new Block(new Vector2(11,11 ));
		blocks[11][12] = new Block(new Vector2(11, 12 ));
		blocks[10][13] = new Block(new Vector2(10, 13 ));
		blocks[9][14] = new Block(new Vector2(9,14 ));
		blocks[9][15] = new Block(new Vector2(9,15 ));
		blocks[9][16] = new Block(new Vector2(9,16 ));
		blocks[9][17] = new Block(new Vector2(9,17 ));
		blocks[9][18] = new Block(new Vector2(9,18 ));
		
		blocks[12][14] = new Block(new Vector2(12, 14 ));
		blocks[12][15] = new Block(new Vector2(12, 15 ));
		 finish= new Vector2(53, 15);
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
private void level_setup(int width, int height){
	blocks = new Block[width][height];
	fires = new Fire[width][height];
	skeletons =new Skeleton[width][height];
	ninja_stars =new NinjaStars[width][height];
	springs = new Spring[width][height];
	fire_balls= new FireBall[width][height];
	//draw all as empty block to start 
	for (int col = 0; col < width; col++) {
		for (int row = 0; row < height; row++) {
			blocks[col][row] = null;
			fires[col][row]= null;
			skeletons[col][row]=null;
			ninja_stars[col][row]=null;
			springs[col][row]=null;
			fire_balls[col][row]=null;
		}
	}

	
}





}
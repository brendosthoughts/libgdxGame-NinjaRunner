package wiser.development.starAssault.model;


import java.util.ArrayList;

import wiser.development.starAssault.model.Block.BlockType;
import wiser.development.starAssault.model.Climable.ClimbType;
import wiser.development.starAssault.model.FireBall.FireBallState;
import wiser.development.starAssault.model.Platform.PlatformState;
import wiser.development.starAssault.model.Skeleton.SkeletonType;
import wiser.development.starAssault.model.SpeedPad.SpeedPadType;

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
	private SpeedPad[][] speed_pads;
	private Climable[][] climables;
	private Platform[][] platforms;
	ArrayList<Platform> realPlatforms = new ArrayList<Platform>();
	ArrayList<Skeleton> realSkeletons = new ArrayList<Skeleton>();
	ArrayList<NinjaStars> thrownStars = new ArrayList<NinjaStars>();
	ArrayList<FireBall> realFireBalls = new ArrayList<FireBall>();
	private Vector2 bob_start = new Vector2(3,2);

	
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
	public Climable[][] getClimables(){
		return climables;
	}
	public Fire[][] getFires(){
		return fires;
	}
	public SpeedPad[][] getSpeedPads() {
		return speed_pads;
	}
	public Spring[][] getSprings() {
		return springs;
	}
	public ArrayList<Platform> getPlatforms() {
		return realPlatforms;
	}
	public NinjaStars[][] getNinjaStar() {
		return ninja_stars;
	}
	public ArrayList<Skeleton> getSkeletons() {
		return realSkeletons;
	}
	public ArrayList<FireBall> getFireBalls(){
		return realFireBalls;
		
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
	public Climable getColliableClimbs(int x, int y) {
		return climables[x][y];
	}
	public FireBall getCollidableFireBalls(int x, int y){
		return fire_balls[x][y];
	}
	public SpeedPad getCollidableSpeedPads(int x, int y){
		return speed_pads[x][y];
	}
	public NinjaStars getCollidableStars(int x, int y) {
		return ninja_stars[x][y];
	}
	public ArrayList<Skeleton> getCollidableSkeletons(int x, int y) {
		return realSkeletons;
	}
	public ArrayList<Platform> getCollidablePlatforms(int x, int y) {
		return realPlatforms;
	}	
	public Spring getCollidableSprings(int x, int y) {
		return springs[x][y];
	}
	
	
	//controls the dynamic adding and removing of throwing stars to the level (thrown by bob)
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
		 platforms[8][5] = new Platform(new Vector2(8,5),  1, 7, PlatformState.LEFT);

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
		 level_finish_build();

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
		
		fire_balls[7][3]= new FireBall(new Vector2(7,3), 5f, 4f, FireBallState.UP);
		speed_pads[7][1]= new SpeedPad(new Vector2(7,1), SpeedPadType.RIGHT);
		
		skeletons[8][1]= new Skeleton(new Vector2(8,1), 2f , 2f, SkeletonType.SKELETON , true);
	//	skeletons[8][1]= new Skeleton(new Vector2(8, 1), SkeletonType.REAPER);	
		skeletons[10][1]= new Skeleton(new Vector2(10, 1), SkeletonType.SKELETON);
		ninja_stars[7][2]=new NinjaStars(new Vector2(7, 2), new Vector2(0,0));
		springs[1][1] = new Spring(new Vector2(1,1));		
		blocks[9][5]= new Block(new Vector2(9,5), BlockType.SPIKE);
		springs[10][1] = new Spring(new Vector2(10,1));
		for (int i = 1; i < 15; i++) {
			climables[2][i] = new Climable(new Vector2(2,i),ClimbType.LADDER);
		}
		fires[11][1]= new Fire(new Vector2(11,1));
		//jump one
		 blocks[12][1] = new Block(new Vector2( 12, 1));
		 blocks[12][2] = new Block(new Vector2(12 ,2 ));
		 
		
		//fire 2
		fires[13][1]= new Fire(new Vector2(13,1));
		 fire_balls[1][1] = new FireBall(new Vector2(1,1), new Vector2(1,0));
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
		 
		 skeletons[52][17]= new Skeleton(new Vector2(52, 17), SkeletonType.SKELETON);

		 finish= new Vector2(53, 15);
		 level_finish_build();
		
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
		blocks[7][1] = new Block(new Vector2( 7,1));
		 fires[7][2] = new Fire(new Vector2(7,2));
			blocks[8][1] = new Block(new Vector2( 8,1));		 
		 fires[8][2] = new Fire(new Vector2(8,2));
		// and ninja stars over area
		blocks[9][3] = new Block(new Vector2(9,3 ));
		blocks[10][3] = new Block(new Vector2(10,3 ));
		blocks[11][3] = new Block(new Vector2(11,3 ));
	
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
				
		blocks[23][7] = new Block(new Vector2(23, 7 ));
		blocks[23][8] = new Block(new Vector2(23, 8 ));
		blocks[23][9] = new Block(new Vector2(23, 9 ));
		blocks[23][10]= new Block(new Vector2(23, 10 ));
		blocks[23][11]= new Block(new Vector2(23, 11 ));
		blocks[23][12]= new Block(new Vector2(23, 12 ));
		blocks[23][13]= new Block(new Vector2(23, 13 ));
		blocks[23][14]= new Block(new Vector2(23, 14 ));
		for (int i =23; i<= 55 ; i++){
		blocks[i][15]= new Block(new Vector2(i, 15 ), BlockType.SPIKE_TOP);
		}
		blocks[20][11] = new Block(new Vector2(20,11 ));
		blocks[19][11] = new Block(new Vector2(19,11 ));
		blocks[18][11] = new Block(new Vector2(18,11 ));
		//put skeleton backforth in here
		skeletons[18][12]= new Skeleton(new Vector2(18, 12), SkeletonType.SKELETON);
		blocks[17][11] = new Block(new Vector2(17,11 ));
		blocks[16][11] = new Block(new Vector2(16,11 ));
		
		blocks[15][11] = new Block(new Vector2(15,11 ));
		blocks[14][10] = new Block(new Vector2(14, 10 ));
		blocks[13][9] = new Block(new Vector2(13,9 ));
		//insert block of fire along with fireball up down
		 fires[13][10] = new Fire(new Vector2(13,10));
		 fire_balls[13][12] = new FireBall(new Vector2(13,12), new Vector2(0, 1));
		blocks[12][10] = new Block(new Vector2(12, 10 ));
		blocks[11][11] = new Block(new Vector2(11,11 ));
		blocks[11][12] = new Block(new Vector2(11, 12 ));
		blocks[10][13] = new Block(new Vector2(10, 13 ));
		blocks[9][14] = new Block(new Vector2(9,14 ));
		blocks[9][15] = new Block(new Vector2(9,15 ));
		blocks[9][16] = new Block(new Vector2(9,16 ));
		blocks[9][17] = new Block(new Vector2(9,17 ));
		blocks[9][18] = new Block(new Vector2(9,18 ));
		
	//	blocks[12][14] = new Block(new Vector2(12, 14 ));
		blocks[12][15] = new Block(new Vector2(12, 15 ));
		blocks[13][16] = new Block(new Vector2( 13, 16));
		blocks[14][16] = new Block(new Vector2(14 ,16));
		//insert fire and single fireball 
		
		fire_balls[16][14] = new FireBall(new Vector2(16,14), new Vector2(0, 2));
		fire_balls[15][16] = new FireBall(new Vector2(15,16), new Vector2(0, 1));
		fire_balls[17][14] = new FireBall(new Vector2(17,14), new Vector2(0, 2));
		
		blocks[18][15] = new Block(new Vector2(18 ,15 ));
		blocks[19][15] = new Block(new Vector2(19 ,15 ));
		blocks[20][15] = new Block(new Vector2( 20,15 ));
		blocks[21][16] = new Block(new Vector2(21 ,16 ));
		
		blocks[21][17] = new Block(new Vector2( 21, 17));
		blocks[22][18] = new Block(new Vector2( 22,18 ));
		blocks[23][18] = new Block(new Vector2(23 ,18 ));
		
		springs[23][19] = new Spring(new Vector2(23,19));
		
		blocks[28][20] = new Block(new Vector2( 28, 20));       
		blocks[29][20] = new Block(new Vector2(29 ,20 ));
		blocks[30][20] = new Block(new Vector2(30 ,20 ));
		blocks[31][20] = new Block(new Vector2(31 ,20 ));
		blocks[32][20] = new Block(new Vector2(32 ,20 ));
		blocks[33][20] = new Block(new Vector2(33 ,20 ));
		blocks[34][20] = new Block(new Vector2(34 ,20 ));
		blocks[35][20] = new Block(new Vector2(35 ,20 ));
		blocks[36][20] = new Block(new Vector2(36 ,20 ));
		blocks[37][20] = new Block(new Vector2(37 ,20 ));
		blocks[38][20] = new Block(new Vector2(38 ,20 ));
		blocks[39][20] = new Block(new Vector2(39 ,20 ));
		skeletons[31][21]= new Skeleton(new Vector2(31, 21), SkeletonType.SKELETON);
		skeletons[33][21]= new Skeleton(new Vector2(33, 21), SkeletonType.SKELETON);
		
		blocks[32][24] = new Block(new Vector2(32 ,24 ), BlockType.SPIKE);
		blocks[33][24] = new Block(new Vector2(33 ,24 ), BlockType.SPIKE);
		blocks[34][24] = new Block(new Vector2(34 ,24 ), BlockType.SPIKE);
		blocks[35][24] = new Block(new Vector2(35 ,24 ), BlockType.SPIKE);
		blocks[36][24] = new Block(new Vector2(36 ,24 ), BlockType.SPIKE);
		blocks[37][24] = new Block(new Vector2(37 ,24 ), BlockType.SPIKE);
		blocks[38][24] = new Block(new Vector2(38 ,24 ), BlockType.SPIKE);
		blocks[39][24] = new Block(new Vector2(39 ,24 ), BlockType.SPIKE );
		
		blocks[41][20] = new Block(new Vector2( 41, 20));
		fire_balls[42][20] = new FireBall(new Vector2(42,20), new Vector2(0, 1));		
		blocks[43][20] = new Block(new Vector2( 43,20 ));
		blocks[44][20] = new Block(new Vector2( 44,20 ));
		
		blocks[47][20] = new Block(new Vector2( 47,20 ));
		fire_balls[48][20] = new FireBall(new Vector2(48,20), new Vector2(0, 1));
		blocks[49][20] = new Block(new Vector2( 49,20 ));
		blocks[50][20] = new Block(new Vector2( 50,20 ));
		blocks[51][20] = new Block(new Vector2(51 ,20 ));
		for(int i=20 ; i<=28; i++){
			blocks[52][i] = new Block(new Vector2( 52, i));
		}
		springs[51][21] = new Spring(new Vector2(51,21));
		
		for(int i=53 ; i<=64; i++){
			blocks[i][26] = new Block(new Vector2( i, 26));
		}
		skeletons[58][27]= new Skeleton(new Vector2(58,27), SkeletonType.SKELETON);
		springs[64][27] = new Spring(new Vector2(64,27));
		for(int i=28 ; i<=45; i++){
			blocks[65][i] = new Block(new Vector2( 65, i));
		}
		blocks[62][34] = new Block(new Vector2( 62, 34));
		blocks[61][34] = new Block(new Vector2(61 ,34 ));
		blocks[58][34] = new Block(new Vector2( 58, 34));
		blocks[55][34] = new Block(new Vector2(55 ,34 ));
		blocks[51][34] = new Block(new Vector2(51 , 34));
		blocks[50][34] = new Block(new Vector2(50 , 34));
		blocks[49][34] = new Block(new Vector2(49 ,34 ));
		skeletons[49][35]= new Skeleton(new Vector2(49,35), SkeletonType.SKELETON);
		blocks[48][34] = new Block(new Vector2(48 ,34 ));
		blocks[47][34] = new Block(new Vector2( 47,34 ));
		blocks[46][34] = new Block(new Vector2(46 ,34 ));
		blocks[45][34] = new Block(new Vector2(45 ,34 ));
		fire_balls[43][34] = new FireBall(new Vector2(44,34), new Vector2(0, 1));				
		blocks[40][34] = new Block(new Vector2( 40, 34));
		fire_balls[39][35] = new FireBall(new Vector2(39,35), new Vector2(0, 1));		
		blocks[38][37] = new Block(new Vector2(38 ,37 ));
		blocks[37][37] = new Block(new Vector2(37 ,37 ));
		blocks[36][37] = new Block(new Vector2(36 ,37 ));
		blocks[35][37] = new Block(new Vector2(35 ,37 ));
		blocks[34][37] = new Block(new Vector2(34 ,37 ));
		blocks[33][37] = new Block(new Vector2(33 ,37 ));
		springs[33][38] = new Spring(new Vector2(33, 38));
		for(int i=20 ; i<=50; i++){
			if(i<32 || i>34){
			blocks[i][42] = new Block(new Vector2(i, 42), BlockType.SPIKE);
			blocks[i][43] = new Block(new Vector2(i, 43), BlockType.SPIKE_TOP);
			}
		}
		blocks[34][47] = new Block(new Vector2(34 ,47 ));
		blocks[36][51] = new Block(new Vector2( 36, 51));
		fire_balls[38][51] = new FireBall(new Vector2(38,51), new Vector2(0, 1));				
		blocks[39][52] = new Block(new Vector2( 39, 52));
		blocks[42][52] = new Block(new Vector2( 42, 52));		
		blocks[43][52] = new Block(new Vector2(43 ,52 ));		
		blocks[44][53] = new Block(new Vector2( 44, 53));		
	
		
		springs[44][54] = new Spring(new Vector2( 44, 54));		
		for(int i=54 ; i<=70; i++){
			blocks[45][i] = new Block(new Vector2(45, i));		
		}
		
		blocks[43][64] = new Block(new Vector2( 43, 64));
		
		blocks[42][64] = new Block(new Vector2(42 ,64 ),BlockType.SPIKE_TOP);
		blocks[41][65] = new Block(new Vector2(41 ,65 ),BlockType.SPIKE_TOP);
		blocks[40][65] = new Block(new Vector2( 40, 65),BlockType.SPIKE_TOP);
		blocks[39][66] = new Block(new Vector2( 39,66 ),BlockType.SPIKE_TOP);
		blocks[37][64] = new Block(new Vector2( 38,63),BlockType.SPIKE_TOP);
		blocks[37][63] = new Block(new Vector2( 37, 63));
		blocks[36][63] = new Block(new Vector2(36 ,63 ));
		blocks[35][63] = new Block(new Vector2(35 ,63 ));
		blocks[34][63] = new Block(new Vector2( 34, 63));
		blocks[33][63] = new Block(new Vector2( 34, 63));

		platforms[25][65] = new Platform(new Vector2(25,65), 3, 8, PlatformState.RIGHT);
		blocks[18][63] = new Block(new Vector2(18,63));		
		blocks[17][63] = new Block(new Vector2(17,63));		
		blocks[16][63] = new Block(new Vector2(16,63));		
		
		fires[15][62] = new Fire(new Vector2( 15, 62));		
		fires[14][62] = new Fire(new Vector2( 14, 62));		
		fires[13][62] = new Fire(new Vector2( 13,62 ));	
		blocks[15][61] = new Block(new Vector2( 15, 61));		
		blocks[14][61] = new Block(new Vector2( 14, 61));		
		blocks[13][61] = new Block(new Vector2( 13,61 ));	
		blocks[12][62] = new Block(new Vector2( 12, 62));	
		blocks[12][63] = new Block(new Vector2( 12, 63));
		blocks[12][64] = new Block(new Vector2( 12, 64));		
		blocks[11][64] = new Block(new Vector2( 11, 64));	
		blocks[10][64] = new Block(new Vector2( 10, 64));
		
		fire_balls[20][74] = new FireBall(new Vector2( 20, 74), new Vector2(0, 8));
		fire_balls[22][82] = new FireBall(new Vector2(22 ,84 ), new Vector2(0, 2 ));
		fire_balls[22][64] = new FireBall(new Vector2( 22, 64), new Vector2( 0, 2));
		fire_balls[25][74] = new FireBall(new Vector2(8 ,0 ), new Vector2( 0,8));
		fire_balls[28][82] = new FireBall(new Vector2( 28,82 ), new Vector2( 0,1 ));
		fire_balls[27][65] = new FireBall(new Vector2(27 ,65 ), new Vector2( 0,1 ));
		fire_balls[30][74] = new FireBall(new Vector2(30,74 ), new Vector2(0,1));
		
		
		for(int i=0 ; i<=10; i++){
			blocks[i][65] = new Block(new Vector2(i, 65), BlockType.SPIKE_TOP);
		}
		blocks[11][67] = new Block(new Vector2( 11,67 ));		
		blocks[12][71] = new Block(new Vector2( 12,71 ));		
		blocks[15][74] = new Block(new Vector2( 15,74 ));		
		blocks[17][77] = new Block(new Vector2( 17, 77));		
		platforms[20][80] = new Platform(new Vector2(20,80), 3, 5, PlatformState.RIGHT);
		blocks[26][81] = new Block(new Vector2( 26, 81));		
		blocks[27][81] = new Block(new Vector2( 27, 81));	
		
		platforms[30][81] = new Platform(new Vector2(30,81), 2f, 2f, PlatformState.LEFT);
		speed_pads[34][85]= new SpeedPad(new Vector2(34, 85), SpeedPadType.RIGHT);
		
		blocks[42][80] = new Block(new Vector2( 42, 80));	
		blocks[43][80] = new Block(new Vector2( 43, 80));		
		blocks[44][80] = new Block(new Vector2( 44, 80));		
		blocks[45][80] = new Block(new Vector2( 45, 80));		
		blocks[46][80] = new Block(new Vector2( 46, 80));		
		blocks[47][80] = new Block(new Vector2( 47, 80));		
		blocks[48][80] = new Block(new Vector2( 48,80 ));		
		blocks[49][81] = new Block(new Vector2( 49,81 ));		
		
		platforms[55][81] = new Platform(new Vector2(50,81), 3f, 5f, PlatformState.RIGHT);
		blocks[56][80] = new Block(new Vector2(56 ,80 ));	
		platforms[62][80] = new Platform(new Vector2(62,79), 3f, 5f, PlatformState.LEFT);
		
		blocks[68][81] = new Block(new Vector2(68 ,81 ));		
		blocks[70][79] = new Block(new Vector2(70 , 79));		
		blocks[73][77] = new Block(new Vector2(73 ,77 ));		
		
		blocks[70][75] = new Block(new Vector2( 70, 75));		
		blocks[71][75] = new Block(new Vector2( 71, 75));	

		skeletons[73][75]= new Skeleton(new Vector2(73,75), SkeletonType.SKELETON);
		blocks[72][75] = new Block(new Vector2( 72, 75));		
		blocks[73][75] = new Block(new Vector2( 73, 75));		
		blocks[74][75] = new Block(new Vector2( 74, 75));		
		blocks[75][75] = new Block(new Vector2( 75,75 ));		
		blocks[76][76] = new Block(new Vector2( 76,76 ));	
		
		blocks[79][76] = new Block(new Vector2(79, 76));	
		blocks[80][76] = new Block(new Vector2(80,76), BlockType.SPIKE_TOP);
		
		blocks[81][76] = new Block(new Vector2( 81, 76));	
		blocks[82][76] = new Block(new Vector2(82, 76), BlockType.SPIKE_TOP);
		blocks[83][76] = new Block(new Vector2(83, 76), BlockType.SPIKE_TOP);
		blocks[84][76] = new Block(new Vector2( 84,76 ));	
		blocks[85][76] = new Block(new Vector2( 85,76 ));
		blocks[86][76] = new Block(new Vector2( 86,76 ));
		blocks[87][76] = new Block(new Vector2( 87,76 ));
		 finish= new Vector2(86, 76);
		
		
		
		
		//		blocks[][] = new Block(new Vector2( , ));		
		
//		blocks[][] = new Block(new Vector2( , ));
		 finish= new Vector2(86, 76);
		 level_finish_build();
	}
	private void level_four() {
		width=80;
		height=150;
		level_setup(width, height);
		bob_start.x=5;
		bob_start.y=2;
		 
		blocks[4][1] = new Block(new Vector2( 4, 1));	
		blocks[5][1] = new Block(new Vector2( 5,1 ));	
		blocks[6][1] = new Block(new Vector2( 6,1 ));	
		for (int i = 2; i < 16; i++) {
			climables[5][i] = new Climable(new Vector2(5,i),ClimbType.LADDER);
			if (i%3==0){
				if(i%6==0){
					fire_balls[5][i] = new FireBall(new Vector2(5,i), 2f , 3f, FireBallState.RIGHT);
				}else{
					fire_balls[5][i] = new FireBall(new Vector2(5,i), 2f , 3f, FireBallState.LEFT);
				}
			}
		}
		
		blocks[7][15] = new Block(new Vector2( 7, 15));		
		blocks[8][15] = new Block(new Vector2( 8, 15));		
		blocks[9][15] = new Block(new Vector2(9 ,15 ));		
		blocks[10][15] = new Block(new Vector2( 10,15 ));		
		
		for (int i = 17; i < 26; i++) {
			climables[13][i] = new Climable(new Vector2(13,i),ClimbType.LADDER);
			if(i%2==0){
				if(i%4==0){
				fire_balls[13][i] = new FireBall(new Vector2(13,i), 2f , 3f, FireBallState.LEFT);
				}else{
					fire_balls[13][i] = new FireBall(new Vector2(13,i), 2f , 3f, FireBallState.RIGHT);
				}
			}
		}
		
		blocks[11][28] = new Block(new Vector2(11,28));	
		blocks[10][28] = new Block(new Vector2(10,28));	
		blocks[9][28] = new Block(new Vector2(9,28));	
		skeletons[9][29]= new Skeleton(new Vector2(9,29), 2f , 2f, SkeletonType.SKELETON , true);
		blocks[8][28] = new Block(new Vector2( 8, 28));	
		blocks[7][28] = new Block(new Vector2( 7, 28));	
		
		for (int i = 30; i < 55; i++) {
			climables[5][i] = new Climable(new Vector2(5,i),ClimbType.LADDER);
			if(i%4==0){
				fire_balls[5][i] = new FireBall(new Vector2(5,i), 2f , 3f, FireBallState.LEFT);
			}
			if(i%6==0){
				blocks[6][i] = new Block(new Vector2( 6,i));	
				blocks[7][i] = new Block(new Vector2( 7,i));	
				if(i%3==0){
					blocks[3][i] = new Block(new Vector2( 3,i));	
					blocks[4][i] = new Block(new Vector2( 4,i));		
				}
			}
		}
		fire_balls[5][47] = new FireBall(new Vector2(5,47), 2f , 8f, FireBallState.UP);
		platforms[8][55] = new Platform(new Vector2(8,55),  2, 4, PlatformState.RIGHT);
		platforms[14][54] = new Platform(new Vector2(14,54),  4, 8, PlatformState.LEFT);
		fire_balls[10][55] = new FireBall(new Vector2(10,55), 2f , 3f, FireBallState.LEFT);
		blocks[21][55] = new Block(new Vector2( 21,55 ));		
		blocks[22][55] = new Block(new Vector2( 22, 55));		
		blocks[23][55] = new Block(new Vector2( 23,55 ));		
		fire_balls[24][55] = new FireBall(new Vector2(24,55), 3f , 2f, FireBallState.UP);
		fire_balls[25][55] = new FireBall(new Vector2(25,55), 5f , 4f, FireBallState.DOWN);
		fire_balls[26][55] = new FireBall(new Vector2(26,55), 4f , 3f, FireBallState.UP);
		fire_balls[27][55] = new FireBall(new Vector2(27,55), 2f , 1f, FireBallState.UP);
		
		blocks[28][57] = new Block(new Vector2(28,57));		
		blocks[29][57] = new Block(new Vector2(29,57));	
		blocks[30][57] = new Block(new Vector2(30,57));
		skeletons[31][58]= new Skeleton(new Vector2(31,58), 2f , 1.5f, SkeletonType.SKELETON , true);
		blocks[31][57] = new Block(new Vector2(31,57));
		blocks[32][57] = new Block(new Vector2(32,57));
		
		for (int i = 59; i < 76; i++) {
			climables[35][i] = new Climable(new Vector2(35,i),ClimbType.LADDER);
			if(i%3==0){
				fire_balls[35][i] = new FireBall(new Vector2(35,i), 2f , 3f, FireBallState.LEFT);
			}
		}
		platforms[41][78] = new Platform(new Vector2(41,78),  2, 4, PlatformState.RIGHT);
		
		for (int i = 80; i < 97; i++) {
			climables[46][i] = new Climable(new Vector2(46,i),ClimbType.LADDER);
			if(i%3==0){
				fire_balls[46][i] = new FireBall(new Vector2(46,i), 2f , 3f, FireBallState.LEFT);
			}
		}
		for (int i = 96; i < 110; i++) {
			climables[49][i] = new Climable(new Vector2(49,i),ClimbType.LADDER);
			if(i%4==0 && i>101){
				fire_balls[49][i] = new FireBall(new Vector2(49,i), 2f , 3f, FireBallState.LEFT);
			}
		}
		for (int i = 109; i < 126; i++) {
			climables[46][i] = new Climable(new Vector2(46,i),ClimbType.LADDER);
			if(i%4==0 && i>115){
				fire_balls[46][i] = new FireBall(new Vector2(46,i), 2f , 3f, FireBallState.LEFT);
			}
		}
		
		platforms[49][127] = new Platform(new Vector2(49,127),  2, 4, PlatformState.RIGHT);
		
		blocks[55][129] = new Block(new Vector2( 55, 129));		
		blocks[56][129] = new Block(new Vector2( 56, 129));		
		blocks[57][129] = new Block(new Vector2( 57, 129));		
		blocks[58][129] = new Block(new Vector2( 58, 129));		
		blocks[59][129] = new Block(new Vector2( 59, 129));		
		
		
		

		 finish= new Vector2(58, 130);
		 level_finish_build();
		
	}
	private void level_five() {
		
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
	climables= new Climable[width][height];
	speed_pads= new SpeedPad[width][height];
	platforms =new Platform[width][height];
	//draw all as empty block to start 
	for (int col = 0; col < width; col++) {
		for (int row = 0; row < height; row++) {
			blocks[col][row] = null;
			fires[col][row]= null;
			skeletons[col][row]=null;
			ninja_stars[col][row]=null;
			springs[col][row]=null;
			fire_balls[col][row]=null;
			speed_pads[col][row]=null;
			platforms[col][row]=null;
		}
	}

	
}

private void level_finish_build(){
// take all moving object and add them to Array Lists 

	for(int row=0; row<height; row++){
		for(int col=0 ; col<width; col++){
			if(fire_balls[col][row] != null){
				realFireBalls.add(fire_balls[col][row]);	
			}
			if(platforms[col][row]!= null){
				realPlatforms.add(platforms[col][row]);			
			}
			if(skeletons[col][row]!= null){
				realSkeletons.add(skeletons[col][row]);			
			}
			
		}
	}
}



public Vector2 getBobStart(){
	return bob_start;
}

}
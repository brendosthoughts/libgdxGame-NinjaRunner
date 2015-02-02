package wiser.development.NinjaRunner.model;


import java.util.ArrayList;

import wiser.development.NinjaRunner.model.Block.BlockType;
import wiser.development.NinjaRunner.model.Climable.ClimbType;
import wiser.development.NinjaRunner.model.FireBall.FireBallState;
import wiser.development.NinjaRunner.model.Platform.PlatformState;
import wiser.development.NinjaRunner.model.Skeleton.SkeletonType;
import wiser.development.NinjaRunner.model.SpeedPad.SpeedPadType;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;

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
//	float[] bkg_color = new float[4]{0.7,}; // rgb & alpha for background color
//
//	bgk_color[0] =0.7f;
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
			case 7:
				level_seven();
				break;
			case 8:
				level_eight();
				break;
			case 9:
				level_nine();
				break;
			case 10: 
				level_ten();
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
		 platforms[8][5] = new Platform(new Vector2(8,5),  2, 5, PlatformState.RIGHT);
		 platforms[13][6] = new Platform(new Vector2(13,6),  2, 5, PlatformState.LEFT);
		 
		 
		 
		 fires[11][1]= new Fire(new Vector2(11,1));
		//jump oe
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
		
//		skeletons[8][1]= new Skeleton(new Vector2(8,1), 2f , 2f, SkeletonType.SKELETON , true);
	//	skeletons[8][3]= new Skeleton(new Vector2(8, 3),2f, 2f, SkeletonType.REAPER_HOVER, true, 2);	
		skeletons[9][1]= new Skeleton(new Vector2(9,1), 3f , 2f, SkeletonType.SKELETON , true,2);
		skeletons[10][1]= new Skeleton(new Vector2(10, 1), SkeletonType.SKELETON);
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
		skeletons[9][29]= new Skeleton(new Vector2(9,29), 2f , 2f, SkeletonType.SKELETON , true,2);
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
		skeletons[31][58]= new Skeleton(new Vector2(31,58), 2f , 1.5f, SkeletonType.SKELETON , true,1);
		blocks[31][57] = new Block(new Vector2(31,57));
		blocks[32][57] = new Block(new Vector2(32,57));
		
		for (int i = 59; i < 77; i++) {
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
		width=170;
		height=80;
		level_setup(width, height);
		bob_start.x=5;
		bob_start.y=3;
		
		blocks[4][2] = new Block(new Vector2( 4, 2));	
		blocks[5][2] = new Block(new Vector2( 5,2 ));	
		blocks[6][2] = new Block(new Vector2( 6,2 ));	
			
		blocks[10][3] = new Block(new Vector2( 10,3 ));	
		platforms[13][3] = new Platform(new Vector2(13,3),  2, 4, PlatformState.LEFT);
		blocks[17][4] = new Block(new Vector2( 17,4 ));	
		blocks[18][4] = new Block(new Vector2( 18,4 ));	
		fire_balls[20][5] = new FireBall(new Vector2(20,5), 2f , 3f, FireBallState.UP);
		
		platforms[22][5] = new Platform(new Vector2(22,5),  2, 4, PlatformState.RIGHT);
		platforms[27][5] = new Platform(new Vector2(27,5),  2, 4, PlatformState.LEFT);
		blocks[33][6] = new Block(new Vector2( 33, 6));		
		blocks[34][6] = new Block(new Vector2(34 ,6 ));
		blocks[38][6] = new Block(new Vector2( 38, 6));
		blocks[39][6] = new Block(new Vector2( 39, 6));		
		
		blocks[43][6] = new Block(new Vector2( 43, 6));	
		blocks[43][7] = new Block(new Vector2( 43,7 ), BlockType.SPIKE_TOP);		
		
		platforms[43][6] = new Platform(new Vector2(43,6),  2, 3, PlatformState.RIGHT);
		platforms[49][6] = new Platform(new Vector2(49,6),  2, 3, PlatformState.LEFT);
		
		blocks[53][7] = new Block(new Vector2( 53, 7));		
		blocks[54][7] = new Block(new Vector2( 54, 7));		
		blocks[55][7] = new Block(new Vector2( 55,7 ), BlockType.SPIKE_TOP);	
		blocks[56][8] = new Block(new Vector2( 56,8 ), BlockType.SPIKE_TOP);	
		blocks[57][9] = new Block(new Vector2( 57,9 ), BlockType.SPIKE_TOP);	
		blocks[58][8] = new Block(new Vector2( 58,8 ), BlockType.SPIKE_TOP);	
		blocks[59][6] = new Block(new Vector2( 59,6 ), BlockType.SPIKE_TOP);	
		
		blocks[60][6] = new Block(new Vector2(60 ,6 ));		
		blocks[61][6] = new Block(new Vector2( 61, 6));		
		blocks[62][6] = new Block(new Vector2( 62, 6));		
		
		platforms[67][7] = new Platform(new Vector2(67,7),  3, 5, PlatformState.LEFT);
		platforms[75][8] = new Platform(new Vector2(75,8),  3, 5, PlatformState.RIGHT);
		
		blocks[79][9] = new Block(new Vector2( 79, 9));		
		blocks[80][9] = new Block(new Vector2( 780, 9));		
		blocks[81][9] = new Block(new Vector2( 81, 9));		
		
		blocks[82][9] = new Block(new Vector2( 82,9 ), BlockType.SPIKE_TOP);		
		blocks[83][10] = new Block(new Vector2( 83,10 ), BlockType.SPIKE_TOP);		
		blocks[84][11] = new Block(new Vector2( 84, 11), BlockType.SPIKE_TOP);		
		blocks[85][9] = new Block(new Vector2(85,9), BlockType.SPIKE_TOP);		
		
		blocks[86][9] = new Block(new Vector2( 86, 9));                                 
		blocks[87][9] = new Block(new Vector2(87 ,9 ));		
		
		blocks[89][12] =new Block(new Vector2(89 ,12 ));		
		blocks[91][14] = new Block(new Vector2(91 ,14 ));		
		blocks[94][17] = new Block(new Vector2( 94,17 ));		
		blocks[97][20] = new Block(new Vector2(97 ,20 ));		
		
		blocks[100][21] = new Block(new Vector2( 100,21 ));		
		blocks[100][22] = new Block(new Vector2( 100,22 ), BlockType.SPIKE_TOP);		
		
		platforms[102][21] = new Platform(new Vector2(102,21),  3, 5, PlatformState.LEFT);
		blocks[105][21] = new Block(new Vector2( 105, 21));		
		blocks[105][22] = new Block(new Vector2(105 ,22 ), BlockType.SPIKE_TOP);		
		
		blocks[112][22] = new Block(new Vector2( 112, 22));		
		blocks[112][23] = new Block(new Vector2( 112,23 ), BlockType.SPIKE_TOP);		
		
		platforms[115][22] = new Platform(new Vector2(113,22),  3, 5, PlatformState.RIGHT);
		
		blocks[116][22] = new Block(new Vector2(116 , 22));		
		blocks[116][23] = new Block(new Vector2(116 ,23 ), BlockType.SPIKE_TOP);		
		
		
		blocks[119][22] = new Block(new Vector2( 119, 22));		
		blocks[120][23] = new Block(new Vector2( 120,23 ), BlockType.SPIKE_TOP);		
		
		blocks[121][22] = new Block(new Vector2( 121, 22));
		blocks[122][22] = new Block(new Vector2( 122, 22));		
		blocks[123][22] = new Block(new Vector2( 123, 22));		
		
		platforms[129][23] = new Platform(new Vector2(129,23),  4, 5, PlatformState.LEFT);
		blocks[127][24] = new Block(new Vector2( 127,24 ), BlockType.SPIKE_TOP);		
		blocks[128][24] = new Block(new Vector2(128 ,24 ));		
		blocks[129][24] = new Block(new Vector2(129 ,24 ));		
		blocks[130][24] = new Block(new Vector2(130 ,24 ));		
		skeletons[130][25]= new Skeleton(new Vector2(130,25), 2f , 1.5f, SkeletonType.SKELETON , true,1);
		blocks[131][24] = new Block(new Vector2(131 ,24 ));		
		blocks[132][24] = new Block(new Vector2(132,24 ));		
		blocks[133][24] = new Block(new Vector2(133 ,24 ));		
		blocks[134][24] = new Block(new Vector2( 134,24 ), BlockType.SPIKE_TOP);		
		
		platforms[139][24] = new Platform(new Vector2(139,24),  2, 5, PlatformState.RIGHT);
		
		blocks[140][24] = new Block(new Vector2( 140, 24));		
		blocks[140][25] = new Block(new Vector2( 140,25 ), BlockType.SPIKE_TOP);		
		
		blocks[143][24] = new Block(new Vector2( 143, 24));		
		blocks[143][25] = new Block(new Vector2( 143,25 ), BlockType.SPIKE_TOP);		
		
		blocks[147][26] = new Block(new Vector2( 147, 26));		
		blocks[148][26] = new Block(new Vector2( 148, 26));		
		blocks[149][26] = new Block(new Vector2( 149,26 ));		
		blocks[150][26] = new Block(new Vector2(150 ,26 ));		
		blocks[151][26] = new Block(new Vector2( 151,26 ));		
		blocks[152][26] = new Block(new Vector2( 152,26 ));		
		blocks[153][26] = new Block(new Vector2( 153,26 ));		
		blocks[154][26] = new Block(new Vector2( 154,26 ));		
		
		//fire_balls[][] = new FireBall(new Vector2(,), 2f , 3f, FireBallState.);
		
		//blocks[][] = new Block(new Vector2( , ));		
		
		//blocks[][] = new Block(new Vector2( , ), BlockType.SPIKE_TOP);		
		
		
		 finish= new Vector2(152, 27);
		 level_finish_build();
	}

	private void level_six() {
		width=135;
		height=100;
		level_setup(width, height);
		bob_start.x=4;
		bob_start.y=4;
		blocks[1][1] = new Block(new Vector2(1 , 1));
		blocks[2][1] = new Block(new Vector2(2 , 1));
		blocks[3][1] = new Block(new Vector2( 3,1 ));
		blocks[4][1] = new Block(new Vector2( 4,1 ));
		blocks[5][1] = new Block(new Vector2( 5,1 ));
		blocks[6][1] = new Block(new Vector2( 6, 1));
		
		blocks[11][2] = new Block(new Vector2( 11,2 ));
		blocks[12][2] = new Block(new Vector2( 12, 2));
		blocks[13][2] = new Block(new Vector2( 13, 2));
		blocks[14][2] = new Block(new Vector2( 14, 2));
		blocks[15][2] = new Block(new Vector2( 15, 2));
		
		blocks[19][4] = new Block(new Vector2( 19,4 ));
		blocks[20][4] = new Block(new Vector2( 20,4 ));
		blocks[21][4] = new Block(new Vector2( 21,4 ));
		blocks[22][4] = new Block(new Vector2( 22, 4));
		blocks[23][4] = new Block(new Vector2( 23, 4));
		

		fire_balls[20][5] = new FireBall(new Vector2(20,5), 2f , 3f, FireBallState.UP);
		skeletons[21][5]= new Skeleton(new Vector2(21,5), 2f , 1.5f, SkeletonType.SKELETON , true,1);
		
		blocks[19][4] = new Block(new Vector2( 19,4 ));
		blocks[20][4] = new Block(new Vector2( 20,4 ));
		blocks[21][4] = new Block(new Vector2( 21,4 ));
		blocks[22][4] = new Block(new Vector2( 22, 4));
		blocks[23][4] = new Block(new Vector2( 23, 4));
		
		blocks[29][4] = new Block(new Vector2(29 ,4 ));		
		blocks[30][4] = new Block(new Vector2(30 ,4 ));		
		blocks[31][4] = new Block(new Vector2(31 ,4 ));		
		blocks[32][4] = new Block(new Vector2(32 ,4 ));		
		blocks[33][4] = new Block(new Vector2(33 ,4 ));		
		blocks[34][4] = new Block(new Vector2(34 ,4 ));		
		
		blocks[36][9] = new Block(new Vector2( 36,9 ));		
		blocks[37][9] = new Block(new Vector2(37 ,9 ));		
		blocks[38][9] = new Block(new Vector2( 38,9 ));		
		blocks[39][9] = new Block(new Vector2( 39,9 ));		
		blocks[40][9] = new Block(new Vector2( 40,9 ));		
		blocks[41][9] = new Block(new Vector2( 41,9 ));		
		blocks[42][9] = new Block(new Vector2( 42,9 ));		
		blocks[43][9] = new Block(new Vector2( 43,9 ));
		blocks[44][9] = new Block(new Vector2( 44,9 ));
		
		
		blocks[49][11] = new Block(new Vector2( 49, 11));		
		blocks[50][11] = new Block(new Vector2( 50, 11));		
		blocks[51][11] = new Block(new Vector2( 51, 11));		
		blocks[52][11] = new Block(new Vector2( 52,11 ));		
		blocks[53][11] = new Block(new Vector2( 53, 11));		
		blocks[54][11] = new Block(new Vector2( 54,11 ));		
		blocks[55][11] = new Block(new Vector2(55 , 11));		
		blocks[56][11] = new Block(new Vector2(56 ,11 ));		
		blocks[57][11] = new Block(new Vector2( 57,11 ));		
		blocks[58][11] = new Block(new Vector2( 58,11 ));		
		
		springs[57][12] = new Spring(new Vector2(57,12));	
		
		platforms[62][21] = new Platform(new Vector2(62,21),  3, 3, PlatformState.LEFT);
		
		blocks[66][22] = new Block(new Vector2( 66, 22));		
		blocks[67][22] = new Block(new Vector2( 67, 22));		
		blocks[68][22] = new Block(new Vector2( 68,22 ));		
		blocks[69][22] = new Block(new Vector2(69 ,22 ));		
		blocks[70][22] = new Block(new Vector2( 70,22 ));		
		blocks[71][22] = new Block(new Vector2( 71, 22));		
		blocks[72][22] = new Block(new Vector2(72 ,22 ));		
		
		blocks[75][25] = new Block(new Vector2(75 , 25));		
		blocks[76][25] = new Block(new Vector2(76 ,25 ));		
		blocks[77][25] = new Block(new Vector2( 77,25 ));		
		blocks[78][25] = new Block(new Vector2( 78,25 ));		
		springs[78][26] = new Spring(new Vector2(78,26));	
		
		platforms[75][34] = new Platform(new Vector2(75,34),  3, 3, PlatformState.LEFT);
		
		blocks[73][34] = new Block(new Vector2(73 ,34 ));		
		blocks[72][34] = new Block(new Vector2(72 ,34 ));		
		blocks[71][34] = new Block(new Vector2(71 ,34 ));	
		blocks[70][34] = new Block(new Vector2(70 ,34 ));		
		blocks[69][34] = new Block(new Vector2( 69,34 ));		
		blocks[68][34] = new Block(new Vector2( 68,34 ));		
		blocks[67][34] = new Block(new Vector2( 67,34 ));		
		blocks[66][34] = new Block(new Vector2( 66,34 ));		
		
		//put gost below this 
		blocks[67][35] = new Block(new Vector2(67 ,35 ));		
		blocks[66][35] = new Block(new Vector2(66 ,35 ));		
		blocks[64][35] = new Block(new Vector2(64 ,35 ));		
		blocks[63][35] = new Block(new Vector2(63 ,35 ));		
		blocks[61][35] = new Block(new Vector2(61 ,35 ));		
		

		blocks[67][39] = new Block(new Vector2(67 ,39 ), BlockType.SPIKE);		
		blocks[66][39] = new Block(new Vector2(66 ,39 ), BlockType.SPIKE);		
		blocks[65][39] = new Block(new Vector2(65 ,39 ), BlockType.SPIKE);		
		blocks[64][39] = new Block(new Vector2(64 ,39 ), BlockType.SPIKE);		
		blocks[63][39] = new Block(new Vector2(63 ,39 ), BlockType.SPIKE);		
		blocks[62][39] = new Block(new Vector2(62 ,39 ), BlockType.SPIKE);		
		blocks[61][39] = new Block(new Vector2(61 ,39 ), BlockType.SPIKE);		
		
		
		
		platforms[49][33] = new Platform(new Vector2(53,33),  3, 3, PlatformState.LEFT);	
		
		blocks[46][35] = new Block(new Vector2( 46,35 ));		
		blocks[45][35] = new Block(new Vector2(45 ,35 ));		
		blocks[44][35] = new Block(new Vector2( 44,35 ));		
		blocks[43][35] = new Block(new Vector2(43 , 35));		
		blocks[42][35] = new Block(new Vector2(42 ,35 ));		
		
		springs[42][36] = new Spring(new Vector2(42,36));
		
		blocks[45][43] = new Block(new Vector2( 45, 43));	
		blocks[46][43] = new Block(new Vector2( 46, 43));	
		springs[45][44] = new Spring(new Vector2(45,44));
		
		platforms[47][52] = new Platform(new Vector2(47,52),  1.5f, 3, PlatformState.LEFT);	
		
		blocks[52][54] = new Block(new Vector2( 52, 54));		
		blocks[53][54] = new Block(new Vector2( 53, 54));	
		springs[52][55] = new Spring(new Vector2(52,55));
		

		blocks[55][64] = new Block(new Vector2( 55, 64));
		blocks[56][64] = new Block(new Vector2( 56, 64));		
		springs[55][65] = new Spring(new Vector2(55,65));
		
		platforms[59][73] = new Platform(new Vector2(59,73),  1.5f, 3, PlatformState.RIGHT);	
		platforms[64][74] = new Platform(new Vector2(64,74),  1.5f, 3, PlatformState.LEFT);	
		
		blocks[68][75] = new Block(new Vector2(68 ,75 ));		
		blocks[69][75] = new Block(new Vector2(69 ,75 ));		
		blocks[70][75] = new Block(new Vector2(70 ,75 ));		
		blocks[71][75] = new Block(new Vector2( 71,75 ));		
		blocks[72][75] = new Block(new Vector2(72 ,75 ));		
		blocks[73][75] = new Block(new Vector2(73 ,75 ));		
		
		springs[73][76] = new Spring(new Vector2(73,76));
		
		
		platforms[78][86] = new Platform(new Vector2(78,86),  1.5f, 4.5f, PlatformState.RIGHT);	
		platforms[86][87] = new Platform(new Vector2(86,87),  1.5f, 4.5f, PlatformState.LEFT);	
		
		blocks[91][87] = new Block(new Vector2( 91, 87));		
		blocks[94][87] = new Block(new Vector2( 94,87 ));		
		
		
		blocks[95][86] = new Block(new Vector2( 95,86 ));		
		blocks[95][89] = new Block(new Vector2(95 ,90f ), BlockType.SPIKE);		
		
		blocks[96][85] = new Block(new Vector2( 96, 85));		
		blocks[96][88] = new Block(new Vector2(96 ,89f ), BlockType.SPIKE);		
		
		blocks[97][84] = new Block(new Vector2( 97, 84));		
		blocks[97][87] = new Block(new Vector2(97 , 88f), BlockType.SPIKE);		
		
		blocks[98][83] = new Block(new Vector2(98 , 83));		
		blocks[98][86] = new Block(new Vector2( 98,87f ), BlockType.SPIKE);		
		
		blocks[99][82] = new Block(new Vector2( 99, 82));		
		blocks[99][85] = new Block(new Vector2(99 ,86f ), BlockType.SPIKE);		
		
		blocks[100][81] = new Block(new Vector2( 100, 81));		
		blocks[100][84] = new Block(new Vector2(100 ,85f ), BlockType.SPIKE);		
		
		blocks[101][80] = new Block(new Vector2( 101,80 ));		
		blocks[101][83] = new Block(new Vector2( 101, 84f), BlockType.SPIKE);		
		
		blocks[102][79] = new Block(new Vector2( 102, 79));		
		blocks[102][82] = new Block(new Vector2(102 , 83f), BlockType.SPIKE);		
		
		blocks[103][78] = new Block(new Vector2( 103,78 ));		
		blocks[103][81] = new Block(new Vector2( 103,82f ), BlockType.SPIKE);		
		
		blocks[104][77] = new Block(new Vector2(104 ,77 ));		
		blocks[104][80] = new Block(new Vector2(104 , 81f), BlockType.SPIKE);		
		
		blocks[105][77] = new Block(new Vector2(105 ,77 ));		
		blocks[106][80] = new Block(new Vector2(106 , 81f), BlockType.SPIKE);		
		blocks[107][77] = new Block(new Vector2( 107,77 ));		
		blocks[108][80] = new Block(new Vector2( 108, 81f), BlockType.SPIKE);		
		blocks[109][77] = new Block(new Vector2(109 ,77 ));		
		blocks[110][80] = new Block(new Vector2(110 , 81f), BlockType.SPIKE);		
		blocks[111][77] = new Block(new Vector2(111 ,77 ));		
		blocks[112][80] = new Block(new Vector2(112 , 81f), BlockType.SPIKE);		
		blocks[113][77] = new Block(new Vector2( 113,77 ));		
		blocks[114][77] = new Block(new Vector2(114 ,77 ));		
		blocks[115][77] = new Block(new Vector2(115 ,77 ));		
		blocks[116][77] = new Block(new Vector2(116 ,77 ));		
		blocks[117][77] = new Block(new Vector2(117 ,77 ));		
		
		
		
		
		 finish= new Vector2(116, 78);
		 level_finish_build();		
	}
	
	private void level_seven() {
		width=190;
		height=100;
		level_setup(width, height);
		bob_start.x=4;
		bob_start.y=4;
		
		blocks[3][3] = new Block(new Vector2( 3,3 ));
		blocks[4][3] = new Block(new Vector2(4 ,3 ));
		blocks[5][3] = new Block(new Vector2( 5, 3));
		blocks[6][3] = new Block(new Vector2( 6, 3));
		blocks[7][3] = new Block(new Vector2( 7, 3));
		blocks[8][3] = new Block(new Vector2( 8,3 ), BlockType.SPIKE_TOP);		
		blocks[9][4] = new Block(new Vector2(9 ,4 ), BlockType.SPIKE_TOP);		
		blocks[10][4] = new Block(new Vector2( 10,4 ), BlockType.SPIKE_TOP);		
		blocks[11][3] = new Block(new Vector2( 11,3 ), BlockType.SPIKE_TOP);		
		
		blocks[12][2] = new Block(new Vector2( 12,2 ));		
		springs[12][3] = new Spring(new Vector2(12,3));		
		
		blocks[14][9] = new Block(new Vector2( 14, 9));		
		springs[14][10] = new Spring(new Vector2(14,10));		
		
		blocks[17][16] = new Block(new Vector2( 17,16 ));		
		springs[17][17] = new Spring(new Vector2(17,17));		
		
		blocks[20][23] = new Block(new Vector2(20 ,23 ));		
		springs[20][24] = new Spring(new Vector2(20,24));		
		
		blocks[23][30] = new Block(new Vector2(23 ,30 ));		
		springs[23][31] = new Spring(new Vector2(23,31));		
		
		blocks[25][37] = new Block(new Vector2( 25,37 ));		
		springs[25][38] = new Spring(new Vector2(25,38));		
		
		blocks[27][44] = new Block(new Vector2(27 ,44 ));		
		blocks[28][44] = new Block(new Vector2(28 ,44 ));		
		blocks[29][44] = new Block(new Vector2( 29,44 ));		
		
	//	fire_balls[31][44] = new FireBall(new Vector2(31,44), 2f , 1f, FireBallState.UP);	
		blocks[31][44] = new Block(new Vector2( 31,44 ));		
		fire_balls[32][44] = new FireBall(new Vector2(32,44), 3f , 2f, FireBallState.DOWN);
		blocks[33][44] = new Block(new Vector2( 33,44 ));		
		fire_balls[34][44] = new FireBall(new Vector2(34,44), 3f , 2f, FireBallState.UP);		
		blocks[35][44] = new Block(new Vector2( 35, 44));		
		
		blocks[29][48] = new Block(new Vector2(29 ,48 ), BlockType.SPIKE);		
		blocks[30][48] = new Block(new Vector2(30 ,48 ), BlockType.SPIKE);		
		blocks[31][48] = new Block(new Vector2(31 ,48 ), BlockType.SPIKE);		
		blocks[32][48] = new Block(new Vector2( 32,48 ), BlockType.SPIKE);		
		blocks[33][48] = new Block(new Vector2(33 ,48 ), BlockType.SPIKE);		
		blocks[34][48] = new Block(new Vector2(34 ,48 ), BlockType.SPIKE);		
		blocks[35][48] = new Block(new Vector2( 35,48 ), BlockType.SPIKE);		
		blocks[36][47] = new Block(new Vector2( 36,47 ), BlockType.SPIKE);		
		
		for(int i=37; i<50; i++){		
			blocks[i][42] = new Block(new Vector2( i, 42));		
			blocks[i+2][45] = new Block(new Vector2( i+2,45 ), BlockType.SPIKE);		
		}	
		blocks[50][42] = new Block(new Vector2(50 ,42 ));
		blocks[51][42] = new Block(new Vector2( 51,42 ));		
		blocks[52][42] = new Block(new Vector2(52 ,42 ));	
	
	
		skeletons[43][43]= new Skeleton(new Vector2(43,43), 2f , 3f, SkeletonType.SKELETON , true,1);
		skeletons[44][43]= new Skeleton(new Vector2(44,43), 3f , 4f, SkeletonType.SKELETON , true, 1);
		
		fire_balls[46][43] = new FireBall(new Vector2(46,43), 2f , 3f, FireBallState.UP);
		fire_balls[47][43] = new FireBall(new Vector2(47,43), 3f , 4f, FireBallState.UP);
		
		
		fire_balls[52][43] = new FireBall(new Vector2(52,43), 2f , 3f, FireBallState.UP);
		fire_balls[53][43] = new FireBall(new Vector2(53,43), 3f , 4f, FireBallState.UP);
		
		blocks[55][43] = new Block(new Vector2( 55, 43));		
		fire_balls[57][43] = new FireBall(new Vector2(57,43), 2f , 3f, FireBallState.UP);
		fire_balls[58][43] = new FireBall(new Vector2(58,43), 2f , 3f, FireBallState.UP);
		blocks[56][43] = new Block(new Vector2( 56, 43));		
		blocks[57][43] = new Block(new Vector2( 57, 43));		
		blocks[58][43] = new Block(new Vector2( 58, 43));		
		blocks[59][44] = new Block(new Vector2( 59, 44), BlockType.SPIKE);		
		blocks[60][45] = new Block(new Vector2( 60, 45), BlockType.SPIKE);		
		
		platforms[63][43] = new Platform(new Vector2(63,43),  1.5f, 3, PlatformState.RIGHT);
		platforms[66][44] = new Platform(new Vector2(66,44),  1.5f, 3, PlatformState.LEFT);

		ninja_stars[66][47]=new NinjaStars(new Vector2(66, 47), new Vector2(0,0));
		
		fire_balls[61][43] = new FireBall(new Vector2(61,43), 2f , 3f, FireBallState.UP);
		fire_balls[65][44] = new FireBall(new Vector2(65,44), 2f , 3f, FireBallState.DOWN);
		fire_balls[69][43] = new FireBall(new Vector2(69,43), 2f , 3f, FireBallState.UP);
		
		blocks[70][45] = new Block(new Vector2(70 ,45 ));		
		blocks[71][45] = new Block(new Vector2(71 ,45 ));		
		blocks[72][45] = new Block(new Vector2(72 ,45 ));		
		blocks[73][45] = new Block(new Vector2(73 ,45 ));		
		blocks[74][45] = new Block(new Vector2( 74,45 ));		
		blocks[75][45] = new Block(new Vector2( 75,45 ));		
		blocks[76][45] = new Block(new Vector2( 76,45 ));		
		
		skeletons[73][46]= new Skeleton(new Vector2(73,46), 2f , 2f, SkeletonType.REAPER , true,2);
		skeletons[74][46]= new Skeleton(new Vector2(74,46), 2f , 2f, SkeletonType.SKELETON , true,1);
		
		for(int i=79; i<=90; i++){	
			if(i%3 !=0){
			blocks[i][48] = new Block(new Vector2( i, 48));		
			}
			
			blocks[i+3][52] = new Block(new Vector2( i+3,52 ), BlockType.SPIKE);		
		}
		platforms[94][47] = new Platform(new Vector2(94,47),  2, 4.2f, PlatformState.RIGHT);
		fire_balls[99][48] = new FireBall(new Vector2(99,48), 2f , 3f, FireBallState.DOWN);
		fire_balls[103][48] = new FireBall(new Vector2(103,48), 2f , 3f, FireBallState.UP);
		platforms[101][49] = new Platform(new Vector2(101,49),  2, 4.2f, PlatformState.RIGHT);
		
		blocks[105][50] = new Block(new Vector2( 105, 50));		
		for(int i=106; i<=130; i++){	
			if(i%3 !=0){
				blocks[i][51] = new Block(new Vector2( i, 51));		
			}
			if(i%3 ==0){
				blocks[i][50] = new Block(new Vector2( i, 50));	

			}
			blocks[i+3][54] = new Block(new Vector2( i+3,54), BlockType.SPIKE);		
		}
		
		blocks[131][51] = new Block(new Vector2( 131, 51));		
		blocks[132][51] = new Block(new Vector2( 132, 51));		
		blocks[133][51] = new Block(new Vector2( 133, 51));		
		blocks[134][51] = new Block(new Vector2( 134, 51));		
		blocks[135][51] = new Block(new Vector2( 135, 51));		
		
		fire_balls[111][52] = new FireBall(new Vector2( 111,52), 2f , 5f, FireBallState.RIGHT);
	
		fire_balls[121][52] = new FireBall(new Vector2( 121,52), 2f , 5f, FireBallState.LEFT);	
		
		fire_balls[129][52] = new FireBall(new Vector2( 129,52), 2f , 4f, FireBallState.LEFT);
		fire_balls[129][53] = new FireBall(new Vector2( 129,53), 1f , 4f, FireBallState.UP);
		
		platforms[140][50] = new Platform(new Vector2(140,50),  2, 4.2f, PlatformState.RIGHT);
		platforms[147][52] = new Platform(new Vector2(147,52),  2, 4.2f, PlatformState.RIGHT);

		blocks[151][54] = new Block(new Vector2(151 , 54));
		blocks[152][54] = new Block(new Vector2(152 , 54));
		blocks[153][54] = new Block(new Vector2(153 ,54 ));
		speed_pads[153][55]= new SpeedPad(new Vector2(153,55), SpeedPadType.RIGHT);
		
		blocks[156][56] = new Block(new Vector2(156 ,56 ));		
		speed_pads[156][57]= new SpeedPad(new Vector2(156,57), SpeedPadType.RIGHT);
		
		blocks[162][52] = new Block(new Vector2(162 ,52 ));		
		blocks[163][52] = new Block(new Vector2( 163, 52));		
		blocks[164][53] = new Block(new Vector2(164 ,53 ));		
		blocks[165][54] = new Block(new Vector2( 165,54 ));		
		blocks[166][55] = new Block(new Vector2( 166,55 ));		
		
		springs[166][56] = new Spring(new Vector2(166,56));		
		
		for(int i=155; i<=175 ;i++){
			if(i!= 165 && i!= 166 && i!=167){
				blocks[i][63] = new Block(new Vector2( i,63 ), BlockType.SPIKE);		
				blocks[i][64] = new Block(new Vector2( i,64 ), BlockType.SPIKE_TOP);				
			}
		}
		blocks[167][70] = new Block(new Vector2( 167,70 ));		
		springs[168][70] = new Spring(new Vector2(168,70));		
		
		platforms[172][77] = new Platform(new Vector2(172,77),  2, 4, PlatformState.RIGHT);

		blocks[176][79] = new Block(new Vector2(176 ,79 ));		
		blocks[177][79] = new Block(new Vector2( 177,79 ));		
		blocks[178][79] = new Block(new Vector2(178 , 79));		
		blocks[179][79] = new Block(new Vector2(179 ,79 ));		
		blocks[180][79] = new Block(new Vector2(180 , 79));		
		
		finish= new Vector2(179, 79);
		level_finish_build();		
	}
	
	private void level_eight() {
		width=190;
		height=100;
		level_setup(width, height);
		bob_start.x=4;
		bob_start.y=4;
		
		blocks[4][3] = new Block(new Vector2( 4,3 ));		
		platforms[8][3] = new Platform(new Vector2(8,5),  3, 4.2f, PlatformState.RIGHT);
		platforms[15][5] = new Platform(new Vector2(15,5),  3, 4.2f, PlatformState.LEFT);	
		
		for(int i=18; i<=30; i++){	
			if(i%3 !=0){
				blocks[i][6] = new Block(new Vector2( i, 6));		
			}else{
				blocks[i][5] = new Block(new Vector2( i, 5));				
			}
			if(i<25){
				blocks[i+3][8] = new Block(new Vector2( i+3,9 ), BlockType.SPIKE);		
			}
		}
		fire_balls[22][7] = new FireBall(new Vector2(28,7), 1.5f , 5f, FireBallState.LEFT);
		//fire_balls[28][7] = new FireBall(new Vector2(28,7), 2f , 4f, FireBallState.RIGHT);
		
		blocks[34][8] = new Block(new Vector2(34 ,8 ) );		
		blocks[35][8] = new Block(new Vector2(35 ,8 ));		
		blocks[36][8] = new Block(new Vector2(36 ,8 ));		
		
		for (int i = 9; i < 30; i++) {
			climables[36][i] = new Climable(new Vector2(36,i),ClimbType.LADDER);
			if(i%6==0){
				fire_balls[36][i] = new FireBall(new Vector2(36,i), 2f , 3f, FireBallState.LEFT);
			}else if(i%3==0){
				fire_balls[36][i] = new FireBall(new Vector2(36,i), 2f , 3f, FireBallState.RIGHT);
			}
			
		}
		
		blocks[37][29] = new Block(new Vector2( 37, 29));		
		blocks[38][29] = new Block(new Vector2( 38, 29));		
		blocks[39][29] = new Block(new Vector2( 39, 29));		
		springs[39][30] = new Spring(new Vector2(39,30));
		
		for(int i=30; i<=50 ;i++){
			if(i!= 38 && i!= 39 && i!=40){
				blocks[i][35] = new Block(new Vector2( i,35 ), BlockType.SPIKE);		
				blocks[i][35] = new Block(new Vector2( i,35 ), BlockType.SPIKE_TOP);				
			}
		}
		
		springs[40][40] = new Spring(new Vector2(40,40));
		blocks[40][39] = new Block(new Vector2( 40,39 ));
		
		platforms[45][44] = new Platform(new Vector2(45,44),  2, 4, PlatformState.RIGHT);
		
		platforms[48][48] = new Platform(new Vector2(48,48),  2, 4, PlatformState.LEFT);	

		springs[53][50] = new Spring(new Vector2(53,50));
		blocks[53][49] = new Block(new Vector2( 53,49 ));
		
		for (int i = 61; i < 81; i++) {
			climables[53][i] = new Climable(new Vector2(53,i),ClimbType.LADDER);
			if(i%6==0){
				fire_balls[53][i] = new FireBall(new Vector2(53,i), 2f , 3f, FireBallState.LEFT);
			}else if(i%3==0){
				fire_balls[53][i] = new FireBall(new Vector2(53,i), 2f , 3f, FireBallState.RIGHT);
			}
			if(i%5==0){
				blocks[52][i] = new Block(new Vector2( 52, i));		
				blocks[54][i] = new Block(new Vector2(54 ,i ));		
				
			}
			fire_balls[53][70] = new FireBall(new Vector2(53,70), 2f , 6f, FireBallState.UP);		
		}
		
		
		for(int i =54; i<=78; i++){
			blocks[i][80] = new Block(new Vector2( i,80 ));
		}	
		
		speed_pads[58][81]= new SpeedPad(new Vector2(58,81), SpeedPadType.RIGHT);
		speed_pads[66][81]= new SpeedPad(new Vector2(66,81), SpeedPadType.LEFT);
		speed_pads[67][81]= new SpeedPad(new Vector2(67,81), SpeedPadType.RIGHT);
		skeletons[59][81]= new Skeleton(new Vector2(59,81), 2f , 4f, SkeletonType.SKELETON , true,1);
		skeletons[68][81]= new Skeleton(new Vector2(68,81), 3f , 4f, SkeletonType.SKELETON , true,2);
		skeletons[72][81]= new Skeleton(new Vector2(72,81), 2f , 4f, SkeletonType.SKELETON , true,1);
		
		platforms[81][83] = new Platform(new Vector2(81,83),  2, 4, PlatformState.RIGHT);
		fire_balls[85][83] = new FireBall(new Vector2(85,83), 3f , 3f, FireBallState.UP);
		platforms[90][83] = new Platform(new Vector2(90,83),  2, 4, PlatformState.LEFT);
		fire_balls[92][83] = new FireBall(new Vector2(92,83), 3f , 3f, FireBallState.UP);
		platforms[85][84] = new Platform(new Vector2(81,83),  4, 7, PlatformState.RIGHT);
		
		blocks[95][82] = new Block(new Vector2( 95, 82));		
		blocks[96][82] = new Block(new Vector2( 96,82 ));		
		blocks[97][82] = new Block(new Vector2( 97,82 ));		
		blocks[98][82] = new Block(new Vector2(98 ,82 ));		
		
		
		finish= new Vector2(179, 79);
		level_finish_build();		
	}

private void level_nine() {
	// TODO Auto-generated method stub
	width=170;
	height=90;
	level_setup(width, height);
	bob_start.x=63;
	bob_start.y=35;
	blocks[1][2] = new Block(new Vector2(1 , 2));
	blocks[2][2] = new Block(new Vector2(2 , 2));
	blocks[3][2] = new Block(new Vector2(3 , 2));
	blocks[4][2] = new Block(new Vector2(4 , 2));
	blocks[5][2] = new Block(new Vector2(5 , 2));
	blocks[6][2] = new Block(new Vector2(6 , 2));
	blocks[7][2] = new Block(new Vector2(7 , 2));	
	blocks[8][2] = new Block(new Vector2(8 , 2));
	blocks[9][2] = new Block(new Vector2(9 , 2));
	// two fireballs
	fire_balls[11][3] = new FireBall(new Vector2(11,3), 2f , 2f, FireBallState.UP);
	fire_balls[13][3] = new FireBall(new Vector2(13,3), 2f , 2f, FireBallState.DOWN);
	
	
	blocks[14][2] = new Block(new Vector2(14 , 2));
	blocks[15][2] = new Block(new Vector2(15 , 2));
	blocks[16][2] = new Block(new Vector2(16 , 2));
	blocks[17][2] = new Block(new Vector2(17 , 2));
	//reaper
	//skeletons[18][3]= new Skeleton(new Vector2(18,3), 1.5f , 1.5f, SkeletonType.REAPER_HOVER , true, 1);
	
	blocks[21][2] = new Block(new Vector2( 21, 2));
	blocks[22][2] = new Block(new Vector2( 22, 2));
	blocks[23][2] = new Block(new Vector2( 23, 2));
	blocks[24][2] = new Block(new Vector2( 24, 2));
	blocks[25][2] = new Block(new Vector2(25 , 2));
	ninja_stars[24][3]=new NinjaStars(new Vector2(24,3), new Vector2(0,0));

	//tough skeleton back&forth

	skeletons[28][3]= new Skeleton(new Vector2(28,3), SkeletonType.SKELETON );
	
	blocks[26][2] = new Block(new Vector2( 26, 2));
	blocks[27][2] = new Block(new Vector2( 27, 2));
	blocks[28][2] = new Block(new Vector2( 28, 2));
	blocks[29][2] = new Block(new Vector2( 29, 2));
	blocks[30][2] = new Block(new Vector2( 30, 2));
	
	springs[30][3] = new Spring(new Vector2(30,3));		
	

	for(int i=2; i<25; i++){
		blocks[31][i] = new Block(new Vector2(31 , i));
	}
	blocks[28][15] = new Block(new Vector2( 28,15 ));
	blocks[27][15] = new Block(new Vector2( 27, 15));
	blocks[26][15] = new Block(new Vector2( 26, 15));
	blocks[25][15] = new Block(new Vector2(25 ,15 ), BlockType.SPIKE_TOP);	
	blocks[24][16] = new Block(new Vector2( 24,16 ), BlockType.SPIKE_TOP);	
	blocks[23][16] = new Block(new Vector2( 23, 16), BlockType.SPIKE_TOP);	
	blocks[22][17] = new Block(new Vector2( 22,17 ), BlockType.SPIKE_TOP);	
	blocks[21][13] = new Block(new Vector2( 21, 13), BlockType.SPIKE_TOP);	
	blocks[20][13] = new Block(new Vector2(20 ,13 ), BlockType.SPIKE_TOP);
	
	blocks[19][13] = new Block(new Vector2( 19, 13));
	blocks[18][13] = new Block(new Vector2( 18, 13));
	blocks[17][13] = new Block(new Vector2( 17, 13));
	//fireball up down 
	fire_balls[16][13] = new FireBall(new Vector2(16,13), 2f , 2f, FireBallState.UP);
	
	blocks[16][13] = new Block(new Vector2( 16, 13));
	blocks[15][13] = new Block(new Vector2( 15, 13));
	blocks[14][13] = new Block(new Vector2( 14, 13));
	blocks[13][13] = new Block(new Vector2( 13, 13));
	//ladder from 14 to 27 on y axis
	for(int i=14; i<27;i++){
		climables[13][i] = new Climable(new Vector2(13,i),ClimbType.LADDER);
	}
	fire_balls[13][18] = new FireBall(new Vector2(13,18), 2f , 2f, FireBallState.LEFT);
	fire_balls[13][24] = new FireBall(new Vector2(13,24), 2f , 2f, FireBallState.RIGHT);


	blocks[15][26] = new Block(new Vector2( 15, 26));
	blocks[16][26] = new Block(new Vector2( 16, 26));
	blocks[17][26] = new Block(new Vector2( 17, 26));
	blocks[18][26] = new Block(new Vector2( 18, 26));
	blocks[19][26] = new Block(new Vector2( 19, 26));
	blocks[20][26] = new Block(new Vector2( 20, 26));
	blocks[21][26] = new Block(new Vector2( 21, 26));
	blocks[22][26] = new Block(new Vector2( 22, 26));
	blocks[23][26] = new Block(new Vector2( 23, 26));
	blocks[24][26] = new Block(new Vector2( 24, 26));
	blocks[25][26] = new Block(new Vector2( 25, 26));
	blocks[26][26] = new Block(new Vector2( 26, 26));

	// platform form 27 to 50 at 28x
	platforms[38][28] = new Platform(new Vector2(38,28),  3, 12, PlatformState.RIGHT);
	fire_balls[40][29] = new FireBall(new Vector2(40,28),  2, 12, FireBallState.RIGHT);
	
	fire_balls[29][28] = new FireBall(new Vector2( 29, 28), 2f , 2f, FireBallState.UP);
	fire_balls[35][28] = new FireBall(new Vector2(35 , 28), 2f , 2f, FireBallState.DOWN);
	fire_balls[41][28] = new FireBall(new Vector2( 41, 28), 2f , 2f, FireBallState.UP);
	fire_balls[48][28] = new FireBall(new Vector2(48 , 28), 2f , 2f, FireBallState.DOWN);
	
	// fireballs spread acorrs stretch 
	for(int i=27; i<45; i++){
		if(i> 33 && i<39){
			blocks[i][41] = new Block(new Vector2(i ,41 ), BlockType.SPIKE_TOP);
			blocks[i][40] = new Block(new Vector2(i ,40), BlockType.SPIKE);
		}else{
		blocks[i][34] = new Block(new Vector2(i ,34 ), BlockType.SPIKE_TOP);
		blocks[i][33] = new Block(new Vector2(i ,33), BlockType.SPIKE);	
		}
		blocks[i][26] = new Block(new Vector2(i ,26 ), BlockType.SPIKE_TOP);
	}
	blocks[51][30] = new Block(new Vector2(51 , 30));
	blocks[52][30] = new Block(new Vector2(52 , 30));
	blocks[53][30] = new Block(new Vector2(53 , 30));
	blocks[54][30] = new Block(new Vector2(54 , 30));
	blocks[55][30] = new Block(new Vector2(55 , 30));
	blocks[56][30] = new Block(new Vector2(56 , 30));
	blocks[57][30] = new Block(new Vector2(57 , 30));
	blocks[58][30] = new Block(new Vector2( 58, 30));
	blocks[59][30] = new Block(new Vector2( 59, 30));
	blocks[60][30] = new Block(new Vector2( 60, 30));
	
	
	blocks[62][32] = new Block(new Vector2(62 ,32 ));
	blocks[63][32] = new Block(new Vector2( 63, 32));
	blocks[64][32] = new Block(new Vector2( 64, 32));
	 // sping at 63, 33
	springs[63][33] = new Spring(new Vector2(63,33));
	
	// spring on this block
	springs[65][45] = new Spring(new Vector2(65,45));
	blocks[65][44] = new Block(new Vector2( 65, 44));
	for(int i=46; i<66; i++){
		blocks[87][i] = new Block(new Vector2( 87,i ), BlockType.SPIKE);
	}
	for(int i=30; i<55;i++){
		blocks[66][i] = new Block(new Vector2( 66, i));
			
		
	}
	//speedpad on each block below 
	for(int i=70; i<79; i++ ){
		
		speed_pads[i][54]= new SpeedPad(new Vector2(i, 54), SpeedPadType.RIGHT);
		blocks[i][53] = new Block(new Vector2( i, 53));
	}
	
	for(int i=80; i<84; i++ ){
		speed_pads[i][51]= new SpeedPad(new Vector2(i, 51), SpeedPadType.LEFT);
		blocks[i][50] = new Block(new Vector2( i, 50));
	}
	blocks[85][50] = new Block(new Vector2( 85, 50));
	blocks[85][51] = new Block(new Vector2( 85, 51));
	blocks[85][52] = new Block(new Vector2( 85, 52));
	blocks[85][53] = new Block(new Vector2( 85, 53));
	blocks[85][54] = new Block(new Vector2( 85, 54));
	
	
	
	for(int i=71; i<79; i++ ){
		speed_pads[i][48]= new SpeedPad(new Vector2(i, 48), SpeedPadType.RIGHT);
		blocks[i][47] = new Block(new Vector2( i, 47));
	}
	blocks[71][48] = new Block(new Vector2( 71, 48));
	blocks[71][49] = new Block(new Vector2( 71, 49));
	blocks[71][50] = new Block(new Vector2( 71, 50));
	
	for(int i=80; i<89; i++ ){	
		speed_pads[i][41]= new SpeedPad(new Vector2(i, 41), SpeedPadType.RIGHT);
		blocks[i][40] = new Block(new Vector2( i, 40));
	}

	for(int i=90; i<96; i++ ){
		
		speed_pads[i][35]= new SpeedPad(new Vector2(i, 35), SpeedPadType.RIGHT);
		blocks[i][34] = new Block(new Vector2( i, 34));
	}
	
	for(int i=95; i<119; i++ ){
		blocks[i][32] = new Block(new Vector2( i, 32));
	}
	// put skeleton at 113 variance 2
	skeletons[113][32]= new Skeleton(new Vector2(113,32), 2f,2f, SkeletonType.SKELETON , true, 1);
	
	blocks[116][33] = new Block(new Vector2( 116, 33));
	blocks[117][33] = new Block(new Vector2( 117, 34));
	blocks[116][34] = new Block(new Vector2( 116, 34));
	
	blocks[116][36] = new Block(new Vector2( 116, 36));
	blocks[117][36] = new Block(new Vector2( 117, 36));
	blocks[118][36] = new Block(new Vector2( 118, 36));
	
	blocks[120][38] = new Block(new Vector2( 120, 38));
	blocks[121][39] = new Block(new Vector2( 121, 39));
	
	springs[121][40] = new Spring(new Vector2(121,40));
	//reaper
	//skeletons[126][38]= new Skeleton(new Vector2(126,38), 2f,2f, SkeletonType.REAPER_HOVER , true, 1);
	
	for(int i=127; i<138; i++ ){
		
		speed_pads[i][42]= new SpeedPad(new Vector2(i, 42), SpeedPadType.RIGHT);
		blocks[i][41] = new Block(new Vector2( i, 41));
	}
	
	for(int i=140; i<150; i++ ){
		blocks[i][32] = new Block(new Vector2( i, 32));
	}
	
	
	finish= new Vector2(148, 33);
	level_finish_build();
	
}
	private void level_ten() {
		// TODO Auto-generated method stub
		width=190;
		height=100;
		level_setup(width, height);
		bob_start.x=4;
		bob_start.y=4;
		
		for(int i=1 ; i<20; i++){
			blocks[i][2] = new Block(new Vector2( i, 2));
		}
		finish= new Vector2(14, 2);
		level_finish_build();
	}
//skeletons[9][29]= new Skeleton(new Vector2(9,29), 2f , 2f, SkeletonType.SKELETON , true);
	
//		platforms[22][5] = new Platform(new Vector2(22,5),  2, 4, PlatformState.RIGHT);
		
	//	platforms[27][5] = new Platform(new Vector2(27,5),  2, 4, PlatformState.LEFT);	

	//	ninja_stars[7][2]=new NinjaStars(new Vector2(7, 2), new Vector2(0,0));

	// fire_balls[][] = new FireBall(new Vector2(,), 2f , 3f, FireBallState.UP);
	
	//	springs[1][1] = new Spring(new Vector2(1,1));		
	
	//blocks[][] = new Block(new Vector2( , ));		
		
	//blocks[][] = new Block(new Vector2( , ), BlockType.SPIKE);		
			
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
	for(int i =0; i<width; i++){
		blocks[i][1] = new Block(new Vector2(i,0), BlockType.SPIKE_TOP);
		
	}
	for(int i=0; i <height; i++){
		blocks[1][i] = new Block(new Vector2(0,i), BlockType.SPIKE_RIGHT);
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
public float[] getLevelColor() {
	return null; //bkg_color;
}

}
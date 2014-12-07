package wiser.development.NinjaRunner.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Assets {
	private static final float RUNNING_FRAME_DURATION = 0.08f;
	public static final float DAMP 			= 0.90f;
	public static final float MAX_VEL 			= 4f;
	public static final float ACCELERATION 	= 4f;
	public static final float GRAVITY 			= -20f;


	public static Texture background;
	public static TextureRegion backgroundRegion;

	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion play;
	public static TextureRegion menu;
	public static TextureRegion retry;
	public static TextureRegion playOverlay;
	public static TextureRegion na;
	public static TextureRegion levelComplete;
	public static TextureRegion nextLevel;
	public static TextureRegion gameOver;
	
	public static TextureRegion continuePlaying;
	/** Textures **/
	public static TextureRegion directionRight;
	public static TextureRegion directionUp;
	public static TextureRegion directionLeft;
	public static TextureRegion directionDown;
	
	public static TextureRegion bobIdleLeft;
	public static TextureRegion bobIdleRight;
	public static TextureRegion blockTexture;
	public static TextureRegion spikeTexture, spikeLeftTexture, spikeRightTexture ;
	public static TextureRegion spikeTopTexture;
	public static TextureRegion ladderTexture;
	public static TextureRegion fireTexture;
	public static TextureRegion fireBallUpTexture[];
	public static TextureRegion fireBallDownTexture[];
	public static TextureRegion fireBallLeftTexture[];
	public static TextureRegion fireBallRightTexture[];
	public static TextureRegion ghostHoverTexture[];
	
	
	public static TextureRegion bobFrame;
	public static TextureRegion bobJumpLeft;
	public static TextureRegion bobFlipJumpLeft;
	public static TextureRegion bobFallLeft;
	public static TextureRegion bobJumpRight;
	public static TextureRegion bobFlipJumpRight;
	public static TextureRegion bobFallRight;
	public static TextureRegion bobDead;
	public static TextureRegion ninjaStars;
	public static TextureRegion spring;
	public static TextureRegion springDown;
	public static TextureRegion speed1;
	public static TextureRegion speed2;
	public static TextureRegion climbIdle;
	public static TextureRegion metalPlatform;
	public static TextureRegion ghostHover1;
	public static TextureRegion ghostHover2;
	public static TextureRegion ghostHover3;
	public static TextureRegion ghostHover4;
	public static TextureRegion ghostKillerField;
	
	/** Animations **/
	public static Animation walkLeftAnimation;
	public static Animation walkRightAnimation;
	public static Animation climbUpAnimation;
	
	public static Animation fireballUpAnimation;
	public static Animation fireballDownAnimation;
	public static Animation fireballLeftAnimation;
	public static Animation fireballRightAnimation;
	public static Animation ghostLeftAnimation;
	public static Animation ghostRightAnimation;
	
	public static Animation swordLeftAnimation;
	public static Animation swordRightAnimation;	
	public static Animation skeletonLeftAnimation;
	public static Animation skeletonRightAnimation;
	public static Animation skeletonStrongRightAnimation;
	public static Animation skeletonStrongLeftAnimation;
	public static Animation throwingStarAnimation;
	public static Animation skeletonDeadLeftAnimation;
	public static Animation skeletonDeadRightAnimation;
	public static Animation throwingNinjaStar;
	public static Animation speedPadRight, speedPadLeft;
	public static Animation bobDoubleJumpRightAnimation, bobDoubleJumpLeftAnimation;
	public static BitmapFont smallfont;
	public static BitmapFont largerfont;
	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	

	private static AtlasRegion skeletonIdleLeft;

	private static TextureRegion skeletonIdleRight;
	public static Animation skeletonToughLeftAnimation;
	public static Animation skeletonToughRightAnimation;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		TextureAtlas iconatlas = new TextureAtlas(Gdx.files.internal("textures/menu.pack"));
		continuePlaying =iconatlas.findRegion("continue-playing");
		logo = iconatlas.findRegion("logo");
		soundOff =iconatlas.findRegion("sound-off");
		soundOn = iconatlas.findRegion("sound-on");
		arrow = iconatlas.findRegion("play");
		pause = iconatlas.findRegion("pause");
		play =iconatlas.findRegion("play");
		menu =iconatlas.findRegion("menu");
		retry=iconatlas.findRegion("retry");
		playOverlay=iconatlas.findRegion("play-overlay");
		gameOver= iconatlas.findRegion("gameover");
		levelComplete= iconatlas.findRegion("levelComplete");
		nextLevel= iconatlas.findRegion("nextlevel");
		na=iconatlas.findRegion("na");
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		if (Settings.soundEnabled) music.play();
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
		highJumpSound = Gdx.audio.newSound(Gdx.files.internal("highjump.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));

		//font = new BitmapFont
		smallfont =new BitmapFont(Gdx.files.internal("font/smallfont.fnt"), Gdx.files.internal("font/smallfont.png"), false);
		largerfont =new BitmapFont(Gdx.files.internal("font/largerfont.fnt"), Gdx.files.internal("font/largerfont.png"), false);
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures2/texture.pack"));
		ninjaStars = atlas.findRegion("ninja-star");
		bobIdleLeft = atlas.findRegion("ninja-1");
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);
		blockTexture = atlas.findRegion("block");
		ladderTexture = atlas.findRegion("ladder");
		spikeTexture = atlas.findRegion("spikes");
		spikeTopTexture =new TextureRegion(spikeTexture);
		directionRight = atlas.findRegion("direction-arrow-right");
		directionUp = atlas.findRegion("direction-arrow-up");
		
		directionLeft =new TextureRegion(directionRight);
		directionDown =new TextureRegion(directionUp);
		directionLeft.flip(true, false);
		directionDown.flip(false, true);
//		spikeLeftTexture = atlas.findRegion(spikesLeft)
//		spikeRightTexture = new TextureRegion(spikeLeftTexture);
		spikeTopTexture.flip(true, true);
		fireTexture = atlas.findRegion("fire");
		TextureRegion[] fireBallDownTexture = new TextureRegion[2];
		TextureRegion[] fireBallUpTexture = new TextureRegion[2];
		TextureRegion[] fireBallRightTexture = new TextureRegion[2];
		TextureRegion[] fireBallLeftTexture = new TextureRegion[2];
		
		fireBallDownTexture[0]=atlas.findRegion("fire-ball");		
		fireBallDownTexture[1]=atlas.findRegion("fire-ball-2");	
		fireballDownAnimation =new Animation( RUNNING_FRAME_DURATION*2 , fireBallDownTexture);
		
		fireBallUpTexture[0] = new TextureRegion(fireBallDownTexture[0]);
		fireBallUpTexture[1] = new TextureRegion(fireBallDownTexture[1]);
		fireBallUpTexture[0].flip(true, true);
		fireBallUpTexture[1].flip(true, true);
		fireballUpAnimation =new Animation( RUNNING_FRAME_DURATION*2 , fireBallUpTexture);
		
		fireBallLeftTexture[0] = atlas.findRegion("fire-ball-left");	
		fireBallLeftTexture[1] = atlas.findRegion("fire-ball-left-2");
		fireballLeftAnimation =new Animation( RUNNING_FRAME_DURATION*2 , fireBallLeftTexture);
		
		fireBallRightTexture[0] = new TextureRegion(fireBallLeftTexture[0]);
		fireBallRightTexture[1] = new TextureRegion(fireBallLeftTexture[1]);
		fireBallRightTexture[0].flip(true, true);
		fireBallRightTexture[1].flip(true, true);
		fireballRightAnimation =new Animation( RUNNING_FRAME_DURATION*2 , fireBallRightTexture);
		
		TextureRegion[] throwingStarFrames = new TextureRegion[2];
		throwingStarFrames[0]=atlas.findRegion("ninja-star");
		throwingStarFrames[1]= atlas.findRegion("ninja-star-rotate");

		metalPlatform=atlas.findRegion("metalplatform");
		throwingStarAnimation = new Animation( RUNNING_FRAME_DURATION*2 ,throwingStarFrames);

		TextureRegion [] ghostLeftFrames = new TextureRegion[4];		

		TextureRegion[] walkLeftFrames = new TextureRegion[6];
		for (int i = 0; i < 6; i++) {
			walkLeftFrames[i] = atlas.findRegion("ninja-" + (i+2));
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);
		for(int i =0 ; i<4; i++){
			ghostLeftFrames[i] = atlas.findRegion("ghost-" + (i+1));
			
		}
		ghostHover1 = atlas.findRegion("ghostHover-1");
		ghostHover2 = atlas.findRegion("ghostHover-2");
		ghostHover3 = atlas.findRegion("ghostHover-3");
		ghostHover4 = atlas.findRegion("ghostHover-4");
		ghostKillerField = atlas.findRegion("ghost-field");
		ghostLeftAnimation = new Animation(RUNNING_FRAME_DURATION*2, ghostLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[6];
		TextureRegion[] ghostRightFrames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			ghostRightFrames[i] = new TextureRegion(ghostLeftFrames[i]);
			ghostRightFrames[i].flip(true, false);
		}
		ghostRightAnimation = new Animation(RUNNING_FRAME_DURATION*2, ghostRightFrames);
		for (int i = 0; i < 6; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);

		TextureRegion[] speedRightFrames = new TextureRegion[2];
		speedRightFrames[0]=atlas.findRegion("speed-pad-2");
		speedRightFrames[1]=atlas.findRegion("speed-pad-1");
		speedPadRight = new Animation(RUNNING_FRAME_DURATION*2, speedRightFrames);

		TextureRegion[] speedLeftFrames = new TextureRegion[2];
		speedLeftFrames[0]=new TextureRegion(speedRightFrames[0]);
		speedLeftFrames[0].flip(true, false);
		speedLeftFrames[1]=new TextureRegion(speedRightFrames[1]);
		speedLeftFrames[1].flip(true, false);
		speedPadLeft = new Animation(RUNNING_FRAME_DURATION*2, speedRightFrames);

		TextureRegion[] bobDoubleJumpRight = new TextureRegion[3];
		TextureRegion[] bobDoubleJumpLeft = new TextureRegion[3];
		bobDoubleJumpLeft[0]= atlas.findRegion("ninja-flip-1");
		bobDoubleJumpLeft[1]= atlas.findRegion("ninja-flip-2");
		bobDoubleJumpLeft[2]= atlas.findRegion("ninja-flip-3");
		bobDoubleJumpRight[0]= new TextureRegion(bobDoubleJumpLeft[0]);
		bobDoubleJumpRight[1]= new TextureRegion(bobDoubleJumpLeft[1]);
		bobDoubleJumpRight[2]= new TextureRegion(bobDoubleJumpLeft[2]); 
		
		bobDoubleJumpRight[0].flip(true, false);
		bobDoubleJumpRight[1].flip(true, false);
		bobDoubleJumpRight[2].flip(true, false);
		
		bobDoubleJumpRightAnimation= new Animation(RUNNING_FRAME_DURATION*2, bobDoubleJumpRight);
		bobDoubleJumpLeftAnimation= new Animation(RUNNING_FRAME_DURATION*2, bobDoubleJumpLeft);
		
		
		bobJumpLeft = atlas.findRegion("ninja-up");
		bobFlipJumpRight = new TextureRegion(bobJumpLeft);
		bobFlipJumpRight.flip(true, true);

		bobFlipJumpLeft = new TextureRegion(bobJumpLeft);
		bobFlipJumpLeft.flip(false, true);
		bobJumpRight = new TextureRegion(bobJumpLeft);
		bobJumpRight.flip(true, false);
		bobFallLeft = atlas.findRegion("ninja-down");
		bobFallRight = new TextureRegion(bobFallLeft);
		bobFallRight.flip(true, false);
		bobDead = new TextureRegion(bobFallLeft);
		bobDead.flip(false, true);
		spring = atlas.findRegion("spring-full");
		springDown= atlas.findRegion("spring-compressed");

		TextureRegion[] swordRightFrames = new TextureRegion[4];
		TextureRegion[] swordLeftFrames = new TextureRegion[4];
		/*for(int i=1; i<3; i++){
			swordLeftFrames[i]=atlas.findRegion("ninja-sword-"+i);
			swordRightFrames[i] = new TextureRegion(swordLeftFrames[i-1]);
			swordRightFrames[i].flip(true, false);	
		}*/
		swordLeftFrames[0]=atlas.findRegion("ninja-sword-1");
		swordLeftFrames[1]=atlas.findRegion("ninja-sword-2");
		swordLeftFrames[2]=atlas.findRegion("ninja-sword-3");
		swordLeftFrames[3]=atlas.findRegion("ninja-sword-4");
		
		swordRightFrames[0] = new TextureRegion(swordLeftFrames[0]);
		swordRightFrames[1] =new TextureRegion(swordLeftFrames[1]);
		swordRightFrames[2] = new TextureRegion(swordLeftFrames[2]);
		swordRightFrames[3] =new TextureRegion(swordLeftFrames[3]);
		swordRightFrames[0].flip(true, false);
		swordRightFrames[1].flip(true, false);
		swordRightFrames[2].flip(true, false);
		swordRightFrames[3].flip(true, false);
		/*swordLeftFrames[0]=atlas.findRegion("ninja-sword-1");
		swordLeftFrames[1]=atlas.findRegion("ninja-sword-2");
		swordRightFrames[0] = new TextureRegion(swordLeftFrames[0]);
		swordRightFrames[1] =new TextureRegion(swordLeftFrames[1]);
		swordRightFrames[0].flip(true, false);
		swordRightFrames[1].flip(true, false);
		*/
		swordRightAnimation = new Animation(RUNNING_FRAME_DURATION,swordRightFrames);
		swordLeftAnimation = new Animation(RUNNING_FRAME_DURATION,swordLeftFrames);

		TextureRegion[] climbUpFrames= new TextureRegion[4];

		climbUpFrames[0]=atlas.findRegion("ninja-climb-1");
		climbUpFrames[1]=atlas.findRegion("ninja-climb-2");
		climbUpFrames[2]=atlas.findRegion("ninja-climb-3");
		climbUpFrames[3]=atlas.findRegion("ninja-climb-4");
		climbUpAnimation = new Animation(RUNNING_FRAME_DURATION,climbUpFrames);

		climbIdle = atlas.findRegion("ninja-climb-1");
		/*** Skeleton animation and frames**/
		skeletonIdleLeft = atlas.findRegion("skelton-1");
		skeletonIdleRight = new TextureRegion(skeletonIdleLeft);
	
		TextureRegion[]skeletonLeftFrames = new TextureRegion[3];
		TextureRegion[]skeletonToughLeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			skeletonLeftFrames[i] = atlas.findRegion("skelton-" + (i+1));
			skeletonToughLeftFrames[i] = atlas.findRegion("skelton-tough-" + (i+1));
			
		}
		skeletonLeftAnimation = new Animation(RUNNING_FRAME_DURATION, skeletonLeftFrames);
		skeletonToughLeftAnimation = new Animation(RUNNING_FRAME_DURATION, skeletonToughLeftFrames);
		TextureRegion[] skeletonRightFrames = new TextureRegion[3];
		TextureRegion[] skeletonToughRightFrames = new TextureRegion[3];

		for (int i = 0; i < 3; i++) {
			skeletonRightFrames[i] = new TextureRegion(skeletonLeftFrames[i]);
			skeletonToughRightFrames[i] = new TextureRegion(skeletonToughLeftFrames[i]);
			skeletonToughRightFrames[i].flip(true, false);
			skeletonRightFrames[i].flip(true, false);

			
		}
		
		skeletonRightAnimation = new Animation(RUNNING_FRAME_DURATION, skeletonRightFrames);
		skeletonToughRightAnimation = new Animation(RUNNING_FRAME_DURATION, skeletonToughRightFrames);

		// Skeleton dieing animations
		TextureRegion[]skeletonDeadLeftFrames = new TextureRegion[2];
		skeletonDeadLeftFrames[0] = atlas.findRegion("skelton-die-1" );
		skeletonDeadLeftFrames[1] = atlas.findRegion("skelton-die-2" );		
		skeletonDeadLeftAnimation = new Animation(RUNNING_FRAME_DURATION*5, skeletonDeadLeftFrames);

		TextureRegion[] skeletonDeadRightFrames = new TextureRegion[2];

		skeletonDeadRightFrames[0] = new TextureRegion(skeletonDeadLeftFrames[0]);
		skeletonDeadRightFrames[1] = new TextureRegion(skeletonDeadLeftFrames[1]);		

		skeletonDeadRightFrames[0].flip(true, false);
		skeletonDeadRightFrames[1].flip(true, false);

		skeletonDeadRightAnimation = new Animation(RUNNING_FRAME_DURATION*3, skeletonDeadRightFrames);

	}

	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}
}

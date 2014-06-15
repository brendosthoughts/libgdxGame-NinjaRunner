package wiser.development.starAssault.utils;

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

	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion platform;
	/** Textures **/
	public static TextureRegion bobIdleLeft;
	public static TextureRegion bobIdleRight;
	public static TextureRegion blockTexture;
	public static TextureRegion fireTexture;
	public static TextureRegion bobFrame;
	public static TextureRegion bobJumpLeft;
	public static TextureRegion bobFallLeft;
	public static TextureRegion bobJumpRight;
	public static TextureRegion bobFallRight;
	public static TextureRegion bobDead;

	
	/** Animations **/
	public static Animation walkLeftAnimation;
	public static Animation walkRightAnimation;
	
	public static Animation punchLeftAnimation;
	public static Animation punchRightAnimation;
	
	public static Animation skeletonLeftAnimation;
	public static Animation skeletonRightAnimation;
	
	public static Animation skeletonDeadLeftAnimation;
	public static Animation skeletonDeadRightAnimation;
	public static BitmapFont font;

	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;

	private static AtlasRegion skeletonIdleLeft;

	private static TextureRegion skeletonIdleRight;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		items = loadTexture("mainMenu/items.png");
		mainMenu = new TextureRegion(items, 0, 224, 300, 110);
		pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 256, 160, 96);
		highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
		logo = new TextureRegion(items, 0, 352, 274, 142);
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		pause = new TextureRegion(items, 64, 64, 64, 64);

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		if (Settings.soundEnabled) music.play();
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
		highJumpSound = Gdx.audio.newSound(Gdx.files.internal("highjump.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
		
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures2/texture.pack"));
		bobIdleLeft = atlas.findRegion("ninja-1");
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);
		blockTexture = atlas.findRegion("block");
		fireTexture = atlas.findRegion("fire");
		
		TextureRegion[] walkLeftFrames = new TextureRegion[6];
		for (int i = 0; i < 6; i++) {
			walkLeftFrames[i] = atlas.findRegion("ninja-" + (i+2));
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		bobJumpLeft = atlas.findRegion("ninja-up");
		bobJumpRight = new TextureRegion(bobJumpLeft);
		bobJumpRight.flip(true, false);
		bobFallLeft = atlas.findRegion("ninja-down");
		bobFallRight = new TextureRegion(bobFallLeft);
		bobFallRight.flip(true, false);
		bobDead = new TextureRegion(bobFallLeft);
		bobDead.flip(false, true);
		
		TextureRegion[] punchRightFrames = new TextureRegion[2];
		TextureRegion[] punchLeftFrames = new TextureRegion[2];
		punchLeftFrames[0]=atlas.findRegion("ninja-punch-1");
		punchLeftFrames[1]=atlas.findRegion("ninja-punch-2");
		punchRightFrames[0] = new TextureRegion(punchLeftFrames[0]);
		punchRightFrames[1] =new TextureRegion(punchLeftFrames[1]);
		punchRightFrames[0].flip(true, false);
		punchRightFrames[1].flip(true, false);
		punchRightAnimation = new Animation(RUNNING_FRAME_DURATION,punchRightFrames);
		punchLeftAnimation = new Animation(RUNNING_FRAME_DURATION,punchLeftFrames);
		
		
		
		
		/*** Skeleton animation and frames**/
		skeletonIdleLeft = atlas.findRegion("skelton-1");
		skeletonIdleRight = new TextureRegion(skeletonIdleLeft);
		bobIdleRight.flip(true, false);
		TextureRegion[]skeletonLeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			skeletonLeftFrames[i] = atlas.findRegion("skelton-" + (i+2));
		}
		skeletonLeftAnimation = new Animation(RUNNING_FRAME_DURATION/3, skeletonLeftFrames);
		
		TextureRegion[] skeletonRightFrames = new TextureRegion[3];

		for (int i = 0; i < 3; i++) {
			skeletonRightFrames[i] = new TextureRegion(skeletonLeftFrames[i]);
			skeletonRightFrames[i].flip(true, false);
		}
		skeletonRightAnimation = new Animation(RUNNING_FRAME_DURATION/3, skeletonRightFrames);

		// Skeleton dieing animations
		TextureRegion[]skeletonDeadLeftFrames = new TextureRegion[2];
		skeletonDeadLeftFrames[0] = atlas.findRegion("skelton-die-1" );
		skeletonDeadLeftFrames[1] = atlas.findRegion("skelton-die-2" );		
		skeletonDeadLeftAnimation = new Animation(RUNNING_FRAME_DURATION/3, skeletonDeadLeftFrames);
		
		TextureRegion[] skeletonDeadRightFrames = new TextureRegion[4];

		skeletonDeadRightFrames[0] = new TextureRegion(skeletonDeadLeftFrames[0]);
		skeletonDeadRightFrames[1] = new TextureRegion(skeletonDeadLeftFrames[1]);
		
		skeletonDeadRightFrames[0].flip(true, false);
		skeletonDeadRightFrames[1].flip(true, false);
			
		skeletonDeadRightAnimation = new Animation(RUNNING_FRAME_DURATION/3, skeletonRightFrames);

	}

	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}
}

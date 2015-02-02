package wiser.development.NinjaRunner.utils;


import com.badlogic.gdx.Preferences; 
import com.badlogic.gdx.Gdx;
public class Settings {

	private static Preferences prefs = Gdx.app.getPreferences("gameInfo");
	public static void load () {
	if(prefs.contains("firstInstall") ){
			//default values have already been loaded 
		}else{
			prefs.putInteger("levels", 11);
			prefs.putInteger("levelReached",1);
			prefs.putBoolean("firstInstall", false);
			prefs.putBoolean("soundOn",false);
			prefs.flush();
		}
	}

	public static Boolean isSoundEnabled(){
		return prefs.getBoolean("soundOn");
	}
	public static void toggleSound(){
		prefs.putBoolean("soundOn", !prefs.getBoolean("soundOn"));
		prefs.flush();
	}
	

	public static void newLevelComplete() {
		prefs.putInteger("levelReached", prefs.getInteger("levelReached")+1 );
		prefs.flush();
		// TODO Auto-generated method stub
		
	}

	public static int levelReached() {
		return prefs.getInteger("levelReached");
	}
	public static int totalLevels(){
		return prefs.getInteger("levels");
	}



	
}
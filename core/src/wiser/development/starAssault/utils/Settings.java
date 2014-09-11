package wiser.development.starAssault.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {

	public static boolean soundEnabled = false;
	public final static String file = ".starAssault";
	public static int levels=12;
	public static int levelReached=4;

	public static void load () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			String[] strings = filehandle.readString().split("\n");
			
			soundEnabled = Boolean.parseBoolean(strings[0]);
			levelReached =  Integer.parseInt(strings[1]);
			
			

		} catch (Throwable e) {
		
			// :( It's ok we have defaults
		}
	}

	public static void save () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
			filehandle.writeString(Integer.toString(levelReached)+"\n", true);
			
		} catch (Throwable e) {
			
		}
	}
	public static void saveLevel(int level){
		try{
			FileHandle filehandle = Gdx.files.external(file);	
			filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
			filehandle.writeString(Integer.toString(level)+"\n", true);
			load();
			
		}catch(Throwable e){
			
			//figure out how to handle not being able to save files later
		}
	
	}


	
}
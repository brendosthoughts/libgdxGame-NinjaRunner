package wiser.development.starAssault;

import wiser.development.starAssault.screens.MainMenuScreen;
import wiser.development.starAssault.utils.Assets;
import wiser.development.starAssault.utils.Settings;

import com.badlogic.gdx.Game;

public class StarAssault extends Game {

 @Override
 public void create() {
	 Settings.save();
	 Settings.load();
		Assets.load();
  setScreen(new MainMenuScreen(this));
 }
}
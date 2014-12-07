package wiser.development.NinjaRunner;

import wiser.development.NinjaRunner.screens.MainMenuScreen;
import wiser.development.NinjaRunner.utils.Assets;
import wiser.development.NinjaRunner.utils.Settings;

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
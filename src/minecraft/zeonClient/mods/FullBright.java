package zeonClient.mods;

import org.lwjgl.input.Keyboard;

import zeonClient.main.Category;

public class FullBright extends Mod {

	public FullBright() {
		super("FullBright", "FullBright", Keyboard.KEY_C, Category.WORLD);
		// TODO Auto-generated constructor stub
	}
	
	public void onUpdate() {
		if(this.isToggled()) {
			mc.gameSettings.gammaSetting = 100F;
			System.out.println("light ready");
		}
	}

}

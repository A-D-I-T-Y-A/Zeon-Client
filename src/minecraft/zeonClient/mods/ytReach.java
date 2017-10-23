package zeonClient.mods;

import org.lwjgl.input.Keyboard;

import zeonClient.main.Category;

public class ytReach extends Mod {
	
	public static boolean reach;

	public ytReach() {
		super("Reach", "Reach", Keyboard.KEY_H, Category.COMBAT);
	}
	
	public void onEnable() {
		this.reach = true;
	}
	
	public void onDisable() {
		this.reach = false;
	}

}

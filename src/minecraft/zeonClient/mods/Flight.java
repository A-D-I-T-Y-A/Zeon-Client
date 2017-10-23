package zeonClient.mods;

import org.lwjgl.input.Keyboard;

import zeonClient.main.Category;

public class Flight extends Mod{

	public Flight() {
		super("Flight", "Flight", Keyboard.KEY_F, Category.MOVEMENT);
	}
	
	public void onUpdate() {
		if(this.isToggled()) {
			mc.player.capabilities.isFlying = true;
			mc.player.capabilities.allowFlying = true;
			mc.player.capabilities.setFlySpeed(0.2F);
		}
	}
	
	public void onDisabled() {
		mc.player.capabilities.isFlying = false;
	}

}

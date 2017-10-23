package youtube.mods;

import org.lwjgl.input.Keyboard;

import youtube.main.Category;

public class LongJump extends Mod{

	public LongJump() {
		super("LongJump", "LongJump", Keyboard.KEY_K, Category.MOVEMENT);
	}
	
	public void onUpdate() {
		if(this.isToggled()) {
			if(mc.gameSettings.keyBindForward.isPressed() 
					|| mc.gameSettings.keyBindBack.isPressed()
					|| mc.gameSettings.keyBindRight.isPressed()
					|| mc.gameSettings.keyBindLeft.isPressed()) {
				if(mc.player.isAirBorne) {
					mc.player.motionX *= 2;
					mc.player.motionZ *= 2;
				}
			}
			else
			{
				mc.player.motionX *= 1;
				mc.player.motionZ *= 1;
			}
		}
	}

}

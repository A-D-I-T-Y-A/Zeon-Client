package zeonClient.mods;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.MoverType;
import zeonClient.main.Category;

public class Speed extends Mod {

	public Speed() {
		super("Speed", "Speed", Keyboard.KEY_M, Category.MOVEMENT);
	}
	
	public void onUpdate() {
		if(this.isToggled()) {
			if(mc.player.onGround) {
				mc.player.motionX *= 1.0;
				mc.player.motionZ *= 1.0;
				mc.player.move(MoverType.PLAYER,5800,65,54);
			}
		}
	}
}

package youtube.mods;

import org.lwjgl.input.Keyboard;

import net.minecraft.network.play.client.CPacketPlayer;
import youtube.main.Category;

public class NoFall extends Mod {

	public NoFall() {
		super("NoFall", "NoFall", Keyboard.KEY_N, Category.PLAYER);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onUpdate() {
		
		if(this.isToggled()) {
			if(mc.player.fallDistance >= 2F) {
				mc.player.connection.sendPacket(new CPacketPlayer(true));
			}
		}
	}
	
	
	

}

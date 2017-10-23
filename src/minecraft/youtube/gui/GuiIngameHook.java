package youtube.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import youtube.main.Category;
import youtube.main.YouTube;
import youtube.mods.Mod;

public class GuiIngameHook extends GuiIngame  {
	
	Minecraft mc = Minecraft.getMinecraft();

	public GuiIngameHook(Minecraft mcIn) {
		super(mcIn);
		mc = mcIn;
		// TODO Auto-generated constructor stub
	}
	
	public void renderGameOverlay(float partialTicks) {
		super.renderGameOverlay(partialTicks);
		
		ScaledResolution sr = new ScaledResolution(this.mc);
		this.mc.entityRenderer.setupOverlayRendering();
		
		//ArrayList
		mc.fontRenderer.drawString("§1YouTube", 2, 2, 0x980000);
		int yCount = 2;
		int index = 0;
		int x = 0;
		for(Mod m: YouTube.getMods()) {
			if(m.isToggled() && !m.isCategory(Category.GUI)) {
				int right = sr.getScaledWidth() - mc.fontRenderer.getStringWidth(m.getArrayListName());
				mc.fontRenderer.drawString(m.getArrayListName(), right - 2, yCount, 0xffffff);
				yCount+=10;
				index++;
				x++;
			}
		}
	}

}

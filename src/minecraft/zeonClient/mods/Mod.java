package zeonClient.mods;

import net.minecraft.client.Minecraft;
import zeonClient.main.Category;

public class Mod {
	public static Minecraft mc = Minecraft.getMinecraft();
	private String Name;
	private String ArrayListName;
	private int Key;
	private boolean toggled;
	private Category category;
	
	public Mod(String n, String an, int i, Category c) {
		Name = n;
		ArrayListName = an;
		Key = i;
		category = c;
		toggled = false;
	}
	
	public void Toggle() {
		onToggle();
		toggled = !toggled;
		if(toggled) {
			onEnable();
		}
		else {
			onDisable();
		}
	}

	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	public void onEnable() {
		// TODO Auto-generated method stub
		
	}

	public void onToggle() {
		// TODO Auto-generated method stub
		
	}
	
	public void onRender() {
		
	}
	
	public Minecraft getMc() {
		return mc;
	}
	
	public void setMc(Minecraft mc) {
		this.mc = mc;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		this.Name = name;
	}
	
	public int getKey() {
		return Key;
	}
	
	public void setKey(int key) {
		this.Key = key;
	}
	
	public String getArrayListName() {
		return ArrayListName;
	}
	
	public void setArrayListName(String aln) {
		this.ArrayListName = aln;
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public final boolean isCategory(Category c) {
		return category == c;
	}

	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	
}

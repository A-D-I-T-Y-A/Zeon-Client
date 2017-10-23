package zeonClient.main;

import java.util.ArrayList;

import zeonClient.mods.*;

public class YouTube {
	
	private static ArrayList<Mod> mods;
	
	public YouTube() {
		
		mods = new ArrayList<Mod>();
		//0
		addMod(new Flight());
		//1
		addMod(new NoFall());
		//2
		addMod(new FullBright());
		//3
		addMod(new LongJump());
		//4
		addMod(new Speed());
		//5
		addMod(new ytReach());
		//6
		addMod(new KillAura());
		//7
		addMod(new RectMiner());
		//8
		addMod(new NukeMiner());
	}
	
	public static void addMod(Mod m) {
		mods.add(m);
	}
	
	public static ArrayList<Mod> getMods() {
		return mods;
	}
	
	public static void onUpdate() {
		for(Mod m: mods) {
			m.onUpdate();
		}
	}
	
	
	public static void onRender() {
		for(Mod m: mods) {
			m.onRender();
		}
	}
	
	public static void onKeyPressed(int i) {
		for(Mod m: mods) {
			if(m.getKey() == i) {
				m.Toggle();
			}
		}
	}
}

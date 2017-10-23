package zeonClient.mods;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import zeonClient.main.Category;
import zeonClient.utils.BlockUtils;
import zeonClient.utils.WBlock;
import zeonClient.utils.WMinecraft;
import zeonClient.utils.WPlayer;
import zeonClient.utils.WPlayerController;
import zeonClient.utils.BlockUtils.BlockValidator;

public class NukeMiner extends Mod {


	private final int MAX_TICKS = 20;
	private int ticks = MAX_TICKS;
	private int id = 133;
	private BlockPos currentBlock;
	private BlockValidator validator;
	
	private ArrayList<BlockPos> reachable;
	private ArrayList<BlockPos> nextClosestBlocks;
	
	
	public NukeMiner() {
		super("NukeMiner", "NukeMiner", Keyboard.KEY_V, Category.OTHER);
		validator = (pos) -> id == WBlock.getId(pos);
		reachable = new ArrayList<BlockPos>();
		nextClosestBlocks = new ArrayList<BlockPos>();
		currentBlock = null;
	}
	
	@Override
	public void onUpdate() {
		if(this.isToggled()) {
			
			FindReachableBlocks();
			
			if(reachable.size()!=0) {
				for(BlockPos pos: reachable)
				{
				currentBlock = pos;
				if(!BlockUtils.breakBlockExtraLegit(currentBlock))
					continue;
				else break;
				}
			}
			else
				currentBlock = null;
			
			if(currentBlock == null) {
				mc.playerController.resetBlockRemoving();
				mc.gameSettings.keyBindAttack.pressed = false;
			}
			
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		ticks = MAX_TICKS;
	}
	
	public void FindReachableBlocks() {
		
		reachable.clear();
		
		BlockPos player = mc.player.getPosition();
		BlockPos pos = player;
		
		for(int x = -4; x <= 4; x++) {
			for(int y = 4; y >= -4; y--) {
				for(int z = -4; z <= 4; z++) {
					pos = player;
					pos = pos.add(x, y, z);
					if(Block.getIdFromBlock(mc.world.getBlockState(pos).getBlock()) == id) {
						reachable.add(pos);
					}
				}
			}
		}
	}
	

}

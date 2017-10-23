package zeonClient.mods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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
import zeonClient.utils.RotationUtils;
import zeonClient.utils.WBlock;
import zeonClient.utils.WMinecraft;
import zeonClient.utils.WPlayer;
import zeonClient.utils.WPlayerController;
import zeonClient.utils.BlockUtils.BlockValidator;

public class BoxMiner extends Mod {

	private int idle_ticks = 0;
	private int jump_ticks = 0;
	private int rotationcount = 0;
	private int ticks = 100;
	private int sell_ticks = 9999;
	
	public BoxMiner() {
		super("BoxMiner", "BoxMiner", Keyboard.KEY_V, Category.OTHER);
	}
	
	@Override
	public void onUpdate() {
		if(this.isToggled()) {
			
			
			//Idle
			if(idle_ticks != 0) {
				mc.gameSettings.keyBindForward.pressed = false;
				mc.gameSettings.keyBindAttack.pressed = false;
				idle_ticks--;
				return;
			}
			
			//Sell items
			if(sell_ticks  <= 187) {

				if(sell_ticks <= 50) {
					mc.player.moveRelative(0F, 0F, 0.1F, 1F);
				}
				else if(sell_ticks <= 120) {
					mc.player.moveRelative(-0.1F, 0F, 0F, 1F);
				}
				else if(sell_ticks <= 185) {
					mc.player.moveRelative(0F, 0F, 0.1F, 1F);
					mc.player.rotationYaw = 0F;
					mc.player.rotationPitch = 0F;
				}
				
				if(sell_ticks == 185) {
					mc.gameSettings.keyBindJump.pressed = false;
					mc.gameSettings.keyBindLeft.pressed = false;
					mc.gameSettings.keyBindRight.pressed = false;
					mc.player.rotationYaw += 90F;
				}
				
				if(sell_ticks == 185) {
					mc.gameSettings.keyBindUseItem.pressed = true;
				}
				
				if(sell_ticks == 187) {
					mc.gameSettings.keyBindUseItem.pressed = false;
					mc.player.sendChatMessage("/warp z");
					idle_ticks = 100;
					jump_ticks = 100;
				}
				
				sell_ticks++;
				
				return;
			}
			
			if(sell_ticks == 188 && mc.player.inventory.getFirstEmptyStack() == -1) {
				jump_ticks = 0;
				sell_ticks++;
			}
			
			// Walk back to mine
			if(jump_ticks > 0) {
				mc.player.rotationYaw = 0F;
				mc.player.rotationPitch = 0F;
				
				if(jump_ticks < 50)
					mc.gameSettings.keyBindJump.pressed = true;
				
				if(jump_ticks == 50) {
					int rand = (new Random()).nextInt();
					if(rand%2 == 0) {
						mc.gameSettings.keyBindLeft.pressed = true;
					}
					else {
						mc.gameSettings.keyBindRight.pressed = true;
					}
				}
				
				mc.gameSettings.keyBindForward.pressed = true;
				
				jump_ticks--;
				
				if(jump_ticks == 0) {
					mc.gameSettings.keyBindJump.pressed = false;
					mc.gameSettings.keyBindLeft.pressed = false;
					mc.gameSettings.keyBindRight.pressed = false;
					mc.player.rotationPitch = 35F;
					mc.player.rotationYaw = 5F;
				}
				
				return;
			}
			
			//tp if at bottom
			if(checkUnderPlayer()) {
				mc.player.sendChatMessage("/warp z");
				mc.gameSettings.keyBindForward.pressed = false;
				mc.gameSettings.keyBindAttack.pressed = false;
				idle_ticks = 100;
				jump_ticks = 100;
				System.out.println("Glowstone Under me");
				return;
			}
			
			//Check for full inventory
			if(mc.player.inventory.getFirstEmptyStack() == -1) {
				mc.player.sendChatMessage("/warp z");
				mc.gameSettings.keyBindAttack.pressed = false;
				mc.gameSettings.keyBindForward.pressed = false;
				sell_ticks = 0;
				idle_ticks = 100;
				return;
			}
			
			//Box mine
			mc.player.rotationPitch = 35F;
			mc.gameSettings.keyBindAttack.pressed = true;
			mc.gameSettings.keyBindForward.pressed = true;
			System.out.println("move forward");
			Material M = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getMaterial();
			
			if((M == Material.WOOD || M == Material.ROCK || M == Material.GLASS || M == Material.PISTON) && (Block.getIdFromBlock(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock()) != 56 && 
					Block.getIdFromBlock(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock()) != 129)) {
				mc.player.rotationYaw += 90F;
				rotationcount++;
				if(rotationcount > 4) {
					mc.player.sendChatMessage("/warp z");
					mc.gameSettings.keyBindForward.pressed = false;
					mc.gameSettings.keyBindAttack.pressed = false;
					idle_ticks = 100;
					jump_ticks = 100;
				}
				mc.player.moveRelative(0.25F, 0F, 0F, 1F);
			}
			
			else if(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getMaterial() == Material.AIR) {
				ticks--;
				if(ticks==0) {
					ticks=100;
					mc.player.moveRelative(-0.5F, 0F, 0F, 1F);
				}
			}
			else {
				rotationcount=0;
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
		idle_ticks = 0;
		jump_ticks = 0;
		rotationcount = 0;
		ticks = 100;
		sell_ticks = 9999;
	}
	
	public boolean checkUnderPlayer() {
		BlockPos under = mc.player.getPosition();
		under = under.down();
		if(Block.getIdFromBlock(mc.world.getBlockState(under).getBlock()) == 89){
			return true;
		}
		return false;
	}
	
	

}

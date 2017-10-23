package youtube.mods;

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
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import youtube.main.Category;
import youtube.utils.BlockUtils;
import youtube.utils.RotationUtils;

public class RectMiner extends Mod {

	private int ticks = 100;
	private int idle_ticks = 0;
	private int jump_ticks=0;
	private int id1 = 133;
	private int id2 = 57;
	
	private float yaw = 0;
	private float pitch = 0;
	private ArrayList<BlockPos> reachable;
	private BlockPos currentBlock;
	private Vec3d eyesPos = null;
	
	
	public RectMiner() {
		super("RectMiner", "RectMiner", Keyboard.KEY_G, Category.OTHER);
		reachable = new ArrayList<BlockPos>();
	}
	
	@Override
	public void onUpdate() {
		if(this.isToggled()) {
			
			if(idle_ticks > 0) {
				idle_ticks--;
				if(idle_ticks == 0)
					jump_ticks = 100;
				System.out.println("Waiting for ticks");
				return;
			}
			
			if(jump_ticks > 0) {
				
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
				}
				
				return;
			}
			
			if(mc.player.inventory.getFirstEmptyStack() == -1) {
				mc.player.sendChatMessage("/warp z");
				mc.gameSettings.keyBindAttack.pressed = false;
				mc.gameSettings.keyBindForward.pressed = false;
				Toggle();
				return;
			}
			
			if(checkUnderPlayer()) {
				mc.player.sendChatMessage("/warp z");
				mc.gameSettings.keyBindForward.pressed = false;
				mc.gameSettings.keyBindAttack.pressed = false;
				idle_ticks = 100;
				System.out.println("Glowstone Under me");
				return;
			}
			
			if(eyesPos == null)
				eyesPos  = RotationUtils.getEyesPos();
			
			if(yaw==0 && pitch==0)
			{
				yaw = mc.player.rotationYaw;
				pitch = mc.player.rotationPitch;
			}
			//Legit Nuker
			currentBlock = null;
			
			FindReachableBlocks();
			
			if(reachable.size()!=0) {
				
				for(BlockPos pos: reachable)
				{
					if(!BlockUtils.breakBlockExtraLegit(pos))
						continue;
					else {
						currentBlock = pos;
						mc.gameSettings.keyBindForward.pressed = false;
						break;
					}
				}
				
			}
			else
				currentBlock = null;
			
			if(currentBlock == null) {
				

				mc.player.rotationPitch = pitch;
				mc.player.rotationYaw = yaw;
				
				mc.playerController.resetBlockRemoving();
				// Nothing to break then move forward
				mc.gameSettings.keyBindAttack.pressed = true;
				mc.gameSettings.keyBindForward.pressed = true;
				System.out.println("move forward");
				Material M = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getMaterial();
				
				if((M == Material.WOOD || M == Material.ROCK || M == Material.GLASS) && (Block.getIdFromBlock(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock()) != 56 && 
						Block.getIdFromBlock(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock()) != 129)) {
					mc.player.rotationYaw += 90F;
					mc.player.moveRelative(0.25F, 0F, 0F, 1F);
				}
				
				else if(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getMaterial() == Material.AIR) {
					ticks--;
					if(ticks==0) {
						ticks=100;
						mc.player.moveRelative(-0.5F, 0F, 0F, 1F);
					}
				}
				
				eyesPos = RotationUtils.getEyesPos();
				yaw = mc.player.rotationYaw;
				pitch = mc.player.rotationPitch;
			}
			
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		mc.gameSettings.keyBindAttack.pressed = true;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mc.gameSettings.keyBindAttack.pressed = false;
		mc.gameSettings.keyBindForward.pressed = false;
		yaw = 0;
		pitch = 0;
		idle_ticks = 0;
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
					if(Block.getIdFromBlock(mc.world.getBlockState(pos).getBlock()) == id1 ||
							Block.getIdFromBlock(mc.world.getBlockState(pos).getBlock()) == id2	) {
						if((x*x + y*y + z*z) <= 18.0625 )
							reachable.add(pos);
					}
				}
			}
		}
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

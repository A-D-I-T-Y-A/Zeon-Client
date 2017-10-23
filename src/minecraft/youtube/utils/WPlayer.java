/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package youtube.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
//import net.wurstclient.WurstClient;

public final class WPlayer
{
	public static void swingArmClient()
	{
		WMinecraft.getPlayer().swingArm(EnumHand.MAIN_HAND);
	}
	
	public static void swingArmPacket()
	{
		WConnection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
	}
	
	public static void prepareAttack()
	{
		// AutoSword
		//WurstClient.INSTANCE.mods.autoSwordMod.setSlot();
		
		// Criticals
		//WurstClient.INSTANCE.mods.criticalsMod.doCritical();
	}
	
	public static void attackEntity(Entity entity)
	{
		Minecraft.getMinecraft().playerController
			.attackEntity(WMinecraft.getPlayer(), entity);
		swingArmClient();
	}
	
	public static void sendAttackPacket(Entity entity)
	{
		WConnection
			.sendPacket(new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
	}
	
	public static float getCooldown()
	{
		return WMinecraft.getPlayer().getCooledAttackStrength(0);
	}
	
	public static void addPotionEffect(Potion potion)
	{
		WMinecraft.getPlayer()
			.addPotionEffect(new PotionEffect(potion, 10801220));
	}
	
	public static void removePotionEffect(Potion potion)
	{
		WMinecraft.getPlayer().removePotionEffect(potion);
	}
	
	public static void copyPlayerModel(EntityPlayer from, EntityPlayer to)
	{
		to.getDataManager().set(EntityPlayer.PLAYER_MODEL_FLAG,
			from.getDataManager().get(EntityPlayer.PLAYER_MODEL_FLAG));
	}
}

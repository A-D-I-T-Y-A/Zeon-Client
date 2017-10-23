/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package zeonClient.utils;

import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

public final class WItem
{
	public static boolean isNull(Item item)
	{
		return item == null || item instanceof ItemAir;
	}
	
	public static boolean isNull(ItemStack stack)
	{
		return stack == null || stack.getItem() instanceof ItemAir;
	}
	
	public static int getArmorType(ItemArmor armor)
	{
		return armor.armorType.ordinal() - 2;
	}
	
	public static boolean isThrowable(ItemStack stack)
	{
		Item item = stack.getItem();
		return item instanceof ItemBow || item instanceof ItemSnowball
			|| item instanceof ItemEgg || item instanceof ItemEnderPearl
			|| item instanceof ItemSplashPotion
			|| item instanceof ItemLingeringPotion
			|| item instanceof ItemFishingRod;
	}
	
	public static boolean isPotion(ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof ItemPotion
			|| stack.getItem() instanceof ItemSplashPotion;
	}
	
	public static Item getFromRegistry(ResourceLocation location)
	{
		return Item.REGISTRY.getObject(location);
	}
}

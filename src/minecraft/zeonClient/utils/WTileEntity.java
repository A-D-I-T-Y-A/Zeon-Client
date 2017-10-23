/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package zeonClient.utils;

import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntityChest;

public final class WTileEntity
{
	public static boolean isTrappedChest(TileEntityChest chest)
	{
		return chest.getChestType() == BlockChest.Type.TRAP;
	}
}

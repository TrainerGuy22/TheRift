package dragonborn.rift.handler;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;
import dragonborn.rift.config.Items;

public class RiftFuelHandler implements IFuelHandler
{
	
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		if (fuel.itemID == Items.item_dragonscale.itemID)
		{
			return 12 * 20 * 10; // 12 items * 20 ticks/second * 10 seconds per smelt
		}
		
		return 0;
	}
	
}

package dragonborn.rift.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTerrain extends ItemBlock
{
	
	public ItemBlockTerrain(int blockID)
	{
		super(blockID);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		if (meta == 1)
			return "Dragongrass";
		return "Ender Dirt";
	}
	
	@Override
	public String getItemDisplayName(ItemStack itemStack)
	{
		return getItemStackDisplayName(itemStack);
	}
	
}

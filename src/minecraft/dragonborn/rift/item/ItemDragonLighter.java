package dragonborn.rift.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemDragonLighter extends ItemFlintAndSteel
{
	private boolean	relayUse;
	
	public ItemDragonLighter(int itemID)
	{
		super(itemID);
		this.relayUse = true;
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":dragon_lighter");
	}
	
	@Override
	public int getMaxDamage()
	{
		return 0;
	}
	
	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
	{
		if (side == 0)
		{
			--y;
		}
		
		if (side == 1)
		{
			++y;
		}
		
		if (side == 2)
		{
			--z;
		}
		
		if (side == 3)
		{
			++z;
		}
		
		if (side == 4)
		{
			--x;
		}
		
		if (side == 5)
		{
			++x;
		}
		
		if (!player.canPlayerEdit(x, y, z, side, stack))
		{
			return false;
		}
		else
		{
			if (world.isAirBlock(x, y, z))
			{
				world.setBlock(x, y, z, Block.fire.blockID);
			}
			
			if (relayUse)
			{
				world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.explode", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				this.relayUse = false;
				for (int tX = x - 2; tX <= x + 2; tX++)
				{
					for (int tY = y - 2; tY <= y + 2; tY++)
					{
						for (int tZ = z - 2; tZ <= z + 2; tZ++)
						{
							for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
							{
								onItemUse(stack, player, world, tX, tY, tZ, direction.ordinal(), 0.5f, 0.5f, 0.5f);
							}
						}
					}
				}
				this.relayUse = true;
				stack.stackSize--;
			}
			
			return true;
		}
	}
	
}

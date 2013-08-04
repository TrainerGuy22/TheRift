package dragonborn.rift.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;

public class ItemDragonscale extends Item
{
	
	public ItemDragonscale(int itemID)
	{
		super(itemID);
		setCreativeTab(CreativeTabs.tabMisc);
		setHasSubtypes(true);
		setUnlocalizedName("dragonscale");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale");
	}
	
}

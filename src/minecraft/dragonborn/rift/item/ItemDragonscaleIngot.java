package dragonborn.rift.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;

public class ItemDragonscaleIngot extends Item
{
	
	public ItemDragonscaleIngot(int itemID)
	{
		super(itemID);
		setCreativeTab(CreativeTabs.tabMaterials);
		setHasSubtypes(true);
		setUnlocalizedName("dragonscaleIngot");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale_ingot");
	}
	
}

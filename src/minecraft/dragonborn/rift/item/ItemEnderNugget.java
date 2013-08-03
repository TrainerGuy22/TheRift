package dragonborn.rift.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;

public class ItemEnderNugget extends Item
{
	
	public ItemEnderNugget(int itemID)
	{
		super(itemID);
		setUnlocalizedName("enderNugget");
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":endernugget");
	}
}

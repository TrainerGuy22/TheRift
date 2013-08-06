package dragonborn.rift.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;

public class ItemDragonscaleHoe extends ItemHoe
{
	
	public ItemDragonscaleHoe(int itemID)
	{
		super(itemID, EnumToolMaterial.EMERALD);
		setUnlocalizedName("dragonscaleHoe");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale_hoe");
	}
	
}

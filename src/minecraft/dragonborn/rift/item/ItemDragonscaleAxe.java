<<<<<<< HEAD
package dragonborn.rift.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;

public class ItemDragonscaleAxe extends ItemAxe
{
	
	public ItemDragonscaleAxe(int itemID)
	{
		super(itemID, EnumToolMaterial.EMERALD);
		this.efficiencyOnProperMaterial = 14.0f;
		this.damageVsEntity = 7.0f;
		setUnlocalizedName("dragonscaleAxe");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale_axe");
	}
	
}
=======
package dragonborn.rift.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;

public class ItemDragonscaleAxe extends ItemAxe
{
	
	public ItemDragonscaleAxe(int itemID)
	{
		super(itemID, EnumToolMaterial.EMERALD);
		this.efficiencyOnProperMaterial = 14.0f;
		this.damageVsEntity = 7.0f;
		setUnlocalizedName("dragonscaleAxe");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale_axe");
	}
	
}
>>>>>>> ee9f4d8201f7386f8399ec85abd9a28abf1d8dbb

<<<<<<< HEAD
package dragonborn.rift.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;

public class ItemDragonscaleShovel extends ItemSpade
{
	
	public ItemDragonscaleShovel(int itemID)
	{
		super(itemID, EnumToolMaterial.EMERALD);
		this.efficiencyOnProperMaterial = 14.0f;
		this.damageVsEntity = 7.0f;
		setUnlocalizedName("dragonscaleShovel");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale_shovel");
	}
	
}
=======
package dragonborn.rift.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;

public class ItemDragonscaleShovel extends ItemSpade
{
	
	public ItemDragonscaleShovel(int itemID)
	{
		super(itemID, EnumToolMaterial.EMERALD);
		this.efficiencyOnProperMaterial = 14.0f;
		this.damageVsEntity = 7.0f;
		setUnlocalizedName("dragonscaleShovel");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale_shovel");
	}
	
}
>>>>>>> ee9f4d8201f7386f8399ec85abd9a28abf1d8dbb

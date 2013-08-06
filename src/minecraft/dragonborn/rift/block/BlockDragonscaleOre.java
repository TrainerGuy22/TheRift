package dragonborn.rift.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import dragonborn.rift.config.Items;

public class BlockDragonscaleOre extends Block
{
	public BlockDragonscaleOre(int blockID, Material material)
	{
		super(blockID, material);
		setHardness(10.0f);
		setResistance(20.0f);
		setUnlocalizedName("dragonscaleOre");
		setCreativeTab(CreativeTabs.tabBlock);
		setLightValue(0.1f);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.blockIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale_ore");
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> dropList = new ArrayList<ItemStack>();
		dropList.add(new ItemStack(Items.item_dragonscale, fortune + 1));
		return dropList;
	}
	
}

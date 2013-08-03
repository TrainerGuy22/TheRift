package dragonborn.rift.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;

public class BlockDragonleaves extends Block implements IShearable
{
	public BlockDragonleaves(int id, Material material)
	{
		super(id, material);
		setUnlocalizedName("dragonleaves");
		setHardness(2.5f);
		setResistance(2.0f);
		setLightValue(0.8f);
		setCreativeTab(CreativeTabs.tabBlock);
		setStepSound(Block.soundGrassFootstep);
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
	{
		if (player.getCurrentEquippedItem() != null)
		{
			if (player.getCurrentEquippedItem().itemID == Item.shears.itemID)
				return 0.5f;
		}
		return 0.1f;
	}
	
	@Override
	public boolean isShearable(ItemStack item, World world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, 0));
		return ret;
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		return new ArrayList<ItemStack>();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.blockIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonleaves");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		double xOff = rand.nextDouble() * 2.5 - 1.25;
		double yOff = rand.nextDouble() * 2.5 - 1.25;
		double zOff = rand.nextDouble() * 2.5 - 1.25;
		double xSpd = rand.nextDouble() * 2.0 - 1.0;
		double ySpd = rand.nextDouble() * 2.0 - 1.0;
		double zSpd = rand.nextDouble() * 2.0 - 1.0;
		world.spawnParticle("portal", x + 0.5 + xOff, y + 0.5 + yOff, z + 0.5 + zOff, xSpd, ySpd, zSpd);
	}
	
}

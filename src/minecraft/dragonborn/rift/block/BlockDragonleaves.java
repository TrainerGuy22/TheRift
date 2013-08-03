package dragonborn.rift.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;

public class BlockDragonleaves extends Block
{
	public BlockDragonleaves(int id, Material material)
	{
		super(id, material);
		setUnlocalizedName("dragonleaves");
		setHardness(1.5f);
		setResistance(2.0f);
		setLightValue(0.8f);
		setCreativeTab(CreativeTabs.tabBlock);
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

package dragonborn.rift.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import dragonborn.rift.config.Blocks;
import dragonborn.rift.util.RiftUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BlockRiftTerrain extends Block
{
	private Icon	dirt;
	private Icon	grass;
	private Icon	grass_side;
	
	public BlockRiftTerrain(int id, Material material)
	{
		super(id, material);
		setHardness(0.5f); // dirt base hardness
		setResistance(1.0f); // very brittle
		setUnlocalizedName("riftTerrain");
		setStepSound(soundGrassFootstep);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
	{
		return plant instanceof BlockEnderFlower;
	}
	
	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!world.isRemote)
		{
			if (world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2)
			{
				world.setBlockMetadataWithNotify(x, y, z, 0, RiftUtil.NMASK_BOTH);
			}
			else if (world.getBlockLightValue(x, y + 1, z) >= 9)
			{
				for (int i = 0; i < 4; i++)
				{
					int tX = x + rand.nextInt(3) - 1;
					int tY = y + rand.nextInt(5) - 3;
					int tZ = z + rand.nextInt(3) - 1;
					int tID = world.getBlockId(tX, tY + 1, tZ);
					
					if (world.getBlockId(tX, tY, tZ) == Blocks.blockID_dragonTerrain && world.getBlockMetadata(tX, tY, tZ) == 0 && world.getBlockLightValue(tX, tY + 1, tZ) >= 4 && world.getBlockLightOpacity(tX, tY + 1, tZ) <= 2)
					{
						world.setBlockMetadataWithNotify(tX, tY, tZ, 1, RiftUtil.NMASK_BOTH);
					}
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int meta = world.getBlockMetadata(x, y, z);
		double xOff = rand.nextDouble() - 0.5;
		double yOff = rand.nextDouble() / 4.0;
		double zOff = rand.nextDouble() - 0.5;
		if (meta == 1 && rand.nextInt(5) == 0)
			world.spawnParticle("portal", x + 0.5 + xOff, y + 1.5 + yOff, z + 0.5 + zOff, 0.0, -1.0, 0.0);
	}
	
	@Override
	public void registerIcons(IconRegister register)
	{
		dirt = register.registerIcon(RiftMod.MOD_ID + ":enderdirt");
		grass = register.registerIcon(RiftMod.MOD_ID + ":dragongrass");
		grass_side = register.registerIcon(RiftMod.MOD_ID + ":dragongrass_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		if (meta == 1)
		{
			if (side == ForgeDirection.DOWN.ordinal())
				return dirt;
			else if (side == ForgeDirection.UP.ordinal())
				return grass;
			else
				return grass_side;
		}
		
		return dirt;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		return new ItemStack(this, 1, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		return getIcon(side, meta);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		float baseHardness = super.getBlockHardness(world, x, y, z);
		if (meta == 1) // harder for grass
			baseHardness += 0.2f;
		return baseHardness;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int blockID, CreativeTabs creativeTab, List list)
	{
		if (blockID == this.blockID && creativeTab == CreativeTabs.tabBlock)
		{
			list.add(new ItemStack(this, 1, 0)); // Ender Dirt
			list.add(new ItemStack(this, 1, 1)); // Dragongrass
		}
	}
}

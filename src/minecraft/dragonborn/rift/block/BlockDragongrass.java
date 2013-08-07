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

public class BlockDragongrass extends Block
{
	private Icon	top;
	private Icon	side;
	
	public BlockDragongrass(int id, Material material)
	{
		super(id, material);
		setHardness(0.7f);
		setResistance(1.0f);
		setUnlocalizedName("dragonGrass");
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
				world.setBlock(x, y, z, Blocks.blockID_enderDirt, 0, RiftUtil.NMASK_NONE);
			}
			else if (world.getBlockLightValue(x, y + 1, z) >= 9)
			{
				for (int i = 0; i < 4; i++)
				{
					int tX = x + rand.nextInt(3) - 1;
					int tY = y + rand.nextInt(5) - 3;
					int tZ = z + rand.nextInt(3) - 1;
					int tID = world.getBlockId(tX, tY + 1, tZ);
					
					if (world.getBlockId(tX, tY, tZ) == Blocks.blockID_enderDirt && world.getBlockMetadata(tX, tY, tZ) == 0 && world.getBlockLightValue(tX, tY + 1, tZ) >= 4 && world.getBlockLightOpacity(tX, tY + 1, tZ) <= 2)
					{
						world.setBlock(tX, tY, tZ, Blocks.blockID_dragonGrass, 0, RiftUtil.NMASK_NONE);
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
		this.top = register.registerIcon(RiftMod.MOD_ID + ":dragongrass");
		this.side = register.registerIcon(RiftMod.MOD_ID + ":dragongrass_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		if (side == ForgeDirection.DOWN.ordinal())
			return Blocks.block_enderDirt.getIcon(ForgeDirection.DOWN.ordinal(), 0);
		else if (side == ForgeDirection.UP.ordinal())
			return this.top;
		else
			return this.side;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		return getIcon(side, meta);
	}
}

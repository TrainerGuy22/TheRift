package dragonborn.rift.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import dragonborn.rift.config.Blocks;
import dragonborn.rift.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockDragonscaleBrick extends Block
{
	
	public BlockDragonscaleBrick(int blockID, Material material)
	{
		super(blockID, material);
		setHardness(15.0f);
		setResistance(2000.0f); // blastproof
		setLightValue(0.2f);
		setUnlocalizedName("dragonscaleBlock");
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.blockIcon = register.registerIcon(RiftMod.MOD_ID + ":dragonscale_block");
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID)
	{
		int topID = world.getBlockId(x, y + 1, z);
		if (topID == Block.fire.blockID && world.provider.dimensionId == Config.RIFT_DIMENSION_ID)
		{
			tryToMakePortal(world, x, y + 1, z);
		}
	}
	
	/**
	 * Checks to see if this location is valid to create a portal and will return True if it does. Args: world, x, y, z
	 */
	private boolean tryToMakePortal(World world, int x, int y, int z)
	{
		byte xAxis = 0;
		byte zAxis = 0;
		
		if (world.getBlockId(x - 1, y, z) == Blocks.blockID_dragonscaleBlock || world.getBlockId(x + 1, y, z) == Blocks.blockID_dragonscaleBlock)
		{
			xAxis = 1;
		}
		
		if (world.getBlockId(x, y, z - 1) == Blocks.blockID_dragonscaleBlock || world.getBlockId(x, y, z + 1) == Blocks.blockID_dragonscaleBlock)
		{
			zAxis = 1;
		}
		
		if (xAxis == zAxis)
		{
			return false;
		}
		else
		{
			if (world.isAirBlock(x - xAxis, y, z - zAxis))
			{
				x -= xAxis;
				z -= zAxis;
			}
			
			for (int checkDist = -1; checkDist <= 2; ++checkDist)
			{
				for (int checkHeight = -1; checkHeight <= 3; ++checkHeight)
				{
					boolean checkThisHeight = checkDist == -1 || checkDist == 2 || checkHeight == -1 || checkHeight == 3;
					
					if (checkDist != -1 && checkDist != 2 || checkHeight != -1 && checkHeight != 3)
					{
						int checkedID = world.getBlockId(x + xAxis * checkDist, y + checkHeight, z + zAxis * checkDist);
						boolean isAirBlock = world.isAirBlock(x + xAxis * checkDist, y + checkHeight, z + zAxis * checkDist);
						
						if (checkThisHeight)
						{
							if (checkedID != Blocks.blockID_dragonscaleBlock/* && checkedID != Block.fire.blockID */)
							{
								System.out.println(String.format("1. Bad block @ (%d, %d, %d)", x + xAxis * checkDist, y + checkHeight, z + zAxis * checkDist));
								return false;
							}
						}
						else if (!isAirBlock && checkedID != Block.fire.blockID)
						{
							System.out.println(String.format("2. Bad block @ (%d, %d, %d)", x + xAxis * checkDist, y + checkHeight, z + zAxis * checkDist));
							return false;
						}
					}
				}
			}
			
			for (int checkDist = 0; checkDist < 2; ++checkDist)
			{
				for (int checkHeight = 0; checkHeight < 3; ++checkHeight)
				{
					world.setBlock(x + xAxis * checkDist, y + checkHeight, z + zAxis * checkDist, Blocks.blockID_riftPortal, 0, 2);
				}
			}
			
			return true;
		}
	}
	
}

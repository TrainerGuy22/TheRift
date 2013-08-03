package dragonborn.rift.dimension;

import java.util.Random;

import dragonborn.rift.config.Blocks;
import dragonborn.rift.util.RiftUtil;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDragonTree extends WorldGenerator
{
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		/** Constants */
		int height = random.nextInt(2) + 3;
		int radius = 2;
		
		/** Generate trunk */
		for (int gY = y; gY <= y + height; gY++)
		{
			world.setBlock(x, gY, z, Blocks.blockID_dragonwood, 0, RiftUtil.NMASK_NONE);
		}
		
		/** Generate leaves */
		for (int gX = x - radius; gX <= x + radius; gX++)
		{
			for (int gZ = z - radius; gZ <= z + radius; gZ++)
			{
				if ((gX == x - radius || gX == x + radius) && (gZ == z - radius || gZ == z + radius))
					continue; // don't generate corners
				
				if (gX == x && gZ == z)
				{
					for (int gY = y + height + 1; gY <= y + height + 2; gY++)
					{
						world.setBlock(gX, gY, gZ, Blocks.blockID_dragonleaves, 0, RiftUtil.NMASK_NONE);
					}
				}
				else
				{
					for (int gY = y + (height / 2); gY <= y + height; gY++)
					{
						world.setBlock(gX, gY, gZ, Blocks.blockID_dragonleaves, 0, RiftUtil.NMASK_NONE);
					}
				}
			}
		}
		
		return true;
	}
	
}

package dragonborn.rift.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.IWorldGenerator;
import dragonborn.rift.config.Blocks;
import dragonborn.rift.config.Config;
import dragonborn.rift.util.RiftUtil;

public class WorldGenRiftTerrain implements IWorldGenerator
{
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if (world.provider.dimensionId != Config.DIMENSION_ID)
			return;
		
		int x = chunkX * 16;
		int z = chunkZ * 16;
		
		for (int gX = x; gX < x + 16; gX++)
		{
			for (int gZ = z; gZ < z + 16; gZ++)
			{
				for (int gY = 0; gY < 256; gY++)
				{
					int id = world.getBlockId(gX, gY, gZ);
					if (id == Block.stone.blockID)
					{
						world.setBlock(gX, gY, gZ, Block.whiteStone.blockID, 0, RiftUtil.NMASK_NONE);
					}
					else if (id == Block.gravel.blockID || id == Block.dirt.blockID || id == Block.sand.blockID)
					{
						int meta = 0;
						if (!world.isBlockSolidOnSide(gX, gY + 1, gZ, ForgeDirection.UP) || world.isAirBlock(gX, gY + 1, gZ))
							meta = 1;
						world.setBlock(gX, gY, gZ, Blocks.blockID_dragonTerrain, meta, RiftUtil.NMASK_NONE);
					}
					else if (id == Block.grass.blockID)
					{
						world.setBlock(gX, gY, gZ, Blocks.blockID_dragonTerrain, 1, RiftUtil.NMASK_NONE);
					}
					else
					// we don't want ANYTHING else.
					{
						world.setBlock(gX, gY, gZ, 0, 0, RiftUtil.NMASK_NONE);
					}
				}
			}
		}
	}
	
}

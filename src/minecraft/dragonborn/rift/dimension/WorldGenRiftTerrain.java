package dragonborn.rift.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenRiftTerrain extends WorldGenerator
{
	
	@Override
	public boolean generate(World world, Random random, int chunkX, int genY, int chunkZ)
	{
		for (int x = chunkX; x < chunkX + 16; x++)
		{
			for (int z = chunkZ; z < chunkZ + 16; z++)
			{
				world.setBlock(x, 0, z, Block.bedrock.blockID);
				for (int y = 1; y <= 16; y++)
				{
					world.setBlock(x, y, z, Block.oreDiamond.blockID);
				}
			}
		}
		
		return true;
	}
	
}

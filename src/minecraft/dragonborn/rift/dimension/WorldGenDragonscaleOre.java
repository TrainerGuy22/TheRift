package dragonborn.rift.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import dragonborn.rift.config.Blocks;
import dragonborn.rift.config.Config;
import dragonborn.rift.util.RiftUtil;

public class WorldGenDragonscaleOre implements IWorldGenerator
{
	
	public WorldGenDragonscaleOre()
	{
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if (world.provider.dimensionId != Config.DIMENSION_ID)
			return;
		
		for (int i = 0; i < random.nextInt(2) + 2; i++) // generate between 2 and 4 veins per chunk
		{
			int x, y, z;
			x = chunkX * 16 + random.nextInt(16);
			z = chunkZ * 16 + random.nextInt(16);
			y = random.nextInt(15) + 5;
			if (world.getBlockId(x, y, z) == Block.whiteStone.blockID)
			{
				System.out.println(String.format("Placing ore at (%d, %d, %d)", x, y, z));
				world.setBlock(x, y, z, Blocks.blockID_dragonscaleOre, 0, RiftUtil.NMASK_NONE);
			}
		}
	}
	
}

package dragonborn.rift.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import dragonborn.rift.config.Blocks;
import dragonborn.rift.config.Config;
import dragonborn.rift.util.RiftUtil;

public class WorldGenRiftTerrain implements IWorldGenerator
{
	private WorldGenDragonTree	treeGen;
	
	public WorldGenRiftTerrain()
	{
		this.treeGen = new WorldGenDragonTree();
	}
	
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
				/** Replace non-wanted blocks */
				for (int gY = 128; gY >= 0; gY--)
				{
					int curID = world.getBlockId(gX, gY, gZ);
					Material curMat = world.getBlockMaterial(gX, gY, gZ);
					if (curID == Block.dirt.blockID || curID == Block.sand.blockID || curID == Block.gravel.blockID)
					{
						world.setBlock(gX, gY, gZ, Blocks.blockID_dragonTerrain, 0, RiftUtil.NMASK_NONE);
					}
					else if (curID == Block.stone.blockID || curMat == Material.rock)
					{
						world.setBlock(gX, gY, gZ, Block.whiteStone.blockID, 0, RiftUtil.NMASK_NONE);
					}
					else
					{
						world.setBlock(gX, gY, gZ, 0, 0, RiftUtil.NMASK_NONE);
					}
				}
				
				int topY = world.getTopSolidOrLiquidBlock(gX, gZ);
				
				/** Implant dirt */
				for (int gY = topY - 1; gY >= Math.max(topY - 4, 0); gY--)
				{
					world.setBlock(gX, gY, gZ, Blocks.blockID_dragonTerrain, 0, RiftUtil.NMASK_NONE);
				}
				
				/** Overlay grass */
				world.setBlock(gX, topY, gZ, Blocks.blockID_dragonTerrain, 1, RiftUtil.NMASK_NONE);
				
				/** Underlay bedrock */
				world.setBlock(gX, 0, gZ, Block.bedrock.blockID, 0, RiftUtil.NMASK_NONE);
				
				/** Add a chance of flowers */
				if (random.nextInt(32) == 0 && world.getBlockId(gX, topY, gZ) == Blocks.blockID_dragonTerrain)
					world.setBlock(gX, topY + 1, gZ, Blocks.blockID_enderFlower, 0, RiftUtil.NMASK_NONE);
			}
		}
		
		if (random.nextInt(4) == 0) // every 4 chunks get a chance to spawn trees
		{
			/** Generate trees */
			int numTrees = random.nextInt(2);
			for (int i = 0; i <= numTrees; i++)
			{
				int treeX = random.nextInt(8) + x + 4;
				int treeZ = random.nextInt(8) + z + 4;
				int treeY = world.getTopSolidOrLiquidBlock(treeX, treeZ);
				treeGen.generate(world, random, treeX, treeY, treeZ);
			}
		}
	}
}

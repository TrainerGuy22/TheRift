package dragonborn.rift.dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import dragonborn.rift.config.Blocks;
import dragonborn.rift.util.RiftUtil;

public class BiomeGenRift extends BiomeGenBase
{
	public static BiomeGenRift			biomeGenRift	= (BiomeGenRift) new BiomeGenRift(158).setColor(0x7E6CAD).setDisableRain().setBiomeName("Rift");
	
	private ArrayList<SpawnListEntry>	spawnableList;
	private WorldGenDragonTree			genTrees;
	
	public BiomeGenRift(int biomeID)
	{
		super(biomeID);
		this.fillerBlock = (byte) Blocks.blockID_enderDirt;
		this.topBlock = (byte) Blocks.blockID_dragonGrass;
		
		spawnableList = new ArrayList<SpawnListEntry>();
		spawnableList.add(new SpawnListEntry(EntityEnderman.class, 4, 4, 4));
		
		genTrees = new WorldGenDragonTree();
	}
	
	@Override
	public void decorate(World world, Random random, int chunkX, int chunkZ)
	{
		/** Trees */
		if (random.nextInt(4) == 0)
		{
			int genX = chunkX + random.nextInt(16);
			int genZ = chunkZ + random.nextInt(16);
			genTrees.generate(world, random, genX, world.getTopSolidOrLiquidBlock(genX, genZ), genZ);
		}
		
		/** Flowers */
		int numFlowers = random.nextInt(4); // up to 4 flowers per chunk
		for (int flower = 0; flower < numFlowers; flower++)
		{
			int flowerX = chunkX + random.nextInt(16);
			int flowerZ = chunkZ + random.nextInt(16);
			int flowerY = world.getTopSolidOrLiquidBlock(flowerX, flowerZ);
			int groundBlockID = world.getBlockId(flowerX, flowerY - 1, flowerZ);
			int airBlockID = world.getBlockId(flowerX, flowerY, flowerZ);
			if (groundBlockID == Blocks.blockID_dragonGrass || groundBlockID == Blocks.blockID_enderDirt && airBlockID == 0) // prevents treeflowers
				world.setBlock(flowerX, flowerY, flowerZ, Blocks.blockID_enderFlower, 0, RiftUtil.NMASK_NONE);
		}
		
		/** Ores */
		generateOre(new WorldGenMinable(Blocks.blockID_dragonscaleOre, 4, Block.whiteStone.blockID), world, random, chunkX, chunkZ, 4, 4, 16);
	}
	
	/**
	 * Standard ore generation helper. Generates most ores.
	 */
	protected void generateOre(WorldGenerator worldGen, World world, Random random, int chunkX, int chunkZ, int veinsPerChunk, int minY, int maxY)
	{
		for (int vein = 0; vein < veinsPerChunk; vein++)
		{
			int genX = chunkX + random.nextInt(16);
			int genY = random.nextInt(maxY - minY) + minY;
			int genZ = chunkZ + random.nextInt(16);
			worldGen.generate(world, random, genX, genY, genZ);
		}
	}
	
	@Override
	public List getSpawnableList(EnumCreatureType type)
	{
		if (type == EnumCreatureType.monster)
		{
			return this.spawnableList;
		}
		
		return new ArrayList();
	}
}

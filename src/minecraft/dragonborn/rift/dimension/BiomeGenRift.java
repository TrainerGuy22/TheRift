package dragonborn.rift.dimension;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import dragonborn.rift.config.Blocks;

public class BiomeGenRift extends BiomeGenBase
{
	public static BiomeGenRift			biomeGenRift	= (BiomeGenRift) new BiomeGenRift(158).setColor(0x7E6CAD).setDisableRain().setBiomeName("Rift");
	
	private ArrayList<SpawnListEntry>	spawnableList;
	
	public BiomeGenRift(int biomeID)
	{
		super(biomeID);
		this.fillerBlock = (byte) Block.whiteStone.blockID;
		this.topBlock = (byte) Blocks.blockID_dragonTerrain;
		
		spawnableList = new ArrayList<SpawnListEntry>();
		spawnableList.add(new SpawnListEntry(EntityEnderman.class, 4, 4, 4));
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

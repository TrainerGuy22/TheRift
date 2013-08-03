package dragonborn.rift.dimension;

import java.util.Random;

import dragonborn.rift.util.RiftUtil;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenRift extends BiomeGenBase
{
	public static BiomeGenRift biomeGenRift = (BiomeGenRift) new BiomeGenRift(158).setColor(0x7E6CAD).setDisableRain().setBiomeName("Rift");

	public BiomeGenRift(int biomeID)
	{
		super(biomeID);
	}
	
	@Override
	public void decorate(World world, Random random, int chunkX, int chunkZ)
	{
		
	}
}

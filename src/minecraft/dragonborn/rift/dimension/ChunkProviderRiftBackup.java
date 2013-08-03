package dragonborn.rift.dimension;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import dragonborn.rift.util.RiftUtil;

public class ChunkProviderRiftBackup implements IChunkProvider
{
	private World			worldObj;
	private Random			random;
	private final byte[]	field_82700_c	= new byte[256];
	private final byte[]	field_82698_d	= new byte[256];
	
	private WorldGenRiftTerrain terrainGen;
	
	public ChunkProviderRiftBackup(World world, long seed)
	{
		this.worldObj = world;
		this.random = new Random(seed);
		this.terrainGen = new WorldGenRiftTerrain();
	}
	
	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	public Chunk loadChunk(int par1, int par2)
	{
		return this.provideChunk(par1, par2);
	}
	
	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		RiftUtil.log(String.format("Providing chunk (%d, %d)...", chunkX, chunkZ));
		Chunk chunk = new Chunk(this.worldObj, chunkX, chunkZ);
		
		for (int k = 0; k < this.field_82700_c.length; ++k)
		{
			int l = k >> 4;
			ExtendedBlockStorage storage = chunk.getBlockStorageArray()[l];
			
			if (storage == null)
			{
				storage = new ExtendedBlockStorage(k, !this.worldObj.provider.hasNoSky);
				chunk.getBlockStorageArray()[l] = storage;
			}
			
			for (int blockX = 0; blockX < 16; ++blockX)
			{
				for (int blockZ = 0; blockZ < 16; ++blockZ)
				{
					storage.setExtBlockID(blockX, k & 15, blockZ, this.field_82700_c[k] & 255);
					storage.setExtBlockMetadata(blockX, k & 15, blockZ, this.field_82698_d[k]);
				}
			}
		}
		
		chunk.generateSkylightMap();
		BiomeGenBase[] biomes = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, chunkX * 16, chunkZ * 16, 16, 16);
		byte[] biomeIDs = chunk.getBiomeArray();
		
		for (int biome = 0; biome < biomeIDs.length; biome++)
		{
			biomeIDs[biome] = (byte) biomes[biome].biomeID;
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	/**
	 * Checks to see if a chunk exists at x, z
	 */
	public boolean chunkExists(int chunkX, int chunkZ)
	{
		return true;
	}
	
	/**
	 * Populates chunk with ores etc etc
	 */
	public void populate(IChunkProvider provider, int chunkX, int chunkZ)
	{
		RiftUtil.log(String.format("Populating chunk (%d, %d)...", chunkX, chunkZ));
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(blockX + 16, blockZ + 16);
		this.random.setSeed(this.worldObj.getSeed());
		long randX = this.random.nextLong() / 2L * 2L + 1L;
		long randZ = this.random.nextLong() / 2L * 2L + 1L;
		this.random.setSeed((long) chunkX * randX + (long) chunkZ * randZ ^ this.worldObj.getSeed());
		
		terrainGen.generate(worldObj, random, chunkX, 0, chunkZ);
	}
	
	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If passed false, save up to two chunks.
	 * Return true if all chunks have been saved.
	 */
	public boolean saveChunks(boolean mode, IProgressUpdate progress)
	{
		return true;
	}
	
	public void func_104112_b()
	{
	}
	
	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
	 */
	public boolean unloadQueuedChunks()
	{
		return false;
	}
	
	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	public boolean canSave()
	{
		return true;
	}
	
	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z)
	{
		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x, z);
		return biome == null ? null : biome.getSpawnableList(creatureType);
	}
	
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public String makeString()
	{
		return "RiftProvider";
	}

	@Override
	public ChunkPosition findClosestStructure(World world, String s, int i, int j, int k)
	{
		return null;
	}

	@Override
	public void recreateStructures(int i, int j)
	{
		
	}
}

package dragonborn.rift.dimension;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderRift implements IChunkProvider
{
	private World					world;
	private Random					random;
	private NoiseGeneratorOctaves	noiseGen1;
	private NoiseGeneratorOctaves	noiseGen2;
	private NoiseGeneratorOctaves	noiseGen3;
	private NoiseGeneratorOctaves	noiseGen4;
	private NoiseGeneratorOctaves	noiseGen5;
	private NoiseGeneratorOctaves	noiseGen6;
	private NoiseGeneratorOctaves	noiseGen7;
	private NoiseGeneratorOctaves	noiseGenEight;
	private double					noiseField[];
	private double					noiseField3[];
	private double					noiseField1[];
	private double					noiseField2[];
	private double					noiseField6[];
	private double					noiseField7[];
	private MapGenBase				caveGen;
	private BiomeGenBase			biomes[];
	private int						field_28088_i[][];
	private double[]				stoneNoise	= new double[256];
	
	public ChunkProviderRift(World par1World, long par2)
	{
		this.world = par1World;
		this.random = new Random(par2);
		caveGen = new MapGenCaves();
		field_28088_i = new int[32][32];
		noiseGen1 = new NoiseGeneratorOctaves(random, 16);
		noiseGen2 = new NoiseGeneratorOctaves(random, 16);
		noiseGen3 = new NoiseGeneratorOctaves(random, 8);
		noiseGen4 = new NoiseGeneratorOctaves(random, 4);
		noiseGen5 = new NoiseGeneratorOctaves(random, 4);
		noiseGen6 = new NoiseGeneratorOctaves(random, 10);
		noiseGen7 = new NoiseGeneratorOctaves(random, 16);
		noiseGenEight = new NoiseGeneratorOctaves(random, 8);
	}
	
	/**
	 * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
	 * temperature is low enough
	 */
	public void generateTerrain(int i, int j, byte[] abyte0)
	{
		byte byte0 = 2;
		int k = byte0 + 1;
		byte byte1 = 33;
		int l = byte0 + 1;
		noiseField = initializeNoiseField(noiseField, i * byte0, 0, j * byte0, k, byte1, l);
		for (int i1 = 0; i1 < byte0; i1++)
		{
			for (int j1 = 0; j1 < byte0; j1++)
			{
				for (int k1 = 0; k1 < 32; k1++)
				{
					double d = 0.25D;
					double d1 = noiseField[((i1 + 0) * l + (j1 + 0)) * byte1 + (k1 + 0)];
					double d2 = noiseField[((i1 + 0) * l + (j1 + 1)) * byte1 + (k1 + 0)];
					double d3 = noiseField[((i1 + 1) * l + (j1 + 0)) * byte1 + (k1 + 0)];
					double d4 = noiseField[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 0)];
					double d5 = (noiseField[((i1 + 0) * l + (j1 + 0)) * byte1 + (k1 + 1)] - d1) * d;
					double d6 = (noiseField[((i1 + 0) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d2) * d;
					double d7 = (noiseField[((i1 + 1) * l + (j1 + 0)) * byte1 + (k1 + 1)] - d3) * d;
					double d8 = (noiseField[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d4) * d;
					for (int l1 = 0; l1 < 4; l1++)
					{
						double d9 = 0.125D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for (int i2 = 0; i2 < 8; i2++)
						{
							int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
							char c = '\200';
							double d14 = 0.125D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							for (int k2 = 0; k2 < 8; k2++)
							{
								int l2 = 0;
								if (d15 > 0.0D)
								{
									l2 = Block.whiteStone.blockID;
								}
								abyte0[j2] = (byte) l2;
								j2 += c;
								d15 += d16;
							}
							
							d10 += d12;
							d11 += d13;
						}
						
						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
					
				}
				
			}
			
		}
	}
	
	/**
	 * Replaces the stone that was placed in with blocks that match the biome
	 */
	public void replaceBlocksForBiome(int par1, int par2, byte[] par3ArrayOfByte, BiomeGenBase[] par4ArrayOfBiomeGenBase)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, par1, par2, par3ArrayOfByte, par4ArrayOfBiomeGenBase);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return;
		
		byte b0 = 63;
		double d0 = 0.03125D;
		this.stoneNoise = this.noiseGen4.generateNoiseOctaves(this.stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);
		
		for (int k = 0; k < 16; ++k)
		{
			for (int l = 0; l < 16; ++l)
			{
				BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[l + k * 16];
				float f = biomegenbase.getFloatTemperature();
				int i1 = (int) (this.stoneNoise[k + l * 16] / 3.0D + 3.0D + this.random.nextDouble() * 0.25D);
				int j1 = -1;
				byte b1 = biomegenbase.topBlock;
				byte b2 = biomegenbase.fillerBlock;
				
				for (int k1 = 127; k1 >= 0; --k1)
				{
					int l1 = (l * 16 + k) * 128 + k1;
					byte b3 = par3ArrayOfByte[l1];
					
					if (b3 == 0)
					{
						j1 = -1;
					}
					else if (b3 == Block.whiteStone.blockID)
					{
						if (j1 == -1)
						{
							if (i1 <= 0)
							{
								b1 = 0;
								b2 = (byte) Block.whiteStone.blockID;
							}
							else if (k1 >= b0 - 4 && k1 <= b0 + 1)
							{
								b1 = biomegenbase.topBlock;
								b2 = biomegenbase.fillerBlock;
							}
							
							if (k1 < b0 && b1 == 0)
							{
								if (f < 0.15F)
								{
									b1 = (byte) Block.glowStone.blockID;
								}
								else
								{
									b1 = (byte) Block.lavaStill.blockID;
								}
							}
							
							j1 = i1;
							par3ArrayOfByte[l1] = b1;
						}
						else if (j1 > 0)
						{
							--j1;
							par3ArrayOfByte[l1] = b2;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Replaces the stone that was placed in with blocks that match the biome
	 */
	/*
	 * public void replaceBlocksForBiome(int par1, int par2, byte[] blocks, BiomeGenBase[] par4ArrayOfBiomeGenBase)
	 * {
	 * ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, par1, par2, blocks, par4ArrayOfBiomeGenBase);
	 * MinecraftForge.EVENT_BUS.post(event);
	 * if (event.getResult() == Result.DENY)
	 * return;
	 * 
	 * byte byte0 = 2;
	 * int k = byte0 + 1;
	 * byte byte1 = 33;
	 * int l = byte0 + 1;
	 * noiseField = initializeNoiseField(noiseField, par1 * byte0, 0, par2 * byte0, k, byte1, l);
	 * for (int i1 = 0; i1 < byte0; i1++)
	 * {
	 * for (int j1 = 0; j1 < byte0; j1++)
	 * {
	 * for (int k1 = 0; k1 < 32; k1++)
	 * {
	 * double d = 0.25D;
	 * double d1 = noiseField[((i1 + 0) * l + (j1 + 0)) * byte1 + (k1 + 0)];
	 * double d2 = noiseField[((i1 + 0) * l + (j1 + 1)) * byte1 + (k1 + 0)];
	 * double d3 = noiseField[((i1 + 1) * l + (j1 + 0)) * byte1 + (k1 + 0)];
	 * double d4 = noiseField[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 0)];
	 * double d5 = (noiseField[((i1 + 0) * l + (j1 + 0)) * byte1 + (k1 + 1)] - d1) * d;
	 * double d6 = (noiseField[((i1 + 0) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d2) * d;
	 * double d7 = (noiseField[((i1 + 1) * l + (j1 + 0)) * byte1 + (k1 + 1)] - d3) * d;
	 * double d8 = (noiseField[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d4) * d;
	 * for (int l1 = 0; l1 < 4; l1++)
	 * {
	 * double d9 = 0.125D;
	 * double d10 = d1;
	 * double d11 = d2;
	 * double d12 = (d3 - d1) * d9;
	 * double d13 = (d4 - d2) * d9;
	 * for (int i2 = 0; i2 < 8; i2++)
	 * {
	 * int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
	 * char c = '\200';
	 * double d14 = 0.125D;
	 * double d15 = d10;
	 * double d16 = (d11 - d10) * d14;
	 * for (int k2 = 0; k2 < 8; k2++)
	 * {
	 * int l2 = 0;
	 * if (d15 > 0.0D)
	 * {
	 * l2 = Block.whiteStone.blockID;
	 * }
	 * blocks[j2] = (byte) l2;
	 * j2 += c;
	 * d15 += d16;
	 * }
	 * 
	 * d10 += d12;
	 * d11 += d13;
	 * }
	 * 
	 * d1 += d5;
	 * d2 += d6;
	 * d3 += d7;
	 * d4 += d8;
	 * }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 * }
	 */
	
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
		random.setSeed((long) chunkX * 0x4f9939f508L + (long) chunkZ * 0x1ef1565bd5L);
		byte blocks[] = new byte[32768];
		biomes = world.getWorldChunkManager().loadBlockGeneratorData(biomes, chunkX * 16, chunkZ * 16, 16, 16);
		generateTerrain(chunkX, chunkZ, blocks);
		replaceBlocksForBiome(chunkX, chunkZ, blocks, biomes);
		caveGen.generate(this, world, chunkX, chunkZ, blocks);
		Chunk chunk = new Chunk(world, blocks, chunkX, chunkZ);
		chunk.generateSkylightMap();
		return chunk;
	}
	
	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
	 * size.
	 */
	private double[] initializeNoiseField(double[] ad, int i, int j, int k, int l, int i1, int j1)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, ad, i, j, k, l, i1, j1);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return event.noisefield;
		
		if (ad == null)
		{
			ad = new double[l * i1 * j1];
		}
		double d = 684.41200000000003D;
		double d1 = 684.41200000000003D;
		float ad1[] = new float[l * j1];
		world.getWorldChunkManager().getTemperatures(ad1, i, k, l, j1);
		noiseField6 = noiseGen6.generateNoiseOctaves(noiseField6, i, k, l, j1, 1.121D, 1.121D, 0.5D);
		noiseField7 = noiseGen7.generateNoiseOctaves(noiseField7, i, k, l, j1, 200D, 200D, 0.5D);
		d *= 2D;
		noiseField3 = noiseGen3.generateNoiseOctaves(noiseField3, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
		noiseField1 = noiseGen1.generateNoiseOctaves(noiseField1, i, j, k, l, i1, j1, d, d1, d);
		noiseField2 = noiseGen2.generateNoiseOctaves(noiseField2, i, j, k, l, i1, j1, d, d1, d);
		int k1 = 0;
		int l1 = 0;
		int i2 = 16 / l;
		for (int j2 = 0; j2 < l; j2++)
		{
			int k2 = j2 * i2 + i2 / 2;
			for (int l2 = 0; l2 < j1; l2++)
			{
				int i3 = l2 * i2 + i2 / 2;
				double d5 = (noiseField6[l1] + 256D) / 512D;
				if (d5 > 1.0D)
				{
					d5 = 1.0D;
				}
				double d6 = noiseField7[l1] / 8000D;
				if (d6 < 0.0D)
				{
					d6 = -d6 * 0.29999999999999999D;
				}
				d6 = d6 * 3D - 2D;
				if (d6 > 1.0D)
				{
					d6 = 1.0D;
				}
				d6 /= 8D;
				d6 = 0.0D;
				if (d5 < 0.0D)
				{
					d5 = 0.0D;
				}
				d5 += 0.5D;
				d6 = (d6 * (double) i1) / 16D;
				l1++;
				double d7 = (double) i1 / 2D;
				for (int j3 = 0; j3 < i1; j3++)
				{
					double d8 = 0.0D;
					double d9 = (((double) j3 - d7) * 8D) / d5;
					if (d9 < 0.0D)
					{
						d9 *= -1D;
					}
					double d10 = noiseField1[k1] / 512D;
					double d11 = noiseField2[k1] / 512D;
					double d12 = (noiseField3[k1] / 10D + 1.0D) / 2D;
					if (d12 < 0.0D)
					{
						d8 = d10;
					}
					else if (d12 > 1.0D)
					{
						d8 = d11;
					}
					else
					{
						d8 = d10 + (d11 - d10) * d12;
					}
					d8 -= 8D;
					int k3 = 32;
					if (j3 > i1 - k3)
					{
						double d13 = (float) (j3 - (i1 - k3)) / ((float) k3 - 1.0F);
						d8 = d8 * (1.0D - d13) + -30D * d13;
					}
					k3 = 8;
					if (j3 < k3)
					{
						double d14 = (float) (k3 - j3) / ((float) k3 - 1.0F);
						d8 = d8 * (1.0D - d14) + -30D * d14;
					}
					ad[k1] = d8;
					k1++;
				}
				
			}
			
		}
		
		return ad;
	}
	
	/**
	 * Checks to see if a chunk exists at x, y
	 */
	public boolean chunkExists(int par1, int par2)
	{
		return true;
	}
	
	/**
	 * Populates chunk with ores etc etc
	 */
	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
	{
		BlockSand.fallInstantly = true;
		int k = par2 * 16;
		int l = par3 * 16;
		BiomeGenBase biomegenbase = this.world.getBiomeGenForCoords(k + 16, l + 16);
		this.random.setSeed(this.world.getSeed());
		long i1 = this.random.nextLong() / 2L * 2L + 1L;
		long j1 = this.random.nextLong() / 2L * 2L + 1L;
		this.random.setSeed((long) par2 * i1 + (long) par3 * j1 ^ this.world.getSeed());
		boolean flag = false;
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, world, random, par2, par3, flag));
		
		/** Begin run generators (generate structure in chunk) */
		
		/** End run generators */
		
		biomegenbase.decorate(this.world, this.random, k, l);
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, world, random, par2, par3, flag));
		BlockSand.fallInstantly = false;
	}
	
	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If passed false, save up to two chunks.
	 * Return true if all chunks have been saved.
	 */
	public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
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
	 * Converts the instance data to a readable string.
	 */
	public String makeString()
	{
		return "RandomLevelSource";
	}
	
	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z)
	{
		BiomeGenBase biome = this.world.getBiomeGenForCoords(x, z);
		if (biome != null)
		{
			return biome.getSpawnableList(creatureType);
		}
		return null;
	}
	
	public int getLoadedChunkCount()
	{
		return 0;
	}
	
	public void recreateStructures(int x, int y)
	{
		/** Generate stuff */
	}
	
	@Override
	public ChunkPosition findClosestStructure(World world, String s, int i, int j, int k)
	{
		return null;
	}
}

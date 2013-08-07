package dragonborn.rift.dimension;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.*;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderRift implements IChunkProvider
{
	private Random					rand;
	private NoiseGeneratorOctaves	noiseGen1;
	private NoiseGeneratorOctaves	noiseGen2;
	private NoiseGeneratorOctaves	noiseGen3;
	private NoiseGeneratorOctaves	noiseGen4;
	public NoiseGeneratorOctaves	noiseGen5;
	public NoiseGeneratorOctaves	noiseGen6;
	public NoiseGeneratorOctaves	mobSpawnerNoise;
	private World					worldObj;
	private double[]				noiseArray;
	private double[]				stoneNoise	= new double[256];
	private BiomeGenBase[]			biomesForGeneration;
	double[]						noise3;
	double[]						noise1;
	double[]						noise2;
	double[]						noise5;
	double[]						noise6;
	float[]							parabolicField;
	
	/** Generators */
	private MapGenCaves				caveGen		= new MapGenCaves();
	
	public ChunkProviderRift(World par1World, long par2)
	{
		this.worldObj = par1World;
		this.rand = new Random(par2);
		this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
		
		NoiseGeneratorOctaves[] noiseGens = { noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise };
		noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.rand, noiseGens);
		this.noiseGen1 = noiseGens[0];
		this.noiseGen2 = noiseGens[1];
		this.noiseGen3 = noiseGens[2];
		this.noiseGen4 = noiseGens[3];
		this.noiseGen5 = noiseGens[4];
		this.noiseGen6 = noiseGens[5];
		this.mobSpawnerNoise = noiseGens[6];
		
		this.caveGen = (MapGenCaves) TerrainGen.getModdedMapGen(caveGen, CAVE);
	}
	
	/**
	 * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
	 * temperature is low enough
	 */
	public void generateTerrain(int par1, int par2, byte[] blocks)
	{
		byte arbitraryNumber1 = 4;
		byte arbitraryNumber2 = 16;
		byte seaLevel = 63;
		int arbitraryNumber1PlusOne = arbitraryNumber1 + 1;
		byte arbitraryNumber3 = 17;
		int arbitraryNumber1PlusOneAgain = arbitraryNumber1 + 1;
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, arbitraryNumber1PlusOne + 5, arbitraryNumber1PlusOneAgain + 5);
		this.noiseArray = this.initializeNoiseField(this.noiseArray, par1 * arbitraryNumber1, 0, par2 * arbitraryNumber1, arbitraryNumber1PlusOne, arbitraryNumber3, arbitraryNumber1PlusOneAgain);
		
		for (int genX = 0; genX < arbitraryNumber1; ++genX)
		{
			for (int genZ = 0; genZ < arbitraryNumber1; ++genZ)
			{
				for (int genY = 0; genY < arbitraryNumber2; ++genY)
				{
					double d0 = 0.125D;
					double d1 = this.noiseArray[((genX + 0) * arbitraryNumber1PlusOneAgain + genZ + 0) * arbitraryNumber3 + genY + 0];
					double d2 = this.noiseArray[((genX + 0) * arbitraryNumber1PlusOneAgain + genZ + 1) * arbitraryNumber3 + genY + 0];
					double d3 = this.noiseArray[((genX + 1) * arbitraryNumber1PlusOneAgain + genZ + 0) * arbitraryNumber3 + genY + 0];
					double d4 = this.noiseArray[((genX + 1) * arbitraryNumber1PlusOneAgain + genZ + 1) * arbitraryNumber3 + genY + 0];
					double d5 = (this.noiseArray[((genX + 0) * arbitraryNumber1PlusOneAgain + genZ + 0) * arbitraryNumber3 + genY + 1] - d1) * d0;
					double d6 = (this.noiseArray[((genX + 0) * arbitraryNumber1PlusOneAgain + genZ + 1) * arbitraryNumber3 + genY + 1] - d2) * d0;
					double d7 = (this.noiseArray[((genX + 1) * arbitraryNumber1PlusOneAgain + genZ + 0) * arbitraryNumber3 + genY + 1] - d3) * d0;
					double d8 = (this.noiseArray[((genX + 1) * arbitraryNumber1PlusOneAgain + genZ + 1) * arbitraryNumber3 + genY + 1] - d4) * d0;
					
					for (int l1 = 0; l1 < 8; ++l1)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						
						for (int i2 = 0; i2 < 4; ++i2)
						{
							int j2 = i2 + genX * 4 << 11 | 0 + genZ * 4 << 7 | genY * 8 + l1;
							short short1 = 128;
							j2 -= short1;
							double d14 = 0.25D;
							double d15 = (d11 - d10) * d14;
							double d16 = d10 - d15;
							
							for (int k2 = 0; k2 < 4; ++k2)
							{
								if ((d16 += d15) > 0.0D)
								{
									blocks[j2 += short1] = (byte) Block.whiteStone.blockID;
								}
								else if (genY * 8 + l1 < seaLevel)
								{
									blocks[j2 += short1] = (byte) Block.lavaStill.blockID; // hmm...lava...
								}
								else
								{
									blocks[j2 += short1] = 0;
								}
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
	public void replaceBlocksForBiome(int par1, int par2, byte[] blocks, BiomeGenBase[] par4ArrayOfBiomeGenBase)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, par1, par2, blocks, par4ArrayOfBiomeGenBase);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return;
		
		byte seaLevel = 63;
		double d0 = 0.03125D;
		this.stoneNoise = this.noiseGen4.generateNoiseOctaves(this.stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);
		
		for (int k = 0; k < 16; ++k)
		{
			for (int l = 0; l < 16; ++l)
			{
				BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[l + k * 16];
				float f = biomegenbase.getFloatTemperature();
				int i1 = (int) (this.stoneNoise[k + l * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
				int j1 = -1;
				byte b1 = biomegenbase.topBlock;
				byte b2 = biomegenbase.fillerBlock;
				
				for (int k1 = 127; k1 >= 0; --k1)
				{
					int l1 = (l * 16 + k) * 128 + k1;
					
					if (k1 <= 0)
					{
						blocks[l1] = (byte) Block.bedrock.blockID;
					}
					else
					{
						byte b3 = blocks[l1];
						
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
								else if (k1 >= seaLevel - 4 && k1 <= seaLevel + 1)
								{
									b1 = biomegenbase.topBlock;
									b2 = biomegenbase.fillerBlock;
								}
								
								if (k1 < seaLevel && b1 == 0)
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
								
								if (k1 >= seaLevel - 1)
								{
									blocks[l1] = b1;
								}
								else
								{
									blocks[l1] = b2;
								}
							}
							else if (j1 > 0)
							{
								--j1;
								blocks[l1] = b2;
								
								if (j1 == 0 && b2 == Block.sand.blockID)
								{
									j1 = this.rand.nextInt(4);
									b2 = (byte) Block.sandStone.blockID;
								}
							}
						}
					}
				}
			}
		}
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
		this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
		byte[] blocks = new byte[32768];
		this.generateTerrain(chunkX, chunkZ, blocks);
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		this.replaceBlocksForBiome(chunkX, chunkZ, blocks, this.biomesForGeneration);
		
		/** Begin run generators (generate) */
		caveGen.generate(this, this.worldObj, chunkX, chunkZ, blocks);
		/** End run generators */
		
		Chunk chunk = new Chunk(this.worldObj, blocks, chunkX, chunkZ);
		byte[] abyte1 = chunk.getBiomeArray();
		
		for (int k = 0; k < abyte1.length; ++k)
		{
			abyte1[k] = (byte) this.biomesForGeneration[k].biomeID;
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
	 * size.
	 */
	private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, par1ArrayOfDouble, par2, par3, par4, par5, par6, par7);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return event.noisefield;
		
		if (par1ArrayOfDouble == null)
		{
			par1ArrayOfDouble = new double[par5 * par6 * par7];
		}
		
		if (this.parabolicField == null)
		{
			this.parabolicField = new float[25];
			
			for (int k1 = -2; k1 <= 2; ++k1)
			{
				for (int l1 = -2; l1 <= 2; ++l1)
				{
					float f = 10.0F / MathHelper.sqrt_float((float) (k1 * k1 + l1 * l1) + 0.2F);
					this.parabolicField[k1 + 2 + (l1 + 2) * 5] = f;
				}
			}
		}
		
		double d0 = 684.412D;
		double d1 = 684.412D;
		this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
		this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
		this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
		this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, par2, par3, par4, par5, par6, par7, d0, d1, d0);
		this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
		boolean flag = false;
		boolean flag1 = false;
		int i2 = 0;
		int j2 = 0;
		
		for (int k2 = 0; k2 < par5; ++k2)
		{
			for (int l2 = 0; l2 < par7; ++l2)
			{
				float f1 = 0.0F;
				float f2 = 0.0F;
				float f3 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biomegenbase = this.biomesForGeneration[k2 + 2 + (l2 + 2) * (par5 + 5)];
				
				for (int i3 = -b0; i3 <= b0; ++i3)
				{
					for (int j3 = -b0; j3 <= b0; ++j3)
					{
						BiomeGenBase biomegenbase1 = this.biomesForGeneration[k2 + i3 + 2 + (l2 + j3 + 2) * (par5 + 5)];
						float f4 = this.parabolicField[i3 + 2 + (j3 + 2) * 5] / (biomegenbase1.minHeight + 2.0F);
						
						if (biomegenbase1.minHeight > biomegenbase.minHeight)
						{
							f4 /= 2.0F;
						}
						
						f1 += biomegenbase1.maxHeight * f4;
						f2 += biomegenbase1.minHeight * f4;
						f3 += f4;
					}
				}
				
				f1 /= f3;
				f2 /= f3;
				f1 = f1 * 0.9F + 0.1F;
				f2 = (f2 * 4.0F - 1.0F) / 8.0F;
				double d2 = this.noise6[j2] / 8000.0D;
				
				if (d2 < 0.0D)
				{
					d2 = -d2 * 0.3D;
				}
				
				d2 = d2 * 3.0D - 2.0D;
				
				if (d2 < 0.0D)
				{
					d2 /= 2.0D;
					
					if (d2 < -1.0D)
					{
						d2 = -1.0D;
					}
					
					d2 /= 1.4D;
					d2 /= 2.0D;
				}
				else
				{
					if (d2 > 1.0D)
					{
						d2 = 1.0D;
					}
					
					d2 /= 8.0D;
				}
				
				++j2;
				
				for (int k3 = 0; k3 < par6; ++k3)
				{
					double d3 = (double) f2;
					double d4 = (double) f1;
					d3 += d2 * 0.2D;
					d3 = d3 * (double) par6 / 16.0D;
					double d5 = (double) par6 / 2.0D + d3 * 4.0D;
					double d6 = 0.0D;
					double d7 = ((double) k3 - d5) * 12.0D * 128.0D / 128.0D / d4;
					
					if (d7 < 0.0D)
					{
						d7 *= 4.0D;
					}
					
					double d8 = this.noise1[i2] / 512.0D;
					double d9 = this.noise2[i2] / 512.0D;
					double d10 = (this.noise3[i2] / 10.0D + 1.0D) / 2.0D;
					
					if (d10 < 0.0D)
					{
						d6 = d8;
					}
					else if (d10 > 1.0D)
					{
						d6 = d9;
					}
					else
					{
						d6 = d8 + (d9 - d8) * d10;
					}
					
					d6 -= d7;
					
					if (k3 > par6 - 4)
					{
						double d11 = (double) ((float) (k3 - (par6 - 4)) / 3.0F);
						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}
					
					par1ArrayOfDouble[i2] = d6;
					++i2;
				}
			}
		}
		
		return par1ArrayOfDouble;
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
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		long i1 = this.rand.nextLong() / 2L * 2L + 1L;
		long j1 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long) par2 * i1 + (long) par3 * j1 ^ this.worldObj.getSeed());
		boolean flag = false;
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, rand, par2, par3, flag));
		
		/** Begin run generators (generate structure in chunk) */
		
		/** End run generators */
		
		biomegenbase.decorate(this.worldObj, this.rand, k, l);
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, rand, par2, par3, flag));
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
		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x, z);
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

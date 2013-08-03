package dragonborn.rift.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderRift extends ChunkProviderEnd
{
	private World					riftWorld;
	private Random					riftRNG;
	private NoiseGeneratorOctaves	noiseGen1;
	private NoiseGeneratorOctaves	noiseGen2;
	private NoiseGeneratorOctaves	noiseGen3;
	public NoiseGeneratorOctaves	noiseGen4;
	public NoiseGeneratorOctaves	noiseGen5;
	private double[]				densities;
	double[]						noiseData1;
	double[]						noiseData2;
	double[]						noiseData3;
	double[]						noiseData4;
	double[]						noiseData5;
	private BiomeGenBase[]			biomesForGeneration	= new BiomeGenBase[] { BiomeGenRift.biomeGenRift };
	
	public ChunkProviderRift(World world, long seed)
	{
		super(world, seed);
		
		this.riftWorld = world;
		this.riftRNG = new Random(seed);
		
		this.noiseGen1 = new NoiseGeneratorOctaves(this.riftRNG, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.riftRNG, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.riftRNG, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.riftRNG, 10);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.riftRNG, 16);
		
		NoiseGeneratorOctaves[] noiseGens = { noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5 };
		noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.riftRNG, noiseGens);
		this.noiseGen1 = noiseGens[0];
		this.noiseGen2 = noiseGens[1];
		this.noiseGen3 = noiseGens[2];
		this.noiseGen4 = noiseGens[3];
		this.noiseGen5 = noiseGens[4];
	}
	
	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		this.riftRNG.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
		byte[] blocks = new byte[32768];
		this.generateTerrain(chunkX, chunkZ, blocks, this.biomesForGeneration);
		this.replaceBlocksForBiome(chunkX, chunkZ, blocks, this.biomesForGeneration);
		Chunk chunk = new Chunk(this.riftWorld, blocks, chunkX, chunkZ);
		byte[] biomes = chunk.getBiomeArray();
		
		for (int k = 0; k < biomes.length; ++k)
		{
			biomes[k] = (byte) this.biomesForGeneration[k % biomesForGeneration.length].biomeID;
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
	 * size.
	 */
	private double[] initializeNoiseField(double[] noiseField, int x, int y, int z, int width, int height, int depth)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, noiseField, x, y, z, width, height, depth);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return event.noisefield;
		
		if (noiseField == null)
		{
			noiseField = new double[width * height * depth];
		}
		
		double d0 = 684.412D;
		double d1 = 684.412D;
		this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, x, z, width, depth, 1.121D, 1.121D, 0.5D);
		this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, x, z, width, depth, 200.0D, 200.0D, 0.5D);
		d0 *= 2.0D;
		this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, x, y, z, width, height, depth, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
		this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, x, y, z, width, height, depth, d0, d1, d0);
		this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, x, y, z, width, height, depth, d0, d1, d0);
		int k1 = 0;
		int l1 = 0;
		
		for (int noiseX = 0; noiseX < width; ++noiseX)
		{
			for (int noiseZ = 0; noiseZ < depth; ++noiseZ)
			{
				double d2 = (this.noiseData4[l1] + 256.0D) / 512.0D;
				
				if (d2 > 1.0D)
				{
					d2 = 1.0D;
				}
				
				double d3 = this.noiseData5[l1] / 8000.0D;
				
				if (d3 < 0.0D)
				{
					d3 = -d3 * 0.3D;
				}
				
				d3 = d3 * 3.0D - 2.0D;
				float f = (float) (noiseX + x - 0) / 1.0F;
				float f1 = (float) (noiseZ + z - 0) / 1.0F;
				float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;
				
				if (f2 > 80.0F)
				{
					f2 = 80.0F;
				}
				
				if (f2 < -100.0F)
				{
					f2 = -100.0F;
				}
				
				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}
				
				d3 /= 8.0D;
				d3 = 0.0D;
				
				if (d2 < 0.0D)
				{
					d2 = 0.0D;
				}
				
				d2 += 0.5D;
				d3 = d3 * (double) height / 16.0D;
				++l1;
				double d4 = (double) height / 2.0D;
				
				for (int k2 = 0; k2 < height; ++k2)
				{
					double d5 = 0.0D;
					double d6 = ((double) k2 - d4) * 8.0D / d2;
					
					if (d6 < 0.0D)
					{
						d6 *= -1.0D;
					}
					
					double d7 = this.noiseData2[k1] / 512.0D;
					double d8 = this.noiseData3[k1] / 512.0D;
					double d9 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;
					
					if (d9 < 0.0D)
					{
						d5 = d7;
					}
					else if (d9 > 1.0D)
					{
						d5 = d8;
					}
					else
					{
						d5 = d7 + (d8 - d7) * d9;
					}
					
					d5 -= 8.0D;
					d5 += (double) f2;
					byte b0 = 2;
					double d10;
					
					if (k2 > height / 2 - b0)
					{
						d10 = (double) ((float) (k2 - (height / 2 - b0)) / 64.0F);
						
						if (d10 < 0.0D)
						{
							d10 = 0.0D;
						}
						
						if (d10 > 1.0D)
						{
							d10 = 1.0D;
						}
						
						d5 = d5 * (1.0D - d10) + -3000.0D * d10;
					}
					
					b0 = 8;
					
					if (k2 < b0)
					{
						d10 = (double) ((float) (b0 - k2) / ((float) b0 - 1.0F));
						d5 = d5 * (1.0D - d10) + -30.0D * d10;
					}
					
					noiseField[k1] = d5;
					++k1;
				}
			}
		}
		
		return noiseField;
	}
	
	@Override
	public void generateTerrain(int chunkX, int chunkZ, byte[] blocks, BiomeGenBase[] biomes)
	{
		int size = 2;
		int sizeX = size + 1;
		int sizeY = size + 1;
		byte height = 33;
		this.densities = this.initializeNoiseField(this.densities, chunkX * size, 0, chunkZ * size, sizeX, height, sizeY);
		
		for (int genX = 0; genX < size; ++genX)
		{
			for (int genZ = 0; genZ < size; ++genZ)
			{
				for (int genY = 0; genY < 32; ++genY)
				{
					double noise1Min = this.densities[((genX + 0) * sizeY + genZ + 0) * height + genY + 0];
					double noise2Min = this.densities[((genX + 0) * sizeY + genZ + 1) * height + genY + 0];
					double noise3Min = this.densities[((genX + 1) * sizeY + genZ + 0) * height + genY + 0];
					double noise4Min = this.densities[((genX + 1) * sizeY + genZ + 1) * height + genY + 0];
					double noise1Max = (this.densities[((genX + 0) * sizeY + genZ + 0) * height + genY + 1] - noise1Min) * 0.25;
					double noise2Max = (this.densities[((genX + 0) * sizeY + genZ + 1) * height + genY + 1] - noise2Min) * 0.25;
					double noise3Max = (this.densities[((genX + 1) * sizeY + genZ + 0) * height + genY + 1] - noise3Min) * 0.25;
					double noise4Max = (this.densities[((genX + 1) * sizeY + genZ + 1) * height + genY + 1] - noise4Min) * 0.25;
					
					for (int largeScale = 0; largeScale < 4; ++largeScale)
					{
						double noise1Final = noise1Min;
						double noise2Final = noise2Min;
						double noise3Final = (noise3Min - noise1Min) * 0.125;
						double noise4Final = (noise4Min - noise2Min) * 0.125;
						
						for (int medScale = 0; medScale < 8; ++medScale)
						{
							int blockIndex = medScale + genX * 8 << 11 | 0 + genZ * 8 << 7 | genY * 4 + largeScale;
							short increment = 128;
							double noise1 = noise1Final;
							double noise2 = (noise2Final - noise1Final) * 0.125;
							
							for (int smallScale = 0; smallScale < 8; ++smallScale)
							{
								int newID = 0;
								
								if (noise1 > 0.0D)
								{
									newID = Block.whiteStone.blockID;
								}
								
								blocks[blockIndex] = (byte) newID;
								blockIndex += increment;
								noise1 += 0.125;
							}
							
							noise1Final += noise3Final;
							noise2Final += noise4Final;
						}
						
						noise1Min += noise1Max;
						noise2Min += noise2Max;
						noise3Min += noise3Max;
						noise4Min += noise4Max;
					}
				}
			}
		}
	}
	
}

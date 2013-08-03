package dragonborn.rift.dimension;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderRift implements IChunkProvider
{
	private static final int		STONE_ID	= Block.oreDiamond.blockID;
	private static final int		FLUID_ID	= Block.waterStill.blockID;
	
	private World					worldObj;
	private long					seed;
	private Random					rand;
	private BiomeGenBase[]			biomesForGeneration;
	
	private NoiseGeneratorOctaves	noiseGen1;
	private NoiseGeneratorOctaves	noiseGen2;
	private NoiseGeneratorOctaves	noiseGen3;
	private NoiseGeneratorOctaves	noiseGen4;
	public NoiseGeneratorOctaves	noiseGen5;
	public NoiseGeneratorOctaves	noiseGen6;
	private double[]				noiseArray;
	double[]						noise3;
	double[]						noise1;
	double[]						noise2;
	double[]						noise5;
	double[]						noise6;
	float[]							parabolicField;
	
	public ChunkProviderRift(World world, long seed)
	{
		this.worldObj = world;
		this.seed = seed;
		this.rand = new Random(this.seed);
		biomesForGeneration = new BiomeGenBase[] { BiomeGenRift.biomeGenRift };
		
		this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		
		NoiseGeneratorOctaves[] noiseGens = { noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6 };
		noiseGens = TerrainGen.getModdedNoiseGenerators(this.worldObj, this.rand, noiseGens);
		this.noiseGen1 = noiseGens[0];
		this.noiseGen2 = noiseGens[1];
		this.noiseGen3 = noiseGens[2];
		this.noiseGen4 = noiseGens[3];
		this.noiseGen5 = noiseGens[4];
		this.noiseGen6 = noiseGens[5];
	}
	
	@Override
	public boolean chunkExists(int x, int z)
	{
		return true;
	}
	
	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
	 * size.
	 */
	private double[] initializeNoiseField(double[] emtpyArry, int x, int y, int z, int width, int height, int depth)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, emtpyArry, x, y, z, width, height, depth);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return event.noisefield;
		
		if (emtpyArry == null)
		{
			emtpyArry = new double[width * height * depth];
		}
		
		if (this.parabolicField == null)
		{
			this.parabolicField = new float[25];
			
			for (int i = -2; i <= 2; ++i)
			{
				for (int j = -2; j <= 2; ++j)
				{
					float dist = 10.0F / MathHelper.sqrt_float((float) (i * i + j * j) + 0.2F);
					this.parabolicField[i + 2 + (j + 2) * 5] = dist;
				}
			}
		}
		
		double noiseConst1 = 684.412D;
		double noiseConst2 = 684.412D;
		this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, x, z, width, depth, 1.121D, 1.121D, 0.5D);
		this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, x, z, width, depth, 200.0D, 200.0D, 0.5D);
		this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, x, y, z, width, height, depth, noiseConst1 / 80.0D, noiseConst2 / 160.0D, noiseConst1 / 80.0D);
		this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, x, y, z, width, height, depth, noiseConst1, noiseConst2, noiseConst1);
		this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, x, y, z, width, height, depth, noiseConst1, noiseConst2, noiseConst1);
		int i2 = 0;
		int j2 = 0;
		
		for (int k2 = 0; k2 < width; ++k2)
		{
			for (int l2 = 0; l2 < depth; ++l2)
			{
				float f1 = 0.0F;
				float f2 = 0.0F;
				float f3 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biomegenbase = this.biomesForGeneration[(k2 + 2 + ((l2 + 2) * (width + 5))) % biomesForGeneration.length];
				
				for (int i3 = -b0; i3 <= b0; ++i3)
				{
					for (int j3 = -b0; j3 <= b0; ++j3)
					{
						BiomeGenBase biomegenbase1 = this.biomesForGeneration[(k2 + i3 + 2 + ((l2 + j3 + 2) * (width + 5))) % biomesForGeneration.length];
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
				
				for (int k3 = 0; k3 < height; ++k3)
				{
					double d3 = (double) f2;
					double d4 = (double) f1;
					d3 += d2 * 0.2D;
					d3 = d3 * (double) height / 16.0D;
					double d5 = (double) height / 2.0D + d3 * 4.0D;
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
					
					if (k3 > height - 4)
					{
						double d11 = (double) ((float) (k3 - (height - 4)) / 3.0F);
						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}
					
					emtpyArry[i2] = d6;
					++i2;
				}
			}
		}
		
		return emtpyArry;
	}
	
	/**
	 * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
	 * temperature is low enough
	 */
	public void generateTerrain(int par1, int par2, byte[] par3ArrayOfByte)
	{
		byte b0 = 4;
		byte b1 = 16;
		byte b2 = 63;
		int k = b0 + 1;
		byte b3 = 17;
		int l = b0 + 1;
		// this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, k + 5, l + 5);
		this.noiseArray = this.initializeNoiseField(this.noiseArray, par1 * b0, 0, par2 * b0, k, b3, l);
		
		for (int i1 = 0; i1 < b0; ++i1)
		{
			for (int j1 = 0; j1 < b0; ++j1)
			{
				for (int k1 = 0; k1 < b1; ++k1)
				{
					double d0 = 0.125D;
					double d1 = this.noiseArray[((i1 + 0) * l + j1 + 0) * b3 + k1 + 0];
					double d2 = this.noiseArray[((i1 + 0) * l + j1 + 1) * b3 + k1 + 0];
					double d3 = this.noiseArray[((i1 + 1) * l + j1 + 0) * b3 + k1 + 0];
					double d4 = this.noiseArray[((i1 + 1) * l + j1 + 1) * b3 + k1 + 0];
					double d5 = (this.noiseArray[((i1 + 0) * l + j1 + 0) * b3 + k1 + 1] - d1) * d0;
					double d6 = (this.noiseArray[((i1 + 0) * l + j1 + 1) * b3 + k1 + 1] - d2) * d0;
					double d7 = (this.noiseArray[((i1 + 1) * l + j1 + 0) * b3 + k1 + 1] - d3) * d0;
					double d8 = (this.noiseArray[((i1 + 1) * l + j1 + 1) * b3 + k1 + 1] - d4) * d0;
					
					for (int l1 = 0; l1 < 8; ++l1)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						
						for (int i2 = 0; i2 < 4; ++i2)
						{
							int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
							short short1 = 128;
							j2 -= short1;
							double d15 = (d11 - d10) * 0.25;
							double d16 = d10 - d15;
							
							for (int k2 = 0; k2 < 4; ++k2)
							{
								if ((d16 += d15) > 0.0D)
								{
									par3ArrayOfByte[j2 += short1] = (byte) ChunkProviderRift.STONE_ID;
								}
								else if (k1 * 8 + l1 < b2)
								{
									par3ArrayOfByte[j2 += short1] = (byte) ChunkProviderRift.FLUID_ID;
								}
								else
								{
									par3ArrayOfByte[j2 += short1] = 0;
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
	
	@Override
	public Chunk provideChunk(int x, int z)
	{
		this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
		byte[] abyte = new byte[32768];
		this.generateTerrain(x, z, abyte);
		// this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		
		Chunk chunk = new Chunk(this.worldObj, abyte, x, z);
		byte[] abyte1 = chunk.getBiomeArray();
		
		for (int k = 0; k < abyte1.length; ++k)
		{
			abyte1[k] = (byte) this.biomesForGeneration[k % biomesForGeneration.length].biomeID;
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	@Override
	public Chunk loadChunk(int x, int z)
	{
		return this.provideChunk(x, z);
	}
	
	@Override
	public void populate(IChunkProvider provider, int genX, int genZ)
	{
		
	}
	
	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate)
	{
		return false;
	}
	
	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}
	
	@Override
	public boolean canSave()
	{
		return true;
	}
	
	@Override
	public String makeString()
	{
		return "RiftProvider";
	}
	
	@Override
	public List getPossibleCreatures(EnumCreatureType enumcreaturetype, int i, int j, int k)
	{
		return null;
	}
	
	@Override
	public ChunkPosition findClosestStructure(World world, String s, int i, int j, int k)
	{
		return null;
	}
	
	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}
	
	@Override
	public void recreateStructures(int i, int j)
	{
		
	}
	
	@Override
	public void func_104112_b()
	{
		
	}
	
}

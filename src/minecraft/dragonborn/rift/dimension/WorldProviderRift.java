package dragonborn.rift.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;

public class WorldProviderRift extends WorldProvider
{
	public WorldProviderRift()
	{
		super();
		registerWorldChunkManager();
	}
	
	@Override
	protected void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenRift.biomeGenRift, 0.0F, 0.0F);
	}
	
	@Override
	public IChunkProvider createChunkGenerator()
	{
		// return new ChunkProviderGenerate(worldObj, worldObj.getSeed() + 1, false);
		return new ChunkProviderRift(worldObj, worldObj.getSeed());
	}
	
	@Override
	public BiomeGenBase getBiomeGenForCoords(int x, int z)
	{
		return BiomeGenRift.biomeGenRift;
	}
	
	@Override
	public String getDimensionName()
	{
		return "Rift";
	}
	
	@Override
	public String getSaveFolder()
	{
		return "rift";
	}
	
	@Override
	public boolean canRespawnHere()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight()
	{
		return -8.0f;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float sunAngle, float partialTicks)
	{
		double brightness = Math.cos(sunAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
		
		if (brightness < 0.0F)
		{
			brightness = 0.0F;
		}
		
		if (brightness > 1.0F)
		{
			brightness = 1.0F;
		}
		
		double cR = 75.0 / 255.0;
		double cG = 0.0;
		double cB = 100.0 / 255.0;
		cR *= brightness;
		cG *= brightness;
		cB *= brightness;
		return this.worldObj.getWorldVec3Pool().getVecFromPool((double) cR, (double) cG, (double) cB);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
	{
		return this.getFogColor(this.worldObj.getCelestialAngle(partialTicks), partialTicks);
	}
	
	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isSkyColored()
	{
		return false;
	}
	
	@Override
	public int getRespawnDimension(EntityPlayerMP player)
	{
		return Config.RIFT_DIMENSION_ID;
	}
	
}

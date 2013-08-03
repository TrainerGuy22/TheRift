package dragonborn.rift.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.config.Config;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
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
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenRift.biomeGenRift, 0.0F, 0.0F);
	}
	
	@Override
	protected void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenRift.biomeGenRift, 0.0F, 0.0F);
	}
	
	@Override
	public IChunkProvider createChunkGenerator()
	{
		// return new ChunkProviderRift(this.worldObj, this.worldObj.getSeed());
		return new ChunkProviderGenerate(worldObj, worldObj.getSeed() + 1, false);
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
	public Vec3 getFogColor(float par1, float par2)
	{
		return Vec3.fakePool.getVecFromPool(75.0 / 255.0, 0.0, 100.0 / 255.0);
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
		return Config.DIMENSION_ID;
	}
	
}

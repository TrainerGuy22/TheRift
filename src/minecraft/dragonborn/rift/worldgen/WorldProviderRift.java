package dragonborn.rift.worldgen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;

public class WorldProviderRift extends WorldProvider
{
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
		return Vec3.fakePool.getVecFromPool(149.0 / 255.0, 0.0, 191.0 / 255.0);
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
	
}

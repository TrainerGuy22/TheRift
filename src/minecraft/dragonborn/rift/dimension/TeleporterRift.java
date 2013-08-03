package dragonborn.rift.dimension;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterRift extends Teleporter
{
	public TeleporterRift(WorldServer server)
	{
		super(server);
	}
	
	@Override
	public boolean makePortal(Entity entity)
	{
		return true; // yeah, let's not and say we did
	}
	
	@Override
	public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
	{
		int i = MathHelper.floor_double(par1Entity.posX);
		int j = MathHelper.floor_double(par1Entity.posY) - 1;
		int k = MathHelper.floor_double(par1Entity.posZ);
		byte b0 = 1;
		byte b1 = 0;
		
		par1Entity.setLocationAndAngles((double) i, (double) j, (double) k, par1Entity.rotationYaw, 0.0F);
		par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
	}
}

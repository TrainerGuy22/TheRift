package dragonborn.rift.dimension;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterRift extends Teleporter
{
	public TeleporterRift(WorldServer world)
	{
		super(world);
	}
	
	@Override
	public boolean makePortal(Entity entity)
	{
		return true; // yeah, let's not and say we did
	}
	
	@Override
	public void placeInPortal(Entity entity, double x, double y, double z, float yaw)
	{
		int newX = MathHelper.floor_double(entity.posX);
		int newY = MathHelper.floor_double(entity.posY) + 1;
		int newZ = MathHelper.floor_double(entity.posZ);
		
		entity.setLocationAndAngles((double) newX, (double) newY, (double) newZ, entity.rotationYaw, 0.0F);
		entity.motionX = entity.motionY = entity.motionZ = 0.0D;
	}
}

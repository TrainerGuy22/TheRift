package dragonborn.rift.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import dragonborn.rift.config.Config;
import dragonborn.rift.data.RiftSavedData;
import dragonborn.rift.util.RiftUtil;

public class EntityHandler
{
	@ForgeSubscribe
	public void onEntityDeath(LivingDeathEvent event)
	{
		EntityLivingBase livingBase = event.entityLiving;
		if (livingBase != null)
		{
			World world = livingBase.worldObj;
			if (world.isRemote)
				return;
			if (livingBase instanceof EntityDragon) // dragon died
			{
				RiftSavedData riftData = (RiftSavedData) world.loadItemData(RiftSavedData.class, "rift");
				if (riftData == null)
					riftData = new RiftSavedData("rift");
				riftData.hasDragonDied = true;
				riftData.setDirty(true);
				world.setItemData("rift", riftData);
			}
		}
	}
	
	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		Entity entity = event.entity;
		if (entity != null)
		{
			World world = entity.worldObj;
			if (entity instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP) entity;
				if (player.posY <= -64.0) // falling out of world
				{
					if (player.dimension == 1) // teleport them to the rift
					{
						RiftUtil.teleportPlayer(player, Config.RIFT_DIMENSION_ID);
						ChunkCoordinates spawn = RiftUtil.findValidSpawnPoint(player.worldObj);
						player.playerNetServerHandler.setPlayerLocation(spawn.posX, spawn.posY + 1, spawn.posZ, player.rotationYaw, player.rotationPitch);
						player.fallDistance = 0.0f;
					}
					else if (player.dimension == Config.RIFT_DIMENSION_ID) // wrap them around to sky
					{
						double newY = player.worldObj.getTopSolidOrLiquidBlock((int) Math.round(player.posX), (int) Math.round(player.posZ)) + 4;
						player.fallDistance = 0.0f;
						player.playerNetServerHandler.setPlayerLocation(player.posX, newY, player.posZ, player.rotationYaw, player.rotationPitch);
					}
				}
			}
		}
	}
}

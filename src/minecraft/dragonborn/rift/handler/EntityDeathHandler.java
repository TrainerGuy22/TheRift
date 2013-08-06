package dragonborn.rift.handler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import dragonborn.rift.config.Config;
import dragonborn.rift.data.RiftSavedData;
import dragonborn.rift.util.RiftUtil;

public class EntityDeathHandler
{
	@ForgeSubscribe
	public void onEntityDeath(LivingDeathEvent event)
	{
		EntityLivingBase livingBase = event.entityLiving;
		World world = livingBase.worldObj;
		
		if (livingBase instanceof EntityDragon) // dragon died
		{
			RiftSavedData riftData = (RiftSavedData) world.loadItemData(RiftSavedData.class, "rift");
			if (riftData == null)
				riftData = new RiftSavedData("rift");
			riftData.hasDragonDied = true;
			riftData.setDirty(true);
			world.setItemData("rift", riftData);
		}
		else
		{
			if (livingBase instanceof EntityPlayerMP) // player died
			{
				EntityPlayerMP player = (EntityPlayerMP) livingBase;
				if (player.dimension == 1) // player is in end
				{
					RiftSavedData riftData = (RiftSavedData) world.loadItemData(RiftSavedData.class, "rift");
					if (riftData == null)
						riftData = new RiftSavedData("rift");
					if (riftData.hasDragonDied)
					{
						event.setCanceled(true);
						player.isDead = false; // not dead anymore, enters rift instead
						RiftUtil.teleportPlayer(player, Config.RIFT_DIMENSION_ID);
					}
				}
			}
		}
	}
}

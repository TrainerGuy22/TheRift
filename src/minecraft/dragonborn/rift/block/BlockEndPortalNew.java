package dragonborn.rift.block;

import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import dragonborn.rift.config.Config;
import dragonborn.rift.data.RiftSavedData;
import dragonborn.rift.util.RiftUtil;

public class BlockEndPortalNew extends BlockEndPortal
{
	
	public BlockEndPortalNew(int blockID, Material material)
	{
		super(blockID, material);
	}
	
	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (entity.ridingEntity == null && entity.riddenByEntity == null && !world.isRemote)
		{
			RiftSavedData riftData = (RiftSavedData) world.loadItemData(RiftSavedData.class, "rift");
			if (riftData == null)
				riftData = new RiftSavedData("rift");
			if (!riftData.hasDragonDied || !(entity instanceof EntityPlayerMP) /* Only players go to Rift */)
				entity.travelToDimension(1); // travel to End
			else
			{
				EntityPlayerMP player = (EntityPlayerMP) entity;
				RiftUtil.teleportPlayer(player, Config.RIFT_DIMENSION_ID); // travel to Rift
				ChunkCoordinates spawnPoint = player.worldObj.getSpawnPoint();
				/** Place player at spawn */
				player.posX = spawnPoint.posX;
				player.posY = spawnPoint.posY + 1;
				player.posZ = spawnPoint.posZ;
			}
		}
	}
}

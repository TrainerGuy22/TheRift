package dragonborn.rift.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import dragonborn.rift.config.Blocks;

public class BlockRiftPortal extends BlockPortal
{
	
	public BlockRiftPortal(int blockID)
	{
		super(blockID);
		setUnlocalizedName("riftPortal");
	}
	
	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		// let's not do a freaking thing
	}
	
	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID)
	{
		byte xAxis = 0;
		byte zAxis = 1;
		
		if (world.getBlockId(x - 1, y, z) == this.blockID || world.getBlockId(x + 1, y, z) == this.blockID)
		{
			xAxis = 1;
			zAxis = 0;
		}
		
		int checkHeight;
		
		for (checkHeight = y; world.getBlockId(x, checkHeight - 1, z) == this.blockID; --checkHeight)
		{
			;
		}
		
		if (world.getBlockId(x, checkHeight - 1, z) != Blocks.blockID_dragonscaleBlock)
		{
			world.setBlockToAir(x, y, z);
		}
		else
		{
			int heightOffset;
			
			for (heightOffset = 1; heightOffset < 4 && world.getBlockId(x, checkHeight + heightOffset, z) == this.blockID; ++heightOffset)
			{
				;
			}
			
			if (heightOffset == 3 && world.getBlockId(x, checkHeight + heightOffset, z) == Blocks.blockID_dragonscaleBlock)
			{
				boolean xPortal = world.getBlockId(x - 1, y, z) == this.blockID || world.getBlockId(x + 1, y, z) == this.blockID;
				boolean zPortal = world.getBlockId(x, y, z - 1) == this.blockID || world.getBlockId(x, y, z + 1) == this.blockID;
				
				if (xPortal && zPortal)
				{
					world.setBlockToAir(x, y, z);
				}
				else
				{
					if ((world.getBlockId(x + xAxis, y, z + zAxis) != Blocks.blockID_dragonscaleBlock || world.getBlockId(x - xAxis, y, z - zAxis) != this.blockID) && (world.getBlockId(x - xAxis, y, z - zAxis) != Blocks.blockID_dragonscaleBlock || world.getBlockId(x + xAxis, y, z + zAxis) != this.blockID))
					{
						world.setBlockToAir(x, y, z);
					}
				}
			}
			else
			{
				world.setBlockToAir(x, y, z);
			}
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (entity.ridingEntity == null && entity.riddenByEntity == null)
		{
			if (entity instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP) entity;
				player.triggerAchievement(AchievementList.theEnd2);
				player.worldObj.removeEntity(player);
				player.playerConqueredTheEnd = true;
				player.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(4, 0));
			}
		}
	}
	
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		for (int l = 0; l < 4; ++l)
		{
			double xOff = (double) ((float) x + random.nextFloat());
			double yOff = (double) ((float) y + random.nextFloat());
			double zOff = (double) ((float) z + random.nextFloat());
			double xSpd = 0.0D;
			double ySpd = 0.0D;
			double zspd = 0.0D;
			int something = random.nextInt(2) * 2 - 1;
			xSpd = ((double) random.nextFloat() - 0.5D) * 0.5D;
			ySpd = ((double) random.nextFloat() - 0.5D) * 0.5D;
			zspd = ((double) random.nextFloat() - 0.5D) * 0.5D;
			
			if (world.getBlockId(x - 1, y, z) != this.blockID && world.getBlockId(x + 1, y, z) != this.blockID)
			{
				xOff = (double) x + 0.5D + 0.25D * (double) something;
				xSpd = (double) (random.nextFloat() * 2.0F * (float) something);
			}
			else
			{
				zOff = (double) z + 0.5D + 0.25D * (double) something;
				zspd = (double) (random.nextFloat() * 2.0F * (float) something);
			}
			
			world.spawnParticle("portal", xOff, yOff, zOff, xSpd, ySpd, zspd);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.blockIcon = register.registerIcon(RiftMod.MOD_ID + ":portal_rift");
	}
	
}

package dragonborn.rift.util;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import dragonborn.rift.RiftMod;
import dragonborn.rift.dimension.TeleporterRift;

public class RiftUtil
{
	private static Logger	logger			= null;
	
	/** "Set block with notify" masks */
	public static final int	NMASK_NONE		= 0x00;
	public static final int	NMASK_SEND_ID	= 0x01;
	public static final int	NMASK_BOTH		= 0x02;
	public static final int	NMASK_NOCLIENT	= 0x04;
	
	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}
	
	public static void log(String message)
	{
		if (logger == null)
		{
			logger = Logger.getLogger(RiftMod.MOD_ID);
			logger.setParent(FMLLog.getLogger());
		}
		logger.info(message);
	}
	
	/**
	 * Prints out all loaded blocks. Useful for finding default IDs.
	 */
	public static void printAllBlocks()
	{
		for (int i = 0; i < Block.blocksList.length; i++)
		{
			System.out.println(String.format("#%04d. %s", i, Block.blocksList[i] != null ? Block.blocksList[i].getLocalizedName() : "- NULL -"));
		}
	}
	
	public static void teleportPlayer(EntityPlayerMP player, int dimID)
	{
		
		MinecraftServer server = MinecraftServer.getServer();
		WorldServer newWorld = server.worldServerForDimension(dimID);
		server.getConfigurationManager().transferPlayerToDimension(player, dimID, new TeleporterRift(newWorld));
		
		// player.travelToDimension(dimID);
	}
}

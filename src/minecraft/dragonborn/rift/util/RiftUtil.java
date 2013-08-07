package dragonborn.rift.util;

import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeDirection;
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
		if (player.dimension == 1) // they're in the end, so we de-Mojangify them first
		{
			WorldServer overworld = server.worldServerForDimension(dimID);
			server.getConfigurationManager().transferPlayerToDimension(player, 0, new TeleporterRift(overworld)); // WHY THE **** DO I HAVE TO DO THIS, MOJANG
		}
		server.getConfigurationManager().transferPlayerToDimension(player, dimID, new TeleporterRift(newWorld));
	}
	
	public static double getDistanceBetweenPoints(ChunkCoordinates a, ChunkCoordinates b)
	{
		double xDist = b.posX - a.posX;
		double yDist = b.posY - a.posY;
		double zDist = b.posZ - a.posZ;
		return Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist);
	}
	
	public static ChunkCoordinates findValidSpawnPoint(World world)
	{
		ChunkCoordinates origSpawn = world.getSpawnPoint();
		ChunkCoordinates spawn = new ChunkCoordinates(origSpawn);
		Random random = world.rand;
		
		spawn.posY = world.getTopSolidOrLiquidBlock(spawn.posX, spawn.posY) + 1;
		
		while (!world.isBlockSolidOnSide(spawn.posX, spawn.posY - 1, spawn.posZ, ForgeDirection.UP) && getDistanceBetweenPoints(origSpawn, spawn) < 1024) // we have to give up somewhere if the world is empty
		{
			spawn.posX += random.nextInt(3) - 1; // add random number between -1 to 1 x coordinate
			spawn.posZ += random.nextInt(3) - 1; // add random number between -1 to 1 z coordinate
			spawn.posY = world.getTopSolidOrLiquidBlock(spawn.posX, spawn.posY) + 1; // recalculate top block
		}
		
		return spawn;
	}
}

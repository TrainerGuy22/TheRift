package dragonborn.rift.util;

import java.util.logging.Logger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import dragonborn.rift.RiftMod;
import dragonborn.rift.dimension.TeleporterRift;

public class RiftUtil
{
	
	private static Logger	logger	= null;
	
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
}

package dragonborn.rift.util;

import dragonborn.rift.RiftMod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.logging.Logger;
import cpw.mods.fml.common.FMLLog;

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

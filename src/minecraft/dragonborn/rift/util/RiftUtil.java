package dragonborn.rift.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class RiftUtil
{
	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}
	
	public static void log(String message)
	{
		System.out.println("[Rift] " + message);
	}
}

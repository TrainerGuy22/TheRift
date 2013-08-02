package dragonborn.rift;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dragonborn.rift.proxy.CommonProxy;
import dragonborn.rift.util.RiftUtil;

@Mod(modid = RiftMod.MOD_ID, name = "The Rift", version = "0.5.0")
@NetworkMod(channels = { RiftMod.MOD_ID }, clientSideRequired = true, serverSideRequired = false, packetHandler = CommonProxy.class)
public class RiftMod
{
	public static final String	MOD_ID	= "therift";
	
	@Instance(MOD_ID)
	public static RiftMod		INSTANCE;
	@SidedProxy(serverSide = "dragonborn.rift.proxy.CommonProxy", clientSide = "dragonborn.rift.proxy.ClientProxy")
	public static CommonProxy	PROXY;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		RiftUtil.log("Loading Rift...");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		// Add an egg for the dragon
		EntityList.addMapping(EntityDragon.class, "EnderDragon", 63, 0, 0x6622AA);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		RiftUtil.log("Rift successfully loaded!");
	}
}

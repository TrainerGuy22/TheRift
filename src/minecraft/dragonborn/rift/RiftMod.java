package dragonborn.rift;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import dragonborn.rift.command.CommandRiftTP;
import dragonborn.rift.config.Blocks;
import dragonborn.rift.config.Config;
import dragonborn.rift.config.Items;
import dragonborn.rift.config.Recipes;
import dragonborn.rift.dimension.WorldGenDragonscaleOre;
import dragonborn.rift.dimension.WorldGenRiftTerrain;
import dragonborn.rift.dimension.WorldProviderRift;
import dragonborn.rift.proxy.ClientProxy;
import dragonborn.rift.proxy.CommonProxy;
import dragonborn.rift.util.RiftUtil;

@Mod(modid = RiftMod.MOD_ID, name = "The Rift", version = "0.5.0")
@NetworkMod(channels = { RiftMod.MOD_ID }, clientSideRequired = true, serverSideRequired = false, packetHandler = CommonProxy.class, clientPacketHandlerSpec = @SidedPacketHandler(channels = { RiftMod.MOD_ID }, packetHandler = ClientProxy.class))
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
		
		/** Load configuration */
		Config.loadConfig(event.getSuggestedConfigurationFile());
		
		/** Load blocks and items */
		Blocks.initBlocks();
		Items.initItems();
		
		/** Create recipes */
		Recipes.initRecipes();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		/** Add an egg for the dragon */
		EntityList.addMapping(EntityDragon.class, "EnderDragon", 63, 0, 0x6622AA);
		
		/** Register new dimension */
		int dimensionID = Config.DIMENSION_ID;
		DimensionManager.registerProviderType(dimensionID, WorldProviderRift.class, true);
		DimensionManager.registerDimension(dimensionID, dimensionID);
		
		/** Register world generators */
		GameRegistry.registerWorldGenerator(new WorldGenRiftTerrain());
		GameRegistry.registerWorldGenerator(new WorldGenDragonscaleOre());
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) throws Exception
	{
		/** Register commands */
		if (!RiftUtil.isClient())
		{
			ICommandManager cmdManager = MinecraftServer.getServer().getCommandManager();
			if (cmdManager instanceof ServerCommandManager)
			{
				ServerCommandManager mgr = (ServerCommandManager) cmdManager;
				mgr.registerCommand(new CommandRiftTP());
			}
			else
			{
				throw new Exception("ItemSearch cannot load due to an invalid server instance");
			}
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		RiftUtil.log("Rift successfully loaded!");
	}
}

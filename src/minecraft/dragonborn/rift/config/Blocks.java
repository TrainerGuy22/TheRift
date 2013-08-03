package dragonborn.rift.config;

import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dragonborn.rift.block.BlockDragonleaves;
import dragonborn.rift.block.BlockDragonwood;
import dragonborn.rift.block.BlockRiftTerrain;
import dragonborn.rift.item.ItemBlockTerrain;

public class Blocks
{
	public static final int			DEFAULT_TERRAIN_ID_START	= 200;
	public static final int			DEFAULT_NORMAL_ID_START		= 2000;
	
	/** Terrain IDs */
	public static int				blockID_dragonTerrain;
	public static int				blockID_dragonwood;
	public static int				blockID_dragonleaves;
	
	/** Block instances */
	public static BlockRiftTerrain	block_dragonTerrain;
	public static BlockDragonwood	block_dragonwood;
	public static BlockDragonleaves	block_dragonleaves;
	
	public static void initBlocks()
	{
		/** Load terrain IDs */
		Config.getConfig().addCustomCategoryComment("terrain", "Terrain block IDs must be BELOW 256!");
		blockID_dragonTerrain = Config.getInt("terrain", "riftTerrain", DEFAULT_TERRAIN_ID_START);
		blockID_dragonwood = Config.getInt("terrain", "dragonwood", DEFAULT_TERRAIN_ID_START + 1);
		blockID_dragonleaves = Config.getInt("terrain", "dragonleaves", DEFAULT_TERRAIN_ID_START + 2);
		
		/** Initialize Block instances */
		block_dragonTerrain = new BlockRiftTerrain(blockID_dragonTerrain, Material.ground);
		block_dragonwood = new BlockDragonwood(blockID_dragonwood, Material.wood);
		block_dragonleaves = new BlockDragonleaves(blockID_dragonleaves, Material.leaves);
		
		/** Register blocks */
		GameRegistry.registerBlock(block_dragonTerrain, ItemBlockTerrain.class, "riftTerrain");
		GameRegistry.registerBlock(block_dragonwood, "dragonwood");
		GameRegistry.registerBlock(block_dragonleaves, "dragonleaves");
		
		/** Add names */
		LanguageRegistry.addName(block_dragonwood, "Dragonwood");
		LanguageRegistry.addName(block_dragonleaves, "Dragonleaves");
	}
}

package dragonborn.rift.config;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dragonborn.rift.block.BlockRiftTerrain;
import dragonborn.rift.item.ItemBlockTerrain;

public class Blocks
{
	public static final int			DEFAULT_TERRAIN_ID_START	= 200;
	public static final int			DEFAULT_NORMAL_ID_START		= 2000;
	
	/** Terrain IDs */
	public static int				blockID_dragonTerrain;
	
	/** Block instances */
	public static BlockRiftTerrain	block_dragonTerrain;
	
	public static void initBlocks()
	{
		/** Load terrain IDs */
		Config.getConfig().addCustomCategoryComment("terrain", "Terrain block IDs must be BELOW 256!");
		blockID_dragonTerrain = Config.getInt("terrain", "riftTerrain", DEFAULT_TERRAIN_ID_START);
		
		/** Initialize Block instances */
		block_dragonTerrain = new BlockRiftTerrain(blockID_dragonTerrain, Material.ground);
		
		/** Register blocks */
		GameRegistry.registerBlock(block_dragonTerrain, ItemBlockTerrain.class, "riftTerrain");
	}
}

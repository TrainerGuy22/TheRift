package dragonborn.rift.config;

import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dragonborn.rift.block.BlockDragonleaves;
import dragonborn.rift.block.BlockDragonscaleOre;
import dragonborn.rift.block.BlockDragonwood;
import dragonborn.rift.block.BlockEnderFlower;
import dragonborn.rift.block.BlockRiftTerrain;
import dragonborn.rift.item.ItemBlockTerrain;

public class Blocks
{
	public static final int				DEFAULT_TERRAIN_ID_START	= 200;
	public static final int				DEFAULT_NORMAL_ID_START		= 2000;
	
	/** Terrain IDs */
	public static int					blockID_dragonTerrain;
	public static int					blockID_dragonwood;
	public static int					blockID_dragonleaves;
	public static int					blockID_enderFlower;
	public static int					blockID_dragonscaleOre;
	
	/** Block instances */
	public static BlockRiftTerrain		block_dragonTerrain;
	public static BlockDragonwood		block_dragonwood;
	public static BlockDragonleaves		block_dragonleaves;
	public static BlockEnderFlower		block_enderFlower;
	public static BlockDragonscaleOre	block_dragonscaleOre;
	
	public static void initBlocks()
	{
		/** Load terrain IDs */
		Config.getConfig().addCustomCategoryComment("terrain", "Terrain block IDs must be BELOW 256!");
		blockID_dragonTerrain = Config.getInt("terrain", "riftTerrain", DEFAULT_TERRAIN_ID_START);
		blockID_dragonwood = Config.getInt("terrain", "dragonwood", DEFAULT_TERRAIN_ID_START + 1);
		blockID_dragonleaves = Config.getInt("terrain", "dragonleaves", DEFAULT_TERRAIN_ID_START + 2);
		blockID_enderFlower = Config.getInt("terrain", "enderFlower", DEFAULT_TERRAIN_ID_START + 3);
		blockID_dragonscaleOre = Config.getInt("terrain", "dragonscaleOre", DEFAULT_TERRAIN_ID_START + 4);
		
		/** Initialize Block instances */
		block_dragonTerrain = new BlockRiftTerrain(blockID_dragonTerrain, Material.ground);
		block_dragonwood = new BlockDragonwood(blockID_dragonwood, Material.wood);
		block_dragonleaves = new BlockDragonleaves(blockID_dragonleaves, Material.leaves);
		block_enderFlower = new BlockEnderFlower(blockID_enderFlower);
		block_dragonscaleOre = new BlockDragonscaleOre(blockID_dragonscaleOre, Material.rock);
		
		/** Register Blocks */
		GameRegistry.registerBlock(block_dragonTerrain, ItemBlockTerrain.class, "riftTerrain");
		GameRegistry.registerBlock(block_dragonwood, "dragonwood");
		GameRegistry.registerBlock(block_dragonleaves, "dragonleaves");
		GameRegistry.registerBlock(block_enderFlower, "enderFlower");
		GameRegistry.registerBlock(block_dragonscaleOre, "dragonscaleOre");
		
		/** Add names */
		LanguageRegistry.addName(block_dragonwood, "Dragonwood");
		LanguageRegistry.addName(block_dragonleaves, "Dragonleaves");
		LanguageRegistry.addName(block_enderFlower, "Ender Flower");
		LanguageRegistry.addName(block_dragonscaleOre, "Dragonscale Ore");
	}
}

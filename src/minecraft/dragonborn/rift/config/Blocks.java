package dragonborn.rift.config;

import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dragonborn.rift.block.BlockDragongrass;
import dragonborn.rift.block.BlockDragonleaves;
import dragonborn.rift.block.BlockDragonscaleBrick;
import dragonborn.rift.block.BlockDragonscaleOre;
import dragonborn.rift.block.BlockDragonwood;
import dragonborn.rift.block.BlockEnderFlower;
import dragonborn.rift.block.BlockRiftDirt;
import dragonborn.rift.block.BlockRiftPortal;

public class Blocks
{
	public static final int				DEFAULT_BLOCK_ID_START	= 200;
	
	/** Terrain IDs (under 256) */
	public static int					blockID_enderDirt;
	public static int					blockID_dragonGrass;
	public static int					blockID_dragonwood;
	public static int					blockID_dragonleaves;
	public static int					blockID_enderFlower;
	public static int					blockID_dragonscaleOre;
	
	/** Normal IDs */
	public static int					blockID_dragonscaleBlock;
	public static int					blockID_riftPortal;
	
	/** Block instances */
	public static BlockRiftDirt			block_enderDirt;
	public static BlockDragongrass		block_dragonGrass;
	public static BlockDragonwood		block_dragonwood;
	public static BlockDragonleaves		block_dragonleaves;
	public static BlockEnderFlower		block_enderFlower;
	public static BlockDragonscaleOre	block_dragonscaleOre;
	public static BlockDragonscaleBrick	block_dragonscaleBlock;
	public static BlockRiftPortal		block_riftPortal;
	
	public static void initBlocks()
	{
		/** Load terrain IDs */
		Config.getConfig().addCustomCategoryComment("terrain", "Terrain block IDs must be BELOW 256!");
		blockID_enderDirt = Config.getInt("terrain", "enderDirt", DEFAULT_BLOCK_ID_START);
		blockID_dragonGrass = Config.getInt("terrain", "dragonGrass", DEFAULT_BLOCK_ID_START + 1);
		blockID_dragonwood = Config.getInt("terrain", "dragonwood", DEFAULT_BLOCK_ID_START + 2);
		blockID_dragonleaves = Config.getInt("terrain", "dragonleaves", DEFAULT_BLOCK_ID_START + 3);
		blockID_enderFlower = Config.getInt("terrain", "enderFlower", DEFAULT_BLOCK_ID_START + 4);
		blockID_dragonscaleOre = Config.getInt("terrain", "dragonscaleOre", DEFAULT_BLOCK_ID_START + 5);
		
		/** Load normal IDs */
		blockID_dragonscaleBlock = Config.getInt("blocks", "dragonscaleBlock", DEFAULT_BLOCK_ID_START + 6);
		blockID_riftPortal = Config.getInt("blocks", "riftPortal", DEFAULT_BLOCK_ID_START + 7);
		
		/** Initialize Block instances */
		block_enderDirt = new BlockRiftDirt(blockID_enderDirt, Material.ground);
		block_dragonGrass = new BlockDragongrass(blockID_dragonGrass, Material.ground);
		block_dragonwood = new BlockDragonwood(blockID_dragonwood, Material.wood);
		block_dragonleaves = new BlockDragonleaves(blockID_dragonleaves, Material.leaves);
		block_enderFlower = new BlockEnderFlower(blockID_enderFlower);
		block_dragonscaleOre = new BlockDragonscaleOre(blockID_dragonscaleOre, Material.rock);
		block_dragonscaleBlock = new BlockDragonscaleBrick(blockID_dragonscaleBlock, Material.dragonEgg);
		block_riftPortal = new BlockRiftPortal(blockID_riftPortal);
		
		/** Register Blocks */
		GameRegistry.registerBlock(block_enderDirt, "enderDirt");
		GameRegistry.registerBlock(block_dragonGrass, "dragonGrass");
		GameRegistry.registerBlock(block_dragonwood, "dragonwood");
		GameRegistry.registerBlock(block_dragonleaves, "dragonleaves");
		GameRegistry.registerBlock(block_enderFlower, "enderFlower");
		GameRegistry.registerBlock(block_dragonscaleOre, "dragonscaleOre");
		GameRegistry.registerBlock(block_dragonscaleBlock, "dragonscaleBlock");
		GameRegistry.registerBlock(block_riftPortal, "riftPortal");
		
		/** Add names */
		LanguageRegistry.addName(block_enderDirt, "Ender Dirt");
		LanguageRegistry.addName(block_dragonGrass, "Dragongrass");
		LanguageRegistry.addName(block_dragonwood, "Dragonwood");
		LanguageRegistry.addName(block_dragonleaves, "Dragonleaves");
		LanguageRegistry.addName(block_enderFlower, "Ender Flower");
		LanguageRegistry.addName(block_dragonscaleOre, "Dragonscale Ore");
		LanguageRegistry.addName(block_dragonscaleBlock, "Dragonscale Block");
		LanguageRegistry.addName(block_riftPortal, "Rift Portal");
	}
}

package dragonborn.rift.config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{
	public static void initRecipes()
	{
		/** Ender Flower -> Ender Nugget */
		GameRegistry.addShapelessRecipe(new ItemStack(Items.item_enderNugget, 1), Blocks.block_enderFlower);
		/** Ender Nuggets -> Ender Pearl */
		GameRegistry.addRecipe(new ItemStack(Item.enderPearl, 1), "NNN", "NNN", "NNN", 'N', Items.item_enderNugget);
		/** Ender Pearl -> Ender Nuggets */
		GameRegistry.addRecipe(new ItemStack(Items.item_enderNugget, 9), "P", 'P', Item.enderPearl);
		/** Dragonscale -> Dragonscale Ingot */
		GameRegistry.addSmelting(Items.item_dragonscale.itemID, new ItemStack(Items.item_dragonscaleIngot, 1), 2.0f);
		/** Dragonscale Axe */
		GameRegistry.addRecipe(new ItemStack(Items.item_dragonscaleAxe, 1), "II", "IS", " S", 'I', Items.item_dragonscaleIngot, 'S', Item.stick);
		GameRegistry.addRecipe(new ItemStack(Items.item_dragonscaleAxe, 1), "II", "SI", "S ", 'I', Items.item_dragonscaleIngot, 'S', Item.stick);
		/** Dragonscale Hoe */
		GameRegistry.addRecipe(new ItemStack(Items.item_dragonscaleHoe, 1), "II", " S", " S", 'I', Items.item_dragonscaleIngot, 'S', Item.stick);
		GameRegistry.addRecipe(new ItemStack(Items.item_dragonscaleHoe, 1), "II", "S ", "S ", 'I', Items.item_dragonscaleIngot, 'S', Item.stick);
		/** Dragonscale Pickaxe */
		GameRegistry.addRecipe(new ItemStack(Items.item_dragonscalePickaxe, 1), "III", " S ", " S ", 'I', Items.item_dragonscaleIngot, 'S', Item.stick);
		/** Dragonscale Shovel */
		GameRegistry.addRecipe(new ItemStack(Items.item_dragonscaleShovel, 1), "I", "S", "S", 'I', Items.item_dragonscaleIngot, 'S', Item.stick);
		/** Dragonscale Sword */
		GameRegistry.addRecipe(new ItemStack(Items.item_dragonscaleSword, 1), "I", "I", "S", 'I', Items.item_dragonscaleIngot, 'S', Item.stick);
<<<<<<< HEAD
=======
		/** Dragonscale Block */
		GameRegistry.addRecipe(new ItemStack(Blocks.block_dragonscaleBlock, 1), "III", "III", "III", 'I', Items.item_dragonscaleIngot);
		/** Dragon Lighter */
		GameRegistry.addRecipe(new ItemStack(Items.item_dragonLighter, 1), "I ", " S", 'I', Items.item_dragonscaleIngot, 'S', Items.item_dragonscale);
		
		/***/
		/***/
		/*** TEMPORARY RECIPES (real recipes to be added after ModJam) */
		/***/
		/***/
		/** End Stone -> Furnace (temporary) */
		GameRegistry.addRecipe(new ItemStack(Block.furnaceIdle, 1), "SSS", "S S", "SSS", 'S', Block.whiteStone);
		/** Dragonwood -> Planks */
		GameRegistry.addRecipe(new ItemStack(Block.planks, 4), "L", 'L', Blocks.block_dragonwood);
>>>>>>> ee9f4d8201f7386f8399ec85abd9a28abf1d8dbb
	}
}

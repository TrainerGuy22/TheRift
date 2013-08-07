package dragonborn.rift.config;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dragonborn.rift.item.ItemDragonLighter;
import dragonborn.rift.item.ItemDragonscale;
import dragonborn.rift.item.ItemDragonscaleAxe;
import dragonborn.rift.item.ItemDragonscaleHoe;
import dragonborn.rift.item.ItemDragonscaleIngot;
import dragonborn.rift.item.ItemDragonscalePickaxe;
import dragonborn.rift.item.ItemDragonscaleShovel;
import dragonborn.rift.item.ItemDragonscaleSword;
import dragonborn.rift.item.ItemEnderNugget;

public class Items
{
	public static final int					DEFAULT_ITEM_ID_START	= 20000;
	
	/** Item IDs */
	public static int						itemID_enderNugget;
	public static int						itemID_dragonscale;
	public static int						itemID_dragonscaleIngot;
	public static int						itemID_dragonscaleAxe;
	public static int						itemID_dragonscaleHoe;
	public static int						itemID_dragonscalePickaxe;
	public static int						itemID_dragonscaleShovel;
	public static int						itemID_dragonscaleSword;
	public static int						itemID_dragonLighter;
	
	/** Item instances */
	public static ItemEnderNugget			item_enderNugget;
	public static ItemDragonscale			item_dragonscale;
	public static ItemDragonscaleIngot		item_dragonscaleIngot;
	public static ItemDragonscaleAxe		item_dragonscaleAxe;
	public static ItemDragonscaleHoe		item_dragonscaleHoe;
	public static ItemDragonscalePickaxe	item_dragonscalePickaxe;
	public static ItemDragonscaleShovel		item_dragonscaleShovel;
	public static ItemDragonscaleSword		item_dragonscaleSword;
	public static ItemDragonLighter			item_dragonLighter;
	
	public static void initItems()
	{
		/** Load item IDs */
		itemID_enderNugget = Config.getInt("items", "enderNugget", DEFAULT_ITEM_ID_START);
		itemID_dragonscale = Config.getInt("items", "dragonScale", DEFAULT_ITEM_ID_START + 1);
		itemID_dragonscaleIngot = Config.getInt("items", "dragonScaleIngot", DEFAULT_ITEM_ID_START + 2);
		itemID_dragonscaleAxe = Config.getInt("items", "dragonScaleAxe", DEFAULT_ITEM_ID_START + 3);
		itemID_dragonscaleHoe = Config.getInt("items", "dragonScaleHoe", DEFAULT_ITEM_ID_START + 4);
		itemID_dragonscalePickaxe = Config.getInt("items", "dragonScalePickaxe", DEFAULT_ITEM_ID_START + 5);
		itemID_dragonscaleShovel = Config.getInt("items", "dragonScaleShovel", DEFAULT_ITEM_ID_START + 6);
		itemID_dragonscaleSword = Config.getInt("items", "dragonScaleSword", DEFAULT_ITEM_ID_START + 7);
		itemID_dragonLighter = Config.getInt("items", "dragonLighter", DEFAULT_ITEM_ID_START + 8);
		
		/** Initialize Item instances */
		item_enderNugget = new ItemEnderNugget(itemID_enderNugget);
		item_dragonscale = new ItemDragonscale(itemID_dragonscale);
		item_dragonscaleIngot = new ItemDragonscaleIngot(itemID_dragonscaleIngot);
		item_dragonscaleAxe = new ItemDragonscaleAxe(itemID_dragonscaleAxe);
		item_dragonscaleHoe = new ItemDragonscaleHoe(itemID_dragonscaleHoe);
		item_dragonscalePickaxe = new ItemDragonscalePickaxe(itemID_dragonscalePickaxe);
		item_dragonscaleShovel = new ItemDragonscaleShovel(itemID_dragonscaleShovel);
		item_dragonscaleSword = new ItemDragonscaleSword(itemID_dragonscaleSword);
		item_dragonLighter = new ItemDragonLighter(itemID_dragonLighter);
		
		/** Register Items */
		GameRegistry.registerItem(item_enderNugget, "enderNugget");
		GameRegistry.registerItem(item_dragonscale, "dragonscale");
		GameRegistry.registerItem(item_dragonscaleIngot, "dragonscaleIngot");
		GameRegistry.registerItem(item_dragonscaleAxe, "dragonscaleAxe");
		GameRegistry.registerItem(item_dragonscaleHoe, "dragonscaleHoe");
		GameRegistry.registerItem(item_dragonscalePickaxe, "dragonscalePickaxe");
		GameRegistry.registerItem(item_dragonscaleShovel, "dragonscaleShovel");
		GameRegistry.registerItem(item_dragonscaleSword, "dragonscaleSword");
		GameRegistry.registerItem(item_dragonLighter, "dragonLighter");
		
		if (!Config.USE_LANGUAGE_FILE)
		{
			/** Add names */
			LanguageRegistry.addName(item_enderNugget, "Ender Nugget");
			LanguageRegistry.addName(item_dragonscale, "Dragonscale");
			LanguageRegistry.addName(item_dragonscaleIngot, "Dragonscale Ingot");
			LanguageRegistry.addName(item_dragonscaleAxe, "Dragonscale Axe");
			LanguageRegistry.addName(item_dragonscaleHoe, "Dragonscale Hoe");
			LanguageRegistry.addName(item_dragonscalePickaxe, "Dragonscale Pickaxe");
			LanguageRegistry.addName(item_dragonscaleShovel, "Dragonscale Shovel");
			LanguageRegistry.addName(item_dragonscaleSword, "Dragonscale Sword");
			LanguageRegistry.addName(item_dragonLighter, "Dragon Lighter");
		}
	}
}

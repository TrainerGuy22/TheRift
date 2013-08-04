package dragonborn.rift.config;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dragonborn.rift.item.ItemDragonscale;
import dragonborn.rift.item.ItemDragonscaleIngot;
import dragonborn.rift.item.ItemEnderNugget;

public class Items
{
	public static final int				DEFAULT_ITEM_ID_START	= 20000;
	
	public static int					itemID_enderNugget;
	public static int					itemID_dragonscale;
	public static int					itemID_dragonscaleIngot;
	
	public static ItemEnderNugget		item_enderNugget;
	public static ItemDragonscale		item_dragonscale;
	public static ItemDragonscaleIngot	item_dragonscaleIngot;
	
	public static void initItems()
	{
		/** Load item IDs */
		itemID_enderNugget = Config.getInt("items", "enderNugget", DEFAULT_ITEM_ID_START);
		itemID_dragonscale = Config.getInt("items", "dragonScale", DEFAULT_ITEM_ID_START + 1);
		itemID_dragonscaleIngot = Config.getInt("items", "dragonScaleIngot", DEFAULT_ITEM_ID_START + 2);
		
		/** Initialize Item instances */
		item_enderNugget = new ItemEnderNugget(itemID_enderNugget);
		item_dragonscale = new ItemDragonscale(itemID_dragonscale);
		item_dragonscaleIngot = new ItemDragonscaleIngot(itemID_dragonscaleIngot);
		
		/** Register Items */
		GameRegistry.registerItem(item_enderNugget, "enderNugget");
		GameRegistry.registerItem(item_dragonscale, "dragonscale");
		GameRegistry.registerItem(item_dragonscaleIngot, "dragonscaleIngot");
		
		/** Add names */
		LanguageRegistry.addName(item_enderNugget, "Ender Nugget");
		LanguageRegistry.addName(item_dragonscale, "Dragonscale");
		LanguageRegistry.addName(item_dragonscaleIngot, "Dragonscale Ingot");
	}
}

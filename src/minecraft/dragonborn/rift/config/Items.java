package dragonborn.rift.config;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dragonborn.rift.item.ItemEnderNugget;

public class Items
{
	public static final int			DEFAULT_ITEM_ID_START	= 20000;
	
	public static int				itemID_enderNugget;
	
	public static ItemEnderNugget	item_enderNugget;
	
	public static void initItems()
	{
		/** Load item IDs */
		itemID_enderNugget = Config.getInt("items", "enderNugget", DEFAULT_ITEM_ID_START);
		
		/** Initialize Item instances */
		item_enderNugget = new ItemEnderNugget(itemID_enderNugget);
		
		/** Register Items */
		GameRegistry.registerItem(item_enderNugget, "enderNugget");
		
		/** Add names */
		LanguageRegistry.addName(item_enderNugget, "Ender Nugget");
	}
}

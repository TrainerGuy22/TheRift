package dragonborn.rift.command;

import java.util.ArrayList;
import java.util.List;

import dragonborn.rift.config.Config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class CommandRiftTP extends CommandBase
{
	
	@Override
	public String getCommandName()
	{
		return "rift";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "Usage: /rift <goto/return>";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (sender instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) sender;
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("goto"))
				{
					if (player.dimension != Config.DIMENSION_ID)
					{
						player.travelToDimension(Config.DIMENSION_ID); // Enter The Rift
					}
					else
					{
						player.addChatMessage("You are already in The Rift.");
					}
				}
				else if (args[0].equalsIgnoreCase("return"))
				{
					if (player.dimension == Config.DIMENSION_ID)
					{
						player.travelToDimension(0); // Return to overworld
					}
					else
					{
						player.addChatMessage("You are not in The Rift.");
					}
				}
			}
		}
		else
		{
			sender.sendChatToPlayer(ChatMessageComponent.func_111077_e("This command must be run as a player."));
		}
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("goto");
		options.add("return");
		return options;
	}
	
}

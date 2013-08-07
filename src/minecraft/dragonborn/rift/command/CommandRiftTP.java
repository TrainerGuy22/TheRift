package dragonborn.rift.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import dragonborn.rift.config.Config;
import dragonborn.rift.util.RiftUtil;

public class CommandRiftTP extends CommandBase
{
	
	@Override
	public String getCommandName()
	{
		return "rift";
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		if (!MinecraftServer.getServer().isDedicatedServer())
			return true;
		
		if (!(sender instanceof EntityPlayerMP))
			return true; // they get the "must be a player" message
			
		EntityPlayerMP player = (EntityPlayerMP) sender;
		
		return MinecraftServer.getServer().getConfigurationManager().getOps().contains(player.getEntityName().toLowerCase()) || player.capabilities.isCreativeMode;
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "Usage: /rift <goto/return>";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (sender instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) sender;
			
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("goto"))
				{
					if (player.dimension != Config.RIFT_DIMENSION_ID)
					{
						double oldX = player.posX;
						double oldZ = player.posZ;
						RiftUtil.teleportPlayer(player, Config.RIFT_DIMENSION_ID);
						double newY = player.worldObj.getTopSolidOrLiquidBlock((int) Math.round(oldX), (int) Math.round(oldZ)) + 1;
						player.posX = oldX;
						player.posY = newY;
						player.posZ = oldZ;
					}
					else
					{
						player.addChatMessage("You are already in The Rift.");
					}
				}
				else if (args[0].equalsIgnoreCase("return"))
				{
					if (player.dimension == Config.RIFT_DIMENSION_ID)
					{
						double oldX = player.posX;
						double oldZ = player.posZ;
						RiftUtil.teleportPlayer(player, 0);
						double newY = player.worldObj.getTopSolidOrLiquidBlock((int) Math.round(oldX), (int) Math.round(oldZ)) + 1;
						player.posX = oldX;
						player.posY = newY;
						player.posZ = oldZ;
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

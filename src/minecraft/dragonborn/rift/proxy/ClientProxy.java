package dragonborn.rift.proxy;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.Player;
import dragonborn.rift.util.RiftUtil;

public class ClientProxy extends CommonProxy
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if (!RiftUtil.isClient())
			return; // Don't handle server-side packets on client (how the crap are we even here?!)
			
		try
		{
			EntityPlayer ePlayer = null;
			if (player instanceof EntityPlayer)
				ePlayer = (EntityPlayer) player;
			
			ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
			DataInputStream in = new DataInputStream(bis);
			
			int packetID = in.readInt();
			
			switch (packetID)
			{
				default:
				{
					RiftUtil.log("Unknown packet ID received on client: " + packetID);
				}
			}
		}
		catch (Exception ex)
		{
			RiftUtil.log("An unhandled error has occurred while receiving a packet:");
			ex.printStackTrace();
		}
	}
}

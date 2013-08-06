package dragonborn.rift.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class RiftSavedData extends WorldSavedData
{
	public boolean	hasDragonDied;
	public boolean	hasDefeatedRift;
	
	public RiftSavedData(String name)
	{
		super(name);
		this.hasDragonDied = false;
		this.hasDefeatedRift = false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.hasDragonDied = nbt.getBoolean("dragonDied");
		this.hasDefeatedRift = nbt.getBoolean("defeatedRift");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setBoolean("dragonDied", this.hasDragonDied);
		nbt.setBoolean("defeatedRift", this.hasDefeatedRift);
	}
	
}

package dragonborn.rift.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class RiftSavedData extends WorldSavedData
{
	public boolean	hasDragonDied;
	
	public RiftSavedData(String name)
	{
		super(name);
		this.hasDragonDied = false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.hasDragonDied = nbt.getBoolean("dragonDied");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setBoolean("dragonDied", this.hasDragonDied);
	}
	
}

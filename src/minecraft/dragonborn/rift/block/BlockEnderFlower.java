package dragonborn.rift.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import dragonborn.rift.config.Blocks;

public class BlockEnderFlower extends BlockFlower
{
	public BlockEnderFlower(int id)
	{
		super(id);
		setLightValue(0.2f);
		setUnlocalizedName("enderFlower");
		setStepSound(Block.soundGrassFootstep);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.blockIcon = register.registerIcon(RiftMod.MOD_ID + ":enderflower");
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int blockID)
	{
		return blockID == Blocks.blockID_dragonTerrain;
	}
}

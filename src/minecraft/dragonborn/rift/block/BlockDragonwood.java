package dragonborn.rift.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;

public class BlockDragonwood extends Block
{
	private Icon	side;
	private Icon	top;
	
	public BlockDragonwood(int id, Material material)
	{
		super(id, material);
		setUnlocalizedName("dragonwood");
		setHardness(3.0f);
		setResistance(4.0f);
		setCreativeTab(CreativeTabs.tabBlock);
		setStepSound(soundWoodFootstep);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.side = register.registerIcon(RiftMod.MOD_ID + ":dragonwood_side");
		this.top = register.registerIcon(RiftMod.MOD_ID + ":dragonwood_top");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		if (side == ForgeDirection.UP.ordinal() || side == ForgeDirection.DOWN.ordinal())
			return this.top;
		return this.side;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		return getIcon(side, 0);
	}
	
}

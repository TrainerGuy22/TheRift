package dragonborn.rift.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dragonborn.rift.RiftMod;
import dragonborn.rift.config.Blocks;
import dragonborn.rift.util.RiftUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BlockRiftDirt extends Block
{
	
	public BlockRiftDirt(int id, Material material)
	{
		super(id, material);
		setHardness(0.6f);
		setResistance(1.0f);
		setUnlocalizedName("enderDirt");
		setStepSound(soundGrassFootstep);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
	{
		return plant instanceof BlockEnderFlower;
	}
	
	@Override
	public void registerIcons(IconRegister register)
	{
		this.blockIcon = register.registerIcon(RiftMod.MOD_ID + ":enderdirt");
	}
}

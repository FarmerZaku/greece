package mod.greece;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.main.Main;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ThatchSlope extends BlockContainer {
	
	public ThatchSlope(int id, Material material) {
		super(id, material);
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Greece.thatchSlope.blockID;
	}
	
	
	/**
	* The type of render function that is called for this block
	*/
	public int getRenderType() {
		return -2;
	}
	
	/**
	* Is this block (a) opaque and (B) a full 1m cube? This determines whether or not to render the shared face of two
	* adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	*/
	public boolean isOpaqueCube() {
		return false;
	}
	
	/**
	* If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	*/
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int rotation = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, rotation - 1, 2);
	}
	
	public TileEntity createNewTileEntity(World par1World) {
		return new TileEntitySlope();
	}
	
}

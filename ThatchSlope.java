package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ThatchSlope extends BlockStairs {
	
	public ThatchSlope(int id, Block block, int par3) {
		super(id, block, par3);
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Greece.thatchSlope.blockID;
	}
}

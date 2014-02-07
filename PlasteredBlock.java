package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class PlasteredBlock extends Block {
	
	public PlasteredBlock(int id, Material material, int dropId) {
		super(id, material);
		setTextureName("Greece:plaster");
		
	}
	
	 /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
		world.setBlock(x, y, z, getBlockIdFromPlasterID(metadata));
	}
	
	public int idDropped(int metadata, Random random, int fortune) {
		return 0;
	}
	
	public static int getPlasterID(int blockID) {
		//can only have 15. Make sure this exactly matches the array in getBlockIdFromPlasterID! I know, it's janky... 
		int[] plasterIDs = {Block.planks.blockID, Block.dirt.blockID, Block.stone.blockID, Block.cobblestone.blockID,
				Block.cobblestoneMossy.blockID, Block.brick.blockID, Greece.marble.blockID, Greece.limestone.blockID,
				Greece.mudbrick.blockID};
		for (int i = 0; i < plasterIDs.length; ++i) {
			if (blockID == plasterIDs[i]) {
				return i;
			}
		}
		return -1;
	}
	
	public static int getBlockIdFromPlasterID(int plasterID) {
		//can only have 15. Make sure this exactly matches the array in getPlasterID! I know, it's janky...
		int[] plasterIDs = {Block.planks.blockID, Block.dirt.blockID, Block.stone.blockID, Block.cobblestone.blockID,
				Block.cobblestoneMossy.blockID, Block.brick.blockID, Greece.marble.blockID, Greece.limestone.blockID,
				Greece.mudbrick.blockID};
		if (plasterID < 0 || plasterID > plasterIDs.length) {
			System.err.println("PlasteredBlock: Error! Plaster ID " + plasterID + " outside of bounds of array!");
			return 0;
		}
		return plasterIDs[plasterID];
	}
}

package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class PlasteredBlock extends Block {

	private int toDrop;
	public PlasteredBlock(int id, Material material, int dropId) {
		super(id, material);
		setTextureName("Greece:plaster");
		toDrop = dropId;
	}
	
	 /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
		world.setBlock(x, y, z, toDrop);
	}
	
	public int idDropped(int metadata, Random random, int fortune) {
		return 0;
	}

}

package mod.greece;

import java.util.Calendar;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class GreekAgingBlock extends Block {
	private int resultID;
	private int requiredTicks;
	private boolean heatBased;
	
	/**
     * eventAge is how many in-game hours (~50 sec of real time) it takes for the block to finish aging
     * Note that, due to us using metadata for this and metadata being small, eventAge has a max value of 17
     */
	public GreekAgingBlock(int id, Material material, int resultID, int requiredTicks, boolean heatBased) {
		super(id, material);
		setCreativeTab(CreativeTabs.tabBlock);
		this.resultID = resultID;
		this.requiredTicks = requiredTicks;
		if (requiredTicks > 17) {
			this.requiredTicks = 17;
			System.out.println("[GreekAgingBlock] Value for requiredTicks is too high!");
		}
		this.heatBased = heatBased;
	}
	
	/**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z) {
    	world.setBlockMetadataWithNotify(x, y, z, 0, 4);
    	//Parameter 4 is the id of the block to update, in case the block gets replaced in the mean time
    	world.scheduleBlockUpdate(x, y, z, world.getBlockId(x, y, z), this.tickRate());
    }
    
    public int tickRate() {
    	//Approx. 20 ticks per second
    	return 20*30;
    }
    
	public void updateTick(World world, int x, int y, int z, Random random) {
		//System.out.println("Cur val: " + world.getBlockMetadata(x, y, z));
		int toAdd = 1;
		if (heatBased) {
			//Check if any of the adjacent blocks (besides below) are well-lit, and if so count that as drying sunlight
			if ((world.getBlockLightValue(x, y+1, z) >= 13) || (world.getBlockLightValue(x-1, y, z) >= 13) ||
					(world.getBlockLightValue(x+1, y, z) >= 13) || (world.getBlockLightValue(x, y, z-1) >= 13) ||
					(world.getBlockLightValue(x, y, z+1) >= 13)) {
				toAdd = 1;
			} else {
				toAdd = 0;
			}
			//If this biome is hot, double the drying value. Plains, etc have a temp of like 0.9, deserts 2.0
			if (world.getBiomeGenForCoords(x, z).temperature >= 1.0f) {
				toAdd *= 2;
			}
			//If it's really hot, quadruple the drying value
			if (world.getBiomeGenForCoords(x, z).temperature >= 1.8f) {
				toAdd *= 2;
			}
			//If the drying block is on top of stone or iron, double the drying value
			Material materialBelow = world.getBlockMaterial(x, y-1, z);
			if (materialBelow != null && (materialBelow == Material.rock || materialBelow == Material.iron)) {
				toAdd *= 2;
			}
			//If it's above fire, just go ahead and set a high enough drying value to instantly dry it
			if (materialBelow != null && materialBelow == Material.fire) {
				toAdd = 20;
			}
		}
		if (world.getBlockMetadata(x,y,z)+toAdd >= requiredTicks) {
			//Replace the drying block with the dry block
			world.setBlock(x, y, z, resultID, 0, 2);
			return;
		}
		//Update the metadata of the drying block to keep track of how close it is to finishing
		world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x,y,z)+toAdd, 4);
		world.scheduleBlockUpdate(x, y, z, world.getBlockId(x, y, z), this.tickRate());
	}
	
	public int getMetadata(int meta) {
		return meta;
	}
}

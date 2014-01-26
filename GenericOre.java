package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GenericOre extends Block {

	private int dropID;
	
	public GenericOre(int id, Material material, int drop_ID) {
		super(id, material);
		
		setHardness(4.0f);
		setStepSound(Block.soundStoneFootstep);
		setUnlocalizedName("genericOre");
		setCreativeTab(CreativeTabs.tabBlock);
		dropID = drop_ID;
	}
	public int idDropped(int metadata, Random random, int fortune) {
		return dropID;
	}

}

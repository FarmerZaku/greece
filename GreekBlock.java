package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class GreekBlock extends Block {
	private int dropID;
	private String blockType;
	
	public GreekBlock(int id, Material material) {
		super(id, material);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public GreekBlock(int id, Material material, int drop_id) {
		super(id, material);
		//setTextureName(BasicInfo.NAME.toLowerCase() + ":plaster");
		setCreativeTab(CreativeTabs.tabBlock);
		dropID = drop_id;
	}
	
	public GreekBlock(int id, Material material, int drop_id, String type) {
		super(id, material);
		setCreativeTab(CreativeTabs.tabBlock);
		dropID = drop_id;
		blockType = type;
	}
	
	public int idDropped(int metadata, Random random, int fortune) {
		return dropID;
	}
}

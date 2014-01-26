package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GenericBlock extends Block {
	private int dropID;
	
	public GenericBlock(int id, Material material, int drop_id) {
		super(id, material);
		//setTextureName(BasicInfo.NAME.toLowerCase() + ":plaster");
		dropID = drop_id;
	}
	public int idDropped(int metadata, Random random, int fortune) {
		return dropID;
	}
}

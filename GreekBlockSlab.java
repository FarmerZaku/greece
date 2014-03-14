package mod.greece;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class GreekBlockSlab extends BlockHalfSlab {

	public GreekBlockSlab(int par1, boolean par2, Material par3Material) {
		super(par1, par2, par3Material); // if par2, opaqueCubeLookup
		setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);//Fixes most of the Halfslab lightning bugs
	}

	@Override
	public String getFullSlabName(int i) {
		/*if (!isDoubleSlab)
			return super.getUnlocalizedName() + "roofTilesSlab";
		else
			return super.getUnlocalizedName() + "roofTilesSlabDouble";*/
		if (!isDoubleSlab)
			return "Roof Tiles Double Slab";
		else
			return "Roof Tiles Slab";
	}

	

	

}

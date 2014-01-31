package mod.greece;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class GreekFood extends ItemFood {

	public GreekFood(int par1, int par2, float par3, boolean par4) {
		super(par1, par2, par3, par4);
		setCreativeTab(CreativeTabs.tabFood);
	}

}

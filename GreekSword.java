package mod.greece;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class GreekSword extends ItemSword {

	public GreekSword(int id, EnumToolMaterial material) {
		super(id, material);
		setCreativeTab(CreativeTabs.tabCombat);
	}

}

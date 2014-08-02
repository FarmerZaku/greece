package mod.greece;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class GreekFood extends ItemFood {

	public GreekFood(int id, int healAmount, float saturation, boolean wolvesLike) {
		super(id, healAmount, saturation, wolvesLike);
		setCreativeTab(CreativeTabs.tabFood);
	}

}

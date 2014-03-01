package mod.greece;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GreekItemCoin extends Item {

	public GreekItemCoin(int id, String type) {
		super(id);
		if (type=="drachma") {
			setMaxStackSize(6000);
			setTextureName(GreeceInfo.NAME.toLowerCase() + ":drachma");
			setUnlocalizedName("drachma");
		}
		else if (type=="obol") {
			setMaxStackSize(6);
			setTextureName(GreeceInfo.NAME.toLowerCase() + ":obol");
			setUnlocalizedName("obol");
		}
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public boolean isFull3D()
    {
        return true;
    }

}

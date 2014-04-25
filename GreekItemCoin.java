package mod.greece;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GreekItemCoin extends Item {

	public GreekItemCoin(int id, String type) {
		super(id);
		if (type=="drachma") {
			//setMaxStackSize(6000); // this doesn't work. Can't have stack size greater than 64 *stored in inventory*
			setTextureName(GreeceInfo.NAME.toLowerCase() + ":drachma");
			setUnlocalizedName("drachma");
		}
		else if (type=="obol") {
			//setMaxStackSize(6); // if the problem above can be fixed, make this a higher multiple of 6.
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

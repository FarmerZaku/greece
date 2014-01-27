package mod.greece;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BronzeIngot extends Item {

	public BronzeIngot(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("bronzeIngot");
		setTextureName(GreeceInfo.NAME.toLowerCase() + ":bronze_ingot");
	}
	
	/*@Override
	public boolean func_111207_a(ItemStack itemStack, EntityPlayer player, EntityLivingBase target){
		if (!target.worldObj.isRemote) {
			
		}
		
		return false;
	}*/

}

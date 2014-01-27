package mod.greece;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;

public class GreekItem extends Item {
	private float weaponDamage;
	protected EnumToolMaterial toolMaterial;

	public GreekItem(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public GreekItem(int id, EnumToolMaterial material) {
		super(id);
		this.toolMaterial = material;
		this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
        this.weaponDamage = 0.0F + material.getDamageVsEntity();
	}
	
	public boolean isFull3D()
    {
        return true;
    }
	
	/*@Override
	public boolean func_111207_a(ItemStack itemStack, EntityPlayer player, EntityLivingBase target){
		if (!target.worldObj.isRemote) {
			
		}
		
		return false;
	}*/
	
	public String getToolMaterialName()
    {
        return this.toolMaterial.toString();
    }

}

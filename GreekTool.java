package mod.greece;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;

public class GreekTool extends Item {
	private float weaponDamage;
	protected EnumToolMaterial toolMaterial;
	private int damagePerUse;

	public GreekTool(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public GreekTool(int id, EnumToolMaterial material, int dpu) {
		super(id);
		this.toolMaterial = material;
		this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
        this.weaponDamage = 0.0F + material.getDamageVsEntity();
        damagePerUse = dpu;
	}
	
	public boolean isFull3D()
    {
        return true;
    }
	
	public int getDamagePerUse() {
		return damagePerUse;
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

package mod.greece;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekShield extends Item
{
    private float weaponDamage;
    private final EnumToolMaterial toolMaterial;

    public GreekShield(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1);
        this.toolMaterial = par2EnumToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2EnumToolMaterial.getMaxUses()*6);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.weaponDamage = 4.0F + par2EnumToolMaterial.getDamageVsEntity();
    }
    
//    @Override
//    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int chargeVal)
//    {	
//    	if (world.isRemote && !player.isUsingItem()) {
//	    	if (itemStack != null && itemStack.stackTagCompound.hasKey("LastItem")) {
//	    		int lastItem = itemStack.stackTagCompound.getInteger("LastItem");
//	    		if (player.inventory.mainInventory[lastItem] != null) {
//	    			player.inventory.setCurrentItem(player.inventory.mainInventory[lastItem].itemID, 0, false, false);
//	    		}
//	    	}
//    	}
//    }

    /**
     * Called when the player Left Clicks (attacks) an entity.
     * Processed before damage is done, if return value is true further processing is canceled
     * and the entity is not attacked.
     *
     * @param stack The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
    	//Causes 1 damage on an attack and returns, canceling further attack processing
    	entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 1);
        return true;
    }
    
    public float func_82803_g()
    {
        return this.toolMaterial.getDamageVsEntity();
    }
    
    public float getDamageReduction() {
    	return 1f;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
    	//System.out.println("1");
    	int lastDmg = 0;
    	NBTTagCompound tag = itemStack.getTagCompound();
    	//System.out.println("2");
    	if (tag == null) {
    		tag = new NBTTagCompound();
    	}
    	if (tag.hasKey("LastDmg"))
        {
    		lastDmg = tag.getInteger("LastDmg");
        }
    	//System.out.println("3");
    	int curDamage = this.getDamage(itemStack);
    	if (curDamage >= this.getMaxDamage()) {
    		--itemStack.stackSize;
    		entity.playSound("mob.zombie.woodbreak", 1, 1);
    		if (entity instanceof EntityPlayer) {
    			ItemStack[] inv = ((EntityPlayer)entity).inventory.mainInventory;
    			for (int i = 0; i < inv.length; ++i) {
    				if (inv[i] != null && inv[i].equals(itemStack)) {
    					inv[i] = null;
    					return;
    				}
    			}
    		}
    	} else if (lastDmg != curDamage) {
    		entity.playSound("mob.zombie.wood", 1, 1);
    	}
    	tag.setInteger("LastDmg", curDamage);
    	itemStack.setTagCompound(tag);
    }
    
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        par1ItemStack.damageItem(1, par3EntityLivingBase);
        return true;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName()
    {
        return this.toolMaterial.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return this.toolMaterial.getToolCraftingMaterial() == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    /**
     * Gets a map of item attribute modifiers, used by GreekShield to increase hit damage.
     */
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.weaponDamage, 0));
        return multimap;
    }
}

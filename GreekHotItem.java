package mod.greece;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GreekHotItem extends Item {

	private static int cooledItemID;
	private static int coolingTime;
	public GreekHotItem(int id, int cooledItemID, int coolingTime) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("genericItem");
		this.cooledItemID = cooledItemID;
		this.coolingTime = coolingTime;
	}
	/**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
    	this.setDamage(itemStack, this.getDamage(itemStack)+1);
    	if (this.getDamage(itemStack) > coolingTime) {
    		if (entity instanceof EntityPlayer) {
    			ItemStack[] inv = ((EntityPlayer)entity).inventory.mainInventory;
    			for (int i = 0; i < inv.length; ++i) {
    				if (inv[i] != null && inv[i] == itemStack) {
    					inv[i] = new ItemStack(Greece.bakingCover.itemID, 1, 0);
    				}
    			}
    		}
    	}
    }
    
    /**
     * This is called when the item is used, before the block is activated.
     * @param stack The Item Stack
     * @param player The Player that used the item
     * @param world The Current World
     * @param x Target X Position
     * @param y Target Y Position
     * @param z Target Z Position
     * @param side The side of the target hit
     * @return Return true to prevent any further processing.
     */
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if (world.getBlockId(x, y+1, z) == Block.fire.blockID) {
			stack.setItemDamage(0);
		}
        return false;
    }
}

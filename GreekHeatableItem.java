package mod.greece;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GreekHeatableItem extends Item {
	public static int heatedID;
	public GreekHeatableItem(int id, int heatedID) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("genericItem");
		this.heatedID = heatedID;
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
			ItemStack[] inv = player.inventory.mainInventory;
			for (int i = 0; i < inv.length; ++i) {
				if (inv[i] == stack) {
					inv[i] = new ItemStack(Greece.hotBakingCover, 1, 0);
				}
			}
		}
        return false;
    }

}

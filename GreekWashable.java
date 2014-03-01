package mod.greece;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GreekWashable extends Item {

	int dropID;
	
	public GreekWashable(int id, int dropID) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		this.dropID = dropID;
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
	  {
		  if (world.isRemote) {
			  return itemStack;
		  }
            MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);
            
            if (mop == null)
                return itemStack;
            if (mop.typeOfHit == EnumMovingObjectType.TILE) {
                //player.addChatMessage("Hit a tile");
            	int blockID = world.getBlockId(mop.blockX, mop.blockY, mop.blockZ);
            	if (blockID == Block.waterMoving.blockID || blockID == Block.waterStill.blockID) {
            		//world.setBlock(mop.blockX, mop.blockY, mop.blockZ, Greece.plasteredBlock.blockID, newMeta, 2);
            		/*System.out.println(blockID);
            		System.out.println(Greece.plasteredBlock.blockID);
            		System.out.println(world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));*/
            		world.playSoundAtEntity(player, "liquid.swim", 0.5f, 1);
            		player.swingItem();
    				player.inventory.consumeInventoryItem(itemStack.itemID);
    				player.inventory.addItemStackToInventory(new ItemStack(dropID, 1, 0));
            	}
                //player.addChatMessage("Something strange happened");
            }

            //System.out.println(mop.entityHit);
            
            return itemStack;
	    }
	}
}
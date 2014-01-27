package mod.greece;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class PlasterBucket extends Item {

	public PlasterBucket(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("plasterBucket");
		setTextureName("Greece:bucket_plaster");
		//number of uses:
		setMaxDamage(12);
	}

	/*@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase target) {
		//check to make sure we're modifying the server instance of the entity instead of the client instance,
		//to avoid sync issues
		if (!target.worldObj.isRemote) {
			if (target instanceof EntityZombie) {
				target.motionY = 2.5;
			} else {
				player.addChatMessage("This item only works on zombies.");
			}
			
		}
		return false;
	}*/
	
	@Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
	  {
		  if (world.isRemote) {
			  return itemStack;
		  }
            MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, false);
            if (mop == null)
                return itemStack;
            
            if (mop.typeOfHit == EnumMovingObjectType.TILE) {
                //player.addChatMessage("Hit a tile");
            	int blockID = world.getBlockId(mop.blockX, mop.blockY, mop.blockZ);
            	int newMeta = PlasteredBlock.getPlasterID(blockID);
            	if (newMeta != -1) {
            		world.setBlock(mop.blockX, mop.blockY, mop.blockZ, Greece.plasteredBlock.blockID, newMeta, 2);
            		/*System.out.println(blockID);
            		System.out.println(Greece.plasteredBlock.blockID);
            		System.out.println(world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));*/
            		if (!player.capabilities.isCreativeMode) {
            			//--itemStack.stackSize;
            			int old_damage = itemStack.getItemDamage();
            			if ((old_damage + 1) < itemStack.getMaxDamage()) {
            				itemStack.setItemDamage(old_damage + 1);
            			} else {
            				--itemStack.stackSize;
            				player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty));
            			}
            		}
            	}
                //player.addChatMessage("Something strange happened");
            }

            //System.out.println(mop.entityHit);
            
            return itemStack;
	    }
	}
}

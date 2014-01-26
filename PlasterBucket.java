package mod.greece;

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
            
            if (mop.typeOfHit == EnumMovingObjectType.TILE)
                player.addChatMessage("Hit a tile");
            	int blockID = world.getBlockId(mop.blockX, mop.blockY, mop.blockZ);
            	int replaceID = 0;
            	if (blockID == Block.planks.blockID) {
            		replaceID = Greece.plasteredWood.blockID;
            	} else if (blockID == Block.dirt.blockID) {
            		replaceID = Greece.plasteredDirt.blockID;
            	}
            	if ( replaceID != 0) {
            		world.setBlock(mop.blockX, mop.blockY, mop.blockZ, replaceID);
            		
            		world.markBlockForUpdate(mop.blockX, mop.blockY, mop.blockZ);
            		if (!player.capabilities.isCreativeMode) {
            			--itemStack.stackSize;
            		}
            	}
            else
                player.addChatMessage("Something strange happened");

            System.out.println(mop.entityHit);
            
            return itemStack;
	    }
	}
}

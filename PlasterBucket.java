package mod.greece;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class PlasterBucket extends Item {

	private EntityPlayer tempPlayer;
	private ItemStack tempItem;
	private int droppedTime=-1;
	
	public PlasterBucket(int id) {
		super(id);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("plasterBucket");
		setTextureName("Greece:bucket_plaster");
		//number of uses:
		setMaxDamage(20);
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
	
	/**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     *//*
	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (this.droppedTime != -1) {
			this.droppedTime--;
			if (this.droppedTime == 0) {
				this.droppedTime = -1;
				InventoryPlayer inv = this.tempPlayer.inventory;
				for (int i = 0; i < inv.mainInventory.length; ++i) {
					if (inv.mainInventory[i] == this.tempItem) {
						this.tempPlayer.dropPlayerItemWithRandomChoice(this.tempItem, false);
						this.tempPlayer.inventory.mainInventory[i]=null;
						System.out.println("Dropped Item");
						return;
					}
				}
			}
		}
	}
	
    *//**
     * Called when a player drops the item into the world,
     * returning false from this will prevent the item from
     * being removed from the players inventory and spawning
     * in the world
     *
     * @param player The player that dropped the item
     * @param item The item stack, before the item is removed.
     *//*
	@Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
		if (GreekKeyBind.keyPressed) {
			this.droppedTime=100;
			this.tempPlayer=player;
			this.tempItem=item;
			System.out.println("LOL NOPE");
	        return false;
		} else {
			return true;
		}
		
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

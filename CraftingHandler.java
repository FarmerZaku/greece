package mod.greece;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler {
	
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
			
			for(int i=0; i < craftMatrix.getSizeInventory(); i++)
			{        	
		    	if(craftMatrix.getStackInSlot(i) != null)
		    	{
		    		ItemStack j = craftMatrix.getStackInSlot(i);
		    		if(j.getItem() != null && (j.getItem() == Greece.chisel || j.getItem() == Greece.bakingCover) && j.getItemDamage()+10 < j.getMaxDamage())
		    		{
		    			int oldDamage = j.getItemDamage(); 
		    			ItemStack k;
		    			if (j.getItem() == Greece.chisel) {
		    				k = new ItemStack(Greece.chisel, 2, (j.getItemDamage() + 15));
		    			}
		    			else {
		    				k = new ItemStack(Greece.bakingCover, 2, (j.getItemDamage() + 10));
		    			}
		    			//k.setItemDamage(oldDamage + 10);
		    			craftMatrix.setInventorySlotContents(i, k);
		    		}
		    		else if(j.getItem() != null && (j.getItem() == Greece.chisel || j.getItem() == Greece.bakingCover) && j.getItemDamage()+15 >= j.getMaxDamage())
		    			player.playSound("random.break", 1, 1);
		    		else if (item.itemID == Greece.plasterBucket.itemID && j.getItem() != null & j.getItem() == Item.bucketWater) {
		    			craftMatrix.setInventorySlotContents(i, null);
		    		} else if ((item.itemID == Greece.basketGrain.itemID || item.itemID == Greece.amphoraGrain.itemID) 
		    				&& j.getItem() != null & j.getItem() == Item.wheat) {
		    			//Give 'em a straw item. If it doesn't fit in their inventory, drop it
		    			if (!player.inventory.addItemStackToInventory(new ItemStack(Greece.straw))) {
		    				player.dropPlayerItem(new ItemStack(Greece.straw));
		    			}
		    		} else if (item.itemID == Greece.dough.itemID && j.getItem() != null & j.getItem() == Greece.basketFlour) {
		    			craftMatrix.setInventorySlotContents(i, new ItemStack(Greece.basketEmpty, 2));
		    		} else if (item.itemID == Greece.dough.itemID && j.getItem() != null & j.getItem() == Item.bucketWater) {
		    			craftMatrix.setInventorySlotContents(i, new ItemStack(Item.bucketEmpty, 2));
		    		} else if (item.itemID == Greece.dough.itemID && j.getItem() != null & j.getItem() == Greece.amphoraFlour) {
		    			craftMatrix.setInventorySlotContents(i, new ItemStack(Greece.amphora, 2));
		    		}
		    	}  
			}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub
		
	}

}

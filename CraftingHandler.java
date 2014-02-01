package mod.greece;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
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
		    		if(j.getItem() != null && j.getItem() == Greece.chisel && j.getItemDamage()+10 < j.getMaxDamage())
		    		{
		    			int oldDamage = j.getItemDamage(); 
		    			ItemStack k;
		    			k = new ItemStack(Greece.chisel, 2, (j.getItemDamage() + 15));
		    			//k.setItemDamage(oldDamage + 10);
		    			craftMatrix.setInventorySlotContents(i, k);
		    		}
		    		else if(j.getItem() != null && j.getItem() == Greece.chisel && j.getItemDamage()+15 >= j.getMaxDamage()) {
		    			player.playSound("random.break", 1, 1);
		    			
		    		//Since the hot baking cover won't break, just get cold, it has its own section now
		    		} else if (j.getItem() != null && j.getItem() == Greece.hotBakingCover)
		    		{
		    			int oldDamage = j.getItemDamage(); 
		    			ItemStack k;
		    			k = new ItemStack(Greece.hotBakingCover, 1, (j.getItemDamage() + 30));
		    			
		    			//Put the hot baking cover in the first empty player inventory slot. This is because the onUpdate
		    			//function doesn't run while it is in the crafting grid, so it never cools. This means you can use
		    			//it for as much bread as you want without worrying about it cooling. So we put it in the inventory
		    			//to make it update between each crafting.
		    			int firstOpen = player.inventory.getFirstEmptyStack();
		    			if (firstOpen != -1) {
		    				player.inventory.setInventorySlotContents(firstOpen, k);
		    			} else {
		    				player.dropPlayerItem(k);
		    			}
		    		}
		    	}
			}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub
		
	}

}

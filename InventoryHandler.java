package mod.greece;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

/*import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class InventoryHandler extends TileEntity implements IInventory {

	private ItemStack[] inv;
	
	@Override
	public int getSizeInventory() {
        return inv.length;
}

	@Override
	public ItemStack getStackInSlot(int slot) {
        return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
                setInventorySlotContents(slot, null);
        }
        return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
        inv[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
                stack.stackSize = getInventoryStackLimit();
        }               
	}

	@Override
	public String getInvName() {
        return "greece.inventoryhandler";
	}

	@Override
	public boolean isInvNameLocalized() {
		//return false;
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 6000;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
        player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub	
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		//return false;
		return true;
	}

}*/

public class InventoryHandler extends InventoryPlayer {

	public InventoryHandler(EntityPlayer par1EntityPlayer) {
		super(par1EntityPlayer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 6000;
	}	
}

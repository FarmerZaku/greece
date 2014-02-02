package mod.greece;

import java.util.EnumSet;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
public class PlayerTickHandler implements ITickHandler
{
	private static boolean previouslyBlocking=false;
	private final EnumSet<TickType> ticksToGet;
	
	/*
	         * This Tick Handler will fire for whatever TickType's you construct and register it with.
	         */
	public PlayerTickHandler(EnumSet<TickType> ticksToGet)
	{
	         this.ticksToGet = ticksToGet;
	}
	/*
	         * I suggest putting all your tick Logic in EITHER of these, but staying in one
	         */
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	         playerTick((EntityPlayer)tickData[0]);
	}
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
	}
	@Override
	public EnumSet<TickType> ticks()
	{
	         return ticksToGet;
	}
	@Override
	public String getLabel()
	{
	         return "TutorialPlayerTick";
	}
	
	public static void playerTick(EntityPlayer player)
	{
		if (player.getHealth() <= 0) {
			return;
		}
	     if(GreekKeyBind.blockPressed && (player.isUsingItem() == false || player.getCurrentEquippedItem().itemID != Greece.shield.itemID))
	     {
	    	 ItemStack[] inv = player.inventory.mainInventory;
	    	 ItemStack shield = null;
	    	 if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == Greece.shield.itemID) {
    			 shield = player.getCurrentEquippedItem();
    			 //System.out.println("Already have shield equipped!");
	    	 }
	    	 if (shield == null) {
		    	 //check the player's hotbar for a shield
		    	 for (int i = 0; i < inv.length && i < 9; ++i) {
		    		 if (inv[i] != null && inv[i].itemID == Greece.shield.itemID) {
		    			 //System.out.println("In pos: " + i);
		    			 //player.inventory.currentItem = i;
		    			 if (inv[i].stackTagCompound == null) {
		    				 inv[i].stackTagCompound = new NBTTagCompound();
		    			 }
		    			 inv[i].stackTagCompound.setInteger("LastItem", player.inventory.currentItem);
		    			 player.inventory.setCurrentItem(inv[i].itemID, 0, false, false);
		    			 shield = inv[i];
		    			 break;
		    		 }
		    	 }
	    	 }
	    	 if (shield != null) {
	    		 //System.out.println(player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
	    		 player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setAttribute(0.8);
	    		 player.setItemInUse(shield, shield.getMaxItemUseDuration());
	    		 previouslyBlocking = true;
	    	 }
	     } else if (GreekKeyBind.blockPressed == false && previouslyBlocking && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == Greece.shield.itemID) {
    		 previouslyBlocking = false;
	    	 ItemStack shield = player.getCurrentEquippedItem();
    		 player.setItemInUse(null,0);
    		 //System.out.println("1");
    		 if (shield != null && shield.stackTagCompound.hasKey("LastItem")) {
    			// System.out.println(shield.stackTagCompound.getInteger("LastItem"));
	    		int lastItem = shield.stackTagCompound.getInteger("LastItem");
	    		if (player.inventory.mainInventory[lastItem] != null) {
	    			//System.out.println("3");
	    			player.inventory.setCurrentItem(player.inventory.mainInventory[lastItem].itemID, 0, false, false);
	    		}
    		 }
    		 //Ok, this will mess up in the future if the player somehow gets knockback resistance via other means
    		 player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setAttribute(0.0);
	     }
	}
}
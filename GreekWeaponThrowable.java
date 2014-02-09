package mod.greece;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GreekWeaponThrowable extends GreekSword {
	public static int throwChargeCapacity;
	public GreekWeaponThrowable(int par1, EnumToolMaterial toolMaterial, float knockBack, double reach, int minDamage, int maxDamage, int chargeCapacity) {
		super(par1, toolMaterial, knockBack, reach, minDamage, maxDamage, chargeCapacity);
		throwChargeCapacity = 3*chargeCapacity;
		this.maxStackSize = 4;
	}
	/*
    * Called when a player drops the item into the world,
    * returning false from this will prevent the item from
    * being removed from the players inventory and spawning
    * in the world
    *
    * @param player The player that dropped the item
    * @param item The item stack, before the item is removed.
    */
	@Override
   public boolean onDroppedByPlayer(ItemStack itemStack, EntityPlayer player)
   {
		if (itemStack.stackTagCompound == null) {
			itemStack.stackTagCompound = new NBTTagCompound();
		}
		itemStack.stackTagCompound.setInteger("dropTime", 1);
		return false;
   }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int chargeVal)
    {
		super.onPlayerStoppedUsing(itemStack, world, player, chargeVal);
		//If the item damage > 0 then we hit something
		if (itemStack.getItemDamage() > 0) {
			//Remove the damage
			itemStack.setItemDamage(0);
			//On a random chance, break one of the items
			if (world.rand.nextInt(maxDamage) < 1) {
				player.getCurrentEquippedItem().stackSize--;
				if (player.getCurrentEquippedItem().stackSize <= 0) {
					player.destroyCurrentEquippedItem();
				}
				player.playSound("random.break", 1.0F, 1.0F);
			}
		}
    }
	
	/**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
	@Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
		if (itemStack.stackTagCompound == null || itemStack.stackTagCompound.getInteger("dropTime") == 0) {
			return;
		}
		
		boolean dropPressed = false;
		for (int i = 0; i < KeyBinding.keybindArray.size(); ++i) {
			if (((KeyBinding)KeyBinding.keybindArray.get(i)).keyDescription == "key.drop") {
				dropPressed = ((KeyBinding)KeyBinding.keybindArray.get(i)).pressed;
				break;
			}
		}
		if (dropPressed) {
			if (itemStack.stackTagCompound != null) {
				int dropTime = itemStack.stackTagCompound.getInteger("dropTime") + 1;
				itemStack.stackTagCompound.setInteger("dropTime", dropTime);
			}
			return;
		} else if (itemStack.stackTagCompound.getInteger("dropTime") > 0) {
			int dropTime = 0;
			if (itemStack.stackTagCompound != null) {
				dropTime = itemStack.stackTagCompound.getInteger("dropTime");
			}
			itemStack.stackTagCompound.setInteger("dropTime", 0);
			if (dropTime <= 5) {
				((EntityPlayer)entity).dropPlayerItemWithRandomChoice(itemStack, false);
			} else {
				//System.out.println("Charge Capacity: " + throwChargeCapacity);
				//System.out.println("Charge: " + dropTime);
				float speed = ((float)dropTime / (float)throwChargeCapacity)*1.5F;
				if (speed > 1.5F) {
					speed = 1.5F;
				}
				float damage = speed * (float)this.maxDamage;
				System.out.println("Gonna damage " + damage);
				EntityPlayer player = (EntityPlayer)entity;
				ItemStack toDrop = new ItemStack(itemStack.getItem());
				boolean breakOnHit = false;
				//This is presuming more damaging weapons break less frequently
				if (world.rand.nextInt(maxDamage) < 1) {
					breakOnHit = true;
				}
				GreekEntityJavelin entityjavelin = new GreekEntityJavelin(world, player, speed, toDrop, breakOnHit);
				entityjavelin.setDamage(damage);
				entityjavelin.setCollideable(false);
				entityjavelin.onUpdate();
				entityjavelin.setCollideable(true);
				player.swingItem();
				//entityjavelin.moveFlying((float)player.getLookVec().xCoord, (float)player.getLookVec().yCoord, (float)player.getLookVec().zCoord);
				if (!world.isRemote)
	            {
	                world.spawnEntityInWorld(entityjavelin);
	            }
			}
			//System.out.println("Dropped " + dropTime);
			((EntityPlayer)entity).getCurrentEquippedItem().stackSize--;
			if (((EntityPlayer)entity).getCurrentEquippedItem().stackSize <= 0) {
				((EntityPlayer)entity).destroyCurrentEquippedItem();
			}
		}
	}
}

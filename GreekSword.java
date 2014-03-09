package mod.greece;

import java.util.UUID;
import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekSword extends ItemSword {
	    @SideOnly(Side.CLIENT)
	    protected Icon[] iconArray;
	    protected int minDamage, maxDamage;
		protected int fullCharge;
		protected double reach;
		protected float knockBack;
	    
	    /**
	     * knockBack: knockback scaling. Should be like 0.01-0.1 or so.
	     * reach: block range of weapon
	     * charge capacity: how long it takes to fully charge. Should be like 20-40.
	     */
	    public GreekSword(int par1, EnumToolMaterial toolMaterial, float knockBack, double reach, int minDamage, int maxDamage, int chargeCapacity)
	    {
	        super(par1, toolMaterial);
	        this.maxStackSize = 1;
	        this.setMaxDamage(384);
	        this.setCreativeTab(CreativeTabs.tabCombat);
	    	this.knockBack = knockBack;
	    	this.reach = reach;
	    	this.minDamage = minDamage;
	    	this.maxDamage = maxDamage;
	    	this.fullCharge = chargeCapacity;
	    }
	    
	    /**
	     * Called when the player Left Clicks (attacks) an entity.
	     * Processed before damage is done, if return value is true further processing is canceled
	     * and the entity is not attacked.
	     *
	     * @param stack The Item being used
	     * @param player The player that is attacking
	     * @param entity The entity being attacked
	     * @return True to cancel the rest of the interaction.
	     */
	    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	    {
	    	this.onPlayerStoppedUsing(stack, player.worldObj, player, this.getMaxItemUseDuration(stack));
	        return true;
	    }
	    
	    /**
	     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	     */
	    @Override
	    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int chargeVal)
	    {	
	    	GreekEventHandler.ignoreFOVChanges=2;
			//After the player stops using the item, remove our temporary speed boost. See the onItemRickClick for more
	    	//explanation...
	    	
	    	//This should already have a stackTagCompound, but may as well check...
	    	if (itemStack.stackTagCompound == null) {
	        	itemStack.stackTagCompound = new NBTTagCompound();
	        }
	    	
	    	//Look for the attribute ID (speedID) and if it doesn't exist get a new random one.
	        String uu = itemStack.stackTagCompound.getString("SpeedUUID");
			UUID speedID;
			if(uu.equals("")) {
				speedID = UUID.randomUUID();
				itemStack.stackTagCompound.setString("SpeedUUID", speedID.toString());
			}
			else {
				speedID = UUID.fromString(uu);
			}
	        
			//Create a copy of the attribute modifier, see if it had already been applied to the player, and if so remove it 
	        AttributeInstance atinst = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
	        AttributeModifier mod = new AttributeModifier(speedID,"SpeedBoostComponent",3f,2);
	        if(atinst.getModifier(speedID) != null)
			{
	        	atinst.removeModifier(mod);
			}    	
	        
	        //Make sure the player is dead (i.e. They haven't died while charging up a swing)
	    	if (player.isDead) {
	    		return;
	    	}
	    	
	    	player.moveForward=1;
	    	player.moveStrafing=1;
	    	
	        int maxCharge = this.getMaxItemUseDuration(itemStack);
	        int chargeTime = this.getMaxItemUseDuration(itemStack) - chargeVal;
	        //System.out.println(chargeTime);
	        //System.out.println(fullCharge);
	        if (chargeTime > fullCharge) {
	        	chargeTime = fullCharge;
	        }
	        int damageDiff = maxDamage - minDamage;
	        double damage = ((double)chargeTime / (double)fullCharge) * (double)damageDiff + (double)minDamage;
	        
	        player.swingItem();
	        System.out.println("Gonna damage: " + damage);
	        
	        Minecraft.getMinecraft().entityRenderer.getMouseOver(0);
	        MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
	        if (mop != null && mop.typeOfHit == EnumMovingObjectType.ENTITY) {
	        	EntityLiving target = (EntityLiving) world.getEntityByID(mop.entityHit.entityId);//(EntityLiving) mop.entityHit;
	        	if (player.getDistanceSq(target.posX, target.posY, target.posZ) < reach * reach) {
    	        	target.attackEntityFrom(DamageSource.causePlayerDamage(player), (int)damage);
    	        	Vec3 to_target = player.getPosition(0);
            		to_target.xCoord -= target.posX;
            		to_target.yCoord -= target.posY;
            		to_target.zCoord -= target.posZ;
    	        	double scale = damage*-1*knockBack;
    				target.setVelocity(to_target.xCoord*scale, Math.max(to_target.yCoord*scale, 0.1), to_target.zCoord*scale);
    				itemStack.damageItem(1, player);
	        	}
	        }
        	return;
            }
	    
	    @Override
	    public ItemStack onEaten(ItemStack itemStack, World par2World, EntityPlayer entityPlayer)
	    {
	        return itemStack;
	    }

	    /**
	     * How long it takes to use or consume an item
	     */
	    @Override
	    public int getMaxItemUseDuration(ItemStack itemStack)
	    {
	        return 72000;
	    }

	    /**
	     * returns the action that specifies what animation to play when the items is being used
	     */
	    @Override
	    public EnumAction getItemUseAction(ItemStack itemStack)
	    {
	        return EnumAction.bow;
	    }
	    
	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	     */
	    @Override
	    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	    {
	    	//Queue up a forge event...
	        ArrowNockEvent event = new ArrowNockEvent(player, itemStack);
	        MinecraftForge.EVENT_BUS.post(event);
	        if (event.isCanceled())
	        {
	            return event.result;
	        }

	        
	        player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
	        
	        return itemStack;
	    }
	    
		@Override
		public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
			
		}
	    /**
	     * Called each tick while using an item.
	     * @param stack The Item being used
	     * @param player The Player using the item
	     * @param count The amount of time in tick the item has been used for continuously
	     */
		
		@Override
	    public void onUsingItemTick(ItemStack itemStack, EntityPlayer player, int count)
	    {
			//System.out.println("Time: " + count);
			//To prevent the massive slowdown that accompanies using an item, I boost the player's speed while they are using the
	        //weapon. This is not as simple as increasing their speed, rather you have to create and apply an attribute modifier.
	        //Thanks to Draco18s for the example code!
	        
	        //If the item doesn't have a tag compound already, make one
	        if (itemStack.stackTagCompound == null) {
	        	itemStack.stackTagCompound = new NBTTagCompound();
	        }
	        
	        //Try to grab the attribute modifier ID (speedID) from the tag compound. If that field doesn't yet exist, create it
	        //by getting a new random attribute modifier ID.
	        String uu = itemStack.stackTagCompound.getString("SpeedUUID");
			UUID speedID;
			if(uu.equals("")) {
				speedID = UUID.randomUUID();
				itemStack.stackTagCompound.setString("SpeedUUID", speedID.toString());
			}
			else {
				speedID = UUID.fromString(uu);
			}
	        
			//Get the attribute modifiers applied to the player. If they already have the attribute modifier that we want to
			//create, then don't bother applying the attribute modifier.
	        AttributeInstance atinst = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
	        //The third parameter is a float that is a speed multiplier I believe
	        AttributeModifier mod = new AttributeModifier(speedID,"SpeedBoostComponent",3f,2);
	        if(atinst.getModifier(speedID) == null)
			{
	        	//itemStack.stackTagCompound.setBoolean("Waiting", true);
	        	atinst.applyModifier(mod);
			}
	        
	        //The following was replaced using a forge hook in GreekEventHandler
	        /*ObfuscationReflectionHelper.setPrivateValue(InventoryPlayer.class, player.inventory, new Callable<Integer>() {
	        	public Integer call() {
	        		return 128;
	        	}
	        }, "getInventoryStackLimit", "func_70297_j");*/
	        //ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, 1f, "fovModifierHand", "field_78507_R");
	    }
	}

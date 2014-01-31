package mod.greece;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekSword extends ItemSword {
	    //public static final String[] bowPullIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2"};
	    @SideOnly(Side.CLIENT)
	    private Icon[] iconArray;
	    private int minDamage, maxDamage, fullCharge;
	    private double reach;
	    private float knockBack;
	    
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

	    public void setWeaponStats(float knockBack, double reach, int minDamage, int maxDamage, int chargeCapacity) {
	    	this.knockBack = knockBack;
	    	this.reach = reach;
	    	this.minDamage = minDamage;
	    	this.maxDamage = maxDamage;
	    	this.fullCharge = chargeCapacity;
	    }
	    /**
	     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	     */
	    @Override
	    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int chargeVal)
	    {
	    	//System.out.println("Released!");
	        int maxCharge = this.getMaxItemUseDuration(itemStack);
	        int chargeTime = this.getMaxItemUseDuration(itemStack) - chargeVal;
	        //System.out.println(chargeTime);
	        //System.out.println(fullCharge);
	        if (chargeTime > fullCharge) {
	        	chargeTime = fullCharge;
	        }
	        int damageDiff = maxDamage - minDamage;
	        double damage = ((double)chargeTime / (double)fullCharge) * (double)damageDiff + (double)minDamage;
	        
	        
            if (!world.isRemote)
            {
            	double futureX = player.posX + player.getLookVec().xCoord * this.reach;
            	double futureY = player.posY + player.getLookVec().yCoord * this.reach;
            	double futureZ = player.posZ + player.getLookVec().zCoord * this.reach;
            	double threshold = 0.1;
            	double minX = Math.min(player.posX,  futureX)-threshold;
            	double minY = Math.min(player.posY, futureY)-threshold;
            	double minZ = Math.min(player.posZ, futureZ)-threshold;
            	double maxX = Math.max(player.posX,  futureX)+threshold;
            	double maxY = Math.max(player.posY, futureY)+threshold;
            	double maxZ = Math.max(player.posZ, futureZ)+threshold;
            	AxisAlignedBB hitbox = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
            	List entities_hit = world.getEntitiesWithinAABB(net.minecraft.entity.EntityLiving.class, hitbox);
            	
            	for (int i = 0; i < entities_hit.size(); ++i) {
            		//System.out.println("WHEEEE");
            		//System.out.println(entities_hit.get(i).toString());
            		System.out.println("Gonna damage: " + damage);
            		EntityLiving target = ((EntityLiving) entities_hit.get(i));
            		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, false);
            		if (mop == null || player.getDistanceSq(target.posX, target.posY, target.posZ) < player.getDistanceSq(mop.blockX, mop.blockY, mop.blockZ)) {
                		Vec3 to_target = player.getPosition(0);
                		to_target.xCoord -= target.posX;
                		to_target.yCoord -= target.posY;
                		to_target.zCoord -= target.posZ;
                		to_target = to_target.normalize();
                		Vec3 facing = player.getLookVec().normalize();
            			Vec3 cross = facing.crossProduct(to_target).normalize();
            			double dot = facing.dotProduct(to_target);
            			double crossMag = Math.sqrt(cross.xCoord*cross.xCoord + cross.yCoord*cross.yCoord + cross.zCoord*cross.zCoord);
            			double angle = Math.atan2(crossMag, dot);
            			if (Math.abs(angle-2.35) < 0.11) {
            				target.attackEntityFrom(DamageSource.causePlayerDamage(player), (int)damage);
            				//target.knockBack(target, 0, 0, 0.);
            				double scale = damage*-1*knockBack;
            				target.setVelocity(to_target.xCoord*scale, Math.max(to_target.yCoord*scale, 0.1), to_target.zCoord*scale);
            			} else {
            				System.out.println("Angle too bad: " + angle);
            			}
            			//System.out.println(angle);
            		} else {
            			//System.out.println(dist);
            			////System.out.println("Target Pos: " + target.posX + ", " + target.posY + ", " + target.posZ);
            			//System.out.println("Mop Pos: " + mop.blockX + ", " + mop.blockY + ", " + mop.blockZ);
            			System.out.println("Something in way: " + mop.typeOfHit);
            		}
            	}
            	return;
            }
	    }
	    @Override
	    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	    {
	        return par1ItemStack;
	    }

	    /**
	     * How long it takes to use or consume an item
	     */
	    @Override
	    public int getMaxItemUseDuration(ItemStack par1ItemStack)
	    {
	        return 72000;
	    }

	    /**
	     * returns the action that specifies what animation to play when the items is being used
	     */
	    @Override
	    public EnumAction getItemUseAction(ItemStack par1ItemStack)
	    {
	        return EnumAction.bow;
	    }

	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	     */
	    @Override
	    public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player)
	    {
	        ArrowNockEvent event = new ArrowNockEvent(player, par1ItemStack);
	        MinecraftForge.EVENT_BUS.post(event);
	        if (event.isCanceled())
	        {
	            return event.result;
	        }

	        player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

	        return par1ItemStack;
	    }
	}

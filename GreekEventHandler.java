package mod.greece;

import mod.greece.mobs.GreekArcher;
import mod.greece.mobs.GreekHuman;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class GreekEventHandler {
    @ForgeSubscribe
    public void onEntityLivingSpawn(LivingSpawnEvent event) {
        if (!event.world.isRemote) {
        	//Replace every spawning zombie with a bandit
            if (event.entityLiving instanceof EntityZombie) {
                    EntityZombie oldZom = (EntityZombie)event.entityLiving;
                    GreekHuman newHuman = new GreekHuman(event.world);
                    newHuman.setLocationAndAngles(oldZom.posX, oldZom.posY, oldZom.posZ, oldZom.rotationYaw, oldZom.rotationPitch);
                    if (newHuman.getCanSpawnHere()) {
                    	event.world.spawnEntityInWorld(newHuman);
                    }
                    oldZom.setDead();
            //Replace every spawning Skeleton with an archer bandit
            } else if (event.entityLiving instanceof EntitySkeleton) {
                EntitySkeleton oldSkele = (EntitySkeleton)event.entityLiving;
                GreekArcher newHuman = new GreekArcher(event.world);
                newHuman.setLocationAndAngles(oldSkele.posX, oldSkele.posY, oldSkele.posZ, oldSkele.rotationYaw, oldSkele.rotationPitch);
                if (newHuman.getCanSpawnHere()) {
                	event.world.spawnEntityInWorld(newHuman);
                }
                oldSkele.setDead();
            //Replace every spawning creeper with an archer bandit with flaming arrows
            } else if (event.entityLiving instanceof EntityCreeper) {
                EntityCreeper oldCreep = (EntityCreeper)event.entityLiving;
                GreekArcher newHuman = new GreekArcher(event.world);
                newHuman.setBanditType(1);
                newHuman.setLocationAndAngles(oldCreep.posX, oldCreep.posY, oldCreep.posZ, oldCreep.rotationYaw, oldCreep.rotationPitch);
                if (newHuman.getCanSpawnHere()) {
                	event.world.spawnEntityInWorld(newHuman);
                }
                oldCreep.setDead();
            }
        }
    }
    @ForgeSubscribe
    public void onPlayerInteract(AttackEntityEvent event) {
    	World world = MinecraftServer.getServer().worldServerForDimension(event.entityPlayer.dimension);
    	ItemStack cur_item = event.entityPlayer.getCurrentEquippedItem();
    	if (cur_item != null && cur_item.getItem() instanceof GreekSword) {
    		if (!event.entity.worldObj.isRemote) {
    			cur_item.getItem().onPlayerStoppedUsing(cur_item, world, event.entityPlayer, cur_item.getItem().getMaxItemUseDuration(cur_item));
    		}    		
    		event.setCanceled(true);
    	}
    }
    
    @ForgeSubscribe
    public void onEntityAttacked(LivingAttackEvent event) {
    	//System.out.println("Entity Attacked.");
    	World world = event.entity.worldObj;
    	//World clientWorld = Minecraft.getMinecraft().theWorld;
    	//System.out.println("Entity Attacked2.");
    	if (world != null && !world.isRemote) {
    		//System.out.println("Entity Attacked3.");
	    	if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer && event.entityLiving.isDead == false) {
	    		//System.out.println("Player being attacked");
	    		EntityPlayer player = (EntityPlayer)event.entityLiving;
	    		ItemStack curItem = player.getItemInUse();
	    		if (player.isUsingItem() && curItem.itemID == Greece.shield.itemID) {
	    			if ((float)player.hurtResistantTime > (float)player.maxHurtResistantTime / 2.0F)
	                {
	                    event.setCanceled(true);
	                    event.setResult(Result.DENY);
	                } else {
	                	//Reduce the reduction amount by half, because every time the player is attacked this
	                	//event happens TWICE despite only doing damage once.
		    			float reductionAmt = ((GreekShield)curItem.getItem()).getDamageReduction()*0.5f;
		    			//event.entityLiving.heal(event.ammount*0.5f*reductionAmt);
		    			float toHeal = event.entityLiving.getHealth()+event.ammount*0.5f*reductionAmt;
		    			//System.out.println("Setting health to " + toHeal + " due to an attack that does " + event.ammount + " and a shield that blocks " + reductionAmt + " cur health: " + player.getHealth());
		    			event.entityLiving.getDataWatcher().updateObject(6, toHeal);
		    			//event.entityLiving.attackEntityFrom(par1DamageSource, par2)
		    			//event.entityLiving.hurtResistantTime = 1; 
		    			curItem.damageItem((int)(event.ammount*reductionAmt), event.entityLiving);
//		    			if (curItem.getItemDamage() >= curItem.getMaxDamage()) {
//		    				player.destroyCurrentEquippedItem();
//		    				//player.inventory.consumeInventoryItem(Greece.shield.itemID);
//		    				player.playSound("mob.zombie.woodbreak", 1, 1);
//		    			} else {
//		    				player.playSound("mob.zombie.wood1", 1, 1);
//		    			}
	                }
	    		}
	    	}
    	}
    }
}
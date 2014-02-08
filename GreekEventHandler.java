package mod.greece;

import mod.greece.mobs.GreekArcher;
import mod.greece.mobs.GreekHuman;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class GreekEventHandler {
	public static int ignoreFOVChanges = 0;
	
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
            } else if (event.entityLiving instanceof EntityVillager) {
            	EntityVillager oldVill = (EntityVillager)event.entityLiving;
                GreekVillager newVillager = new GreekVillager(event.world);
                newVillager.setLocationAndAngles(oldVill.posX, oldVill.posY, oldVill.posZ, oldVill.rotationYaw, oldVill.rotationPitch);
               // if (newVillager.getCanSpawnHere()) {
                	event.world.spawnEntityInWorld(newVillager);
                //}
                oldVill.setDead();
            }
        }
    }
    
    //The following function is registered as an event handler for LivingAttackEvents. Whenever a living entity gets attacked,
    //our function is called. Twice if the entity is a player. Because of some weird shit. The point of our function is to
    //detect that the player is about to take damage and heal them up prior to that an amount equal to the damage dealt, or less
    //if the shield blocks less. This'll simulate the shield taking the hit (albeit poorly). The player's armor will still get
    //damaged though, which is not ideal. I should note that this function is only called on the server side.
    @ForgeSubscribe
    public void onEntityAttacked(LivingAttackEvent event) {
    	World world = event.entity.worldObj;
    	
    	//Check to see that the entity being attacked exists, is a player, and that player is alive
    	if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer && event.entityLiving.isDead == false) {
    		EntityPlayer player = (EntityPlayer)event.entityLiving;
    		ItemStack curItem = player.getItemInUse();
    		
    		//Check to see if they're using our mod's shield to block
    		if (player.isUsingItem() && curItem.itemID == Greece.shield.itemID) {
            	//Reduce the reduction amount by half, because every time the player is attacked this
            	//event happens TWICE despite only doing damage once.
    			float reductionAmt = ((GreekShield)curItem.getItem()).getDamageReduction()*0.5f;
    			
    			//calculate the amount of damage that will be dealt after applying armor
                int i = 25 - player.getTotalArmorValue();
                float f1 = event.ammount * (float)i;
                float modifiedDamage = f1 / 25.0F;
                
                //Calculate the amount to heal the player. The *0.6 part is due to how blocking works. Normally blocking
                //prevents a bit less than half damage, and since Minecraft is going to apply that blocking modifier, we'll
                //need to account for it here.
    			float newHealth = event.entityLiving.getHealth()+modifiedDamage*0.6f*reductionAmt;
    			//System.out.println("Setting health to " + toHeal + " due to an attack that does " + event.ammount + " and a shield that blocks " + reductionAmt + " cur health: " + player.getHealth());
    			
    			//Set the player's health to the new value
    			event.entityLiving.getDataWatcher().updateObject(6, newHealth);
    			
    			//Check to see if they're using an axe, and if so damage the shield doubly. Otherwise, damage the shield an
    			//amount equal to the damage "blocked".
    			EntityLiving attacker = (EntityLiving) event.source.getEntity();
				if (attacker != null && attacker.getHeldItem() != null && attacker.getHeldItem().getItem() instanceof ItemAxe) {
					curItem.setItemDamage(curItem.getItemDamage() + (int)(modifiedDamage*modifiedDamage*1.2f*reductionAmt));
				} else {
					curItem.setItemDamage(curItem.getItemDamage() + (int)(modifiedDamage*modifiedDamage*0.6f*reductionAmt));
				}
    		}
    	}
    }
    
    //Ignore all FOV changes while the player is using a shield (because that sets their speed, which gives you tunnel vision),
    //and for two updates afterwards (apparently you need to do that? Otherwise there's a flicker when you use the sword briefly)
    @ForgeSubscribe
    public void onFOVChange(FOVUpdateEvent event) {
    	if (event.entity.isUsingItem() && event.entity.getItemInUse().getItem() instanceof GreekSword) {
    		event.newfov = 1.0f;
    	} else if (ignoreFOVChanges > 0) {
    		ignoreFOVChanges--;
    		event.newfov = 1.0f;
    	}
    }
}
package mod.greece;

import mod.greece.mobs.GreekArcher;
import mod.greece.mobs.GreekHuman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class EventHandlingClass {
    @ForgeSubscribe
    public void onEntityLivingSpawn(LivingSpawnEvent event) {
            if (!event.world.isRemote) {
                    if (event.entityLiving instanceof EntityZombie) {
                            EntityZombie oldZom = (EntityZombie)event.entityLiving;
                            //if (oldZom.getCanSpawnHere()) {
                                    GreekHuman newHuman = new GreekHuman(event.world);
                                    newHuman.setLocationAndAngles(oldZom.posX, oldZom.posY, oldZom.posZ, oldZom.rotationYaw, oldZom.rotationPitch);
                                    if (newHuman.getCanSpawnHere()) {
                                    	event.world.spawnEntityInWorld(newHuman);
                                    }
                                    oldZom.setDead();
                            //}
                    } else if (event.entityLiving instanceof EntitySkeleton) {
                        EntitySkeleton oldSkele = (EntitySkeleton)event.entityLiving;
                        //if (oldZom.getCanSpawnHere()) {
                                GreekArcher newHuman = new GreekArcher(event.world);
                                newHuman.setLocationAndAngles(oldSkele.posX, oldSkele.posY, oldSkele.posZ, oldSkele.rotationYaw, oldSkele.rotationPitch);
                                if (newHuman.getCanSpawnHere()) {
                                	event.world.spawnEntityInWorld(newHuman);
                                }
                                oldSkele.setDead();
                        //}
                    }
            }
           
    }

}
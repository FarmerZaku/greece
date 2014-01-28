package mod.greece;

import mod.greece.mobs.GreekHuman;
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
                                    event.world.spawnEntityInWorld(newHuman);
                                    oldZom.setDead();
                            //}
                    }
            }
           
    }

}
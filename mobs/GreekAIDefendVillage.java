package mod.greece.mobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;

public class GreekAIDefendVillage extends EntityAITarget {
	   GreekGuard guard;

	    /**
	     * The aggressor of the iron golem's village which is now the golem's attack target.
	     */
	    EntityLivingBase villageAgressorTarget;

	    public GreekAIDefendVillage(GreekGuard guard)
	    {
	        super(guard, false, true);
	        this.guard = guard;
	        this.setMutexBits(1);
	    }

	    /**
	     * Returns whether the EntityAIBase should begin execution.
	     */
	    public boolean shouldExecute()
	    {
	        Village village = this.guard.getVillage();

	        if (village == null)
	        {
	            return false;
	        }
	        else
	        {
	            this.villageAgressorTarget = village.findNearestVillageAggressor(this.guard);

	            if (!this.isSuitableTarget(this.villageAgressorTarget, false))
	            {
	                if (this.taskOwner.getRNG().nextInt(20) == 0)
	                {
	                    this.villageAgressorTarget = village.func_82685_c(this.guard);
	                    return this.isSuitableTarget(this.villageAgressorTarget, false);
	                }
	                else
	                {
	                    return false;
	                }
	            }
	            else
	            {
	                return true;
	            }
	        }
	    }

	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	        this.guard.setAttackTarget(this.villageAgressorTarget);
	        super.startExecuting();
	    }
}

package mod.greece.mobs;

import mod.greece.Greece;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekGuard extends EntityGolem {
	
	  /** deincrements, and a distance-to-home check is done at 0 */
    private int homeCheckTimer;
    Village villageObj;
    private int attackTimer;
    private int holdRoseTick;

    
	public GreekGuard(World world) {
		super(world);
		
		
		this.setSize(0.6F, 1.8F);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3);
		//this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(1);
		this.addRandomArmor();
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
        //this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 1.0D, 32.0F));
        this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        //this.tasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new GreekAIDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, true, IMob.mobSelector));
	}

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        if (--this.homeCheckTimer <= 0)
        {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);

            if (this.villageObj == null)
            {
                this.detachHome();
            }
            else
            {
                ChunkCoordinates chunkcoordinates = this.villageObj.getCenter();
                this.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6F));
            }
        }

        super.updateAITick();
    }

    /**
     * Decrements the entity's air supply when underwater
     */
    protected int decreaseAirSupply(int par1)
    {
        return par1;
    }

    protected void collideWithEntity(Entity par1Entity)
    {
        if (par1Entity instanceof IMob && this.getRNG().nextInt(20) == 0)
        {
            this.setAttackTarget((EntityLivingBase)par1Entity);
        }

        super.collideWithEntity(par1Entity);
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean canAttackClass(Class par1Class)
    {
        return EntityPlayer.class.isAssignableFrom(par1Class) ? false : super.canAttackClass(par1Class);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 4)
        {
            this.attackTimer = 10;
        }
        else if (par1 == 11)
        {
            this.holdRoseTick = 400;
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    public Village getVillage()
    {
        return this.villageObj;
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer()
    {
        return this.attackTimer;
    }

    protected int getDropItemId()
    {
        return 0;
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        this.dropItem(Greece.shieldWood.itemID, 1);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        if (this.attackingPlayer != null && this.villageObj != null)
        {
            this.villageObj.setReputationForPlayer(this.attackingPlayer.getCommandSenderName(), -5);
        }

        super.onDeath(par1DamageSource);
    }
	
	@Override
	public void onLivingUpdate()
    {
		super.onLivingUpdate();
		//System.out.println(this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
    }
	
	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
    {
		this.swingItem();
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;
        System.out.println("Attacking with power " + f);

        if (par1Entity instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)par1Entity);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)par1Entity);
        }

        boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                par1Entity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                par1Entity.setFire(j * 4);
            }

            if (par1Entity instanceof EntityLivingBase)
            {
                EnchantmentThorns.func_92096_a(this, (EntityLivingBase)par1Entity, this.rand);
            }
        }

        return flag;
    }
	
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(10);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3);
        this.getAttributeMap().func_111150_b(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(1);
    }
	
	/**
     * Makes entity wear random armor based on difficulty
     */
    @Override
    protected void addRandomArmor()
    {
    	//if (this.rand.nextFloat() < 0.15F * this.worldObj.getLocationTensionFactor(this.posX, this.posY, this.posZ))
    	//make the material leather
        int materialIndex = 0; // 0 is leather in this context
        float difficulty_threshold = this.worldObj.difficultySetting == 3 ? 0.1F : 0.25F;

        for (int j = 1; j <= 4; ++j)
        {
            if (this.rand.nextFloat() < 0.1F)
            {
            	//make the material iron (3)
                materialIndex = 3;
            }
        	//get the current item in the inventory slot
            ItemStack itemstack = this.func_130225_q(j-1);

            //Stop giving them armor at a random point determined by difficulty
            if (j < 3 && this.rand.nextFloat() < difficulty_threshold)
            {
                break;
            }
            
            //if the current slot is empty...
            if (itemstack == null)
            {
                Item item = getArmorItemForSlot(j, materialIndex);

                if (item != null)
                {
                    this.setCurrentItemOrArmor(j, new ItemStack(item));
                }
            }
        }

        //if (this.rand.nextFloat() < (this.worldObj.difficultySetting == 3 ? 0.05F : 0.01F))
        int i = this.rand.nextInt(2);
        
        switch (i) {
        case 0:
        	this.setCurrentItemOrArmor(0, new ItemStack(Greece.spear));
        	break;
        case 1:
        	this.setCurrentItemOrArmor(0, new ItemStack(Greece.bronzeSword));
        	break;
        }
    }
}

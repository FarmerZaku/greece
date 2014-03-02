package mod.greece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class GreekVillager extends EntityVillager {
	/** This villager's current customer. */
    private EntityPlayer buyingPlayer;
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    Village villageObj;
	private MerchantRecipeList buyingList;
	private int timeUntilReset;
    public static final Map villagerStockList = new HashMap();
    public static final Map blacksmithSellingList = new HashMap();
    private boolean needsInitilization;
    private int wealth;
    private String lastBuyingPlayer;
    private boolean field_82190_bM;
    private float field_82191_bN;
	
	public GreekVillager(World par1World) {
		super(par1World);
	}
	
	public GreekVillager(World par1World, int professionID) {
		super(par1World);
        this.setProfession(professionID);
        
	}
	
	@Override
	public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer)
    {
		//buyingList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4), new ItemStack(Item.wheat, 1)));
		if (this.buyingList == null)
		{
            this.addDefaultEquipmentAndRecipies(5);
        }
        return this.buyingList;
    }

    /**
     * based on the villagers profession add items, equipment, and recipies adds par1 random items to the list of things
     * that the villager wants to buy. (at most 1 of each wanted type is added)
     */
    private void addDefaultEquipmentAndRecipies(int par1)
    {
    	MerchantRecipeList merchantrecipelist;
        merchantrecipelist = new MerchantRecipeList();
        VillagerRegistry.manageVillagerTrades(merchantrecipelist, this, this.getProfession(), this.rand);
        
        //addMerchantItem(merchantrecipelist, Item.wheat.itemID, this.rand, 0.9F);
        //addBlacksmithItem(merchantrecipelist, Item.bread.itemID, this.rand, 0.9F);
        
        
        switch(this.getProfession()) {
		case 0: // FARMER
			// villager selling
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 6), new ItemStack(Item.wheat, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 1), new ItemStack(Greece.olives, 1)));
			// villager buying
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.amphora, 1), new ItemStack(Greece.drachma, 1))); // 3 obols
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.spear, 1), new ItemStack(Greece.drachma, 4)));
			break;
		case 1: // MERCHANT
			// villager selling
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 1),
				new ItemStack(Greece.amphora, 1))); // 3 obols
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 1),
				new ItemStack(Greece.amphoraGrain, 6))); // price?
			// villager buying
			break;
		case 2: // TOOL DEALER
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 5), new ItemStack(Greece.chisel, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4), new ItemStack(Greece.spear, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 5), new ItemStack(Greece.shieldBronze, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 7), new ItemStack(Item.bow, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 8), new ItemStack(Item.arrow, 20)));
			break;
		case 3: // METAL DEALER
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 3), // 2.3
					new ItemStack(Greece.copperIngot, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4),
					new ItemStack(Greece.bronzeIngot, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 1), // 1.5
					new ItemStack(Greece.tinCrushed, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 1), // 1.5
					new ItemStack(Greece.copperCrushed, 2)));
			break;
		case 4: // FISH DEALER
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4),
				new ItemStack(Item.fishCooked, 1))); // to buy
			break;
		case 5: // LEATHER DEALER
			// villager selling
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 16),
				new ItemStack(Item.saddle, 1)));
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4),
					new ItemStack(Item.leather, 1)));
			break;
		case 6: // CARPENTER
			merchantrecipelist.add(new MerchantRecipe(new ItemStack(Greece.drachma, 20),
				new ItemStack(Item.bed, 1))); // to buy
			break;	
		default:
			break;
		}
        
        
        //Collections.shuffle(merchantrecipelist);
        
        if (this.buyingList == null)
        {
            this.buyingList = new MerchantRecipeList();
        }

        for (int j1 = 0; j1 < par1 && j1 < merchantrecipelist.size(); ++j1)
        {
            this.buyingList.addToListWithCheck((MerchantRecipe)merchantrecipelist.get(j1));
        }
    }
    
    @Override
    public void useRecipe(MerchantRecipe par1MerchantRecipe)
    {
    	
    	/////
    	if (this.buyingPlayer != null)
        {
            this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
        }
        else
        {
            this.lastBuyingPlayer = null;
        }
    	/////
    	
    	
    	
    	this.needsInitilization = true;
		if (this.needsInitilization)
        {
			
			System.out.print("\nBUYING LIST" + this.buyingList.size() + "\n");
			
            if (this.buyingList.size() > 1)
            {	
                Iterator iterator = this.buyingList.iterator();

                while (iterator.hasNext())
                {
                    MerchantRecipe merchantrecipe = (MerchantRecipe)iterator.next();

                    // this might set the trade limit per trade?
                    /*if (merchantrecipe.func_82784_g())
                    {
                        merchantrecipe.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                    }*/
                }
            }

            this.addDefaultEquipmentAndRecipies(1);
            this.needsInitilization = false;

            if (this.villageObj != null && this.lastBuyingPlayer != null)
            {
                this.worldObj.setEntityState(this, (byte)14);
                this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
            }
        }
    	
    	
    	
        par1MerchantRecipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound("random.pop", this.getSoundVolume(), this.getSoundPitch());

        if (par1MerchantRecipe.hasSameIDsAs((MerchantRecipe)this.buyingList.get(this.buyingList.size() - 1)))
        {
            this.timeUntilReset = 40;
            this.needsInitilization = true;

            if (this.buyingPlayer != null)
            {
                this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
            }
            else
            {
                this.lastBuyingPlayer = null;
            }
        }

        if (par1MerchantRecipe.getItemToBuy().itemID == Item.emerald.itemID)
        {
            this.wealth += par1MerchantRecipe.getItemToBuy().stackSize;
        }
    }
    
    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
    	if (--this.randomTickDivider <= 0)
        {
            this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);

            if (this.villageObj == null)
            {
                this.detachHome();
            }
            else
            {
                ChunkCoordinates chunkcoordinates = this.villageObj.getCenter();
                this.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6F));

                if (this.field_82190_bM)
                {
                    this.field_82190_bM = false;
                    this.villageObj.func_82683_b(5);
                }
            }
        }

        if (!this.isTrading() && this.timeUntilReset > 0)
        {
            --this.timeUntilReset;

            if (this.timeUntilReset <= 0)
            {
                if (this.needsInitilization)
                {
                    if (this.buyingList.size() > 1)
                    {
                        Iterator iterator = this.buyingList.iterator();

                        while (iterator.hasNext())
                        {
                            MerchantRecipe merchantrecipe = (MerchantRecipe)iterator.next();

                            if (merchantrecipe.func_82784_g())
                            {
                                merchantrecipe.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                            }
                        }
                    }

                    this.addDefaultEquipmentAndRecipies(1);
                    this.needsInitilization = false;

                    if (this.villageObj != null && this.lastBuyingPlayer != null)
                    {
                        this.worldObj.setEntityState(this, (byte)14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }

                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }

        super.updateAITick();
    	
        
        
        /*if (--this.randomTickDivider <= 0)
        {
            this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);

            if (this.villageObj == null)
            {
                this.detachHome();
            }
            else
            {
                ChunkCoordinates chunkcoordinates = this.villageObj.getCenter();
                this.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6F));

                if (this.field_82190_bM)
                {
                    this.field_82190_bM = false;
                    this.villageObj.func_82683_b(5);
                }
            }
        }

        if (!this.isTrading() && this.timeUntilReset > 0)
        {
            --this.timeUntilReset;

            if (this.timeUntilReset <= 0)
            {
                if (this.needsInitilization)
                {
                    if (this.buyingList.size() > 1)
                    {
                        Iterator iterator = this.buyingList.iterator();

                        while (iterator.hasNext())
                        {
                            MerchantRecipe merchantrecipe = (MerchantRecipe)iterator.next();

                            if (merchantrecipe.func_82784_g())
                            {
                                merchantrecipe.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                            }
                        }
                    }

                    this.addDefaultEquipmentAndRecipies(1);
                    this.needsInitilization = false;

                    if (this.villageObj != null && this.lastBuyingPlayer != null)
                    {
                        this.worldObj.setEntityState(this, (byte)14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }

                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }

        super.updateAITick();*/
    }
    
    @Override
    public void playLivingSound()
    {
        /*String s = this.getLivingSound();

        if (s != null)
        {
            this.playSound(s, this.getSoundVolume(), this.getSoundPitch());
        }*/
    }
    
    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return this.isTrading() ? "" : ""; //"mob.villager.haggle" : "mob.villager.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "damage.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "damage.fallbig";
    }
    
    /**
     * handles entity death timer, experience orb and particle creation
     */
    protected void onDeathUpdate()
    {
        ++this.deathTime;

        if (this.deathTime == 20)
        {
            int i;

            if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && !this.isChild() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
            {
                i = this.getExperiencePoints(this.attackingPlayer);

                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
                }
            }

            this.setDead();

            for (i = 0; i < 20; ++i)
            {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
            }
            
            int posX = (int)this.posX;
            int posY = (int)this.posY;
            int posZ = (int)this.posZ;
            int blockID = this.worldObj.getBlockId(posX, posY, posZ);
            if (blockID == 0 || blockID == Greece.ash.blockID) {
            	for (int checkY = posY-1; checkY > posY-20; --checkY) {
            		if (this.worldObj.getBlockId(posX, checkY, posZ) != 0) {
            			this.worldObj.setBlock(posX, checkY+1, posZ, Block.skull.blockID, 1, 2);
            			return;
            		}
            	}
            } else {
            	for (int checkX = posX-1; checkX < posX+2; ++checkX) {
            		for (int checkZ = posZ-1; checkZ < posZ+2; ++checkZ) {
            			for (int checkY = posY+2; checkY > posY-10; --checkY) {
            				blockID = this.worldObj.getBlockId(checkX, checkY, checkZ);
            				if (blockID != 0 && this.worldObj.getBlockId(checkX, checkY+1, checkZ) == 0) {
            					this.worldObj.setBlock(checkX, checkY+1, checkZ, Block.skull.blockID, 1, 2);
            					return;
            				}
            			}
            		}
            	}
            }
        }
    }
    
    @Override
    public void func_110297_a_(ItemStack par1ItemStack)
    {
        /*if (!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20)
        {
            this.livingSoundTime = -this.getTalkInterval();

            if (par1ItemStack != null)
            {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
            }
            else
            {
                this.playSound("mob.villager.no", this.getSoundVolume(), this.getSoundPitch());
            }
        }*/
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3D);
    }
}

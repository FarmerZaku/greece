package mod.greece;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekFire extends BlockFire
{
    /** The chance this block will encourage nearby blocks to catch on fire */
    private int[] chanceToEncourageFire = new int[256];

    /**
     * This is an array indexed by block ID the larger the number in the array the more likely a block type will catch
     * fires
     */
    private int[] abilityToCatchFire = new int[256];
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    protected GreekFire(int par1)
    {
        super(par1);
        this.setTickRandomly(true);
        this.setLightValue(1.0f);
    }

    /**
     * This method is called on a block after all other blocks gets already created. You can use it to reference and
     * configure something on the block that needs the others ones.
     */
    public void initializeBlock()
    {
        abilityToCatchFire = Block.blockFlammability;
        chanceToEncourageFire = Block.blockFireSpreadSpeed;
        this.setBurnRate(Block.planks.blockID, 5, 20);
        this.setBurnRate(Block.woodDoubleSlab.blockID, 5, 20);
        this.setBurnRate(Block.woodSingleSlab.blockID, 5, 20);
        this.setBurnRate(Block.fence.blockID, 5, 20);
        this.setBurnRate(Block.stairsWoodOak.blockID, 5, 20);
        this.setBurnRate(Block.stairsWoodBirch.blockID, 5, 20);
        this.setBurnRate(Block.stairsWoodSpruce.blockID, 5, 20);
        this.setBurnRate(Block.stairsWoodJungle.blockID, 5, 20);
        this.setBurnRate(Block.wood.blockID, 5, 5);
        this.setBurnRate(Block.leaves.blockID, 30, 60);
        this.setBurnRate(Block.bookShelf.blockID, 30, 20);
        this.setBurnRate(Block.tnt.blockID, 15, 100);
        this.setBurnRate(Block.tallGrass.blockID, 60, 100);
        this.setBurnRate(Block.cloth.blockID, 30, 60);
        this.setBurnRate(Block.vine.blockID, 15, 100);
        this.setBurnRate(Block.coalBlock.blockID, 5, 5);
        this.setBurnRate(Block.hay.blockID, 60, 20);
    }

    /**
     * Sets the burn rate for a block. The larger abilityToCatchFire the more easily it will catch. The larger
     * chanceToEncourageFire the faster it will burn and spread to other blocks. Args: blockID, chanceToEncourageFire,
     * abilityToCatchFire
     */
    private void setBurnRate(int par1, int par2, int par3)
    {
        Block.setBurnProperties(par1, par2, par3);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 3;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World par1World)
    {
        return 30;
    }
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	entity.setFire(15);
    }

    private void dropAsh(World world, int x, int y, int z, int quantity) {
    	for (int checkY = y; checkY > y - 15 && checkY > 0; --checkY) {
    		int blockID = world.getBlockId(x, checkY, z);
    		if (blockID == Greece.ash.blockID && world.getBlockMetadata(x, checkY, z) < 7) {
    			world.setBlockMetadataWithNotify(x, checkY, z, world.getBlockMetadata(x, checkY, z)+1, 2);
    			break;
    		} else if (blockID != 0 && world.getBlockMaterial(x, checkY, z).blocksMovement()) {
    			int aboveID = world.getBlockId(x, checkY+1, z);
    			if (aboveID == 0) {
    				//world.setBlock(x, checkY+1, z, Greece.ash.blockID);
    				if (world.getBlockMaterial(x, checkY, z).getCanBurn()) {
	    				if (quantity > 7) {
	    					quantity = 7;
	    				}
    					world.setBlock(x, checkY+1, z, Greece.greekFire.blockID, quantity, 3);
    				} else if (Block.blocksList[world.getBlockId(x, checkY, z)].isOpaqueCube()) { //if we're going to put ash down, make sure it's on top of something opaque
    					if (quantity > 7) {
    						world.setBlock(x, checkY+1, z, Greece.ash.blockID, 7, 3);
    						if (world.getBlockId(x, checkY+2, z) == 0) {
    							world.setBlock(x, checkY+2, z, Greece.ash.blockID, quantity-7, 3);
    						}
    					} else {
    						world.setBlock(x, checkY+1, z, Greece.ash.blockID, quantity, 3);
    					}
    				} else if (world.getBlockId(x, checkY, z) == Block.skull.blockID) {
    		        	TileEntity tileEntity = world.getBlockTileEntity(x, checkY, z);
    		        	if (tileEntity != null) {
    		        		//If it is already charred, then return
    		        		if (((TileEntitySkull)tileEntity).getSkullType() == 1) {
    		        			return;
    		        		}
    		        	}
    		        	
    		        	world.setBlockToAir(x, checkY, z);
    		        	world.setBlock(x, checkY, z, Block.skull.blockID, 1, 2);
    		        	
    		        	tileEntity = world.getBlockTileEntity(x, checkY, z);
    		        	if (tileEntity != null) {
    		        		((TileEntitySkull)tileEntity).setSkullType(1, "");
    		        		world.rand.setSeed(x*checkY*z);
    		        		((TileEntitySkull)tileEntity).setSkullRotation(world.rand.nextInt(8));
    		        	}
    				}
    			} else if (aboveID == Greece.greekFire.blockID) {
    				int newMeta = world.getBlockMetadata(x, checkY+1, z) + quantity;
    				if (newMeta > 7) {
    					newMeta -= 7;
    					//world.setBlock(x, checkY+1, z, Greece.ash.blockID, 7, 2);
    					//if (world.getBlockId(x, checkY+2, z) == 0) {
    						world.setBlock(x, checkY+1, z, Greece.greekFire.blockID, 7, 2);
    					//}
    				} else {
    					world.setBlockMetadataWithNotify(x, checkY+1, z, newMeta, 2);
    				}
    			}
    			break;
    		}
    	}
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick"))
        {
            Block base = Block.blocksList[world.getBlockId(x, y - 1, z)];
            boolean flag = (base != null && base.isFireSource(world, x, y - 1, z, world.getBlockMetadata(x, y - 1, z), UP));

            if (!this.canPlaceBlockAt(world, x, y, z))
            {
            	int ashLoad = world.getBlockMetadata(x, y, z);
                world.setBlockToAir(x, y, z);
                dropAsh(world, x, y, z, ashLoad);
            }

            if (!flag && world.isRaining() && (world.canLightningStrikeAt(x, y, z) || world.canLightningStrikeAt(x - 1, y, z) || world.canLightningStrikeAt(x + 1, y, z) || world.canLightningStrikeAt(x, y, z - 1) || world.canLightningStrikeAt(x, y, z + 1)))
            {
            	int ashLoad = world.getBlockMetadata(x, y, z);
                world.setBlockToAir(x, y, z);
                dropAsh(world, x, y, z, ashLoad);
            }
            else
            {
                int l = world.getBlockMetadata(x, y, z);

                if (l < 15)
                {
                    world.setBlockMetadataWithNotify(x, y, z, l + random.nextInt(3) / 2, 4);
                }

                world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world) + random.nextInt(10));

                if (!flag && !this.canNeighborBurn(world, x, y, z))
                {
                    if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z) || l > 3)
                    {
                    	int ashLoad = world.getBlockMetadata(x, y, z);
                        world.setBlockToAir(x, y, z);
                        dropAsh(world, x, y, z, ashLoad);
                    }
                }
                else if (!flag && !this.canBlockCatchFire(world, x, y - 1, z, UP) && l == 15 && random.nextInt(4) == 0)
                {
                	int ashLoad = world.getBlockMetadata(x, y, z);
                    world.setBlockToAir(x, y, z);
                    dropAsh(world, x, y, z, ashLoad);
                }
                else
                {
                    boolean flag1 = world.isBlockHighHumidity(x, y, z);
                    byte b0 = 0;

                    if (flag1)
                    {
                        b0 = -50;
                    }

                    this.tryToCatchBlockOnFire(world, x + 1, y, z, 300 + b0, random, l, WEST );
                    this.tryToCatchBlockOnFire(world, x - 1, y, z, 300 + b0, random, l, EAST );
                    this.tryToCatchBlockOnFire(world, x, y - 1, z, 250 + b0, random, l, UP   );
                    this.tryToCatchBlockOnFire(world, x, y + 1, z, 250 + b0, random, l, DOWN );
                    this.tryToCatchBlockOnFire(world, x, y, z - 1, 300 + b0, random, l, SOUTH);
                    this.tryToCatchBlockOnFire(world, x, y, z + 1, 300 + b0, random, l, NORTH);

                    //check every neighboring block...
                    for (int i1 = x - 1; i1 <= x + 1; ++i1)
                    {
                        for (int j1 = z - 1; j1 <= z + 1; ++j1)
                        {
                            for (int k1 = y - 1; k1 <= y + 4; ++k1)
                            {
                            	//exclude our block
                                if (i1 != x || k1 != y || j1 != z)
                                {
                                    int l1 = 100;

                                    if (k1 > y + 1)
                                    {
                                        l1 += (k1 - (y + 1)) * 100;
                                    }

                                    int i2 = this.getChanceOfNeighborsEncouragingFire(world, i1, k1, j1);

                                    if (i2 > 0)
                                    {
                                        int j2 = (i2 + 40 + world.difficultySetting * 7) / (l + 30);

                                        if (flag1)
                                        {
                                            j2 /= 2;
                                        }

                                        if (j2 > 0 && random.nextInt(l1) <= j2 && (!world.isRaining() || !world.canLightningStrikeAt(i1, k1, j1)) && !world.canLightningStrikeAt(i1 - 1, k1, z) && !world.canLightningStrikeAt(i1 + 1, k1, j1) && !world.canLightningStrikeAt(i1, k1, j1 - 1) && !world.canLightningStrikeAt(i1, k1, j1 + 1))
                                        {
                                            //int k2 = l + random.nextInt(5) / 4;
                                            int k2 = 0;

                                            if (k2 > 15)
                                            {
                                                k2 = 15;
                                            }

                                            world.setBlock(i1, k1, j1, this.blockID, k2, 3);
                                            dropAsh(world, i1, k1, j1, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean func_82506_l()
    {
        return false;
    }

    @Deprecated
    private void tryToCatchBlockOnFire(World par1World, int par2, int par3, int par4, int par5, Random par6Random, int par7)
    {
        tryToCatchBlockOnFire(par1World, par2, par3, par4, par5, par6Random, par7, UP);
    }

    private void tryToCatchBlockOnFire(World world, int x, int y, int z, int par5, Random par6Random, int par7, ForgeDirection face)
    {
        int j1 = 0;
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            j1 = block.getFlammability(world, x, y, z, world.getBlockMetadata(x, y, z), face);
        }

        if (par6Random.nextInt(par5) < j1)
        {
            boolean flag = world.getBlockId(x, y, z) == Block.tnt.blockID;

            if (par6Random.nextInt(par7 + 10) < 5 && !world.canLightningStrikeAt(x, y, z))
            {
                int k1 = 0;//par7;// + par6Random.nextInt(5) / 4;

                if (k1 > 15)
                {
                    k1 = 15;
                }

                world.setBlock(x, y, z, this.blockID, k1, 3);
            }
            else
            {
                world.setBlockToAir(x, y, z);
            }

            if (flag)
            {
                Block.tnt.onBlockDestroyedByPlayer(world, x, y, z, 1);
            }
        } else if (world.getBlockId(x, y, z) == Block.skull.blockID) {
        	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        	if (tileEntity != null) {
        		//If it is already charred, then return
        		if (((TileEntitySkull)tileEntity).getSkullType() == 1) {
        			return;
        		}
        	}
        	
        	world.setBlockToAir(x, y, z);
        	world.setBlock(x, y, z, Block.skull.blockID, 1, 2);
        	
        	tileEntity = world.getBlockTileEntity(x, y, z);
        	if (tileEntity != null) {
        		((TileEntitySkull)tileEntity).setSkullType(1, "");
        		world.rand.setSeed(x*y*z);
        		((TileEntitySkull)tileEntity).setSkullRotation(world.rand.nextInt(8));
        	}
        }
    }

    /**
     * Returns true if at least one block next to this one can burn.
     */
    private boolean canNeighborBurn(World par1World, int par2, int par3, int par4)
    {
        return canBlockCatchFire(par1World, par2 + 1, par3, par4, WEST ) ||
               canBlockCatchFire(par1World, par2 - 1, par3, par4, EAST ) ||
               canBlockCatchFire(par1World, par2, par3 - 1, par4, UP   ) ||
               canBlockCatchFire(par1World, par2, par3 + 1, par4, DOWN ) ||
               canBlockCatchFire(par1World, par2, par3, par4 - 1, SOUTH) ||
               canBlockCatchFire(par1World, par2, par3, par4 + 1, NORTH);
    }

    /**
     * Gets the highest chance of a neighbor block encouraging this block to catch fire
     */
    private int getChanceOfNeighborsEncouragingFire(World par1World, int par2, int par3, int par4)
    {
        byte b0 = 0;

        if (!par1World.isAirBlock(par2, par3, par4))
        {
            return 0;
        }
        else
        {
            int l = this.getChanceToEncourageFire(par1World, par2 + 1, par3, par4, b0, WEST);
            l = this.getChanceToEncourageFire(par1World, par2 - 1, par3, par4, l, EAST);
            l = this.getChanceToEncourageFire(par1World, par2, par3 - 1, par4, l, UP);
            l = this.getChanceToEncourageFire(par1World, par2, par3 + 1, par4, l, DOWN);
            l = this.getChanceToEncourageFire(par1World, par2, par3, par4 - 1, l, SOUTH);
            l = this.getChanceToEncourageFire(par1World, par2, par3, par4 + 1, l, NORTH);
            return l;
        }
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean isCollidable()
    {
        return false;
    }

    /**
     * Checks the specified block coordinate to see if it can catch fire.  Args: blockAccess, x, y, z
     * Deprecated for a side-sensitive version
     */
    @Deprecated
    public boolean canBlockCatchFire(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return canBlockCatchFire(par1IBlockAccess, par2, par3, par4, UP);
    }

    /**
     * Retrieves a specified block's chance to encourage their neighbors to burn and if the number is greater than the
     * current number passed in it will return its number instead of the passed in one.  Args: world, x, y, z,
     * curChanceToEncourageFire
     * Deprecated for a side-sensitive version
     */
    @Deprecated
    public int getChanceToEncourageFire(World par1World, int par2, int par3, int par4, int par5)
    {
        return getChanceToEncourageFire(par1World, par2, par3, par4, par5, UP);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || this.canNeighborBurn(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
    {
        if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z) && !this.canNeighborBurn(world, x, y, z))
        {
        	int ashLoad = world.getBlockMetadata(x, y, z);
            world.setBlockToAir(x, y, z);
            dropAsh(world, x, y, z, ashLoad);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (par1World.provider.dimensionId > 0 || par1World.getBlockId(par2, par3 - 1, par4) != Block.obsidian.blockID || !Block.portal.tryToCreatePortal(par1World, par2, par3, par4))
        {
            if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !this.canNeighborBurn(par1World, par2, par3, par4))
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
            else
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World) + par1World.rand.nextInt(10));
            }
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(24) == 0)
        {
            par1World.playSound((double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), "fire.fire", 1.0F + par5Random.nextFloat(), par5Random.nextFloat() * 0.7F + 0.3F, false);
        }

        int l;
        float f;
        float f1;
        float f2;

        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !Block.fire.canBlockCatchFire(par1World, par2, par3 - 1, par4, UP))
        {
            if (Block.fire.canBlockCatchFire(par1World, par2 - 1, par3, par4, EAST))
            {
                for (l = 0; l < 2; ++l)
                {
                    f = (float)par2 + par5Random.nextFloat() * 0.1F;
                    f1 = (float)par3 + par5Random.nextFloat();
                    f2 = (float)par4 + par5Random.nextFloat();
                    par1World.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Block.fire.canBlockCatchFire(par1World, par2 + 1, par3, par4, WEST))
            {
                for (l = 0; l < 2; ++l)
                {
                    f = (float)(par2 + 1) - par5Random.nextFloat() * 0.1F;
                    f1 = (float)par3 + par5Random.nextFloat();
                    f2 = (float)par4 + par5Random.nextFloat();
                    par1World.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Block.fire.canBlockCatchFire(par1World, par2, par3, par4 - 1, SOUTH))
            {
                for (l = 0; l < 2; ++l)
                {
                    f = (float)par2 + par5Random.nextFloat();
                    f1 = (float)par3 + par5Random.nextFloat();
                    f2 = (float)par4 + par5Random.nextFloat() * 0.1F;
                    par1World.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Block.fire.canBlockCatchFire(par1World, par2, par3, par4 + 1, NORTH))
            {
                for (l = 0; l < 2; ++l)
                {
                    f = (float)par2 + par5Random.nextFloat();
                    f1 = (float)par3 + par5Random.nextFloat();
                    f2 = (float)(par4 + 1) - par5Random.nextFloat() * 0.1F;
                    par1World.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Block.fire.canBlockCatchFire(par1World, par2, par3 + 1, par4, DOWN))
            {
                for (l = 0; l < 2; ++l)
                {
                    f = (float)par2 + par5Random.nextFloat();
                    f1 = (float)(par3 + 1) - par5Random.nextFloat() * 0.1F;
                    f2 = (float)par4 + par5Random.nextFloat();
                    par1World.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        else
        {
            for (l = 0; l < 3; ++l)
            {
                f = (float)par2 + par5Random.nextFloat();
                f1 = (float)par3 + par5Random.nextFloat() * 0.5F + 0.5F;
                f2 = (float)par4 + par5Random.nextFloat();
                par1World.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[] {par1IconRegister.registerIcon(this.getTextureName() + "_layer_0"), par1IconRegister.registerIcon(this.getTextureName() + "_layer_1")};
    }

    @SideOnly(Side.CLIENT)
    public Icon getFireIcon(int par1)
    {
        return this.iconArray[par1];
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return this.iconArray[0];
    }
    
    /**
     * Side sensitive version that calls the block function.
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param face The side the fire is coming from
     * @return True if the face can catch fire.
     */
    public boolean canBlockCatchFire(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            return block.isFlammable(world, x, y, z, world.getBlockMetadata(x, y, z), face);
        }
        return false;
    }

    /**
     * Side sensitive version that calls the block function.
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param oldChance The previous maximum chance.
     * @param face The side the fire is coming from
     * @return The chance of the block catching fire, or oldChance if it is higher
     */
    public int getChanceToEncourageFire(World world, int x, int y, int z, int oldChance, ForgeDirection face)
    {
        int newChance = 0;
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            newChance = block.getFireSpreadSpeed(world, x, y, z, world.getBlockMetadata(x, y, z), face);
        }
        return (newChance > oldChance ? newChance : oldChance);
    }
}

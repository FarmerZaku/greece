package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekAsh extends Block
{
    protected GreekAsh(int par1)
    {
        super(par1, Material.snow);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsForAshDepth(0);
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(this.textureName);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4) & 7;
        float f = 0.125F;
        return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)((float)par3 + (float)l * f), (double)par4 + this.maxZ);
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
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBoundsForAshDepth(0);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.setBlockBoundsForAshDepth(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * calls setBlockBounds based on the depth of the snow. Int is any values 0x0-0x7, usually this blocks metadata.
     */
    protected void setBlockBoundsForAshDepth(int par1)
    {
        int j = par1 & 7;
        float f = (float)(2 * (1 + j)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int x, int y, int z)
    {
        int l = par1World.getBlockId(x, y - 1, z);        
        Block block = Block.blocksList[l];
        return true;
//        if (block == null) return false;
//        if (block == this && (par1World.getBlockMetadata(x, y - 1, z) & 7) == 7) return true;
//        if (!block.isLeaves(par1World, x, y - 1, z) && !Block.blocksList[l].isOpaqueCube()) return false;
//        return par1World.getBlockMaterial(x, y - 1, z).blocksMovement();
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        this.canAshStay(par1World, par2, par3, par4);
    }

    /**
     * Checks if this ash block can stay at this location.
     */
    private boolean canAshStay(World world, int x, int y, int z)
    {
    	if (y <= 0) {
    		return false;
    	}
    	int belowID = world.getBlockId(x, y-1, z);
    	int thisMeta = world.getBlockMetadata(x, y, z);
        if (belowID != 0) {
        	int belowMeta =  world.getBlockMetadata(x, y-1, z);
        	if (belowID == this.blockID && belowMeta < 7) {
        		thisMeta++; //adding 1 because the first level is level 0. 0x2=0, so two layers lose a layer when adding
        		int remainder = belowMeta + thisMeta - 7;
        		world.setBlockMetadataWithNotify(x, y-1, z, Math.min(belowMeta + thisMeta, 7), 2);
        		if (remainder > 0) {
        			world.setBlockMetadataWithNotify(x, y, z, remainder, 2);
        			return true;
        		} else {
        			world.setBlock(x, y, z, 0, 0, 2);
        			return false;
        		}
        	} else if (thisMeta > 2) {
        		int[] diffs = {0, 0, 0, 0};
        		int i = 0;
        		boolean isDifference = true;
        		
        		while (isDifference) {
        			int winX = 0;
        			int winZ = 0;
        			int winNum = 0;
        			for (int checkX = x - 1; checkX < x + 2; ++checkX) {
            			for (int checkZ = z - 1; checkZ < z + 2; ++checkZ) {
            				if (checkZ != z ^ checkX != x) {
            					int diff = 0;
            					if (world.getBlockId(checkX, y, checkZ) == Greece.ash.blockID) {
            						diff = thisMeta - world.getBlockMetadata(checkX, y, checkZ);
            					} else if (world.getBlockId(checkX, y, checkZ) == 0) {
            						diff = thisMeta;
            					}
            					if (diff > winNum) {
            						winNum = diff;
            						winX = checkX;
            						winZ = checkZ;
            					}
            				}
            			}
        			}
        			if (winNum > 3) {
        				world.setBlockMetadataWithNotify(x, y, z, thisMeta-1, 0);
        				if (world.getBlockId(winX, y, winZ) == Greece.ash.blockID) {
        					world.setBlockMetadataWithNotify(winX, y, winZ, world.getBlockMetadata(winX, y, winZ)+1, 3);
        				} else {
        					//world.setBlock(winX, y, winZ, Greece.ash.blockID, 0, 3);
        					if (!world.getBlockMaterial(winX, y-1, winZ).blocksMovement() || world.getBlockId(winX, y-1, winZ) == Greece.ash.blockID) {
        						world.setBlock(winX, y, winZ, Greece.ash.blockID, 0, 3);
        						canAshStay(world, winX, y, winZ);
        					} else if (Block.blocksList[world.getBlockId(winX, y-1, winZ)].isOpaqueCube()) {
        						world.setBlock(winX, y, winZ, Greece.ash.blockID, 0, 3);
        						canAshStay(world, winX, y, winZ);
        					}
        				}
        				thisMeta = world.getBlockMetadata(x, y, z);
        			} else {
        				isDifference = false;
        			}
        		}
        		/*
        		//System.out.println("A");
        		for (int checkX = x - 1; checkX < x + 2; ++checkX) {
        			for (int checkZ = z - 1; checkZ < z + 2; ++checkZ) {
        				if (checkZ != z && checkX != x) {
        					if (world.getBlockId(checkX, y, checkZ) == Greece.ash.blockID) {
        						diffs[i] = thisMeta - world.getBlockMetadata(checkX, y, checkZ);
        					} else if (world.getBlockId(checkX, y, checkZ) == 0) {
        						diffs[i] = thisMeta;
        					}
        					if (diffs[i] > 1) {
        						isDifference = true;
        					}
        					i++;
        				}
        			}
        		}
        		
        		//System.out.println("B");
        		while (isDifference) {
        			int winIndex = 0;
        			int winNum = 0;
	        		for (int j = 0; j < 4; ++j) {
	        			if (diffs[j] > winNum) {
	        				winIndex = j;
	        				winNum = diffs[j];
	        			}
	        		}
	        		//System.out.println("C");
	        		if (winNum <= 1) {
	        			isDifference = false;
	        		} else {
	        			//System.out.println("D");
	        			thisMeta--;
	        			for (int j = 0; j < 4; ++j) {
	        				diffs[j]--;
	        			}
	        			//System.out.println("E");
	        			diffs[winIndex]--;
	        			//System.out.println("F");
	        			int setX = x;
	        			int setZ = z;
	        			switch (winIndex) {
	        			case 0:
	        				setX--;
	        				setZ--;
	        				break;
	        			case 1:
	        				setX--;
	        				setZ++;
	        				break;
	        			case 2:
	        				setX++;
	        				setZ--;
	        				break;
	        			case 3:
	        				setX++;
	        				setZ++;	        				
	        			}
	        			world.setBlockMetadataWithNotify(x, y, z, thisMeta, 0);
	        			if (world.getBlockId(setX, y, setZ) == Greece.ash.blockID) {
        					world.setBlockMetadataWithNotify(setX, y, setZ, world.getBlockMetadata(setX, y, setZ)+1, 3);
        				} else {
        					if (!world.getBlockMaterial(setX, y-1, setZ).blocksMovement() || world.getBlockId(setX, y-1, setZ) == Greece.ash.blockID) {
        						world.setBlock(setX, y, setZ, Greece.ash.blockID, 0, 3);
        						canAshStay(world, setX, y, setZ);
        					} else if (Block.blocksList[world.getBlockId(setX, y-1, setZ)].isOpaqueCube()) {
        						world.setBlock(setX, y, setZ, Greece.ash.blockID, 0, 3);
        					}
        				}
	        			thisMeta = world.getBlockMetadata(x, y, z);
	        		}
        		}
        		//world.setBlockMetadataWithNotify(x, y, z, thisMeta, 3);
        		if (world.getBlockId(x, y+1, z) == Greece.ash.blockID) {
        			canAshStay(world, x, y+1, z);
        		}*/
        	}
        } else if (belowID == Greece.greekFire.blockID || belowID == 0) {
        	for (int checkY = y-2; checkY > y - 25 && checkY > 0; --checkY) {
        		//world.spawnParticle("smoke", x, checkY, z, 0.0D, 0.0D, 0.0D);
        		//Minecraft.getMinecraft().effectRenderer.addEffect(new EntitySmokeFX(world,x,checkY,z,-0.1D,-0.1D,-0.1D,10F));
        		int curID = world.getBlockId(x, checkY, z);
        		int belowMeta =  world.getBlockMetadata(x, checkY, z);
        		if (curID == this.blockID && belowMeta < 7) {
            		int remainder = belowMeta + thisMeta - 7;
            		world.setBlockMetadataWithNotify(x, checkY, z, Math.min(belowMeta + thisMeta, 7), 2);
            		if (remainder > 0 && world.getBlockId(x, checkY+1, z) == 0) {
            			world.setBlockMetadataWithNotify(x, checkY+1, z, remainder, 2);
            		}
            		break;
        		} else if (curID != 0 && world.getBlockMaterial(x, checkY, z).blocksMovement() && Block.blocksList[world.getBlockId(x, checkY, z)].isOpaqueCube()) {
        			if (world.getBlockId(x, checkY+1, z) == 0) {
        				world.setBlock(x, checkY+1, z, this.blockID, world.getBlockMetadata(x, y, z), 2);
        			}
        			break;
        		}
        	}
        	world.playSoundEffect(x+0.5D, y+0.5D, z+0.5D, "step.snow", 10.0F, 1.0F);
        	world.setBlock(x, y, z, 0, 0, 2);
        	return false;
        }
        return true;
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	this.canAshStay(par1World, par2, par3, par4);
    }
    
    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        par1World.setBlockToAir(par3, par4, par5);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.snowball.itemID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return (meta & 7) + 1;
    }

    /**
     * Determines if a new block can be replace the space occupied by this one,
     * Used in the player's placement code to make the block act like water, and lava.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return True if the block is replaceable by another block
     */
    @Override
    public boolean isBlockReplaceable(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta >= 7 ? false : blockMaterial.isReplaceable());
    }
}

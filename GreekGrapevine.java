package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekGrapevine extends BlockVine {

	@SideOnly(Side.CLIENT)
    private Icon[] iconArray;
	
	public GreekGrapevine(int id) {
		super(id);
		
		setHardness(1.0f);
		setStepSound(Block.soundGrassFootstep);
		setUnlocalizedName("greekGrapevine");
		setCreativeTab(CreativeTabs.tabBlock);
		setTickRandomly(false);
	}
	
	public int tickRate() {
    	//Approx. 20 ticks per second
    	return 20*60*5;
    }
	
	/**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
    	//make sure we're at least near to some dirt or another vine piece
    	boolean flag = false;
    	for (int mod_x = -1; mod_x < 2; ++mod_x) {
    		for (int mod_y = -1; mod_y < 2; ++mod_y) {
    			for (int mod_z = -1; mod_z < 2; ++mod_z) {
    				if (mod_x == 0 || mod_y == 0 || mod_z == 0) {
    					int check_id = world.getBlockId(x+mod_x, y+mod_y, z+mod_z);
    					if (check_id == this.blockID || check_id == Block.dirt.blockID || check_id == Block.grass.blockID) {
    						flag = true;
    						break;
    					}
    				}
    			}
    		}
    	}
    	if (!flag) {
    		return false;
    	}
        switch (side)
        {
            case 1:
                return this.canBePlacedOn(world.getBlockId(x, y + 1, z));
            case 2:
                return this.canBePlacedOn(world.getBlockId(x, y, z + 1));
            case 3:
                return this.canBePlacedOn(world.getBlockId(x, y, z - 1));
            case 4:
                return this.canBePlacedOn(world.getBlockId(x + 1, y, z));
            case 5:
                return this.canBePlacedOn(world.getBlockId(x - 1, y, z));
            default:
                return false;
        }
    }

	public void updateTick(World world, int x, int y, int z, Random random)
    {		
		//Make the crop spread
		if (!world.isRemote) // && world.rand.nextInt(4) == 0)
        {
			//Update the crop's maturity
			int this_meta = world.getBlockMetadata(x, y, z);
			int crop_maturity = (this_meta >> 2) & 3;
			crop_maturity++;
			//System.out.println("Maturity: " + crop_maturity);
			if (crop_maturity <= 3) {
				this_meta = this_meta & 3;
				crop_maturity = (crop_maturity << 2) & 12;
				this_meta = this_meta | crop_maturity;
				world.setBlockMetadataWithNotify(x, y, z, this_meta, 2);
			}
			
			world.scheduleBlockUpdate(x, y, z, world.getBlockId(x, y, z), this.tickRate());
			
			//spread the vine
			for (int i = 0; i < 5; ++i) {
				//make it check upwards less frequently (1 in 6), same level sometimes (2 in 6), below most times (3 in 6)
				int y_dir_to_check = random.nextInt(6)-4;
				if (y_dir_to_check < -1) {
					y_dir_to_check = -1;
				} else if (y_dir_to_check < 0) {
					y_dir_to_check = 0;
				}
				
				int x_bias = 0;
				int z_bias = 0;
				this_meta = this_meta & 3;
				if (this_meta == 0) {
					z_bias = 1;
				} else if (this_meta == 1) {
					x_bias = -1;
				} else if (this_meta == 2) {
					z_bias = -1;
				} else {
					x_bias = 1;
				}
				
				int z_dir_to_check = 0;
				int x_dir_to_check = 0;
				
				if (y_dir_to_check == 0) {
					if (x_bias != 0) {
						x_dir_to_check = random.nextInt(2) * x_bias;
					} else {
						if (random.nextBoolean()) {
							x_dir_to_check = -1;
						} else {
							x_dir_to_check = 1;
						}
					}
					if (z_bias != 0) {
						z_dir_to_check = random.nextInt(2) * z_bias;
					} else {
						if (random.nextBoolean()) {
							z_dir_to_check = -1;
						} else {
							z_dir_to_check = 1;
						}
					}		
				}
				
				int check_x = x + x_dir_to_check;
				int check_y = y + y_dir_to_check;
				int check_z = z + z_dir_to_check;
				
				//if growing straight down, don't bother to check for surface
	//			if (x_dir_to_check == 0 && z_dir_to_check == 0 && y_dir_to_check == -1) {
	//				if (world.getBlockId(check_x, check_y, check_z) == 0) {
	//					world.setBlock(check_x, check_y, check_z, this.blockID, this_meta, 2);
	//					world.scheduleBlockUpdate(check_x, check_y, check_z, this.blockID, this.tickRate()*2);
	//					world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate()*2);
	//					return;
	//				}
	//			}
	//			
				//check if can grow there
				if (world.getBlockId(check_x, check_y, check_z) == 0) {
					if (y_dir_to_check != 0 || check_x == x || check_z == z) {
						if (canBePlacedOn(world.getBlockId(check_x+x_bias, check_y, check_z+z_bias))) {
							//System.out.println("growin' flat like");
							world.setBlock(check_x, check_y, check_z, this.blockID, this_meta, 2);
							world.scheduleBlockUpdate(check_x, check_y, check_z, this.blockID, this.tickRate()*2);
							world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate()*2);
						}
					} else {
						if (canBePlacedOn(world.getBlockId(x+x_bias, y, z+z_bias))) {
							//System.out.println("growin' 'round corner");
							int dir_meta = 0;
							if (x_bias != 0) {
								if (z_dir_to_check == -1) {
									dir_meta = 0;
								} else {
									dir_meta = 2;
								}
							} else {
								if (x_dir_to_check == -1) {
									dir_meta = 3;
								} else {
									dir_meta = 1;
								}
							}
							world.setBlock(check_x, check_y, check_z, this.blockID, dir_meta, 2);
							world.scheduleBlockUpdate(check_x, check_y, check_z, this.blockID, this.tickRate()*2);
							world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate()*2);
						}
					}
				}
			}
        }
    }
	
	/**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        /*if (!par1World.isRemote)// && !this.canVineStay(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }*/
    }
	
	/**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        byte b0 = 0;

        System.out.println("Placed on side " + side);
        switch (side)
        {
            case 2:
                b0 = 0;
                break;
            case 3:
                b0 = 2;
                break;
            case 4:
                b0 = 3;
                break;
            case 5:
                b0 = 1;
        }

        //System.out.println("Direction: " + b0);
        world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
        return b0;// != 0 ? b0 : par9;
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
        float f = 0.0625F;
        int this_meta = par1IBlockAccess.getBlockMetadata(x, y, z);
        int this_facing = this_meta & 3;
        //System.out.println("This facing: " + this_facing);
        float minX = 1.0F;
        float minY = 1.0F;
        float minZ = 1.0F;
        float maxX = 0.0F;
        float maxY = 0.0F;
        float maxZ = 0.0F;
        boolean flag = this_meta > 0;

        //this_facing = 0;
        
        int below_id = par1IBlockAccess.getBlockId(x, y-1, z);
        
        if (this_facing == 0) {
            minZ = 0.9375F;
            if (below_id != Block.dirt.blockID && below_id != Block.grass.blockID) {
            	minZ = 0.8375F;
            }
            maxZ = 1.0F;
            minX = 0.0F;
            maxX = 1.0F;
            minY = 0.0F;
            maxY = 1.0F;
            flag = true;
        } else if (this_facing == 1) {
            maxX = 0.0625F;
            if (below_id != Block.dirt.blockID && below_id != Block.grass.blockID) {
            	maxX = 0.1625F;
            }
            minX = 0.0F;
            minY = 0.0F;
            maxY = 1.0F;
            minZ = 0.0F;
            maxZ = 1.0F;
            flag = true;
        } else if (this_facing == 2) {
            maxZ = 0.0625F;
            if (below_id != Block.dirt.blockID && below_id != Block.grass.blockID) {
            	maxZ = 0.1625F;
            }
            minZ = 0.0F;
            minX = 0.0F;
            maxX = 1.0F;
            minY = 0.0F;
            maxY = 1.0F;
            flag = true;
        } else if (this_facing == 3)
        {
            minX = 0.9375F;
            if (below_id != Block.dirt.blockID && below_id != Block.grass.blockID) {
            	minX = 0.8375F;
            }
            maxX = 1.0F;
            minY = 0.0F;
            maxY = 1.0F;
            minZ = 0.0F;
            maxZ = 1.0F;
            flag = true;
        }
        
        if (!flag && this.canBePlacedOn(par1IBlockAccess.getBlockId(x, y + 1, z)))
        {
            minY = 0.9375F;
            if (below_id == Block.dirt.blockID || below_id == Block.grass.blockID) {
            	minY = 0.8375F;
            }
            maxY = 1.0F;
            minX = 0.0F;
            maxX = 1.0F;
            minZ = 0.0F;
            maxZ = 1.0F;
        }

        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
	public Icon getIcon(int par1, int meta)
    {
		int crop_maturity = (meta >> 2) & 3;
			
        if (crop_maturity < 3)
        {
            return this.iconArray[crop_maturity];
        }
        else
        {
            return this.iconArray[3];
        }
    }
	
	@SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[4];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }
	
	private boolean canBePlacedOn(int blockId)
    {
        if (blockId == 0)
        {
            return false;
        }
        else
        {
            Block block = Block.blocksList[blockId];
            return block.renderAsNormalBlock() && block.blockMaterial.blocksMovement();
        }
    }
	
	/**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return random.nextInt(3)+2;
    }
    
	public int idDropped(int metadata, Random random, int fortune) {
		int crop_maturity = (metadata >> 2) & 3;
		
		//System.out.println("Dropping with crop maturity of " + crop_maturity);
        if (crop_maturity < 3)
        {
        	return 0;
        } else {
        	return Greece.grapes.itemID;
        }
        
	}
	
	 /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
		int crop_maturity = (metadata >> 2) & 3;
		if (crop_maturity != 0) {
			world.setBlock(x, y, z, this.blockID, metadata&3, 2);
			world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
		}
	}
	
	@Override
    public int getRenderType()
    {
        return Greece.vineBlockModelID;
    }
}

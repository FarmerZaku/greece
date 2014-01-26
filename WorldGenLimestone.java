package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


public class WorldGenLimestone extends WorldGenerator
{
    /** Stores ID of block to create */
    private int blockID;

    /** The maximum radius used when generating a patch of blocks. */
    private int radius;

    public WorldGenLimestone(int limestoneId, int genRadius)
    {
        this.blockID = limestoneId;
        this.radius = genRadius;
    }

    public boolean generate(World world, Random random, int x, int y, int z)
    {
        if (world.getBlockMaterial(x, y, z) != Material.rock)
        {
            return false;
        }
        else
        {
        	/*int stoneId = Block.stone.blockID;
        	
        	for (int i = 0; i < areaPoints.length; ++i) {
        		int temp_x = areaPoints[i][0]+x;
        		//accidentally transposed the y&z when making the variable...
        		int temp_y = areaPoints[i][2]+y;
        		int temp_z = areaPoints[i][1]+z;
        		
				if (world.getBlockId(temp_x, temp_y, temp_z) == stoneId) {
					world.setBlock(temp_x, temp_y, temp_z, blockID, 0, 2);
				}
        	}
        	
        	*/
            int l = random.nextInt(this.radius - 2) + 2;
            byte b0 = 2;

            for (int i1 = x - l; i1 <= x + l; ++i1)
            {
                for (int j1 = z - l; j1 <= z + l; ++j1)
                {
                    int k1 = i1 - x;
                    int l1 = j1 - z;

                    if (k1 * k1 + l1 * l1 <= l * l)
                    {
                        for (int i2 = y - b0; i2 <= y + b0; ++i2)
                        {
                            int j2 = world.getBlockId(i1, i2, j1);

                            if (j2 == Block.stone.blockID)
                            {
                                world.setBlock(i1, i2, j1, this.blockID, 0, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}

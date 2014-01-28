package mod.greece;
 
import java.util.Random;
 

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
 
public class EventManager implements IWorldGenerator {
		int sardOffsetX, sardOffsetY;
		int onyxOffsetX, onyxOffsetY;
		int bOffsetX, bOffsetY;
		int cOffsetX, cOffsetY;
		int dOffsetX, dOffsetY;
		int eOffsetX, eOffsetY;
		int fOffsetX, fOffsetY;
		int gOffsetX, gOffsetY;
		int hOffsetX, hOffsetY;
		int iOffsetX, iOffsetY;
		int jOffsetX, jOffsetY;
		int kOffsetX, kOffsetY;
		int marbleOffsetX, marbleOffsetY;
		int limestoneOffsetX, limestoneOffsetY;
		
		public EventManager() {
			Random temp_rand = new Random();
			sardOffsetX = temp_rand.nextInt(600)-300;
			sardOffsetY = temp_rand.nextInt(600)-300;
			onyxOffsetX = temp_rand.nextInt(600)-300; 
			onyxOffsetY = temp_rand.nextInt(600)-300; 
			bOffsetX = temp_rand.nextInt(600)-300; 
			bOffsetY = temp_rand.nextInt(600)-300;
			cOffsetX = temp_rand.nextInt(600)-300;
			cOffsetY = temp_rand.nextInt(600)-300;
			dOffsetX = temp_rand.nextInt(600)-300;
			dOffsetY = temp_rand.nextInt(600)-300;
			eOffsetX = temp_rand.nextInt(600)-300;
			eOffsetY = temp_rand.nextInt(600)-300;
			fOffsetX = temp_rand.nextInt(600)-300;
			fOffsetY = temp_rand.nextInt(600)-300;
			gOffsetX = temp_rand.nextInt(600)-300;
			gOffsetY = temp_rand.nextInt(600)-300;
			hOffsetX = temp_rand.nextInt(600)-300;
			hOffsetY = temp_rand.nextInt(600)-300;
			iOffsetX = temp_rand.nextInt(600)-300;
			iOffsetY = temp_rand.nextInt(600)-300;
			jOffsetX = temp_rand.nextInt(600)-300;
			jOffsetY = temp_rand.nextInt(600)-300;
			kOffsetX = temp_rand.nextInt(600)-300;
			kOffsetY = temp_rand.nextInt(600)-300;
			marbleOffsetX = temp_rand.nextInt(600)-300;
			marbleOffsetY = temp_rand.nextInt(600)-300;
			limestoneOffsetX = temp_rand.nextInt(600)-300;
			limestoneOffsetY = temp_rand.nextInt(600)-300;
		}
	
        @Override
        public void generate(Random random, int chunkX, int chunkZ, World world,
                        IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
                switch(world.provider.dimensionId){
                        case -1: generateNether(world, random, chunkX * 16, chunkZ * 16);
                        case 0: generateSurface(world, random, chunkX * 16, chunkZ * 16);
                        case 1: generateEnd(world, random, chunkX * 16, chunkZ * 16);
                }
               
        }
       
        private boolean isInRegion(String type, int chunk_x, int chunk_y) {
        	int offset_x = 0, offset_y = 0;
        	// affects how widely spread the regions are. Higher = more frequent
        	float frequency = 0.2f;
        	// affects how big the regions are. Higher = smaller. Should be between -1 and 1
        	float threshold = 0.92f;
        	if (type == "sard") {
        		offset_x = sardOffsetX;
        		offset_y = sardOffsetY;
        	} else if (type == "onyx") {
        		offset_x = onyxOffsetX;
        		offset_y = onyxOffsetY;
        	} else if (type == "b") {
        		offset_x = bOffsetX;
        		offset_y = bOffsetY;
        	} else if (type == "c") {
        		offset_x = cOffsetX;
        		offset_y = cOffsetY;
        	} else if (type == "d") {
        		offset_x = dOffsetX;
        		offset_y = dOffsetY;
        	} else if (type == "e") {
        		offset_x = eOffsetX;
        		offset_y = eOffsetY;
        	} else if (type == "f") {
        		offset_x = fOffsetX;
        		offset_y = fOffsetY;
        	} else if (type == "g") {
        		offset_x = gOffsetX;
        		offset_y = gOffsetY;
        	} else if (type == "h") {
        		offset_x = hOffsetX;
        		offset_y = hOffsetY;
        	} else if (type == "i") {
        		offset_x = iOffsetX;
        		offset_y = iOffsetY;
        	} else if (type == "j") {
        		offset_x = jOffsetX;
        		offset_y = jOffsetY;
        	} else if (type == "k") {
        		offset_x = kOffsetX;
        		offset_y = kOffsetY;
        	} else if (type == "marble") {
        		offset_x = marbleOffsetX;
        		offset_y = marbleOffsetY;
        		threshold = 0.1f;
        		frequency = 3f;
        	} else if (type == "limestone") {
        		offset_x = limestoneOffsetX;
        		offset_y = limestoneOffsetY;
        		threshold = 0;
        		frequency = 5f;
        	}
        	
        	if ((Math.sin(chunk_x*frequency + offset_x) > threshold) && (Math.sin(chunk_y*frequency + offset_y) > threshold)) {
        		return true;
        	}
        	return false;
        }
        private void generateEnd(World world, Random random, int x, int z) {
               
        }
 
        private void generateSurface(World world, Random random, int x, int z) {
    		this.addOreSpawn(Greece.silverOre, world, random, x, z, 16, 16, 10+random.nextInt(20), 10, 15, 160);
    		this.addOreSpawn(Greece.copperOre, world, random, x, z, 16, 16, 3+random.nextInt(20), 20, 15, 160);
    		this.addOreSpawn(Greece.tinOre, world, random, x, z, 16, 16, 3+random.nextInt(20), 20, 15, 160);
    		
        	if (isInRegion("sard", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("onyx", x, z)) {
                this.addOreSpawn(Greece.onyxOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("b", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("c", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("d", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("e", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("f", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("g", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("h", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("i", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("j", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("k", x, z)) {
                this.addOreSpawn(Greece.sardOre, world, random, x, z, 16, 16, 3+random.nextInt(8), 640, 15, 160);
        	}
        	if (isInRegion("limestone", x, z)) {
                this.addStoneSpawn(Greece.limestone, world, random, x, z, 16, 16, 15, 20, 40, 240);
        	}
        	//Add in marble any place higher than 64
        	this.addStoneSpawn(Greece.marble, world, random, x, z, 16, 16, 15, 20, 64, 240);
        }
 
        private void generateNether(World world, Random random, int x, int z) {
               
        }
 
        /**
     * Adds an Ore Spawn to Minecraft. Simply register all Ores to spawn with this method in your Generation method in your IWorldGeneration extending Class
     *
     * @param The Block to spawn
     * @param The World to spawn in
     * @param A Random object for retrieving random positions within the world to spawn the Block
     * @param An int for passing the X-Coordinate for the Generation method
     * @param An int for passing the Z-Coordinate for the Generation method
     * @param An int for setting the maximum X-Coordinate values for spawning on the X-Axis on a Per-Chunk basis
     * @param An int for setting the maximum Z-Coordinate values for spawning on the Z-Axis on a Per-Chunk basis
     * @param An int for setting the maximum size of a vein
     * @param An int for the Number of chances available for the Block to spawn per-chunk
     * @param An int for the minimum Y-Coordinate height at which this block may spawn
     * @param An int for the maximum Y-Coordinate height at which this block may spawn
     **/
    public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY)
    {
          int maxPossY = minY + (maxY - 1);
          assert maxY > minY: "The maximum Y must be greater than the Minimum Y";
          assert maxX > 0 && maxX <= 16: "addOreSpawn: The Maximum X must be greater than 0 and less than 16";
          assert minY > 0: "addOreSpawn: The Minimum Y must be greater than 0";
          assert maxY < 256 && maxY > 0: "addOreSpawn: The Maximum Y must be less than 256 but greater than 0";
          assert maxZ > 0 && maxZ <= 16: "addOreSpawn: The Maximum Z must be greater than 0 and less than 16";
         
          int diffBtwnMinMaxY = maxY - minY;
          WorldGenMinable generator = new WorldGenMinable(block.blockID, maxVeinSize);
          for(int x = 0; x < chancesToSpawn; x++)
          {
                 int posX = blockXPos + random.nextInt(maxX);
                 int posY = minY + random.nextInt(diffBtwnMinMaxY);
                 int posZ = blockZPos + random.nextInt(maxZ);
                 generator.generate(world, random, posX, posY, posZ);   
          }
    }
    /**
 * Adds a stone Spawn to Minecraft. Simply register all Ores to spawn with this method in your Generation method in your IWorldGeneration extending Class
 *
 * @param The Block to spawn
 * @param The World to spawn in
 * @param A Random object for retrieving random positions within the world to spawn the Block
 * @param An int for passing the X-Coordinate for the Generation method
 * @param An int for passing the Z-Coordinate for the Generation method
 * @param An int for setting the maximum X-Coordinate values for spawning on the X-Axis on a Per-Chunk basis
 * @param An int for setting the maximum Z-Coordinate values for spawning on the Z-Axis on a Per-Chunk basis
 * @param An int for setting the maximum size of a vein
 * @param An int for the Number of chances available for the Block to spawn per-chunk
 * @param An int for the minimum Y-Coordinate height at which this block may spawn
 * @param An int for the maximum Y-Coordinate height at which this block may spawn
 **/
public void addStoneSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int radius, int chancesToSpawn, int minY, int maxY)
{
      int maxPossY = minY + (maxY - 1);
      assert maxY > minY: "The maximum Y must be greater than the Minimum Y";
      assert maxX > 0 && maxX <= 16: "addOreSpawn: The Maximum X must be greater than 0 and less than 16";
      assert minY > 0: "addOreSpawn: The Minimum Y must be greater than 0";
      assert maxY < 256 && maxY > 0: "addOreSpawn: The Maximum Y must be less than 256 but greater than 0";
      assert maxZ > 0 && maxZ <= 16: "addOreSpawn: The Maximum Z must be greater than 0 and less than 16";
     
      int diffBtwnMinMaxY = maxY - minY;
      WorldGenLimestone generator = new WorldGenLimestone(block.blockID, radius);
      for(int x = 0; x < chancesToSpawn; x++)
      {
             int posX = blockXPos + random.nextInt(maxX);
             int posY = minY + random.nextInt(diffBtwnMinMaxY);
             int posZ = blockZPos + random.nextInt(maxZ);
             generator.generate(world, random, posX, posY, posZ);
      }
}
 
}
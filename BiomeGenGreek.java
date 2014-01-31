package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenGreek extends BiomeGenBase {
	    public BiomeGenGreek(int par1, int topid, int fillid)
	    {
	        super(par1);
	        //this.spawnableCreatureList.clear();
	        //this.topBlock = (byte)Block.blockIron.blockID;
	        if (topid != -1) {
	        	if (topid <= 255) {
	        		this.topBlock = (byte)topid;
	        	} else {
	        		System.out.println("Biome generator BiomeGenGreek received invalid top block id " + topid + ": too high (>255)");
	        	}
	        }
	        if (fillid != -1) {
	        	if (fillid <= 255) {
		        	this.fillerBlock = (byte)fillid;
	        	} else {
	        		System.out.println("Biome generator BiomeGenGreek received invalid fill block id " + fillid + ": too high (>255)");
	        	}
	        }
	        this.theBiomeDecorator.treesPerChunk=1;
	        this.theBiomeDecorator.clayPerChunk=3;
	        this.theBiomeDecorator.reedsPerChunk=0;
	        //this.theBiomeDecorator.deadBushPerChunk = 2;
	        //this.theBiomeDecorator.reedsPerChunk = 50;
	        //this.theBiomeDecorator.cactiPerChunk = 10;
	    }

	    
//	    @Override
//	    public void decorate(World par1World, Random par2Random, int par3, int par4)
//	    {
//	        super.decorate(par1World, par2Random, par3, par4);
//
//	        if (par2Random.nextInt(1000) == 0)
//	        {
//	            int k = par3 + par2Random.nextInt(16) + 8;
//	            int l = par4 + par2Random.nextInt(16) + 8;
//	            WorldGenDesertWells worldgendesertwells = new WorldGenDesertWells();
//	            worldgendesertwells.generate(par1World, par2Random, k, par1World.getHeightValue(k, l) + 1, l);
//	        }
//	    }
//	    @Override
//	    public float getSpawningChance() {
//	    	return 6.8f;
//	    }
	}

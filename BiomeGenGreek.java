package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDesertWells;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;

public class BiomeGenGreek extends BiomeGenBase {
		private Integer grassColor;
		private Integer foliageColor;
	
	    public BiomeGenGreek(int par1, boolean extraStone, Integer gColor, Integer fColor)
	    {
	        super(par1);
	        //this.spawnableCreatureList.clear();
	        //this.topBlock = (byte)Block.blockIron.blockID;
	        if (extraStone) {
	        	this.fillerBlock = (byte)Block.stone.blockID;
	        }
	        this.theBiomeDecorator.treesPerChunk=1;
	        this.theBiomeDecorator.clayPerChunk=3;
	        this.theBiomeDecorator.reedsPerChunk=0;
	        //this.theBiomeDecorator.deadBushPerChunk = 2;
	        //this.theBiomeDecorator.reedsPerChunk = 50;
	        //this.theBiomeDecorator.cactiPerChunk = 10;
	        
	        grassColor = gColor;
	        foliageColor = fColor;
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
	    
	    @Override
	    public int getBiomeGrassColor()
	    {
	    	if (grassColor != null) 
	    		return grassColor;
	    	else {
	    		double d0 = (double)MathHelper.clamp_float(this.getFloatTemperature(), 0.0F, 1.0F);
	            double d1 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
	            return getModdedBiomeGrassColor(ColorizerGrass.getGrassColor(d0, d1));
	    	}
	    }
	    
	    @Override
	    public int getModdedBiomeFoliageColor(int original)
	    {
	    	if (foliageColor != null) 
	    		return foliageColor;
	    	else {
	    		BiomeEvent.GetFoliageColor event = new BiomeEvent.GetFoliageColor(this, original);
	    		MinecraftForge.EVENT_BUS.post(event);
	    		return event.newColor;
	    	}
	    }
	}

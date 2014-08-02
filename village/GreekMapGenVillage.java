package mod.greece.village;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureStart;
import cpw.mods.fml.common.IWorldGenerator;

public class GreekMapGenVillage extends MapGenVillage implements IWorldGenerator
{
    /** A list of all the biomes villages can spawn in. */
    public static List villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] {BiomeGenBase.plains, BiomeGenBase.desert});

    /** World terrain type, 0 for normal, 1 for flat map */
    private int terrainType;
    private int field_82665_g;
    private int field_82666_h;

    public GreekMapGenVillage()
    {
        this.field_82665_g = 16; //32
        this.field_82666_h = 4; //8
    }

    public GreekMapGenVillage(Map par1Map)
    {
        this();
        Iterator iterator = par1Map.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();

            if (((String)entry.getKey()).equals("size"))
            {
                this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.terrainType, 0);
            }
            else if (((String)entry.getKey()).equals("distance"))
            {
                this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_82665_g, this.field_82666_h + 1);
            }
        }
    }

    public String func_143025_a()
    {
        return "GreekVillage";
    }

    protected boolean canSpawnStructureAtCoords(int x, int z)
    {
    	//return true;
        int oldX = x;
        int oldZ = z;
        int xSpacing = 16; //make these 4 for a chaotic infinite city with buildings on/in buildings. 3 is even taller / more chaotic 
        int zSpacing = 16;

        if (x < 0)
        {
            x -= xSpacing - 1; //x -= 31
        }

        if (z < 0)
        {
            z -= zSpacing - 1; //z -= 31
        }

        //i1 & j1 are reduced by a percentage
        int i1 = (x / xSpacing); //i1 = x / 32
        int j1 = (z / zSpacing); //j1 = z / 32
        //set the random seed to a known value, to make sure we get a consistent result when checking this coordinate
        Random random = this.worldObj.setRandomSeed(i1, j1, 10387312);
        //i1 & j1 are restored to the old x & z values
        i1 *= xSpacing; //i1 *= 32
        j1 *= zSpacing; //j1 *= 32
        //And then added to by a random int.
        //i1 += random.nextInt(this.field_82665_g - this.field_82666_h); //rand(26)
        //j1 += random.nextInt(this.field_82665_g - this.field_82666_h);//rand(26)
        //i1 += random.nextInt(1);
        //j1 += random.nextInt(1);
        
        //if (random.nextInt(4) == 0) {
        	//return true;
        //}
        //if that random int happened to be zero...
        if (oldX == i1 && oldZ == j1)
        //if (random.nextInt(4) == 0)
        {
        	//check to see if this is an allowed biome
            boolean flag = this.worldObj.getWorldChunkManager().areBiomesViable(oldX * 16 + 8, oldZ * 16 + 8, 0, villageSpawnBiomes);

            //if so, return true
            if (flag)
            {
                return true;
            }
        }

        return false;
    }

    protected StructureStart getStructureStart(int par1, int par2)
    {
    	System.out.println("Starting Village Gen");
        return new GreekStructureVillageStart(this.worldObj, this.rand, par1, par2, this.terrainType+20);
    }

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		super.generate(chunkGenerator, world, chunkX, chunkZ, null);
		
	}
}

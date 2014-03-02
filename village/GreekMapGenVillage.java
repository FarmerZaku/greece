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
    	System.out.println("A");
        this.field_82665_g = 16; //32
        this.field_82666_h = 4; //8
    }

    public GreekMapGenVillage(Map par1Map)
    {
        this();
        System.out.println("B");
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
    	System.out.println("C");
        return "GreekVillage";
    }

    protected boolean canSpawnStructureAtCoords(int x, int z)
    {
        int k = x;
        int l = z;

        if (x < 0)
        {
            x -= this.field_82665_g - 1;
        }

        if (z < 0)
        {
            z -= this.field_82665_g - 1;
        }

        int i1 = x / this.field_82665_g;
        int j1 = z / this.field_82665_g;
        Random random = this.worldObj.setRandomSeed(i1, j1, 10387312);
        i1 *= this.field_82665_g;//*=32
        j1 *= this.field_82665_g;//*=32
        i1 += random.nextInt(this.field_82665_g - this.field_82666_h); //rand(26)
        j1 += random.nextInt(this.field_82665_g - this.field_82666_h);//rand(26)

        if (k == i1 && l == j1)
        {
            boolean flag = this.worldObj.getWorldChunkManager().areBiomesViable(k * 16 + 8, l * 16 + 8, 0, villageSpawnBiomes);

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
        return new GreekStructureVillageStart(this.worldObj, this.rand, par1, par2, this.terrainType);
    }

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		super.generate(chunkGenerator, world, chunkX, chunkZ, null);
		
	}
}

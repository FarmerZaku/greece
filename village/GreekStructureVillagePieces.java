package mod.greece.village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class GreekStructureVillagePieces extends StructureVillagePieces
{
    public static void func_143016_a()
    {
        MapGenStructureIO.func_143031_a(GreekComponentVillageHouse1.class, "ViBH");
        MapGenStructureIO.func_143031_a(GreekComponentVillageField.class, "ViDF");
        MapGenStructureIO.func_143031_a(GreekComponentVillageField2.class, "ViF");
        MapGenStructureIO.func_143031_a(GreekComponentVillageTorch.class, "ViL");
        MapGenStructureIO.func_143031_a(GreekComponentVillageHall.class, "ViPH");
        MapGenStructureIO.func_143031_a(GreekComponentVillageHouse4_Garden.class, "ViSH");
        MapGenStructureIO.func_143031_a(GreekComponentVillageWoodHut.class, "ViSmH");
        MapGenStructureIO.func_143031_a(GreekComponentVillageChurch.class, "ViST");
        MapGenStructureIO.func_143031_a(GreekComponentVillageHouse2.class, "ViS");
        MapGenStructureIO.func_143031_a(GreekComponentVillageStartPiece.class, "ViStart");
        MapGenStructureIO.func_143031_a(GreekComponentVillagePathGen.class, "ViSR");
        MapGenStructureIO.func_143031_a(GreekComponentVillageHouse3.class, "ViTRH");
        MapGenStructureIO.func_143031_a(GreekComponentVillageWell.class, "ViW");
    }

    public static List getStructureVillageWeightedPieceList(Random par0Random, int par1)
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageHouse4_Garden.class, 4, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageChurch.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 1 + par1)));
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageHouse1.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageWoodHut.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 5 + par1 * 3)));
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageHall.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageField.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 4 + par1)));
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageField2.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageHouse2.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0, 1 + par1)));
        arraylist.add(new GreekStructureVillagePieceWeight(GreekComponentVillageHouse3.class, 8, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 3 + par1 * 2)));
        //VillagerRegistry.addExtraVillageComponents(arraylist, par0Random, par1);

        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext())
        {
            if (((GreekStructureVillagePieceWeight)iterator.next()).villagePiecesLimit == 0)
            {
                iterator.remove();
            }
        }

        return arraylist;
    }

    private static int func_75079_a(List par0List)
    {
        boolean flag = false;
        int i = 0;
        GreekStructureVillagePieceWeight structurevillagepieceweight;

        for (Iterator iterator = par0List.iterator(); iterator.hasNext(); i += structurevillagepieceweight.villagePieceWeight)
        {
            structurevillagepieceweight = (GreekStructureVillagePieceWeight)iterator.next();

            if (structurevillagepieceweight.villagePiecesLimit > 0 && structurevillagepieceweight.villagePiecesSpawned < structurevillagepieceweight.villagePiecesLimit)
            {
                flag = true;
            }
        }

        return flag ? i : -1;
    }

    private static GreekComponentVillage func_75083_a(GreekComponentVillageStartPiece par0ComponentVillageStartPiece, GreekStructureVillagePieceWeight par1StructureVillagePieceWeight, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8)
    {
        Class oclass = par1StructureVillagePieceWeight.villagePieceClass;
        Object object = null;

        if (oclass == GreekComponentVillageHouse4_Garden.class)
        {
            object = GreekComponentVillageHouse4_Garden.func_74912_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (oclass == GreekComponentVillageChurch.class)
        {
            object = GreekComponentVillageChurch.func_74919_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (oclass == GreekComponentVillageHouse1.class)
        {
            object = GreekComponentVillageHouse1.func_74898_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (oclass == GreekComponentVillageWoodHut.class)
        {
            object = GreekComponentVillageWoodHut.func_74908_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (oclass == GreekComponentVillageHall.class)
        {
            object = GreekComponentVillageHall.func_74906_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (oclass == GreekComponentVillageField.class)
        {
            object = GreekComponentVillageField.func_74900_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (oclass == GreekComponentVillageField2.class)
        {
            object = GreekComponentVillageField2.func_74902_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (oclass == GreekComponentVillageHouse2.class)
        {
            object = GreekComponentVillageHouse2.func_74915_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (oclass == GreekComponentVillageHouse3.class)
        {
            object = GreekComponentVillageHouse3.func_74921_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        /*else //I don't know what this does normally, but it breaks when we make villages so I'm leaving it out for now.
        {
            object = VillagerRegistry.getVillageComponent((StructureVillagePieceWeight)par1StructureVillagePieceWeight, par0ComponentVillageStartPiece , par2List, par3Random, par4, par5, par6, par7, par8);
        }*/

        return (GreekComponentVillage)object;
    }

    /**
     * attempts to find a next Village Component to be spawned
     */
    private static GreekComponentVillage getNextVillageComponent(GreekComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        int j1 = func_75079_a(par0ComponentVillageStartPiece.structureVillageWeightedPieceList);

        if (j1 <= 0)
        {
            return null;
        }
        else
        {
            int k1 = 0;

            while (k1 < 5)
            {
                ++k1;
                int l1 = par2Random.nextInt(j1);
                Iterator iterator = par0ComponentVillageStartPiece.structureVillageWeightedPieceList.iterator();

                while (iterator.hasNext())
                {
                    GreekStructureVillagePieceWeight structurevillagepieceweight = (GreekStructureVillagePieceWeight)iterator.next();
                    l1 -= structurevillagepieceweight.villagePieceWeight;

                    if (l1 < 0)
                    {
                        if (!structurevillagepieceweight.canSpawnMoreVillagePiecesOfType(par7) || structurevillagepieceweight == par0ComponentVillageStartPiece.structVillagePieceWeight && par0ComponentVillageStartPiece.structureVillageWeightedPieceList.size() > 1)
                        {
                            break;
                        }

                        GreekComponentVillage componentvillage = func_75083_a(par0ComponentVillageStartPiece, structurevillagepieceweight, par1List, par2Random, par3, par4, par5, par6, par7);

                        if (componentvillage != null)
                        {
                            ++structurevillagepieceweight.villagePiecesSpawned;
                            par0ComponentVillageStartPiece.structVillagePieceWeight = structurevillagepieceweight;

                            if (!structurevillagepieceweight.canSpawnMoreVillagePieces())
                            {
                                par0ComponentVillageStartPiece.structureVillageWeightedPieceList.remove(structurevillagepieceweight);
                            }

                            return componentvillage;
                        }
                    }
                }
            }

            StructureBoundingBox structureboundingbox = GreekComponentVillageTorch.func_74904_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

            if (structureboundingbox != null)
            {
                return new GreekComponentVillageTorch(par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox, par6);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * attempts to find a next Structure Component to be spawned, private Village function
     */
    private static StructureComponent getNextVillageStructureComponent(GreekComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 50)
        {
            return null;
        }
        else if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112)
        {
            GreekComponentVillage componentvillage = getNextVillageComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7 + 1);

            if (componentvillage != null)
            {
                int j1 = (componentvillage.getBoundingBox().minX + componentvillage.getBoundingBox().maxX) / 2;
                int k1 = (componentvillage.getBoundingBox().minZ + componentvillage.getBoundingBox().maxZ) / 2;
                int l1 = componentvillage.getBoundingBox().maxX - componentvillage.getBoundingBox().minX;
                int i2 = componentvillage.getBoundingBox().maxZ - componentvillage.getBoundingBox().minZ;
                int j2 = l1 > i2 ? l1 : i2;

                if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(j1, k1, j2 / 2 + 4, MapGenVillage.villageSpawnBiomes))
                {
                    par1List.add(componentvillage);
                    par0ComponentVillageStartPiece.field_74932_i.add(componentvillage);
                    return componentvillage;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    private static StructureComponent getNextComponentVillagePath(GreekComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 3 + par0ComponentVillageStartPiece.terrainType)
        {
            return null;
        }
        else if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112)
        {
            StructureBoundingBox structureboundingbox = GreekComponentVillagePathGen.func_74933_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

            if (structureboundingbox != null && structureboundingbox.minY > 10)
            {
                GreekComponentVillagePathGen componentvillagepathgen = new GreekComponentVillagePathGen(par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox, par6);
                int j1 = (componentvillagepathgen.getBoundingBox().minX + componentvillagepathgen.getBoundingBox().maxX) / 2;
                int k1 = (componentvillagepathgen.getBoundingBox().minZ + componentvillagepathgen.getBoundingBox().maxZ) / 2;
                int l1 = componentvillagepathgen.getBoundingBox().maxX - componentvillagepathgen.getBoundingBox().minX;
                int i2 = componentvillagepathgen.getBoundingBox().maxZ - componentvillagepathgen.getBoundingBox().minZ;
                int j2 = l1 > i2 ? l1 : i2;

                if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(j1, k1, j2 / 2 + 4, MapGenVillage.villageSpawnBiomes))
                {
                    par1List.add(componentvillagepathgen);
                    par0ComponentVillageStartPiece.field_74930_j.add(componentvillagepathgen);
                    return componentvillagepathgen;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    /**
     * attempts to find a next Structure Component to be spawned
     */
    static StructureComponent getNextStructureComponent(GreekComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return getNextVillageStructureComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);
    }

    static StructureComponent getNextStructureComponentVillagePath(GreekComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return getNextComponentVillagePath(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);
    }
}

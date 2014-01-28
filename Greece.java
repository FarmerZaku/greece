package mod.greece;
 
import mod.greece.mobs.GreekArcher;
import mod.greece.mobs.GreekHuman;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
 
@Mod(modid="Greece", name="Ancient Greece Mod", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Greece {
	
		//---------MATERIALS---------
		public static EnumToolMaterial bronze = EnumHelper.addToolMaterial("Bronze", 2, 200, 5.0F, 2.0F, 12);
	
		//---------ITEMS---------
		private final static Item plasterBucket = new PlasterBucket(5000);
		private final static Item sard = new GenericItem(5001).setTextureName("Greece:sard").setUnlocalizedName("sard");
		private final static Item lime = new GenericItem(5002).setTextureName("Greece:lime").setUnlocalizedName("lime");
		private final static Item onyx = new GenericItem(5003).setTextureName("Greece:onyx").setUnlocalizedName("onyx");
			
		//---------BLOCKS---------
		public final static Block genericDirt = new GreekBlock(500, Material.rock, 500).setHardness(0.5f).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("genericDirt").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block sardOre = new GenericOre(501, Material.rock, Greece.sard.itemID).setTextureName("Greece:sard_ore");
		public final static Block plasteredBlock = new PlasteredBlock(502, Material.ground, Block.dirt.blockID).setHardness(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("plasteredBlock").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block limestone = new GreekBlock(504, Material.rock, 504).setHardness(0.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("limestone").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:limestone");
		public final static Block onyxOre = new GenericOre(505, Material.rock, Greece.onyx.itemID).setTextureName("Greece:onyx_ore");
		public final static Block thatch = new GreekBlock(506, Material.grass, 512).setHardness(0.2f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("thatch").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:thatch");
		public final static Block thatchSlope = new ThatchSlope(507, thatch, 0).setTextureName("Greece:thatch").setHardness(0.5f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("thatchSlope").setCreativeTab(CreativeTabs.tabBlock);
		
		//---------EVENT HANDLERS---------
		EventManager oreManager = new EventManager(); // Matthew's ore generator
		
		//---------BLOCKS - Mills---------
		public final static Block marble = new GreekOre(600, Material.rock).setTextureName(GreeceInfo.NAME.toLowerCase() + ":marble").setUnlocalizedName("marble");
		public final static Block silverOre = new GreekOre(601, Material.rock).setTextureName(GreeceInfo.NAME.toLowerCase() + ":silver_ore").setUnlocalizedName("silverOre");
		public final static Block copperOre = new GreekOre(602, Material.rock).setTextureName(GreeceInfo.NAME.toLowerCase() + ":copper_ore").setUnlocalizedName("copperOre");
		public final static Block tinOre = new GreekOre(603, Material.rock).setTextureName(GreeceInfo.NAME.toLowerCase() + ":tin_ore").setUnlocalizedName("tinOre");
		public final static Block copperTin = new GreekOre(604, Material.rock).setTextureName(GreeceInfo.NAME.toLowerCase() + ":copper_tin").setUnlocalizedName("copperTin");
		public final static Block marbleBrick = new GreekBlock(605, Material.rock)
		.setHardness(4.0f)
		.setStepSound(Block.soundStoneFootstep)
		.setUnlocalizedName("marbleBrick")
		.setCreativeTab(CreativeTabs.tabBlock)
		.setTextureName(GreeceInfo.NAME.toLowerCase() + ":marblebrick");
		
		//---------ITEMS - Mills---------
		public final static Item bronzeIngot = new BronzeIngot(6000);
		public final Item bronzeSword = new GreekSword(6001, bronze).setTextureName(GreeceInfo.NAME.toLowerCase() + ":bronze_sword").setUnlocalizedName("bronzeSword");
		public final Item spear = new GreekSword(6002, bronze).setTextureName(GreeceInfo.NAME.toLowerCase() + ":spear").setUnlocalizedName("spear");
		public final static Item chisel = new GreekItem(6003, bronze).setTextureName(GreeceInfo.NAME.toLowerCase() + ":chisel").setUnlocalizedName("chisel");
		public final static Item silverIngot = new GreekItem(6004).setTextureName(GreeceInfo.NAME.toLowerCase() + ":silver_ingot").setUnlocalizedName("silverIngot");
		public final static Item drachma = new GreekItem(6005).setTextureName(GreeceInfo.NAME.toLowerCase() + ":drachma").setUnlocalizedName("drachma");
		public final static Item marbleEye = new GreekItem(6006).setTextureName(GreeceInfo.NAME.toLowerCase() + ":marble_eye").setUnlocalizedName("marbleEye");
		
		//---------EVENT HANDLERS - Mills---------
		MillsEventManager millsOreManager = new MillsEventManager();
		CraftingHandler chiselCrafting = new CraftingHandler();
		
		// The instance of your mod that Forge uses.
        @Instance("Greece")
        public static Greece instance;
       
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="mod.greece.client.ClientProxy", serverSide="mod.greece.CommonProxy")
        public static CommonProxy proxy;
       
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
        }
       
        @EventHandler
        public void load(FMLInitializationEvent event) {
                proxy.registerRenderers();
                //GameRegistry.addShapelessRecipe(new ItemStack(Item.diamond, 64), new ItemStack(Block.dirt));
                //GameRegistry.addShapelessRecipe(new ItemStack(Item.coal, 64), new ItemStack(Item.diamond));
                //GameRegistry.addShapelessRecipe(new ItemStack(Blo), new ItemStack(Block.stone, 32));
                //GameRegistry.addRecipe(new ItemStack(Item.diamond, 64), "xy", "yx", 'x', new ItemStack(Block.dirt), 'y', new ItemStack(Block.stone));
                //GameRegistry.addSmelting(Block.dirt.blockID, new ItemStack(Item.diamond), 1);
                GameRegistry.addSmelting(limestone.blockID, new ItemStack(lime), 1);
                GameRegistry.addShapelessRecipe(new ItemStack(plasterBucket), new ItemStack(lime), new ItemStack(Block.sand), new ItemStack(Item.bucketWater));
                
                //---------REGISTER BLOCKS---------
                GameRegistry.registerBlock(genericDirt, "genericDirt");
                LanguageRegistry.addName(genericDirt, "DIRT");
                MinecraftForge.setBlockHarvestLevel(genericDirt, "shovel", 3);
                
                GameRegistry.registerBlock(plasteredBlock, "plasteredBlock");
                LanguageRegistry.addName(plasteredBlock, "Plastered Block");
                MinecraftForge.setBlockHarvestLevel(plasteredBlock, "pick", 0);
                
                GameRegistry.registerBlock(sardOre, "sardOre");
                LanguageRegistry.addName(sardOre, "Sard Ore");
                MinecraftForge.setBlockHarvestLevel(sardOre, "pickaxe", 1);
                
                GameRegistry.registerBlock(onyxOre, "onyxOre");
                LanguageRegistry.addName(onyxOre, "Onyx Ore");
                MinecraftForge.setBlockHarvestLevel(onyxOre, "pickaxe", 1);
                
                GameRegistry.registerBlock(limestone, "limestone");
                LanguageRegistry.addName(limestone, "Limestone");
                MinecraftForge.setBlockHarvestLevel(limestone, "pickaxe", 0);
                
                GameRegistry.registerBlock(thatchSlope, "thatchSlope");
                LanguageRegistry.addName(thatchSlope, "Thatch Slope");
                MinecraftForge.setBlockHarvestLevel(thatchSlope, "axe", 0);
                
                GameRegistry.registerBlock(thatch, "thatch");
                LanguageRegistry.addName(thatch, "Thatch");
                MinecraftForge.setBlockHarvestLevel(thatch, "axe", 0);
                
                //---------REGISTER ITEMS---------
                LanguageRegistry.addName(sard, "Sard");
                GameRegistry.registerItem(sard, "sard");
                LanguageRegistry.addName(onyx, "Onyx");
                GameRegistry.registerItem(onyx, "onyx");
                LanguageRegistry.addName(lime, "Lime");
                GameRegistry.registerItem(lime, "lime");
                LanguageRegistry.addName(plasterBucket, "Plaster Bucket");
                GameRegistry.registerItem(plasterBucket, "plasterBucket");
                
                // Misc
        		MinecraftForge.EVENT_BUS.register(new EventHandlingClass());
                GameRegistry.registerWorldGenerator(oreManager);
                proxy.registerRenderers();
                
                registerEntity(GreekHuman.class, "Bandit", 0xefaf00, 0xaa00aa);
                LanguageRegistry.instance().addStringLocalization("entity.GreekHuman.name", "Bandit");
                
                registerEntity(GreekArcher.class, "ArcherBandit", 0xe00abcd, 0x0abcd0);
                LanguageRegistry.instance().addStringLocalization("entity.GreekArcher.name", "ArcherBandit");
                
                //EntityRegistry.registerModEntity(GreekHuman.class, "Bandit", 20, this, 40, 3, true);
                //EntityRegistry.addSpawn(GreekHuman.class, 50, 5, 10, EnumCreatureType.monster, BiomeGenBase.beach, BiomeGenBase.extremeHills,
                //        BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills,
                //        BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.swampland);
                
                //---------REGISTER BLOCKS - Mills---------
                // MARBLE
                GameRegistry.registerBlock(marble, "marble");
                LanguageRegistry.addName(marble, "Marble");
                MinecraftForge.setBlockHarvestLevel(marble, "pickaxe", 1);              
                
                // MARBLE BRICK
                GameRegistry.registerBlock(marbleBrick, "marbleBrick");
                LanguageRegistry.addName(marbleBrick, "Marble Blocks");
                MinecraftForge.setBlockHarvestLevel(marble, "pickaxe", 2);
                
                ItemStack marbleBrickStack = new ItemStack(marbleBrick, 4);
                GameRegistry.addRecipe(marbleBrickStack, "y ", "xx", "xx",
                		'x', marble, 'y', new ItemStack(chisel, 1, OreDictionary.WILDCARD_VALUE));
                // END MARBLE BRICK
                
                // SILVER
                GameRegistry.registerBlock(silverOre, "silverOre");
                LanguageRegistry.addName(silverOre, "Silver Ore");
                MinecraftForge.setBlockHarvestLevel(silverOre, "pickaxe", 1);
                
                // COPPER
                GameRegistry.registerBlock(copperOre, "copperOre");
                LanguageRegistry.addName(copperOre, "Copper Ore");
                MinecraftForge.setBlockHarvestLevel(copperOre, "pickaxe", 1);               
                
                // TIN
                GameRegistry.registerBlock(tinOre, "tinOre");
                LanguageRegistry.addName(tinOre, "Tin Ore");
                MinecraftForge.setBlockHarvestLevel(tinOre, "pickaxe", 1);               
                
                // COPPER WITH TIN
                GameRegistry.registerBlock(copperTin, "copperTin");
                LanguageRegistry.addName(copperTin, "Copper and Tin");
                MinecraftForge.setBlockHarvestLevel(copperTin, "pickaxe", 1);  
                
                ItemStack copperTinStack = new ItemStack(copperTin, 2);
                GameRegistry.addShapelessRecipe(copperTinStack, copperOre, tinOre);
       
                //---------REGISTER ITEMS - Mills---------
                // BRONZE INGOT
                GameRegistry.registerItem(bronzeIngot, "bronzeIngot");
                LanguageRegistry.addName(bronzeIngot, "Bronze Ingot");
                ItemStack bronzeIngotStack = new ItemStack(bronzeIngot);
                GameRegistry.addSmelting(copperTin.blockID, bronzeIngotStack, 1);
                
                // BRONZE SWORD
                GameRegistry.registerItem(bronzeSword, "bronzeSword");
                LanguageRegistry.addName(bronzeSword, "Bronze Xiphos");
                //ItemStack bronzeSwordStack = new ItemStack(bronzeSword);
                GameRegistry.addRecipe(new ItemStack(bronzeSword), "x", "x", "y",
                		'x', bronzeIngot, 'y', Item.stick);
                
                // BRONZE SPEAR
                GameRegistry.registerItem(spear, "spear");
                LanguageRegistry.addName(spear, "Doru");
                GameRegistry.addRecipe(new ItemStack(spear), "  x", " y ", "z  ",
                		'x', Item.ingotIron, 'y', Item.stick, 'z', bronzeIngot);
                
                // CHISEL
                GameRegistry.registerItem(chisel, "chisel");
                LanguageRegistry.addName(chisel, "Chisel");
                ItemStack chiselStack = new ItemStack(chisel);
                GameRegistry.addRecipe(chiselStack, "x", "y",
                		'x', bronzeIngot, 'y', Item.stick);
                
                // SILVER INGOT
                GameRegistry.registerItem(silverIngot, "silverIngot");
                LanguageRegistry.addName(silverIngot, "Silver Ingot");
                //ItemStack silverIngotStack = new ItemStack(silverIngot);
                GameRegistry.addSmelting(silverOre.blockID, new ItemStack(silverIngot), 1);
                
                // DRACHMA
                GameRegistry.registerItem(drachma, "drachma");
                LanguageRegistry.addName(drachma, "Drachma");
                //ItemStack drachmaStack = new ItemStack(drachma);
                GameRegistry.addRecipe(new ItemStack(drachma), "x", "x",
                		'x', silverIngot);
                
                // MARBLE EYE
                GameRegistry.registerItem(marbleEye, "marbleEye");
                LanguageRegistry.addName(marbleEye, "Ship's Eye");
                GameRegistry.addRecipe(new ItemStack(marbleEye), "a ", "bb", "cd",
                		'a', new ItemStack(chisel, 1, OreDictionary.WILDCARD_VALUE), 'b', marble, 'c', new ItemStack(Item.dyePowder, 1, 1), 'd', new ItemStack(Item.dyePowder, 1, 11));
                
                //---------MISC - Mills---------
                GameRegistry.registerWorldGenerator(millsOreManager);
                GameRegistry.registerCraftingHandler(chiselCrafting);
                
                GameRegistry.removeBiome(BiomeGenBase.extremeHills);
                GameRegistry.removeBiome(BiomeGenBase.frozenOcean);
                GameRegistry.removeBiome(BiomeGenBase.frozenRiver);
                GameRegistry.removeBiome(BiomeGenBase.iceMountains);
                GameRegistry.removeBiome(BiomeGenBase.icePlains);
                GameRegistry.removeBiome(BiomeGenBase.jungle);
                GameRegistry.removeBiome(BiomeGenBase.jungleHills);
                GameRegistry.removeBiome(BiomeGenBase.mushroomIsland);
                GameRegistry.removeBiome(BiomeGenBase.mushroomIslandShore);
                GameRegistry.removeBiome(BiomeGenBase.taiga);
                GameRegistry.removeBiome(BiomeGenBase.taigaHills);
                
        }
       
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
       
        public void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
            int id = EntityRegistry.findGlobalUniqueEntityId();

            EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
            EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
        }

	    public void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
	            if (spawnProb > 0) {
	                    EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
	            }
	    }
}
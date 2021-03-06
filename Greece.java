package mod.greece;
 
import static net.minecraft.world.biome.BiomeGenBase.forest;
import static net.minecraft.world.biome.BiomeGenBase.forestHills;
import static net.minecraft.world.biome.BiomeGenBase.plains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import mod.greece.mobs.GreekArcher;
import mod.greece.mobs.GreekGuard;
import mod.greece.mobs.GreekHuman;
import mod.greece.village.GreekMapGenVillage;
import mod.greece.village.GreekStructureVillagePieces;
import mod.greece.village.GreekStructureVillageStart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="Greece", name="Ancient Greece Mod", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Greece {
	
		//---------MATERIALS---------
		public static EnumToolMaterial bronze = EnumHelper.addToolMaterial("Bronze", 2, 200, 5.0F, 2.0F, 12);
		public static EnumToolMaterial clay = EnumHelper.addToolMaterial("Clay", 1, 100, 3.0F, 0.5F, 12);
		public static EnumToolMaterial copper = EnumHelper.addToolMaterial("Copper", 1, 100, 3.0F, 1.0F, 18);
		public static EnumToolMaterial badWood = EnumHelper.addToolMaterial("badWood", 0, 59, 0.5F, 0.0F, 15);
	
		//---------ITEMS---------
		public final static Item plasterBucket = new PlasterBucket(5000).setMaxStackSize(1).setNoRepair();
		public final static Item sard = new GenericItem(5001).setTextureName("Greece:sard").setUnlocalizedName("sard");
		public final static Item lime = new GenericItem(5002).setTextureName("Greece:lime").setUnlocalizedName("lime");
		public final static Item onyx = new GenericItem(5003).setTextureName("Greece:onyx").setUnlocalizedName("onyx");
		public final static Item shieldWood = new GreekShield(5004, EnumToolMaterial.WOOD).setTextureName("Greece:shield_wood").setUnlocalizedName("shieldWood");
		public final static Item bronzePick = new GreekPickaxe(5005, bronze).setTextureName("Greece:bronzePick").setUnlocalizedName("bronzePick");
		public final static Item bronzeAxe = new GreekAxe(5006, bronze, 0.08f, 2, 2, 8, 20).setTextureName("Greece:bronzeAxe").setUnlocalizedName("bronzeAxe");
		public final static Item bronzeShovel = new GreekShovel(5007, bronze).setTextureName("Greece:bronzeShovel").setUnlocalizedName("bronzeShovel");
		public final static Item copperPick = new GreekPickaxe(5008, copper).setTextureName("Greece:copperPick").setUnlocalizedName("copperPick");
		public final static Item copperAxe = new GreekAxe(5009, copper, 0.06f, 2, 2, 7, 20).setTextureName("Greece:copperAxe").setUnlocalizedName("copperAxe");
		public final static Item copperShovel = new GreekShovel(5010, copper).setTextureName("Greece:copperShovel").setUnlocalizedName("copperShovel");
		public final static Item tinIngot = new GenericItem(5011).setTextureName("Greece:tinIngot").setUnlocalizedName("tinIngot");
		public final static Item copperIngot = new GenericItem(5012).setTextureName("Greece:copperIngot").setUnlocalizedName("copperIngot");
		public final static Item straw = new GenericItem(5013).setTextureName("Greece:straw").setUnlocalizedName("straw");
		public final static Item basketEmpty = new GenericItem(5014).setTextureName("Greece:basket_empty").setUnlocalizedName("basketEmpty").setMaxStackSize(16);
		public final static Item basketGrain = new GenericItem(5015).setTextureName("Greece:basket_grain").setUnlocalizedName("basketGrain").setMaxStackSize(1);
		public final static Item basketFlour = new GenericItem(5016).setTextureName("Greece:basket_flour").setUnlocalizedName("basketFlour").setMaxStackSize(1);
		public final static Item dough = new GenericItem(5017).setTextureName("Greece:dough").setUnlocalizedName("dough");
		public final static Item amphoraGrain = new GreekItem(5018, clay).setTextureName(GreeceInfo.NAME.toLowerCase() + ":amphora_grain").setUnlocalizedName("amphoraGrain");
		public final static Item amphoraFlour = new GreekItem(5019, clay).setTextureName(GreeceInfo.NAME.toLowerCase() + ":amphora_flour").setUnlocalizedName("amphoraFlour");
		public final static Item spearCopper = new GreekSword(5020, copper, 0.03f, 3.1, 1, 6, 14).setTextureName(GreeceInfo.NAME.toLowerCase() + ":spear_copper").setUnlocalizedName("spearCopper");
		public final static Item shieldBronze = new GreekShield(5021, bronze).setTextureName("Greece:shield_bronze").setUnlocalizedName("shieldBronze");
		public final static Item javelinStone = new GreekWeaponThrowable(5022, EnumToolMaterial.STONE, 0.03f, 3.1, 1, 4, 10).setTextureName(GreeceInfo.NAME.toLowerCase() + ":javelin_stone").setUnlocalizedName("javelinStone");
		public final static Item javelinCopper = new GreekWeaponThrowable(5023, copper, 0.03f, 3.1, 1, 5, 10).setTextureName(GreeceInfo.NAME.toLowerCase() + ":javelin_copper").setUnlocalizedName("javelinCopper");
		public final static Item javelinBronze = new GreekWeaponThrowable(5024, bronze, 0.03f, 3.1, 1, 6, 10).setTextureName(GreeceInfo.NAME.toLowerCase() + ":javelin_bronze").setUnlocalizedName("javelinBronze");
		public final static Item badWoodPickaxe = new GreekPickaxe(5025, badWood).setTextureName("Greece:woodPick").setUnlocalizedName("badWoodPickaxe");
		public final static Item copperWashed = new GenericItem(5026).setTextureName("Greece:copperWashed").setUnlocalizedName("copperWashed");
		public final static Item tinWashed = new GenericItem(5027).setTextureName("Greece:tinWashed").setUnlocalizedName("tinWashed");
		public final static Item silverWashed = new GenericItem(5028).setTextureName("Greece:silverWashed").setUnlocalizedName("silverWashed");
		public final static Item bronzeWashed = new GenericItem(5029).setTextureName("Greece:bronzeWashed").setUnlocalizedName("bronzeWashed");
		public final static Item copperCrushed = new GreekWashable(5030, copperWashed.itemID).setTextureName("Greece:copperCrushed").setUnlocalizedName("copperCrushed");
		public final static Item tinCrushed = new GreekWashable(5031, tinWashed.itemID).setTextureName("Greece:tinCrushed").setUnlocalizedName("tinCrushed");
		public final static Item silverCrushed = new GreekWashable(5032, silverWashed.itemID).setTextureName("Greece:silverCrushed").setUnlocalizedName("silverCrushed");
		public static Item grapes = new GreekFood(5033, 4, 1f, false).setTextureName(GreeceInfo.NAME.toLowerCase() + ":grapes").setUnlocalizedName("grapes");
		
		//---------BLOCKS - Matthew ---------
		public final static Block sardOre = new GreekOre(501, Material.rock, Greece.sard.itemID).setTextureName("Greece:sard_ore");
		public final static Block plasteredBlock = new PlasteredBlock(502, Material.ground, Block.dirt.blockID).setHardness(1.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("plasteredBlock").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block limestone = new GreekBlock(504, Material.rock, 504).setHardness(1.3f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("limestone").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:limestone");
		public final static Block onyxOre = new GreekOre(505, Material.rock, Greece.onyx.itemID).setTextureName("Greece:onyx_ore");
		public final static Block thatch = new GreekBlock(506, Material.grass, 506).setHardness(0.5f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("thatch").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:thatch");
		public final static Block thatchSlope = new ThatchSlope(507, thatch, 0).setTextureName("Greece:thatch").setHardness(0.5f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("thatchSlope").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block granite = new GreekBlock(508, Material.rock, 508).setHardness(2f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("granite").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:granite");
		public final static Block tinBlock = new GreekBlock(509, Material.iron, 509).setHardness(4f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("tinBlock").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:tinBlock");
		public final static Block copperBlock = new GreekBlock(510, Material.iron, 510).setHardness(5f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("copperBlock").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:copperBlock");
		public final static Block bronzeBlock = new GreekBlock(511, Material.iron, 511).setHardness(5f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("bronzeBlock").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:bronzeBlock");
		public final static Block silverBlock = new GreekBlock(512, Material.iron, 512).setHardness(5f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("silverBlock").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:silverBlock");
		public final static Block mudbrick = new GreekBlock(513, Material.rock, 513).setHardness(1.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("mudbrick").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:mudbrick");
		public final static Block mudbrickWet = new GreekAgingBlock(514, Material.clay, 513, 14, true, true).setHardness(0.5f).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("mudbrickWet").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:mudbrick_wet");
		public final static Block greekFire = new GreekFire(517).setUnlocalizedName("greekFire").setTextureName("Greece:greekFire");
		public final static Block ash = new GreekAsh(518).setUnlocalizedName("ash").setTextureName("Greece:greek_ash");
		public final static Block grapevine = new GreekGrapevine(519).setUnlocalizedName("grapevine").setTextureName("Greece:grapevine");
		
		//---------EVENT HANDLERS---------
		OreManager oreManager = new OreManager(); // Matthew's ore generator
		public static int vineBlockModelID;
		
		//---------FOOD - Mills---------
		public static Item olives = new GreekFood(6500, 2, 0.5f, false).setTextureName(GreeceInfo.NAME.toLowerCase() + ":olives").setUnlocalizedName("olives");
		public static Item pancake = new GreekFood(6501, 2, 4, false).setTextureName(GreeceInfo.NAME.toLowerCase() + ":pancake").setUnlocalizedName("pancake");
		
		//---------BLOCKS - Mills---------
		public final static Block marble = new GreekOre(600, Material.rock, 600).setTextureName(GreeceInfo.NAME.toLowerCase() + ":marble").setUnlocalizedName("marble");
		public final static Block silverOre = new GreekOre(601, Material.rock, 601).setTextureName(GreeceInfo.NAME.toLowerCase() + ":silver_ore").setUnlocalizedName("silverOre");
		public final static Block copperOre = new GreekOre(602, Material.rock, 602).setTextureName(GreeceInfo.NAME.toLowerCase() + ":copper_ore").setUnlocalizedName("copperOre");
		public final static Block tinOre = new GreekOre(603, Material.rock, 603).setTextureName(GreeceInfo.NAME.toLowerCase() + ":tin_ore").setUnlocalizedName("tinOre");
		public final static Block copperTin = new GreekOre(604, Material.rock, 604).setTextureName(GreeceInfo.NAME.toLowerCase() + ":copper_tin").setUnlocalizedName("copperTin");
		public final static Block marbleBrick = new GreekBlock(605, Material.rock).setHardness(4.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("marbleBrick").setTextureName(GreeceInfo.NAME.toLowerCase() + ":marblebrick");
		public final static Block oliveLeaves = new GreekBlockLeaves(606, 0).setUnlocalizedName("oliveLeaves").setTextureName(GreeceInfo.NAME.toLowerCase() + ":leaves_olive")
				.setHardness(1f)
				.setLightOpacity(100)
				.setStepSound(Block.soundGrassFootstep);
		public final static Block oliveLeavesRipe = new GreekBlockLeaves(607, 1).setUnlocalizedName("oliveLeavesRipe").setTextureName(GreeceInfo.NAME.toLowerCase() + ":leaves_olive_ripe")
				.setHardness(1f)
				.setLightOpacity(10)
				.setStepSound(Block.soundGrassFootstep);
		public final static Block oliveWood = new MultiTextureBlock(608, Material.wood, "logs").setUnlocalizedName("oliveWood");
		public final static Block olivePress = new GreekBlockPress(609, Material.rock, 609, olives.itemID).setHardness(4.0f).setUnlocalizedName("olivePress");
		public final static Block roofTiles = new GreekBlock(610, Material.clay, 506).setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("roofTiles").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:roof_tiles");
		public final static Block roofTilesSlope = new ThatchSlope(611, roofTiles, 0).setTextureName("Greece:roof_tiles").setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("roofTilesSlope").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block roofTilesSlab = new GreekBlockSlab(612, false, Material.clay).setTextureName("Greece:roof_tiles").setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("roofTilesSlab");
		public final static Block roofTilesSlabDouble = new GreekBlockSlab(613, true, Material.clay).setTextureName("Greece:roof_tiles").setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("roofTilesSlabDouble");
		
		//---------ITEMS - Mills---------
		public final static Item bronzeIngot = new BronzeIngot(6000);
		public final static Item bronzeSword = new GreekSword(6001, bronze, 0.07f, 2.5, 1, 9, 15)
			.setTextureName(GreeceInfo.NAME.toLowerCase() + ":bronze_sword").setUnlocalizedName("bronzeSword");
		public final static Item spear = new GreekWeaponThrowable(6002, bronze, 0.04f, 3.1, 1, 7, 15)
			.setTextureName(GreeceInfo.NAME.toLowerCase() + ":spear").setUnlocalizedName("spear");
		public final static Item chisel = new GreekTool(6003, bronze, 12).setTextureName(GreeceInfo.NAME.toLowerCase() + ":chisel").setUnlocalizedName("chisel");
		public final static Item silverIngot = new GreekItem(6004).setTextureName(GreeceInfo.NAME.toLowerCase() + ":silver_ingot").setUnlocalizedName("silverIngot");
		public final static Item drachma = new GreekItemCoin(6005, "drachma");
		public final static Item obol = new GreekItemCoin(6006, "obol");
		public final static Item marbleEye = new GreekItem(6007).setTextureName(GreeceInfo.NAME.toLowerCase() + ":marble_eye").setUnlocalizedName("marbleEye");
		public final static Item unfiredBakingCover = new GreekItem(6008).setTextureName(GreeceInfo.NAME.toLowerCase() + ":baking_cover_unfired").setUnlocalizedName("unfiredBakingCover");
		public final static Item bakingCover = new GreekTool(6009, clay, 15).setTextureName(GreeceInfo.NAME.toLowerCase() + ":baking_cover").setUnlocalizedName("bakingCover");
		public final static Item unfiredAmphora = new GreekItem(6010, clay).setTextureName(GreeceInfo.NAME.toLowerCase() + ":amphora_unfired").setUnlocalizedName("unfiredAmphora");
		public final static Item amphora = new GreekItem(6011, clay).setTextureName(GreeceInfo.NAME.toLowerCase() + ":amphora_empty").setUnlocalizedName("amphora");
		public final static Item oliveOil = new GreekItem(6012, clay).setTextureName(GreeceInfo.NAME.toLowerCase() + ":amphora_oil").setUnlocalizedName("oliveOil");
		public final static Item wine = new GreekItem(6013, clay).setTextureName(GreeceInfo.NAME.toLowerCase() + ":amphora_wine").setUnlocalizedName("wine");
		public final static Item roofTile = new GreekItem(6014, clay).setTextureName(GreeceInfo.NAME.toLowerCase() + ":roof_tile").setUnlocalizedName("roof_tile");
		public final static Item fryingPanUnfired = new GreekItem(6015, clay).setTextureName(GreeceInfo.NAME.toLowerCase() + ":frying_pan_unfired").setUnlocalizedName("fryingPanUnfired");
		public final static Item fryingPanCeramic = new GreekTool(6016, clay, 20).setTextureName(GreeceInfo.NAME.toLowerCase() + ":frying_pan_ceramic").setUnlocalizedName("fryingPanCeramic");
		public final static Item fryingPanBronze = new GreekTool(6017, bronze, 10).setTextureName(GreeceInfo.NAME.toLowerCase() + ":frying_pan_bronze").setUnlocalizedName("fryingPanBronze");
		public final static Item hotCoals = new GreekItem(6018).setTextureName(GreeceInfo.NAME.toLowerCase() + ":hot_coals").setUnlocalizedName("hotCoals");
		public final static Item triobol = new GreekItemCoin(6019, "triobol").setTextureName(GreeceInfo.NAME.toLowerCase() + ":coinTriobol").setUnlocalizedName("triobol");
		
		
		//---------EVENT HANDLERS - Mills---------
		CraftingHandler chiselCrafting = new CraftingHandler();
		TreeManager treeManager = new TreeManager();
		
		// The instance of your mod that Forge uses.
        @Instance("Greece")
        public static Greece instance;
       
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="mod.greece.client.ClientProxy", serverSide="mod.greece.CommonProxy")
        public static CommonProxy proxy;
       
        //---------------BIOMES-----------------
		public static BiomeGenBase limeCliffsBiome;
		public static BiomeGenBase tinIslesBiome;
		public static BiomeGenBase korinthiaBiome;
		public static BiomeGenBase graniteMountainsBiome;
		
		//------------ BLOCKS - Matthew2 ------- these need the itemIDs from above-defined blocks/items
		public final static Block quern = new GreekQuern(515, Material.rock,
				new int[] {basketGrain.itemID, amphoraGrain.itemID},
				new int[] {0, 0},
				new int[] {1, 1},
				new int[] {basketFlour.itemID, amphoraFlour.itemID}, 0).setUnlocalizedName("quern");
		public final static Block crusher = new GreekQuern(516, Material.rock,
				new int[] {silverOre.blockID, copperOre.blockID, tinOre.blockID},
				new int[] {0, 0, 0},
				new int[] {1, 1, 1},
				new int[] {silverCrushed.itemID, copperCrushed.itemID, tinCrushed.itemID}, 1).setUnlocalizedName("crusher");
		
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {        	
        	// iterate through all the villager types and add their new trades
        	//for (int i = 0; i < 7; ++i) {
        	//	VillagerRegistry.instance().registerVillageTradeHandler(i, new TradeHandler());
        	//}
        	MapGenStructureIO.func_143034_b(GreekStructureVillageStart.class, "GreekVillage");
        	GreekStructureVillagePieces.func_143016_a();
            GameRegistry.registerWorldGenerator(new GreekMapGenVillage());
    		MinecraftForge.TERRAIN_GEN_BUS.register(new GreekEventHandler());
        }
       
        @EventHandler
        public void load(FMLInitializationEvent event) {
                proxy.registerRenderers();
                
                //---------REGISTER BLOCKS--------- 
                //PLASTER
                GameRegistry.registerBlock(plasteredBlock, "plasteredBlock");
                LanguageRegistry.addName(plasteredBlock, "Plastered Block");
                MinecraftForge.setBlockHarvestLevel(plasteredBlock, "pick", 0);
                
                //SARD
                GameRegistry.registerBlock(sardOre, "sardOre");
                LanguageRegistry.addName(sardOre, "Sard Ore");
                MinecraftForge.setBlockHarvestLevel(sardOre, "pickaxe", 1);
                
                //ONYX
                GameRegistry.registerBlock(onyxOre, "onyxOre");
                LanguageRegistry.addName(onyxOre, "Onyx Ore");
                MinecraftForge.setBlockHarvestLevel(onyxOre, "pickaxe", 1);
                
                //LIMESTONE
                GameRegistry.registerBlock(limestone, "limestone");
                LanguageRegistry.addName(limestone, "Limestone");
                MinecraftForge.setBlockHarvestLevel(limestone, "pickaxe", 0);
                GameRegistry.addSmelting(limestone.blockID, new ItemStack(lime), 1);
                GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone), new ItemStack(limestone));
                
                //GRANITE
                GameRegistry.registerBlock(granite, "granite");
                LanguageRegistry.addName(granite, "Granite");
                MinecraftForge.setBlockHarvestLevel(granite, "pickaxe", 2);
                GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone), new ItemStack(granite));
                
                //TIN BLOCK
                GameRegistry.registerBlock(tinBlock, "tinBlock");
                LanguageRegistry.addName(tinBlock, "Tin Block");
                MinecraftForge.setBlockHarvestLevel(tinBlock, "pickaxe", 1);
                GameRegistry.addRecipe(new ItemStack(tinBlock), "xxx", "xxx", "xxx",
                		'x', tinIngot);
                GameRegistry.addShapelessRecipe(new ItemStack(tinIngot, 9), new ItemStack(tinBlock));
                
                //COPPER BLOCK
                GameRegistry.registerBlock(copperBlock, "copperBlock");
                LanguageRegistry.addName(copperBlock, "Copper Block");
                MinecraftForge.setBlockHarvestLevel(copperBlock, "pickaxe", 1);
                GameRegistry.addRecipe(new ItemStack(copperBlock), "xxx", "xxx", "xxx",
                		'x', copperIngot);
                GameRegistry.addShapelessRecipe(new ItemStack(copperIngot, 9), new ItemStack(copperBlock));
                
                //BRONZE BLOCK
                GameRegistry.registerBlock(bronzeBlock, "bronzeBlock");
                LanguageRegistry.addName(bronzeBlock, "Bronze Block");
                MinecraftForge.setBlockHarvestLevel(bronzeBlock, "pickaxe", 2);
                GameRegistry.addRecipe(new ItemStack(bronzeBlock), "xxx", "xxx", "xxx",
                		'x', bronzeIngot);
                GameRegistry.addShapelessRecipe(new ItemStack(bronzeIngot, 9), new ItemStack(bronzeBlock));
                
                //SILVER BLOCK
                GameRegistry.registerBlock(silverBlock, "silverBlock");
                LanguageRegistry.addName(silverBlock, "Silver Block");
                MinecraftForge.setBlockHarvestLevel(silverBlock, "pickaxe", 1);
                GameRegistry.addRecipe(new ItemStack(silverBlock), "xxx", "xxx", "xxx",
                		'x', silverIngot);
                GameRegistry.addShapelessRecipe(new ItemStack(silverIngot, 9), new ItemStack(silverBlock));
                
                //THATCH SLOPE
                GameRegistry.registerBlock(thatchSlope, "thatchSlope");
                LanguageRegistry.addName(thatchSlope, "Thatch Slope");
                MinecraftForge.setBlockHarvestLevel(thatchSlope, "axe", 0);
                GameRegistry.addRecipe(new ItemStack(thatchSlope, 4), "x  ", "yx ", "yyx",
                		'x', straw, 'y', Item.stick);
                GameRegistry.addRecipe(new ItemStack(thatchSlope, 4), "x  ", "yx ", "yyx",
                		'x', Item.reed, 'y', Item.stick);
                thatchSlope.setBurnProperties(thatchSlope.blockID, 5, 5);
                
                //THATCH
                GameRegistry.registerBlock(thatch, "thatch");
                LanguageRegistry.addName(thatch, "Thatch");
                MinecraftForge.setBlockHarvestLevel(thatch, "axe", 0);
                GameRegistry.addRecipe(new ItemStack(thatch, 4), "xxx", "yyy",
                		'x', straw, 'y', Item.stick);
                GameRegistry.addRecipe(new ItemStack(thatch, 4), "xxx", "yyy",
                		'x', Item.reed, 'y', Item.stick);
                thatchSlope.setBurnProperties(thatch.blockID, 5, 5);
                
                //MUDBRICK
                GameRegistry.registerBlock(mudbrick, "mudbrick");
                LanguageRegistry.addName(mudbrick, "Mudbrick");
                MinecraftForge.setBlockHarvestLevel(mudbrick, "pick", 0);
                
                //WET MUDBRICK
                GameRegistry.registerBlock(mudbrickWet, "mudbrickWet");
                LanguageRegistry.addName(mudbrickWet, "Wet Mudbrick");
                MinecraftForge.setBlockHarvestLevel(mudbrickWet, "shovel", 0);
                GameRegistry.addShapelessRecipe(new ItemStack(mudbrickWet, 9),
                		new ItemStack(Item.bucketWater), new ItemStack(straw), new ItemStack(straw), new ItemStack(straw),
                		new ItemStack(Block.dirt), new ItemStack(Block.dirt), new ItemStack(Block.dirt), new ItemStack(Block.dirt), new ItemStack(Block.dirt));
                GameRegistry.addShapelessRecipe(new ItemStack(mudbrickWet, 9),
                		new ItemStack(Item.bucketWater), new ItemStack(Item.reed), new ItemStack(Item.reed),
                		new ItemStack(Block.dirt), new ItemStack(Block.dirt), new ItemStack(Block.dirt), new ItemStack(Block.dirt), new ItemStack(Block.dirt), new ItemStack(Block.dirt));
                GameRegistry.addSmelting(mudbrickWet.blockID, new ItemStack(mudbrick), 1.0f);
                
                //QUERN
                GameRegistry.registerBlock(quern, "quern");
                LanguageRegistry.addName(quern, "Quern");
                MinecraftForge.setBlockHarvestLevel(quern, "pick", 0);
                GameRegistry.addRecipe(new ItemStack(quern), "xyx", "yyy",
                		'x', Item.stick, 'y', Block.cobblestone);
                
                //CRUSHER
                GameRegistry.registerBlock(crusher, "crusher");
                LanguageRegistry.addName(crusher, "Ore Grinder");
                MinecraftForge.setBlockHarvestLevel(crusher, "pick", 0);
                GameRegistry.addRecipe(new ItemStack(crusher), "xyx", "yyy", "yyy",
                		'x', Item.stick, 'y', Block.cobblestone);
                
                //GREEK FIRE
                GameRegistry.registerBlock(greekFire, "greekFire");
                LanguageRegistry.addName(greekFire, "Greek Fire");
                
                //ASH
                GameRegistry.registerBlock(ash, "ash");
                LanguageRegistry.addName(ash, "Ash");
                
                //GRAPEVINE
                GameRegistry.registerBlock(grapevine, "grapevine");
                LanguageRegistry.addName(grapevine, "Grapevine");
                
                //---------REGISTER ITEMS---------
                //SARD ITEM
                LanguageRegistry.addName(sard, "Sard");
                GameRegistry.registerItem(sard, "sard");
                
                //ONYX ITEM
                LanguageRegistry.addName(onyx, "Onyx");
                GameRegistry.registerItem(onyx, "onyx");
                
                //LIME ITEM
                LanguageRegistry.addName(lime, "Lime");
                GameRegistry.registerItem(lime, "lime");
                
                //TIN INGOT
                LanguageRegistry.addName(tinIngot, "Tin Ingot");
                GameRegistry.registerItem(tinIngot, "tinIngot");
                GameRegistry.addSmelting(tinWashed.itemID, new ItemStack(tinIngot), 1);
                GameRegistry.addRecipe(new ItemStack(Item.bucketEmpty), "x x", " x ",
                		'x', tinIngot);
                
                //COPPER INGOT
                LanguageRegistry.addName(copperIngot, "Copper Ingot");
                GameRegistry.registerItem(copperIngot, "copperIngot");
                GameRegistry.addSmelting(copperWashed.itemID, new ItemStack(copperIngot), 1);
                GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), " x ", " y ", " z ",
                		'x', copperIngot, 'y', Item.stick, 'z', Item.feather);
                GameRegistry.addRecipe(new ItemStack(Item.shears), " x ", "x ",
                		'x', copperIngot);
                
                //CRUSHED TIN
                LanguageRegistry.addName(tinCrushed, "Crushed Tin");
                GameRegistry.registerItem(tinCrushed, "tinCrushed");
                
                //CRUSHED COPPER
                LanguageRegistry.addName(copperCrushed, "Crushed Copper");
                GameRegistry.registerItem(copperCrushed, "copperCrushed");
                
                //CRUSHED SILVER
                LanguageRegistry.addName(silverCrushed, "Crushed Silver");
                GameRegistry.registerItem(silverCrushed, "silverCrushed");
                
                //WASHED TIN
                LanguageRegistry.addName(tinWashed, "Refined Tin");
                GameRegistry.registerItem(tinWashed, "tinWashed");
                
                //WASHED COPPER
                LanguageRegistry.addName(copperWashed, "Refined Copper");
                GameRegistry.registerItem(copperWashed, "copperWashed");
                
                //WASHED SILVER
                LanguageRegistry.addName(silverWashed, "Refined Silver");
                GameRegistry.registerItem(silverWashed, "silverWashed");
                
                //WASHED BRONZE
                LanguageRegistry.addName(bronzeWashed, "Refined Bronze");
                GameRegistry.registerItem(bronzeWashed, "bronzeWashed");
                //1 refined tin + 8 refined copper = 9 refined bronze
                GameRegistry.addShapelessRecipe(new ItemStack(bronzeWashed, 9), new ItemStack(tinWashed), new ItemStack(copperWashed), new ItemStack(copperWashed), new ItemStack(copperWashed), new ItemStack(copperWashed), new ItemStack(copperWashed), new ItemStack(copperWashed), new ItemStack(copperWashed), new ItemStack(copperWashed));
                
                //BAD WOOD PICKAXE
                LanguageRegistry.addName(badWoodPickaxe, "Wooden Pickaxe");
                GameRegistry.registerItem(badWoodPickaxe, "badWoodPickaxe");
                GameRegistry.addRecipe(new ItemStack(badWoodPickaxe), "xxx", " y ", " y ",
                		'x', Block.planks, 'y', Item.stick);
                
                //COPPER PICKAXE
                LanguageRegistry.addName(copperPick, "Copper Pickaxe");
                GameRegistry.registerItem(copperPick, "copperPick");
                GameRegistry.addRecipe(new ItemStack(copperPick), "xxx", " y ", " y ",
                		'x', copperIngot, 'y', Item.stick);
                
                //COPPER AXE
                LanguageRegistry.addName(copperAxe, "Copper Axe");
                GameRegistry.registerItem(copperAxe, "copperAxe");
                GameRegistry.addRecipe(new ItemStack(copperAxe), " xx", " yx", " y ",
                		'x', copperIngot, 'y', Item.stick);
                
                //COPPER SHOVEL
                LanguageRegistry.addName(copperShovel, "Copper Shovel");
                GameRegistry.registerItem(copperShovel, "copperShovel");
                GameRegistry.addRecipe(new ItemStack(copperShovel), " x ", " y ", " y ",
                		'x', copperIngot, 'y', Item.stick);
                
                //BRONZE PICKAXE
                LanguageRegistry.addName(bronzePick, "Bronze Pickaxe");
                GameRegistry.registerItem(bronzePick, "bronzePick");
                GameRegistry.addRecipe(new ItemStack(bronzePick), "xxx", " y ", " y ",
                		'x', bronzeIngot, 'y', Item.stick);
                
                //BRONZE AXE
                LanguageRegistry.addName(bronzeAxe, "Bronze Axe");
                GameRegistry.registerItem(bronzeAxe, "bronzeAxe");
                GameRegistry.addRecipe(new ItemStack(bronzeAxe), " xx", " yx", " y ",
                		'x', bronzeIngot, 'y', Item.stick);
                
                //BRONZE SHOVEL
                LanguageRegistry.addName(bronzeShovel, "Bronze Shovel");
                GameRegistry.registerItem(bronzeShovel, "bronzeShovel");
                GameRegistry.addRecipe(new ItemStack(bronzeShovel), " x ", " y ", " y ",
                		'x', bronzeIngot, 'y', Item.stick);
                
                //PLASTER BUCKET
                LanguageRegistry.addName(plasterBucket, "Plaster Bucket");
                GameRegistry.registerItem(plasterBucket, "plasterBucket");
                GameRegistry.addShapelessRecipe(new ItemStack(plasterBucket), new ItemStack(lime), new ItemStack(Block.sand), new ItemStack(Item.bucketWater));
                
                //SHIELD
                LanguageRegistry.addName(shieldWood, "Shield");
                GameRegistry.registerItem(shieldWood, "shieldWood");
                GameRegistry.addRecipe(new ItemStack(shieldWood), " x ", "xyx", " x ",
                		'x', Block.planks, 'y', Item.leather);
                
                //BRONZE SHIELD
                LanguageRegistry.addName(shieldBronze, "Reinforced Shield");
                GameRegistry.registerItem(shieldBronze, "shield");
                GameRegistry.addRecipe(new ItemStack(shieldBronze), " x ", "xyx", " x ",
                		'x', bronzeIngot, 'y', shieldWood);
                
                //COPPER SPEAR
                LanguageRegistry.addName(spearCopper, "Copper Spear");
                GameRegistry.registerItem(spearCopper, "spearCopper");
                GameRegistry.addRecipe(new ItemStack(spearCopper), "  x", " y ", "y  ",
                		'x', copperIngot, 'y', Item.stick);
                
                //STONE JAVELIN
                LanguageRegistry.addName(javelinStone, "Stone Javelin");
                GameRegistry.registerItem(javelinStone, "javelinStone");
                GameRegistry.addRecipe(new ItemStack(javelinStone), "x  ", " y ", "  y",
                		'x', Block.cobblestone, 'y', Item.stick);
                
                //COPPER JAVELIN
                LanguageRegistry.addName(javelinCopper, "Copper Javelin");
                GameRegistry.registerItem(javelinCopper, "javelinCopper");
                GameRegistry.addRecipe(new ItemStack(javelinCopper), "x  ", " y ", "  y",
                		'x', copperIngot, 'y', Item.stick);
                
                //BRONZE JAVELIN
                LanguageRegistry.addName(javelinBronze, "Bronze Javelin");
                GameRegistry.registerItem(javelinBronze, "javelinBronze");
                GameRegistry.addRecipe(new ItemStack(javelinBronze), "x  ", " y ", "  y",
                		'x', bronzeIngot, 'y', Item.stick);
                
                //STRAW
                LanguageRegistry.addName(straw, "Straw");
                GameRegistry.registerItem(straw, "straw");
                //3 Grass = 1 Straw
                GameRegistry.addShapelessRecipe(new ItemStack(straw), new ItemStack(Block.tallGrass), new ItemStack(Block.grass), new ItemStack(Block.grass));
                //Temporary bread recipe to allow the seeds. We'll probably want to include flour-making and baking in the future
                //GameRegistry.addRecipe(new ItemStack(Item.bread), "xxx",
                		//'x', Item.seeds);
                
                //EMPTY BASKET
                LanguageRegistry.addName(basketEmpty, "Basket");
                GameRegistry.registerItem(basketEmpty, "basketEmpty");
                GameRegistry.addRecipe(new ItemStack(basketEmpty), "x x", "x x", " x ",
                		'x', Item.reed);
                GameRegistry.addRecipe(new ItemStack(basketEmpty), "x x", "x x", " x ",
                		'x', straw);
                
                //GRAIN BASKET
                LanguageRegistry.addName(basketGrain, "Grain Basket");
                GameRegistry.registerItem(basketGrain, "basketGrain");
                //In the crafting handler we return straw as part of this process
                GameRegistry.addShapelessRecipe(new ItemStack(basketGrain),
                		new ItemStack(basketEmpty), new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat),  
                		new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat));
                
                //FLOUR BASKET
                LanguageRegistry.addName(basketFlour, "Flour Basket");
                GameRegistry.registerItem(basketFlour, "basketFlour");
                
                //GRAIN AMPHORA
                LanguageRegistry.addName(amphoraGrain, "Grain Amphora");
                GameRegistry.registerItem(amphoraGrain, "amphoraGrain");
                //In the crafting handler we return straw as part of this process
                GameRegistry.addShapelessRecipe(new ItemStack(amphoraGrain),
                		new ItemStack(amphora), new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat),  
                		new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat));
                
                //FLOUR AMPHORA
                LanguageRegistry.addName(amphoraFlour, "Flour Amphora");
                GameRegistry.registerItem(amphoraFlour, "amphoraFlour");
                
                //GRAPES
                LanguageRegistry.addName(grapes, "Grapes");
                GameRegistry.registerItem(grapes, "Grapes");
                
                //DOUGH
                LanguageRegistry.addName(dough, "Dough");
                GameRegistry.registerItem(dough, "dough");
                GameRegistry.addShapelessRecipe(new ItemStack(dough, 3), new ItemStack(basketFlour), new ItemStack(Item.bucketWater));
                GameRegistry.addShapelessRecipe(new ItemStack(dough, 3), new ItemStack(amphoraFlour), new ItemStack(Item.bucketWater));
                GameRegistry.addSmelting(dough.itemID, new ItemStack(Item.bread), 1.0f);
                
                // Create a keybinding and add it via our GreekKeyBind class. That way we can do stuff whenever specific
                // keys are pressed, like block or whatever
                KeyBinding[] key = {new KeyBinding("Block", Keyboard.KEY_LCONTROL)};
	           	boolean[] repeat = {false};
	           	KeyBindingRegistry.registerKeyBinding(new GreekKeyBind(key, repeat));
	           	TickRegistry.registerTickHandler(new PlayerTickHandler(EnumSet.of(TickType.PLAYER)), Side.SERVER);
	           	TickRegistry.registerTickHandler(new PlayerTickHandler(EnumSet.of(TickType.PLAYER)), Side.CLIENT);
	           	List recipes = CraftingManager.getInstance().getRecipeList();
	           	int terminus = recipes.size();
	           	for (int i = 0; i < terminus; ++i) {
	           		IRecipe recipe = (IRecipe)recipes.get(i);
	           		int idOutput = 0;
	           		if (recipe.getRecipeOutput() == null) {
	           			System.out.println("Continuing");
	           			continue;
	           		}
	           		idOutput = recipe.getRecipeOutput().itemID;
	           		if (idOutput == Item.pickaxeStone.itemID || idOutput == Item.pickaxeWood.itemID) {
	           			CraftingManager.getInstance().getRecipeList().remove(i);
	           			System.out.println("Removed Crafting Recipe " + i);
	           			terminus--;
	           			i--;
	           		}
	           	}
	           	
	           	
                //------------ENTITIES---------------
	           	registerEntity(GreekGuard.class, "Guard", 0xef00af, 0x000fa0);
                LanguageRegistry.instance().addStringLocalization("entity.GreekGuard.name", "Guard");
	           	
                registerEntity(GreekHuman.class, "Bandit", 0xefaf00, 0xaa00aa);
                LanguageRegistry.instance().addStringLocalization("entity.GreekHuman.name", "Bandit");
                
                registerEntity(GreekArcher.class, "ArcherBandit", 0xe00abcd, 0x0abcd0);
                LanguageRegistry.instance().addStringLocalization("entity.GreekArcher.name", "ArcherBandit");
                
                //registerEntity(GreekEntityJavelin.class, "Javelin", 0xefdfe0, 0xaaccaa);
                EntityRegistry.registerModEntity(GreekEntityJavelin.class, "Javelin", 
                		cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId(), this, 64, 1, true);
                LanguageRegistry.instance().addStringLocalization("entity.GreekJavelin.name", "Javelin");
                
                registerEntity(GreekVillager.class, "Demesman", 0xefaf00, 0xaa00aa);
                LanguageRegistry.instance().addStringLocalization("entity.GreekVillager.name", "Demesman");
                
                // Register the event handler, so we can catch events like zombies spawning and do stuff, like replacing
                // the zombies with bandits.
        		MinecraftForge.EVENT_BUS.register(new GreekEventHandler());
                
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
                //GameRegistry.registerBlock(copperTin, "copperTin");
                //LanguageRegistry.addName(copperTin, "Copper and Tin");
                //MinecraftForge.setBlockHarvestLevel(copperTin, "pickaxe", 1);  
                
                //ItemStack copperTinStack = new ItemStack(copperTin, 2);
                //GameRegistry.addShapelessRecipe(copperTinStack, copperOre, tinOre);
                
                // OLIVE LEAVES
                GameRegistry.registerBlock(oliveLeaves, "oliveLeaves");
                LanguageRegistry.addName(oliveLeaves, "Olive Branches");
                //MinecraftForge.setBlockHarvestLevel(oliveLeaves, "shears", 1);
                
                // OLIVE LEAVES RIPE
                GameRegistry.registerBlock(oliveLeavesRipe, "oliveLeavesRipe");
                LanguageRegistry.addName(oliveLeavesRipe, "Ripe Olive Branches");
                
                // OLIVE WOOD
                GameRegistry.registerBlock(oliveWood, "oliveWood");
                LanguageRegistry.addName(oliveWood, "Olive Wood");
                MinecraftForge.setBlockHarvestLevel(oliveWood, "axe", 0);
                
                // OLIVE PRESS
                GameRegistry.registerBlock(olivePress, "olivePress");
                LanguageRegistry.addName(olivePress, "Olive Press");
                MinecraftForge.setBlockHarvestLevel(olivePress, "pickaxe", 1);
                GameRegistry.addRecipe(new ItemStack(olivePress), "x", "y", "x",
                		'x', Block.cobblestone, 'y', new ItemStack(chisel, 1, OreDictionary.WILDCARD_VALUE));
                GameRegistry.addRecipe(new ItemStack(olivePress), "x", "y", "x",
                		'x', limestone, 'y', new ItemStack(chisel, 1, OreDictionary.WILDCARD_VALUE));
                GameRegistry.addRecipe(new ItemStack(olivePress), "x", "y", "x",
                		'x', marble, 'y', new ItemStack(chisel, 1, OreDictionary.WILDCARD_VALUE));
                
                // ROOF TILES
                GameRegistry.registerItem(roofTile, "roofTile");
                LanguageRegistry.addName(roofTile, "Roof Tile");
                GameRegistry.addSmelting(Item.clay.itemID, new ItemStack(roofTile), 1);
                GameRegistry.registerBlock(roofTiles, "roofTiles");
                LanguageRegistry.addName(roofTiles, "Roof Tiles");
                //MinecraftForge.setBlockHarvestLevel(roofTiles, "pickAxe", 0);
                
                GameRegistry.registerBlock(roofTilesSlope, "roofTilesSlope");
                LanguageRegistry.addName(roofTilesSlope, "Sloped Roof Tiles");
                GameRegistry.registerBlock(roofTilesSlab, "roofTilesSlab");
                LanguageRegistry.addName(roofTilesSlab, "Roof Tiles Slab");
                GameRegistry.registerBlock(roofTilesSlabDouble, "roofTilesSlabDouble");
                
                GameRegistry.addRecipe(new ItemStack(roofTiles, 1), "xxx", "xxx", "xxx",
                		'x', roofTile);
                GameRegistry.addRecipe(new ItemStack(roofTilesSlope, 1), "x  ", "xx ", "xxx",
                		'x', roofTile);
                GameRegistry.addRecipe(new ItemStack(roofTilesSlope, 4), "x  ", "xx ", "xxx",
                		'x', roofTiles);
                GameRegistry.addRecipe(new ItemStack(roofTilesSlab, 1), "xxx", "xxx",
                		'x', roofTile);
                GameRegistry.addRecipe(new ItemStack(roofTiles, 4), "xxx", "xxx",
                		'x', roofTiles);
       
                //---------REGISTER ITEMS - Mills---------
                // BRONZE INGOT
                GameRegistry.registerItem(bronzeIngot, "bronzeIngot");
                LanguageRegistry.addName(bronzeIngot, "Bronze Ingot");
                ItemStack bronzeIngotStack = new ItemStack(bronzeIngot);
                GameRegistry.addSmelting(bronzeWashed.itemID, bronzeIngotStack, 1);
                GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), " x ", " y ", " z ",
                		'x', bronzeIngot, 'y', Item.stick, 'z', Item.feather);
                
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
                GameRegistry.addSmelting(silverWashed.itemID, new ItemStack(silverIngot), 1);
                
                // DRACHMA
                GameRegistry.registerItem(drachma, "drachma");
                LanguageRegistry.addName(drachma, "Drachma");
                GameRegistry.addShapelessRecipe(new ItemStack(drachma, 1), new ItemStack(silverIngot));
                GameRegistry.addShapelessRecipe(new ItemStack(drachma, 1), new ItemStack(obol), new ItemStack(obol), new ItemStack(obol), new ItemStack(obol), new ItemStack(obol), new ItemStack(obol)); // 6 obols to 1 drachma
                
                // OBOL
                GameRegistry.registerItem(obol, "obol");
                LanguageRegistry.addName(obol, "Obol");
                GameRegistry.addShapelessRecipe(new ItemStack(obol, 6), new ItemStack(silverIngot));
                GameRegistry.addShapelessRecipe(new ItemStack(obol, 6), new ItemStack(drachma)); // 1 drachma to 6 obols
                
                // MARBLE EYE
                GameRegistry.registerItem(marbleEye, "marbleEye");
                LanguageRegistry.addName(marbleEye, "Ship's Eye");
                GameRegistry.addRecipe(new ItemStack(marbleEye), "a ", "bb", "cd",
                		'a', new ItemStack(chisel, 1, OreDictionary.WILDCARD_VALUE), 'b', marble, 'c', new ItemStack(Item.dyePowder, 1, 1), 'd', new ItemStack(Item.dyePowder, 1, 11));
                
                // BAKING COVER
                GameRegistry.registerItem(unfiredBakingCover, "unfiredBakingCover");
                LanguageRegistry.addName(unfiredBakingCover, "Unfired Baking Cover");
                GameRegistry.addRecipe(new ItemStack(unfiredBakingCover), " a ", "aaa", "a a",
                		'a', Item.clay);
                
                GameRegistry.registerItem(bakingCover, "bakingCover");
                LanguageRegistry.addName(bakingCover, "Baking Cover");
                GameRegistry.addSmelting(unfiredBakingCover.itemID, new ItemStack(bakingCover), 1);
                GameRegistry.addShapelessRecipe(new ItemStack(Item.bread, 1), new ItemStack(bakingCover, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Item.coal, 1, OreDictionary.WILDCARD_VALUE), Item.wheat, Item.wheat);
                GameRegistry.addShapelessRecipe(new ItemStack(Item.bread, 1), new ItemStack(bakingCover, 1, OreDictionary.WILDCARD_VALUE), hotCoals, Item.wheat, Item.wheat);
                GameRegistry.addShapelessRecipe(new ItemStack(Item.bread, 1), new ItemStack(bakingCover, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Item.coal, 1, OreDictionary.WILDCARD_VALUE), dough);
                GameRegistry.addShapelessRecipe(new ItemStack(Item.bread, 1), new ItemStack(bakingCover, 1, OreDictionary.WILDCARD_VALUE), hotCoals, dough);
                
                // AMPHORA
                GameRegistry.registerItem(unfiredAmphora, "unfiredAmphora");
                LanguageRegistry.addName(unfiredAmphora, "Unfired Amphora");
                GameRegistry.addRecipe(new ItemStack(unfiredAmphora), "a a", "aaa", " a ",
                		'a', Item.clay);
                
                GameRegistry.registerItem(amphora, "amphora");
                LanguageRegistry.addName(amphora, "Amphora");
                GameRegistry.addSmelting(unfiredAmphora.itemID, new ItemStack(amphora), 1);
                
                // OLIVE OIL
                GameRegistry.registerItem(oliveOil, "oliveOil");
                LanguageRegistry.addName(oliveOil, "Olive Oil");
                GameRegistry.addRecipe(new ItemStack(oliveOil), "aaa", "aaa", " b ",
                		'a', olives, 'b', amphora);
                
                // WINE
                GameRegistry.registerItem(wine, "wine");
                LanguageRegistry.addName(wine, "Wine");
                
                // FRYING PANS
                GameRegistry.registerItem(fryingPanUnfired, "fryingPanUnfired");
                LanguageRegistry.addName(fryingPanUnfired, "Unfired Frying Pan");
                GameRegistry.addRecipe(new ItemStack(fryingPanUnfired), " aa", "bba", " aa",
                		'a', Item.clay, 'b', Item.stick);
                GameRegistry.addSmelting(fryingPanUnfired.itemID, new ItemStack(fryingPanCeramic), 1);
                GameRegistry.registerItem(fryingPanCeramic, "fryingPanCeramic");
                LanguageRegistry.addName(fryingPanCeramic, "Ceramic Frying Pan");
                GameRegistry.registerItem(fryingPanBronze, "fryingPanBronze");
                LanguageRegistry.addName(fryingPanBronze, "Bronze Frying Pan");
                GameRegistry.addRecipe(new ItemStack(fryingPanBronze), " aa", "bba", " aa",
                		'a', bronzeIngot, 'b', Item.stick);
                
                // "SHORTCUT" ITEMS
                GameRegistry.registerItem(hotCoals, "hotCoals"); // used in cooking
                LanguageRegistry.addName(hotCoals, "Hot Coals");
                GameRegistry.addSmelting(Item.coal.itemID, new ItemStack(hotCoals), 8);
                GameRegistry.addShapelessRecipe(new ItemStack(hotCoals, 4), new ItemStack(Item.coal, 1, OreDictionary.WILDCARD_VALUE));
                GameRegistry.addShapelessRecipe(new ItemStack(hotCoals, 8), new ItemStack(Item.coal, 1, OreDictionary.WILDCARD_VALUE), Block.torchWood);
                
                GameRegistry.registerItem(triobol, "triobol"); // makes currency conversion easier.
                LanguageRegistry.addName(triobol, "Triobol");
                GameRegistry.addShapelessRecipe(new ItemStack(drachma, 1), triobol, triobol); // 2 triobols -> 1 drachma
                GameRegistry.addShapelessRecipe(new ItemStack(obol, 3), triobol); // 1 triobol -> 3 obols
                GameRegistry.addShapelessRecipe(new ItemStack(triobol, 1), obol, obol, obol); // 1 triobol -> 3 obols
                
                // REGISTER FOOD
                GameRegistry.registerItem(olives, "olives");
                LanguageRegistry.addName(olives, "Olives");
                GameRegistry.registerItem(pancake, "pancake");
                LanguageRegistry.addName(pancake, "Pancake");
                GameRegistry.addShapelessRecipe(new ItemStack(pancake, 1), new ItemStack(fryingPanCeramic, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Item.coal, 1, OreDictionary.WILDCARD_VALUE), oliveOil, Item.bucketMilk, basketFlour);
                GameRegistry.addShapelessRecipe(new ItemStack(pancake, 1), new ItemStack(fryingPanCeramic, 1, OreDictionary.WILDCARD_VALUE), hotCoals, oliveOil, Item.bucketMilk, basketFlour);
                GameRegistry.addShapelessRecipe(new ItemStack(pancake, 1), new ItemStack(fryingPanBronze, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Item.coal, 1, OreDictionary.WILDCARD_VALUE), oliveOil, Item.bucketMilk, basketFlour);
                GameRegistry.addShapelessRecipe(new ItemStack(pancake, 1), new ItemStack(fryingPanBronze, 1, OreDictionary.WILDCARD_VALUE), hotCoals, oliveOil, Item.bucketMilk, basketFlour);
                
                //---------MISC - Mills---------
                GameRegistry.registerCraftingHandler(chiselCrafting);
                GameRegistry.registerWorldGenerator(treeManager);             
                
                //---------BIOMES & WORLDGEN-----------
                GameRegistry.registerWorldGenerator(oreManager);
                
                // last two parameters: grass color, tree leaf color. Pass in null for default color.
                limeCliffsBiome = new BiomeGenGreek(66, -1, Block.stone.blockID, null, null).setBiomeName("Limestone Cliffs").setColor(11977652).setMinMaxHeight(-0.3f, 0.8f);                
                GameRegistry.addBiome(limeCliffsBiome);
                
                tinIslesBiome = new BiomeGenGreek(67, -1, -1, null, null).setBiomeName("Tin Isles").setColor(10537122).setMinMaxHeight(-0.4f, 0.4f);
                GameRegistry.addBiome(tinIslesBiome);
                
                graniteMountainsBiome = new BiomeGenGreek(68, -1, Block.stone.blockID, 15588736, null).setBiomeName("Granite Mountains").setColor(0).setMinMaxHeight(0.5f, 2f);
                GameRegistry.addBiome(graniteMountainsBiome);
                
                korinthiaBiome = new BiomeGenGreek(69, -1, -1, 15588736, null).setBiomeName("Korinthia").setMinMaxHeight(0.0f, 0.5f);
                GameRegistry.addBiome(korinthiaBiome); //13878634
                
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
        	// this determines where you can spawn... I think.
        	WorldChunkManager.allowedBiomes = new ArrayList<BiomeGenBase>(Arrays.asList(forest, plains, forestHills, limeCliffsBiome, graniteMountainsBiome, tinIslesBiome, korinthiaBiome));
        	MapGenVillage.villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] {forest, plains, forestHills,
           			limeCliffsBiome, korinthiaBiome, BiomeGenBase.desert});
        	GreekMapGenVillage.villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] {forest, plains, forestHills,
           			limeCliffsBiome, korinthiaBiome, BiomeGenBase.desert});
        	
        	// allow stackable slabs
        		// template:
        		// Item.itemsList[YourSingleSlab.blockID] = (new ItemSlab(YourSingleSlab.blockID - 256, (BlockHalfSlab)YourSingleSlab, (BlockHalfSlab)YourDoubleSlab, false)).setUnlocalizedName("YourSlabName");
        	Item.itemsList[roofTilesSlab.blockID] = (new ItemSlab(roofTilesSlab.blockID - 256, (BlockHalfSlab)roofTilesSlab, (BlockHalfSlab)roofTilesSlabDouble, false)).setUnlocalizedName("roofTilesSlabCombined");
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
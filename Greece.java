package mod.greece;
 
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
 
@Mod(modid="Greece", name="Ancient Greece Mod", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Greece {
		private final static Item plasterBucket = new PlasterBucket(5000);
		private final static Item sard = new GenericItem(5001).setTextureName("Greece:sard").setUnlocalizedName("sard");
		private final static Item lime = new GenericItem(5002).setTextureName("Greece:lime").setUnlocalizedName("lime");
		private final static Item onyx = new GenericItem(5003).setTextureName("Greece:onyx").setUnlocalizedName("onyx");
		public final static Block genericDirt = new GreekBlock(500, Material.rock, 500).setHardness(0.5f).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("genericDirt").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block sardOre = new GenericOre(501, Material.rock, Greece.sard.itemID).setTextureName("Greece:sard_ore");
		public final static Block plasteredDirt = new PlasteredBlock(502, Material.ground, Block.dirt.blockID).setHardness(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("Plastered Earth").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block plasteredWood = new PlasteredBlock(503, Material.wood, Block.planks.blockID).setHardness(1f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("Plastered Wood").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block limestone = new GreekBlock(504, Material.rock, 504).setHardness(0.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("limestone").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:limestone");
		public final static Block onyxOre = new GenericOre(505, Material.rock, Greece.onyx.itemID).setTextureName("Greece:onyx_ore");
		public final static Block thatchSlope = new ThatchSlope(506, Block.hay.blockMaterial).setTextureName("Greece:thatch");
		EventManager oreManager = new EventManager();
		
		///// MILLS ADDITIONS START HERE /////
		public static EnumToolMaterial bronze = EnumHelper.addToolMaterial("Bronze", 2, 200, 5.0F, 2.0F, 12);
		
		//---------NEW BLOCKS---------
		public final static Block marble = new GreekOre(506, Material.rock)
		.setUnlocalizedName("marble")
		.setTextureName(GreeceInfo.NAME.toLowerCase() + ":marble");
		
		public final static Block silverOre = new GreekOre(507, Material.rock)
		.setUnlocalizedName("silverOre")
		.setTextureName(GreeceInfo.NAME.toLowerCase() + ":silver_ore");
		
		public final static Block copperOre = new GreekOre(508, Material.rock)
		.setUnlocalizedName("copperOre")
		.setTextureName(GreeceInfo.NAME.toLowerCase() + ":copper_ore");
		
		public final static Block tinOre = new GreekOre(509, Material.rock)
		.setUnlocalizedName("tinOre")
		.setTextureName(GreeceInfo.NAME.toLowerCase() + ":tin_ore");
		
		public final static Block copperTin = new GreekOre(510, Material.rock)
		.setUnlocalizedName("copperTin")
		.setTextureName(GreeceInfo.NAME.toLowerCase() + ":copper_tin");
		
		public final static Block marbleBrick = new GreekBlock(511, Material.rock)
		.setHardness(4.0f)
		.setStepSound(Block.soundStoneFootstep)
		.setUnlocalizedName("marbleBrick")
		.setCreativeTab(CreativeTabs.tabBlock)
		.setTextureName(GreeceInfo.NAME.toLowerCase() + ":marblebrick");
		
		/*public final static Block altar = new GenericBlock(506, Material.rock)
		.setHardness(1.0f)
		.setStepSound(Block.soundStoneFootstep)
		.setUnlocalizedName("altar")
		.setCreativeTab(CreativeTabs.tabBlock);*/
		
		//---------END NEW BLOCKS---------
		
		//---------NEW ITEMS---------
		public final static Item bronzeIngot = new BronzeIngot(5004);
		
		public final Item bronzeSword = new GenericSword(5005, bronze)
			.setCreativeTab(CreativeTabs.tabCombat)
			.setUnlocalizedName("bronzeSword")
			.setTextureName(GreeceInfo.NAME.toLowerCase() + ":spear");
		
		public final static Item chisel = new GreekItem(5006, bronze)
		.setUnlocalizedName("chisel")
		.setTextureName(GreeceInfo.NAME.toLowerCase() + ":chisel");
		//---------END NEW ITEMS---------
		
		MillsEventManager millsOreManager = new MillsEventManager();
		CraftingHandler marbleCrafting = new CraftingHandler();
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
                
                //Register Blocks:
                GameRegistry.registerBlock(genericDirt, "genericDirt");
                LanguageRegistry.addName(genericDirt, "DIRT");
                MinecraftForge.setBlockHarvestLevel(genericDirt, "shovel", 3);
                
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
                
                //Register Items:
                LanguageRegistry.addName(sard, "Sard");
                GameRegistry.registerItem(sard, "sard");
                LanguageRegistry.addName(onyx, "Onyx");
                GameRegistry.registerItem(onyx, "onyx");
                LanguageRegistry.addName(lime, "Lime");
                GameRegistry.registerItem(lime, "lime");
                LanguageRegistry.addName(plasterBucket, "Plaster Bucket");
                GameRegistry.registerItem(plasterBucket, "plasterBucket");
                
                //Misc
                GameRegistry.registerWorldGenerator(oreManager);
                proxy.registerRenderers();
                
                
                ///// MILLS'S REGISTRATIONS STARTS HERE /////
                //---------NEW BLOCKS---------
                
                // MARBLE
                GameRegistry.registerBlock(marble, "marble");
                LanguageRegistry.addName(marble, "Marble");
                MinecraftForge.setBlockHarvestLevel(marble, "pickaxe", 1);              
                // END MARBLE
                
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
                // END SILVER
                
                // COPPER
                GameRegistry.registerBlock(copperOre, "copperOre");
                LanguageRegistry.addName(copperOre, "Copper Ore");
                MinecraftForge.setBlockHarvestLevel(copperOre, "pickaxe", 1);               
                // END COPPER
                
                // TIN
                GameRegistry.registerBlock(tinOre, "tinOre");
                LanguageRegistry.addName(tinOre, "Tin Ore");
                MinecraftForge.setBlockHarvestLevel(tinOre, "pickaxe", 1);               
                // END TIN
                
                // COPPER WITH TIN
                GameRegistry.registerBlock(copperTin, "copperTin");
                LanguageRegistry.addName(copperTin, "Copper and Tin");
                MinecraftForge.setBlockHarvestLevel(copperTin, "pickaxe", 1);  
                
                ItemStack copperTinStack = new ItemStack(copperTin, 2);
                GameRegistry.addShapelessRecipe(copperTinStack, copperOre, tinOre);
                // END TIN
                
                // ALTAR
                //GameRegistry.registerBlock(altar, "altar");
                //LanguageRegistry.addName(altar, "Altar");
                //MinecraftForge.setBlockHarvestLevel(altar, "pickaxe", 1);
                
                //ItemStack altarStack = new ItemStack(altar);
                
                // crafting
                //GameRegistry.addRecipe(altarStack, "xx", "yy",
                //		'x', Item.stick, 'y', Block.stoneBrick);
                // END ALTAR
                
                //---------END NEW BLOCKS---------
                
                //---------NEW ITEMS---------
                
                // BRONZE INGOT
                GameRegistry.registerItem(bronzeIngot, "bronzeIngot");
                LanguageRegistry.addName(bronzeIngot, "Bronze Ingot");
                ItemStack bronzeIngotStack = new ItemStack(bronzeIngot);
                GameRegistry.addSmelting(copperTin.blockID, bronzeIngotStack, 0.1f);
                
                // BRONZE SWORD
                GameRegistry.registerItem(bronzeSword, "bronzeSword");
                LanguageRegistry.addName(bronzeSword, "Bronze Sword");
                ItemStack bronzeSwordStack = new ItemStack(bronzeSword);
                
                // CHISEL
                GameRegistry.registerItem(chisel, "chisel");
                LanguageRegistry.addName(chisel, "Chisel");
                ItemStack chiselStack = new ItemStack(chisel);
                GameRegistry.addRecipe(chiselStack, "x", "y",
                		'x', bronzeIngot, 'y', Item.stick);
                //---------END NEW ITEMS---------
                
                // WORLD GEN
                GameRegistry.registerWorldGenerator(millsOreManager);
                GameRegistry.registerCraftingHandler(marbleCrafting);
        }
       
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
       
}
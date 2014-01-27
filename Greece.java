package mod.greece;
 
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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
		public final static Block genericDirt = new GenericBlock(500, Material.rock, 500).setHardness(0.5f).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("genericDirt").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block sardOre = new GenericOre(501, Material.rock, Greece.sard.itemID).setTextureName("Greece:sard_ore");
		public final static Block plasteredDirt = new PlasteredBlock(502, Material.ground, Block.dirt.blockID).setHardness(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("Plastered Earth").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block plasteredWood = new PlasteredBlock(503, Material.wood, Block.planks.blockID).setHardness(1f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("Plastered Wood").setCreativeTab(CreativeTabs.tabBlock);
		public final static Block limestone = new GenericBlock(504, Material.rock, 504).setHardness(0.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("limestone").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Greece:limestone");
		public final static Block onyxOre = new GenericOre(505, Material.rock, Greece.onyx.itemID).setTextureName("Greece:onyx_ore");
		public final static Block thatchSlope = new ThatchSlope(506, Block.hay.blockMaterial).setTextureName("Greece:thatch");
		EventManager oreManager = new EventManager();
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
        }
       
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
       
}
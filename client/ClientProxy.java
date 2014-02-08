package mod.greece.client;
 
import mod.greece.CommonProxy;
import mod.greece.GreekEntityJavelin;
import mod.greece.GreekRenderJavelin;
import mod.greece.GreekVillager;
import mod.greece.mobs.GreekArcher;
import mod.greece.mobs.GreekHuman;
import mod.greece.mobs.RenderHuman;
import net.minecraft.client.model.ModelBiped;
import cpw.mods.fml.client.registry.RenderingRegistry;
 
public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
                // This is for rendering entities and so forth later on
        	//ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySlope.class, new TileEntitySlopeRenderer());
        	//MinecraftForgeClient.registerItemRenderer(Greece.thatchSlope.blockID, new ItemSlopeRenderer());
        	RenderingRegistry.registerEntityRenderingHandler(GreekHuman.class, new RenderHuman(new ModelBiped(), 0.5f));
        	RenderingRegistry.registerEntityRenderingHandler(GreekArcher.class, new RenderHuman(new ModelBiped(), 0.5f));
        	RenderingRegistry.registerEntityRenderingHandler(GreekEntityJavelin.class, new GreekRenderJavelin());
        	RenderingRegistry.registerEntityRenderingHandler(GreekVillager.class, new RenderHuman(new ModelBiped(), 0.5f, "/textures/mobs/demesman.png"));
        }
       
}
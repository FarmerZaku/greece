package mod.greece.client;
 
import mod.greece.CommonProxy;
import mod.greece.Greece;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
 
public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
                // This is for rendering entities and so forth later on
        	//ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySlope.class, new TileEntitySlopeRenderer());
        	//MinecraftForgeClient.registerItemRenderer(Greece.thatchSlope.blockID, new ItemSlopeRenderer());
        }
       
}
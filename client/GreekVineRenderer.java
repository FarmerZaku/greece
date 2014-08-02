package mod.greece.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class GreekVineRenderer implements ISimpleBlockRenderingHandler {
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
         return;
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
    	//System.out.println("AAAAAA");
    	//renderer.overrideBlockTexture = ;
    	//block.setBlockBounds(0.4F, 0.0F, 0.4F, 0.6F, 0.5F, 0.6F);
    	block.setBlockBoundsBasedOnState(world, x, y, z);
    	renderer.renderStandardBlock(block, x, y, z);
    	
    	//renderer.overrideBlockTexture = (Icon)(-1);

    	//renderblocks.overrideBlockTexture = [/size][/size][/size][/size][size=x-small]TutorialBlockTexture[/size][size="2"][size="2"][size="2"][size="2"];
    	//block.setBlockBoundsBasedOnState(world, x, y, z);
    	//renderer.renderStandardBlock(block, x, y, z);
    	//renderer.overrideBlockTexture = -1;

    	//block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         return false;
    }

    public boolean shouldRender3DInInventory()
    {
         return false;
    }

    public int getRenderId()
    {
         return 0;
    }
}

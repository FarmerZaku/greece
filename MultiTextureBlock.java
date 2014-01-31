package mod.greece;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MultiTextureBlock extends Block {
	private String blockType;

	public MultiTextureBlock(int id, Material material) {
		super(id, material);
		setHardness(2f);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public MultiTextureBlock(int id, Material material, String type) {
		super(id, material);
		setHardness(2f);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(CreativeTabs.tabBlock);
		blockType = type;
	}
	
	public boolean canSustainLeaves(World world, int x, int y, int z)
    {
        if (blockType == "logs")
        	return true;
		return false;
    }
	
	@SideOnly(Side.CLIENT)
	public static Icon topIcon;
	@SideOnly(Side.CLIENT)
	public static Icon bottomIcon;
	@SideOnly(Side.CLIENT)
	public static Icon sideIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		topIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":log_olive_top");
		bottomIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":log_olive_top");
		sideIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":log_olive");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		if (side==0)
			return bottomIcon;
		else if (side==1)
			return topIcon;
		else
			return sideIcon;
	}
}

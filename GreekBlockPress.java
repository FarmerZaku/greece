package mod.greece;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class GreekBlockPress extends Block {
	private int dropID;
	private int inputType;
	
	public GreekBlockPress(int id, Material material) {
		super(id, material);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public GreekBlockPress(int id, Material material, int drop_id) {
		super(id, material);
		//setTextureName(BasicInfo.NAME.toLowerCase() + ":plaster");
		setCreativeTab(CreativeTabs.tabBlock);
		dropID = drop_id;
	}
	
	public GreekBlockPress(int id, Material material, int drop_id, int inputItemID) {
		super(id, material);
		setCreativeTab(CreativeTabs.tabBlock);
		dropID = drop_id;
		inputType = inputItemID;
	}
	
	public int idDropped(int metadata, Random random, int fortune) {
		return dropID;
	}
	
	public int counter(EntityPlayer player, ItemStack item) {
		int amount = 0;
		
		for (int i = 0; i < player.inventory.mainInventory.length; i++)
        {
            if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].isItemEqual(item))
            {
                amount += player.inventory.mainInventory[i].stackSize;
            }
        }
		return amount;
	}
	
	/**
     * Called upon block activation (right click on the block.)
     */
	@Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		if (par5EntityPlayer.inventory.hasItemStack(new ItemStack(Greece.amphora)) && 
				counter(par5EntityPlayer, new ItemStack(Greece.olives)) > 15) {
		par5EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Greece.oliveOil));
		par1World.playSoundAtEntity(par5EntityPlayer, "random.pop", 1, 1);
		par5EntityPlayer.inventory.consumeInventoryItem(Greece.amphora.itemID);
		for (int i = 0; i <= 15; i++) {
			par5EntityPlayer.inventory.consumeInventoryItem(inputType);
		}
        return true;
		}
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
		topIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":press_top");
		bottomIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":press_side");
		sideIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":press_side");
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

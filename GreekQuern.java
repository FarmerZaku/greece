package mod.greece;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekQuern extends Block {
	private int[] outputs;
	private int[] inputs;
	private int[] secondaryInputs;
	private int[] inputQuantities;
	private int textureType;
	
	public GreekQuern(int id, Material material, int[] inputs, int[] secondaryInputs, int[] inputQuantities, int[] outputs, int textureType) {
		super(id, material);
		setCreativeTab(CreativeTabs.tabBlock);
		this.outputs = outputs;
		this.inputs = inputs;
		this.inputQuantities = inputQuantities;
		this.secondaryInputs = secondaryInputs;
		this.textureType = textureType;
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
		for (int i = 0; i < inputs.length; ++i) {
			if ((secondaryInputs[i] == 0 || par5EntityPlayer.inventory.hasItemStack(new ItemStack(secondaryInputs[i], 1, 0))) && 
					counter(par5EntityPlayer, new ItemStack(inputs[i], 1, 0)) >= inputQuantities[i]) {
				if (par5EntityPlayer.inventory.getFirstEmptyStack() != -1) {
					par5EntityPlayer.inventory.addItemStackToInventory(new ItemStack(outputs[i], 1, 0));
				} else {
					par5EntityPlayer.dropPlayerItem(new ItemStack(outputs[i], 1, 0));
				}
				par1World.playSoundAtEntity(par5EntityPlayer, "random.wood_click", 1, 1);
				if (secondaryInputs[i] != 0) {
					par5EntityPlayer.inventory.consumeInventoryItem(secondaryInputs[i]);
				}
				for (int j = 0; j < inputQuantities[i]; j++) {
					par5EntityPlayer.inventory.consumeInventoryItem(inputs[i]);
				}
				return true;
			}
		}
		return false;
    }
	
	@SideOnly(Side.CLIENT)
	public Icon topIcon;
	@SideOnly(Side.CLIENT)
	public Icon bottomIcon;
	@SideOnly(Side.CLIENT)
	public Icon sideIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		switch (textureType) {
		case 0:
			topIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":quern_top");
			bottomIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":press_side");
			sideIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":quern_side");
			break;
		case 1:
			topIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":quern_top");
			bottomIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":press_side");
			sideIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":crusher_side");
			break;
		default:
			topIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":quern_top");
			bottomIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":press_side");
			sideIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":quern_side");
		}
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

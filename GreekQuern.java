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
	private Item[] outputs;
	private Item[] inputs;
	private Item[] secondaryInputs;
	private int[] inputQuantities;
	
	//for secondary inputs, use Item.comparator for a blank value. null makes there be problems... Sorry, I know that's ugly
	public GreekQuern(int id, Material material, Item[] inputs, Item[] secondaryInputs, int[] inputQuantities, Item[] outputs) {
		super(id, material);
		setCreativeTab(CreativeTabs.tabBlock);
		this.outputs = outputs;
		this.inputs = inputs;
		this.inputQuantities = inputQuantities;
		this.secondaryInputs = secondaryInputs;
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
			if ((secondaryInputs[i] == null || par5EntityPlayer.inventory.hasItemStack(new ItemStack(secondaryInputs[i]))) && 
					counter(par5EntityPlayer, new ItemStack(inputs[i])) >= inputQuantities[i]) {
				par5EntityPlayer.inventory.addItemStackToInventory(new ItemStack(outputs[i]));
				par1World.playSoundAtEntity(par5EntityPlayer, "random.wood_click", 1, 1);
				if (secondaryInputs[i] != null) {
					par5EntityPlayer.inventory.consumeInventoryItem(secondaryInputs[i].itemID);
				}
				for (int j = 0; j < inputQuantities[i]; j++) {
					par5EntityPlayer.inventory.consumeInventoryItem(inputs[i].itemID);
				}
				return true;
			}
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
		topIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":quern_top");
		bottomIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":press_side");
		sideIcon = icon.registerIcon(GreeceInfo.NAME.toLowerCase() + ":quern_side");
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

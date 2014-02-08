package mod.greece;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class TradeHandler implements IVillageTradeHandler
{	
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
	{
		villager.blacksmithSellingList.clear();
		villager.villagerStockList.clear();
		
		switch(villager.getProfession()) {
		case 0: // FARMER
			// standard trades
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4), new ItemStack(Greece.olives, 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4), new ItemStack(Item.wheat, 1)));
			break;
		case 1: // LIBRARIAN
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4),
				new ItemStack(Greece.oliveOil, 1))); // to buy
			// use the vanilla Item method to easily construct an ItemStack containing an enchanted book of any level
           // recipeList.add(new MerchantRecipe(new ItemStack(Item.diamond, 1), Item.enchantedBook.getEnchantedItemStack(new EnchantmentData(Enchantment.flame, 1))));
			break;
		case 2: // PRIEST
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4), new ItemStack(Greece.oliveOil)));
			break;
		case 3: // BLACKSMITH
			// using the passed in Random to randomize amounts; nextInt(value) returns an int between 0 and value (non-inclusive)
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4),
				new ItemStack(Greece.bronzeSword, 1))); // to buy
			break;
		case 4: // BUTCHER
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4),
				new ItemStack(Item.fishCooked, 1))); // to buy
			
			// You can also add directly to the villager with 2 different methods:

			// Method 1: takes the list, an item ID that may be bought OR sold, rand, and a float value that
			// determines how common the trade is. The price of the item is determined in the HashMap
			// blacksmithSellingList, which we'll add our custom Item to first:
			//villager.blacksmithSellingList.put(Integer.valueOf(Greece.drachma.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(8)));
			// Then add the trade, which will buy or sell for between 4 and 8 emeralds
			//villager.addBlacksmithItem(recipeList, Greece.drachma.itemID, random, 0.5F);

			// Method 2: Basically the same as above, but only for selling items and at a fixed price of 1 emerald
			// However, the stack sold will have a variable size determined by the HashMap villagerStockList,
			// to which we first need to add our custom Item:
			//villager.villagerStockList.put(Integer.valueOf(Greece.drachma.itemID), new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
			// Then add the trade, which will sell between 16 and 24 of our Item for 1 emerald
			//villager.addMerchantItem(recipeList, Greece.oliveOil.itemID, random, 0.5F);
			break;
		default:
			break;
		}
	}
}
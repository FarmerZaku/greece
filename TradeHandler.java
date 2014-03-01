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
		//villager.blacksmithSellingList.clear();
		//villager.villagerStockList.clear();
		
		switch(villager.getProfession()) {
		case 0: // FARMER
			// standard trades
			//if (random.nextInt(4) > 2) { 
				recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 1), new ItemStack(Greece.drachma, 5)));
			//}
			//else
				recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 5), new ItemStack(Greece.amphoraGrain, 1)));
			break;
		case 1: // ORATOR
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 16),
				new ItemStack(Greece.amphora, 1))); // to buy
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 1),
				new ItemStack(Greece.javelinBronze, 1))); // to buy
			// use the vanilla Item method to easily construct an ItemStack containing an enchanted book of any level
           // recipeList.add(new MerchantRecipe(new ItemStack(Item.diamond, 1), Item.enchantedBook.getEnchantedItemStack(new EnchantmentData(Enchantment.flame, 1))));
			break;
		case 2: // PRIEST
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 16), new ItemStack(Greece.bronzeIngot)));
			break;
		case 3: // BLACKSMITH
			// using the passed in Random to randomize amounts; nextInt(value) returns an int between 0 and value (non-inclusive)
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 16),
				new ItemStack(Greece.tinIngot, 1))); // to buy
			break;
		case 4: // BUTCHER
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 4),
				new ItemStack(Item.fishCooked, 1))); // to buy
			break;
		case 5: // YOUTH
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 16),
				new ItemStack(Item.saddle, 1))); // to buy
			break;
		case 6: // OTHER
			recipeList.add(new MerchantRecipe(new ItemStack(Greece.drachma, 16),
				new ItemStack(Greece.copperIngot, 1))); // to buy
			break;	
		default:
			break;
		}
	}
}
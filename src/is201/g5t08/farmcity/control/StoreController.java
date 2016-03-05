package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.dao.CropDataManager;
import is201.g5t08.farmcity.dao.InventoryDataManager;
import is201.g5t08.farmcity.dao.UserDataManager;
import is201.g5t08.farmcity.entity.Crop;
import is201.g5t08.farmcity.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A StoreController does all the validations and executes buy seeds function
 */
public class StoreController {

	Map<String, Crop> crops = new CropDataManager().listCrops();
	UserDataManager udm = new UserDataManager();

	/**
	 * This methods executes buy seeds from store. After purchase, it will add
	 * the seeds to user's inventory
	 *
	 * @param user
	 *            the logged user
	 * @param option
	 *            the choice of seeds to purchase
	 * @param quantity
	 *            the quantity of seeds to purchase
	 * @return the number of seeds purchased
	 */
	public String buySeeds(User user, String option, int quantity) {
		InventoryDataManager idm = new InventoryDataManager();
		String seeds = getSeeds(option);
		// count the total amount of seeds purchased
		int purchasedAmount = getTotalPrice(option, quantity);

		if (purchasedAmount > user.getGold()) {
			return String.format(
					"   Your account has left %d, please re-enter quantity.",
					user.getGold());
		}

		// deduct amount from gold
		user.setGold(user.getGold() - purchasedAmount);
		udm.update(user);

		idm.updateInventory(user, seeds, quantity);

		if (quantity != 0) {
			String plurality = "s";
			if (quantity == 1) {
				plurality = "";
			}
			return String.format("   %d bag%s of seeds purchased for %d gold.",
					quantity, plurality, purchasedAmount);
		}
		return "";

	}

	/**
	 * This method returns int value of purchasing amount
	 *
	 * @param option
	 *            the choice of seeds to purchase
	 * @param quantity
	 *            the number of seeds to purchase
	 * @return the sum of purchasing amount
	 */
	private int getTotalPrice(String option, int quantity) {

		int seedCost = getSeedsCost(option);
		int totalAmount = quantity * seedCost;

		return totalAmount;
	}

	/**
	 * This method returns name of seeds to purchase
	 *
	 * @param option
	 *            the choice of seeds to purchase
	 * @return the name of seed
	 */
	public String getSeeds(String option) {
		String seeds = null;
		List<String> cList = new ArrayList<String>();

		for (String s : crops.keySet()) {
			cList.add(s);
		}

		for (int i = 0; i < cList.size(); i++) {
			if (Integer.parseInt(option) - 1 == i) {
				seeds = cList.get(i);
			}
		}

		return seeds;

	}

	/**
	 * This method returns cost of selected seeds
	 * 
	 * @param option
	 *            the choice of seeds to purchase
	 * @return int value of seed cost
	 */
	private int getSeedsCost(String option) {
		int gold = 0;
		List<Integer> cList = new ArrayList<Integer>();

		for (Crop s : crops.values()) {
			cList.add(s.getCost());
		}

		for (int i = 0; i < cList.size(); i++) {
			if (Integer.parseInt(option) - 1 == i) {
				gold = cList.get(i);
			}
		}
		return gold;

	}

}

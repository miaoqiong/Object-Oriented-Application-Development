package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.control.StoreController;
import is201.g5t08.farmcity.dao.CropDataManager;
import is201.g5t08.farmcity.entity.Crop;
import is201.g5t08.farmcity.entity.User;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The StoreMenu gets input from the user and display information to the user.
 */
public class StoreMenu {

	/** The user. */
	User user;

	/** The Constant sc. */
	private static final Scanner sc = new Scanner(System.in);

	/** The ctrl. */
	StoreController ctrl = new StoreController();

	/** The crops. */
	Crop[] crops = new CropDataManager().listCrops().values()
			.toArray(new Crop[0]);

	/**
	 * Creates a StoreMenu object with the specified user.
	 *
	 * @param user the logged user
	 */
	public StoreMenu(User user) {
		this.user = user;
		String option;
		do {
			display(user);
			option = readOption();
		} while (selectOption(option)); // quit menu if false

	}

	/**
	 * This method displays a Store Menu to the logged user.
	 *
	 * @param u the logged in user
	 */
	public void display(User u) {
		System.out.printf("%n== Farm City :: Store ==");
		System.out.printf("%nWelcome, %s!", u.getFullName());
		System.out.printf("%nRank: %s\t\tGold: %d", u.getRank().toString(),
				u.getGold());
		System.out.println();
		System.out.printf("%nSeeds Available");
		System.out.println();
		listSeeds();
		System.out.println();
	}

	/**
	 * This method captures user input for choice of seeds to purchase.
	 *
	 * @return the choice of seeds
	 */
	public String readOption() {
		System.out.printf("%n[M]ain | Select choice > ");
		return sc.nextLine();
	}

	/**
	 * This method captures user input for quantity of seeds.
	 *
	 * @return the quantity of seeds to purchase
	 */
	public String readQuantity() {
		System.out.printf("   Enter quantity > ");
		return sc.nextLine();
	}

	/**
	 * This method displays available seeds in the store.
	 */
	public void listSeeds() {
		for (int i = 0; i < crops.length; i++) {
			System.out.println(i + 1 + ". " + crops[i].getName() + " costs: "
					+ crops[i].getCost() + " gold");
			System.out.println("   Harvests in: " + crops[i].getTime()
					+ " mins ");
			System.out.println("   XP Gained: " + crops[i].getXp());

		}
	}

	/**
	 * This method returns boolean value.
	 *
	 * @param option the choice of seeds to purchase
	 * @return false if user enters "M"
	 */
	private boolean selectOption(String option) {
		switch (option.toUpperCase()) {
		case "M":
			return false;
		default:
			return selectSeeds(option);

		}

	}

	/**
	 * This method returns boolean value.
	 *
	 * @param option  the choice of seeds to purchase
	 * @return false if the choice of seeds is valid
	 */
	private boolean selectSeeds(String option) {
		if (!StringUtils.isNumeric(option) || Integer.parseInt(option) <= 0
				|| Integer.parseInt(option) > crops.length) {
			System.out.println("Invalid Choice Option.");
			return true;
		}

		// ctrl.buySeeds(user, option);
		buy(option);
		return false;

	}

	/**
	 * This method calls the readQuantity method to get user's input for seeds
	 * quantity It also calls buySeeds method from store controller to execute
	 * buy seeds action.
	 *
	 * @param option  the choice of seeds to purchase
	 */
	private void buy(String option) {
		String q = readQuantity();
		if (StringUtils.isNumeric(q)) {
			int quantity = Integer.parseInt(q);
			String result = ctrl.buySeeds(user, option, quantity);
			System.out.println(result);
		} else {
			System.out.println("Please Enter a Valid Number!");
		}
	}
}

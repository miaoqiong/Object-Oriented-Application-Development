package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.entity.Inventory;
import is201.g5t08.farmcity.entity.User;

import java.util.List;
import java.util.Scanner;

/**
 * The InventoryMenu class represents the menu for function "Buy", "Gift". It
 * displays store menu and gift menu accordingly based on user input
 */
public class InventoryMenu {
	private User user;
	private static final Scanner sc = new Scanner(System.in);

	/**
	 * Creates a InventoryMenu object with the specified user
	 *
	 * @param user
	 *            the logged user
	 */
	public InventoryMenu(User user) {
		this.user = user;
		String option;
		do {
			display(user);
			option = readOption();
		} while (selectOption(option)); // quit menu if false
	}

	/**
	 * This method displays a Inventory Menu to the logged user
	 *
	 * @param user
	 *            the logged user
	 */
	private void display(User u) {
		System.out.printf("%n== Farm City :: My Inventory ==");
		System.out.printf("%nWelcome, %s!", u.getFullName());
		System.out.printf("%nRank: %s\t\tGold: %d", u.getRank().toString(),
				u.getGold());
		System.out.println();
		System.out.printf("%nMy Seeds:");

		List<Inventory> inventoryList = u.getInventoryList();
		int n = 1;
		for (Inventory inventory : inventoryList) {
			String plurality = "s";
			if (inventory.getQuantity() == 1) {
				plurality = "";
			}
			System.out.printf("%n%d. %d Bag%s of %s", n,
					inventory.getQuantity(), plurality, inventory.getSeed());
			n++;
		}

		System.out.println();
	}

	/**
	 * This method returns a boolean value It return false when user enter "M"
	 * or other invalid options
	 *
	 * @param option
	 *            the choice entered by the logged user
	 * @return false if user enters "M"
	 */
	private boolean selectOption(String option) {
		if (option.length() > 0) { // check if string is at least 1 character
			// long
			switch (option.toUpperCase().substring(0, 1)) { // switch statement
															// based on the
			// first character
			case "M":
				return false; // close this menu
			case "B":
				new StoreMenu(user);
				break; // harvest if first character is 'H'
			case "G":
				new GiftMenu(user);
				break; // plant crop if first character is 'P' then pass in the
			// remaining characters
			default:
				System.out.printf("%nInvalid menu option.%n");
			}
		}
		return true;
	}

	/**
	 * This method captures user's input in Inventory Menu
	 * 
	 * @return choice of inventory menu
	 */
	private String readOption() {
		System.out.printf("%n[M]ain | [B]uy | [G]ift | Select choice > ");
		return sc.nextLine();
	}
}

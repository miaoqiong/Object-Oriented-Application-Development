package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.control.GiftController;
import is201.g5t08.farmcity.entity.Inventory;
import is201.g5t08.farmcity.entity.User;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

/**
 * The GiftMenu class gets input from the user and display information to the
 * user
 */
public class GiftMenu {
	User user;
	private static final Scanner sc = new Scanner(System.in);
	GiftController gCtrl;

	/**
	 * Creates a GiftMenu object with the specified user
	 *
	 * @param user
	 *            the logged user
	 */
	public GiftMenu(User user) {
		this.user = user;
		gCtrl = new GiftController(user);
		String option;
		do {
			display(user);
			option = readOption();
		} while (selectGiftOption(option)); // quit menu if false

	}

	/**
	 * This method displays a Gift Menu to the logged user
	 *
	 * @param user
	 *            the logged user
	 */
	private void display(User u) {
		System.out.printf("%n== Farm City :: Send a Gift ==");
		System.out.printf("%nWelcome, %s!", u.getFullName());
		System.out.printf("%nRank: %s\t\tGold: %d", u.getRank().toString(),
				u.getGold());
		System.out.println();
		System.out.printf("%nGifts Available:");

		List<Inventory> inventoryList = u.getInventoryList();

		int n = 1;
		for (Inventory inventory : inventoryList) {
			if (inventory.getQuantity() != 0) {
				System.out.printf("%n%d. 1 Bag of %s Seeds", n,
						inventory.getSeed());
				n++;
			}
		}
	}

	/**
	 * This method captures user input for choice of gifts
	 *
	 * @return the choice of gift
	 */
	private String readOption() {
		System.out.printf("%n[M]ain | Select choice > ");
		return sc.nextLine();
	}

	/**
	 * This method captures user input for receiver of gifts
	 *
	 * @return the gift receiver
	 */
	public String getReceiver() {
		System.out.printf("Send to > ");
		return sc.nextLine();
	}

	/**
	 * This method returns a boolean value It return false when user enter "M"
	 * or other invalid options
	 *
	 * @param option
	 *            the choice of gifts entered by the logged user
	 * @return false
	 */
	private boolean selectGiftOption(String option) {
		if (option.toUpperCase().equals("M")) {
			return false;
		}
		List<Inventory> inventoryList = user.getInventoryList();
		if (!StringUtils.isNumeric(option) || Integer.parseInt(option) <= 0
				|| Integer.parseInt(option) > inventoryList.size()
				|| gCtrl.getAvaiQuantity(option) == 0) {
			System.out.println("Invalid Choice Option.");
			return true;
		}

		send(option);
		return false;

	}

	/**
	 * This method displays the message to user after user send the gifts
	 *
	 * @param option
	 *            the choice of gifts entered by the logged user
	 */
	private void send(String option) {
		String receiver = getReceiver();
		String result = gCtrl.validate(option, receiver);
		System.out.println(result);
	}

}

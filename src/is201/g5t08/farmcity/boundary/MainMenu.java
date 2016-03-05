/*
 * 
 */
package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.entity.User;

import java.util.Scanner;

/**
 * The MainMenu class is the boundary class for the user to choose between the
 * various sections of the game. They can access their friends list, their farm,
 * and the store from this menu.
 */
public class MainMenu {

	/** The currently logged in user. */
	private User user;

	/** The scanner used for user input for this menu. */
	private static final Scanner sc = new Scanner(System.in);

	/**
	 * Instantiates a new main menu. This menu runs on a loop. It will display
	 * the menu header and then prompt the user for their option. If the user
	 * selects a new menu, it will instantiate that menu. Otherwise, it will
	 * return false, breaking the loop and returning to the previous menu,
	 * logging the user out in the process.
	 *
	 * @param user
	 *            the user
	 */
	public MainMenu(User user) {
		this.user = user;
		String option;
		do {
			display();
			option = readOption();
		} while (selectOption(option));
	}

	/**
	 * Displays the menu header and various options for the user to choose from.
	 */
	private void display() {
		System.out.printf("%n== Farm City :: Main Menu ==");
		System.out.printf("%nWelcome, %s!%n", user.getFullName());
		System.out.printf("%n1. My Friends");
		System.out.printf("%n2. My Farm");
		System.out.printf("%n3. My Inventory");
		System.out.printf("%n4. Logout");
	}

	/**
	 * Reads the user option.
	 *
	 * @return the option
	 */
	public String readOption() {
		System.out.printf("%nEnter your choice > ");
		return sc.nextLine();
	}

	/**
	 * Select a new menu to open depending on the user's input. If no menu is
	 * selected, this method returns false and closes this menu, returning to
	 * the previous menu.
	 *
	 * @param option
	 *            the option
	 * @return true, run the menu loop again
	 */
	private boolean selectOption(String option) {
		switch (option) {
		case "1":
			new FriendsMenu(user);
			break;
		case "2":
			new FarmMenu(user);
			break;
		case "3":
			new InventoryMenu(user);
			break;
		case "4":
			return false;
		default:
			System.out.println("Invalid menu option.");
		}
		return true;
	}
}

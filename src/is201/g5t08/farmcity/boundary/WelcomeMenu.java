package is201.g5t08.farmcity.boundary;

import java.util.Scanner;

/**
 * The Class WelcomeMenu is the boundary class that the user first interacts
 * with. They can choose one of three options from here: to login, to register
 * or to quit the application.
 */
public class WelcomeMenu {

	/** The scanner used for user input for this menu. */
	private static final Scanner sc = new Scanner(System.in);

	/**
	 * Instantiates a new welcome menu. This menu runs on a loop that asks the
	 * user to pick an option. Depending on the user's selected option, a new
	 * menu may be instantiated, or false is returned - breaking the loop and
	 * closing this menu.
	 */
	public WelcomeMenu() {
		String option;
		do {
			display();
			option = readOption();
		} while (selectOption(option));
	}

	/**
	 * Displays the list of available options.
	 */
	private void display() {
		System.out.printf("%n== Farm City :: Welcome ==");
		System.out.printf("%nHi,");
		System.out.printf("%n1. Register");
		System.out.printf("%n2. Login");
		System.out.printf("%n3. Exit");
	}

	/**
	 * Ask the user to enter an option.
	 *
	 * @return the option
	 */
	private String readOption() {
		System.out.printf("%nEnter your choice > ");
		return sc.nextLine();
	}

	/**
	 * Decide which menu to open based on the user's selected option. If the
	 * user chooses option 3, this method returns false and this menu closes.
	 *
	 * @param option
	 *            the option
	 * @return true, if continue to show this menu
	 */
	private boolean selectOption(String option) {
		switch (option) {
		case "1":
			new RegistrationMenu();
			break;
		case "2":
			new LoginMenu();
			break;
		case "3":
			return false;
		default:
			System.out.printf("%nInvalid menu option.%n");
		}
		return true;
	}

}

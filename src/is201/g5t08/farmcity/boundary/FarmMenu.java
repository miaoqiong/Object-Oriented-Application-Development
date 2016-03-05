package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.control.FarmController;
import is201.g5t08.farmcity.entity.Plot;
import is201.g5t08.farmcity.entity.User;

import java.util.List;
import java.util.Scanner;

/**
 * The Class FarmMenu presents to the user a 4 choices to choose from. They are
 * able to Plant a crop, Clear a crop, Harvest their crops, and exit to the
 * previous menu..
 */
public class FarmMenu {

	/** The user. */
	private final User user;

	/** The scanner used for user input for this menu. */
	private static final Scanner sc = new Scanner(System.in);

	/** The farm controller. */
	FarmController farmController = new FarmController();

	/**
	 * Instantiates a new farm menu. The menu runs on a loop, and performs
	 * different actions depending on the user's input. Given a user's input of
	 * "M", the menu will quit and return to the previous menu. H will harvest
	 * all crops. P[number] will plant a crop at the numbered position.
	 * C[number] will clear a crop that the numbered position..
	 *
	 * @param user
	 *            the user
	 */
	public FarmMenu(User user) {
		this.user = user;
		String option;
		do {
			display();
			option = readOption();
		} while (selectOption(option));
	}

	/**
	 * Displays farm details to the user. This method retrieves user details
	 * from the user object and formats it.
	 */
	private void display() {
		System.out.printf("%n== Farm City :: My Farm ==");
		System.out.printf("%nWelcome, %s!", user.getFullName());
		System.out.printf("%nRank: %s\t\tGold: %d%n",
				user.getRank().toString(), user.getGold());
		System.out.printf("%nYou have %d plots of land",
				user.getNumberOfPlots());
		List<Plot> plotList = user.getPlotList();
		for (int i = 0; i < user.getNumberOfPlots(); i++) {
			if (plotList == null) {
				System.out.printf("%n%d. <empty>", i + 1);
			} else {
				Plot plot = plotList.get(i);
				if (plot == null) {
					System.out.printf("%n%d. <empty>", i + 1);
				} else {
					System.out.printf("%n%d. %s\t%s", i + 1, plot.getCrop()
							.getName(), plot.getProgress());
				}
			}
		}
	}

	/**
	 * Read user input.
	 *
	 * @return the user's selected option
	 */
	private String readOption() {
		System.out.printf("%n[M]ain | [P]lant | C[L]ear | [H]arvest > ");
		return sc.nextLine();
	}

	// The user can input a string of 1-2 characters.
	/**
	 * Select option. If the user selects something that is not "M", this method
	 * returns true and the loop will continue to run. Otherwise, this method
	 * will return false and will return the user to the previous menu. This
	 * menu will accept strings of more than one character length, in order for
	 * the user to be able to plant/clear on specific crops. It will use the
	 * first character as the menu option (e.g. plant, clear) then the remaining
	 * characters as the position of the plot to perform the action on.
	 *
	 * @param option
	 *            the option
	 * @return true, if the user has not quit the menu
	 */
	private boolean selectOption(String option) {
		if (option.length() > 0) { // check if string is at least 1 character
									// long
			switch (option.toUpperCase().substring(0, 1)) { // switch statement
															// based on the
															// first character
			case "M":
				return false;
			case "H":
				harvest();
				break;
			case "P":
				plant(option.substring(1));
				break;
			case "L":
				clear(option.substring(1));
				break;
			default:
				System.out.printf("%nInvalid menu option.%n");
			}
		}
		return true;
	}

	/**
	 * The Harvest method will send a message to the FarmController to perform
	 * the harvest method on the user.
	 */
	private void harvest() {
		String result = farmController.harvest(user);
		System.out.println(result);
	}

	/**
	 * The Plant method will send a message to the FarmController to perform the
	 * plant crop action on the user at a specific plot.
	 *
	 * @param option
	 *            the option
	 */
	private void plant(String option) {
		String result = farmController.plant(user, option);
		System.out.println(result);
	}

	/**
	 * The Clear method will send a message to the FarmController to perform the
	 * clear plot action on the user at a specific plot.
	 *
	 * @param option
	 *            the option
	 */
	private void clear(String option) {
		String result = farmController.clear(user, option);
		System.out.println(result);
	}

}

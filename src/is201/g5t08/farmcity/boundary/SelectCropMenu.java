package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.entity.Crop;
import is201.g5t08.farmcity.entity.User;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class SelectCropMenu presents to the user a list of crops to choose from
 * for planting.
 */
public class SelectCropMenu {

	/** The seeds. */
	private final Crop[] seeds;

	/** The Constant sc. */
	private static final Scanner sc = new Scanner(System.in);

	/**
	 * Instantiates a new select crop menu. Retrieves all the crops that the
	 * user has seeds for.
	 *
	 * @param user
	 *            the user
	 */
	public SelectCropMenu(User user) {
		this.seeds = user.getSeeds();
	}

	/**
	 * Shows the user the available crop choices, then reads user input. If no
	 * crop is chosen, return null and to the previous menu.
	 *
	 * @return the crop chosen by the user to plant
	 */
	public Crop readInput() {
		if (seeds.length < 1) { // if no seeds
			System.out
					.printf("You have no seeds available. Buy some from the store!");
			return null;
		}
		// show the user the available crop choices
		System.out.printf("%nSelect the crop:");
		for (int i = 0; i < seeds.length; i++) {
			System.out.printf("%n%d. %s", i + 1, seeds[i].getName());
		}
		System.out.printf("%n[M]ain | Select Choice > ");
		String option = sc.nextLine();
		switch (option.toUpperCase()) {
		case "M":
			return null;
		default:
			return selectCrop(option);
		}
	}

	/**
	 * Select crop method checks that the selected option is within ability of
	 * the user to plant (e.g. does not select the 4th crop if there are only
	 * three crops available, etc.).
	 *
	 * @param option
	 *            the option
	 * @return the chosen crop
	 */
	public Crop selectCrop(String option) {
		Crop crop = null;
		if (!StringUtils.isNumeric(option)) {
			System.out.printf("%nPlease enter a valid number.");
			return crop;
		}
		int index = Integer.parseInt(option);
		if (index <= seeds.length && index > 0
				&& (crop = seeds[index - 1]) != null) {
			return crop;
		} else {
			System.out.printf("%nThat is not a valid option for a crop.");
		}
		return crop;
	}
}

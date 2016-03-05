package is201.g5t08.farmcity;

import is201.g5t08.farmcity.boundary.WelcomeMenu;

import java.io.File;

/**
 * The Class FarmCityApp is the main entry point into the application.
 */
public final class FarmCityApp {

	/**
	 * This constructor is marked as private with an empty body as it is not
	 * meant to be instantiated.
	 */
	private FarmCityApp() {
	}

	/**
	 * The main method. Creates an instance of the WelcomeMenu.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		File cropFile = new File("data/crop.csv");
		if (!cropFile.exists()) {
			System.out
					.printf("File not found: %s.%nCannot run game without this file. Exiting...%n",
							cropFile.getPath());
			return;
		}
		File rankFile = new File("data/rank.csv");
		if (!rankFile.exists()) {
			System.out
					.printf("File not found: %s.%nCannot run game without this file. Exiting...%n",
							rankFile.getPath());
			return;
		}
		new WelcomeMenu();
	}
}

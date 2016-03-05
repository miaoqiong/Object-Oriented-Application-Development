package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.control.RegistrationController;

import java.util.Scanner;

/**
 * The RegistrationMenu class is the boundary class for the user to register an
 * account.
 */
public class RegistrationMenu {

	/** The scanner used for user input for this menu. */
	private static final Scanner sc = new Scanner(System.in);

	/**
	 * Instantiates a new registration menu. It will display the menu header
	 * once and then begin the registration process. This menu returns to the
	 * previous menu once it has completed execution.
	 */
	public RegistrationMenu() {
		display();
		register();
	}

	/**
	 * Begin registration process. Will prompt user for all user details. Once
	 * all the user details have been collected, submit the user details to the
	 * RegistrationController for processing. The result (including any errors)
	 * will be returned and printed to the user.
	 */
	private void register() {
		System.out.printf("%nEnter your username > ");
		String username = sc.nextLine();
		System.out.printf("Enter your full name > ");
		String fullName = sc.nextLine();
		System.out.printf("Enter your password > ");
		String password = sc.nextLine();
		System.out.printf("Confirm your password > ");
		String confirmPassword = sc.nextLine();

		RegistrationController rc = new RegistrationController();
		String result = rc.register(username, fullName, password,
				confirmPassword); // ...register user
		System.out.print(result);
	}

	/**
	 * Displays the menu header.
	 */
	private void display() {
		System.out.printf("%n== Farm City  :: Registration ==");
	}
}

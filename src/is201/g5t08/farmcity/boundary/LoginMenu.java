package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.control.LoginController;
import is201.g5t08.farmcity.entity.User;

import java.util.Scanner;

/**
 * The RegistrationMenu class is the boundary class for the user to login with
 * their user credentials.
 */
public class LoginMenu {

	/** The scanner used for user input for this menu. */
	private static final Scanner sc = new Scanner(System.in);

	/**
	 * Instantiates a new login menu. It will display the menu header once and
	 * then prompt the user for their login credentials. If the user
	 * successfully logs in, the MainMenu will be instantiated. Otherwise, it
	 * will return to the previous menu upon completing execution.
	 */
	public LoginMenu() {
		login();
	}

	/**
	 * Begin Login process. Prompt the user for their username and password. The
	 * username and password is passed to the LoginController for processing. If
	 * the username and password combination is valid, the MainMenu is
	 * instantiated. Otherwise, the error result is printed.
	 */
	private void login() {
		System.out.printf("%nEnter your username > ");
		String username = sc.nextLine();
		System.out.printf("Enter your password > ");
		String password = sc.nextLine();

		LoginController loginController = new LoginController();
		User user = loginController.login(username, password);
		if (user == null) {
			System.out
					.printf("%nThe username or password you entered is incorrect."); // login
		} else {
			new MainMenu(user); //failed
		}
		System.out.println();
	}
}

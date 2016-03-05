package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.dao.UserDataManager;
import is201.g5t08.farmcity.entity.User;

/**
 * The Class RegistrationController.
 */
public class RegistrationController {

	/**
	 * Register controls the flow of the user registration action. First,
	 * several validations are run on the user's input (e.g. if username is
	 * already taken, if username is not alphanumeric, if user password and
	 * confirmation do not match). When the user object passes all validations,
	 * it is saved to disk and will feedback to the user that their account has
	 * been created. Otherwise, an error message will be returned, informing the
	 * user what their error was.
	 *
	 * @param username
	 *            the username
	 * @param fullName
	 *            the full name
	 * @param password
	 *            the password
	 * @param confirmPassword
	 *            the password confirmation
	 * @return the result
	 */
	public String register(String username, String fullName, String password,
			String confirmPassword) {

		// Validate user input
		if (!UserValidator.validateUsernameUniqueness(username)) {
			return String.format("%nSomeone already has that username.");
		}
		if (!UserValidator.validateUsernameCharacters(username)) {
			return String
					.format("%nPlease use only letters (a-z) and numbers in your username.");
		}
		if (!UserValidator.validatePassword(password, confirmPassword)) {
			return String.format("%nYour passwords don't match.");
		}

		UserDataManager udm = new UserDataManager();
		User user = new User(username, fullName, password);
		udm.update(user);
		return String.format("Hi, %s! Your account is successfully created!%n",
				username);
	}
}

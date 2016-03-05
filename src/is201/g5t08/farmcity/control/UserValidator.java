package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.dao.UserDataManager;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class UserValidator is a utility class that validates the user object for
 * registration and login purposes.
 */
public final class UserValidator {

	/**
	 * This constructor is marked as private with an empty body as it is not
	 * meant to be instantiated.
	 */
	private UserValidator() {
	}

	/**
	 * Validate username characters.
	 *
	 * @param username
	 *            the username
	 * @return true, if username consists of only alphanumeric characters.
	 */
	public static boolean validateUsernameCharacters(String username) {
		if (StringUtils.isAlphanumeric(username)) {
			return true;
		}
		return false;
	}

	/**
	 * Validate username uniqueness.
	 *
	 * @param username
	 *            the username
	 * @return true, if username has not yet been taken
	 */
	public static boolean validateUsernameUniqueness(String username) {
		UserDataManager udm = new UserDataManager();
		if (udm.findByUsername(username) == null) {
			return true;
		}
		return false;
	}

	/**
	 * Validate the password is equal to the password confirmation.
	 *
	 * @param password
	 *            the password
	 * @param confirmPassword
	 *            the password confirmation
	 * @return true, if the password is equal to password confirmation
	 */
	public static boolean validatePassword(String password,
			String confirmPassword) { // check for equality
		if (password.equals(confirmPassword)) { // if equal...
			return true; // ...password is valid
		}
		return false; // else, password does not equal confirmation
	}
}

package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.dao.UserDataManager;
import is201.g5t08.farmcity.entity.User;

/**
 * The Class LoginController controls the flow of the login process, checking
 * that the user passes all validations (e.g. user exist, password is the same).
 */
public class LoginController {

	/**
	 * Login retrieves the requested user object from the username. If user
	 * exists, check if the password matches. If both succeed, return the user
	 * object. Otherwise, return null.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the user
	 */
	public User login(String username, String password) {
		UserDataManager udm = new UserDataManager();
		User user = udm.findByUsername(username); // retrieve requested user
													// object
		// check if user exists AND password is the correct
		if (user != null
				&& UserValidator.validatePassword(user.getPassword(), password)) {
			return user;
		}
		return null;
	}

}

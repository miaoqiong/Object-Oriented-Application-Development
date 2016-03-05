package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.dao.FriendsDataManager;
import is201.g5t08.farmcity.dao.UserDataManager;
import is201.g5t08.farmcity.entity.Friends;
import is201.g5t08.farmcity.entity.User;

import org.apache.commons.lang3.StringUtils;

/**
 * A FriendController does all the validation and controls the
 * FriendsDataManager
 */
public class FriendsController {
	User user;
	FriendsDataManager fdm;
	UserDataManager udm;

	/**
	 * Creates a FriendsContoller object with the specified user
	 *
	 * @param user
	 *            the user
	 */
	public FriendsController(User user) {
		this.user = user;
		fdm = new FriendsDataManager(user);
		udm = new UserDataManager();
	}

	/**
	 * This method checks for valid user input by checking the number of friends
	 * the user have It returns error messages if user enters invalid input.
	 * Else, this method returns successfully unfriended friends.
	 *
	 * @param user
	 *            the user
	 * @param input
	 *            the choice of the user
	 * @return message
	 */
	public String unfriend(User user, String input) {
		if (!StringUtils.isNumeric(input)) {
			return String.format(String
					.format("%nPlease enter a valid number."));
		} else if (input.length() > 0) {
			try {
				String receiver;
				Friends friend = fdm.getFriends().get(
						Integer.parseInt(input) - 1);
				if (friend.getSender().equals(user.getUsername())
						&& friend.getStatus().equals("accepted")) {
					receiver = friend.getReceiver();
					fdm.unfriend(user, receiver);
					return String.format("You have unfriended %s.", receiver);
				} else if (friend.getReceiver().equals(user.getUsername())
						&& friend.getStatus().equals("accepted")) {
					receiver = friend.getSender();
					fdm.unfriend(user, receiver);
					return String.format("You have unfriended %s.", receiver);
				}

			} catch (NumberFormatException e) {
				return String.format("%nPlease type a valid number.%n");
			} catch (Exception e) {
				return String.format("%nYou do not have that much friends!%n");
			}

		} else {
			return String
					.format("%nYou need to specify a friend to unfriend!%n");
		}
		return "";
	}

	/**
	 * This method checks for valid receiver It returns error messages if user
	 * enters invalid receiver. Else, this method returns successfully sent
	 * friend request
	 *
	 * @param user
	 *            the user
	 * @param receiver
	 *            the receiver of friend's request
	 * @return messages
	 */
	public String request(User user, String receiver) {
		try {
			if (udm.findByUsername(receiver) == null) {
				return String.format("Username does not exist!");
			} else if (receiver.equals(user.getUsername())) {
				return String.format("Unable to send request to yourself!");
			} else {
				boolean request = false;
				boolean friend = false;
				fdm.load();
				for (int i = 0; i < fdm.getCSV().size() && request == false
						&& friend == false; i++) {
					Friends f = fdm.getCSV().get(i);
					if (f.getSender().equals(user.getUsername())
							&& f.getReceiver().equals(receiver)
							&& f.getStatus().equals("accepted")
							|| f.getReceiver().equals(user.getUsername())
							&& f.getSender().equals(receiver)
							&& f.getStatus().equals("accepted")) {
						friend = true;
						return String.format("You are already a friend of %s.",
								receiver);

					}

					else if (f.getSender().equals(user.getUsername())
							&& f.getReceiver().equals(receiver)
							&& f.getStatus().equals("request")) {
						request = true;
						return String.format(
								"You have already sent a request to %s.",
								receiver);
					} else if (f.getSender().equals(receiver)
							&& f.getReceiver().equals(user.getUsername())) {
						fdm.addAccept(user, receiver);
						request = true;
						return String
								.format("%s is now your friend!", receiver);
					}

				}
				if (request == false && friend == false) {
					fdm.add(user, receiver);
					return String.format("A friend request is sent to %s.",
							receiver);
				}
			}
			return "";
		} catch (Exception e) {
			return String.format("Invalid username");
		}
	}

	/**
	 * This method checks for valid user input by checking the number of
	 * requests the user have It returns error messages if user enters invalid
	 * input. Else, this method returns successfully accepted friend request.
	 *
	 * @param user
	 *            the user
	 * @param input
	 *            the choice of the user
	 * @return messages
	 */
	public String accept(User user, String input) {
		if (!StringUtils.isNumeric(input)) {
			return String.format(String
					.format("%nPlease enter a valid number."));
		} else if (Integer.parseInt(input) > (fdm.getRequests().size() + fdm
				.getFriends().size())) {
			return String.format("%nYou do not have that much requests!%n");
		} else if (input.length() > 0) {
			try {
				String receiver = fdm
						.getRequests()
						.get(Integer.parseInt(input) - 1
								- fdm.getFriends().size()).getSender();
				fdm.addAccept(user, receiver);
				return String.format(receiver + " is now your friend.");
			} catch (NumberFormatException e) {
				return String.format("%nPlease type a valid number.%n");
			} catch (Exception e) {
				return String.format("%nPlease enter a valid number.%n");

			}

		} else {
			return String
					.format("%nYou need to specify a request to accept!%n");
		}
	}

	/**
	 * This method checks for valid user input by checking the number of
	 * requests the user have It returns error messages if user enters invalid
	 * input. Else, this method returns successfully rejected friend request.'
	 *
	 * @param user
	 *            the user
	 * @param input
	 *            the choice of the user
	 * @return messages
	 */
	public String reject(User user, String input) {
		if (!StringUtils.isNumeric(input)) {
			return String.format(String
					.format("%nPlease enter a valid number."));
		} else if (Integer.parseInt(input) > (fdm.getRequests().size() + fdm
				.getFriends().size())) {
			return String.format("%nYou do not have that much requests!%n");
		} else if (input.length() > 0) {
			try {
				String receiver = fdm
						.getRequests()
						.get(Integer.parseInt(input) - 1
								- fdm.getFriends().size()).getSender();
				fdm.remove(user, receiver);
				return String.format("You have rejected %s's friend request.",
						receiver);
			} catch (NumberFormatException e) {
				return String.format("%nPlease type a valid number.%n");
			} catch (Exception e) {
				return String.format("%nPlease enter a valid number!%n");

			}
		} else {
			return String
					.format("%nYou need to specify a request to reject!%n");
		}
	}
}

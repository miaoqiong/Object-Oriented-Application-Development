package is201.g5t08.farmcity.boundary;

import is201.g5t08.farmcity.control.FriendsController;
import is201.g5t08.farmcity.dao.FriendsDataManager;
import is201.g5t08.farmcity.entity.Friends;
import is201.g5t08.farmcity.entity.User;

import java.util.List;
import java.util.Scanner;

/**
 * A FriendMenu gets input from the user and display information to the user
 */
public class FriendsMenu {
	private User user;
	private static final Scanner sc = new Scanner(System.in);
	private FriendsDataManager fdm;
	private FriendsController fCtrl;

	/**
	 * Creates a FriendsMenu object with the specified user
	 *
	 * @param user
	 *            the user
	 */
	public FriendsMenu(User user) {
		this.user = user; // save user as an instance variable
		fdm = new FriendsDataManager(user);
		fCtrl = new FriendsController(user);
		String option;
		do {
			display(); // display friends and requests
			option = readOption(); // prompt user to enter an option
		} while (selectOption(option)); // quit menu if false
	}

	/**
	 * This method displays all the friends and requests that belongs to the
	 * user
	 */
	public void display() {
		System.out.printf("%n== Farm City :: My Friends ==");
		System.out.printf("%nWelcome, %s!", user.getFullName());
		System.out.println();

		fdm.load();
		List<Friends> friendsList = fdm.getCSV();

		System.out.println("My Friends:");
		int num = 1;
		for (int i = 0; i < friendsList.size(); i++) {
			Friends f = friendsList.get(i);
			if (f.getSender().equals(user.getUsername())
					&& f.getStatus().equals("accepted")) {
				System.out.println(num++ + ". " + f.getReceiver());
				fdm.getFriends().add(f);
			} else if (f.getReceiver().equals(user.getUsername())
					&& f.getStatus().equals("accepted")) {
				System.out.println(num++ + ". " + f.getSender());
				fdm.getFriends().add(f);
			}
		}
		System.out.println();

		System.out.println("My Requests:");
		for (int i = 0; i < friendsList.size(); i++) {
			Friends f = friendsList.get(i);
			if (f.getReceiver().equals(user.getUsername())
					&& f.getStatus().equals("request")) {
				System.out.println(num++ + ". " + f.getSender());
				fdm.getRequests().add(f);
			}
		}
	}

	/**
	 * This method captures user input for choice of gifts.
	 *
	 * @return the string
	 */
	public String readOption() {
		System.out
				.printf("%n[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject > ");
		return sc.nextLine();
	}

	/**
	 * This method returns a boolean value It return false when user enter "M"
	 * or other invalid options
	 *
	 * @param option
	 *            the choice entered by the logged user
	 * @return false
	 */

	private boolean selectOption(String option) {
		if (option.length() == 0) {
			System.out.println("Please enter an option!");
			return true;
		} else {
			switch (option.toUpperCase().charAt(0)) { // switch based on first
														// character user input
			case 'M':
				return false; // quit menu
			case 'U':
				unfriend(option.substring(1)); // unfriend if first character is
												// 'U' and unfriend the friend
												// based on remaining character
				break;
			case 'Q':
				request(); // request if first character is 'Q';
				break;
			case 'A':
				accept(option.substring(1)); // accept if first character is 'A'
												// and accept the friend request
												// based on remaining character
				break;
			case 'R':
				reject(option.substring(1)); // reject if first character is 'R'
												// and reject the friend request
												// based on remaining character
				break;
			default:
				System.out.println("Invalid option.");
			}
			return true;
		}
	}

	/**
	 * This method display the message from the unfriend method from
	 * FriendsController.
	 *
	 * @param option the option
	 */
	public void unfriend(String option) {
		String result = fCtrl.unfriend(user, option);
		System.out.println(result);
	}

	/**
	 * This method display the message from the request method from
	 * FriendsController.
	 */
	public void request() {
		System.out.print("Enter the username > ");
		String username = sc.nextLine();
		String result = fCtrl.request(user, username);
		System.out.println(result);
	}

	/**
	 * This method display the message from the accept method from
	 * FriendsController.
	 *
	 * @param option the option
	 */
	public void accept(String option) {
		String result = fCtrl.accept(user, option);
		System.out.println(result);
	}

	/**
	 * This method display the message from the reject method from
	 * FriendsController.
	 *
	 * @param option the option
	 */
	public void reject(String option) {
		String result = fCtrl.reject(user, option);
		System.out.println(result);
	}

}

package is201.g5t08.farmcity.dao;

import is201.g5t08.farmcity.entity.Friends;
import is201.g5t08.farmcity.entity.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

// TODO: Auto-generated Javadoc
/**
 * A FriendDataManager gets the information from the CSV.
 */
public class FriendsDataManager {
	
	/** The udm. */
	UserDataManager udm;
	
	/** The Constant fileName. */
	private static final String fileName = "data/friendsrequest.csv";
	
	/** The u. */
	private final User u;
	
	/** The friends list. */
	private final List<Friends> friendsList = new ArrayList<Friends>();

	/**
	 * Creates a FriendsDataManager object with the specified user.
	 *
	 * @param u the user
	 */
	public FriendsDataManager(User u) {
		udm = new UserDataManager();
		this.u = u;
		load();
	}

	/**
	 * Gets the list of friends in the CSV.
	 *
	 * @return the list of friends
	 */
	public List<Friends> getCSV() {
		return friendsList;
	}

	/**
	 * Gets the list of friends of the user.
	 *
	 * @return the list of friends
	 */
	public List<Friends> getFriends() {
		List<Friends> fList = new ArrayList<Friends>();
		for (int i = 0; i < getCSV().size(); i++) {
			Friends f = getCSV().get(i);
			if (f.getSender().equals(u.getUsername())
					&& f.getStatus().equals("accepted")
					|| f.getReceiver().equals(u.getUsername())
					&& f.getStatus().equals("accepted")) {
				fList.add(f);
			}
		}
		return fList;
	}

	/**
	 * Gets the list of requests of the user.
	 *
	 * @return the list of friend requests
	 */
	public List<Friends> getRequests() {
		List<Friends> rList = new ArrayList<Friends>();
		for (int i = 0; i < getCSV().size(); i++) {
			Friends f = getCSV().get(i);
			if (f.getReceiver().equals(u.getUsername())
					&& f.getStatus().equals("request")) {
				rList.add(f);
			}
		}

		return rList;
	}

	/**
	 * This method read the CSV file and add them to an List.
	 *
	 * @return Error messages if there's exception
	 */
	public String load() {
		try {
			friendsList.clear();
			CSVReader reader = new CSVReader(new FileReader(fileName));
			reader.readNext(); // skip first line
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				try {
					String sender = nextLine[0].trim();
					String receiver = nextLine[1].trim();
					String status = nextLine[2].trim();

					Friends f = new Friends(udm.findByUsername(sender),
							udm.findByUsername(receiver), status);
					friendsList.add(f);
				} catch (Exception e) {
					reader.close();
					return String.format("invalid input");
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			try {
				CSVWriter writer = new CSVWriter(
						new FileWriter(fileName, false), ',',
						CSVWriter.NO_QUOTE_CHARACTER);
				String[] headers = { "Sender", "Receiver", "Status" };
				writer.writeNext(headers);
				writer.close();
				load();
			} catch (IOException ee) {
				System.out.printf("%nUnexpected IO error");
			}
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
		return "";
	}

	/**
	 * This method loop through the list of friends and remove friend to be
	 * unfriended After removing from the list of friends, save it to the CSV.
	 *
	 * @param user            the user
	 * @param username            the username of friend that user wants to unfriend
	 */
	public void unfriend(User user, String username) {
		try {
			List<Friends> newList = new ArrayList<Friends>();
			CSVWriter writer = new CSVWriter(new FileWriter(fileName, false),
					',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "Sender", "Receiver", "Status" };
			writer.writeNext(headers);
			for (int i = 0; i < friendsList.size(); i++) {
				Friends friend = friendsList.get(i);
				if (!(friend.getSender().equals(user.getUsername())
						&& friend.getReceiver().equals(username)
						&& friend.getStatus().equals("accepted") || friend
						.getSender().equals(username)
						&& friend.getReceiver().equals(user.getUsername())
						&& friend.getStatus().equals("accepted"))) {
					String[] entries = { friend.getSender(),
							friend.getReceiver(), friend.getStatus() };
					newList.add(friend);
					writer.writeNext(entries);
				}
			}
			friendsList.clear();
			for (int i = 0; i < newList.size(); i++) {
				Friends friend = newList.get(i);
				friendsList.add(friend);
			}
			writer.close();

		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}

	}

	/**
	 * This method add the friend request to the CSV.
	 *
	 * @param user            the user
	 * @param username            the username of a user that user wants to send friend request
	 *            to
	 */
	public void add(User user, String username) {

		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName, true),
					',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] entries = { user.getUsername(), username, "request" };
			writer.writeNext(entries);
			writer.close();

		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}

	}

	/**
	 * This method add the accepted friends to the CSV.
	 *
	 * @param user            the user
	 * @param username            the sender of friend request that user wants to accept
	 */
	public void addAccept(User user, String username) {

		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName, false),
					',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "Sender", "Receiver", "Status" };
			writer.writeNext(headers);
			for (int i = 0; i < friendsList.size(); i++) {
				Friends f = friendsList.get(i);
				if (f.getSender().equals(username)
						&& f.getReceiver().equals(user.getUsername())
						&& f.getStatus().equals("request")) {
					String[] entries = { f.getSender(), f.getReceiver(),
							"accepted" };
					writer.writeNext(entries);
					f.setStatus("accepted");
				} else {
					String[] entries = { f.getSender(), f.getReceiver(),
							f.getStatus() };
					writer.writeNext(entries);
				}

			}
			writer.close();

		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}

	}

	/**
	 * This method loop through the list of requests and remove friend to be
	 * rejected After removing from the list of requests, save it to the CSV.
	 *
	 * @param user            the user
	 * @param username            the sender of friend request that user wants to reject
	 */
	public void remove(User user, String username) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName, false),
					',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "Sender", "Receiver", "Status" };
			writer.writeNext(headers);
			Friends f = null;
			for (int i = 0; i < friendsList.size(); i++) {
				Friends friend = friendsList.get(i);
				if (friend.getSender().equals(username)
						&& friend.getReceiver().equals(user.getUsername())
						&& friend.getStatus().equals("request")) {
					f = friend;
				} else {
					String[] entries = { friend.getSender(),
							friend.getReceiver(), friend.getStatus() };
					writer.writeNext(entries);
				}
			}
			friendsList.remove(f);

			writer.close();

		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}

	}

}

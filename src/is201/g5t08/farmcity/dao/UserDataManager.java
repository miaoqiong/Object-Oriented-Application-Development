package is201.g5t08.farmcity.dao;

import is201.g5t08.farmcity.entity.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * The Class UserDataManager stores data about all the users in a key-value
 * store, with the user's username as the key and the User as the value..
 */
public class UserDataManager {

	/**
	 * The Constant fileName is the relative path of where the user data will be
	 * stored.
	 */
	private static final String fileName = "data/user.csv";

	/** The users. */
	private final Map<String, User> users = new HashMap<String, User>();

	/**
	 * Instantiates a new user data manager, loading the user data from the csv
	 * into the users hashmap.
	 */
	public UserDataManager() {
		load();
	}

	/**
	 * Updates a user's data, then writes it to the csv data file.
	 *
	 * @param user
	 *            the user
	 */
	public void update(User user) {
		users.put(user.getUsername(), user);
		write();
	}

	/**
	 * Writes the user's data to a csv-formatted file.
	 */
	private void write() {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "Username", "FullName", "Password", "Xp",
					"Gold" };
			writer.writeNext(headers);
			for (User user : users.values()) {
				String[] entries = { user.getUsername(), user.getFullName(),
						user.getPassword(), String.valueOf(user.getXp()),
						String.valueOf(user.getGold()) };
				writer.writeNext(entries);
			}
			writer.close();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
	}

	/**
	 * Reads a csv-formatted file and loads the user data into the users
	 * HashMap.
	 */
	private void load() {
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName));
			reader.readNext(); // skip first line
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				try {
					String username = nextLine[0].trim();
					String fullName = nextLine[1].trim();
					String password = nextLine[2].trim();
					int xp = Integer.parseInt(nextLine[3].trim());
					int gold = Integer.parseInt(nextLine[4].trim());
					User user = new User(username, fullName, password, xp, gold);
					users.put(username, user);
				} catch (NumberFormatException e) {
					System.out.println("Unexpected number format");
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			write();
			load();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
		InventoryDataManager idm = new InventoryDataManager();
		PlotDataManager pdm = new PlotDataManager(this);
		for (User user : users.values()) {
			String username = user.getUsername();
			user.setInventoryList(idm.findInventoryListByUsername(username));
			user.setPlotList(pdm.findByUsername(username));
		}
		write();
	}

	/**
	 * Find a user by username.
	 *
	 * @param username
	 *            the username
	 * @return the user
	 */
	public User findByUsername(String username) {
		return users.get(username);
	}
}

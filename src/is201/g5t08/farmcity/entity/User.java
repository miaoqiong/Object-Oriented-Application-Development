package is201.g5t08.farmcity.entity;

import is201.g5t08.farmcity.control.RankCalculator;
import is201.g5t08.farmcity.dao.CropDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The User class presents a player in the game. The player is uniquely
 * identified by their username. A user can earn xp and gold by planting crops
 * in their farm. The number of crops they can plant at any one time is
 * determined by the user's rank.
 */
public class User {

	/**
	 * The user's uniquely identifiable username. The username should only
	 * contain alphanumeric characters.
	 */
	private String username;

	/** The user's full name. */
	private String fullName;

	/**
	 * The user's password. It is compared with user input when the user
	 * attempts to login.
	 */
	private String password;

	/** The total number of experience points earned by the user. */
	private int xp;

	/** The total amount of gold held by the user. */
	private int gold;

	/** The user's inventory list. Contains the list of seeds held by the user. */
	private List<Inventory> inventoryList;

	/**
	 * The user's list of plots. The user can plant and harvest crops in plots
	 * to earn xp and gold.
	 */
	private List<Plot> plotList;

	/**
	 * Instantiates a new user with the specified username, full name and
	 * password. Upon successful registration, the user gets 1000 gold coins and
	 * starts with 0 xp.
	 *
	 * @param username
	 *            the username
	 * @param fullName
	 *            the full name
	 * @param password
	 *            the password
	 */
	public User(String username, String fullName, String password) {
		this(username, fullName, password, 0, 1000);
	}

	/**
	 * Instantiates a new user with the specified username, full name, password,
	 * xp and gold.
	 *
	 * @param username
	 *            the username
	 * @param fullName
	 *            the full name
	 * @param password
	 *            the password
	 * @param xp
	 *            the total number of experience points earned by the user
	 * @param gold
	 *            the total number of gold held by the user
	 */
	public User(String username, String fullName, String password, int xp,
			int gold) {
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.xp = xp;
		this.gold = gold;
	}

	/**
	 * Gets the user's username.
	 *
	 * @return the user's username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user's username.
	 *
	 * @param username
	 *            the user's new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the user's full name.
	 *
	 * @return the user's full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the user's full name.
	 *
	 * @param fullName
	 *            the user's new full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Gets the user's password.
	 *
	 * @return the user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's password.
	 *
	 * @param password
	 *            the user's new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the total number of experience points earned by the user.
	 *
	 * @return the total number of experience points earned by the user
	 */
	public int getXp() {
		return this.xp;
	}

	/**
	 * Sets the total number of experience points earned by the user.
	 *
	 * @param xp
	 *            the new xp amount
	 */
	public void setXp(int xp) {
		this.xp = xp;
	}

	/**
	 * Gets the total amount of gold held by the user.
	 *
	 * @return the total amount of gold held by the user
	 */
	public int getGold() {
		return this.gold;
	}

	/**
	 * Sets the total amount of gold held by the user.
	 *
	 * @param gold
	 *            the new gold amount
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * Adds a number of experience points to the user's total.
	 *
	 * @param xp
	 *            the number of experience points to be added
	 */
	public void addXp(int xp) {
		this.xp += xp;
	}

	/**
	 * Adds a number of gold to the user's total.
	 *
	 * @param gold
	 *            the amount of gold to be added
	 */
	public void addGold(int gold) {
		this.gold += gold;
	}

	/**
	 * Gets the rank currently achieved by the user.
	 *
	 * @return the rank currently achieved by the user
	 */
	public Rank getRank() {
		return RankCalculator.calculateRank(this);
	}

	/**
	 * Gets the number of plots owned by the user.
	 *
	 * @return the number of plots owned by the user
	 */
	public int getNumberOfPlots() {
		return getRank().getNumberOfPlots();
	}

	/**
	 * Gets the user's inventory list.
	 *
	 * @return the user's inventory list
	 */
	public List<Inventory> getInventoryList() {
		return inventoryList;
	}

	/**
	 * Sets the inventory list.
	 *
	 * @param inventoryList
	 *            the new inventory list
	 */
	public void setInventoryList(List<Inventory> inventoryList) {
		this.inventoryList = inventoryList;
	}

	/**
	 * Gets the list of plots owned by the user.
	 *
	 * @return the list of plots owned by the user
	 */
	public List<Plot> getPlotList() {
		return plotList;
	}

	/**
	 * Sets the list of plots owned by the user.
	 *
	 * @param plotList
	 *            the new list of plots owned by the user
	 */
	public void setPlotList(List<Plot> plotList) {
		this.plotList = plotList;
	}

	/**
	 * Gets the types of seeds held by the user.
	 *
	 * @return the types of seeds held by the user
	 */
	public Crop[] getSeeds() {
		CropDataManager cdm = new CropDataManager();
		List<Crop> crops = new ArrayList<Crop>();
		for (Inventory inventory : inventoryList) {
			if (inventory.getQuantity() > 0) {
				Crop crop = cdm.findByName(inventory.getSeed());
				crops.add(crop);
			}
		}
		return crops.toArray(new Crop[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			User other = (User) o;
			return username.equals(other.getUsername());
		}
		return false;
	}

}

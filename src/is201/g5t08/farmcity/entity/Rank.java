package is201.g5t08.farmcity.entity;

/**
 * The Rank class represents a particular level of rank. Each successive rank is
 * achieved when the user's experience points hits the requisite number of
 * experience points to obtain the rank. The number of plots of land that a user
 * owns is dependent on their rank.
 */
public class Rank {

	/** The name of the rank. */
	private final String name;

	/**
	 * The number of Experience points required by the player to obtain the
	 * rank.
	 */
	private final int xp;

	/**
	 * The number of plots of land a player of a particular rank owns of land a player of a particular rank owns.
	 */
	private final int numberOfPlots;

	/**
	 * Instantiates a new rank.
	 *
	 * @param name the name of the rank
	 * @param xp the number of Experience points required by the player to obtain the rank
	 * @param numberOfPlots the number of plots
	 */
	public Rank(String name, int xp, int numberOfPlots) {
		this.name = name;
		this.xp = xp;
		this.numberOfPlots = numberOfPlots;
	}

	/**
	 * Gets the name of the rank.
	 *
	 * @return the name of the rank
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the number of Experience points required by the player to obtain the
	 * rank.
	 *
	 * @return the number of Experience points required by the player to obtain
	 *         the rank
	 */
	public int getXp() {
		return xp;
	}

	/**
	 * Gets the number of plots of land a player of a particular rank owns.
	 *
	 * @return the number of plots of land a player of a particular rank owns
	 */
	public int getNumberOfPlots() {
		return numberOfPlots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}
}

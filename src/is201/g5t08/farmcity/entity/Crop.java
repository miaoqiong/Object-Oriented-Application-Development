package is201.g5t08.farmcity.entity;

/**
 * The Crop class represents a type of crop. Crop seeds can be bought from the
 * store and be planted by users in their farm. After waiting for a set amount
 * of time, the crop matures and can be harvested by the user, earning them
 * experience points and gold.
 */
public class Crop {

	/** The name of the crop. */
	private final String name;

	/**
	 * The number of gold coins required to purchase one bag of seed of the
	 * crop.
	 */
	private final int cost;

	/**
	 * The time in minutes it takes for the specific seed planted to grow to
	 * maturity.
	 */
	private final int time;

	/** The number of experience points earned for one unit of crop produced. */
	private final int xp;

	/**
	 * The minimum yield (in units) produced by planting one bag of seed of the
	 * given crop.
	 */
	private final int minYield;

	/**
	 * The maximum yield (in units) produced by planting one bag of seed of the
	 * given crop.
	 */
	private final int maxYield;

	/** The number of gold coins one unit of produce gives the farmer. */
	private final int salePrice;

	/**
	 * Instantiates a new crop.
	 *
	 * @param name
	 *            the name of the crop
	 * @param cost
	 *            the number of gold coins required to purchase one bag of seed
	 *            of the crop
	 * @param time
	 *            the time in minutes it takes for the specific seed planted to
	 *            grow to maturity
	 * @param xp
	 *            the number of experience points earned for one unit of crop
	 *            produced
	 * @param minYield
	 *            the minimum yield (in units) produced by planting one bag of
	 *            seed of the given crop
	 * @param maxYield
	 *            the maximum yield (in units) produced by planting one bag of
	 *            seed of the given crop
	 * @param salePrice
	 *            the number of gold coins one unit of produce gives the farmer
	 */
	public Crop(String name, int cost, int time, int xp, int minYield,
			int maxYield, int salePrice) {
		this.name = name;
		this.cost = cost;
		this.time = time;
		this.xp = xp;
		this.minYield = minYield;
		this.maxYield = maxYield;
		this.salePrice = salePrice;
	}

	/**
	 * Gets the name of the crop.
	 *
	 * @return the name of the crop
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the number of gold coins required to purchase one bag of seed of the
	 * crop.
	 *
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Gets the time in minutes it takes for the specific seed planted to grow
	 * to maturity.
	 *
	 * @return the time in minutes it takes for the specific seed planted to
	 *         grow to maturity
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Gets the number of experience points earned for one unit of crop
	 * produced.
	 *
	 * @return the number of experience points earned for one unit of crop
	 *         produced
	 */
	public int getXp() {
		return xp;
	}

	/**
	 * Gets the minYield the minimum yield (in units) produced by planting one
	 * bag of seed of the given crop.
	 *
	 * @return the minYield the minimum yield (in units) produced by planting
	 *         one bag of seed of the given crop
	 */
	public int getMinYield() {
		return minYield;
	}

	/**
	 * Gets the maximum yield (in units) produced by planting one bag of seed of
	 * the given crop.
	 *
	 * @return the maximum yield (in units) produced by planting one bag of seed
	 *         of the given crop
	 */
	public int getMaxYield() {
		return maxYield;
	}

	/**
	 * Gets the number of gold coins one unit of produce gives the farmer.
	 *
	 * @return the number of gold coins one unit of produce gives the farmer
	 */
	public int getSalePrice() {
		return salePrice;
	}
}

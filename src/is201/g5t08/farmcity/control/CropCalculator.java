package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.entity.Crop;

import java.util.Random;

/**
 * The Class CropCalculator is a utility class to help calculate the crop yield
 * when harvesting.
 */
public final class CropCalculator {

	/**
	 * This constructor is marked as private with an empty body as it is not
	 * meant to be instantiated.
	 */
	private CropCalculator() {
	}

	/**
	 * Calculate crop yield.
	 *
	 * @param crop
	 *            the crop to be harvested
	 * @param number
	 *            the number of plots this crop has been harvested from
	 * @return the total yield of the crop
	 */
	public static int calculateCropYield(Crop crop, int number) {
		int totalYield = 0;
		for (int i = 0; i < number; i++) {
			totalYield += calculateYield(crop.getMinYield(), crop.getMaxYield());
		}
		return totalYield;
	}

	/**
	 * Calculate yield for one instance of a crop. The formula to calculate the
	 * total number of units produced is :min yield + random number in the range
	 * of 0 to (max yield - min yield).
	 *
	 * @param minYield
	 *            the min yield of the crop
	 * @param maxYield
	 *            the max yield of the crop
	 * @return the total amount of units produced by the crop
	 */
	public static int calculateYield(int minYield, int maxYield) {
		Random rand = new Random();
		int randInt = rand.nextInt(maxYield - minYield + 1);
		return minYield + randInt;
	}
}

package is201.g5t08.farmcity.entity;

import is201.g5t08.farmcity.control.PlotCalculator;

import java.time.LocalDateTime;

/**
 * The Plot class represents a plot of land that the user can plant crops on.
 * Crops that have been planted will take some time to fully mature. Upon
 * reaching maturity, users can harvest their crops, which will earn them
 * experience points and gold. The number of plots a user has depends on their
 * rank.
 */
public class Plot {

	/** The type of crop that is planted. */
	private final Crop crop;

	/** The time at which the crop was planted. */
	private final LocalDateTime plantedAt;

	/**
	 * Instantiates a new plot with a type of crop and the time at which it was
	 * planted.
	 *
	 * @param crop
	 *            the type of the crop that is planted
	 * @param plantedAt
	 *            the time at which the crop was planted
	 */
	public Plot(Crop crop, LocalDateTime plantedAt) {
		this.crop = crop;
		this.plantedAt = plantedAt;
	}

	/**
	 * Gets the name of the crop that is planted.
	 *
	 * @return the name of the crop that is planted
	 */
	public Crop getCrop() {
		return crop;
	}

	/**
	 * Gets the time at which the crop was planted.
	 *
	 * @return the time at which the crop was planted
	 */
	public LocalDateTime getPlantedAt() {
		return plantedAt;
	}

	/**
	 * Gets the time at which the crop can be harvested.
	 *
	 * @return the time at which the crop can be harvested
	 */
	public LocalDateTime getHarvestableAt() {
		long time = crop.getTime();
		return plantedAt.plusMinutes(time);
	}

	/**
	 * Gets the time at which the crop wilts.
	 *
	 * @return the time at which the crop wilts
	 */
	public LocalDateTime getWiltsAt() {
		long time = (long) crop.getTime() * 2;
		return plantedAt.plusMinutes(time);
	}

	/**
	 * Checks if the crop has wilted.
	 *
	 * @return true, if the crop has wilted
	 */
	public boolean isWilted() {
		return PlotCalculator.calculateWilted(this);
	}

	/**
	 * Checks if the crop can be harvested.
	 *
	 * @return true, if the crop is can be harvested
	 */
	public boolean isHarvestable() {
		return PlotCalculator.calculateHarvestable(this);
	}

	/**
	 * Gets a string visual representation of the progress of the crop. If the
	 * crop is still growing, the number of # displayed indicates the growth
	 * progress. This number is rounded down to the nearest ten percent. For
	 * example, 37% or 32% will display the same number of # signs (3 of them).
	 * The growth is calculated as percentage of time elapsed since the seeds
	 * have been planted to the T minutes for the crop to mature. If the plot
	 * had been left alone for too long and the crop wilted, it will display
	 * that it has wilted.
	 *
	 * @return the string visual representation of the progress of the crop
	 */
	public String getProgress() {
		return PlotCalculator.calculateProgress(this);
	}
}

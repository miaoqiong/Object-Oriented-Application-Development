package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.entity.Plot;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The Class PlotCalculator.
 */
public final class PlotCalculator {

	/**
	 * This constructor is marked as private with an empty body as it is not
	 * meant to be instantiated.
	 */
	private PlotCalculator() {
	}

	/**
	 * Composes a string that reports the progress of a plot of land. It is
	 * represented by a number of # that indicates growth progress. The number
	 * of # is rounded down to the nearest ten percent. If the plot has been
	 * left unattended and has wilted, it will display that it has wilted.
	 *
	 * @param plot
	 *            the plot
	 * @return the string
	 */
	public static String calculateProgress(final Plot plot) {
		int progressPercentage = calculateProgressPercentage(plot);
		if (progressPercentage == -1) {
			return "[  wilted  ]";
		} else if (progressPercentage >= 0 && progressPercentage <= 100) {
			StringBuilder sb = new StringBuilder();
			int hashes = progressPercentage / 10;
			sb.append("[");
			for (int i = 0; i < hashes; i++) {
				sb.append("#");
			}
			for (hashes -= 10; hashes < 0; hashes++) {
				sb.append("-");
			}
			sb.append("] ");
			sb.append(String.valueOf(progressPercentage));
			sb.append("%");
			return sb.toString();
		}
		return null;
	}

	/**
	 * Calculate the progress percentage of a plot. The growth is calculated as
	 * percentage of the time elapsed since the seeds have been planted to the T
	 * minutes for the crop to mature.
	 *
	 * @param plot
	 *            the plot
	 * @return the progress of a plant on its way to maturity, in percentage
	 */
	public static int calculateProgressPercentage(final Plot plot) {
		if (plot.getPlantedAt() != null) {
			if (plot.isWilted()) {
				return -1;
			} else {
				Duration elapsed = Duration.between(plot.getPlantedAt(),
						LocalDateTime.now());
				long elapsedSeconds = elapsed.getSeconds();
				Duration total = Duration.between(plot.getPlantedAt(),
						plot.getHarvestableAt());
				long totalSeconds = total.getSeconds();
				double progress = (double) elapsedSeconds / totalSeconds * 100;
				return Integer.min((int) progress, 100);
			}
		}
		return -2;
	}

	/**
	 * Calculate if a plot of land has wilted.
	 *
	 * @param plot
	 *            the plot
	 * @return true, if the plot has wilted
	 */
	public static boolean calculateWilted(final Plot plot) {
		if (plot.getPlantedAt() != null) {
			LocalDateTime wiltsAt = plot.getWiltsAt();
			LocalDateTime now = LocalDateTime.now();
			if (now.equals(wiltsAt) || now.isAfter(wiltsAt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calculate if a plot of land can be harvested.
	 *
	 * @param plot
	 *            the plot
	 * @return true, if the plot can be harvested
	 */
	public static boolean calculateHarvestable(final Plot plot) {
		if (plot.getPlantedAt() != null) {
			LocalDateTime harvestableAt = plot.getHarvestableAt();
			LocalDateTime wiltsAt = plot.getWiltsAt();
			LocalDateTime now = LocalDateTime.now();
			if ((now.equals(harvestableAt) || now.isAfter(harvestableAt))
					&& now.isBefore(wiltsAt)) {
				return true;
			}
		}
		return false;
	}
}

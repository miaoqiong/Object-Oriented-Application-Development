package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.entity.Plot;
import is201.g5t08.farmcity.entity.User;

import java.util.List;

/**
 * The Class PlotValidator validates whether the user can plant at a particular
 * plot of land.
 */
public final class PlotValidator {

	/**
	 * This constructor is marked as private with an empty body as it is not
	 * meant to be instantiated.
	 */
	private PlotValidator() {
	}

	/**
	 * Validate plot position. Checks if the user can plant at that plot (if
	 * they have obtained the proper rank).
	 *
	 * @param user
	 *            the user
	 * @param position
	 *            the position
	 * @return true, if the user's rank is high enough
	 */
	public static boolean validatePlotPosition(User user, int position) {
		if (position > user.getNumberOfPlots()) {
			return false;
		} else if (position < 1) {
			return false;
		}
		return true;
	}

	/**
	 * Validate plot is not yet occupied by another crop.
	 *
	 * @param user
	 *            the user
	 * @param position
	 *            the position
	 * @return true, if plot exists and is currently unoccupied
	 */
	public static boolean validatePlotNotOccupied(User user, int position) {
		List<Plot> plotList = user.getPlotList();
		Plot plot = plotList.get(position - 1);
		if (plot != null) {
			return false;
		}
		return true;
	}

	/**
	 * Validate if a plot has wilted.
	 *
	 * @param user
	 *            the user
	 * @param position
	 *            the position
	 * @return true, if the plot has already wilted
	 */
	public static boolean validatePlotWilted(User user, int position) {
		List<Plot> plotList = user.getPlotList();
		Plot plot = plotList.get(position - 1);
		if (plot != null && !plot.isWilted()) {
			return false;
		}
		return true;
	}
}

package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.boundary.SelectCropMenu;
import is201.g5t08.farmcity.dao.InventoryDataManager;
import is201.g5t08.farmcity.dao.PlotDataManager;
import is201.g5t08.farmcity.dao.UserDataManager;
import is201.g5t08.farmcity.entity.Crop;
import is201.g5t08.farmcity.entity.Plot;
import is201.g5t08.farmcity.entity.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * The Class FarmController controls the flow of farm-related operations.
 */
public class FarmController {

	/**
	 * Harvesting causes all the user's plots of land to be evaluated to see
	 * which plots of land are able to be harvested. Once the harvestable plots
	 * are determined, a yield is calculated for each crop that is harvested.
	 * The yield for each crop determines how much experience points and gold is
	 * earned by the user. The yield of each crop is multiplied by the crop's
	 * gold and experience points, which are then added to the user's
	 * accumulated gold and xp totals, respectively.
	 *
	 * @param user
	 *            the user
	 * @return a list of results to be displayed to the user
	 */
	public String harvest(User user) {
		int xpGained = 0;
		int goldGained = 0;
		Multiset<Crop> harvestableCrops = HashMultiset.create();

		List<Plot> plotList = user.getPlotList();

		UserDataManager udm = new UserDataManager();
		PlotDataManager pdm = new PlotDataManager(udm);

		for (int i = 0; i < plotList.size(); i++) {
			Plot plot = plotList.get(i);
			if (plot != null && plot.isHarvestable()) {
				harvestableCrops.add(plot.getCrop());
				plotList.set(i, null);
			}
		}

		pdm.update(user, plotList);

		StringBuilder sb = new StringBuilder();

		for (Crop crop : harvestableCrops.elementSet()) {
			int cropCount = harvestableCrops.count(crop);
			int units = CropCalculator.calculateCropYield(crop, cropCount);
			int xp = units * crop.getXp();
			int gold = units * crop.getSalePrice();
			xpGained += xp;
			goldGained += gold;
			sb.append(String
					.format("%nYou have harvested %d units of %s for %d XP and %d gold.",
							units, crop.getName(), xp, gold));
		}

		if (sb.length() == 0) {
			sb.append(String.format("%nNo crops available for harvesting!"));
		}

		user.addXp(xpGained);
		user.addGold(goldGained);
		udm.update(user);
		return sb.toString();
	}

	/**
	 * Planting a crop occupies a plot of land. Planting a crop requires
	 * validating that the plot to be planted on is valid (the user owns such a
	 * plot) and that the plot is empty (not currently occupied by any other
	 * crop). Once the plot has passed all the validation checks, the user is
	 * prompted to choose which crop they want to plant on the plot. Then, the
	 * user's inventory of that crop's seed is deducted, and the user's plot has
	 * been updated with the newly planted crop.
	 *
	 * @param user
	 *            the user
	 * @param _position
	 *            the position of the crop as shown in the menu
	 * @return the result of the planting operation to the user
	 */
	public String plant(User user, String _position) {
		if (!StringUtils.isNumeric(_position)) {
			return String.format("%nPlease enter a valid number.");
		}
		int position = Integer.parseInt(_position);
		if (!PlotValidator.validatePlotPosition(user, position)) {
			return String.format("%nThat is not a valid plot number.");
		}
		if (!PlotValidator.validatePlotNotOccupied(user, position)) {
			return String.format("%nThat plot is currently occupied.");
		}

		SelectCropMenu selectCropMenu = new SelectCropMenu(user);

		Crop crop = selectCropMenu.readInput();
		if (crop == null) {
			return "";
		}

		InventoryDataManager idm = new InventoryDataManager();
		idm.deductInventory(user, crop.getName(), 1);

		Plot plot = new Plot(crop, LocalDateTime.now());

		UserDataManager udm = new UserDataManager();
		PlotDataManager pdm = new PlotDataManager(udm);
		List<Plot> plotList = user.getPlotList();
		plotList.set(position - 1, plot);
		pdm.update(user, plotList);
		udm.update(user);

		return String
				.format("%s planted on plot %d.", crop.getName(), position);
	}

	/**
	 * Clearing a land needs to pass several validations: that the user owns
	 * that plot, that the plot is not already empty, and if the plot has
	 * already wilted. If the plot does not meet these criteria, the user will
	 * not be allowed to clear the plot. 5 gold will be deducted from the user
	 * upon successful clearing, unless the user does not have enough gold, then
	 * it will be cleared for free. The user's plot and gold totals are then
	 * updated.
	 *
	 * @param user
	 *            the user
	 * @param _position
	 *            the position of the crop as shown in the menu
	 * @return the result of the clearing operation to the user
	 */
	public String clear(User user, String _position) {
		String result = String
				.format("%nYou spend 5 gold to pull the wilted crop out of the ground");
		if (!StringUtils.isNumeric(_position)) {
			return String.format("%nPlease enter a valid number.");
		}
		int position = Integer.parseInt(_position);
		if (!PlotValidator.validatePlotPosition(user, position)) { // test if
																	// plot
																	// number is
																	// valid
			return String.format("%nThat is not a valid plot number.");
		}
		if (PlotValidator.validatePlotNotOccupied(user, position)) { // test if
																		// plot
																		// is
																		// empty
			return String.format("%nThat plot is already empty.");
		}
		if (!PlotValidator.validatePlotWilted(user, position)) { // test if plot
																	// is empty
			return String.format("%nThat plot has not yet wilted.");
		}

		if (user.getGold() < 5) {
			result = String
					.format("%nA magical power has cleared the land for you for free.");
		} else {
			user.addGold(-5);
		}

		UserDataManager udm = new UserDataManager();
		PlotDataManager pdm = new PlotDataManager(udm);
		List<Plot> plotList = user.getPlotList();
		plotList.set(position - 1, null);
		pdm.update(user, plotList);
		udm.update(user);

		return result;
	}

}

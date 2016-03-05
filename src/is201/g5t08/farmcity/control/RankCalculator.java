package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.dao.RankDataManager;
import is201.g5t08.farmcity.entity.Rank;
import is201.g5t08.farmcity.entity.User;

import java.util.NavigableMap;

/**
 * The Class RankCalculator is a utility class to help calculate the the rank of
 * the user based on their accumulated experience points.
 */
public final class RankCalculator {

	/**
	 * This constructor is marked as private with an empty body as it is not
	 * meant to be instantiated.
	 */
	private RankCalculator() {
	}

	/**
	 * Calculates the rank of the user based on their experience points. Uses a
	 * TreeMap's floorEntry method in order to get the highest rank obtained by
	 * a user.
	 *
	 * @param user
	 *            the user
	 * @return the user's rank
	 */
	public static Rank calculateRank(User user) {
		NavigableMap<Integer, Rank> rankMap = new RankDataManager().getRanks();
		return rankMap.floorEntry(user.getXp()).getValue();
	}
}

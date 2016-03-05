package is201.g5t08.farmcity.dao;

import is201.g5t08.farmcity.entity.Rank;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * The Class RankDataManager keeps a TreeMap of the types of obtainable ranks.
 */
public class RankDataManager {

	/**
	 * The Constant fileName is the relative path of where the rank data will be
	 * stored.
	 */
	private static final String fileName = "data/rank.csv";

	/** The rank map. */
	private final NavigableMap<Integer, Rank> rankMap = new TreeMap<Integer, Rank>();

	/**
	 * Instantiates a new rank data manager, loading the rank data from the csv
	 * into the rankMap.
	 */
	public RankDataManager() {
		load();
	}

	/**
	 * Writes the data of all the types of ranks into a csv-formatted file.
	 */
	private void write() {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "RankName", "XP", "Plots" };
			writer.writeNext(headers);
			for (Rank rank : rankMap.values()) {
				String[] entries = { rank.getName(),
						String.valueOf(rank.getXp()),
						String.valueOf(rank.getNumberOfPlots()) };
				writer.writeNext(entries);
			}
			writer.close();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
	}

	/**
	 * Loads from a csv-formatted file into the rankMap.
	 */
	private void load() {
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName));
			reader.readNext(); // skip first line
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				try {
					String name = nextLine[0].trim();
					int xp = Integer.parseInt(nextLine[1].trim());
					int plots = Integer.parseInt(nextLine[2].trim());
					Rank rank = new Rank(name, xp, plots);
					rankMap.put(xp, rank);
				} catch (NumberFormatException e) {
					System.out.println("Unexpected number format");
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
		write();
	}

	/**
	 * Gets the ranks.
	 *
	 * @return the rankMap
	 */
	public NavigableMap<Integer, Rank> getRanks() {
		return rankMap;
	}
}

package is201.g5t08.farmcity.dao;

import is201.g5t08.farmcity.entity.Crop;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * The Class CropDataManager keeps a key-value store of the different types of
 * crops that users can plant.
 */
public class CropDataManager {

	/**
	 * The Constant fileName is the relative path of where the crop data will be
	 * stored.
	 */
	private static final String fileName = "data/crop.csv";

	/** The hash map stores crops with their name as the key. */
	private final Map<String, Crop> hashMap = new HashMap<String, Crop>();

	/**
	 * Instantiates a new crop data manager, loading the crop data from the csv
	 * file.
	 */
	public CropDataManager() {
		load();
	}

	/**
	 * Dumps the entire contents of the hashMap into the csv-formatted file.
	 */
	private void write() {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "Name", "Cost", "Time", "XP", "MinYield",
					"MaxYield", "SalePrice" };
			writer.writeNext(headers);
			for (Crop crop : hashMap.values()) {
				String[] entries = { crop.getName(),
						String.valueOf(crop.getCost()),
						String.valueOf(crop.getTime()),
						String.valueOf(crop.getXp()),
						String.valueOf(crop.getMinYield()),
						String.valueOf(crop.getMaxYield()),
						String.valueOf(crop.getSalePrice()) };
				writer.writeNext(entries);
			}
			writer.close();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
	}

	/**
	 * Reads the contents of the csv file and loads the contents into the
	 * HashMap.
	 */
	private void load() {
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName));
			reader.readNext(); // skip first line
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				try {
					String name = nextLine[0].trim();
					int cost = Integer.parseInt(nextLine[1].trim());
					int time = Integer.parseInt(nextLine[2].trim());
					int xp = Integer.parseInt(nextLine[3].trim());
					int minYield = Integer.parseInt(nextLine[4].trim());
					int maxYield = Integer.parseInt(nextLine[5].trim());
					int salePrice = Integer.parseInt(nextLine[6].trim());
					Crop crop = new Crop(name, cost, time, xp, minYield,
							maxYield, salePrice);
					hashMap.put(name, crop);
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
	 * List crops.
	 *
	 * @return the map
	 */
	public Map<String, Crop> listCrops() {
		return hashMap;
	}

	/**
	 * Find by name.
	 *
	 * @param name
	 *            the name
	 * @return the crop
	 */
	public Crop findByName(String name) {
		return hashMap.get(name);
	}
}

package is201.g5t08.farmcity.dao;

import is201.g5t08.farmcity.entity.Crop;
import is201.g5t08.farmcity.entity.Plot;
import is201.g5t08.farmcity.entity.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * The Class PlotDataManager keeps a key-value store of the user's plots. The
 * key is the User's username and the value is a List of Plots.
 */
public class PlotDataManager {

	/**
	 * The Constant fileName is the relative path of where the plot data will be
	 * stored.
	 */
	private static final String fileName = "data/plot.csv";

	/** The plot map stores a list of plots with the user's username as the key. */
	private final Map<String, List<Plot>> plotMap = new HashMap<String, List<Plot>>();

	/** The udm. */
	UserDataManager udm;

	/**
	 * Instantiates a new plot data manager, loading the plot data from the csv
	 * data file. The user data from the UserDataManager is required to
	 * calculate how many plots a user should have.
	 *
	 * @param udm
	 *            the udm
	 */
	public PlotDataManager(UserDataManager udm) {
		this.udm = udm;
		load();
	}

	/**
	 * Updates a user's list of plots, and saves it to the csv file.
	 *
	 * @param user
	 *            the user
	 * @param plotList
	 *            the plot list
	 */
	public void update(User user, List<Plot> plotList) {
		plotMap.put(user.getUsername(), plotList);
		write();
	}

	/**
	 * Writes the headers of the csv file, then loops through the plotMap to get
	 * the list of plots for each user, then loops through the list to store
	 * individual plots into the csv file.
	 */
	private void write() {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "Username", "Index", "Crop", "PlantedAt" };
			writer.writeNext(headers);
			for (Map.Entry<String, List<Plot>> e : plotMap.entrySet()) {
				String username = e.getKey();
				List<Plot> plotList = e.getValue();
				for (int i = 0; i < plotList.size(); i++) {
					Plot plot = plotList.get(i);
					if (plot != null) {
						String[] entries = { username, String.valueOf(i),
								plot.getCrop().getName(),
								plot.getPlantedAt().toString() };
						writer.writeNext(entries);
					}
				}
			}
			writer.close();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
	}

	/**
	 * Loads the plot data from the csv file and assigns plots, based on the
	 * username, to the plotMap.
	 */
	private void load() {
		try {
			CropDataManager cdm = new CropDataManager();
			CSVReader reader = new CSVReader(new FileReader(fileName));
			reader.readNext(); // skip first line
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				try {
					String username = nextLine[0].trim();
					int index = Integer.parseInt(nextLine[1].trim());
					Crop crop = cdm.findByName(nextLine[2].trim());
					LocalDateTime plantedAt = LocalDateTime.parse(nextLine[3]
							.trim());
					Plot plot = new Plot(crop, plantedAt);
					List<Plot> plotList = findByUsername(username);
					plotList.add(index, plot);
					plotMap.put(username, plotList);
				} catch (NumberFormatException e) {
					System.out.println("Unexpected number format");
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			write();
			load();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
		write();
	}

	/**
	 * Find a list of plots by username.
	 *
	 * @param username
	 *            the username
	 * @return the list of plots owned by a user
	 */
	public List<Plot> findByUsername(String username) {
		List<Plot> plotList = plotMap.get(username);
		if (plotList == null) {
			User user = udm.findByUsername(username);
			plotList = new ArrayList<Plot>(Collections.nCopies(
					user.getNumberOfPlots(), null));
		}
		return plotList;
	}
}

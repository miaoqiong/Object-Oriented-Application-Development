package is201.g5t08.farmcity.dao;

import is201.g5t08.farmcity.entity.Gift;
import is201.g5t08.farmcity.entity.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * A GiftDataManager gets the information from the gift.csv
 */
public class GiftDataManager {
	private static final String fileName = "data/gift.csv";
	private final Map<Integer, Gift> giftMap = new HashMap<Integer, Gift>();

	/**
	 * Creates a GiftDataManager object by calling method loadCSV()
	 */
	public GiftDataManager() {
		loadCSV();
	}

	/**
	 * This method returns giftMap
	 *
	 * @return Map of gifts
	 */
	public Map<Integer, Gift> listGifts() {
		return giftMap;
	}

	/**
	 * This method read the CSV file and add them to the giftMap It returns
	 * error messages if there's exception
	 *
	 */
	private void loadCSV() {
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName));
			UserDataManager udm = new UserDataManager();
			reader.readNext(); // skip first line
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				try {
					int id = Integer.parseInt(nextLine[0].trim());
					User sender = udm.findByUsername(nextLine[1].trim());
					User receiver = udm.findByUsername(nextLine[2].trim());
					LocalDate time = LocalDate.parse(nextLine[3].trim());

					Gift tempGift = new Gift(sender, receiver, time);
					giftMap.put(id, tempGift);

				} catch (NumberFormatException e) {
					System.out.println("Unexpected number format");
				}

			}
			reader.close();
		} catch (FileNotFoundException e) {
			writeToCSV();
			loadCSV();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
		writeToCSV();
	}

	/**
	 * This method adds Gift record to the CSV file It loops through
	 * inventoryMap, fetchs all gift records in giftMap and then write to the
	 * CSV file accordingly It returns error messages if there's exception
	 */
	public void writeToCSV() {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "Id", "Sender", "Receiver", "Time" };
			writer.writeNext(headers);
			for (Integer key : giftMap.keySet()) {
				String senderName = giftMap.get(key).getSender().getUsername();
				String receiverName = giftMap.get(key).getReceiver()
						.getUsername();
				// System.out.println(". "+username);
				LocalDate time = giftMap.get(key).getTime();
				// System.out.println(". " +time);
				String[] entries = { key.toString(), senderName, receiverName,
						time.toString() };
				writer.writeNext(entries);
			}

			writer.close();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
	}

	/**
	 * This method returns an updated giftMap by adding new gift record to the
	 * map
	 *
	 * @param g
	 *            the gift record
	 * @return Map of gift
	 */
	public Map<Integer, Gift> updateGiftRecord(Gift g) {
		int key = giftMap.size() + 1;
		giftMap.put(key, g);
		return giftMap;

	}

	/**
	 * This method returns a specific user's giftMap It loops through the whole
	 * gitMap and gets all gift records of the specific user Then add the
	 * records to new map of giftRecords.
	 *
	 * @param u the logged user
	 * @return Map of gift records
	 */
	public Map<Integer, Gift> getGiftRecords(User u) {
		Map<Integer, Gift> giftRecords = new HashMap<Integer, Gift>();
		for (Integer k : giftMap.keySet()) {
			if (giftMap.get(k).getSender().getUsername()
					.equals(u.getUsername())
					&& giftMap.get(k).getTime().isEqual(LocalDate.now())) {
				Integer key = giftRecords.size() + 1;
				Gift g = new Gift(giftMap.get(k).getSender(), giftMap.get(k)
						.getReceiver(), giftMap.get(k).getTime());
				giftRecords.put(key, g);
			}
		}
		return giftRecords;
	}

}

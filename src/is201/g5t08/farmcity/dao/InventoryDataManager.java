package is201.g5t08.farmcity.dao;

import is201.g5t08.farmcity.entity.Inventory;
import is201.g5t08.farmcity.entity.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * A InventoryDataManager gets the information from the inventory.csv
 */
public class InventoryDataManager {
	private static final String fileName = "data/inventory.csv";
	private final Map<String, List<Inventory>> inventoryMap = new HashMap<String, List<Inventory>>();

	/**
	 * Creates an InventoryDataManager object by calling loadCSV()
	 */
	public InventoryDataManager() {
		loadCSV();
	}

	/**
	 * This method read the CSV file and add them to the inventoryMap It returns
	 * error messages if there's exception
	 */
	private void loadCSV() {
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName));
			reader.readNext(); // skip first line
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				try {
					String username = nextLine[0].trim();
					String seed = nextLine[1].trim();
					int quantity = Integer.parseInt(nextLine[2].trim());
					Inventory inventory = new Inventory(seed, quantity);
					List<Inventory> inventoryList = inventoryMap.get(username);
					if (inventoryList == null) {
						inventoryList = new ArrayList<Inventory>();
					}
					inventoryList.add(inventory);
					inventoryMap.put(username, inventoryList);
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
	 * This method adds Inventory to the CSV file It loops through inventoryMap,
	 * fetchs all inventories in inventoryMap and then write to the CSV file
	 * accordingly It returns error messages if there's exception
	 */
	public void writeToCSV() {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			String[] headers = { "Username", "Seed", "Quantity" };
			writer.writeNext(headers);
			for (Map.Entry<String, List<Inventory>> e : inventoryMap.entrySet()) {
				String username = e.getKey();
				for (Inventory inventory : e.getValue()) {
					String seedName = inventory.getSeed();
					String quantity = String.valueOf(inventory.getQuantity());
					String[] entries = { username, seedName, quantity };
					writer.writeNext(entries);
				}
			}
			writer.close();
		} catch (IOException e) {
			System.out.printf("%nUnexpected IO error");
		}
	}

	/**
	 * This method returns an updated inventoryMap It loops through the
	 * inventoryList of Inventory to check if there is exiting user and crop If
	 * yes, update the quantity of existing records in inventoryMap If no, add
	 * the new Inventory to inventoryList, and then add to inventoryMap
	 *
	 * @param user the username of Inventory
	 * @param seeds the name of Crop of Inventory
	 * @param quantity the quantity of Inventory
	 * @return Map of Inventory
	 */
	public Map<String, List<Inventory>> updateInventory(User user,
			String seeds, int quantity) {
		String username = user.getUsername();
		List<Inventory> inventoryList = user.getInventoryList();
		boolean exists = false;
		for (Inventory inv : inventoryList) {
			if (seeds.equals(inv.getSeed())) {
				exists = true;
				int q = inv.getQuantity();
				inv.setQuantity(quantity + q);
				break; // stop forloop!!!!
			}
		}
		if (exists == false) {
			Inventory invt = new Inventory(seeds, quantity);
			inventoryList.add(invt);
		}
		inventoryMap.put(username, inventoryList);
		writeToCSV();
		return inventoryMap;
	}

	/**
	 * This method returns an updated inventoryMap It loops through the
	 * inventoryList of Inventory to check if there is exiting user and crop If
	 * yes, deduct the quantity of existing records in inventoryMap Then, add
	 * updated records to inventoryMap if the Inventory is not null and quantity
	 * is greater than 0
	 *
	 * @param user the username of Inventory
	 * @param seeds the name of Crop of Inventory
	 * @param quantity the quantity of Inventory
	 * @return Map of Inventory
	 */
	public Map<String, List<Inventory>> deductInventory(User user,
			String seeds, int quantity) {
		String username = user.getUsername();
		Inventory invt = null;
		List<Inventory> inventoryList = user.getInventoryList();
		for (Inventory inventory : inventoryList) {
			String ss = inventory.getSeed();

			if (seeds.equals(ss)) {
				int q = inventory.getQuantity();
				inventoryList.remove(inventory);
				invt = new Inventory(seeds, q - quantity);
				break; // stop forloop!!!!
			}
		}
		if (invt != null && invt.getQuantity() > 0) {
			inventoryList.add(invt);
		}
		inventoryMap.put(username, inventoryList);
		writeToCSV();
		return inventoryMap;
	}

	/**
	 * This method returns an inventoryList It gets inventoryList based on the
	 * username from inventoryMap
	 *
	 * @param username the username of user
	 * @return List of Inventory
	 */
	public List<Inventory> findInventoryListByUsername(String username) {
		List<Inventory> inventoryList = inventoryMap.get(username);
		if (inventoryList == null) {
			inventoryList = new ArrayList<Inventory>();
		}
		return inventoryList;
	}

}

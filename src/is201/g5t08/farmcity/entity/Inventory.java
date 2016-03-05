package is201.g5t08.farmcity.entity;

import is201.g5t08.farmcity.dao.CropDataManager;

/**
 * A Inventory represents a inventory object with a seed, quantity
 */
public class Inventory {
	private int quantity;
	private Crop crop;
	CropDataManager cdm = new CropDataManager();

	/**
	 * Creates a Inventory object with the specified quantity and seed
	 * 
	 * @param seed
	 *            the crop's name
	 * @param quantity
	 *            the inventory's quantity
	 */
	public Inventory(String seed, int quantity) {
		this.crop = cdm.findByName(seed);
		this.quantity = quantity;
	}

	/**
	 * Gets the quantity of this inventory.
	 * 
	 * @return the quantity of this inventory
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity of this inventory.
	 * 
	 * @param quantity
	 *            the inventory's quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the name of crop of this inventory.
	 * 
	 * @return the name of crop of this inventory
	 */
	public String getSeed() {
		return crop.getName();
	}
}

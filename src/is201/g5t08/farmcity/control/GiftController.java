package is201.g5t08.farmcity.control;

import is201.g5t08.farmcity.dao.FriendsDataManager;
import is201.g5t08.farmcity.dao.GiftDataManager;
import is201.g5t08.farmcity.dao.InventoryDataManager;
import is201.g5t08.farmcity.dao.UserDataManager;
import is201.g5t08.farmcity.entity.Friends;
import is201.g5t08.farmcity.entity.Gift;
import is201.g5t08.farmcity.entity.Inventory;
import is201.g5t08.farmcity.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * A GiftController does all the validations, controls the GiftDataManager,
 * execute sending gift function
 */
public class GiftController {
	private User user;

	/**
	 * Create a GiftController object with the specified user
	 *
	 * @param user
	 *            the logged user
	 */
	public GiftController(User user) {
		this.user = user;
	}

	// check if receiver is exist in the records
	/**
	 * This method returns boolean value It loops through the gift records of
	 * the logged user If the receiver of existing gift records exists in
	 * receiverList, it returns true It returns false if the receiverList does
	 * not contain same user
	 *
	 * @param receiverList
	 *            the gift receiver list
	 * @return false if gift receiver is not founded in receiverList
	 */
	public boolean isSameReceiver(List<User> receiverList) {

		GiftDataManager gdm = new GiftDataManager();
		Map<Integer, Gift> giftRecords = gdm.getGiftRecords(user);

		for (Gift g : giftRecords.values()) {
			if (receiverList.contains(g.getReceiver())) {
				// System.out.println("Can not send to same receiver!");
				return true; // receiver in receiverList has received gift alr
			}
		}
		return false;
	}

	/**
	 * This method returns boolean value It loops through the receiver list It
	 * returns true, if the user from receiverList is null or the user is logged
	 * user or the user is not friend of logged user
	 *
	 * @param receiverList
	 *            the list of gift receiver
	 * @return false if user is a valid user
	 */
	public boolean isValidUser(List<User> receiverList) {
		for (int i = 0; i < receiverList.size(); i++) {
			User receiver = receiverList.get(i);
			User tempUser = receiver;
			if (tempUser == null || tempUser.equals(user)
					|| !isFriend(receiver)) {
				// System.out.println("Please enter a valid user!");
				return true;
			}
		}
		return false;
	}

	// return true, if the receiver is the logged user's friend
	/**
	 * This method returns boolean value It loops through the friend list It
	 * returns true if the receiver is friend of logged user
	 *
	 * @param receiver
	 *            the user of gift receiver
	 * @return false if receiver is not friend of logged user
	 */
	public boolean isFriend(User receiver) {
		FriendsDataManager fdm = new FriendsDataManager(user);
		List<Friends> fList = fdm.getFriends();

		for (int f = 0; f < fList.size(); f++) {
			if (receiver.getUsername().equals(fList.get(f).getReceiver())
					|| receiver.getUsername().equals(fList.get(f).getSender())) {
				return true;

			}
		}
		return false;

	}

	/**
	 * This method returns boolean value It returns true, if the receiver list
	 * contains two same names
	 *
	 * @param receiverList
	 *            the list of gift receiver
	 * @return false if the receiver in the receiver list is unique
	 */
	public boolean checkDuplicate(List<User> receiverList) {
		for (int i = 0; i < receiverList.size(); i++) {
			User tempUser = receiverList.get(i);
			for (int j = i + 1; j < receiverList.size(); j++) {
				if (tempUser.equals(receiverList.get(j))) {
					// System.out.println("Can not send to 2 same friend in a day");
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * This method displays error message to user
	 *
	 * @param option
	 *            the selected choice of gift
	 * @param receiver
	 *            the gift receiver
	 * @return error message based on the type of error
	 */
	public String validate(String option, String receiver) {
		List<User> receiverList = getReceiverList(receiver);

		GiftDataManager gdm = new GiftDataManager();
		Map<Integer, Gift> giftRecords = gdm.getGiftRecords(user);
		int avaiGiftNum = 5 - giftRecords.size();
		if (avaiGiftNum == 0) {
			return String.format("%nYou have sent 5 Gifts!");
			// return true;
		}

		if (checkDuplicate(receiverList)) {
			return String.format("%nCan not send to 2 same friend in a day");
		}
		if (isSameReceiver(receiverList)) {
			return String.format("%nCan not send to same receiver!");
		}
		if (isValidUser(receiverList)) {
			return String.format("%nPlease enter a valid user!");
		}
		// sending gift
		return sendGift(option, receiver);

	}

	/**
	 * This method displays message to user to show the number of gifts left and
	 * the available quantity of gift
	 *
	 * @param option
	 *            the choice of gift
	 * @param receiver
	 *            the gift receiver
	 * @return to the number of gift left or the available quantity left
	 */
	public String sendGift(String option, String receiver) {
		String toReturn = null;
		GiftDataManager gdm = new GiftDataManager();
		String seeds = getSeeds(option);

		int avaiQuantity = getAvaiQuantity(option);
		int avaiGiftNum = 5 - gdm.getGiftRecords(user).size();

		if (avaiGiftNum < avaiQuantity) {
			toReturn = send(avaiGiftNum, seeds, receiver);
			if (avaiGiftNum < getReceiverList(receiver).size()) {
				return String.format(toReturn + "%nYou only have "
						+ avaiGiftNum + " gift(s) left!");

			}
			return String.format(toReturn);

		} else {
			// avaiQuantity < avaiGiftNum
			toReturn = send(avaiQuantity, seeds, receiver);
			if (avaiQuantity < getReceiverList(receiver).size()) {
				return String.format(toReturn + "%nYou only have "
						+ avaiQuantity + "  left in inventory!");
			}
			return String.format(toReturn);
		}

	}

	/**
	 * This method returns sending message to user
	 *
	 * @param num
	 *            the number of gift available
	 * @param seeds
	 *            the choice of gift
	 * @param receiver
	 *            the gift receiver
	 * @return to whom the gift was sent to
	 */
	public String send(int num, String seeds, String receiver) {
		String toReturn = "Gift sent to ";
		List<User> receiverList = getReceiverList(receiver);
		InventoryDataManager idm = new InventoryDataManager();
		GiftDataManager gdm = new GiftDataManager();

		if (num < receiverList.size()) {
			for (int i = 0; i < num - 1; i++) {
				// update Inventory to each receiver, q increase by 1
				idm.updateInventory(receiverList.get(i), seeds, 1);
				// update sent gift record to gift CSV
				Gift tempGift = new Gift(user, receiverList.get(i),
						LocalDate.now());
				// System.out.print(receiverList.get(i) + ", ");
				toReturn += receiverList.get(i) + ", ";
				gdm.updateGiftRecord(tempGift);

			}
			idm.updateInventory(receiverList.get(num - 1), seeds, 1);
			Gift tempGift = new Gift(user, receiverList.get(num - 1),
					LocalDate.now());
			// System.out.print(receiverList.get(num - 1));
			toReturn += receiverList.get(num - 1);
			gdm.updateGiftRecord(tempGift);
			// update inventory of sender, deduct q by num
			idm.deductInventory(user, seeds, num);
			// update the giftNo in gift.csv
			gdm.writeToCSV();

		} else {
			// num > receiverList.size()

			toReturn += receiver.replaceAll("\\s+", "");
			// deduct sender's inventory by rList.size()
			idm.deductInventory(user, seeds, receiverList.size());

			// add inventory to receiver.
			for (int i = 0; i < receiverList.size(); i++) {
				idm.updateInventory(receiverList.get(i), seeds, 1);
				Gift tempGift = new Gift(user, receiverList.get(i),
						LocalDate.now());
				gdm.updateGiftRecord(tempGift);

			}
			// update the giftNo in gift.csv

			gdm.writeToCSV();
		}
		return toReturn;

	}

	/**
	 * This method returns selected gift
	 *
	 * @param option
	 *            the choice of gift selected by user
	 * @return the name of seeds
	 */
	public String getSeeds(String option) {
		String seeds = null;
		List<Inventory> inventoryList = user.getInventoryList();
		if (StringUtils.isNumeric(option)) {
			seeds = inventoryList.get(Integer.parseInt(option) - 1).getSeed();
			// System.out.print(seeds +quantity+);
		}
		return seeds;

	}

	/**
	 * This method returns int value of available gift quantity
	 *
	 * @param option
	 *            the choice of gift selected by user
	 * @return the quantity of selected gift
	 */
	public int getAvaiQuantity(String option) {
		int quantity = 0;
		List<Inventory> inventoryList = user.getInventoryList();
		if (StringUtils.isNumeric(option)) {
			quantity = inventoryList.get(Integer.parseInt(option) - 1)
					.getQuantity();

		}
		return quantity;

	}

	/**
	 * This method returns list of receiver It removes the space and comma of
	 * receivers string Then adds the receiver to list
	 *
	 * @param receivers
	 *            the name of gift receiver
	 * @return the list of gift receiver
	 */
	public List<User> getReceiverList(String receivers) {
		List<User> receiverList = new ArrayList<User>();
		UserDataManager udm = new UserDataManager();
		String newReceivers = receivers.replaceAll("\\s+", "");
		String part[] = newReceivers.split(",");
		for (int i = 0; i < part.length; i++) {
			receiverList.add(udm.findByUsername(part[i]));
		}
		return receiverList;

	}

}

package is201.g5t08.farmcity.entity;

import is201.g5t08.farmcity.dao.UserDataManager;

/**
 * A Friend represents a relationship with a sender, receiver and status
 */
public class Friends {
	private User sender;
	private User receiver;
	private String status;
	UserDataManager udm = new UserDataManager();

	// constructor
	/**
	 * Creates a Friend object with the specified sender, receiver and status
	 *
	 * @param sender
	 *            the sender
	 * @param receiver
	 *            the receiver
	 * @param status
	 *            the friend's status
	 */
	public Friends(User sender, User receiver, String status) {
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}

	// getters and setters
	/**
	 * Gets the username of this sender
	 *
	 * @return the username of this sender
	 */
	public String getSender() {
		return sender.getUsername();
	}

	/**
	 * Gets the username of this receiver
	 *
	 * @return the username of tis receiver
	 */
	public String getReceiver() {
		return receiver.getUsername();
	}

	/**
	 * Gets the status of this friend
	 *
	 * @return the status of this friend
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the user of this sender
	 *
	 * @param s the friend's sender
	 */
	public void setSender(User s) {
		sender = s;
	}

	/**
	 * Sets the user of this receiver.
	 *
	 * @param r the new receiver
	 */
	public void setReceiver(User r) {
		receiver = r;
	}

	/**
	 * Sets the status of this friend
	 *
	 * @param status the friend's status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}

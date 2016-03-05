package is201.g5t08.farmcity.entity;

import java.time.LocalDate;

/**
 * A Gift represents a gift object with a sender, receiver and time
 */
public class Gift {
	private User sender;
	private User receiver;
	private LocalDate time;

	/**
	 * Creates a Gift object with the specified sender, receiver and local date
	 * 
	 * @param s
	 *            the sender
	 * @param r
	 *            the receiver
	 * @param t
	 *            the local date
	 */
	public Gift(User s, User r, LocalDate t) {
		sender = s;
		receiver = r;
		time = t;
	}

	/**
	 * Gets the sender of this gift.
	 * 
	 * @return the sender of this gift
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * Gets the receiver of this gift.
	 * 
	 * @return the receiver of this gift
	 */
	public User getReceiver() {
		return receiver;
	}

	/**
	 * Gets the local date of this gift.
	 * 
	 * @return the local date of this gift
	 */
	public LocalDate getTime() {
		return time;
	}

}

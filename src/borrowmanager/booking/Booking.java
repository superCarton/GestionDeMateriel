package borrowmanager.booking;

public class Booking {
	private Integer borrowerId;
	private String reason;
	private Boolean isValidated;
	public DateInterval interval;

	public boolean overlaps(DateInterval interval) {
		return this.interval.overlaps(interval);
	}

	public boolean isValidated() {
		return isValidated;
	}
}
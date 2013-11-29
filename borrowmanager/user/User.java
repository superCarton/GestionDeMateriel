package borrowmanager.user;

public class User {
	private Integer id;
	private String name;
	public UserType userType;

	public Boolean canValidateBookings() {
		throw new UnsupportedOperationException();
	}

	public Integer getMaxBookingLength() {
		throw new UnsupportedOperationException();
	}

	public Integer getMaxReservationLength() {
		throw new UnsupportedOperationException();
	}
}
package borrowmanager.user;

public class User {
	private Integer id;
	private String name;
	public UserType userType;

	public Boolean canValidateBookings() {
		return userType == UserType.STOCK_MANAGER; 
	}

	public Integer getMaxBookingLength() {
		return UserType.getBookingLength(userType); 
	}

	public Integer getMaxReservationLength() {
		return UserType.getMaxReservationLength(userType);
	}
}
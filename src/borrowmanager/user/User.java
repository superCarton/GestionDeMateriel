package borrowmanager.user;

public class User {
	private Integer id;
	private String name;
	public UserType userType;

	public User(int id, String name, UserType u) {
		this.id = id ;
		this.name = name;
		this.userType = u;
	}
	
	public Boolean canValidateBookings() {
		return userType == UserType.STOCK_MANAGER; 
	}

	public Integer getMaxBookingLength() {
		return UserType.getBookingLength(userType); 
	}

	public Integer getMaxReservationLength() {
		return UserType.getMaxReservationLength(userType);
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}
}
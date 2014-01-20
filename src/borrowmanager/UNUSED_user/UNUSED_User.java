package borrowmanager.UNUSED_user;

/**
 * User class, represents an user in the software
 * @author Tom Guillermin
 *
 */
public class UNUSED_User implements Comparable<UNUSED_User> {
	private Integer id;
	private String name;
	public UNUSED_UserType userType;

	public UNUSED_User(int id, String name, UNUSED_UserType u) {
		this.id = id ;
		this.name = name;
		this.userType = u;
	}
	
	public Boolean canValidateBookings() {
		return userType == UNUSED_UserType.STOCK_MANAGER; 
	}

	public Integer getMaxBookingLength() {
		return UNUSED_UserType.getBookingLength(userType); 
	}

	public Integer getMaxReservationLength() {
		return UNUSED_UserType.getMaxReservationLength(userType);
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
	public UNUSED_UserType getUserType() {
		return userType;
	}

	@Override
	public int compareTo(UNUSED_User arg0) {
		return arg0.id - this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		UNUSED_User u = (UNUSED_User)obj;
		
		return u.id == this.id && u.name == this.name;
	}
}
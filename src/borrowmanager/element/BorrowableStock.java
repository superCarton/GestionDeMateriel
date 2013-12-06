package borrowmanager.element;

import java.util.Date;
import java.util.Map;

import borrowmanager.booking.Booking;
import borrowmanager.booking.BookingCalendar;

public class BorrowableStock {
	private BorrowableModel model;
	private BookingCalendar calendar;
	private Integer initialStock;
	
	public BorrowableStock(BorrowableModel model) {
		this(model, 1);
	}
	
	public BorrowableStock(BorrowableModel model, Integer initialStock) {
		this.model = model;
		this.initialStock = initialStock;
		this.calendar = new BookingCalendar(model);
	}

	public HardwareType getType() {
		return model.getType();
	}

	public Map<String, String> getData() {
		return model.getData();
	}

	/**
	 * Returns true if the borrowable has a feature.
	 * @param feature The name of the feature.
	 * @return True if the borrowable has the feature.
	 */
	public Boolean hasFeature(String feature) {
		return model.hasFeature(feature);
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return model.getId();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return model.getName();
	}
	
	/**
	 * Returns the model of the borrowable
	 * @return
	 */
	public BorrowableModel getModel() {
		return model;
	}
	
	/**
	 * Returns the initial value of the stock. 
	 * @return
	 */
	public Integer getInitialStock() {
		return initialStock;
	}
	
	/**
	 * Returns the booking calendar of the stock.
	 * @return
	 */
	public BookingCalendar getCalendar() {
		return calendar;
	}
	
	/**
	 * Returns true if the borrowable is available in a given 
	 * quantity between a date range.
	 * @param quantity The quantity
	 * @param start The start date
	 * @param end The end date
	 * @return True if the borrowable is available in a given
	 */
	public boolean isAvailable(Integer quantity, Date start, Date end) {
		long startTime = start.getTime();
		long endTime = end.getTime();
		long dayLength = 24*60*60*1000;
		
		for(long i = startTime ; i <= endTime ; i+= dayLength) {
			Date d = new Date(i);
			if (!isAvailable(quantity, d)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true if a given quantity of the borrowable is available at a given date.
	 * @param quantity
	 * @param date
	 * @return
	 */
	public boolean isAvailable(Integer quantity, Date date) {
		int available = getAvailableNumber(date);
		return available >= quantity;
	}
	
	/**
	 * Returns the number available items now.
	 * @return
	 */
	public Integer getAvailableNumber() {
		return getAvailableNumber(new Date());
	}
	
	/**
	 * Returns the available number of the item at a given date.
	 * @param date The date to check
	 * @return The available number of the item at the given date.
	 */
	public Integer getAvailableNumber(Date date) {
		Integer availableNumber = initialStock;
		//System.out.println("(Initial stock : "+initialStock+")");
		for(Booking b : calendar.getBookings()) {
			if (b.getInterval().contains(date)) {
				//System.out.println(b.getInterval().toString()+" contains "+date.toLocaleString());
				//System.out.println("   ==> existing booking quantity = "+b.getQuantity());
				availableNumber -= b.getQuantity();
			}
		}
		//System.out.println("Available number @ "+date.toLocaleString()+" : "+availableNumber);
		return availableNumber;
	}
}
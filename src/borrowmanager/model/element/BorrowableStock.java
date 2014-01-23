package borrowmanager.model.element;

import java.util.AbstractSet;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.booking.BookingCalendar;
import borrowmanager.model.booking.DateInterval;
import borrowmanager.model.material.Material;
import borrowmanager.model.material.MaterialType;

/**
 * BorrowableStock, representing the stock of models
 * @author  Tom Guillermin
 *
 */
public class BorrowableStock {
	private MaterialType materialType;
	private BookingCalendar calendar;
	private List<Material> stock;
	
	public BorrowableStock(MaterialType type) {
		this(type, new LinkedList<Material>());
	}
	
	public BorrowableStock(MaterialType type, List<Material> initialStock) {
		this.materialType = type;
		this.stock = initialStock;
		this.calendar = new BookingCalendar(type);
	}

	/**
	 * Returns the model of the borrowable
	 * @return
	 */
	public MaterialType getType() {
		return materialType;
	}

	/*
	public Map<String, String> getData() {
		return materialType.getData();
	}*/

	/**
	 * Returns true if the borrowable has a feature.
	 * @param feature The name of the feature.
	 * @return True if the borrowable has the feature.
	 */
	/*
	public Boolean hasFeature(String feature) {
		return materialType.hasFeature(feature);
	}*/

	/**
	 * @return the id
	 */
	public Integer getId() {
		return materialType.getId();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return materialType.getName();
	}
	
	/**
	 * Returns the initial value of the stock. 
	 * @return
	 */
	public List<Material> getStock() {
		return stock;
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
		return getAvailableNumber(Manager.now);
	}
	
	/**
	 * Returns the available number of the item at a given date.
	 * @param date The date to check
	 * @return The available number of the item at the given date.
	 */
	public Integer getAvailableNumber(Date date) {
		return getAvailableMaterials(date).size();
	}
	
	/**
	 * Returns the list of the unavailable materials at a given date.
	 * @param date The date
	 * @return
	 */
	public Set<Material> getUnavailableMaterials(Date date) {
		Set<Material> unavailable = new HashSet<Material>();
		for (Booking b : calendar.getBookings()) {
			if (b.isActive(date)) {
			//if (b.getInterval().contains(date)) {
				unavailable.addAll(b.getMaterials());
			}
		}
		return unavailable;
	}
	
	public List<Material> getAvailableMaterials(Date date) {
		Set<Material> unavailable = getUnavailableMaterials(date);
		
		return getAvailableMaterials(unavailable);
	}
	
	public List<Material> getAvailableMaterials(Set<Material> unavailable) {
		List<Material> available = new LinkedList<Material>();
		for (Material m : stock) {
			if (! unavailable.contains(m)) {
				available.add(m);
			}
		}
		return available;
	}
	
	public List<Material> getAvailableMaterials(Date start, Date end) {
		long startTime = start.getTime();
		long endTime = end.getTime();
		long dayLength = 24*60*60*1000;
		
		Set<Material> unavailable = new HashSet<Material>();
		for(long i = startTime ; i <= endTime ; i+= dayLength) {
			Date d = new Date(i);
			// TODO : maybe remove doublons
			unavailable.addAll(getUnavailableMaterials(d));
		}
		
		return getAvailableMaterials(unavailable);
	}
	
	public Booking book(Integer borrowerId, Integer quantity, DateInterval bookingInterval, String reason) {
		List<Material> list = getAvailableMaterials(bookingInterval.getStart(), bookingInterval.getEnd());
		return getCalendar().book(borrowerId, list, bookingInterval, reason);
	}
}
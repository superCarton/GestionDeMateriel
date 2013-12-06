package borrowmanager.booking;

import java.util.Date;

import borrowmanager.util.StringConfig;

/**
 * This class represents a timespan between two dates, to be used in the BookingCalendar.
 * @author Franck Dechavanne
 *
 */
public class DateInterval implements Comparable<DateInterval> {
	/**
	 * Start of the Interval
	 */
	private Date start;
	/**
	 * End of the Interval
	 */
	private Date end;

	/**
	 * Constructs a DateInterval from two dates. Start must be before end.
	 * @param start	Start of the interval
	 * @param end	End of the interval
	 * @throws IllegalArgumentException	start is after end
	 */
	public DateInterval(Date start, Date end) throws IllegalArgumentException {
		// Check the validity of the interval
		//
		if (start.after(end) && !(Math.abs(start.getTime() - end.getTime()) < 1000)) {
			System.out.println(start);
			System.out.println(end);
			throw new IllegalArgumentException(
					StringConfig.ERROR_INTERVAL_INVALID);
		}

		// Store the Dates
		//
		this.start = start;
		this.end = end;
	}

	/**
	 * Controls if a DateInterval overlaps another one.
	 * @param interval	The DateInterval to check for
	 * @return	Does interval overlaps the current object?
	 */
	public Boolean overlaps(DateInterval interval) {
		return !(interval.start.after(this.end) || interval.end.before(this.start));
	}
	
	/**
	 * Returns the length of the interval in days
	 */
	public Integer getLength(){
		return (int) ((end.getTime() - start.getTime()) / 86400000);
	}

	/**
	 * Checks if the interval ends before a certain date
	 * @param date	The date to compare to
	 * @return	Does the interval end before the date?
	 */
	public Boolean isLate(Date date) {
		return end.before(date);
	}
	
	/**
	 * Checks whether a date is contained in the interval
	 * @param date	The date to check
	 * @return	Is the date in the interval?
	 */
	public Boolean contains(Date date){
		return this.start.before(date) && end.after(date);
	}

	/**
	 * End the current interval at the current moment
	 */
	public void end() {
		this.end = new Date();
	}
	
	@Override
	protected Object clone(){
		return new DateInterval(start, end);
	}

	@Override
	public boolean equals(Object obj) {
		DateInterval w = (DateInterval) obj;
		return this.start.equals(w.start) && this.end.equals(w.end);
	}
	
	@Override
	public String toString() {
		return "Interval from " + this.start.toString() + " to " + this.end.toString();
	}

	@Override
	public int compareTo(DateInterval interval) {
		return (int) ((start.getTime() - interval.start.getTime()) / 3600000);
	}

	public Date getStart() {
		return start;
	}
	
	public Date getEnd() {
		return end;
	}
}
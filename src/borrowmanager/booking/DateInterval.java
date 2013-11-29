package borrowmanager.booking;

import java.util.Date;

import borrowmanager.util.StringConfig;

/**
 * This class represents a timespan between two dates, to be used in the BookingCalendar.
 * @author Franck Dechavanne
 *
 */
public class DateInterval {
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
		if (start.after(end)) {
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
}
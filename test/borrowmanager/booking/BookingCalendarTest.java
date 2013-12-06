package borrowmanager.booking;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import borrowmanager.element.BorrowableStack;
import static org.junit.Assert.*;

/**
 * Test module for the BookingCalendar class
 * 
 * @author Franck Dechavanne
 *
 */
public class BookingCalendarTest {
	
	public static Date start, start2, startold, end, end2, endold;
	
	@BeforeClass
	public static void setup(){
		start = Calendar.getInstance().getTime();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 6);
		end = c.getTime();
		
		c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		start2 = c.getTime();
		
		c.add(Calendar.DAY_OF_MONTH, 4);
		end2 = c.getTime();
		
		c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2000);
		startold = c.getTime();
		
		c.add(Calendar.DAY_OF_MONTH, 7);
		endold = c.getTime();
	}
	
	@Test
	public void book(){
		BookingCalendar bc = new BookingCalendar(null);
		Integer borrowableId = 0;
		Integer userId = 0;
		Integer quantity = 1;
		
		assertTrue(bc.getBookings().isEmpty());
		
		assertTrue(bc.book(userId, quantity, new DateInterval(start, end), "test"));
		
		assertEquals(1, bc.getBookings().size());
		
		assertFalse(bc.book(userId, quantity, new DateInterval(startold, endold), "test"));
		
		assertEquals(1, bc.getBookings().size());
	}
	
	@Test
	public void getCurrentBooking(){
		BookingCalendar bc = new BookingCalendar(null);
		Integer borrowableId = 0;
		Integer userId = 0;
		Integer quantity = 1;
		
		assertTrue(bc.book(userId, quantity, new DateInterval(end2, end), "test"));
		assertNull(bc.getCurrentBooking());
		
		assertFalse(bc.book(userId, quantity, new DateInterval(startold, start2), "test"));
		assertNull(bc.getCurrentBooking());
		
	}
	
}

package borrowmanager.booking;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookingCalendarTest {
	
	public static Date start, start2, startold, end, end2, endold;
	
	@BeforeClass
	public static void setup(){
		start = Calendar.getInstance().getTime();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);
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
		BookingCalendar bc = new BookingCalendar();
		
		assertTrue(bc.getBookings().isEmpty());
		
		assertTrue(bc.book(0, 0, new DateInterval(start, end), "test"));
		
		assertEquals(1, bc.getBookings().size());
		
		assertFalse(bc.book(0, null, new DateInterval(startold, endold), "test"));
		
		assertEquals(1, bc.getBookings().size());
	}
	
	@Test
	public void getCurrentBooking(){
		BookingCalendar bc = new BookingCalendar();	
		assertTrue(bc.book(0, 0, new DateInterval(end2, end), "test"));
		assertNull(bc.getCurrentBooking());
		assertTrue(bc.book(0, 0, new DateInterval(startold, start2), "test"));
		assertNotNull(bc.getCurrentBooking());
		
	}
	
}

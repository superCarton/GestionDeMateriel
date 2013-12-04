package borrowmanager.booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import borrowmanager.element.BorrowableStack;

public class BookingTest {

	private static Date start, end, start2, end2, startold, endold;
	
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
	public void constructorErrors(){
		try{
			new Booking(0, new BorrowableStack(0, null), null, "test");
			fail("IllegalArgumentException should have been thrown on interval");
			new Booking(null, new BorrowableStack(0, null), new DateInterval(start, end), "test");
			fail("IllegalArgumentException should have been thrown on borrowerid");
			new Booking(0, null, new DateInterval(start, end), "test");
			fail("IllegalArgumentException should have been thrown on borrowableid");
		} catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void overlaps(){
		Booking b = new Booking(0, new BorrowableStack(0, null), new DateInterval(start, end), "test");
		
		assertTrue(b.overlaps(new DateInterval(start2, end2)));
		
		b = new Booking(0, new BorrowableStack(0, null), new DateInterval(end2, end), "test");
		assertFalse(b.overlaps(new DateInterval(start, start2)));
	}
	
	@Test
	public void validate(){
		Booking b = new Booking(0, new BorrowableStack(0, null), new DateInterval(start, end), "test");
		
		assertFalse(b.isValidated());
		
		b.validate();
		
		assertTrue(b.isValidated());
	}
	
	@Test
	public void isLateAndIsCurrent(){
		Booking b = new Booking(0, new BorrowableStack(0, null), new DateInterval(start, end), "test");
		
		assertFalse(b.isLate());
		assertTrue(b.isCurrent());
		
		b = new Booking(0, new BorrowableStack(0, null), new DateInterval(startold, endold), "test");
		
		assertTrue(b.isLate());
		assertFalse(b.isCurrent());
	}
	
	@Test
	public void compareTo(){
		Booking b = new Booking(0, new BorrowableStack(0, null), new DateInterval(start, end), "test"),
				b2 = new Booking(0, new BorrowableStack(0, null), new DateInterval(startold, endold), "test");

		assertTrue(b.compareTo(b2) > 0);
		assertTrue(b2.compareTo(b) < 0);
		assertTrue(b.compareTo(b) == 0);
	}
}

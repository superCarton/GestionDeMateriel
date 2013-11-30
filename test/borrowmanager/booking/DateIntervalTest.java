package borrowmanager.booking;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

public class DateIntervalTest {
	private static Date start, end, start2, end2;
	
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
	}
	
	@Test
	public void constructorErrors(){
		try{
			new DateInterval(end, start);
			fail("IllegalArgumentException should have been thrown");
		} catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void overlaps(){
		DateInterval i = new DateInterval(start, end),
				i2 = new DateInterval(start2, end2);
		
		assertTrue(i.overlaps(i2));
		assertTrue(i2.overlaps(i));
		
		i = new DateInterval(start, end2);
		i2 = new DateInterval(start2, end);
		
		assertTrue(i.overlaps(i2));
		assertTrue(i2.overlaps(i));
		
		i = new DateInterval(start, start2);
		i2 = new DateInterval(end2, end);
		
		assertFalse(i.overlaps(i2));
		assertFalse(i2.overlaps(i));
		
		i = new DateInterval(start, end);
		i2 = new DateInterval(start2, end);
		
		assertTrue(i.overlaps(i2));
		assertTrue(i2.overlaps(i));
	}
	
	@Test
	public void length(){
		DateInterval i = new DateInterval(start, end),
				i2 = new DateInterval(start2, end2),
				i3 = new DateInterval(start, start2);
		
		assertEquals((Integer)7, i.getLength());
		assertEquals((Integer)4, i2.getLength());
		assertEquals((Integer)1, i3.getLength());
	}
}

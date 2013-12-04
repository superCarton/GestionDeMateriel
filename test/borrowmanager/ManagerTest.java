package borrowmanager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import borrowmanager.user.User;
import borrowmanager.user.UserType;

public class ManagerTest {
	@Test
	public void testPermissions(){
		Manager m = new Manager();
		
		m.setUser(new User(0, "student", UserType.STUDENT));
		assertNull(m.getNotYetValidatedBookings());
		m.setUser(new User(1, "teacher", UserType.TEACHER));
		assertNull(m.getNotYetValidatedBookings());
		m.setUser(new User(2, "manager", UserType.STOCK_MANAGER));
		assertNotNull(m.getNotYetValidatedBookings());
	}
	
	@Test
	public void getNotYetValidatedBookings(){
		Manager m = new Manager();
		m.setUser(new User(0, "manager", UserType.STOCK_MANAGER));
		m.fillTemporaryStock();
		
		assertTrue(m.getNotYetValidatedBookings().isEmpty());
		
		m.setUser(new User(1, "student", UserType.STUDENT));
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		assertTrue(m.book(0, 0, new Date(), c.getTime(), "test"));
		
		m.setUser(new User(2, "manager", UserType.STOCK_MANAGER));
		
		assertEquals((Integer)1, (Integer)m.getNotYetValidatedBookings().size());
	}
	
	@Test
	public void isAvailable(){
		Manager m = new Manager();
		m.setUser(new User(0, "manager", UserType.STOCK_MANAGER));
		m.fillTemporaryStock();
		
		assertTrue(m.isAvailable(0));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		assertTrue(m.isAvailable(0, new Date(), c.getTime()));
		
		// TODO
	}
}

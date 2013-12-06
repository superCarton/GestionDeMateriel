package borrowmanager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import borrowmanager.user.User;
import borrowmanager.user.UserType;

/**
 * JUnit test module for Manager
 * @author Franck Dechavanne
 *
 */
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
	public void setUser(){
		Manager m = new Manager();
		
		m.setUser(new User(0, "student", UserType.STUDENT));
		
		try{
			m.setUser(new User(0, "student", UserType.STUDENT));
			
			m.setUser(new User(0, "test", UserType.STUDENT));
			
			fail("Runtime exception should have been thrown");
		} catch(RuntimeException e){
			
		}
	}
	
	@Test
	public void book(){
		Manager m = new Manager();
		m.fillTemporaryStock();

		Integer quantity = 1;
		
		m.setUser(new User(0, "student", UserType.STUDENT));
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		Date plus5 = c.getTime();
		assertTrue(m.book(0, quantity, 0, new Date(), plus5, "test"));
		assertFalse(m.book(0, quantity, 0, new Date(), plus5, "test2"));
		
		c.add(Calendar.DAY_OF_MONTH, 6);
		Date plus11 = c.getTime();
		assertFalse(m.book(1, quantity, 0, new Date(), plus11, "test3"));
		
		c.add(Calendar.DAY_OF_MONTH, 4);
		Date plus15 = c.getTime();
		assertFalse(m.book(1, 0, 0, new Date(), plus15, "test4"));
		
		m.setUser(new User(1, "manager", UserType.STOCK_MANAGER));
		assertFalse(m.book(1, quantity, 1, new Date(), plus5, "test5"));
		
		m.setUser(new User(2, "teacher", UserType.TEACHER));
		assertTrue(m.book(1, quantity, 2, plus11, plus15, "test6"));		
	}
	
	@Test
	public void getNotYetValidatedBookings(){
		Manager m = new Manager();
		m.setUser(new User(0, "manager", UserType.STOCK_MANAGER));
		m.fillTemporaryStock();
		
		Integer quantity = 1;
		
		assertTrue(m.getNotYetValidatedBookings().isEmpty());
		
		m.setUser(new User(1, "student", UserType.STUDENT));
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		assertTrue(m.book(0, quantity, 0, new Date(), c.getTime(), "test"));
		
		m.setUser(new User(2, "manager", UserType.STOCK_MANAGER));
		
		assertEquals((Integer)1, (Integer)m.getNotYetValidatedBookings().size());
		m.getNotYetValidatedBookings().get(0).validate();
		
		assertEquals((Integer)0, (Integer)m.getNotYetValidatedBookings().size());
	}
	
	@Test
	public void isAvailable(){
		Manager m = new Manager();
		m.setUser(new User(0, "manager", UserType.STOCK_MANAGER));
		m.fillTemporaryStock();
		
		assertTrue(m.isAvailable(0, 1));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		assertTrue(m.isAvailable(0, 1, new Date(), c.getTime()));
		
		// TODO
	}
}

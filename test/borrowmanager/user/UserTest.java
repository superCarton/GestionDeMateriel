package borrowmanager.user;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {	
	@Test
	public void constructorErrors(){
		User student = new User(0, "the famous \"test\" student", UserType.STUDENT),
				teacher = new User(1, "the bad teacher", UserType.TEACHER),
				manager = new User(2, "the mighty stock manager", UserType.STOCK_MANAGER);
		
		assertFalse(student.canValidateBookings());
		assertFalse(teacher.canValidateBookings());
		assertTrue(manager.canValidateBookings());
		
		assertEquals((Integer)7, student.getMaxBookingLength());
		assertEquals((Integer)30, teacher.getMaxBookingLength());
		
		assertEquals((Integer)10, student.getMaxReservationLength());
		assertEquals((Integer)60, teacher.getMaxReservationLength());
	}
}

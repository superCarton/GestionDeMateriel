package borrowmanager.model.booking;

import java.util.Date;

public class Reminder {
	private Date date;
	private boolean read;
	
	public Reminder(Date d) {
		date = d;
		read = false;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void markRead() {
		read = true;
	}
	
	public boolean hasBeenRead() {
		return read;
	}
}

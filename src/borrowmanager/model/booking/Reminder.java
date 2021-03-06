package borrowmanager.model.booking;

import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Reminder {
	private Date date;
	private boolean read;
	
	public Reminder(Date d) {
		date = d;
		read = false;
	}
	
	public Reminder(JsonObject json) {
		fromJSON(json);
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

	public JsonElement toJSON() {
		JsonObject json = new JsonObject();
		json.addProperty("date", date.getTime());
		json.addProperty("read", read);
		return json;
	}
	
	private void fromJSON(JsonObject json) {
		date = new Date(json.get("date").getAsLong());
		read = json.get("read").getAsBoolean();
	}
}

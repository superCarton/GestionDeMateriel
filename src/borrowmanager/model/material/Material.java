/*
 * 
 */
package borrowmanager.model.material;

import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import borrowmanager.model.element.State;

/**
 * The Class Material.
 * @author Marina Delerce & Romain Guillot & Tom Guillermin
 * @version 1.0.0
 */
public class Material {

	/** The material_type. */
	private MaterialType materialType;
	
	private State state;
	
	/** The serial_number. */
	private String serialNumber;

	private Integer id;
	
	private Boolean inRepair;
	private Date repairEnd = null;

	private int healthPoint;
	
	private Integer repairDuration = 3; // in days
	
	/**
	 * Instantiates a new material.
	 *
	 * @param type the type
	 * @param serial the serial
	 */
	public Material(MaterialType type, Integer id, String serial) {
		this(type, id, serial, 100);
	}
	
	/**
	 * Instantiates a new material.
	 *
	 * @param type the type
	 * @param serial the serial
	 */
	public Material(MaterialType type, Integer id, String serial, Integer health) {
		this.healthPoint = 100;
		updateState();
		this.materialType = type;
		this.id = id;
		this.serialNumber = serial;
		this.inRepair = false;
	}
	
	public Material(JsonObject json, MaterialType type) {
		materialType = type;
		fromJSON(json);
	}

	/**
	 * Gets the material type.
	 *
	 * @return the material type
	 */
	public MaterialType getMaterialType(){
		return materialType;
	}
	
	/**
	 * Gets the material state.
	 * @return the material state
	 */
	public State getState() {
		return state;
	}
	
	public void setDestroyed(boolean b) {
		if (b) state = State.DESTROYED;
	}
	
	public void naturalDegradation() {
		healthPoint -= 10;
	}
	
	public void updateState() {
		if (healthPoint == 100) {
			state = State.NEW;
		}
		else if (healthPoint >= 85) {
			state = State.EXCELLENT;
		}
		else if (healthPoint >= 50) {
			state = State.GOOD;
		}
		else if (healthPoint >= 10) {
			state = State.BAD;
		}
		else {
			state = State.DESTROYED;
		}
	}
	
	public void sendInRepair(Date now) {
		inRepair = true;
		// Add 3 days of repair
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DATE, getRepairDuration());
		repairEnd = c.getTime();
	}
	
	/**
	 * Returns true if the material is in repair.
	 * @return
	 */
	public Boolean isInRepair() {
		return inRepair;
	}
	
	public void takeBackFromRepair() {
		inRepair = false;
	}
	
	/**
	 * Get the date at which the repair ends.
	 * @return
	 */
	public Date getRepairEnd() {
		return repairEnd;
	}
	
	/**
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
	public String getSerialNumber(){
		return serialNumber;
	}
	
	/**
	 * Gets the serializable description.
	 *
	 * @return the serializable description
	 */
	public HashMap<String, Object> getSerializableDescription(){

		HashMap<String, Object> materialDescription = new HashMap<String, Object>();
		materialDescription.put("materialType", materialType.getSerializableDescription());
		materialDescription.put("serialNumber", serialNumber);
		
		return materialDescription;
	}
	
	public Boolean isAvailable() {
		return !inRepair && state != State.DESTROYED;
	}
	
	/**
	 * Creates the object.
	 *
	 * @param className the class name
	 * @return the object
	 */
	static Object createObject(String className) {
		 
		Object object = null;
		try {
		    Class<?> classDefinition = Class.forName(className);
		    object = classDefinition.newInstance();
	    } catch (InstantiationException e) {
		          System.out.println(e);
		} catch (IllegalAccessException e) {
		          System.out.println(e);
		} catch (ClassNotFoundException e) {
		          System.out.println(e);
		}
		return object;
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Material)) return false;
		Material m = (Material)o;
		if (m.serialNumber != this.serialNumber) return false;
		if (!m.materialType.equals(materialType)) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return this.getSerialNumber() + " ( " + this.getMaterialType().getName() + ") ";
	}

	public Integer getId() {
		return id;
	}

	public JsonElement toJSON() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("stateName", state.name());
		json.addProperty("serial_number", serialNumber);
		json.addProperty("isInRepair", inRepair);
		json.addProperty("repairEnd", repairEnd != null ? repairEnd.getTime() : null);

		return json;
	}
	
	public void fromJSON(JsonObject json) {
		id = json.get("id").getAsInt();
		state = State.valueOf(json.get("stateName").getAsString());
		serialNumber = json.get("serial_number").getAsString();
		inRepair = json.get("isInRepair").getAsBoolean();
		
		JsonElement repairEndJson = json.get("repairEnd");
		repairEnd = (repairEndJson != null) ? new Date(json.get("repairEnd").getAsLong()) : null;
	}

	public Integer getRepairDuration() {
		return repairDuration;
	}

	/**
	 * Returns the full name of the material : brand model serial#
	 * @return
	 */
	public String getFullName() {
		return this.materialType.getFullName()+" with serial number "+serialNumber;
	}
}

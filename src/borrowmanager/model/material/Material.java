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
	private MaterialType material_type;
	
	private State state;
	
	/** The serial_number. */
	private String serial_number;

	private Integer id;
	
	/**
	 * Instantiates a new material.
	 *
	 * @param type the type
	 * @param serial the serial
	 */
	public Material(MaterialType type, Integer id, String serial) {
		this.state = State.NEW;
		this.material_type = type;
		this.id = id;
		this.serial_number = serial;
	}
	
	/**
	 * Instantiates a new material.
	 *
	 * @param description the description
	 */
	public Material(HashMap<String, Object> description){
		serial_number = (String) description.get("serialNumber");
		HashMap<String, Object> materialTypeDescription = (HashMap<String, Object>) description.get("materialType");
		material_type = (MaterialType) createObject((String) materialTypeDescription.get("className"));
		material_type.setObject(materialTypeDescription);
	}
	
	/**
	 * Gets the material type.
	 *
	 * @return the material type
	 */
	public MaterialType getMaterialType(){
		return material_type;
	}
	
	/**
	 * Gets the material state.
	 * @return the material state
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
	public String getSerialNumber(){
		return serial_number;
	}
	
	/**
	 * Gets the serializable description.
	 *
	 * @return the serializable description
	 */
	public HashMap<String, Object> getSerializableDescription(){

		HashMap<String, Object> materialDescription = new HashMap<String, Object>();
		materialDescription.put("materialType", material_type.getSerializableDescription());
		materialDescription.put("serialNumber", serial_number);
		
		return materialDescription;
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
		if (m.serial_number != this.serial_number) return false;
		if (!m.material_type.equals(material_type)) return false;
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
		json.addProperty("materialTypeId", material_type.getId());
		json.addProperty("stateLevel", state.getLevel());
		json.addProperty("serial_number", serial_number);
		return json;
	}
}

/*
 * 
 */
package borrowmanager.model.material;

import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * The Class MaterialType.
 * @author Marina Delerce & Romain Guillot & Tom Guillermin 
 * @version 1.0.0
 */
public class MaterialType implements JsonSerializer<MaterialType>, JsonDeserializer<MaterialType> {

	private Integer id;
	/** The name. */
	protected String name;
	
	/** The brand. */
	protected String brand;
	
	/** The description. */
	protected String description;
	
	/** The reference. */
	protected int reference;
	
	/** The max time loan. */
	protected int maxTimeLoan;

	/**
	 * Instantiates a new material type.
	 * @param id TODO
	 * @param name the name
	 * @param brand the brand
	 * @param description the description
	 * @param reference the reference
	 * @param maxTimeLoan the max time loan
	 * @param id the unique id
	 */
	public MaterialType(int id, String name, String brand,
			String description, int reference, int maxTimeLoan) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.description = description;
		this.reference = reference;
		this.maxTimeLoan = maxTimeLoan;
	}

	/**
	 * Instantiates a new material type.
	 */
	public MaterialType() {}
	
	/**
	 * Returns the ID
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the object.
	 *
	 * @param description the description
	 */
	public void setObject(HashMap<String, Object> description){}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the brand.
	 *
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the reference.
	 *
	 * @return the reference
	 */
	public int getReference() {
		return reference;
	}

	/**
	 * Gets the max time loan.
	 *
	 * @return the max time loan
	 */
	public int getMaxTimeLoan() {
		return maxTimeLoan;
	}

	/**
	 * Sets the max time loan.
	 *
	 * @param maxTimeLoan the new max time loan
	 */
	public void setMaxTimeLoan(int maxTimeLoan) {
		this.maxTimeLoan = maxTimeLoan;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone(){
		return null;
	}
	
	/**
	 * Gets the serializable description.
	 *
	 * @return the serializable description
	 */
	public HashMap<String, Object> getSerializableDescription(){
		return null;
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof MaterialType)) return false;
		MaterialType m = (MaterialType)o;
		if (name!=m.name || brand != m.brand || description != m.description || reference != m.reference || maxTimeLoan != m.maxTimeLoan) return false; 
		return true;
	}
	
	public String toJSON() {
		Gson json = new Gson();
		return json.toJson(this);
	}

	@Override
	public JsonElement serialize(MaterialType arg0, Type arg1, JsonSerializationContext arg2) {
		final JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("name", name);
		json.addProperty("brand", brand);
		json.addProperty("maxBorrowDuration", maxTimeLoan);
		json.addProperty("description", description);
		json.addProperty("reference", reference);
		return json;
	}

	@Override
	public MaterialType deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

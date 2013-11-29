package borrowmanager.element;

import java.util.Map;

public class BorrowableG {
	private Integer id;
	private String name;
	private Map<String, String> data;
	public HardwareType elementType;	
	
	public BorrowableG(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public HardwareType getType() {
		return elementType;
	}

	public Map<String, String> getData() {
		return this.data;
	}

	public Boolean hasFeature(String feature) {
		return data.containsKey(feature);
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the elementType
	 */
	public HardwareType getElementType() {
		return elementType;
	}
	
}
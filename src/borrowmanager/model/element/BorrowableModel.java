package borrowmanager.model.element;

import java.util.Map;

/**
 * Class containing the data defining a borrowable
 * @author Tom Guillermin
 *
 */
public class BorrowableModel {
	private String name;
	private Integer id;
	private Map<String, String> data;
	public HardwareType elementType;	
	
	public BorrowableModel(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	// TODO
	public void loadData() {

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

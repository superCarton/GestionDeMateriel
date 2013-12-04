package borrowmanager.element;

import java.util.Map;

public class BorrowableStack {
	private Integer id;
	private BorrowableModel model;
	private State state;
	private Integer quantity;
	
	public BorrowableStack(Integer id, BorrowableModel model) {
		this(id, model, State.NEW);
	}
	
	public BorrowableStack(Integer id, BorrowableModel model, State state) {
		this.id = id;
		this.model = model;
		this.state = state;
	}

	public HardwareType getType() {
		return model.getType();
	}

	public Map<String, String> getData() {
		return model.getData();
	}

	/**
	 * Returns true if the borrowable has a feature.
	 * @param feature The name of the feature.
	 * @return True if the borrowable has the feature.
	 */
	public Boolean hasFeature(String feature) {
		return model.hasFeature(feature);
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
		return model.getName();
	}
	
	public BorrowableModel getModel() {
		return model;
	}
}
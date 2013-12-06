package borrowmanager.element;

import java.util.Map;

public class BorrowableStack {
	private Integer id;
	private BorrowableModel model;
	private State state;
	private Integer quantity;
	
	public BorrowableStack(Integer id, BorrowableModel model) {
		this(id, model, 1);
	}
	
	public BorrowableStack(Integer id, BorrowableModel model, Integer quantity) {
		this(id, model, quantity, State.NEW);
	}
	
	public BorrowableStack(Integer id, BorrowableModel model, Integer quantity, State state) {
		this.id = id;
		this.model = model;
		this.state = state;
		this.quantity = quantity;
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
	
	public Integer getQuantity() {
		return quantity;
	}
	
	/**
	 * Extract a substack of the stack.
	 * Returns the extracted stack.
	 * Returns null if it is impossible to extract a stack of this size.
	 * @param n The size of the substack to extract.
	 * @return The extracted stack.
	 */
	public BorrowableStack extract(Integer n) {
		if (n >= quantity) {
			BorrowableStack newStack = new BorrowableStack(-1, model, quantity);
			quantity -= n;
			return newStack;
		}
		else {
			return null;
		}
	}
}
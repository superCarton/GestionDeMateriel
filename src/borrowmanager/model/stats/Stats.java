package borrowmanager.model.stats;

import borrowmanager.model.Manager;

public abstract class Stats<T> {
	protected Manager manager;
	private String name, description;
	
	
	public Stats(Manager manager, String name, String description) {
		this.manager = manager;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public abstract T calculate();
}

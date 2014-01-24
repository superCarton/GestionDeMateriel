package borrowmanager.model.element;

public enum State {
	NEW(1, "New"),
	EXCELLENT(2, "Excellent"),
	GOOD(3, "Good"),
	BAD(4, "Bad"),
	DESTROYED(5, "Destroyed");
	
	private int level;
	private String name;
	
	State(int level, String s) {
		this.level = level;
		this.name = s;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getName() {
		return name;
	}
}

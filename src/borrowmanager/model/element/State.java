package borrowmanager.model.element;

public enum State {
	NEW(1),
	EXCELLENT(2),
	GOOD(3),
	BAD(4),
	DESTROYED(5);
	
	private int level;
	
	State(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
}

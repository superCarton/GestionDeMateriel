package borrowmanager.element;

public class BorrowableG {
	private Integer id;
	private String name;
	private Map<String, String> data;
	public HardwareType elementType;

	public BorrowableG(Integer id, String name) {
		throw new UnsupportedOperationException();
	}

	public HardwareType getType() {
		throw new UnsupportedOperationException();
	}

	public Map<String, String> getData() {
		return this.data;
	}

	public Boolean hasFeature(String feature) {
		throw new UnsupportedOperationException();
	}
}
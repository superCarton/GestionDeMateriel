package borrowmanager.model.stats;

/**
 * The Stats Value class for return two parameters 
 * @author Jonathan Pujol & Tom Guillermin
 * 
 */
public class StatValue<A, B> {
	private A a;
	private B b;
	
	public StatValue(A key, B value) {
		this.a = key;
		this.b = value;
	}
	
	public A getKey() {
		return a;
	}
	
	public B getValue() {
		return b;
	}
}

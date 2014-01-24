package borrowmanager.model.stats;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

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

package borrowmanager.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class TextInterfacePage {
	private BufferedReader br;
	
	public TextInterfacePage() {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		show();
	}
	
	protected String input() {
		String s = null;
	    try {
			s = br.readLine();
		} catch (IOException e) {
			// nothing
		}
	    return s;
	}
	
	protected abstract boolean show();
	
	
}

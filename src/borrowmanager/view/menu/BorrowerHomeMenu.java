package borrowmanager.view.menu;

import java.util.EventObject;

import borrowmanager.model.Manager;
import borrowmanager.view.TextInterfaceOptionPage;

public class BorrowerHomeMenu extends TextInterfaceOptionPage {

	private Manager manager;
	
	public BorrowerHomeMenu(Manager m) {
		manager = m;
	}
	
	
	@Override
	protected void build() {
		addOption("borrow", "Borrow something");
		addOption("giveBack", "Give back something");
		addOption("logout", "Log out");
	}
	
	@Override
	public void handleCommand(String c) {
		if (c.equals("borrow")) {
			
		}
		else if (c.equals("giveBack")) {
			
		}
		else if (c.equals("logout")) {
			
		}
	}

}

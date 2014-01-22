package borrowmanager.view.menu;

import java.util.EventObject;

import borrowmanager.model.Manager;
import borrowmanager.view.TextInterfaceOptionPage;

public class MainMenu extends TextInterfaceOptionPage {
	private Manager manager;
	public MainMenu(Manager m) {
		manager = m;
	}
	
	@Override
	protected void build() {
		addOption("login","Login");
		addOption("quit", "Quit");
	}
	
	@Override
	public void handleEvent(EventObject e) {
		String command = (String) e.getSource();
		if (command.equals("login")) {
			new LoginMenu(manager);
		}
		else if (command.equals("quit")) {
			System.exit(0);
		}
		
	}



}

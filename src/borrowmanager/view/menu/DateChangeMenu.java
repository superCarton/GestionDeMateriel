package borrowmanager.view.menu;

import java.util.Date;

import borrowmanager.model.Manager;
import borrowmanager.view.TextInterfacePage;

public class DateChangeMenu extends TextInterfacePage {
	private Manager manager;

	public DateChangeMenu(Manager m) {
		manager = m;
	}
	
	@Override
	public TextInterfacePage display() {
		System.out.println("You can change the date used as 'today' in the app (for testing purposes)");
		manager.now = readDate();
		System.out.println("New date set !");
		System.out.println();
		System.out.println("Press enter to continue");
		input();
		return null;
	}
	
	
}

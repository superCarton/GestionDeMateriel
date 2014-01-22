package borrowmanager;

import borrowmanager.model.Manager;
import borrowmanager.view.TextInterface;
import borrowmanager.view.menu.MainMenu;

public class App {
	public static void main(String args[]) {
		Manager manager = new Manager();
		new MainMenu(manager);
		/*TextInterface ui = new TextInterface();
		ui.start();*/
	}
}

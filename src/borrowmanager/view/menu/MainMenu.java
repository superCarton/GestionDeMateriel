package borrowmanager.view.menu;


import borrowmanager.model.Manager;
import borrowmanager.view.TextInterfaceOptionPage;

public class MainMenu extends TextInterfaceOptionPage {
	private Manager manager;
	public MainMenu(Manager m) {
		manager = m;
		ready();
	}
	
	@Override
	protected void build() {
		addOption("login","Login");
		addOption("newAccount","Create a new account");
		addOption("quit", "Quit");
	}
	
	@Override
	public void handleCommand(String command) {
		if (command.equals("login")) {
			new LoginMenu(manager);
		}
		else if (command.equals("newAccount")) {
			new CreateAccountMenu(manager);
		}
		else if (command.equals("quit")) {
			System.exit(0);
		}
		
	}
}

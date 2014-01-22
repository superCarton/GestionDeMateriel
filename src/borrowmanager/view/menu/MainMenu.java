package borrowmanager.view.menu;


import borrowmanager.model.Manager;
import borrowmanager.view.TextInterfaceOptionPage;
import borrowmanager.view.TextInterfacePage;

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
		addOption("displayDate","[TEST] Display the current date ");
		addOption("changeDate","[TEST] Change the date");
		addOption("quit", "Quit");
	}
	
	@Override
	public TextInterfacePage handleCommand(String command) {
		if (command.equals("login")) {
			openChildPage(new LoginMenu(manager));
		}
		else if (command.equals("newAccount")) {
			openChildPage(new CreateAccountMenu(manager));
		}
		else if (command.equals("displayDate")) {
			System.out.println();
			System.out.println("Current date: "+simpleDateFormat.format(manager.now));
			System.out.println("Press enter to continue");
			input();
		}
		else if (command.equals("changeDate")) {
			openChildPage(new DateChangeMenu(manager));
		}
		else if (command.equals("quit")) {
			System.exit(0);
		}
		display();
		return this;
	}
}

package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.user.User;
import borrowmanager.view.TextInterfacePage;

public class LoginMenu extends TextInterfacePage {
	private Manager manager;
	
	public LoginMenu(Manager m) {
		manager = m;
	}
	
	@Override
	protected boolean show() {
		System.out.println("Login: ");
		String login = input();
		// No password
		
		User user = manager.getUsersManager().getUserByLogin(login);
	    if (user != null) {
	    	manager.setActiveUser(user);
		    System.out.println("Hello "+user.getName()+" !");
		    new HomeMenu(manager);
		    return false;
	    }
	    else {
	    	System.out.println("User "+login+" not found!");
	    	new LoginMenu(manager);
	    	return false;
	    }
	}

}

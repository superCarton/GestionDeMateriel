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
		String name = input();
		
		User user = manager.getUserByName(name);
	    if (user != null) {
	    	manager.setUser(user);
		    System.out.println("Hello "+user.getName()+" !");
		    new UserHomeMenu(manager);
		    return false;
	    }
	    else {
	    	System.out.println("User "+name+" not found!");
	    	new LoginMenu(manager);
	    	return false;
	    }
		return false;
	}

}

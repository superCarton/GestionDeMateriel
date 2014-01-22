package borrowmanager.view.menu;

import java.io.IOException;

import borrowmanager.UNUSED_user.UNUSED_UserType;
import borrowmanager.model.Manager;
import borrowmanager.model.user.*;
import borrowmanager.view.TextInterfacePage;

public class CreateAccount extends TextInterfacePage {
	private Manager manager;
	public CreateAccount(Manager manager) {
		this.manager = manager;
	}
	
	@Override
	protected boolean show() {
		System.out.println("Please select what you want to be :");
		System.out.println("Available UserTypes :");
		System.out.println("   1. STUDENT");
		System.out.println("   2. TEACHER");
		System.out.println("   3. STOCK_MANAGER");
		System.out.println("Type 1,2 or 3 : ");
		
		boolean valid = false;
		String input = null;
		UNUSED_UserType userType = null ;
		String userName = null;
		while (!valid) {
			input = input();
			
			valid = true;
			if (input.equals("1")) {
				userType = UNUSED_UserType.STUDENT;
			} else if (input.equals("2")) {
				userType = UNUSED_UserType.TEACHER;
			} else if (input.equals("3")) {
				userType = UNUSED_UserType.STOCK_MANAGER;
			} else {
				valid = false;
			}
		}
		
		System.out.println("Please enter your name : ");
		valid = false;
		System.out.println("Please enter your first name :");
		String firstName = input();
		System.out.println("Please enter your first name :");
		String lastName = input();
		System.out.println("Please enter your password :");
		String password = input();
		String login = inputLogin();
		
		Integer id = manager.getIDAutoIncrement();
		
		User u = null;
		switch(userType) {
			case STUDENT:
				u = new Student(id, firstName, lastName, login, password);
				break;
			case TEACHER:
				u = new Teacher(id, firstName, lastName, login, password);
				break;
			case STOCK_MANAGER:
				u = new StockManager(id, firstName, lastName, login, password);
				break;
			default:
				throw new RuntimeException("Unknown user type "+userType);
		}
		if (u != null) manager.setActiveUser(u);
		new HomeMenu(manager);
		
		return false;
	}
	
	public String inputLogin() {	
		String login = null;
		boolean valid = false;
		while (!valid) {
			System.out.println("Please enter your first name :");
			login = input();
			User existing = manager.getUsersManager().getUserByLogin(login);
			if (existing == null) valid = true;
		}
		
		return login;
	}

}

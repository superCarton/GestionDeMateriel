package borrowmanager.view.menu;

import java.io.IOException;

import borrowmanager.UNUSED_user.UNUSED_UserType;
import borrowmanager.model.Manager;
import borrowmanager.model.user.User;
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
		String login = inputLogin();
		
		Integer id = manager.getIDAutoIncrement();
		
		User u;
		switch(userType) {
			case STUDENT:
				u = new Student(id, firstName, lastName, login);
				break;
			case TEACHER:
				u = manager.createTeacher(id, firstName, lastName, login);
				break;
			case STOCK_MANAGER:
				u = manager.createStockManager(id, firstName, lastName, login);
				break;
		}
		manager.setUser(u);
		new HomeMenu(u);
		
		return false;
	}
	
	public String inputLogin() {
		System.out.println("Please enter your first name :");
		String login = input();
	}

}

package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.stats.BiggestBorrower;
import borrowmanager.model.stats.StatValue;
import borrowmanager.model.stats.WorstBorrower;
import borrowmanager.model.user.User;
import borrowmanager.view.TextInterfaceOptionPage;
import borrowmanager.view.TextInterfacePage;

public class StatisticsMenu extends TextInterfaceOptionPage {

	private Manager manager ;
	public StatisticsMenu(Manager m) {
		manager = m;
		ready();
	}

	@Override
	protected void build() {
		addOption("biggestBorrower","Biggest borrower");
		addOption("worstBorrower","Worst borrower");
		addOption("mostBorrowed","Most borrowed");
		addOption("mostBrokenMaterial","Most broken material");
	}

	@Override
	protected TextInterfacePage handleCommand(String c) {
		if (c.equals("biggestBorrower")) {
			BiggestBorrower stat = new BiggestBorrower(manager);
			StatValue<User, Integer> result = stat.calculate();
			System.out.println(stat.getDescription());
			System.out.println("The biggest borrower is "+result.getKey().getName()+" with "+result.getValue()+" borrowings.");
		}
		else if (c.equals("worstBorrower")) {
			WorstBorrower stat = new WorstBorrower(manager);
			StatValue<User, Integer> result = stat.calculate();
			System.out.println(stat.getDescription());
			System.out.println("The worst borrower is "+result.getKey().getName()+" with "+result.getValue()+" borrowings returned late.");
		}
		else if (c.equals("mostBorrowed")) {
			/*MostBorrowedMaterial stat = new MostBorrowed(manager);
			StatValue<MaterialType, Integer> result = stat.calculate();
			System.out.println(stat.getDescription());
			System.out.println("The most borrowed material is "+result.getKey().getName()+" with "+result.getValue()+" borrows.");
			*/
		}
		enterToContinue();
		return null;
	}

}

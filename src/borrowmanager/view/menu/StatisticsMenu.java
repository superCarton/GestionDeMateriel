package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.stats.BiggestBorrower;
import borrowmanager.view.TextInterfaceOptionPage;
import borrowmanager.view.TextInterfacePage;

public class StatisticsMenu extends TextInterfaceOptionPage {

	private Manager manager ;
	public StatisticsMenu(Manager m) {
		manager = m;
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
			stat.calculate();
		}
		return null;
	}

}

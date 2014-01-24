package borrowmanager.view.menu;

import java.util.Map;

import borrowmanager.model.Manager;
import borrowmanager.model.material.MaterialCategory;
import borrowmanager.model.material.MaterialType;
import borrowmanager.model.stats.BiggestBorrower;
import borrowmanager.model.stats.MostBorrowedMaterial;
import borrowmanager.model.stats.MostBrokenMaterial;
import borrowmanager.model.stats.NumberByCategory;
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
		setHasGoBackOption(true);
		addOption("numberByCategory", "Number of material by category");
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
			MostBorrowedMaterial stat = new MostBorrowedMaterial(manager);
			StatValue<MaterialType, Integer> result = stat.calculate();
			System.out.println(stat.getDescription());
			System.out.println("The most borrowed material is "+result.getKey().getFullName()+" with "+result.getValue()+" borrows.");
		}
		else if (c.equals("mostBrokenMaterial")) {
			MostBrokenMaterial stat = new MostBrokenMaterial(manager);
			StatValue<MaterialType, Integer> result = stat.calculate();
			System.out.println(stat.getDescription());
			if (result.getKey() == null) {
				System.out.println("Nothing is broken in the stock ! :)");
			}
			else {
				System.out.println("The most broken material is "+result.getKey().getFullName()+" with "+result.getValue()+" currently broken.");
			}
		}
		else if (c.equals("numberByCategory")) {
			NumberByCategory stat = new NumberByCategory(manager);
			Map<MaterialCategory, StatValue<Integer, Double>> result = stat.calculate();
			System.out.println(stat.getDescription());
			for (MaterialCategory cat : result.keySet()) {
				System.out.println(cat.name()+" : "+ result.get(cat).getKey()+" ("+result.get(cat).getValue()*100+"%)");
			}
		}
		else {
			
		}
		enterToContinue();
		return this;
	}

}

package borrowmanager.view.menu;

import java.util.Date;
import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.booking.DateInterval;
import borrowmanager.model.element.BorrowableStock;
import borrowmanager.model.material.Material;
import borrowmanager.model.user.Borrower;
import borrowmanager.view.ItemPicker;
import borrowmanager.view.TextInterfacePage;

public class StocksList extends TextInterfacePage {

	private Manager manager;
	
	public StocksList(Manager m) {
		manager = m;
	}
	
	@Override
	public TextInterfacePage display() {
		System.out.println("Here are the available stocks.");
		System.out.println("Pick one to have the details of each item :");
		// Pick an item
		List<BorrowableStock> stocks = manager.getStockList();
		ItemPicker picker = new ItemPicker(stocks);
		picker.display();
		Integer index = picker.getPickedItemId();
		
		// If the user wants to go back
		if (index == null) {
			return null;
		}
		
		BorrowableStock s = stocks.get(index);
		openChildPage(new StockView(manager, s));
		
		return null;
	}
}

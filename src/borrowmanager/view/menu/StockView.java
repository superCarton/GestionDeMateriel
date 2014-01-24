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

public class StockView extends TextInterfacePage {

	private Manager manager;
	private BorrowableStock stock;
	
	public StockView(Manager m, BorrowableStock s) {
		manager = m;
		stock = s;
	}
	
	@Override
	public TextInterfacePage display() {
		System.out.println("Here are the list of the items in the stock.");
		
		for (Material m : stock.getMaterials()) {
			System.out.println("  - "+m.getLineDescription());
		}
		enterToContinue();
		return null;
	}
}

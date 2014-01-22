package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.element.BorrowableStock;
import borrowmanager.view.ItemPicker;

public class ItemInStockPicker extends ItemPicker {

	public ItemInStockPicker(Manager m) {
		super(m.getStockList());
	}

}

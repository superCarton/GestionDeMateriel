package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.view.ItemPicker;

public class ItemInStockPicker extends ItemPicker {

	public ItemInStockPicker(Manager m) {
		super(m.getStockList());
	}

}

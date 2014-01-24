package borrowmanager.view;

import java.util.List;

import borrowmanager.model.element.BorrowableStock;

// TODO : rename to StockPicker
public class ItemPicker extends Picker<Integer, BorrowableStock> {
	
	public ItemPicker(List<BorrowableStock> l) {
		super(l);
	}

	@Override
	protected String elementToListString(BorrowableStock element) {
		return element.getName();
	}

	@Override
	protected Integer getUniqueIdentifier(BorrowableStock element) {
		return getList().indexOf(element);
	}
}

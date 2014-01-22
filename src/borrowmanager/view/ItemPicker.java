package borrowmanager.view;

import java.util.Collection;

import borrowmanager.model.element.BorrowableStock;

public class ItemPicker extends Picker<Integer, BorrowableStock> {
	
	public ItemPicker(Collection<BorrowableStock> l) {
		super(l);
	}

	@Override
	protected String elementToListString(BorrowableStock element) {
		return element.getName();
	}

	@Override
	protected Integer getUniqueIdentifier(BorrowableStock element) {
		return element.getId();
	}
}

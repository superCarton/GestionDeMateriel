package borrowmanager.view;

import java.util.Collection;

import borrowmanager.model.element.BorrowableStock;

public abstract class Picker<ID, T> extends TextInterfacePage {
	private Collection<T> itemList;
	private ID pickedItemId = null;
	
	public Picker(Collection<T> l) {
		itemList = l;
	}
	
	protected Collection<T> getList() {
		return itemList;
	}
	
	public ID getPickedItemId() {
		return pickedItemId;
	}

	@Override
	protected boolean show() {
		String input = null;
		Boolean valid = false;
		
		while (!valid) {
			System.out.println("Select an item in the following list :");
			for (T element : itemList) {
				System.out.println("   "+elementToListString(element));
			}
			System.out.println("   b. Go back");
			
			input = input();
			
			for (T element : itemList) {
				ID uniqueIdentifier = getUniqueIdentifier(element);
				if (input.equals(uniqueIdentifier)) {
					pickedItemId = uniqueIdentifier;
					return false;
				}
			}
			if (input.toLowerCase().equals("b")) {
				pickedItemId = null;
				return false;
			}
		}
		return false;
	}
	
	protected abstract String elementToListString(T element) ;
	
	protected abstract ID getUniqueIdentifier(T element);
}

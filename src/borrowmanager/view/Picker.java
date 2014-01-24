package borrowmanager.view;

import java.util.Collection;
import java.util.List;

import borrowmanager.model.element.BorrowableStock;

public abstract class Picker<ID, T> extends TextInterfacePage {
	private List<T> itemList;
	private ID pickedItemId = null;
	
	public Picker(List<T> l) {
		itemList = l;
	}
	
	protected List<T> getList() {
		return itemList;
	}
	
	public ID getPickedItemId() {
		return pickedItemId;
	}

	@Override
	public TextInterfacePage display() {
		String input = null;
		Boolean valid = false;
		
		while (!valid) {
			System.out.println("Select an item in the following list :");
			for (T element : itemList) {
				System.out.println("   "+getUniqueIdentifier(element)+". "+elementToListString(element));
			}
			System.out.println();
			System.out.println("   b. Go back");
			
			input = input();
			
			if (input.toLowerCase().equals("b")) {
				pickedItemId = null;
				return null;
			}
			
			for (T element : itemList) {
				ID uniqueIdentifier = getUniqueIdentifier(element);
				if (input.equals(uniqueIdentifier.toString())) {
					pickedItemId = uniqueIdentifier;
					return null;
				}
			}
		}
		return null;
	}
	
	protected abstract String elementToListString(T element) ;
	
	protected abstract ID getUniqueIdentifier(T element);
}

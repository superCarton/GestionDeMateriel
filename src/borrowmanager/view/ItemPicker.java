package borrowmanager.view;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import borrowmanager.model.element.BorrowableStock;

public class ItemPicker extends TextInterfacePage {
	
	private Collection<BorrowableStock> itemList;
	private Integer pickedItemId = null;
	
	public ItemPicker(Collection<BorrowableStock> l) {
		itemList = l;
	}
	
	public Integer getPickedItemId() {
		return pickedItemId;
	}

	@Override
	protected boolean show() {
		String input = null;
		Boolean valid = false;
		
		while (!valid) {
			System.out.println("Select an item in the following list :");
			for (BorrowableStock b : itemList) {
				String borrowableName = b.getName();
				System.out.println("   "+b.getId()+". "+borrowableName);
			}
			System.out.println("   b. Go back");
			
			input = input();
			
			for (BorrowableStock b : itemList) {
				Integer id = b.getId();
				if (input.equals(Integer.toString(id))) {
					pickedItemId = id;
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
}

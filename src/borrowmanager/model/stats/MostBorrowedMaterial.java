package borrowmanager.model.stats;

import java.util.Collection;

import borrowmanager.model.Manager;
import borrowmanager.model.element.BorrowableStock;
import borrowmanager.model.material.MaterialType;

/**
 * The MostBorrowingMaterial is a class extends of Stats which gives the most borrowing material
 * @author Jonathan Pujol
 * 
 */
public class MostBorrowedMaterial extends Stats<MaterialType> {

	public MostBorrowedMaterial(Manager manager) {
		super(manager, "Most borrowing Material" , "The material which is the most borrow");
	}

	@Override
	public MaterialType calculate() {
		MaterialType  mostBorrow = null;
		int numberOfBorrow = 0;
		Collection<BorrowableStock> object = manager.getStockList();
		for (BorrowableStock o : object) {
			int number = o.getCalendar().getBookings().size();
			if (number > numberOfBorrow) {
				mostBorrow = o.getType();
				numberOfBorrow = number;
			}
		}
		return mostBorrow;
	}

}

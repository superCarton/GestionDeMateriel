package borrowmanager.view;

import java.util.List;

public abstract class ListPicker<T> extends Picker<Integer, T> {

	private List<T> realList;
	
	public ListPicker(List<T> l) {
		super(l);
	}

	@Override
	protected Integer getUniqueIdentifier(T element) {
		return realList.indexOf(element);
	}

}

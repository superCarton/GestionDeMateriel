package borrowmanager.view.menu;

import java.util.Arrays;

import borrowmanager.model.element.State;
import borrowmanager.view.Picker;

public class StatePicker extends Picker<String, State> {

	public StatePicker() {
		super(Arrays.asList(State.values()));
	}

	@Override
	protected String elementToListString(State element) {
		return element.name();
	}

	@Override
	protected String getUniqueIdentifier(State element) {
		return element.name();
	}

}

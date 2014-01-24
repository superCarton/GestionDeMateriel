package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.element.State;
import borrowmanager.model.material.Material;
import borrowmanager.view.ItemPicker;
import borrowmanager.view.MaterialPicker;
import borrowmanager.view.TextInterfacePage;

public class NeedRepairList extends TextInterfacePage {

	private Manager manager;
	public NeedRepairList(Manager m) {
		manager = m;
	}
	
	@Override
	public TextInterfacePage display() {
		System.out.println("Here is the list of the material that needs to be repaired.");
		System.out.println("");
		MaterialPicker picker = new MaterialPicker(manager.getMaterialWithState(State.DESTROYED));
		picker.display();
		Material m = picker.getPickedItemId();
		
		if (m != null) {
			
		}
		return this;
	}

}

package borrowmanager.view;

import java.util.Collection;
import java.util.List;

import borrowmanager.model.material.Material;

public class MaterialPicker extends Picker<Integer, Material>{

	public MaterialPicker(List<Material> l) {
		super(l);
	}

	@Override
	protected String elementToListString(Material element) {
		return element.getMaterialType().getFullName()+" with serial number "+element.getSerialNumber();
	}

	@Override
	protected Integer getUniqueIdentifier(Material element) {
		return ((List<Material>) getList()).indexOf(element);
	}

}

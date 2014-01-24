package borrowmanager.view;

import java.util.Collection;

import borrowmanager.model.material.Material;

public class MaterialPicker extends Picker<Material, Material>{

	public MaterialPicker(Collection<Material> l) {
		super(l);
	}

	@Override
	protected String elementToListString(Material element) {
		return element.getMaterialType().getFullName()+" with serial number "+element.getSerialNumber();
	}

	@Override
	protected Material getUniqueIdentifier(Material element) {
		return element;
	}

}

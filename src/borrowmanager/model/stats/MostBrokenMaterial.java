package borrowmanager.model.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import borrowmanager.model.Manager;
import borrowmanager.model.element.State;
import borrowmanager.model.material.Material;
import borrowmanager.model.material.MaterialType;

/**
 * The MostBrokenMaterial is a class extends of Stats which gives the most broken material
 * @author Jonathan Pujol
 *
 */
public class MostBrokenMaterial extends Stats<StatValue<MaterialType, Integer>> {

	public MostBrokenMaterial(Manager manager) {
		super(manager, "Most broken material", "The materiel which is the most broken");
	}

	@Override
	public StatValue<MaterialType, Integer> calculate() {
		List<Material> listAllMaterial = manager.getMaterials();
		Map<MaterialType, Integer> brokenMaterial = new HashMap<MaterialType, Integer>();
		for (Material material : listAllMaterial) {
			if (material.getState() == (State.DESTROYED)) {
				MaterialType type = material.getMaterialType();
				if (brokenMaterial.containsKey(type)) {
					int numberBroken = brokenMaterial.get(type);
					numberBroken++;
					brokenMaterial.put(type, numberBroken);
				}
				else {
					brokenMaterial.put(type, 1);
				}
			}
		}
		Set<MaterialType> keys = brokenMaterial.keySet();
		MaterialType typeBrokenMaterial = null;
		int number = 0;
		for (MaterialType key : keys) {
			int numberByKey = brokenMaterial.get(key);
			if (numberByKey > number) {
				number = numberByKey;
				typeBrokenMaterial = key;
			}
		}
		return new StatValue<MaterialType, Integer>(typeBrokenMaterial, number);
	}
	

}

package borrowmanager.model.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import borrowmanager.model.Manager;
import borrowmanager.model.material.Material;
import borrowmanager.model.material.MaterialCategory;

/**
 * The NumberByCategory is a class extends of Stats which gives the number and the pourcent of a category of material.
 * @author Jonathan Pujol
 * 
 */
public class NumberByCategory extends Stats<Map<MaterialCategory, StatValue<Integer, Double>>> {

	public NumberByCategory(Manager manager) {
		super(manager, "Number by category", "The number of material in stock given by category");
	}

	@Override
	public Map<MaterialCategory, StatValue<Integer, Double>> calculate() {
		List<Material> materialList = manager.getMaterials();
		Map<MaterialCategory, Integer> map = new HashMap<MaterialCategory, Integer>();
		for (Material m : materialList) {
			MaterialCategory category = m.getMaterialType().getCategory();
			if (map.containsKey(category)) {
				int number = map.get(m);
				number++;
				map.put(category, number);
			}
			else {
				map.put(category, 1);
			}
		}
		int sizeStock = materialList.size();
		MaterialCategory[] listCategory = MaterialCategory.values();
		Map<MaterialCategory, StatValue<Integer, Double>> numberAndPourcent = new HashMap<MaterialCategory, StatValue<Integer, Double>>();
		for (MaterialCategory mc : listCategory) {
			int numberCat = map.get(mc);
			double pourcent = numberCat/sizeStock;
			numberAndPourcent.put(mc, new StatValue<Integer, Double>(numberCat, pourcent));
		}
		return numberAndPourcent;
	}

}

import java.util.*;

public class Sandwich {
	private Set<String> ingredients = Collections.synchronizedSet(new HashSet<String>());
	private boolean eatable = false;
	private boolean isReadyForChef = false;
	private boolean isReadyForAgent = true;
	
	public void addIngredient(String s) {
		if(eatable) {
			System.out.println(eatable);
			return;
		}
		if(sandwichIngredientsDoesntContain(s) && SandwichUtils.isValidIngredient(s)) {
			ingredients.add(s);
		}
		
		if(ingredients.size() == SandwichUtils.MAX_SIZE) {
			eatable = true;
		}
		if(ingredients.size() == 2) {
			isReadyForChef = true;
			isReadyForAgent = false;
		}
	}
	public Set<String> getIngredients(){
		return ingredients;
	}
	public void eatSandwich() {
		if(!eatable) {
			return;
		}
		
		ingredients.clear();
		eatable = false;
		isReadyForChef = false;
		isReadyForAgent = true;
	}

	private boolean sandwichIngredientsDoesntContain(String s) {
		Iterator<String> iter = ingredients.iterator();
		while (iter.hasNext()) {
			String ingredient = iter.next();
			if(s.equalsIgnoreCase(ingredient)) {
				return false;
			}
		}
		return true;
	}
	public boolean isEatable() {
		return eatable;
	}
	public boolean isReadyForChef() {
		return isReadyForChef;
	}
	public boolean isReadyForAgent() {
		return isReadyForAgent;
	}
	public String toString() {
		String s = "Sandwich Ingredients: ";
		Iterator<String> iter = ingredients.iterator();
		while (iter.hasNext()) {
			String ingredient = iter.next();
			s+= ingredient + " ";
		}
		return s;
	}
}

import java.util.*;

public class Sandwich {
	private Collection<String> ingredients = Collections.synchronizedCollection(new ArrayList<String>());
	private boolean eatable = false;
	private boolean isReadyForChef = false;
	private boolean isReadyForAgent = true;
	
	public synchronized void addIngredient(String s) {
		if(eatable) {
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
	public synchronized Collection<String> getIngredients(){
		return ingredients;
	}
	public synchronized void eatSandwich() {
		if(!eatable) {
			return;
		}
		
		ingredients.clear();
		eatable = false;
		isReadyForChef = false;
		isReadyForAgent = true;
		System.out.println("IMA EAT THIS SANDWICH");
	}

	private boolean sandwichIngredientsDoesntContain(String s) {
		synchronized(ingredients) { 
			Iterator<String> iter = ingredients.iterator();
			while (iter.hasNext()) {
				String ingredient = iter.next();
				if(s.equalsIgnoreCase(ingredient)) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean isEatable() {
		return eatable;
	}
	public boolean isReadyForChef() {
		System.out.println("WE READY? " + isReadyForChef);
		return isReadyForChef;
	}
	public boolean isReadyForAgent() {
		return isReadyForAgent;
	}
	public String toString() {
		String s = "Sandwich Ingredients: ";
		synchronized(ingredients) { 
			Iterator<String> iter = ingredients.iterator();
			while (iter.hasNext()) {
				String ingredient = iter.next();
				s+= ingredient + " ";
			}
		}
		return s;
	}
	public static void main(String[] args) {
		Sandwich s = new Sandwich();
		
	}
}

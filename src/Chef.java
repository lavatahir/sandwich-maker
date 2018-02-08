import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Chef implements Runnable{
	private String ingredient;
	private Sandwich sandwich;
	
	public Chef(String ingredient) {
		this.setIngredient(ingredient);
	}
	public Chef(Sandwich s, String ingredient){
		this.sandwich = s;
		this.ingredient = ingredient;
	}
	public String getIngredient() {
		return this.ingredient;
	}
	public void setIngredient(String ingredient) {
		if(checkIfIngredientValid(ingredient)){
			this.ingredient = ingredient;
		}
	}
	public void addIngredientToSandwich(Sandwich s) {
		s.addIngredient(ingredient);
	}
	public boolean checkIfIngredientValid(String s) {
		for(String ing : SandwichUtils.ingredients) {
			if(ing.equals(s)) {
				return true;
			}
		}
		return false;
	}
	public String toString() {
		String s = "Chef\n";
		if(ingredient != null) {
			s+="Ingredient Available: " + ingredient;
		}
		else {
			s+="No Ingredient Available";
		}
		return s;	
	}
	private boolean checkIfMissingIngredient(Sandwich sandwich) {
		Set<String> copySandwichIngredients = sandwich.getIngredients();
		copySandwichIngredients.add(ingredient);
		return copySandwichIngredients.containsAll(SandwichUtils.ingredients);
	}
	
	@Override
	public void run() {
		System.out.println("Chef " + Thread.currentThread().getName() + " started.");
		while(true) {
			synchronized (sandwich) {
				while ((!sandwich.isEatable() && !sandwich.isReadyForChef()) || !checkIfMissingIngredient(sandwich)) {
					try {
						sandwich.wait();
					} catch (InterruptedException e) {
						return;
					}
				}
				if(checkIfMissingIngredient(sandwich) && sandwich.isReadyForChef()) {
					sandwich.addIngredient(ingredient);
					System.out.println("Chef " +Thread.currentThread().getName() + " added ingredient " + ingredient);	
					sandwich.eatSandwich();
					System.out.println("Chef " +Thread.currentThread().getName() + " consumed sandwich");	
				}
				sandwich.notifyAll();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
}

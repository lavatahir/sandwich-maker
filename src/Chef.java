
public class Chef implements Runnable{
	private String ingredient;
	private Sandwich sandwich;
	private boolean isSandwichMissingChefIngredient = false;
	
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
		System.out.println("WE MISSIN? " + sandwich.getIngredients().contains(ingredient));
		return sandwich.getIngredients().contains(ingredient);
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " started.");
		
		synchronized (sandwich) {
			while (!sandwich.isEatable() && !sandwich.isReadyForChef()) {
				try {
					sandwich.wait();
				} catch (InterruptedException e) {
					return;
				}
			}
			checkIfMissingIngredient(sandwich);
			/*
			if(sandwich.isEatable()){
				System.out.println("EATIN");
				sandwich.eatSandwich();
			}
			if(sandwich.isReadyForChef()){
				if(checkIfMissingIngredient(sandwich)){
					System.out.println("ADDIN");
					sandwich.addIngredient(ingredient);
					sandwich.eatSandwich();
				}
			}
			*/
			sandwich.notifyAll();
		}
		System.out.println(Thread.currentThread().getName() + " consumed sandwich");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
	}
	
	public static void main(String[] args) {
	}
}

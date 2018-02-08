import java.util.Set;

public class Chef implements Runnable{
	private String ingredient;
	private Plate plate;
	
	public Chef(Plate p, String ingredient){
		this.plate = p;
		this.ingredient = ingredient;
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
	
	private boolean checkIfMissingIngredient(Plate plate) {
		Set<String> copyPlateIngredients = plate.getIngredients();
		copyPlateIngredients.add(ingredient);
		return copyPlateIngredients.containsAll(PlateUtils.ingredients);
	}
	
	@Override
	public void run() {
		System.out.println("Chef " + Thread.currentThread().getName() + " started.");
		while(true) {
			synchronized (plate) {
				while ((!plate.isEatable() && !plate.isReadyForChef()) || !checkIfMissingIngredient(plate)) {
					try {
						plate.wait();
					} catch (InterruptedException e) {
						return;
					}
				}
				if(checkIfMissingIngredient(plate) && plate.isReadyForChef()) {
					plate.addIngredient(ingredient);
					System.out.println("Chef " +Thread.currentThread().getName() + " added ingredient " + ingredient);	
					plate.eatSandwich();
					System.out.println("Chef " +Thread.currentThread().getName() + " consumed sandwich");	
				}
				plate.notifyAll();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
}

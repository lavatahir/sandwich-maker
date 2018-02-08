import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agent implements Runnable{
	private Sandwich sandwich;
	
	public Agent(Sandwich s) {
		this.sandwich = s;
	}
	public String getRandomIngredient() {
		int index = (int )(Math.random() * SandwichUtils.MAX_SIZE);
		return SandwichUtils.ingredients.get(index);
		
	}
	
	public List<String> pickNRandomIngredients(int n) {
	    List<String> copy = new ArrayList<String>(SandwichUtils.ingredients);
	    Collections.shuffle(copy);
	    return copy.subList(0, n);
	}
	
	@Override
	public void run() {
		for (int x = 0; x < 3; x++) {
			List<String> randomIngredients = pickNRandomIngredients(2);
			System.out.println("Agent " + Thread.currentThread().getName() + " started.");
			for(int i = 0; i < randomIngredients.size(); i++) {
	            String item = randomIngredients.get(i);
	            synchronized (sandwich) {
	                while (sandwich.isEatable() || !sandwich.isReadyForAgent()) {
	                    try {
	                    	sandwich.wait();
	                    } catch (InterruptedException e) {
	                        return;
	                    }
	                }    
	                
	                if(sandwich.isReadyForAgent()) {
	                	sandwich.addIngredient(item);
	                	System.out.println("Agent " + Thread.currentThread().getName() + " added " + item + " to sandwich.");
	                	sandwich.notifyAll();
	                }
	            }
	            try {
	                Thread.sleep(500);
	            } catch (InterruptedException e) {}
	        }
		}
	}
}

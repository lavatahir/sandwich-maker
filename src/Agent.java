import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agent implements Runnable{
	private Plate plate;
	
	public Agent(Plate p) {
		this.plate = p;
	}
	
	public List<String> pickRandomIngredients() {
	    List<String> copy = new ArrayList<String>(PlateUtils.ingredients);
	    Collections.shuffle(copy);
	    return copy.subList(0, 2);
	}
	
	@Override
	public void run() {
		System.out.println("Agent " + Thread.currentThread().getName() + " started.");
		while(PlateUtils.counter <= PlateUtils.NUM_SANDWICHES) {
			System.out.println("\nAgent " + Thread.currentThread().getName() + " creating sandwich #"+PlateUtils.counter+".");
			List<String> randomIngredients = pickRandomIngredients();
			for(int i = 0; i < randomIngredients.size(); i++) {
	            String item = randomIngredients.get(i);
	            synchronized (plate) {
	                while (plate.isEatable() || !plate.isReadyForAgent()) {
	                    try {
	                    	plate.wait();
	                    } catch (InterruptedException e) {
	                        return;
	                    }
	                }    
	                
	                if(plate.isReadyForAgent()) {
	                	plate.addIngredient(item);
	                	System.out.println("Agent " + Thread.currentThread().getName() + " added " + item + " to sandwich.");
	                	plate.notifyAll();
	                }
	            }
	            try {
	                Thread.sleep(500);
	            } catch (InterruptedException e) {}
	        }
			PlateUtils.incrementCounter();
		}
	}
}

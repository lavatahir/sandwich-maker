import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Agent implements Runnable{
	private Sandwich sandwich;
	private HashSet<String> ingredient = new HashSet<String>(1);
	
	public Agent(Sandwich s) {
		this.sandwich = s;
	}
	public String getRandomIngredient() {
		int index = (int )(Math.random() * SandwichUtils.MAX_SIZE);
		return SandwichUtils.ingredients.get(index);
		
	}
	
	@Override
	public void run() {
		List<String> randomIngredients = SandwichUtils.pickNRandomIngredients(2);
		for(int i = 0; i < randomIngredients.size(); i++) {
            String item = randomIngredients.get(i);
            System.out.println(Thread.currentThread().getName() + " started ");
            System.out.println(Thread.currentThread().getName() + " produced " + item);
             
            synchronized (sandwich) {
                while (sandwich.isEatable() || !sandwich.isReadyForAgent()) {
                    try {
                        sandwich.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }            
                sandwich.addIngredient(item);               	
                if(sandwich.isReadyForChef()) {
                	sandwich.notifyAll();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
		
	}

	public static void main(String[] args) {
		Sandwich s = new Sandwich();
		Thread agentThread = new Thread(new Agent(s));
		agentThread.start();
	}

}

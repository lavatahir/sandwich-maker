
public class Main {
	public static void main(String[] args){
		Plate s = new Plate();
		Agent a = new Agent(s);
		Chef c1 = new Chef(s,"BREAD");
		Chef c2 = new Chef(s,"PEANUT BUTTER");
		Chef c3 = new Chef(s,"JAM");
		
		Thread agentThread = new Thread(a);
		Thread c1Thread = new Thread(c1);
		Thread c2Thread = new Thread(c2);
		Thread c3Thread = new Thread(c3);
		
		agentThread.start();
		c1Thread.start();
		c2Thread.start();
		c3Thread.start();
	}

}

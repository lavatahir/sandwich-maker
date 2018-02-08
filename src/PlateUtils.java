import java.util.Arrays;
import java.util.List;

public class PlateUtils {
	public static final int MAX_SIZE = 3;
	public static List<String> ingredients = Arrays.asList("BREAD", "PEANUT BUTTER", "JAM");
	public static final int NUM_SANDWICHES = 20;
	
	public static int counter = 1;

	public static void incrementCounter() {
		counter++;
	}

	public static boolean isValidIngredient(String s) {
		return PlateUtils.ingredients.contains(s);
	}
	
}

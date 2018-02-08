import java.util.Arrays;
import java.util.List;

public class PlateUtils {
	public static final int MAX_SIZE = 3;
	public static List<String> ingredients = Arrays.asList("BREAD", "PEANUT BUTTER", "JAM");
	public static boolean isValidIngredient(String s) {
		return PlateUtils.ingredients.contains(s);
	}
	
}

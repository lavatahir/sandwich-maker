import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SandwichUtils {
	public static final int MAX_SIZE = 3;
	public static List<String> ingredients = Arrays.asList("BREAD", "PEANUT BUTTER", "JAM");
	public static boolean isValidIngredient(String s) {
		return SandwichUtils.ingredients.contains(s);
	}
	
}

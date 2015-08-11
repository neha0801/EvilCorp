import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	public static long validateAccNo(Scanner sc, String prompt) {
		long acc=0;
		boolean isValid = false;
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextLong()) {
				acc = sc.nextLong();
				isValid = true;
			} else {
				System.out.println("Error! Invalid account value. Try again.");
			}
			sc.nextLine();

		}
		return acc;
	}
	public static boolean validateCustName(String s) {
		String regx = "[a-zA-Z]+(\\s)*[a-zA-Z]";
	    Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(s);
	    return matcher.find();
	}
	
	public static double validateAmount(Scanner sc, String prompt) {
		double bal=0.0;
		boolean isValid = false;
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextDouble()) {
				bal = sc.nextDouble();
				isValid = true;
			} else {
				System.out.println("Error! Invalid account balance value. Try again.");
				break;
			}
			sc.nextLine();
		}
		if(bal<0.0){
			System.out.println("If Account balance is less than $0.00, then you will be charged $35 fee.");
		}
		return bal;
	}
}
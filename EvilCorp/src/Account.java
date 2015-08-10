import java.text.NumberFormat;
import java.util.List;

/**
 * @author Neha
 *
 */
public class Account {

	private long accountNumber;
	private String customerName;
	private double accountBalance;
	NumberFormat currency;
	
	public Account(){
		this.accountNumber =0;
		this.customerName ="";
		this.accountBalance=0.0;
		this.currency = NumberFormat.getCurrencyInstance();
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public void updateAccountBalance(List<Double> amt){
		for (double a : amt)
			this.accountBalance += a;
		if (this.accountBalance <=0.0){
			this.accountBalance -=35.0;
		}

	}
	
	public String toString(){
		return "Customer Name: " + this.customerName + "\n" 
				+ "Account Number: " + this.accountNumber
				+ "\nAccount Balance: " + currency.format(this.accountBalance);
					
		
	}
	
}

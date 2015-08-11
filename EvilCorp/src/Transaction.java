import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Transaction implements Comparable<Transaction> { 
	
	private Account myAccount;
	private Date transDate;
	private double amt;
	private NumberFormat currency;
	
	public Transaction(Account account){
		this.myAccount = account;
		this.currency = NumberFormat.getCurrencyInstance(); 
	}
	
	public Account getMyAccount() {
		return myAccount;
	}

	public void setMyAccount(Account myAccount) {
		this.myAccount = myAccount;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String toString(){
		String dateOutput = "";
		String amtOutput="";
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
		cal.setTimeInMillis(this.transDate.getTime());
		dateOutput += "The Date of transaction: " +  formatter.format((cal.getTime()));
		amtOutput += "The transaction amount: " + currency.format(this.amt);
		return "--------------------ACCOUNT DETAILS---------------------" + "\n"  
				+ myAccount.toString() + "\n" 
				+ dateOutput + "\n" + amtOutput + "\n";
	}

	/**
	 * to compare objects 
	 */
	@Override
	public int compareTo(Transaction t1) {
		return this.getTransDate().compareTo(t1.transDate);
	}

}

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Transaction {
	
	private Account myAccount;
	private HashMap<Long,Double> transData = new HashMap<Long,Double>();
	private List<Double> sortedAmt = new ArrayList<Double>();
	private NumberFormat currency;
	
	public Transaction(Account account){
		this.myAccount = account;
		this.currency = NumberFormat.getCurrencyInstance(); 
	}
	
	public void setTransData(long date, double amt){
		this.transData.put(date, amt);
	}

	public Account getMyAccount() {
		return myAccount;
	}

	public void setMyAccount(Account myAccount) {
		this.myAccount = myAccount;
	}

	public HashMap<Long, Double> getTransData() {
		return transData;
	}
	

	public List<Double> getSortedAmt() {
		return sortedAmt;
	}

	public void setSortedAmt(List<Double> sortedAmt) {
		this.sortedAmt = sortedAmt;
	}
	
	public void sortedDateAmt(){
		SortedSet<Long> keys = new TreeSet<Long>(transData.keySet());
		for(long k : keys){
			sortedAmt.add(transData.get(k));
		}
	}

	public String toString(){
		String dateOutput = "";
		String amtOutput="";
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
		for (Long k : transData.keySet()) {
			cal.setTimeInMillis(k);
			dateOutput += "The Date of transaction: " +  formatter.format((cal.getTime()));
			amtOutput += "The transaction amount: " + currency.format(transData.get(k));
		}
		return "--------------------ACCOUNT DETAILS---------------------" + "\n"  
				+ myAccount.toString() + "\n" 
				+ dateOutput + "\n" + amtOutput + "\n";
	}
	
	public Date changeStringToDate(String date)
	{
		SimpleDateFormat formattedDate = new SimpleDateFormat("mm/dd/yyyy");
		Date date1 = null;
		GregorianCalendar cal = new GregorianCalendar();
		try
		{
			date1 = formattedDate.parse(date);
			cal.setTime(date1);
			
			
		} catch(ParseException e)
		{
			// if incorrect format is entered for date exit the application
			System.out.println("Incorrect date Format. Run the application again. Good Bye!!");
		}
		return date1;
	}
}

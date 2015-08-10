/**
 * @author Neha
 *
 */

import java.util.*;

public class TransactionApp {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		Account myAccount = null;
		Transaction myTrans = null;
		long accNo=1;
		String custName, transType,dateofTrans;
		Date transDate= null;
		double accBal,amtEntered;

		List<Account> arrayAccount = new ArrayList<Account>();
		
		List<Transaction> transArrayList = new ArrayList<Transaction>();
		
		System.out.println("Welcome to Evil Corp Savings and Loan");
		System.out.println("Please create the user account(s)");
		
		System.out.print("Enter an account # or -1 to stop entering accounts :");
		accNo=sc.nextLong();		
		
		while (accNo >=0){
			myAccount = new Account();
			myAccount.setAccountNumber(accNo);
			sc.nextLine();
			System.out.print("Enter the name for acct # " + myAccount.getAccountNumber() + ": ");
			custName = sc.nextLine();
			myAccount.setCustomerName(custName);
			
			System.out.print("Enter the balance for acct # " + myAccount.getAccountNumber() + ": ");
			accBal = sc.nextDouble();
			myAccount.setAccountBalance(accBal);
			arrayAccount.add(myAccount);
			
			System.out.print("Enter an account # or -1 to stop entering accounts :");
			accNo=sc.nextLong();
		} 
		
		System.out.println("Enter a transaction type: \n"
				+ "C : Check \nDC: Debit card \nD : Deposit \nW : Withdrawal \n-1 to finish");
		transType = sc.next();
		
		while (!transType.equalsIgnoreCase("-1"))
		{
			System.out.print("Enter the account #: ");
			accNo = sc.nextLong();

			System.out.print("Enter the amount for your transaction: ");
			amtEntered = sc.nextDouble();
			
			System.out.println("Enter the transaction date:");
			System.out.print("(Date format should be mm/dd/yyyy): ");
			dateofTrans = sc.next();

			if (transType.equalsIgnoreCase("W") || transType.equalsIgnoreCase("DC")){
				amtEntered = - amtEntered;
				System.out.println("Transaction Type: Debit");
			} else if (transType.equalsIgnoreCase("C") || transType.equalsIgnoreCase("D"))
				System.out.println("Transaction Type: Credit");
			else {
				System.out.println("Incorrect value entered");
				continue;
			}

			for (Account acc : arrayAccount){
				if (acc.getAccountNumber() == accNo){
					myTrans = new Transaction(acc);
					transDate = myTrans.changeStringToDate(dateofTrans);
					myTrans.setTransData(transDate.getTime(), amtEntered);
					transArrayList.add(myTrans);
				}
			}
			System.out.println("Enter a transaction type: \n"
					+ "C : Check \nDC: Debit card \nD : Deposit \nW : Withdrawal \n-1 to finish");
			transType = sc.next();
			
		}
		for(Transaction trans : transArrayList){
			trans.sortedDateAmt();
			trans.getMyAccount().updateAccountBalance(trans.getSortedAmt());
		}
		
		System.out.println("\n---------------------------RESULTS-------------------------------");
		for(Account acc : arrayAccount){
			System.out.println("\nThe Balance for account #" 
						+ acc.getAccountNumber() + " :" +acc.getAccountBalance());
		}
		
		System.out.println("-------------------------------------------------------------------");
		for(Transaction trans : transArrayList){
			System.out.println(trans.toString());
		}
		sc.close();
		System.out.println("Good Bye!!");
	}

}

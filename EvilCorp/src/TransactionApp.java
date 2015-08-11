/**
 * @author Neha
 *
 */

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class TransactionApp {
	
	private static Scanner sc = new Scanner(System.in);
	private static Account myAccount = null;
	private static List<Account> fileAccount = new ArrayList<Account>();
	private static long accNo;

	/**
	 * @param args
	 */
	public static void main(String[] args){
	
		Transaction myTrans = null;
		
		String choice,custName, transType,dateofTrans;
		Date transDate= null;
		double accBal,amtEntered;
		boolean exists = false;
		NumberFormat currency = NumberFormat.getCurrencyInstance(); 
		
		List<Transaction> transArrayList = new ArrayList<Transaction>();
		
		String filepath = "C:/Users/rvhu321018ur/Desktop/GIT Files/EvilCorp/EvilCorp/myAccounts.txt";
		readFile(filepath);
		
		System.out.println("Welcome to Evil Corp Savings and Loan");
		System.out.println("Please select the required operation");
		System.out.print("Add an account(A):\nRemove an account(D):"
				+ "\nTo exit and enter Transaction Application(-1):\n");
		choice = sc.next();
		while(true){
			if(choice.equalsIgnoreCase("A"))
			{
				System.out.print("Enter an account #: ");
				accNo=sc.nextLong();	
				exists = checkFile(accNo);
				if(!exists){
					myAccount = new Account();
					myAccount.setAccountNumber(accNo);
					sc.nextLine();
					System.out.print("Enter the name for acct # " + myAccount.getAccountNumber() + ": ");
					custName = sc.nextLine();
					myAccount.setCustomerName(custName);
					System.out.print("Enter the balance for acct # " + myAccount.getAccountNumber() + ": ");
					accBal = sc.nextDouble();
					myAccount.setAccountBalance(accBal);
					fileAccount.add(myAccount);
					writeToFile();
					System.out.println("Account added");
					System.out.println("Please select the required operation");
					System.out.println("Add an account(A):\nRemove an account(D):"
							+ "\nTo exit and enter Transaction Application(-1):\n");
					choice = sc.next();
				}else{
					exists = false;
					System.out.println("Please select the required operation");
					System.out.println("Add an account(A):\nRemove an account(D):"
							+ "\nTo exit and enter Transaction Application(-1):\n");
					choice = sc.next();
				}
			} else if (choice.equalsIgnoreCase("D")){
				removeAccount();
				System.out.println("Please select the required operation");
				System.out.println("Add an account(A):\nRemove an account(D):"
						+ "\nTo exit and enter Transaction Application(-1):\n");
				choice = sc.next();							
			}else if(choice.equalsIgnoreCase("-1")){
				break;
			}else{
				System.out.println("Incorrect Input");
				System.out.println("Please select the required operation");
				System.out.println("Add an account(A):\nRemove an account(D):"
						+ "\nTo exit and enter Transaction Application(-1):\n");
				choice = sc.next();
			}
				
		}
		
		System.out.println("Transaction application");
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

			for (Account acc : fileAccount){
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
		
		for(Account acc : fileAccount){
			System.out.println("\nThe Balance for account #" 
						+ acc.getAccountNumber() + " :" +currency.format(acc.getAccountBalance()));
		}
		//add the final values to file
		writeToFile();
		
		System.out.println("-------------------------------------------------------------------");
		for(Transaction trans : transArrayList){
			System.out.println(trans.toString());
		}
		sc.close();
		System.out.println("Good Bye!!");
	}
	
	/**
	 * 
	 * @param filepath
	 */
	public static void readFile(String filepath){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line;
			while((line=reader.readLine())!=null){
				myAccount = new Account();
				myAccount.setAccountNumber(Long.parseLong(line.split(":")[1]));
				line = reader.readLine();
				myAccount.setCustomerName(line.split(":")[1]);
				line = reader.readLine();
				myAccount.setAccountBalance(Double.parseDouble(line.split(":")[1]));
				fileAccount.add(myAccount);
			}
			reader.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		} 
	}
	public static void writeToFile(){
		String fileName = (System.getProperty("user.dir") + File.separatorChar +"myAccounts.txt");
		FileWriter writer;
		try{
			writer = new FileWriter(fileName);
			BufferedWriter bufferWritter = new BufferedWriter(writer);
			for(Account acc : fileAccount){
				bufferWritter.write("Account#:" + acc.getAccountNumber() + "\n");
				bufferWritter.write("Customer Name:" + acc.getCustomerName() + "\n");
				bufferWritter.write("Account Balance:" + acc.getAccountBalance() + "\n");
		      }
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * if the account already exists in database or not
	 * @param accNo
	 * @return whether account exists or not
	 */
	public static boolean checkFile(long accNo){
		for (Account acc : fileAccount){
			if(acc.getAccountNumber()==accNo)
			{
				System.out.println("Account already exists. Details shown below:");
				System.out.println(acc.toString());
				return true;
			}
		}
		return false;
	}

	/**
	 *  to remove the account from the array
	 * @param  
	 */
	public static void removeAccount(){
		String c;
		System.out.println("Enter the account number you want to remove:");
		accNo = sc.nextLong();
		sc.nextLine();
		for(Account account : fileAccount)
		{
			if(account.getAccountNumber()==accNo){
				if(account.eligibleToRemove()){
					System.out.println("Are you sure you want to remove the account?(Y/N)");
					c = sc.next();
					sc.nextLine();
					if (c.equalsIgnoreCase("Y")){
						fileAccount.remove(account);
						System.out.println("Account is removed.");
						break;
					}else{					
						System.out.println("Operation Canceled:");
						break;
					}
					
				}else{
					System.out.println("Kindly clear the account balance");
					break;
				}					
			}
		}
	}

}

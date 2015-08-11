/**
 * @author Neha
 *
 */

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionApp {

	private static Scanner sc = new Scanner(System.in);
	private static Account myAccount = null;
	private static List<Account> fileAccount = new ArrayList<Account>();
	private static long accNo;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Transaction myTrans = null;
		String choice, custName, transType, dateofTrans;
		Date transDate = null;
		double accBal, amtEntered;
		boolean exists = false;
		NumberFormat currency = NumberFormat.getCurrencyInstance();

		List<Transaction> transArrayList = new ArrayList<Transaction>();

		File myFile = new File("myAccounts");
		try {
			myFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		readFile(myFile);

		System.out.println("---------------------Welcome to Evil Corp Savings and Loan-----------------------");
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("Please select the required operation");
		System.out.println("Add an account(A):\nRemove an account(D):"
				+ "\nTo finish and enter Transaction Application(-1):");
		choice = sc.next();
		while (true) 
		{
			if (choice.equalsIgnoreCase("A")) {
				accNo = Validator.validateAccNo(sc, "Enter an account #: ");
				exists = checkFile(accNo);
				if (!exists) {
					myAccount = new Account();
					myAccount.setAccountNumber(accNo);
					System.out.print("Enter the name for acct # "
							+ myAccount.getAccountNumber() + ": ");
					custName = sc.nextLine();
					if (!Validator.validateCustName(custName)) {
						System.out.println("Name validation failed");
						continue;
					}
					myAccount.setCustomerName(custName);
					accBal = Validator.validateAmount(
							sc,
							("Enter the balance for acct # "
									+ myAccount.getAccountNumber() + ": "));

					myAccount.setAccountBalance(accBal);

					fileAccount.add(myAccount);

					writeToFile(myFile);

					System.out.println("Account added");
					System.out.println("Please select the required operation");
					System.out
							.println("Add an account(A):\nRemove an account(D):"
									+ "\nTo finish and enter Transaction Application(-1):");
					choice = sc.next();
				} else {
					exists = false;
					System.out.println("Please select the required operation");
					System.out
							.println("Add an account(A):\nRemove an account(D):"
									+ "\nTo finish and enter Transaction Application(-1):");
					choice = sc.next();
				}
			} else if (choice.equalsIgnoreCase("D")) {
				removeAccount();
				System.out.println("Please select the required operation");
				System.out.println("Add an account(A):\nRemove an account(D):"
						+ "\nTo finish and enter Transaction Application(-1):");
				choice = sc.next();
			} else if (choice.equalsIgnoreCase("-1")) {
				break;
			} else {
				System.out.println("Incorrect Input");
				System.out.println("Please select the required operation");
				System.out.println("Add an account(A):\nRemove an account(D):"
						+ "\nTo finish and enter Transaction Application(-1):");
				choice = sc.next();
			}

		}

		System.out.println("-------------------Transaction application-----------------");
		System.out
				.println("Enter a transaction type: \n"
						+ "C : Check \nDC: Debit card \nD : Deposit \nW : Withdrawal \n-1 to finish");
		transType = sc.next();
		
		while (!transType.equalsIgnoreCase("-1")) {
			
			if (transType.equalsIgnoreCase("W")
					|| transType.equalsIgnoreCase("DC")) 
				System.out.println("Transaction Type: WITHDRAWAL");
			else if (transType.equalsIgnoreCase("C")
					|| transType.equalsIgnoreCase("D"))
				System.out.println("Transaction Type: DEPOSIT");
			else if (transType.equalsIgnoreCase("-1"))
				System.out.println("Exiting the application");
			else {
				System.out.println("Incorrect value entered");
				System.out.println("Enter a transaction type: \n"
						+ "C : Check \nDC: Debit card \nD : Deposit \nW : Withdrawal \n-1 to finish");
				transType = sc.next();
				continue;
			}
			accNo = Validator.validateAccNo(sc, "Enter an account #: ");

			amtEntered = Validator.validateAmount(sc,
					("Enter the amount for your transaction: "));

			System.out.println("Enter the transaction date:");
			System.out.print("(Date format should be mm/dd/yyyy): ");
			dateofTrans = sc.next();

			transDate = changeStringToDate(dateofTrans);

			if (transDate == null) {
				System.out.println("Enter transaction details again.");
				continue;
			}
			if (transType.equalsIgnoreCase("W")	|| transType.equalsIgnoreCase("DC")) 
				amtEntered = -amtEntered;

			for (Account acc : fileAccount) {
				if (acc.getAccountNumber() == accNo) {
					myTrans = new Transaction(acc);
					myTrans.setAmt(amtEntered);
					myTrans.setTransDate(transDate);
					transArrayList.add(myTrans);
				}
			}
			if (myTrans == null)
				System.out.println("The account does not exist. No action performed");
			else
			 System.out.println(myTrans.toString());

			System.out
					.println("Enter a transaction type: \n"
							+ "C : Check \nDC: Debit card \nD : Deposit \nW : Withdrawal \n-1 to finish");
			transType = sc.next();

		}
		Collections.sort(transArrayList);	
		for (Transaction trans : transArrayList) {
					
			trans.getMyAccount().updateAccountBalance(trans.getAmt());
		}

		System.out
				.println("\n---------------------------RESULTS-------------------------------");

		for (Account acc : fileAccount) {
			System.out.println("\nThe Balance for account #"
					+ acc.getAccountNumber() + " :"
					+ currency.format(acc.getAccountBalance()));
		}
		// add the final values to file
		writeToFile(myFile);

		System.out
				.println("-------------------------------------------------------------------");
		for (Transaction trans : transArrayList) {
			System.out.println(trans.toString());
		}
		sc.close();
		System.out.println("Good Bye!!");
	}

	/**
	 * to convert string to date object
	 * 
	 * @param date
	 * @return date object
	 */

	public static Date changeStringToDate(String date) {
		SimpleDateFormat formattedDate = new SimpleDateFormat("mm/dd/yyyy");
		Date date1 = null;
		GregorianCalendar cal = new GregorianCalendar();
		try {
			date1 = formattedDate.parse(date);
			cal.setTime(date1);
		} catch (ParseException e) {
			// if incorrect format is entered for date exit the application
			System.out.println("Incorrect date Format");

		}
		return date1;
	}

	/**
	 * *
	 * 
	 * @param myFile
	 */
	public static void readFile(File myFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(myFile));
			String line;
			while ((line = reader.readLine()) != null) {
				myAccount = new Account();
				myAccount.setAccountNumber(Long.parseLong(line.split(":")[1]));
				line = reader.readLine();
				myAccount.setCustomerName(line.split(":")[1]);
				line = reader.readLine();
				myAccount
						.setAccountBalance(Double.parseDouble(line.split(":")[1]));
				fileAccount.add(myAccount);
			}
			reader.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * 
	 * @param myFile
	 */
	public static void writeToFile(File myFile) {
		FileWriter writer;
		try {
			writer = new FileWriter(myFile);
			BufferedWriter bufferWritter = new BufferedWriter(writer);
			for (Account acc : fileAccount) {
				bufferWritter
						.write("Account#:" + acc.getAccountNumber() + "\n");
				bufferWritter.write("Customer Name:" + acc.getCustomerName()
						+ "\n");
				bufferWritter.write("Account Balance:"
						+ acc.getAccountBalance() + "\n");
			}
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * if the account already exists in database or not
	 * 
	 * @param accNo
	 * @return whether account exists or not
	 */
	public static boolean checkFile(long accNo) {
		for (Account acc : fileAccount) {
			if (acc.getAccountNumber() == accNo) {
				System.out
						.println("Account already exists. Details shown below:");
				System.out.println(acc.toString());
				return true;
			}
		}
		return false;
	}

	/**
	 * to remove the account from the array
	 * 
	 * @param
	 */
	public static void removeAccount() {
		String c;
		System.out.println("Enter the account number you want to remove:");
		accNo = sc.nextLong();
		sc.nextLine();
		for (Account account : fileAccount) {
			if (account.getAccountNumber() == accNo) {
				if (account.eligibleToRemove()) {
					System.out
							.println("Are you sure you want to remove the account?(Y/N)");
					c = sc.next();
					sc.nextLine();
					if (c.equalsIgnoreCase("Y")) {
						fileAccount.remove(account);
						System.out.println("Account is removed.");
						break;
					} else {
						System.out.println("Operation Canceled:");
						break;
					}// close user choice if

				} else {
					System.out.println("Kindly clear the account balance");
					break;
				}// close eligible for deleting account if
			}// closing if to check account object with account number
		}// closing for loop
	}

}

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

class Transaction {
    private String type;
    private double amount;
    private String date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = new Date().toString(); // Get the current date and time
    }

    @Override
    public String toString() {
        return "TYPE: " + type + ", AMOUNT: Rs. " + amount + ", DATE: " + date;
    }
}

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public BankAccount(String accountNumber, String accountHolder, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add(new Transaction("ACCOUNT CREATED", initialDeposit)); // Record the account creation transaction
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("DEPOSIT", amount));
            System.out.println("DEPOSITED: Rs. " + amount);
        } else {
            System.out.println("INVALID AMOUNT!");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("WITHDRAWAL", amount));
            System.out.println("WITHDRAWN: Rs. " + amount);
            return true;
        } else {
            System.out.println("INSUFFICIENT BALANCE OR INVALID AMOUNT!");
            return false;
        }
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("NO TRANSACTIONS FOUND!");
        } else {
            System.out.println("\nTRANSACTION HISTORY FOR ACCOUNT " + accountNumber + ":");
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public static void deleteAccount(ArrayList<BankAccount> accounts, String accNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accNumber)) {
                System.out.println("ARE YOU SURE YOU WANT TO DELETE THIS ACCOUNT? (YES/NO)");
                Scanner scanner = new Scanner(System.in);
                String confirm = scanner.nextLine();

                if (confirm.equalsIgnoreCase("YES")) {
                    accounts.remove(i);
                    System.out.println("ACCOUNT DELETED SUCCESSFULLY!");
                } else {
                    System.out.println("ACCOUNT DELETION CANCELED.");
                }
                return;
            }
        }
        System.out.println("ACCOUNT NOT FOUND!");
    }

    public void showDetails() {
        System.out.println("ACCOUNT NUMBER: " + accountNumber);
        System.out.println("ACCOUNT HOLDER: " + accountHolder);
        System.out.println("BALANCE: Rs. " + balance);
    }

    // Currency Conversion
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        double conversionRate = getConversionRate(fromCurrency, toCurrency);
        return amount * conversionRate;
    }

    // Get conversion rate (mocked for this example)
    private double getConversionRate(String fromCurrency, String toCurrency) {
        // Hardcoded exchange rates for PKR as the source currency
        switch (fromCurrency) {
            case "PKR":
                if (toCurrency.equals("USD")) {
                    return 0.0056;  // PKR to USD
                } else if (toCurrency.equals("EUR")) {
                    return 0.0052;  // PKR to EUR
                } else if (toCurrency.equals("INR")) {
                    return 0.76;    // PKR to INR
                }
                break;
            case "USD":
                if (toCurrency.equals("PKR")) {
                    return 178.57;  // USD to PKR
                } else if (toCurrency.equals("EUR")) {
                    return 0.85;    // USD to EUR
                } else if (toCurrency.equals("INR")) {
                    return 73.0;    // USD to INR
                }
                break;
            case "EUR":
                if (toCurrency.equals("PKR")) {
                    return 211.0;   // EUR to PKR
                } else if (toCurrency.equals("USD")) {
                    return 1.18;    // EUR to USD
                } else if (toCurrency.equals("INR")) {
                    return 87.0;    // EUR to INR
                }
                break;
        }
        return 1; // Default case if no rate found
    }
}

public class BankSystem {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nBANK SYSTEM MENU:");
            System.out.println("1. CREATE ACCOUNT");
            System.out.println("2. DEPOSIT MONEY");
            System.out.println("3. WITHDRAW MONEY");
            System.out.println("4. TRANSFER MONEY");
            System.out.println("5. SHOW ALL ACCOUNTS");
            System.out.println("6. DELETE ACCOUNT");
            System.out.println("7. VIEW TRANSACTION HISTORY");
            System.out.println("8. CURRENCY CONVERSION");
            System.out.println("9. EXIT");
            System.out.print("CHOOSE AN OPTION: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    showAllAccounts();
                    break;
                case 6:
                    deleteAccount();
                    break;
                case 7:
                    viewTransactionHistory();
                    break;
                case 8:
                    currencyConversion();
                    break;
                case 9:
                    System.out.println("EXITING... THANK YOU FOR USING THE BANK SYSTEM!");
                    return;
                default:
                    System.out.println("INVALID CHOICE! TRY AGAIN.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("ENTER ACCOUNT NUMBER: ");
        String accNumber = scanner.nextLine();
        System.out.print("ENTER ACCOUNT HOLDER NAME: ");
        String accHolder = scanner.nextLine();
        System.out.print("ENTER INITIAL DEPOSIT AMOUNT: Rs. ");
        double deposit = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        accounts.add(new BankAccount(accNumber, accHolder, deposit));
        System.out.println("ACCOUNT CREATED SUCCESSFULLY!");
    }

    private static void depositMoney() {
        System.out.print("ENTER ACCOUNT NUMBER: ");
        String accNumber = scanner.nextLine();
        BankAccount account = findAccount(accNumber);

        if (account != null) {
            System.out.print("ENTER AMOUNT TO DEPOSIT: Rs. ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("ACCOUNT NOT FOUND!");
        }
    }

    private static void withdrawMoney() {
        System.out.print("ENTER ACCOUNT NUMBER: ");
        String accNumber = scanner.nextLine();
        BankAccount account = findAccount(accNumber);

        if (account != null) {
            System.out.print("ENTER AMOUNT TO WITHDRAW: Rs. ");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("ACCOUNT NOT FOUND!");
        }
    }

    private static void transferMoney() {
        System.out.print("ENTER YOUR ACCOUNT NUMBER: ");
        String senderAcc = scanner.nextLine();
        BankAccount sender = findAccount(senderAcc);

        if (sender == null) {
            System.out.println("SENDER ACCOUNT NOT FOUND!");
            return;
        }

        System.out.print("ENTER RECIPIENT'S ACCOUNT NUMBER: ");
        String receiverAcc = scanner.nextLine();
        BankAccount receiver = findAccount(receiverAcc);

        if (receiver == null) {
            System.out.println("RECIPIENT ACCOUNT NOT FOUND!");
            return;
        }

        System.out.print("ENTER AMOUNT TO TRANSFER: Rs. ");
        double amount = scanner.nextDouble();

        if (sender.withdraw(amount)) {
            receiver.deposit(amount);
            System.out.println("TRANSFER SUCCESSFUL!");
        }
    }

    private static void showAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("NO ACCOUNTS FOUND!");
            return;
        }

        System.out.println("\nLIST OF ACCOUNTS:");
        for (BankAccount acc : accounts) {
            acc.showDetails();
            System.out.println("----------------------");
        }
    }

    private static void deleteAccount() {
        System.out.print("ENTER ACCOUNT NUMBER TO DELETE: ");
        String accNumber = scanner.nextLine();
        BankAccount.deleteAccount(accounts, accNumber);
    }

    private static void viewTransactionHistory() {
        System.out.print("ENTER ACCOUNT NUMBER TO VIEW TRANSACTION HISTORY: ");
        String accNumber = scanner.nextLine();
        BankAccount account = findAccount(accNumber);

        if (account != null) {
            account.showTransactionHistory();
        } else {
            System.out.println("ACCOUNT NOT FOUND!");
        }
    }

    private static void currencyConversion() {
        System.out.print("ENTER ACCOUNT NUMBER FOR CURRENCY CONVERSION: ");
        String accNumber = scanner.nextLine();
        BankAccount account = findAccount(accNumber);
    
        if (account != null) {
            System.out.print("ENTER AMOUNT TO CONVERT: Rs. ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
    
            System.out.print("ENTER SOURCE CURRENCY (PKR/USD/EUR): ");
            String fromCurrency = scanner.nextLine().toUpperCase();
            System.out.print("ENTER TARGET CURRENCY (PKR/USD/EUR): ");
            String toCurrency = scanner.nextLine().toUpperCase();
    
            double convertedAmount = account.convertCurrency(amount, fromCurrency, toCurrency);
    
            // Round off to 2 decimal places
            double roundedAmount = Math.round(convertedAmount * 1000.0) / 1000.0;
            System.out.println("CONVERTED AMOUNT: " + roundedAmount + " " + toCurrency);
        } else {
            System.out.println("ACCOUNT NOT FOUND!");
        }
    }    

    private static BankAccount findAccount(String accNumber) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(accNumber)) {
                return acc;
            }
        }
        return null;
    }
}

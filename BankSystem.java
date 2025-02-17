import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, String accountHolder, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
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
            System.out.println("✅ DEPOSITED ₹" + amount);
        } else {
            System.out.println("❌ INVALID AMOUNT!");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("✅ WITHDRAWN ₹" + amount);
            return true;
        } else {
            System.out.println("❌ INSUFFICIENT BALANCE OR INVALID AMOUNT!");
            return false;
        }
    }

    public static void deleteAccount(ArrayList<BankAccount> accounts, String accNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accNumber)) {
                System.out.println("⚠️ ARE YOU SURE YOU WANT TO DELETE THIS ACCOUNT? (YES/NO)");
                Scanner scanner = new Scanner(System.in);
                String confirm = scanner.nextLine();

                if (confirm.equalsIgnoreCase("YES")) {
                    accounts.remove(i);
                    System.out.println("✅ ACCOUNT DELETED SUCCESSFULLY!");
                } else {
                    System.out.println("❌ ACCOUNT DELETION CANCELED.");
                }

                scanner.close();

                return;
            }
        }
        System.out.println("⚠️ ACCOUNT NOT FOUND!");
    }

    public void showDetails() {
        System.out.println("📌 ACCOUNT NUMBER: " + accountNumber);
        System.out.println("👤 ACCOUNT HOLDER: " + accountHolder);
        System.out.println("💰 BALANCE: ₹" + balance);
    }
}

public class BankSystem {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n🏦 BANK SYSTEM MENU:");
            System.out.println("1️⃣ CREATE ACCOUNT");
            System.out.println("2️⃣ DEPOSIT MONEY");
            System.out.println("3️⃣ WITHDRAW MONEY");
            System.out.println("4️⃣ TRANSFER MONEY");
            System.out.println("5️⃣ SHOW ALL ACCOUNTS");
            System.out.println("6️⃣ DELETE ACCOUNT");
            System.out.println("7️⃣ EXIT");
            System.out.print("➡ CHOOSE AN OPTION: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.println("🔴 EXITING... THANK YOU FOR USING THE BANK SYSTEM!");
                    return;
                default:
                    System.out.println("❌ INVALID CHOICE! TRY AGAIN.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("ENTER ACCOUNT NUMBER: ");
        String accNumber = scanner.nextLine();
        System.out.print("ENTER ACCOUNT HOLDER NAME: ");
        String accHolder = scanner.nextLine();
        System.out.print("ENTER INITIAL DEPOSIT AMOUNT: ₹");
        double deposit = scanner.nextDouble();
        scanner.nextLine();

        accounts.add(new BankAccount(accNumber, accHolder, deposit));
        System.out.println("✅ ACCOUNT CREATED SUCCESSFULLY!");
    }

    private static void depositMoney() {
        System.out.print("ENTER ACCOUNT NUMBER: ");
        String accNumber = scanner.nextLine();
        BankAccount account = findAccount(accNumber);

        if (account != null) {
            System.out.print("ENTER AMOUNT TO DEPOSIT: ₹");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("⚠️ ACCOUNT NOT FOUND!");
        }
    }

    private static void withdrawMoney() {
        System.out.print("ENTER ACCOUNT NUMBER: ");
        String accNumber = scanner.nextLine();
        BankAccount account = findAccount(accNumber);

        if (account != null) {
            System.out.print("ENTER AMOUNT TO WITHDRAW: ₹");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("⚠️ ACCOUNT NOT FOUND!");
        }
    }

    private static void transferMoney() {
        System.out.print("ENTER YOUR ACCOUNT NUMBER: ");
        String senderAcc = scanner.nextLine();
        BankAccount sender = findAccount(senderAcc);

        if (sender == null) {
            System.out.println("⚠️ SENDER ACCOUNT NOT FOUND!");
            return;
        }

        System.out.print("ENTER RECIPIENT'S ACCOUNT NUMBER: ");
        String receiverAcc = scanner.nextLine();
        BankAccount receiver = findAccount(receiverAcc);

        if (receiver == null) {
            System.out.println("⚠️ RECIPIENT ACCOUNT NOT FOUND!");
            return;
        }

        System.out.print("ENTER AMOUNT TO TRANSFER: ₹");
        double amount = scanner.nextDouble();

        if (sender.withdraw(amount)) {
            receiver.deposit(amount);
            System.out.println("✅ TRANSFER SUCCESSFUL!");
        }
    }

    private static void showAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("⚠️ NO ACCOUNTS FOUND!");
            return;
        }

        System.out.println("\n📋 LIST OF ACCOUNTS:");
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

    private static BankAccount findAccount(String accNumber) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(accNumber)) {
                return acc;
            }
        }
        return null;
    }
}

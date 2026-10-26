package service.practical2;

import java.util.Scanner;

class Customer {
    private static long counter = 100;
    private final String customerId;
    private final String name, email, mobile;
    Customer(String name, String email, String mobile) {
        this.customerId = "CUST" + (++counter);
        this.name = name; this.email = email; this.mobile = mobile;
    }
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
}

class Account {
    private static int counter = 1;
    private final String accountNumber;
    private final String ownerName;
    private long balance;
    Account(String owner, long opening) {
        accountNumber = String.format("AC%04d", counter++);
        ownerName = owner; balance = opening;
    }
    public void deposit(long amount) {
        if (amount > 0) balance += amount;
        else System.out.println("Invalid deposit amount.");
    }
    public boolean withdraw(long amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount; return true;
    }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
}

public class CustomerAccountDemo {
    private static Scanner sc;
    private static String readText(String prompt) {
        System.out.print(prompt); return sc.nextLine().trim();
    }
    private static long readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { long n = Long.parseLong(sc.nextLine().trim()); if (n >= 0) return n; }
            catch (NumberFormatException ignored) { }
            System.out.println("Enter a valid non-negative whole number.");
        }
    }
    public static void main(String[] args) { run(new java.util.Scanner(System.in)); }

    public static void run(java.util.Scanner scanner) {
        sc = scanner;
        System.out.println("--- Create Customer ---");
        String name = readText("Customer name: ");
        String email = readText("Email: ");
        String mobile = readText("Mobile: ");
        Customer customer = new Customer(name, email, mobile);
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Mobile: " + customer.getMobile());

        int count = (int) readAmount("How many accounts to create for this demo? ");
        while (count < 1 || count > 20) {
            System.out.println("Enter a number from 1 to 20.");
            count = (int) readAmount("Number of accounts: ");
        }
        Account[] accounts = new Account[count];
        for (int i = 0; i < count; i++) {
            String owner = readText("Owner name for account " + (i+1) + ": ");
            long opening = readAmount("Opening balance (Rs.): ");
            accounts[i] = new Account(owner, opening);
        }
        System.out.println("\n--- Account Details ---");
        for (Account a : accounts) {
            System.out.println("Account: " + a.getAccountNumber());
            System.out.println("Owner: " + a.getOwnerName());
            System.out.println("Balance: Rs. " + a.getBalance());
        }
        Account chosen = accounts[0];
        long deposit = readAmount("Deposit amount into " + chosen.getAccountNumber() + " (Rs.): ");
        chosen.deposit(deposit);
        System.out.println("Updated balance: Rs. " + chosen.getBalance());
        long withdrawal = readAmount("Withdrawal amount from " + chosen.getAccountNumber() + " (Rs.): ");
        if (chosen.withdraw(withdrawal)) System.out.println("Withdrawal successful.");
        else System.out.println("Withdrawal failed: amount must be positive and not exceed balance.");
        System.out.println("Final balance: Rs. " + chosen.getBalance());
    }
}

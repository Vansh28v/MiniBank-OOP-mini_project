
package service;

import java.util.Scanner;

class SafeAccount {
    private long balance;

    SafeAccount(long balance) {
        this.balance = balance;
    }

    public synchronized void deposit(long amount)
            throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Amount must be positive");
        }

        balance += amount;
    }

    public synchronized void withdraw(long amount)
            throws InvalidAmountException,
                   InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Amount must be positive");
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                    amount - balance);
        }

        balance -= amount;
    }

    public synchronized long getBalance() {
        return balance;
    }
}

public class ExceptionDemo {
    public static void main(String[] args) {
        SafeAccount account = new SafeAccount(1000);

        try {
            account.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: short by "
                    + e.getShortfall());
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Transaction attempt completed.");
        }

        System.out.println("Balance: ₹" + account.getBalance());

        try {
            account.deposit(500);
            System.out.println("After deposit: ₹"
                    + account.getBalance());
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter account number to search: ");
            String accountNumber = sc.nextLine();

            try {
                findAccount(accountNumber);
            } catch (AccountNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void findAccount(String accountNumber)
            throws AccountNotFoundException {
        throw new AccountNotFoundException(
                "Account not found: " + accountNumber);
    }
}

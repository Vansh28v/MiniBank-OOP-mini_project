
package service;

public class TransferAccount {
    private final int accountNumber;
    private long balance;

    public TransferAccount(int accountNumber, long balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public static void transfer(
            TransferAccount from,
            TransferAccount to,
            long amount) {

        TransferAccount first =
                from.accountNumber < to.accountNumber ? from : to;

        TransferAccount second =
                from.accountNumber < to.accountNumber ? to : from;

        synchronized (first) {
            synchronized (second) {
                if (amount <= 0 || from.balance < amount) {
                    System.out.println("Transfer failed.");
                    return;
                }

                from.balance -= amount;
                to.balance += amount;

                System.out.println(
                    "Transferred Rs. " + amount
                    + " from Account " + from.accountNumber
                    + " to Account " + to.accountNumber
                    + " by " + Thread.currentThread().getName()
                );
            }
        }
    }
}
